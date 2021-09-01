package show.isaBack.service.AppointmentService;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;


import show.isaBack.DTO.AppointmentDTO.AppointmentReportDTO;
import show.isaBack.model.appointment.Appointment;
import show.isaBack.model.appointment.AppointmentReport;
import show.isaBack.repository.AppointmentRepository.AppointmentReportRepository;
import show.isaBack.repository.AppointmentRepository.AppointmentRepository;
import show.isaBack.unspecifiedDTO.UnspecifiedDTO;

public class AppointmentReportService {
	@Autowired
	AppointmentReportRepository appointmentReportRepository;
	@Autowired
	AppointmentRepository appointmentRepository;

	
	public List<UnspecifiedDTO<AppointmentReportDTO>> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	
	public UnspecifiedDTO<AppointmentReportDTO> findById(UUID id) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public boolean delete(UUID id) {
		// TODO Auto-generated method stub
		return false;
	}

	public UUID create(AppointmentReportDTO entityDTO) {
		// TODO Auto-generated method stub
		try {
			Appointment appointment = appointmentRepository.findById(entityDTO.getAppointmentId()).get();
			AppointmentReport appointmentReport = new AppointmentReport(entityDTO.getAnamnesis(), entityDTO.getDiagnosis(), entityDTO.getTherapy(), appointment);
			appointmentReportRepository.save(appointmentReport);
			return appointmentReport.getId();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
			
		}
		
	}

	
	public void update(AppointmentReportDTO entityDTO, UUID id) {
		// TODO Auto-generated method stub
		
	}
}
