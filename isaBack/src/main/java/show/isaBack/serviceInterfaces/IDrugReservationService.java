package show.isaBack.serviceInterfaces;

import java.util.List;
import java.util.UUID;

import javax.mail.MessagingException;

import org.springframework.mail.MailException;

import show.isaBack.DTO.drugDTO.DrugReservationDTO;
import show.isaBack.DTO.drugDTO.DrugReservationResponseDTO;
import show.isaBack.DTO.drugDTO.EmployeeReservationDrugDTO;
import show.isaBack.unspecifiedDTO.UnspecifiedDTO;


public interface IDrugReservationService {

	UUID reserveDrugAsEmployee(EmployeeReservationDrugDTO employeeReservationDrugDTO);

	void reduceAmountOfReservedDrug(UUID drugId, UUID pharmacyId, int count);

	UnspecifiedDTO<DrugReservationResponseDTO> getDrugReservation(UUID reservationId);

	void processReservation(UUID drugReservationId) throws MailException, InterruptedException, MessagingException;

}
