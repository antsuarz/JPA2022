package uo.ri.cws.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import uo.ri.cws.domain.base.BaseEntity;
import uo.ri.util.assertion.ArgumentChecks;

@Entity
@Table(name = "TCONTRACTTYPES")
public class ContractType extends BaseEntity{

	@OneToMany(mappedBy = "contractType")
	private Set<Contract> contracts = new HashSet<>();

	@Column(unique = true)
	private String name;
	
	private double compensationDays;
	
	ContractType(){}
	
	public ContractType(String name, double compensationDays) {
		checkArguments(name, compensationDays);
		this.name = name;
		this.compensationDays = compensationDays;
	}

	private void checkArguments(String name2, double compensationDays2) { 
		ArgumentChecks.isNotBlank(name2);
		ArgumentChecks.isTrue(compensationDays2 >= 0);
		
	}

	public String getName() {
		return name;
	}

	public double getCompensationDays() {
		return compensationDays;
	}

	public Set<Contract> getContracts() { 
		return new HashSet<>(contracts);
	}
	
	Set<Contract> _getContracts(){
		return contracts;
	}

}
