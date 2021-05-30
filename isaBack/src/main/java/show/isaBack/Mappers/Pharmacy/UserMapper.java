package show.isaBack.Mappers.Pharmacy;

import show.isaBack.DTO.userDTO.PharmacyAdminDTO;
import show.isaBack.model.PharmacyAdmin;
import show.isaBack.unspecifiedDTO.UnspecifiedDTO;

public class UserMapper {
	
	public static UnspecifiedDTO<PharmacyAdminDTO> MapPharmacyAdminPersistenceToPharmacyAdminIdentifiableDTO(
			PharmacyAdmin pharmacyAdmin) {
		if(pharmacyAdmin == null) throw new IllegalArgumentException();

		return new UnspecifiedDTO<PharmacyAdminDTO>(pharmacyAdmin.getId(), new PharmacyAdminDTO(pharmacyAdmin.getEmail(), pharmacyAdmin.getName(), pharmacyAdmin.getSurname(), pharmacyAdmin.getAddress(),
				pharmacyAdmin.getPhoneNumber(), pharmacyAdmin.isActive(), pharmacyAdmin.getUserAuthorities()));
	}

}
