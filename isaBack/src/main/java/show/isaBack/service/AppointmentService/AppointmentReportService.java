package show.isaBack.service.AppointmentService;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import show.isaBack.DTO.AppointmentDTO.AppointmentReportDTO;
import show.isaBack.DTO.userDTO.AuthorityDTO;
import show.isaBack.model.appointment.Appointment;
import show.isaBack.model.appointment.AppointmentReport;
import show.isaBack.repository.AppointmentRepository.AppointmentReportRepository;
import show.isaBack.repository.AppointmentRepository.AppointmentRepository;
import show.isaBack.unspecifiedDTO.UnspecifiedDTO;
import show.isaBack.serviceInterfaces.IAppointmentReportService;

@Service
public class AppointmentReportService implements IAppointmentReportService{
	@Autowired
	AppointmentReportRepository appointmentReportRepository;
	@Autowired
	AppointmentRepository appointmentRepository;

	@Override
	public List<UnspecifiedDTO<AuthorityDTO>> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UnspecifiedDTO<AppointmentReportDTO> findById(UUID id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean delete(UUID id) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
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

	@Override
	public void update(AppointmentReportDTO entityDTO, UUID id) {
		// TODO Auto-generated method stub
		
	}
}
