package uo.ri.cws.application.service.payroll.crud.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.ContractRepository;
import uo.ri.cws.application.repository.ProfessionalGroupRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.payroll.PayrollService.PayrollSummaryBLDto;
import uo.ri.cws.application.util.DtoAssembler;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Contract;
import uo.ri.cws.domain.Payroll;
import uo.ri.cws.domain.ProfessionalGroup;
import uo.ri.util.assertion.ArgumentChecks;

public class GetAllPayrollsForProfessionalGroup implements Command<List<PayrollSummaryBLDto>> {

	private String name;

	public GetAllPayrollsForProfessionalGroup(String name) {
		ArgumentChecks.isNotBlank(name);
		this.name = name;
	}

	@Override
	public List<PayrollSummaryBLDto> execute() throws BusinessException {
		ArrayList<PayrollSummaryBLDto> result = new ArrayList<>();
		ContractRepository cr = Factory.repository.forContract();
		ProfessionalGroupRepository pgr = Factory.repository.forProfessionalGroup();
		Optional<ProfessionalGroup> op = pgr.findByName(name);
		if(op.isEmpty()) {
			throw new BusinessException("The professional group does not exist");
		}
		List<Contract> list = cr.findByProfessionalGroupId(op.get().getId()); 
		for(Contract contract : list) {
			for(Payroll p : contract.getPayrolls()) {
				result.add(DtoAssembler.toPayrollSummaryBLDto(p));
			}
		}
		return result;
	}

}
