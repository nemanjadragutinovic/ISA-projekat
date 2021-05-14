package show.isaBack.serviceInterfaces;

import java.util.List;

import show.isaBack.DTO.pharmacyDTO.PharmacyDTO;
import show.isaBack.DTO.pharmacyDTO.PharmacySearchDTO;
import show.isaBack.unspecifiedDTO.UnspecifiedDTO;

public interface IPharmacyService extends IService<PharmacyDTO, UnspecifiedDTO<PharmacyDTO>>{

	public List<UnspecifiedDTO<PharmacyDTO>> getAllPharmacies();
	
	public List<UnspecifiedDTO<PharmacyDTO>> getSearchedPharmacies(PharmacySearchDTO searchPharmacyRequest);
	
}
