package show.isaBack.model;

import javax.persistence.Column;

public class Address {

	
	 	@Column(name = "city" , nullable = false)
		private String city;
	    
	    @Column(name = "street" , nullable = false)
	   	private String street;
		
	    @Column(name = "country" , nullable = false)
		private String country;
	    
		
	    @Column(name = "postCode" , nullable = false)
		private String postCode;
	    
	    @Column(name = "latitude", nullable=false)
		private double latitude;
		
	    @Column(name = "longitude" , nullable=false)
		private double longitude;

	    
	    public Address() {
			super();
			
		}
	    
		public Address(String city, String street, String country, String postCode,double latitude, double longitude) {
			super();
			this.city = city;
			this.street = street;
			this.country = country;
			this.postCode = postCode;
			this.latitude = latitude;
			this.longitude=longitude;
			
		}

		public String getCity() {
			return city;
		}

		public void setCity(String city) {
			this.city = city;
		}

		public String getStreet() {
			return street;
		}

		public void setStreet(String street) {
			this.street = street;
		}

		public String getCountry() {
			return country;
		}

		public void setCountry(String country) {
			this.country = country;
		}

		public String getPostCode() {
			return postCode;
		}

		public void setPostCode(String postCode) {
			this.postCode = postCode;
		}
		
		public double getLatitude() {
			return latitude;
		}

		public void setLatitude(double latitude) {
			this.latitude = latitude;
		}

		public double getLongitude() {
			return longitude;
		}

		public void setLongitude(double longitude) {
			this.longitude = longitude;
		}
	    
	    
	
}
