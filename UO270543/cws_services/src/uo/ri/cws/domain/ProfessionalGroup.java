package uo.ri.cws.domain;

import java.util.HashSet;
import java.util.Set;

import uo.ri.cws.domain.base.BaseEntity;
import uo.ri.util.assertion.ArgumentChecks;

public class ProfessionalGroup extends BaseEntity{
	
	private HashSet<Contract> contracts = new HashSet<>();
	
	private String name;
	private double productivityRate;
	private double trienniumSalary;

	public ProfessionalGroup(String name, double trienniumSalary, double productivityRate) {
		checkArguments(name, productivityRate, trienniumSalary);
		this.name = name;
		this.productivityRate = productivityRate;
		this.trienniumSalary = trienniumSalary;
	}

	private void checkArguments(String name2, double productivityRate2, double trienniumSalary2) {
		ArgumentChecks.isNotBlank(name2);
		ArgumentChecks.isTrue(productivityRate2 >= 0);
		ArgumentChecks.isTrue(trienniumSalary2 >= 0);
		
	}

	public String getName() {
		return name; 
	}

	public double getProductivityBonusPercentage() { 
		return productivityRate;
	}

	public double getTrienniumPayment() {
		return trienniumSalary;
	}
	
	public double getProductivityRate() {
		return productivityRate;
	}

	public Set<Contract> getContracts() { 
		return new HashSet<Contract>(contracts);
	}
	
	Set<Contract> _getContracts() { 
		return contracts;
	}

}
