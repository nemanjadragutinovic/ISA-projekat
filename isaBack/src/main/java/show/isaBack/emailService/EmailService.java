package show.isaBack.emailService;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import show.isaBack.model.ComplaintPharmacy;
import show.isaBack.model.ComplaintStaff;
import show.isaBack.model.Patient;
import show.isaBack.model.appointment.Appointment;
import show.isaBack.model.drugs.EReceiptItems;
@Service
public class EmailService {
	
	@Autowired
	private JavaMailSender javaMailSender;
	
	private final String LOCAL_URL = "http://localhost:8081";
	
	
	
	@Autowired
	private Environment env;
	
	
	
	@Async
	public void sendSignUpNotificaitionAsync(Patient patient)
			throws MailException, InterruptedException, MessagingException {
		
		
		System.out.println("usao 1");
		
		String url = LOCAL_URL + "/reg/activeAccountForPatient/" + patient.getId();
		
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
		String htmlMsg = "<p>Hello " + patient.getName() + ",</p>" +
					"<p>You registered an account on Health Clinic, before being able to use your account you need to verify that this is your email address by clicking here:</p>"
					+ "<a href=\"" + url + "\">Verify your account</a>.</p>" + "<p>Health Clinic</p>"; 
		helper.setText(htmlMsg, true);
		helper.setTo(patient.getEmail());
		helper.setSubject("Activate account");
		helper.setFrom(env.getProperty("spring.mail.username"));
		System.out.println("usao 2");
		javaMailSender.send(mimeMessage);
		
	}
	
	
	@Async
	public void sendEmailforReplyedComplaint(ComplaintStaff complaintStaff)
			throws MailException, InterruptedException, MessagingException {
		
		
		System.out.println("usao 1");
		
		
		
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
		String htmlMsg = "<p>Hello " + complaintStaff.getPatient().getName() + ",</p>" +
					"<p>Here is your reply for your complaint:" + complaintStaff.getText()+ "</p>"
					+ "<p>" + complaintStaff.getReply()+ "</p>" + "<p>Health Clinic</p>"; 
		helper.setText(htmlMsg, true);
		helper.setTo(complaintStaff.getPatient().getEmail());
		System.out.println(complaintStaff.getPatient().getEmail());
		helper.setSubject("Reply from Health Clinic");
		helper.setFrom(env.getProperty("spring.mail.username"));
		System.out.println("usao 2");
		javaMailSender.send(mimeMessage);
		
	}
	
	
	@Async
	public void sendEmailforReplyedComplaint(ComplaintPharmacy complaintPharmacy)
			throws MailException, InterruptedException, MessagingException {
		
		
		System.out.println("usao 1");
		
		
		
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
		String htmlMsg = "<p>Hello " + complaintPharmacy.getPatient().getName() + ",</p>" +
					"<p>Here is your reply for your complaint:" + complaintPharmacy.getText()+ "</p>"
					+ "<p>" + complaintPharmacy.getReply()+ "</p>" + "<p>Health Clinic</p>"; 
		helper.setText(htmlMsg, true);
		helper.setTo(complaintPharmacy.getPatient().getEmail());
		System.out.println(complaintPharmacy.getPatient().getEmail());
		helper.setSubject("Reply from Health Clinic");
		helper.setFrom(env.getProperty("spring.mail.username"));
		System.out.println("usao 2");
		javaMailSender.send(mimeMessage);
		
	}
	
	@Async
	public void sendAppointmentReservationNotification(Appointment appointment) throws MessagingException {
		
		DateFormat formatterForTime = new SimpleDateFormat("HH:mm");
		DateFormat mainFormatter = new SimpleDateFormat("yyyy-MM-dd");
		
		
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
		String htmlMsg = "<p>Hello " + appointment.getPatient().getName() + ",</p>" + "<p>You successfully reserved appointment by dermatologist: " + appointment.getEmployee().getName() + " "
						+ appointment.getEmployee().getSurname() + 
						", on date " + mainFormatter.format(appointment.getStartDateTime()) + ", at " + formatterForTime.format(appointment.getStartDateTime()) + " o'clock."+
						"</p> <p>Health Clinic</p>";

		helper.setText(htmlMsg, true);
		helper.setTo(appointment.getPatient().getEmail());
		helper.setSubject("Appointment reservation");
		helper.setFrom(env.getProperty("spring.mail.username"));
		javaMailSender.send(mimeMessage);
		
	}
	
	
	@Async
	public void sendConsultationAppointmentReservationNotification(Appointment appointment) throws MessagingException {
		
		DateFormat formatterForTime = new SimpleDateFormat("HH:mm");
		DateFormat mainFormatter = new SimpleDateFormat("yyyy-MM-dd");
		
		
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
		String htmlMsg = "<p>Hello " + appointment.getPatient().getName() + ",</p>" + "<p>You successfully reserved appointment by pharmacist: " + appointment.getEmployee().getName() + " "
						+ appointment.getEmployee().getSurname() + 
						", on date " + mainFormatter.format(appointment.getStartDateTime()) + ", at " + formatterForTime.format(appointment.getStartDateTime()) + " o'clock."+
						"</p> <p>Health Clinic</p>";

		helper.setText(htmlMsg, true);
		//helper.setTo(appointment.getPatient().getEmail());
		helper.setTo("stefanzec@hotmail.rs");
		helper.setSubject("Appointment reservation");
		helper.setFrom(env.getProperty("spring.mail.username"));
		javaMailSender.send(mimeMessage);
		
	}
	
	@Async
	public void sendEmailAfterQRPurchase(Patient pat,List<EReceiptItems> items)
			throws MailException, InterruptedException, MessagingException {
		
		
		System.out.println("usao 1");
		
		String show="<p>";
		
		for (EReceiptItems eReceiptItems : items) {
			show += "Drug name: " + eReceiptItems.getDrugInstance().getDrugInstanceName() + "         Amount: " +  eReceiptItems.getAmount() + "<br>";
		}
		
		show += "</p>";
		
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
		String htmlMsg = "<p>Hello " + pat.getName() + " " +pat.getSurname() + ",</p>" +
					"<p>Here is the list of drugs you bought:" + "</p>"
					+ show 
					+ "<p>Health Clinic</p>"; 
		
		helper.setText(htmlMsg, true);
		helper.setTo(pat.getEmail());
		System.out.println(pat.getEmail());
		helper.setSubject("List of drugs you bought");
		helper.setFrom(env.getProperty("spring.mail.username"));
		System.out.println("usao 2");
		javaMailSender.send(mimeMessage);
		
	}
	

}
