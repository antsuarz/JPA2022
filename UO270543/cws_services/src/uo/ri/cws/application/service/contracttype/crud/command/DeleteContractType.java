package uo.ri.cws.application.service.contracttype.crud.command;

import java.util.Optional;

import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.ContractTypeRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.util.BusinessChecks;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.ContractType;
import uo.ri.util.assertion.ArgumentChecks;

public class DeleteContractType implements Command<Void> {

	private String name;

	public DeleteContractType(String name) {
		ArgumentChecks.isNotBlank(name);
		this.name = name;
	}

	@Override
	public Void execute() throws BusinessException {
		ContractTypeRepository ctr = Factory.repository.forContractType();
		Optional<ContractType> oc = ctr.findByName(name);
		BusinessChecks.exists(oc);
		checkContractType( oc.get() );
		ctr.remove(oc.get());
		
		return null;
	}

	private void checkContractType(ContractType contractType) throws BusinessException {
		BusinessChecks.isTrue(contractType.getContracts().isEmpty());
	}

}
