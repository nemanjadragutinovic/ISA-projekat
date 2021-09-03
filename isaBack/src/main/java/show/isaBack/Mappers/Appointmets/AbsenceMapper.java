package show.isaBack.Mappers.Appointmets;

import java.util.ArrayList;
import java.util.List;

import show.isaBack.DTO.userDTO.AbsenceDTO;
import show.isaBack.model.FreeDays;
import show.isaBack.unspecifiedDTO.UnspecifiedDTO;

public class AbsenceMapper {
public static List<UnspecifiedDTO<AbsenceDTO>> MapAbsencePersistenceListToAbsenceIdentifiableDTOList(List<FreeDays> absence){
		
		List<UnspecifiedDTO<AbsenceDTO>> absenceListDTO = new ArrayList<UnspecifiedDTO<AbsenceDTO>>();
		absence.forEach((a) -> absenceListDTO.add(MapAbsencePersistenceToAbsenceIdentifiableDTO(a)));
		return absenceListDTO;
	}

public static UnspecifiedDTO<AbsenceDTO> MapAbsencePersistenceToAbsenceIdentifiableDTO(FreeDays absence){
	if(absence == null) throw new IllegalArgumentException();
	
	return new UnspecifiedDTO<AbsenceDTO>(absence.getId(), new AbsenceDTO(absence.getUser().getId(), absence.getStartDate(), absence.getEndDate(), absence.getStatus(),absence.getRejectReason()));
}
}
