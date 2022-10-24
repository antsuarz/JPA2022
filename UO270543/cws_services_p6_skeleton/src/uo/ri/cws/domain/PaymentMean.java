package uo.ri.cws.domain;

import java.util.HashSet;
import java.util.Set;

public abstract class PaymentMean {
	// natural attributes
	private double accumulated = 0.0;

	// accidental attributes
	private Client client;
	private Set<Charge> charges = new HashSet<>();


	public PaymentMean(double accumulated, Client client) {
		this.accumulated = accumulated;
		Associations.Pay.link(client, this);  
	}

	public double getAccumulated() {
		return accumulated;
	}

	public void setAccumulated(double accumulated) {
		this.accumulated = accumulated;
	}

	public void pay(double importe) {
		this.accumulated += importe;
	}
	
	public Client getClient() {
		return client;
	}

	void _setClient(Client client) {
		this.client = client;
	}

	public Set<Charge> getCharges() {
		return new HashSet<>( charges );
	}

	Set<Charge> _getCharges() {
		return charges;
	}

}
