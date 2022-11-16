package uo.ri.cws.domain;

import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.TemporalAdjusters;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import uo.ri.cws.domain.base.BaseEntity;
import uo.ri.util.assertion.ArgumentChecks;

@Entity(name = "TCONTRACT")
public class Contract extends BaseEntity{

	public enum ContractState{IN_FORCE,TERMINATED}

	@ManyToOne
	private Mechanic mechanic;
	
	@ManyToOne
	private ProfessionalGroup professionalGroup;
	
	@ManyToOne
	private ContractType contractType;
	
	@OneToMany(mappedBy = "contracts")
	private Set<Payroll> payRolls = new HashSet<>();
	
	private Mechanic firedMechanic;
	private LocalDate startDate;
	private double annualWage;
	private LocalDate endDate;
	private double settlement;
	
	@Enumerated(EnumType.STRING)
	private ContractState state;
	
	Contract(){}
	
	public Contract(Mechanic mechanic, ContractType type, ProfessionalGroup group, double wage) {
		this(mechanic, type, group, LocalDate.now().plusDays(1), wage);
	}

	public Contract(Mechanic mechanic, ContractType type, ProfessionalGroup group, LocalDate endDate, double wage) {
		checkArguments( mechanic,  type, group, endDate,  wage);
		this.startDate = LocalDate.now();
		this.annualWage = wage;
		this.endDate = endDate;
		this.state = ContractState.IN_FORCE;
		Associations.Hire.link(this, mechanic);
		Associations.Group.link(this, group);
		Associations.Type.link(this, type);
	}

	private void checkArguments(Mechanic mechanic2, ContractType type, ProfessionalGroup group, LocalDate endDate2,
			double wage) {
		ArgumentChecks.isNotNull(mechanic2);
		ArgumentChecks.isNotNull(type);
		ArgumentChecks.isNotNull(group);
		ArgumentChecks.isNotNull(endDate2);
	}

	public Optional<Mechanic> getMechanic() { 
		return Optional.of(mechanic);
	}

	public void _setMechanic(Mechanic mechanic) {
		this.mechanic = mechanic;
	}

	public ProfessionalGroup getProfessionalGroup() {
		return professionalGroup;
	}

	public void setProfessionalGroup(ProfessionalGroup professionalGroup) {
		this.professionalGroup = professionalGroup;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public Optional<LocalDate> getEndDate() {
		if(endDate == null) {
			return Optional.empty();
		}
		return Optional.of(endDate);
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public double getSettlement() {
		if(payRolls.size() < 12) {
			this.settlement = 0.0;
			return 0.0;
		}
		double media = calculateBruto() / 365;
		
		double numeroAños = Period.between(startDate, endDate).getYears();
		
		this.settlement = media * getContractType().getCompensationDays() * numeroAños;
		return this.settlement;
	}

	private double calculateBruto() {
        double res = 0.0; 
        for(Payroll p: getPayrolls()) {
            if(LocalDate.now().minusYears(1).compareTo(p.getDate()) < 0
            		&& endDate.compareTo(p.getDate()) > 0){
                    res += p.getBruteSalary();
            }
        }
        return res;
    }
	
	public ContractState getState() {
		return state;
	}

	public void setState(ContractState state) {
		this.state = state;
	}

	public double getAnnualBaseWage() {
		return annualWage;
	}
	
	Set<Payroll> _getPayrolls() {
		return payRolls;
	}

	public Set<Payroll> getPayrolls() {
		return new HashSet<Payroll>(payRolls);
	}

	public void terminate() {
		this.state = ContractState.TERMINATED; 
		Associations.Fire.link(this);
		this.endDate = LocalDate.now().with(TemporalAdjusters.lastDayOfMonth());
	}

	public ContractType getContractType() {
		return contractType;
	}

	public Optional<Mechanic> getFiredMechanic() {
		if(firedMechanic == null) {
			return Optional.empty();
		}
		return Optional.of(firedMechanic);
	}

	public void setFiredMechanic(Mechanic mechanic) { 
		this.firedMechanic = mechanic;
	}

	public void setType(ContractType type) {
		this.contractType = type;
		
	}
	
	

	

	
}
