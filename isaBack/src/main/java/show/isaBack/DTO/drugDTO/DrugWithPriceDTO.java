package show.isaBack.DTO.drugDTO;

public class DrugWithPriceDTO {
	
	private String name;
	
	private String producerName;
	
	private String fabricCode;
	
	private FormatDrug formatDrug;
	
	private double quantity;
	
	private Integer count;

	private double avgGrade;

	private double price;
	
	

	public DrugWithPriceDTO(String name, String producerName, String fabricCode, FormatDrug formatDrug,
			double quantity, double avgGrade, double price,Integer count) {
		super();
		this.name = name;
		this.producerName = producerName;
		this.fabricCode = fabricCode;
		this.formatDrug = formatDrug;
		this.quantity = quantity;
		this.count=count;
		this.avgGrade = avgGrade;
		this.price = price;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public FormatDrug getDrugFormat() {
		return formatDrug;
	}

	public void setDrugFormat(FormatDrug formatDrug) {
		this.formatDrug = formatDrug;
	}

	public double getQuantity() {
		return quantity;
	}

	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}


	public double getAvgGrade() {
		return avgGrade;
	}

	public void setAvgGrade(double avgGrade) {
		this.avgGrade = avgGrade;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}
}
