package uo.ri.cws.application.service.professionalgroup.crud;

import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.professionalgroup.ProfessionalGroupService;
import uo.ri.util.exception.NotYetImplementedException;

public class ProfessionalGroupCrudServiceImpl implements ProfessionalGroupService{

	@Override
	public ProfessionalGroupBLDto addProfessionalGroup(ProfessionalGroupBLDto dto) throws BusinessException {
		throw new NotYetImplementedException();
	}

	@Override
	public void deleteProfessionalGroup(String name) throws BusinessException {
		throw new NotYetImplementedException();
		
	}

	@Override
	public void updateProfessionalGroup(ProfessionalGroupBLDto dto) throws BusinessException {
		throw new NotYetImplementedException();
		
	}

	@Override
	public Optional<ProfessionalGroupBLDto> findProfessionalGroupByName(String id) throws BusinessException {
		throw new NotYetImplementedException();
	}

	@Override
	public List<ProfessionalGroupBLDto> findAllProfessionalGroups() throws BusinessException {
		throw new NotYetImplementedException();
	}

}
