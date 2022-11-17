package uo.ri.cws.application.service.payroll.crud.commands;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.ContractRepository;
import uo.ri.cws.application.repository.PayrollRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Contract;
import uo.ri.cws.domain.Contract.ContractState;
import uo.ri.cws.domain.Intervention;
import uo.ri.cws.domain.Payroll;
import uo.ri.util.math.Round;

public class GeneratePayrolls implements Command<Void> {

	LocalDate present;
	public GeneratePayrolls() {
		this.present = LocalDate.now();
	}
	
	public GeneratePayrolls(LocalDate present) {
		if(present == null) {
			this.present = LocalDate.now();
		}
		else {
			this.present = present;
		}
	}

	@Override
	public Void execute() throws BusinessException {  
		PayrollRepository pr = Factory.repository.forPayroll();
		ContractRepository cr = Factory.repository.forContract();
		List<Contract> toPayRollContracts = nominasAGenerar(cr); 
		for (Contract con : toPayRollContracts) {
			List<Payroll> payrolls = pr.findByContract(con.getId());
			boolean existeEnMes = false;
			for (Payroll dto : payrolls) {
				if (dto.getDate().getMonth().equals(present.getMonth()) && dto.getDate().getYear() == present.getYear())
					existeEnMes = true;
			}

			if (!existeEnMes) {
				Payroll newPayroll = new Payroll(con, present);
				newPayroll.setProductivityBonus(calculateBonus(con)); 
				newPayroll.calculateTaxes();
				Factory.repository.forPayroll().add(newPayroll); 
			}
		}
		return null;

	}

	private ArrayList<Contract> nominasAGenerar(ContractRepository cr) {
		ArrayList<Contract> toPayRollContracts = new ArrayList<>();
		for (Contract c : cr.findAll()) {
			if (c.getState().equals(ContractState.TERMINATED)) {
				if (c.getEndDate().isPresent() && (c.getEndDate().get().getMonth().equals(present.getMonth())
						&& c.getEndDate().get().getYear() == present.getYear()))
					toPayRollContracts.add(c);
			} else {
				toPayRollContracts.add(c);
			}	
		}
		return toPayRollContracts;
	}

	private double calculateBonus(Contract contract) {
		double percentage = contract.getProfessionalGroup().getProductivityBonusPercentage()/ 100;
		double amounts = 0.0;
		if(contract.getMechanic().isPresent()) {
			for(Intervention i: contract.getMechanic().get().getInterventions()) {
				if(i.getWorkOrder().getDate().getMonth().equals(present.getMonth())){
					if(i.getWorkOrder().isInvoiced()) {
						amounts += i.getWorkOrder().getAmount();
					}
				}
			}
		}
		if(contract.getFiredMechanic().isPresent()) {
			for(Intervention i: contract.getFiredMechanic().get().getInterventions()) {
				if(i.getWorkOrder().getDate().getMonth().equals(present.getMonth())){
					if(i.getWorkOrder().isInvoiced()) {
						amounts += i.getWorkOrder().getAmount();
					}
				}
			}
		}
		return Round.twoCents(amounts * percentage);
	}
	

}
