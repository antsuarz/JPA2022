package uo.ri.cws.application.service.contracttype.crud.command;

import java.util.List;

import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.ContractTypeRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.contracttype.ContractTypeService.ContractTypeDto;
import uo.ri.cws.application.util.DtoAssembler;
import uo.ri.cws.application.util.command.Command;

public class FindAllContractTypes implements Command<List<ContractTypeDto>> {

	@Override
	public List<ContractTypeDto> execute() throws BusinessException {
		ContractTypeRepository ctr = Factory.repository.forContractType();
		return DtoAssembler.toContractTypeDtoList(ctr.findAll());
	}

}
