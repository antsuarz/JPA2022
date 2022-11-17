package uo.ri.cws.application.service.payroll.crud.commands;

import java.time.LocalDate;

import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.PayrollRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Payroll;

public class DeleteLastPayrolls implements Command<Void> {

	@Override
	public Void execute() throws BusinessException {
		PayrollRepository pr = Factory.repository.forPayroll();
		for(Payroll p: pr.findAll()) {
			if(p.getDate().getMonth().equals(LocalDate.now().getMonth()) && 
					p.getDate().getYear() == LocalDate.now().getYear()) {
				pr.remove(p);
			}
		}
		return null;
	}

}
