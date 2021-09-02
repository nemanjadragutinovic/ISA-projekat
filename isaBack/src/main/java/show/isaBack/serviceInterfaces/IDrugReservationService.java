package show.isaBack.serviceInterfaces;

import java.util.List;
import java.util.UUID;

import javax.mail.MessagingException;

import org.springframework.mail.MailException;

import show.isaBack.DTO.drugDTO.EmployeeReservationDrugDTO;

public interface IDrugReservationService {

	UUID reserveDrugAsEmployee(EmployeeReservationDrugDTO employeeReservationDrugDTO);

	void reduceAmountOfReservedDrug(UUID drugId, UUID pharmacyId, int count);

}
