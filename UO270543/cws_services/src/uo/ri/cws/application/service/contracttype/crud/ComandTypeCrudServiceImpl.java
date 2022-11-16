package uo.ri.cws.application.service.contracttype.crud;

import java.util.List;
import java.util.Optional;

import uo.ri.conf.Factory;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.contracttype.ContractTypeService;
import uo.ri.cws.application.util.command.CommandExecutor;

public class ComandTypeCrudServiceImpl implements ContractTypeService{

	private CommandExecutor executor = Factory.executor.forExecutor();
	
	@Override
	public ContractTypeDto addContractType(ContractTypeDto dto) throws BusinessException {
		
		return executor.execute(new AddContractType(dto));
	}

	@Override
	public void deleteContractType(String name) throws BusinessException {
		executor.execute(new DeleteContractType(name));
		
	}

	@Override
	public void updateContractType(ContractTypeDto dto) throws BusinessException {
		executor.execute(new UpdateContractType(dto));
		
	}

	@Override
	public Optional<ContractTypeDto> findContractTypeByName(String name) throws BusinessException {
		return executor.execute(new FindContractTypeByName(name));
	}

	@Override
	public List<ContractTypeDto> findAllContractTypes() throws BusinessException {
		return executor.execute(new FindAllContractTypes());
	}

}
