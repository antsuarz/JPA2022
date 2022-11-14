package uo.ri.cws.application.service.mechanic.crud.command;

import java.util.Optional;

import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.MechanicRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.util.BusinessChecks;
import uo.ri.cws.domain.Mechanic;

public class DeleteMechanic {

	private String mechanicId;

	public DeleteMechanic(String mechanicId) {
		this.mechanicId = mechanicId;
	}

	public Void execute() throws BusinessException {

		MechanicRepository mr = Factory.repository.forMechanic();
		Optional<Mechanic> om = mr.findById(mechanicId);
		BusinessChecks.exists(om);
		checkMechanic( om.get() );
		mr.remove(om.get());
		
		return null;
	}

	private void checkMechanic(Mechanic m) throws BusinessException {
		BusinessChecks.isTrue(m.getInterventions().isEmpty(), "The mechanic has interventions");
		BusinessChecks.isTrue(m.getAssigned().isEmpty(), "The mechanic has work orders");
	}

}
