package show.isaBack.Mappers.Appointmets;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import show.isaBack.DTO.AppointmentDTO.AppointmentDTO;
import show.isaBack.DTO.AppointmentDTO.DermatologistAppointmentDTO;
import show.isaBack.DTO.userDTO.EmployeeGradeDTO;
import show.isaBack.Mappers.Pharmacy.UserMapper;
import show.isaBack.model.Dermatologist;
import show.isaBack.model.Pharmacist;
import show.isaBack.model.appointment.Appointment;
import show.isaBack.repository.Pharmacy.PharmacyGradeRepository;
import show.isaBack.repository.userRepository.EmployeeGradeRepository;
import show.isaBack.serviceInterfaces.IEmployeeGradeService;
import show.isaBack.serviceInterfaces.IUserInterface;
import show.isaBack.unspecifiedDTO.UnspecifiedDTO;

@Service
public class AppointmentsMapper {

	
	
	
	public UnspecifiedDTO<EmployeeGradeDTO> MapDermatologistToEmployeeDTO(Dermatologist dermatologist,double avgGrade){
		if(dermatologist == null) 
			throw new IllegalArgumentException();
		
		return new UnspecifiedDTO<EmployeeGradeDTO> (dermatologist.getId(), new EmployeeGradeDTO( dermatologist.getName(), dermatologist.getSurname(),
				dermatologist.getEmail(),dermatologist.getAddress(), dermatologist.getPhoneNumber(),avgGrade));
	}
	
	
	public UnspecifiedDTO<EmployeeGradeDTO> MapPharmacistsToEmployeeDTO(Pharmacist pharmacist, double avgGrade){
		if(pharmacist == null) 
			throw new IllegalArgumentException();

		
		return new UnspecifiedDTO<EmployeeGradeDTO> (pharmacist.getId(), new EmployeeGradeDTO( pharmacist.getName(), pharmacist.getSurname(),
				pharmacist.getEmail(),pharmacist.getAddress(), pharmacist.getPhoneNumber(), avgGrade));
	}
	
	
	public UnspecifiedDTO<EmployeeGradeDTO> findMatchingEmployee(UUID pharmacyEmployeId,List<UnspecifiedDTO<EmployeeGradeDTO>> employeesDTO){
		
		for (UnspecifiedDTO<EmployeeGradeDTO> currentEmployee : employeesDTO) {
			if(currentEmployee.Id.equals(pharmacyEmployeId))
				return currentEmployee;
		}
		
		return null;
	
	}
	
	
	public List<UnspecifiedDTO<DermatologistAppointmentDTO>> MapAppointmentsToListAppointmentsDTO(List<Appointment> appointments,
			List<UnspecifiedDTO<EmployeeGradeDTO>> dermatologistEmployees){
		
		 List<UnspecifiedDTO<DermatologistAppointmentDTO>> freeAppointmentsDTO= new ArrayList<UnspecifiedDTO<DermatologistAppointmentDTO>>();
		 
		 appointments.forEach((currentAppointment) -> freeAppointmentsDTO.add(MappAppointmentsToDermatologistAppointmentsDTO(currentAppointment, findMatchingEmployee(currentAppointment.getEmployee().getId(), dermatologistEmployees))));
		
		
		
		return freeAppointmentsDTO;
		
		
	}
	
	
		
	
	public UnspecifiedDTO<DermatologistAppointmentDTO> MappAppointmentsToDermatologistAppointmentsDTO(Appointment appointment,UnspecifiedDTO<EmployeeGradeDTO> employeeDTO){
		
		return new UnspecifiedDTO<DermatologistAppointmentDTO>(appointment.getId(), 
				new DermatologistAppointmentDTO(employeeDTO,appointment.getStartDateTime(),appointment.getEndDateTime(),appointment.getPrice()));
		
	}
	
	public static List<UnspecifiedDTO<AppointmentDTO>> MapAppointmentPersistenceListToAppointmentUnspecifiedDTOList(
			List<Appointment> appointments) {
		
		List<UnspecifiedDTO<AppointmentDTO>> appointmentDTOList = new ArrayList<UnspecifiedDTO<AppointmentDTO>>();
		appointments.forEach((a) -> appointmentDTOList.add(MapAppointmentPersistenceToAppointmentUnspecifiedDTO(a)));

		return appointmentDTOList;
	}
	
	public static UnspecifiedDTO<AppointmentDTO> MapAppointmentPersistenceToAppointmentUnspecifiedDTO(Appointment appointment){
		if(appointment == null) throw new IllegalArgumentException();
		
		return new UnspecifiedDTO<AppointmentDTO>(appointment.getId(), new AppointmentDTO(UserMapper.MapEmployeePersistenceToEmployeeIdentifiableDTO(appointment.getEmployee()), UserMapper.MapUserPersistenceToUserUnspecifiedDTO(appointment.getPatient()), appointment.getAppointmentStatus(), appointment.getStartDateTime(), appointment.getEndDateTime(), appointment.getPrice()));
	}
	
}
