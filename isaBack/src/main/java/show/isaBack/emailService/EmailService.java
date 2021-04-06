package show.isaBack.emailService;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import show.isaBack.model.Patient;
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
					+ "<a href=\"" + url + "\">Verify your account</a>.</p>" + "<p>Kind Regards, Health Clinic</p>"; 
		helper.setText(htmlMsg, true);
		helper.setTo(patient.getEmail());
		helper.setSubject("Activate account");
		helper.setFrom(env.getProperty("spring.mail.username"));
		System.out.println("usao 2");
		javaMailSender.send(mimeMessage);
		
	}

}
