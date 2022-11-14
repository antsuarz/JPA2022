package uo.ri.cws.domain;

import java.time.LocalDate;

import uo.ri.cws.domain.base.BaseEntity;

public class Contract extends BaseEntity{

	public enum ContractState{IN_FORCE,TERMINATED}

	public Contract(Mechanic mechanic, ContractType type, ProfessionalGroup group, double wage) {
		// TODO Auto-generated constructor stub
	}

	public Contract(Mechanic mechanic, ContractType type, ProfessionalGroup group, LocalDate endDate, double wage) {
		// TODO Auto-generated constructor stub
	}

	
}
