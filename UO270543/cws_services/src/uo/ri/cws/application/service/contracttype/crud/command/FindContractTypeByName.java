package uo.ri.cws.application.service.contracttype.crud.command;

import java.util.Optional;

import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.ContractTypeRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.contracttype.ContractTypeService.ContractTypeDto;
import uo.ri.cws.application.util.DtoAssembler;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.ContractType;
import uo.ri.util.assertion.ArgumentChecks;

public class FindContractTypeByName implements Command<Optional<ContractTypeDto>> {

	private String name;

	public FindContractTypeByName(String name) {
		ArgumentChecks.isNotBlank(name);
		this.name = name;
	}

	@Override
	public Optional<ContractTypeDto> execute() throws BusinessException {
		ContractTypeRepository ctr = Factory.repository.forContractType();
		Optional<ContractType> oct = ctr.findByName(name);
		if(oct.isEmpty()) {
			return Optional.empty();
		}
		return Optional.of(DtoAssembler.toDto(oct.get()));
	}

}
