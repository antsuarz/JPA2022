package uo.ri.cws.domain;

public class Cash extends PaymentMean {

	public Cash(double accumulated, Client client) {
		super(accumulated, client);
	}
	
	public Cash(Client c) {
		this(0.0, c);
	}

	 


}
