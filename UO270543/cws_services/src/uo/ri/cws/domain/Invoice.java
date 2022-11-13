package uo.ri.cws.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import uo.ri.cws.domain.base.BaseEntity;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.math.Round;

@Entity
@Table(name = "TInvoices")
public class Invoice extends BaseEntity{
	public enum InvoiceStatus { NOT_YET_PAID, PAID }

	// natural attributes
	@Column(unique = true)
	private Long number;
	
	private LocalDate date;
	private double amount;
	private double vat;
	
	@Enumerated(EnumType.STRING)
	private InvoiceStatus state = InvoiceStatus.NOT_YET_PAID;

	// accidental attributes
	@OneToMany(mappedBy = "invoice")
	private Set<WorkOrder> workOrders = new HashSet<>();
	
	@OneToMany(mappedBy = "invoice")
	private Set<Charge> charges = new HashSet<>();

	Invoice(){}
	
	public Invoice(Long number) {
		this(number,  LocalDate.now(), new ArrayList<WorkOrder>());
	}

	public Invoice(Long number, LocalDate date) {
		this(number, date, new ArrayList<WorkOrder>());
	}

	public Invoice(Long number, List<WorkOrder> workOrders) {
		this(number, LocalDate.now(), workOrders);
	}

	// full constructor
	public Invoice(Long number, LocalDate date, List<WorkOrder> workOrders) { 
		checkArguments(number,date,workOrders); 
		this.number = number; 
		this.date = date;
		for(WorkOrder wo: workOrders) {
			addWorkOrder(wo);
		}
	}

	private void computeVat(LocalDate date) {
		this.vat = 21.0;
        if(date.isBefore(LocalDate.parse("2012-07-01"))){
        	this.vat = 18.0;
        }
	}

	private void checkArguments(Long number, LocalDate date, List<WorkOrder> workOrders) {
		ArgumentChecks.isNotNull(workOrders);
		ArgumentChecks.isNotNull(date);
		ArgumentChecks.isNotNull(number);
		ArgumentChecks.isTrue(number >= 0);
	}

	/**
	 * Computes amount and vat (vat depends on the date)
	 */
	private void computeAmount() {
		amount = 0;
        for (WorkOrder wo : workOrders) {
            amount += wo.getAmount();
        }
        computeVat(date);
        amount = amount * (1 + vat / 100);
        amount = Round.twoCents(amount);
	}

	/**
	 * Adds (double links) the workOrder to the invoice and updates the amount and vat
	 * @param workOrder
	 * @see UML_State diagrams on the problem statement document
	 * @throws IllegalStateException if the invoice status is not NOT_YET_PAID
	 */
	public void addWorkOrder(WorkOrder workOrder) {
		if(!getState().equals(InvoiceStatus.NOT_YET_PAID)) {
			throw new IllegalStateException("The invoice has not been paid yet");
		}
		Associations.ToInvoice.link(this, workOrder);
		workOrder.markAsInvoiced();
		computeAmount(); 
	}

	/**
	 * Removes a work order from the invoice and recomputes amount and vat
	 * @param workOrder
	 * @see UML_State diagrams on the problem statement document
	 * @throws IllegalStateException if the invoice status is not NOT_YET_PAID
	 */
	public void removeWorkOrder(WorkOrder workOrder) {
		if(!getState().equals(InvoiceStatus.NOT_YET_PAID)) {
			throw new IllegalStateException("The invoice has not been paid yet");
		}
		Associations.ToInvoice.unlink(this, workOrder);
		workOrder.markBackToFinished(); 
		computeAmount();
	}

	/**
	 * Marks the invoice as PAID, but
	 * @throws IllegalStateException if
	 * 	- Is already settled
	 *  - Or the amounts paid with charges to payment means do not cover
	 *  	the total of the invoice
	 */
	public void settle() {
		if(getState().equals(InvoiceStatus.PAID)) {
			throw new IllegalStateException("The invoice is already settled");
		}
		double cargos = 0; 
        for (Charge c : charges) {
            cargos += c.getAmount();
        } 
        if (Math.abs(cargos - amount) > 0.01) {
            throw new IllegalStateException("The amounts paid with charges to payment means do not cover the total of the invoice");
        }
        
        setState(InvoiceStatus.PAID);
	}

	public Long getNumber() {
		return number;
	}

	public void setNumber(Long number) {
		this.number = number;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public double getVat() {
		return vat;
	}

	public void setVat(double vat) {
		this.vat = vat;
	}

	public InvoiceStatus getState() {
		return state;
	}

	public void setState(InvoiceStatus status) {
		this.state = status;
	}

	public Set<WorkOrder> getWorkOrders() {
		return new HashSet<>( workOrders );
	}

	Set<WorkOrder> _getWorkOrders() {
		return workOrders;
	}

	public Set<Charge> getCharges() {
		return new HashSet<>( charges );
	}

	Set<Charge> _getCharges() {
		return charges;
	}

	@Override
	public int hashCode() {
		return Objects.hash(amount, date, number, state, vat);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Invoice other = (Invoice) obj;
		return Double.doubleToLongBits(amount) == Double.doubleToLongBits(other.amount)
				&& Objects.equals(date, other.date) && Objects.equals(number, other.number) && state == other.state
				&& Double.doubleToLongBits(vat) == Double.doubleToLongBits(other.vat);
	}

	@Override
	public String toString() {
		return "Invoice [number=" + number + ", date=" + date + ", amount=" + amount + ", vat=" + vat + ", status="
				+ state + "]";
	}

	public boolean isNotSettled() {
		if(this.state.equals(InvoiceStatus.PAID)) { 
			return false;
		}
		return true;
	}
	
	

}
