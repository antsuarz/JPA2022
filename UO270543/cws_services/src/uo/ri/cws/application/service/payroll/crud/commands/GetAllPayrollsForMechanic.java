package uo.ri.cws.application.service.payroll.crud.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.MechanicRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.payroll.PayrollService.PayrollSummaryBLDto;
import uo.ri.cws.application.util.DtoAssembler;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Contract;
import uo.ri.cws.domain.Mechanic;
import uo.ri.cws.domain.Payroll;
import uo.ri.util.assertion.ArgumentChecks;

public class GetAllPayrollsForMechanic implements Command<List<PayrollSummaryBLDto>> {

	private String id;
	public GetAllPayrollsForMechanic(String id) {
		ArgumentChecks.isNotBlank(id);
		this.id = id;
	}

	@Override
	public List<PayrollSummaryBLDto> execute() throws BusinessException { 
		MechanicRepository mr = Factory.repository.forMechanic();
		Optional<Mechanic> om = mr.findById(id);
		if(om.isEmpty()) {
			throw new BusinessException("The mechanic does not exist");
		}
		ArrayList<PayrollSummaryBLDto> result = new ArrayList<>();
		if(om.get().isInForce()) {
			for(Payroll p : om.get().getContractInForce().get().getPayrolls()) {
				result.add(DtoAssembler.toPayrollSummaryBLDto(p));
			}
		}
		for(Contract c : om.get().getTerminatedContracts()) {
			for(Payroll p: c.getPayrolls())
				result.add(DtoAssembler.toPayrollSummaryBLDto(p));
		}
		return result;
	}

}
