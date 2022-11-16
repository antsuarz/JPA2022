package uo.ri.cws.application.service.mechanic.crud.command;

import java.util.Optional;

import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.MechanicRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;
import uo.ri.cws.application.util.BusinessChecks;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Mechanic;
import uo.ri.util.assertion.ArgumentChecks;

public class UpdateMechanic implements Command<Void>{

	private MechanicDto dto;

	
	public UpdateMechanic(MechanicDto dto) {
		checkArguments(dto);
		this.dto = dto;
	}

	private void checkArguments(MechanicDto dto) {
		ArgumentChecks.isNotNull(dto);
		ArgumentChecks.isNotBlank(dto.id);
		ArgumentChecks.isNotBlank(dto.dni);
		ArgumentChecks.isNotBlank(dto.name);
		ArgumentChecks.isNotBlank(dto.surname);
	}
	
	public Void execute() throws BusinessException {
		
		MechanicRepository mr = Factory.repository.forMechanic();
		
		Optional<Mechanic> om = mr.findById(dto.id);
		BusinessChecks.exists(om);
		Mechanic m = om.get(); 
		
		BusinessChecks.hasVersion(m, dto.version);
		m.setName(dto.name);
		m.setSurname(dto.surname);
		
		return null;
	}

}
