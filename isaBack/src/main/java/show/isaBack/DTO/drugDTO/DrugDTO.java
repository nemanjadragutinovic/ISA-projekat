package show.isaBack.DTO.drugDTO;



import show.isaBack.model.Drug;

public class DrugDTO {
	
		
		   
	    private String name;	   
	    private String producerName;	    
	    private String fabricCode;
		
	        
	    
	    public DrugDTO() {
			super();
		}
	    
	    public DrugDTO(Drug drug) {
			super();
						
			this.name = drug.getName();
			this.producerName = drug.getProducerName();
			this.fabricCode = drug.getFabricCode();
			
		}
	    
	    
		public DrugDTO(String name, String producerName, String fabricCode) {
			super();
	
			this.name = name;
			this.producerName = producerName;
			this.fabricCode = fabricCode;
		}
		
		
		
		
		public String getName() {
			return name;
		}
		
		public void setName(String name) {
			this.name = name;
		}
		
		
		public String getProducerName() {
			return producerName;
		}
		
		
		public void setProducerName(String producerName) {
			this.producerName = producerName;
		}
		
		
		public String getFabricCode() {
			return fabricCode;
		}
		
		
		public void setFabricCode(String fabricCode) {
			this.fabricCode = fabricCode;
		}
	    
	    

}
