package uo.ri.cws.infrastructure.persistence.jpa.repository;

import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.repository.PayrollRepository;
import uo.ri.cws.domain.Payroll;
import uo.ri.cws.infrastructure.persistence.jpa.util.BaseJpaRepository;
import uo.ri.cws.infrastructure.persistence.jpa.util.Jpa;
import uo.ri.util.exception.NotYetImplementedException;

public class PayrollJpaRepository
	extends BaseJpaRepository<Payroll> 
		implements PayrollRepository {

	@Override
	public List<Payroll> findByContract(String contractId) {
		return Jpa.getManager().createNamedQuery("Payroll.findByContract", Payroll.class)
				.setParameter(1, contractId).getResultList();
	}

	@Override
	public List<Payroll> findCurrentMonthPayrolls() {
		throw new NotYetImplementedException();
	}

	@Override
	public Optional<Payroll> findCurrentMonthByContractId(String contractId) {
		throw new NotYetImplementedException();
	}

}
