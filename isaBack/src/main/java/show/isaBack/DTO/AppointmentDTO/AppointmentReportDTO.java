package show.isaBack.DTO.AppointmentDTO;
import java.util.UUID;

public class AppointmentReportDTO {
	private UUID appointmentId;
	private String therapy;
	private String diagnosis;
	private String anamnesis;
	
	public AppointmentReportDTO(String anamnesis, String diagnosis, String therapy, UUID appointmentId) {
		super();
		this.anamnesis = anamnesis;
		this.diagnosis = diagnosis;
		this.therapy = therapy;
		this.appointmentId = appointmentId;
		
		
	}
	public AppointmentReportDTO() {
		super();
	}
	
	public UUID getAppointmentId() {
		return appointmentId;
	}
	public void setAppointmentId(UUID appointmentId) {
		this.appointmentId = appointmentId;
	}
	
	public String getTherapy() {
		return therapy;
	}
	public void setTherapy(String therapy) {
		this.therapy = therapy;
	}
	
	public String getDiagnosis() {
		return diagnosis;
	}
	public void setDiagnosis(String diagnosis) {
		this.diagnosis = diagnosis;
	}
	
	public String getAnamnesis() {
		return anamnesis;
	}
	public void setAnamnesis(String anamnesis) {
		this.anamnesis = anamnesis;
	}
	
	
}
