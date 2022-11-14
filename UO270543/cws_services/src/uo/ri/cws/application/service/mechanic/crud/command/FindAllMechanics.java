package uo.ri.cws.application.service.mechanic.crud.command;

import java.util.List;

import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.MechanicRepository;
import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;
import uo.ri.cws.application.util.DtoAssembler;

public class FindAllMechanics {

	public List<MechanicDto> execute() {
		MechanicRepository mr = Factory.repository.forMechanic();
		
		return DtoAssembler.toMechanicDtoList(mr.findAll());
	}

}
