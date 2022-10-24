package uo.ri.cws.domain;

import uo.ri.cws.domain.Invoice.InvoiceStatus;

public class Charge {
	// natural attributes
	private double amount = 0.0;

	// accidental attributes
	private Invoice invoice;
	private PaymentMean paymentMean;

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
		if(invoice.getStatus().equals(InvoiceStatus.PAID)){
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
