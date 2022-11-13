package uo.ri.cws.domain;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import uo.ri.cws.domain.base.BaseEntity;
import uo.ri.util.assertion.ArgumentChecks;

@Entity
@Table(name = "TVehicletypes")
public class VehicleType extends BaseEntity{
	
	// natural attributes
	@Column(unique = true)
	private String name;
	private double pricePerHour;

	// accidental attributes
	@OneToMany(mappedBy = "vehicleType")
	private Set<Vehicle> vehicles = new HashSet<>();

	VehicleType(){}
	
	public VehicleType(String name) {
		this(name, 0.0);
	}
	
	public VehicleType(String name, double pricePerHour) {
		checkArguments(name, pricePerHour);
		this.name = name;
		this.pricePerHour = pricePerHour;
	}

	
	private void checkArguments(String name, double pricePerHour) {
		ArgumentChecks.isNotBlank(name);
		ArgumentChecks.isTrue(pricePerHour >= 0.0);
		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPricePerHour() {
		return pricePerHour;
	}

	public void setPricePerHour(double pricePerHour) {
		this.pricePerHour = pricePerHour;
	}

	public Set<Vehicle> getVehicles() {
		return new HashSet<>( vehicles );
	}

	Set<Vehicle> _getVehicles() {
		return vehicles;
	}
	

	@Override
	public String toString() {
		return "VehicleType [name=" + name + ", pricePerHour=" + pricePerHour + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(name, pricePerHour);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		VehicleType other = (VehicleType) obj;
		return Objects.equals(name, other.name)
				&& Double.doubleToLongBits(pricePerHour) == Double.doubleToLongBits(other.pricePerHour);
	}
	
	

}
