package show.isaBack.DTO.AppointmentDTO;
import java.util.UUID;
public class CreateAppointmentDTO {

	private UUID patientId;
	private UUID appointmentId;
	public CreateAppointmentDTO(UUID patientId, UUID appointmentId) {
		super();
		this.patientId = patientId;
		this.appointmentId = appointmentId;
	}
	public CreateAppointmentDTO() {
		super();
	}
	public UUID getPatientId() {
		return patientId;
	}
	public void setPatientId(UUID patientId) {
		this.patientId = patientId;
	}
	public UUID getAppointmentId() {
		return appointmentId;
	}
	public void setAppointmentId(UUID appointmentId) {
		this.appointmentId = appointmentId;
	}
}
