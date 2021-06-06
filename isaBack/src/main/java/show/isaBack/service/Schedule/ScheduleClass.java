package show.isaBack.service.Schedule;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import show.isaBack.serviceInterfaces.IAppointmentService;
import show.isaBack.serviceInterfaces.IDrugService;
import show.isaBack.serviceInterfaces.IUserInterface;




@Component
public class ScheduleClass {

	@Autowired
	private IDrugService drugService;
	
	@Autowired
	private IUserInterface userSerice;
	
	@Autowired
	private IAppointmentService appointmentService;
	
	
	@Scheduled(fixedRate = 240000, initialDelay = 1500)
	public void refreshPatientDrugsReservations() {
		System.out.println("drug rezervacije");
		
			drugService.refreshPatientDrugsReservations();
			 
		
	}
	
	@Scheduled(cron="@monthly")
	public void refreshPatientsPenalties() {
		System.out.println("restartovanje penala");
		
		userSerice.refreshPatientsPenalties();
			 
		
	}
	
	@Scheduled(fixedRate = 240000, initialDelay = 1500)
	public void refreshPatientsAppointments() {
		System.out.println("rezervacije termina");
		
		appointmentService.refreshPatientsAppointments();
			 
		
	}
	
}
