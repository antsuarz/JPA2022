package uo.ri.cws.infrastructure.persistence.jpa.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.repository.ContractRepository;
import uo.ri.cws.domain.Contract;

public class ContractJpaRepository implements ContractRepository {

	@Override
	public void add(Contract t) {
		// TODO Auto-generated method stub

	}

	@Override
	public void remove(Contract t) {
		// TODO Auto-generated method stub

	}

	@Override
	public Optional<Contract> findById(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Contract> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Contract> findAllInForce() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Contract> findByMechanicId(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Contract> findByProfessionalGroupId(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Contract> findByContractTypeId(String id2Del) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Contract> findAllInForceThisMonth(LocalDate present) {
		// TODO Auto-generated method stub
		return null;
	}

}
