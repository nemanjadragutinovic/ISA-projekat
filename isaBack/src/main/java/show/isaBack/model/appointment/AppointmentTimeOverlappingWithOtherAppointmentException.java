package show.isaBack.model.appointment;

public class AppointmentTimeOverlappingWithOtherAppointmentException extends Exception{

	private static final long serialVersionUID = 1L;

	public AppointmentTimeOverlappingWithOtherAppointmentException(){
		super();
	}
	
	public AppointmentTimeOverlappingWithOtherAppointmentException(String s){  
		  super(s);  
	}
}
