package uo.ri.cws.application.service.mechanic.crud.command;

import java.util.Optional;

import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.MechanicRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;
import uo.ri.cws.application.util.DtoAssembler;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Mechanic;
import uo.ri.util.assertion.ArgumentChecks;

public class FindMechanicById implements Command<Optional<MechanicDto>>{

	private String id;

	public FindMechanicById(String id) {
		checkArgument(id);
		this.id = id;
	}

	private void checkArgument(String id) {
		ArgumentChecks.isNotBlank(id); 
	}

	public Optional<MechanicDto> execute() throws BusinessException {
		MechanicRepository mr = Factory.repository.forMechanic();
		Optional<Mechanic> om = mr.findById(id); 
		if(om.isEmpty()) {
			return Optional.empty();
		}
		return Optional.of(DtoAssembler.toDto(om.get()));
	}

}
