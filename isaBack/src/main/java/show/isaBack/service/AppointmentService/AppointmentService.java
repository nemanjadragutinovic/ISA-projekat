package show.isaBack.service.AppointmentService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import show.isaBack.DTO.AppointmentDTO.DermatologistAppointmentDTO;
import show.isaBack.DTO.userDTO.AuthorityDTO;
import show.isaBack.DTO.userDTO.EmployeeGradeDTO;
import show.isaBack.Mappers.Appointmets.AppointmentsMapper;
import show.isaBack.model.Dermatologist;
import show.isaBack.model.appointment.Appointment;
import show.isaBack.model.appointment.AppointmentType;
import show.isaBack.repository.AppointmentRepository.AppointmentRepository;
import show.isaBack.repository.userRepository.DermatologistRepository;
import show.isaBack.serviceInterfaces.IAppointmentService;
import show.isaBack.serviceInterfaces.IService;
import show.isaBack.serviceInterfaces.IUserInterface;
import show.isaBack.unspecifiedDTO.UnspecifiedDTO;

@Service
public class AppointmentService implements IAppointmentService{

	@Autowired
	private AppointmentRepository appointmentRepository;
	
	@Autowired
	private DermatologistRepository dermatologistRepository;
	
	private AppointmentsMapper appointmentsMapper= new AppointmentsMapper();
	
	@Override
	public List<UnspecifiedDTO<DermatologistAppointmentDTO>> findAllFreeAppointmentsForPharmacyAndForAppointmentType(UUID pharmacyId,
			AppointmentType appointmentType) {
		
		System.out.println("2");
		List<Appointment> appointments = appointmentRepository.findAllFreeAppointmentsForPharmacyAndForAppointmentType(pharmacyId, appointmentType); 
		System.out.println("3");
		System.out.println(appointments);
		List<Dermatologist> allDermatologist= dermatologistRepository.findAll();		
		
		List<UnspecifiedDTO<EmployeeGradeDTO>> dermatologistEmployees= new ArrayList<UnspecifiedDTO<EmployeeGradeDTO>>();
		
		allDermatologist.forEach((dermatologist) -> dermatologistEmployees.add(appointmentsMapper.MapDermatologistToEmployeeDTO(dermatologist)));
		
		List<UnspecifiedDTO<DermatologistAppointmentDTO>> freeAppointments=  appointmentsMapper.MapAppointmentsToListAppointmentsDTO(appointments,dermatologistEmployees);             
		
		
		return freeAppointments;
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	@Override
	public List<UnspecifiedDTO<AuthorityDTO>> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UnspecifiedDTO<DermatologistAppointmentDTO> findById(UUID id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UUID create(DermatologistAppointmentDTO entityDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(DermatologistAppointmentDTO entityDTO, UUID id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean delete(UUID id) {
		// TODO Auto-generated method stub
		return false;
	}

	
	
	
}
