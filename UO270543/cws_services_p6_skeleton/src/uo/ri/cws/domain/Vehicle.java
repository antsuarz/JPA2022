package uo.ri.cws.domain;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import uo.ri.util.assertion.ArgumentChecks;

public class Vehicle {
	private String plateNumber;
	private String make;
	private String model;
	
	// atributos accidentales
	
	private Client client;
	private VehicleType vehicleType;
	
	private Set<WorkOrder> workOrders = new HashSet<>();
	
	
	public Vehicle(String plateNumber) {
		this(plateNumber,"noMake","noModel");
	}
	
	public Vehicle(String plateNumber, String make, String model) {
		checkArguments(plateNumber, make, model);
		this.plateNumber = plateNumber;
		this.make = make;
		this.model = model;
	}
	
	Set<WorkOrder> _getWorkOrders() {
		return workOrders;
	}
	
	public Set<WorkOrder> getWorkOrders() {
		return new HashSet<WorkOrder>(workOrders);
	}

	public Client getClient() {
		return client;
	}
	
	void setClient(Client owner) {
		this.client = owner;
	}
	
	public VehicleType getVehicleType() {
		return vehicleType;
	}

	void setVehicleType(VehicleType vehicleType) {
		this.vehicleType = vehicleType;
	}

	
	private void checkArguments(String plateNumber, String make, String model) {
		ArgumentChecks.isNotBlank(plateNumber);
		ArgumentChecks.isNotBlank(make);
		ArgumentChecks.isNotBlank(model);
	}
	
	public String getPlateNumber() {
		return plateNumber;
	}
	
	public void setPlateNumber(String plateNumber) {
		this.plateNumber = plateNumber;
	}
	
	public String getMake() {
		return make;
	}
	
	public void setMake(String make) {
		this.make = make;
	}
	
	public String getModel() {
		return model;
	}
	
	public void setModel(String model) {
		this.model = model;
	}
	
	@Override
	public String toString() {
		return "Vehicle [plateNumber=" + plateNumber + ", make=" + make + ", model=" + model + "]";
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(make, model, plateNumber);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Vehicle other = (Vehicle) obj;
		return Objects.equals(make, other.make) && Objects.equals(model, other.model)
				&& Objects.equals(plateNumber, other.plateNumber);
	}
	
	

}
