package show.isaBack.model;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

@Embeddable
public class StaffFeedbackId implements Serializable{

	private static final long serialVersionUID = 1L;

	@ManyToOne(optional = false)
	private User user;
	
	@ManyToOne(optional = false)
	private Patient patient;
	
	
    public StaffFeedbackId() {}
	
	public StaffFeedbackId(User user, Patient patient) {
		super();
		this.user = user;
		this.patient = patient;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}
	
	
	
}
