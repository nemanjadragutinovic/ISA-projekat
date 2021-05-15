package show.isaBack.DTO.drugDTO;

import java.util.List;

import show.isaBack.unspecifiedDTO.UnspecifiedDTO;

public class DrugInstanceDTO {

private String name;
	
	private String code;
	
	private String drugInstanceName;
	
	private UnspecifiedDTO<ManufacturerDTO> manufacturer;
	
	private FormatDrug drugFormat;
	
	private DrugKind drugKind;

	private double quantity;
	
	private String sideEffects;
	
	private String recommendedAmount;
	
	private List<UnspecifiedDTO<ReplaceDrugDTO>> replacingDrugs;
	
    private List<UnspecifiedDTO<AllergenDTO>> allergens;
	
    private List<UnspecifiedDTO<IngredientDTO>> ingredients;
    
	private int loyalityPoints;
	
	private boolean onReciept;
	
	private double avgGrade;
	
	public DrugInstanceDTO() {}
	
	public DrugInstanceDTO(String name, String code, String drugInstanceName, UnspecifiedDTO<ManufacturerDTO> manufacturer,
			FormatDrug drugFormat, double quiantity, String sideEffects, String recommendedAmount,
			List<UnspecifiedDTO<ReplaceDrugDTO>> replacingDrugs, List<UnspecifiedDTO<AllergenDTO>> allergens,
			List<UnspecifiedDTO<IngredientDTO>> ingredients, int loyalityPoints, boolean onReciept, DrugKind drugKind) {
		super();
		this.name = name;
		this.code = code;
		this.drugInstanceName = drugInstanceName;
		this.manufacturer = manufacturer;
		this.drugFormat = drugFormat;
		this.quantity = quiantity;
		this.sideEffects = sideEffects;
		this.recommendedAmount = recommendedAmount;
		this.replacingDrugs = replacingDrugs;
		this.allergens = allergens;
		this.ingredients = ingredients;
		this.onReciept = onReciept;
		this.drugKind = drugKind;
		this.loyalityPoints = loyalityPoints;
	}
	
	public DrugInstanceDTO(String name, String code, String drugInstanceName, UnspecifiedDTO<ManufacturerDTO> manufacturer,
			FormatDrug drugFormat, double quiantity, String sideEffects, String recommendedAmount,
			List<UnspecifiedDTO<ReplaceDrugDTO>> replacingDrugs, List<UnspecifiedDTO<AllergenDTO>> allergens,
			List<UnspecifiedDTO<IngredientDTO>> ingredients, int loyalityPoints, boolean onReciept, DrugKind drugKind, double avg) {
		super();
		this.name = name;
		this.code = code;
		this.drugInstanceName = drugInstanceName;
		this.manufacturer = manufacturer;
		this.drugFormat = drugFormat;
		this.quantity = quiantity;
		this.sideEffects = sideEffects;
		this.recommendedAmount = recommendedAmount;
		this.replacingDrugs = replacingDrugs;
		this.allergens = allergens;
		this.ingredients = ingredients;
		this.onReciept = onReciept;
		this.drugKind = drugKind;
		this.loyalityPoints = loyalityPoints;
		this.avgGrade = avg;
	}
	
	public DrugInstanceDTO(String name, String code, String drugInstanceName,
			FormatDrug drugFormat, double quiantity, String sideEffects, String recommendedAmount,
			int loyalityPoints, boolean onReciept, DrugKind drugKind) {
		super();
		this.name = name;
		this.code = code;
		this.drugInstanceName = drugInstanceName;
		this.drugFormat = drugFormat;
		this.quantity = quiantity;
		this.sideEffects = sideEffects;
		this.recommendedAmount = recommendedAmount;
		this.onReciept = onReciept;
		this.drugKind = drugKind;
		this.loyalityPoints = loyalityPoints;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDrugInstanceName() {
		return drugInstanceName;
	}

	public void setDrugInstanceName(String drugInstanceName) {
		this.drugInstanceName = drugInstanceName;
	}

	public UnspecifiedDTO<ManufacturerDTO> getManufacturer() {
		return manufacturer;
	}
	
	public int getLoyalityPoints() {
		return loyalityPoints;
	}

	public void setLoyalityPoints(int loyalityPoints) {
		this.loyalityPoints = loyalityPoints;
	}
	public void setManufacturer(UnspecifiedDTO<ManufacturerDTO> manufacturer) {
		this.manufacturer = manufacturer;
	}

	public FormatDrug getDrugFormat() {
		return drugFormat;
	}

	public void setDrugFormat(FormatDrug drugFormat) {
		this.drugFormat = drugFormat;
	}

	public double getQuantity() {
		return quantity;
	}

	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}

	public String getSideEffects() {
		return sideEffects;
	}

	public void setSideEffects(String sideEffects) {
		this.sideEffects = sideEffects;
	}

	public String getRecommendedAmount() {
		return recommendedAmount;
	}

	public void setRecommendedAmount(String recommendedAmount) {
		this.recommendedAmount = recommendedAmount;
	}

	public List<UnspecifiedDTO<ReplaceDrugDTO>> getReplacingDrugs() {
		return replacingDrugs;
	}

	public void setReplacingDrugs(List<UnspecifiedDTO<ReplaceDrugDTO>> replacingDrugs) {
		this.replacingDrugs = replacingDrugs;
	}

	public List<UnspecifiedDTO<AllergenDTO>> getAllergens() {
		return allergens;
	}

	public void setAllergens(List<UnspecifiedDTO<AllergenDTO>> allergens) {
		this.allergens = allergens;
	}

	public List<UnspecifiedDTO<IngredientDTO>> getIngredients() {
		return ingredients;
	}

	public void setIngredients(List<UnspecifiedDTO<IngredientDTO>> ingredients) {
		this.ingredients = ingredients;
	}

	public boolean isOnReciept() {
		return onReciept;
	}

	public void setOnReciept(boolean onReciept) {
		this.onReciept = onReciept;
	}

	public DrugKind getDrugKind() {
		return drugKind;
	}

	public void setDrugKind(DrugKind drugKind) {
		this.drugKind = drugKind;
	}

	public double getAvgGrade() {
		return avgGrade;
	}

	public void setAvgGrade(double avgGrade) {
		this.avgGrade = avgGrade;
	}
	
	
	
}
