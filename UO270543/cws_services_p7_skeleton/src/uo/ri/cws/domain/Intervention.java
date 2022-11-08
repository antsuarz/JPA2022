package uo.ri.cws.domain;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import uo.ri.cws.domain.base.BaseEntity;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.math.Round;

@Entity
@Table(name = "TInterventions", uniqueConstraints = { @UniqueConstraint(columnNames = {"DATE","MECHANIC_ID","WORKORDER_ID"})})
public class Intervention extends BaseEntity{
	// natural attributes
	private LocalDateTime date;
	private int minutes;

	// accidental attributes
	@ManyToOne
	private WorkOrder workOrder;
	@ManyToOne
	private Mechanic mechanic;
	@OneToMany(mappedBy = "intervention")
	private Set<Substitution> substitutions = new HashSet<>();

	Intervention(){}
	
	public Intervention(Mechanic mechanic , WorkOrder workOrder, LocalDateTime date, int minutes) {
		cheackArguments(mechanic, workOrder, date, minutes);
		this.date = date.truncatedTo(ChronoUnit.MILLIS);
		this.minutes = minutes;
		Associations.Intervene.link(workOrder, this, mechanic); 
	}

	private void cheackArguments(Mechanic mechanic, WorkOrder workOrder, LocalDateTime date, int minutes) {
		ArgumentChecks.isNotNull(mechanic);
		ArgumentChecks.isNotNull(workOrder);
		ArgumentChecks.isNotNull(date);
		ArgumentChecks.isTrue(minutes >= 0);
	}

	public Intervention(Mechanic mechanic, WorkOrder workOrder, int minutes) {
		this(mechanic,workOrder,LocalDateTime.now(),minutes); 
	}

	void _setWorkOrder(WorkOrder workOrder) {
		this.workOrder = workOrder;
	}

	void _setMechanic(Mechanic mechanic) {
		this.mechanic = mechanic;
	}

	public Set<Substitution> getSubstitutions() {
		return new HashSet<>( substitutions );
	}

	Set<Substitution> _getSubstitutions() {
		return substitutions;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	public int getMinutes() {
		return minutes;
	}

	public void setMinutes(int minutes) {
		this.minutes = minutes;
	}

	public WorkOrder getWorkOrder() {
		return workOrder;
	}

	public void setWorkOrder(WorkOrder workOrder) {
		this.workOrder = workOrder;
	}

	public Mechanic getMechanic() {
		return mechanic;
	}

	public void setMechanic(Mechanic mechanic) {
		this.mechanic = mechanic;
	}

	public double getAmount() {
		double susbtitutionAmount = 0.0;
		for(Substitution s: getSubstitutions()) {
			susbtitutionAmount += s.getAmount();
		} 
		susbtitutionAmount += getMinutes()/60.0 * getWorkOrder().getVehicle().getVehicleType().getPricePerHour();
		return Round.twoCents(susbtitutionAmount);
	}
	
	

}
