package uo.ri.cws.application.service.payroll.crud.commands;

import java.util.Optional;

import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.PayrollRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.payroll.PayrollService.PayrollBLDto;
import uo.ri.cws.application.util.DtoAssembler;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Payroll;
import uo.ri.util.assertion.ArgumentChecks;

public class GetPayrollDetails implements Command<Optional<PayrollBLDto>> {

	private String id;

	public GetPayrollDetails(String id) {
		ArgumentChecks.isNotBlank(id);
		this.id = id;
	}

	@Override
	public Optional<PayrollBLDto> execute() throws BusinessException {
		PayrollRepository pr = Factory.repository.forPayroll();
		Optional<Payroll> op = pr.findById(id);
		if(op.isEmpty()) {
			return Optional.empty();
		} 
		return Optional.of(DtoAssembler.toPayrollBLDto(op.get()));
	}

}
