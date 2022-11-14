package uo.ri.cws.application.service.mechanic.crud.command;

import java.util.Optional;

import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.MechanicRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;
import uo.ri.cws.application.util.BusinessChecks;
import uo.ri.cws.domain.Mechanic;
import uo.ri.util.assertion.ArgumentChecks;

public class AddMechanic {

	private MechanicDto dto;

	public AddMechanic(MechanicDto dto) {
		checkArgument(dto);
		this.dto = dto;
	}

	private void checkArgument(MechanicDto dto) {
		ArgumentChecks.isNotNull(dto); 
		ArgumentChecks.isTrue(!dto.dni.isBlank());
	}

	public MechanicDto execute() throws BusinessException {
		MechanicRepository mr = Factory.repository.forMechanic();
		Optional<Mechanic> om = mr.findByDni(dto.dni);
		
		BusinessChecks.isTrue(om.isEmpty(), "There is a mechanic with the same dni");
		
		Mechanic m = new Mechanic(dto.dni, dto.surname, dto.name);
		mr.add(m);
		dto.id = m.getId();
		return dto;
	}

}
