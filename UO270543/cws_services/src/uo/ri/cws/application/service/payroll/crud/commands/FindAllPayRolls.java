package uo.ri.cws.application.service.payroll.crud.commands;

import java.util.ArrayList;
import java.util.List;

import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.PayrollRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.payroll.PayrollService.PayrollSummaryBLDto;
import uo.ri.cws.application.util.DtoAssembler;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Payroll;

public class FindAllPayRolls implements Command<List<PayrollSummaryBLDto>> {

	@Override
	public List<PayrollSummaryBLDto> execute() throws BusinessException {
		PayrollRepository pr = Factory.repository.forPayroll();
		ArrayList<PayrollSummaryBLDto> result = new ArrayList<>();
		for(Payroll p : pr.findAll()) {
			result.add(DtoAssembler.toPayrollSummaryBLDto(p));
		}
		return result;
	}

}
