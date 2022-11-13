package uo.ri.cws.domain;

import javax.persistence.*;

@Entity
@Table(name = "TCashes")
public class Cash extends PaymentMean {

	Cash(){}
	
	public Cash(double accumulated, Client client) {
		super(accumulated, client);
	}
	
	public Cash(Client c) {
		this(0.0, c);
	}

	 


}
