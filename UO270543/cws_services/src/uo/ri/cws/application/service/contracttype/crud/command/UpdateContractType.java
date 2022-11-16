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

public class UpdateContractType implements Command<Void> {

	private ContractTypeDto dto;

	public UpdateContractType(ContractTypeDto dto) {
		checkArguments(dto);
		this.dto = dto;
	}

	private void checkArguments(ContractTypeDto dto) {
		ArgumentChecks.isNotNull(dto);
		ArgumentChecks.isNotBlank(dto.name);
		ArgumentChecks.isTrue(dto.compensationDays >= 0);
	}

	@Override
	public Void execute() throws BusinessException {
		ContractTypeRepository ctr = Factory.repository.forContractType();
		Optional<ContractType> ocp = ctr.findByName(dto.name); 
		BusinessChecks.exists(ocp);
		ContractType ct = ocp.get(); 
		ct.setCompensationDays(dto.compensationDays); 
		
		return null;
	}

}
