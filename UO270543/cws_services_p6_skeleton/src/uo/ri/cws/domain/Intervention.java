package uo.ri.cws.domain;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.Set;

import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.math.Round;

public class Intervention {
	// natural attributes
	private LocalDateTime date;
	private int minutes;

	// accidental attributes
	private WorkOrder workOrder;
	private Mechanic mechanic;
	private Set<Substitution> substitutions = new HashSet<>();

	
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
