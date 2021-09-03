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

import show.isaBack.model.ActionPromotion;
import show.isaBack.model.ActionType;
import show.isaBack.model.ComplaintPharmacy;
import show.isaBack.model.ComplaintStaff;
import show.isaBack.model.Patient;
import show.isaBack.model.appointment.Appointment;

import show.isaBack.model.drugs.DrugReservation;

import show.isaBack.model.drugs.EReceiptItems;
import show.isaBack.model.drugs.Offers;
import show.isaBack.model.drugs.Order;

@Service
public class EmailService {
	
	@Autowired
	private JavaMailSender javaMailSender;
	
	private final String LOCAL_URL = "http://localhost:3000";
	
	
	
	@Autowired
	private Environment env;
	
	
	
	@Async
	public void sendSignUpNotificaitionAsync(Patient patient)
			throws MailException, InterruptedException, MessagingException {
		
		
		System.out.println("usao 1");
		
		String url = LOCAL_URL + "/activeAccount/" + patient.getId();
		
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
		String htmlMsg = "<p>Hello " + patient.getName() + ",</p>" +
					"<p>You registered an account on Health Clinic, before being able to use your account you need to verify that this is your email address by clicking here:</p>"
					+ "<a href=\"" + url + "\">Verify your account</a>.</p>" + "<p>Health Clinic</p>"; 
		helper.setText(htmlMsg, true);
		helper.setTo("acamijatovic.98@gmail.com");
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
		helper.setTo(appointment.getPatient().getEmail());
		helper.setSubject("Appointment reservation");
		helper.setFrom(env.getProperty("spring.mail.username"));
		javaMailSender.send(mimeMessage);
		
	}
	
	
	@Async
	public void sendNotificationForDrugReservation(DrugReservation drugReservation) throws MessagingException {
		
		DateFormat formatterForTime = new SimpleDateFormat("HH:mm");
		DateFormat mainFormatter = new SimpleDateFormat("yyyy-MM-dd");
		
		
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
		String htmlMsg = "<p>Hello " + drugReservation.getPatient().getName() + ",</p>" + "<p>You successfully reserved drug in phamracy: " + drugReservation.getPharmacy().getName() + 
						", on date " + mainFormatter.format(drugReservation.getStartDate()) + ", at " + formatterForTime.format(drugReservation.getStartDate()) + " o'clock"+
						", to date " + mainFormatter.format(drugReservation.getEndDate()) + ", at " + formatterForTime.format(drugReservation.getEndDate()) + " o'clock."+
						" Drug reservation specific id : " + drugReservation.getId() +
						"</p> <p>Health Clinic</p>";

		helper.setText(htmlMsg, true);
		helper.setTo(drugReservation.getPatient().getEmail());
		//helper.setTo("stefanzec@hotmail.rs");
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
	
	@Async
	public void sendActionAndPromotionNotificaitionAsync(Patient patient, ActionPromotion actionPromotion)
			throws  MailException, InterruptedException, MessagingException {
		System.out.println("Salji Mejl Bidibou");
		String aa="";
		if(actionPromotion.getActionType()==ActionType.DRUGDISCOUNT) {
			aa= " buying drugs";
		}else if(actionPromotion.getActionType()==ActionType.CONSULTATIONDISCOUNT) {
			aa= " consultation";
		}else {
			aa= " consultation";
		}
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
		String htmlMsg = "<p>Dear " + patient.getName()+" "+patient.getSurname() + ",</p>" +
				"<p>"+"From: " + actionPromotion.getDateFrom().toString()+" we have special action."+"</p>"+
				"<p>"+"In our pharmcy you have " +actionPromotion.getDiscount() +"% for"+ aa +
				"<p> P.S This action is valid to: "+ actionPromotion.getDateTo().toString()+"</p>"+
				"<p>"+"Best regards, Health Clinic "+ "</p>";
		helper.setText(htmlMsg, true);
		System.out.println("Na spavanje");
		//helper.setTo(patient.getEmail());
		helper.setTo("acamijatovic.98@gmail.com");
		helper.setSubject("ACTION AND PROMOTION IN "+actionPromotion.getPharmacy().getName());
		helper.setFrom(env.getProperty("spring.mail.username"));
		System.out.println("Budjenje");
		javaMailSender.send(mimeMessage);
		System.out.println("Poslatoooooooo");
	}
	
	@Async
	public void sendOfferAccepted(Order order, Offers offer)
			throws MessagingException {

		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
		String htmlMsg = "<p>Dear " + offer.getSupplier().getName()+" "+offer.getSupplier().getSurname() + ",</p>" +
				
				"<p>"+"Your offer is accepted" +"</p>"+
			
				"<p>"+"Best regards, Health Clinic "+ "</p>";
		helper.setText(htmlMsg, true);
		System.out.println("Na spavanje");
		//helper.setTo(patient.getEmail());
		helper.setTo("acamijatovic.98@gmail.com");
		helper.setSubject("OFFER IS ACCEPTED in "+order.getPharmacy().getName());
		helper.setFrom(env.getProperty("spring.mail.username"));
		System.out.println("Budjenje");
		javaMailSender.send(mimeMessage);
		System.out.println("Poslatoooooooo");
	}
	
}
