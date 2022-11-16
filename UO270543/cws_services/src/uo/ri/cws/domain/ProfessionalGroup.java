package uo.ri.cws.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import uo.ri.cws.domain.base.BaseEntity;
import uo.ri.util.assertion.ArgumentChecks;

@Entity(name = "TPROFESSIONALGROUPS")
public class ProfessionalGroup extends BaseEntity{
	
	@OneToMany(mappedBy = "professionalGroup")
	private Set<Contract> contracts = new HashSet<>();
	
	@Column(unique = true)
	private String name;
	 
	private double productivityBonusPercentage;
	 
	private double trienniumPayment;

	ProfessionalGroup() {}
	
	public ProfessionalGroup(String name, double trienniumSalary, double productivityRate) {
		checkArguments(name, productivityRate, trienniumSalary);
		this.name = name;
		this.productivityBonusPercentage = productivityRate;
		this.trienniumPayment = trienniumSalary;
	}

	private void checkArguments(String name, double productivityRate, double trienniumSalary) {
		ArgumentChecks.isNotBlank(name);
		ArgumentChecks.isTrue(productivityRate >= 0);
		ArgumentChecks.isTrue(trienniumSalary >= 0);
		
	}

	public String getName() {
		return name; 
	}

	public double getProductivityBonusPercentage() { 
		return productivityBonusPercentage;
	}

	public double getTrienniumPayment() {
		return trienniumPayment;
	}

	public Set<Contract> getContracts() { 
		return new HashSet<Contract>(contracts);
	}
	
	Set<Contract> _getContracts() { 
		return contracts;
	}

}
