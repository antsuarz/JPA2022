package uo.ri.cws.application.service.mechanic.crud.command;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.ContractTypeRepository;
import uo.ri.cws.application.repository.MechanicRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;
import uo.ri.cws.application.util.DtoAssembler;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.ContractType;
import uo.ri.util.assertion.ArgumentChecks;

public class FindMechanicsWithContractInForceInContractType implements Command<List<MechanicDto>> {

	private String name; 
	
	public FindMechanicsWithContractInForceInContractType(String name) {
		argumentChecks(name);
		this.name = name;
	}

	private void argumentChecks(String name) {
		ArgumentChecks.isNotBlank(name);
	}

	@Override
	public List<MechanicDto> execute() throws BusinessException {
		MechanicRepository mr = Factory.repository.forMechanic();
		ContractTypeRepository ctr = Factory.repository.forContractType();
		
		Optional<ContractType> oct = ctr.findByName(name); 
		if(oct.isEmpty()) { 
			return new ArrayList<MechanicDto>();
		}
		return DtoAssembler.toMechanicDtoList(mr.findInForceInContractType(oct.get()));
	}

}
