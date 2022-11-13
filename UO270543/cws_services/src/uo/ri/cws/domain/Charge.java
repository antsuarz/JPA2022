package uo.ri.cws.domain;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import uo.ri.cws.domain.Invoice.InvoiceStatus;
import uo.ri.cws.domain.base.BaseEntity;

@Entity
@Table(name = "TCharges", uniqueConstraints = {@UniqueConstraint(columnNames = {  "PAYMENTMEAN_ID" , "INVOICE_ID" })})
public class Charge extends BaseEntity{
	// natural attributes
	private double amount = 0.0;

	// accidental attributes
	@ManyToOne
	private Invoice invoice;
	@ManyToOne
	private PaymentMean paymentMean;

	
	Charge(){}
	
	public Charge(Invoice invoice, PaymentMean paymentMean, double amount) {
		this.amount = amount;
		paymentMean.pay(amount);
		Associations.ToCharge.link(paymentMean, this, invoice);
	}

	/**
	 * Unlinks this charge and restores the accumulated to the payment mean
	 * @throws IllegalStateException if the invoice is already settled
	 */
	public void rewind() {
		if(invoice.getState().equals(InvoiceStatus.PAID)){
			throw new IllegalStateException();
		}
		paymentMean.pay(- amount); 
		Associations.ToCharge.unlink(this);
	}

	void _setInvoice(Invoice invoice) {
		this.invoice= invoice;
		
	}
	
	public Invoice getInvoice() { 
		return invoice;
	}
	
	void _setPaymentMean(PaymentMean pm) {
		this.paymentMean = pm;
	}
	
	public PaymentMean getPaymentMean() {
		return paymentMean;
	}

	public double getAmount() { 
		return amount;
	}

	@Override
	public String toString() {
		return "Charge [amount=" + amount + ", invoice=" + invoice + ", paymentMean=" + paymentMean + "]";
	}

	

	
	
}
