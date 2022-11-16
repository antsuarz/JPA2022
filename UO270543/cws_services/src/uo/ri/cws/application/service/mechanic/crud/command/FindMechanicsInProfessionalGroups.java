package uo.ri.cws.application.service.mechanic.crud.command;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.MechanicRepository;
import uo.ri.cws.application.repository.ProfessionalGroupRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;
import uo.ri.cws.application.util.DtoAssembler;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.ProfessionalGroup;
import uo.ri.util.assertion.ArgumentChecks;

public class FindMechanicsInProfessionalGroups implements Command<List<MechanicDto>> {

	
	private String name;

	public FindMechanicsInProfessionalGroups(String name) {
		ArgumentChecks.isNotBlank(name);
		this.name = name;
	}

	@Override
	public List<MechanicDto> execute() throws BusinessException {
		MechanicRepository mr = Factory.repository.forMechanic();
		ProfessionalGroupRepository pgr = Factory.repository.forProfessionalGroup();
		Optional<ProfessionalGroup> op = pgr.findByName(name) ;
		if( op.isEmpty()) {
			return new ArrayList<>();
		}
		return DtoAssembler.toMechanicDtoList(mr.findAllInProfessionalGroup(op.get()));
	}

}
