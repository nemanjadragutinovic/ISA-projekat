package show.isaBack.DTO.drugDTO;

import java.util.UUID;

public class DrugOrderDTO {
	private UUID drugInstanceId;
    private String drugName;
    private String fabricCode;
    private String producerName;
    private double quantity;
    private int amount;
	public DrugOrderDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	public DrugOrderDTO(UUID drugInstanceId, String drugName, String fabricCode, String producerName, double quantity,
			int amount) {
		super();
		this.drugInstanceId = drugInstanceId;
		this.drugName = drugName;
		this.fabricCode = fabricCode;
		this.producerName = producerName;
		this.quantity = quantity;
		this.amount = amount;
	}



	public DrugOrderDTO(UUID drugInstanceId, int amount) {
		super();
		this.drugInstanceId = drugInstanceId;
		this.amount = amount;
	}



	public String getDrugName() {
		return drugName;
	}



	public void setDrugName(String drugName) {
		this.drugName = drugName;
	}



	public String getFabricCode() {
		return fabricCode;
	}



	public void setFabricCode(String fabricCode) {
		this.fabricCode = fabricCode;
	}



	public String getProducerName() {
		return producerName;
	}



	public void setProducerName(String producerName) {
		this.producerName = producerName;
	}



	public double getQuantity() {
		return quantity;
	}



	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}



	public UUID getDrugInstanceId() {
		return drugInstanceId;
	}
	public void setDrugInstanceId(UUID drugInstanceId) {
		this.drugInstanceId = drugInstanceId;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}

	
}