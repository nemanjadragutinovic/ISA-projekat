package show.isaBack.service.pharmacyService;

import java.util.ArrayList;
import java.util.List;

import java.util.Optional;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import show.isaBack.DTO.drugDTO.DrugDTO;
import show.isaBack.DTO.pharmacyDTO.PharmacyDTO;
import show.isaBack.DTO.pharmacyDTO.PharmacySearchDTO;
import show.isaBack.DTO.userDTO.AuthorityDTO;
import show.isaBack.model.Drug;
import show.isaBack.model.Pharmacy;
import show.isaBack.repository.pharmacyRepository.PharmacyRepository;
import show.isaBack.serviceInterfaces.IPharmacyService;
import show.isaBack.unspecifiedDTO.UnspecifiedDTO;

@Service
public class PharmacyService implements IPharmacyService{

	
	@Autowired
	private PharmacyRepository pharmacyRepository;
	
	
	@Override
	public List<UnspecifiedDTO<PharmacyDTO>> getAllPharmacies() {
		 	
		List<Pharmacy> pharmacies = pharmacyRepository.findAll();
		
		List<UnspecifiedDTO<PharmacyDTO>> pharmacyDTOList = new ArrayList<UnspecifiedDTO<PharmacyDTO>>();
				
		for (Pharmacy currentPharmacy : pharmacies) 
		{
			PharmacyDTO pharmacyDTO= new PharmacyDTO(currentPharmacy);	
			pharmacyDTOList.add(new UnspecifiedDTO<PharmacyDTO>(currentPharmacy.getId(),pharmacyDTO));
		}
		
		return pharmacyDTOList;
	}
	
	
	
	@Override
	public List<UnspecifiedDTO<PharmacyDTO>> getSearchedPharmacies(PharmacySearchDTO searchPharmacyRequest) {
		 				
		List<Pharmacy> pharmacies =new ArrayList<Pharmacy>();
		List<UnspecifiedDTO<PharmacyDTO>> pharmacyDTOList = new ArrayList<UnspecifiedDTO<PharmacyDTO>>();
				
		if(!SearchRequestIsEmpty(searchPharmacyRequest)) {
			pharmacies =pharmacyRepository.getAllSearchedPharmacies(searchPharmacyRequest.getName().toLowerCase(),
					searchPharmacyRequest.getStreet().toLowerCase(),searchPharmacyRequest.getCity().toLowerCase(),
					searchPharmacyRequest.getCountry().toLowerCase());
		}else {
			pharmacies = pharmacyRepository.findAll();
		}
		
		for (Pharmacy currentPharmacy : pharmacies) 
		{
				PharmacyDTO pharmacyDTO= new PharmacyDTO(currentPharmacy);				
				pharmacyDTOList.add(new UnspecifiedDTO<PharmacyDTO>(currentPharmacy.getId(),pharmacyDTO));
		}
		
		
		
		return pharmacyDTOList;
	}
	
	private boolean SearchRequestIsEmpty(PharmacySearchDTO searchRequest) {
		
		if(searchRequest.getName().equals("") && searchRequest.getStreet().equals("")
		  && searchRequest.getCity().equals("") &&  searchRequest.getCountry().equals("") ) {
			     return true;
		}
		return false;
		
	}
	
	public UUID createPharmacy(PharmacyDTO pharmacyDTO) {
		
		Pharmacy pharmacy = createPharmacyFromDTO(pharmacyDTO);
		
		pharmacyRepository.save(pharmacy);
		
		return pharmacy.getId();
	}
	
	private Pharmacy createPharmacyFromDTO(PharmacyDTO pharmacyDTO) {
		
		return new Pharmacy(pharmacyDTO.getName(),pharmacyDTO.getAddress().getCity(),pharmacyDTO.getAddress().getStreet(),pharmacyDTO.getAddress().getCountry(),pharmacyDTO.getAddress().getPostCode(),pharmacyDTO.getDescription());
	}



	@Override
	public List<UnspecifiedDTO<AuthorityDTO>> findAll() {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public UnspecifiedDTO<PharmacyDTO> findById(UUID id) {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public UUID create(PharmacyDTO entityDTO) {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public void update(PharmacyDTO entityDTO, UUID id) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public boolean delete(UUID id) {
		// TODO Auto-generated method stub
		return false;
	}
	
	
	
}
