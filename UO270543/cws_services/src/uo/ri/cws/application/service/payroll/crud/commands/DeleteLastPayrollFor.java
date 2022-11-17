package uo.ri.cws.application.service.payroll.crud.commands;

import java.time.LocalDate;
import java.util.Optional;

import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.MechanicRepository;
import uo.ri.cws.application.repository.PayrollRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.util.BusinessChecks;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Mechanic;
import uo.ri.cws.domain.Payroll;
import uo.ri.util.assertion.ArgumentChecks;

public class DeleteLastPayrollFor implements Command<Void> {

	private String id;

	public DeleteLastPayrollFor(String mechanicId) {
		ArgumentChecks.isNotBlank(mechanicId);
		this.id = mechanicId;
	}

	@Override
	public Void execute() throws BusinessException {
		PayrollRepository pr = Factory.repository.forPayroll();
		MechanicRepository mr = Factory.repository.forMechanic();
		
		Optional<Mechanic> om = mr.findById(id);
		BusinessChecks.exists(om);
		Mechanic m = om.get();
		for(Payroll p : m.getContractInForce().get().getPayrolls()) {
			if(p.getDate().getMonth().equals(LocalDate.now().getMonth()) && 
					p.getDate().getYear() == LocalDate.now().getYear()) {
				pr.remove(p);
			}
		}
		return null;
	}

}
