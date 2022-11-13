package uo.ri.cws.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import uo.ri.util.assertion.ArgumentChecks;

@Entity
@Table(name = "TVouchers")
public class Voucher extends PaymentMean {
	
	@Column(unique = true)
	private String code;
	private double available = 0.0;
	private String description;


	Voucher(){}
	
	public Voucher(double accumulated, Client client, String code, String description, double available) {
		super(accumulated, client);
		checkArguments(code, description, available);
		this.code = code;
		this.description = description;
		this.available = available;
	}
	private void checkArguments(String code, String description, double available) {
		ArgumentChecks.isNotBlank(code);
		ArgumentChecks.isNotBlank(description);
		ArgumentChecks.isNotNull(code);
		ArgumentChecks.isNotNull(description);
		ArgumentChecks.isTrue(available >= 0.0);
	}
	public Voucher(String code, String descripcion, double available) {
		this(0.0, new Client("123","n","a"),code, descripcion, available);
	}

	public Voucher(String code, double available) {
		this(0.0, new Client("123","n","a"),code, "no-description", available); 
	}

	/**
	 * Augments the accumulated (super.pay(amount) ) and decrements the available
	 * @throws IllegalStateException if not enough available to pay
	 */
	@Override
	public void pay(double amount) {
		if(amount > getAvailable()) {
			throw new IllegalStateException("The voucher does not have enough money available to pay the amount");
		}
		super.pay(amount);
		setAvailable(getAvailable() - amount);
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public double getAvailable() {
		return available;
	}

	public void setAvailable(double available) {
		this.available = available;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	
}
