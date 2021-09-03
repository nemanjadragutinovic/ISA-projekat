package show.isaBack.model.appointment;

import java.util.Comparator;
import java.util.Date;
import java.util.List;

import show.isaBack.DTO.AppointmentDTO.FreeAppointmentPeriodDTO;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.time.ZoneId;
import java.time.Duration;


public class AppointmentScheduler {
	private DateRange workTime;
	private int duration;
	private List<Appointment> scheduledAppointments;
	
	public AppointmentScheduler(DateRange workTime, List<Appointment> scheduledAppointments, int duration) {
		super(); 
		this.workTime = workTime;
		this.duration=duration;
		this.scheduledAppointments = SortScheduledAppointment(scheduledAppointments);
	}

	private List<Appointment> SortScheduledAppointment(List<Appointment> scheduledAppointments) {
		// TODO Auto-generated method stub
		Collections.sort(scheduledAppointments, new Comparator<Appointment>() {
			  public int compare(Appointment o1, Appointment o2) {
			      return o1.getStartDateTime().compareTo(o2.getStartDateTime());
			  }
			});
		
		return scheduledAppointments;
	}

	public List<FreeAppointmentPeriodDTO> GetFreeAppointment(){
		List<FreeAppointmentPeriodDTO> freeAppointmentsPeriods = new ArrayList<FreeAppointmentPeriodDTO>();
		
        LocalDateTime startTime = workTime.getStartDateTime();
        LocalDateTime endTime;
        //da li postoji
        if(scheduledAppointments!=null) {
        	for (Appointment appointment : scheduledAppointments)
            {
                 endTime = convertToLocalDateTimeViaInstant(appointment.getStartDateTime());
                 freeAppointmentsPeriods.addAll(FillFreeInterval(startTime, endTime));
                 startTime = convertToLocalDateTimeViaInstant(appointment.getEndDateTime());
            }
        }
        
        endTime = workTime.getEndDateTime();
        freeAppointmentsPeriods.addAll(FillFreeInterval(startTime, endTime));
        return freeAppointmentsPeriods;
	}

	private Collection<? extends FreeAppointmentPeriodDTO> FillFreeInterval(LocalDateTime startTime, LocalDateTime endTime) {
		List<FreeAppointmentPeriodDTO> freeAppointments = new ArrayList<FreeAppointmentPeriodDTO>();
		
		while (Duration.between(startTime, endTime).toMinutes() >= Duration.ofMinutes(duration).toMinutes())
        {
            DateRange dateRange = new DateRange(startTime, startTime.plusMinutes(duration));
            FreeAppointmentPeriodDTO freeAppointmentPeriod = new FreeAppointmentPeriodDTO(convertToDateViaInstant(dateRange.getStartDateTime()),convertToDateViaInstant(dateRange.getEndDateTime()));
            startTime = convertToLocalDateTimeViaInstant(freeAppointmentPeriod.getEndDate());
            freeAppointments.add(freeAppointmentPeriod);
        }
        return freeAppointments;
	}
	
	private LocalDateTime convertToLocalDateTimeViaInstant(Date dateToConvert) {
	    return dateToConvert.toInstant()
	      .atZone(ZoneId.systemDefault())
	      .toLocalDateTime();
	}
	
	private Date convertToDateViaInstant(LocalDateTime dateToConvert) {
	    return java.util.Date
	    	      .from(dateToConvert.atZone(ZoneId.systemDefault())
	    	      .toInstant());
	    }

	
	
	 
}