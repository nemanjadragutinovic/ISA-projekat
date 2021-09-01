package show.isaBack.repository.AppointmentRepository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import show.isaBack.model.appointment.AppointmentReport;

public interface AppointmentReportRepository extends JpaRepository<AppointmentReport, UUID>{
	AppointmentReport findByAppointmentId(UUID appointmentId);

}
