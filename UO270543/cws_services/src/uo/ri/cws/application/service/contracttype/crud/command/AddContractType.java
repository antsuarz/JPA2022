package uo.ri.cws.application.service.contracttype.crud.command;

import java.util.Optional;

import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.ContractTypeRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.contracttype.ContractTypeService.ContractTypeDto;
import uo.ri.cws.application.util.BusinessChecks;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.ContractType;
import uo.ri.util.assertion.ArgumentChecks;

public class AddContractType implements Command<ContractTypeDto> {

	private ContractTypeDto dto;
	
	public AddContractType(ContractTypeDto dto) {
		ArgumentChecks.isNotNull(dto);
		ArgumentChecks.isNotBlank(dto.name);
		ArgumentChecks.isTrue(dto.compensationDays >= 0);
		this.dto = dto;
	}
	@Override
	public ContractTypeDto execute() throws BusinessException {
		ContractTypeRepository ctr = Factory.repository.forContractType();
		Optional<ContractType> ocp = ctr.findByName(dto.name);
		BusinessChecks.isTrue(ocp.isEmpty(), "There is a contract type with the same name");
		ContractType ct = createContractType();
		ctr.add(ct);
		dto.id = ct.getId();
		return dto;
	}
	private ContractType createContractType() {
		return new ContractType(dto.name, dto.compensationDays);
		
	}

}
