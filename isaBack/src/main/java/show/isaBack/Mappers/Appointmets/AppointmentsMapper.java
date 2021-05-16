package show.isaBack.Mappers.Appointmets;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import show.isaBack.DTO.AppointmentDTO.DermatologistAppointmentDTO;
import show.isaBack.DTO.userDTO.EmployeeGradeDTO;
import show.isaBack.model.Dermatologist;
import show.isaBack.model.appointment.Appointment;
import show.isaBack.unspecifiedDTO.UnspecifiedDTO;

public class AppointmentsMapper {

	
	public UnspecifiedDTO<EmployeeGradeDTO> MapDermatologistToEmployeeDTO(Dermatologist dermatologist){
		if(dermatologist == null) throw new IllegalArgumentException();
		
		return new UnspecifiedDTO<EmployeeGradeDTO> (dermatologist.getId(), new EmployeeGradeDTO( dermatologist.getName(), dermatologist.getSurname(),
				dermatologist.getEmail(),dermatologist.getAddress(), dermatologist.getPhoneNumber()));
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
	
}
