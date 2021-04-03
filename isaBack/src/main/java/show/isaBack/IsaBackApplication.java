package show.isaBack;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import show.isaBack.DTO.drugDTO.DrugDTO;
import show.isaBack.controller.drugController.DrugController;
import show.isaBack.model.Drug;
import show.isaBack.unspecifiedDTO.UnspecifiedDTO;

@SpringBootApplication
public class IsaBackApplication {

	public static void main(String[] args) {
		SpringApplication.run(IsaBackApplication.class, args);
		
		
	}

}
