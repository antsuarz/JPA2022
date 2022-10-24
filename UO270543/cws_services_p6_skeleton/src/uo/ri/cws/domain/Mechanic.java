package uo.ri.cws.domain;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import uo.ri.util.assertion.ArgumentChecks;

public class Mechanic {
	// natural attributes
	private String dni;
	private String surname;
	private String name;

	// accidental attributes
	private Set<WorkOrder> assigned = new HashSet<>();
	private Set<Intervention> interventions = new HashSet<>();

	
	public Mechanic(String dni) {
		this(dni,"noName","noSurname");
	}


	public Mechanic(String dni, String name, String surname) {
		checkArguments(dni, name,surname);
		this.dni = dni;
		this.surname = surname;
		this.name = name;
	}


	private void checkArguments(String dni, String name, String surname) {
		ArgumentChecks.isNotBlank(name);
		ArgumentChecks.isNotBlank(surname);
		ArgumentChecks.isNotBlank(dni);
	}


	public Set<WorkOrder> getAssigned() {
		return new HashSet<>( assigned );
	}

	
	public String getDni() {
		return dni;
	}


	public void setDni(String dni) {
		this.dni = dni;
	}


	public String getSurname() {
		return surname;
	}


	public void setSurname(String surname) {
		this.surname = surname;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	Set<WorkOrder> _getAssigned() {
		return assigned;
	}

	public Set<Intervention> getInterventions() {
		return new HashSet<>( interventions );
	}

	Set<Intervention> _getInterventions() {
		return interventions;
	}


	@Override
	public int hashCode() {
		return Objects.hash(dni, name, surname);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Mechanic other = (Mechanic) obj;
		return Objects.equals(dni, other.dni) && Objects.equals(name, other.name)
				&& Objects.equals(surname, other.surname);
	}


	@Override
	public String toString() {
		return "Mechanic [dni=" + dni + ", surname=" + surname + ", name=" + name + ", assigned=" + assigned
				+ ", interventions=" + interventions + "]";
	}

	
}
