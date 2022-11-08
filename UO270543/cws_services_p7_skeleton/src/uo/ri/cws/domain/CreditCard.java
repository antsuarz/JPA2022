package uo.ri.cws.domain;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import uo.ri.util.assertion.ArgumentChecks;

@Entity
@Table(name = "TCreditcards")
public class CreditCard extends PaymentMean {
	
	@Column(unique = true)
	private String number;
	private String type;
	private LocalDate validThru;
	
	CreditCard(){}
	
	
	public CreditCard(double accumulated, Client client, String number, String type, LocalDate validThru) {
		super(accumulated, client); 
		checkArguments(number, type, validThru);
		this.number = number;
		this.type = type;
		this.validThru = validThru;
	}

	public CreditCard(String number, String type, LocalDate validThru) {
		this(0.0, new Client("123","n","a"),number, type, validThru);
	}

	public CreditCard(String number) {
		this(0.0, new Client("123","n","a"),number, "UNKNOWN", LocalDate.now().plusDays(1));
	}

	private void checkArguments(String number, String type, LocalDate validThru) {
		ArgumentChecks.isNotBlank(number);
		ArgumentChecks.isNotBlank(type);
		ArgumentChecks.isNotNull(number);
		ArgumentChecks.isNotNull(type);
		ArgumentChecks.isNotNull(validThru);
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public LocalDate getValidThru() {
		return validThru;
	}

	public void setValidThru(LocalDate validThru) {
		this.validThru = validThru;
	}

	public boolean isValidNow() {
		return this.validThru.isAfter(LocalDate.now());
	}
	
	@Override
	public void pay(double importe) {
		if(!isValidNow()) { 
			throw new IllegalStateException("The credit card is expired");
		}
		super.pay(importe);
	}
	

}
