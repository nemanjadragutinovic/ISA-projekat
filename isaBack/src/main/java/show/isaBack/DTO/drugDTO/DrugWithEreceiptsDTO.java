package show.isaBack.DTO.drugDTO;

import java.util.List;

import show.isaBack.unspecifiedDTO.UnspecifiedDTO;

public class DrugWithEreceiptsDTO {

	private String drugName;

	private FormatDrug drugFormat;
	
	private List<UnspecifiedDTO<EreceiptWithPharmacyDTO>> Ereceipts;

	public DrugWithEreceiptsDTO() {
		super();
	}

	public DrugWithEreceiptsDTO(String drugName,FormatDrug drugFormat,
			List<UnspecifiedDTO<EreceiptWithPharmacyDTO>> ereceipts) {
		super();
		this.drugName = drugName;
		this.drugFormat=drugFormat;
		Ereceipts = ereceipts;
	}

	public String getDrugName() {
		return drugName;
	}

	public void setDrugName(String drugName) {
		this.drugName = drugName;
	}

	
	
	public FormatDrug getDrugFormat() {
		return drugFormat;
	}

	public void setDrugFormat(FormatDrug drugFormat) {
		this.drugFormat = drugFormat;
	}

	public List<UnspecifiedDTO<EreceiptWithPharmacyDTO>> getEreceipts() {
		return Ereceipts;
	}

	public void setEreceipts(List<UnspecifiedDTO<EreceiptWithPharmacyDTO>> ereceipts) {
		Ereceipts = ereceipts;
	}
	
	
	
	
	
	
}
