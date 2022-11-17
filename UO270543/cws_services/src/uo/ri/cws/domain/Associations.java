package uo.ri.cws.domain;

import java.util.Optional;

public class Associations {

	public static class Own {

		public static void link(Client client, Vehicle vehicle) {
			vehicle.setClient(client);
			client._getVehicles().add(vehicle);
		}

		public static void unlink(Client client, Vehicle vehicle) {
			client._getVehicles().remove(vehicle);
			vehicle.setClient(null);
		}

	}

	public static class Classify {

		public static void link(VehicleType vehicleType, Vehicle vehicle) {
			vehicle.setVehicleType(vehicleType);
			vehicleType._getVehicles().add(vehicle);
		}

		public static void unlink(VehicleType vehicleType, Vehicle vehicle) {
			vehicleType._getVehicles().remove(vehicle);
			vehicle.setVehicleType(null);
		}

	}

	public static class Pay {

		public static void link(Client client, PaymentMean pm) {
			pm._setClient(client);
			client._getPaymentMeans().add(pm);
		}

		public static void unlink(Client client, PaymentMean pm) {
			client._getPaymentMeans().remove(pm);
			pm._setClient(null);
		}

	}

	public static class Fix {

		public static void link(Vehicle vehicle, WorkOrder workOrder) {
			workOrder._setVehicle(vehicle);
			vehicle._getWorkOrders().add(workOrder);
		}

		public static void unlink(Vehicle vehicle, WorkOrder workOrder) { 
			vehicle._getWorkOrders().remove(workOrder); 
			workOrder._setVehicle(null);
		}

	}

	public static class ToInvoice {

		public static void link(Invoice invoice, WorkOrder workOrder) {
			workOrder._setInvoice(invoice);
			invoice._getWorkOrders().add(workOrder);
		}

		public static void unlink(Invoice invoice, WorkOrder workOrder) { 
			invoice._getWorkOrders().remove(workOrder);
			workOrder._setInvoice(null);
		}
	}

	public static class ToCharge {

		public static void link(PaymentMean pm, Charge charge, Invoice invoice) {
			charge._setPaymentMean(pm);
			charge._setInvoice(invoice);
			pm._getCharges().add(charge);
			invoice._getCharges().add(charge);
		}

		public static void unlink(Charge charge) {  
			charge.getPaymentMean()._getCharges().remove(charge);
			charge.getInvoice()._getCharges().remove(charge);
			charge._setPaymentMean(null);
			charge._setInvoice(null);
		}

	}

	public static class Assign {

		public static void link(Mechanic mechanic, WorkOrder workOrder) {
			workOrder._setMechanic(mechanic);
			mechanic._getAssigned().add(workOrder);
		}

		public static void unlink(Mechanic mechanic, WorkOrder workOrder) {
			mechanic._getAssigned().remove(workOrder);
			workOrder._setMechanic(null);
		}

	}

	public static class Intervene {

		public static void link(WorkOrder workOrder, Intervention intervention,
				Mechanic mechanic) {
			intervention._setMechanic(mechanic);
			intervention._setWorkOrder(workOrder);
			workOrder._getInterventions().add(intervention);
			mechanic._getInterventions().add(intervention);
		}

		public static void unlink(Intervention intervention) {
			intervention.getMechanic()._getInterventions().remove(intervention);
			intervention.getWorkOrder()._getInterventions().remove(intervention);
			intervention._setMechanic(null);
			intervention._setWorkOrder(null);
		}

	}

	public static class Substitute {

		public static void link(SparePart spare, Substitution sustitution,
				Intervention intervention) {
			sustitution._setIntervention(intervention);
			sustitution._setSparePart(spare);
			spare._getSubstitutions().add(sustitution);
			intervention._getSubstitutions().add(sustitution);
		}

		public static void unlink(Substitution sustitution) {
			sustitution.getIntervention()._getSubstitutions().remove(sustitution);
			sustitution.getSparePart()._getSubstitutions().remove(sustitution);
			sustitution._setIntervention(null);
			sustitution._setSparePart(null);
		}

	}

	public static class Fire{

		public static void link(Contract contract) {
			contract.setFiredMechanic(contract.getMechanic().get());
			contract.getMechanic().get()._getTerminatedContracts().add(contract);
			contract.getMechanic().get()._setInForceContract(Optional.empty());
		}
		

		public static void unlink(Contract contract) {
			contract.getMechanic().get()._setInForceContract(Optional.of(contract));
			contract.getMechanic().get()._getTerminatedContracts().remove(contract);
			contract.setFiredMechanic(null);		
		}

	}

	public static class Group{

		public static void link(Contract contract, ProfessionalGroup group) { 
			contract.setProfessionalGroup(group);
			group._getContracts().add(contract);
		}

		public static void unlink(Contract contract, ProfessionalGroup group) {
			group._getContracts().remove(contract);
			contract.setProfessionalGroup(null);
		}

	}
	
	public static class Hire{

		public static void link(Contract contract, Mechanic mechanic) {  
			contract._setMechanic(mechanic);
			mechanic._setInForceContract(Optional.of(contract));
		}

		public static void unlink(Contract contract, Mechanic mechanic) { 
			mechanic._setInForceContract(Optional.empty());  
		}

	}
	
	public static class Run{

		public static void link(Payroll payroll, Contract contract) { 
			payroll.setContract(contract);
			contract._getPayrolls().add(payroll);
		}
 
		public static void unlink(Payroll payroll) {
			payroll.getContract()._getPayrolls().remove(payroll);
			payroll.setContract(null);
			
		}

	}
	
	public static class Type{

		public static void link(Contract contract, ContractType type) { 
			contract.setType(type);
			type._getContracts().add(contract);
		} 

		public static void unlink(Contract contract, ContractType type) {
			type._getContracts().remove(contract);
			contract.setType(null);
		}

	}

}
