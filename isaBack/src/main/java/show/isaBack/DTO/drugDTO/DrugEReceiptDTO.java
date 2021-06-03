package show.isaBack.DTO.drugDTO;

public class DrugEReceiptDTO {

	private String drugName;
	private int drugCount;
	
	
	public DrugEReceiptDTO() {
		super();
	}
	public DrugEReceiptDTO(String drugName, int drugCount) {
		super();
		this.drugName = drugName;
		this.drugCount = drugCount;
	}
	public String getDrugName() {
		return drugName;
	}
	public void setDrugName(String drugName) {
		this.drugName = drugName;
	}
	public int getDrugCount() {
		return drugCount;
	}
	public void setDrugCount(int drugCount) {
		this.drugCount = drugCount;
	}
	
	
	
	
}
