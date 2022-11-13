package uo.ri.cws.domain;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import uo.ri.cws.domain.base.BaseEntity;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.math.Round;

@Entity
@Table(name = "TWORKORDERS", uniqueConstraints = { @UniqueConstraint(columnNames = {"DATE","VEHICLE_ID"})}) 
public class WorkOrder extends BaseEntity{
	public enum WorkOrderStatus {
		OPEN,
		ASSIGNED,
		FINISHED,
		INVOICED
	}

	// natural attributes
	private LocalDateTime date;
	private String description;
	private double amount = 0.0;
	
	@Enumerated(EnumType.STRING)
	private WorkOrderStatus state = WorkOrderStatus.OPEN;

	// accidental attributes
	@ManyToOne
	private Vehicle vehicle;
	@ManyToOne
	private Mechanic mechanic;
	@ManyToOne
	private Invoice invoice;
	@OneToMany(mappedBy = "workOrder")
	private Set<Intervention> interventions = new HashSet<>();

	WorkOrder(){}
	
	public WorkOrder( Vehicle vehicle ,LocalDateTime date, String description, double amount, WorkOrderStatus status) {
		checkArguments(vehicle, date, description);
		this.date = date.truncatedTo(ChronoUnit.MILLIS);
		this.description = description;
		this.amount = amount;
		this.state = status;
		Associations.Fix.link(vehicle, this); 
	}
	
	public WorkOrder(Vehicle vehicle, LocalDateTime date, String description) {
		this(vehicle, date ,description, 0.0, WorkOrderStatus.OPEN);
	}

	private void checkArguments(Vehicle vehicle, LocalDateTime date, String description) {
		ArgumentChecks.isNotNull(vehicle);
		ArgumentChecks.isNotNull(date);
		ArgumentChecks.isNotBlank(description);
		
	}

	public WorkOrder(Vehicle vehicle, String description) {
		this(vehicle, LocalDateTime.now(), description);
	}
	
	public WorkOrder(Vehicle vehicle, LocalDateTime fecha) {
		this(vehicle, fecha, "no description");
	}
	
	public WorkOrder(Vehicle vehicle) {
		this(vehicle, LocalDateTime.now(), "no description");
	}

	/**
	 * Changes it to INVOICED state given the right conditions
	 * This method is called from Invoice.addWorkOrder(...)
	 * @see UML_State diagrams on the problem statement document
	 * @throws IllegalStateException if
	 * 	- The work order is not FINISHED, or
	 *  - The work order is not linked with the invoice
	 */
	public void markAsInvoiced() {
		if(!state.equals(WorkOrderStatus.FINISHED) || this.invoice == null) {
			throw new IllegalStateException("The Work Order is not finished or it has not an invoice linked");
		}
		this.state = WorkOrderStatus.INVOICED;
	}

	/**
	 * Changes it to FINISHED state given the right conditions and
	 * computes the amount
	 *
	 * @see UML_State diagrams on the problem statement document
	 * @throws IllegalStateException if
	 * 	- The work order is not in ASSIGNED state, or
	 *  - The work order is not linked with a mechanic
	 */
	public void markAsFinished() {
		if(!state.equals(WorkOrderStatus.ASSIGNED) || this.mechanic == null) {
			throw new IllegalStateException("The Work Order is not assigned or it has not a mechanic linked");
		}
		this.state = WorkOrderStatus.FINISHED;
		double interventionAmount = 0.0;
		for(Intervention i : interventions) {
			interventionAmount += i.getAmount();
		}
		amount = Round.twoCents(interventionAmount);
	}

	/**
	 * Changes it back to FINISHED state given the right conditions
	 * This method is called from Invoice.removeWorkOrder(...)
	 * @see UML_State diagrams on the problem statement document
	 * @throws IllegalStateException if
	 * 	- The work order is not INVOICED, or
	 *  - The work order is still linked with the invoice
	 */
	public void markBackToFinished() {
		if(!state.equals(WorkOrderStatus.INVOICED) || this.invoice != null) {
			throw new IllegalStateException("The Work Order is not invoiced or it has an invoice linked");
		}
		this.state = WorkOrderStatus.FINISHED;
	}

	/**
	 * Links (assigns) the work order to a mechanic and then changes its status
	 * to ASSIGNED
	 * @see UML_State diagrams on the problem statement document
	 * @throws IllegalStateException if
	 * 	- The work order is not in OPEN status, or
	 *  - The work order is already linked with another mechanic
	 */
	public void assignTo(Mechanic mechanic) {
		if(!state.equals(WorkOrderStatus.OPEN) || this.mechanic != null) {
			throw new IllegalStateException("The Work Order is not open or it has an mechanic linked");
		}
		Associations.Assign.link(mechanic, this);
		this.state = WorkOrderStatus.ASSIGNED;
	}

	/**
	 * Unlinks (deassigns) the work order and the mechanic and then changes
	 * its status back to OPEN
	 * @see UML_State diagrams on the problem statement document
	 * @throws IllegalStateException if
	 * 	- The work order is not in ASSIGNED status
	 */
	public void desassign() {
		if(!state.equals(WorkOrderStatus.ASSIGNED)) {
			throw new IllegalStateException("The Work Order is not assigned yet");
		}
		Associations.Assign.unlink(mechanic, this);
		this.state = WorkOrderStatus.OPEN;
	}

	/**
	 * In order to assign a work order to another mechanic is first have to
	 * be moved back to OPEN state and unlinked from the previous mechanic.
	 * @see UML_State diagrams on the problem statement document
	 * @throws IllegalStateException if
	 * 	- The work order is not in FINISHED status
	 */
	public void reopen() {
		if(!state.equals(WorkOrderStatus.FINISHED)) {
			throw new IllegalStateException("The Work Order is not finished yet");
		}
		Associations.Assign.unlink(mechanic, this);
		this.state = WorkOrderStatus.OPEN;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getAmount() {
		return amount;
	} 

	public WorkOrderStatus getState() {
		return state;
	} 

	public Vehicle getVehicle() {
		return vehicle;
	} 

	public Set<Intervention> getInterventions() {
		return new HashSet<>( interventions );
	}

	Set<Intervention> _getInterventions() {
		return interventions;
	}

	void _setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}

	void _setMechanic(Mechanic mechanic) {
		this.mechanic = mechanic;
	}

	void _setInvoice(Invoice invoice) {
		this.invoice = invoice;
	}

	public Invoice getInvoice() {
		return invoice;
	}
	
	public Mechanic getMechanic() { 
		return mechanic;
	} 

	@Override
	public String toString() {
		return "WorkOrder [date=" + date + ", description=" + description + ", amount=" + amount + ", status=" + state
				+ ", vehicle=" + vehicle + ", mechanic=" + mechanic + ", invoice=" + invoice + ", interventions="
				+ interventions + "]";
	}

	public boolean isInvoiced() {
		if(state.equals(WorkOrderStatus.INVOICED)) {
			return true;
		}
		return false;
	}

	public boolean isFinished() {
		if(state.equals(WorkOrderStatus.FINISHED)) {
			return true;
		}
		return false;
	}
	

}
