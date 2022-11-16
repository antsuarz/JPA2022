package uo.ri.cws.domain;

import java.time.LocalDate;
import java.time.Month;
import java.time.Period;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import uo.ri.cws.domain.base.BaseEntity;
import uo.ri.util.assertion.ArgumentChecks;

@Entity(name= "TPAYROLL")
public class Payroll extends BaseEntity{

	@ManyToOne
	private Contract contract;
	
	private LocalDate date;	
	private double monthlyWage; 
	private double bonus;
	private double productivityBonus;
	private double trienniumPayment;
	private double incomeTax;
	private double nic;
	
	Payroll() {}
	

	public Payroll(Contract contract) {
		this(contract, LocalDate.now());
	}

	public Payroll(Contract contract, LocalDate date, double anualWage, double extra, double productivity,
			double trienniums, double tax, double nic) {
		Associations.Run.link(this, contract);
		this.date = date;
		this.monthlyWage = anualWage;
		this.bonus = extra;
		this.productivityBonus = productivity;
		this.trienniumPayment = trienniums;
		this.incomeTax = tax;
		this.nic = nic;
	}
	public Payroll(Contract contract, LocalDate date) { 
		checkArguments(contract, date);
		Associations.Run.link(this, contract);
		this.date = date;
		calculateWageAndBonus(date, contract.getAnnualBaseWage()/14);
		this.productivityBonus = calculateProductivityBonus();
		this.trienniumPayment = calcaulateTriennium(contract);
		this.nic =  Math.floor((( contract.getAnnualBaseWage() * 0.05) / 12) * 100) / 100;
		calculateTaxes();
	}


	private double calcaulateTriennium(Contract contract) {
		return (Period.between(contract.getStartDate(), contract.getEndDate().get()).getYears() / 3) 
				* contract.getProfessionalGroup().getTrienniumPayment();
	}
	
	
	private double calculateProductivityBonus() {
		double percentage = contract.getProfessionalGroup().getProductivityBonusPercentage()/ 100;
		double amounts = 0.0;
		
		for(Intervention i: contract.getMechanic().get().getInterventions()) {
			if(i.getWorkOrder().getDate().getMonth().equals(date.getMonth())){
				if(i.getWorkOrder().isInvoiced()) {
					amounts += i.getWorkOrder().getAmount();
				}
			}
		}
		return amounts * percentage;
	}


	private void calculateWageAndBonus(LocalDate d, double wage) { 
		if (d.getMonth() == Month.JUNE || d.getMonth() == Month.DECEMBER) { 
			this.monthlyWage = wage;
			this.bonus =  wage;
		}
		else {
			this.monthlyWage = wage;
			this.bonus = 0.0;
		}
	}

	public double getBruteSalary() {
		 return this.monthlyWage + bonus + productivityBonus + trienniumPayment;
	}
	
	private void checkArguments(Contract contract2, LocalDate date2) {
		ArgumentChecks.isNotNull(date2);
		ArgumentChecks.isNotNull(contract2);
		
	}

	public Contract getContract() { 
		return contract;
	}

	public void setContract(Contract contract) {
		this.contract = contract;
	}
	
	public LocalDate getDate() {
		return date;
	}

	public double getMonthlyWage() { 
		return monthlyWage;
	}

	public double getBonus() {
		return bonus;
	}

	public double getProductivityBonus() {
		return productivityBonus;
	}

	public double getTrienniumPayment() {
		return this.trienniumPayment;
	}

	public double getNIC() { 
		return nic;
	}

	public double getIncomeTax() {
		return incomeTax;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public void setMonthlyWage(double monthlyWage) {
		this.monthlyWage = monthlyWage;
	}

	public void setProductivityBonus(double productivityBonus) {
		this.productivityBonus = productivityBonus;
	}

	
	private void calculateTaxes() {
		if(contract.getAnnualBaseWage() <= 12450) {
			this.incomeTax = getBruteSalary() * 0.19;
		}
		else if(contract.getAnnualBaseWage() > 12450 && contract.getAnnualBaseWage() <= 20200) {
			this.incomeTax = getBruteSalary() * 0.24;
		}
		else if(contract.getAnnualBaseWage() > 20200 && contract.getAnnualBaseWage() <= 35200) {
			this.incomeTax =  getBruteSalary() * 0.30;
		}
		else if(contract.getAnnualBaseWage() > 35200 && contract.getAnnualBaseWage() <= 60000) {
			this.incomeTax = getBruteSalary() * 0.37;
		}
		else if(contract.getAnnualBaseWage() > 60000 && contract.getAnnualBaseWage() <= 300000) {
			this.incomeTax = getBruteSalary() * 0.45;
		}
		else if(contract.getAnnualBaseWage() < 300000) {
			this.incomeTax = getBruteSalary() * 0.47;
		}
	}
	

}
