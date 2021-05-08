import React, { Component } from "react";
import Axios from "axios";
import Header from './Header';
import PharmacyLogoPicture from "../Images/pharmacyLogo.jpg" ;
const API_URL="http://localhost:8080";

class Pharmacies extends Component {
	
  
    
    state = {
	
        allPharmacies: [],
        pharmacySearchName: "",
        searchCountryName: "",
        searchStreetName: "",
        searchCityName: "",
        showSearchedForm: false,
        showResetSearced: false,
        inputError : "none"
		


  };


  

  componentDidMount() {
		

		Axios.get(API_URL + "/pharmacy/allPharmacies")

			.then((res) => {
				this.setState({ allPharmacies: res.data });
			})
			.catch((err) => {
				console.log(err);
			});


  
         
	}


   handleSearchForm = () => {    
   
    if(this.state.inputError=== "initial")
      this.setState({inputError :"none"});
      
   
    this.setState({showSearchedForm : !this.state.showSearchedForm,
                    showResetSearced: false});
          
                    if(this.state.showSearchedForm===false){

                      this.setState({
    
                        showResetSearced: true,
                        pharmacySearchName: "",
                        searchCountryName: "",
                        searchStreetName: "",
                        searchCityName: "",
                        inputError : "none"     
                      
                      });

                    }

                    
   }

   handlePharmacySearchNameChange = (event) => {
		this.setState({ pharmacySearchName: event.target.value });
	};

  handleSearchCountryNameChange= (event) => {
		this.setState({ searchCountryName: event.target.value });
	};

  handleSearchStreetNameChange= (event) => {
		this.setState({ searchStreetName: event.target.value });
	};

  
  handleSearchCityNameChange= (event) => {
		this.setState({ searchCityName: event.target.value });
	};



    SearchPharmacies = () => {


    if (this.state.pharmacySearchName === "" && this.state.searchCityName === "" 
          &&  this.state.searchCountryName=== "" &&  this.state.searchStreetName=== "" ) {
          this.setState({ inputError : "initial" });
             return false;
    } 
  
    this.setState({ inputError : "none" });
  
      const searchDTO = {
  
        name : this.state.pharmacySearchName,
        street : this.state.searchStreetName,
        city: this.state.searchCityName,
        country : this.state.searchCountryName,
  
  
       };

      

      Axios.post(API_URL + "/pharmacy/searchPharmacies", searchDTO)
  
      .then((res) => {
        this.setState({
          allPharmacies: res.data,
          showResetSearced : true,
          showSearchedForm : false,      
        
        });
       
      })
      .catch((err) => {
        console.log(err);
        
      });
  
      
    };





    resetSearch = () => {

   
    this.setState({
    
     showResetSearced : false,
     showSearchedForm : false, 
     pharmacySearchName: "",
     searchCountryName: "",
     searchStreetName: "",
     searchCityName: "",
     inputError : "none"     
   
   });

          
    Axios.get(API_URL + "/pharmacy/allPharmacies")

    .then((res) => {
      this.setState({ allPharmacies
        : res.data });
    })
    .catch((err) => {
      console.log(err);
    });


		
	};


   

 


	render() {
	

		return (
      
      <React.Fragment>

      <Header/>
      
      <div id="allPharmacies">

            
           
           
            <div style={{ width: "70%", marginTop: "5em", marginLeft: "auto",marginRight: "auto" }} width="100%">

            <div className="inline" >
            <button type="button" class="btn btn-primary btn-lg mr-3" onClick={this.handleSearchForm}>
               
                {this.state.showSearchedForm ? "Close search" : "Open search"}
                                
            </button>

            <button hidden={!this.state.showResetSearced} type="button" class="btn btn-outline-primary btn-lg mr-3" onClick={this.resetSearch}>
               
               Reset search
                               
           </button>

            </div>




            <form hidden={!this.state.showSearchedForm} className="form-inline" width="100%" id="searchForm">
						
              
              <input
								placeholder="Pharmacy name"
                class="form-control mr-2"
								type="text"
								onChange={this.handlePharmacySearchNameChange}
								value={this.state.pharmacySearchName}
							/>             
              
             
              
              <input
								placeholder="Street"
                class="form-control mr-2"
								type="text"
								onChange={this.handleSearchStreetNameChange}
								value={this.state.searchStreetName}
							/>

              <input
								placeholder="City"
                class="form-control mr-2"
								type="text"
								onChange={this.handleSearchCityNameChange}
								value={this.state.searchCityName}
							/>
					  	

              <input
								placeholder="Country"
                class="form-control mr-2"
								type="text"
								onChange={this. handleSearchCountryNameChange}
								value={this.state.searchCountryName}
							/>
              
              
							<button								
								onClick={this.SearchPharmacies}
								className="btn btn-outline-primary btn-lg "
								type="button"
                
							>								
								Search
							</button>
             

					</form>

          <div className="text-danger" style={{ display: this.state.inputError, fontSize: "17px"}}>
										Enter something in field!
					</div>
          </div>




        <div className="container">
                    <h1 >All pharmacies</h1>
                    <table className="table" style={{ width: "70%", marginTop: "5em", marginLeft: "auto",marginRight: "auto" }}>
                        
                        <tbody>
                            {
                                this.state.allPharmacies.map((pharmacy) => (
                                    <tr key={pharmacy.Id} id={pharmacy.Id} >
                                       

                                      <td width="100px">  
                                       
                                        <img src={PharmacyLogoPicture} width="70px"></img>                                 
                                     
                                      </td>


                                      <td>
                                        
                                        <div>  
                                        <b>Name: </b>{pharmacy.EntityDTO.name}
                                        </div>  

                                        <div>  
                                        <b>Location: </b> {pharmacy.EntityDTO.address.street}, {" "} {pharmacy.EntityDTO.address.city},{" "}
										                                      {pharmacy.EntityDTO.address.country}
                                        </div> 

                                        <div>  
                                        <b>Description: </b>{pharmacy.EntityDTO.description}
                                        </div>

                                      </td>


                                    </tr>
                                ))
                            }
                        </tbody>
                    </table>
                </div>
        </div>
        </React.Fragment>
        
		);
	}
}

export default Pharmacies;
