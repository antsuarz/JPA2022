package uo.ri.cws.domain;

import java.util.HashSet;
import java.util.Set;

import uo.ri.cws.domain.base.BaseEntity;
import uo.ri.util.assertion.ArgumentChecks;

public class ContractType extends BaseEntity{

	private Set<Contract> contracts = new HashSet<>();
	
	private String name;
	private double compensationDays;
	
	public ContractType(String name, double compensationDays) {
		checkArguments(name, compensationDays);
		this.name = name;
		this.compensationDays = compensationDays;
	}

	private void checkArguments(String name2, double compensationDays2) {
		ArgumentChecks.isNotNull(name2);
		ArgumentChecks.isNotEmpty(name2);
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
