import React, { Component } from "react";
import Axios from "axios";
import Header from './Header';
import PharmacyLogoPicture from "../Images/pharmacyLogo.jpg" ;
import GetAuthorisation from "../Funciton/GetAuthorisation";
import CreateComplaintModal from "../Components/CreateComplaintModal";
import ModalDialog from './ModalDialog';
const API_URL="http://localhost:8080";

class Pharmacies extends Component {
	
  
    
    state = {
	
        allPharmacies: [],
        subscribedPharmacies: [],
        pharmacySearchName: "",
        searchCountryName: "",
        searchStreetName: "",
        searchCityName: "",
        showSearchedForm: false,
        showResetSearced: false,
        inputError : "none",
        pharmacyID : "",
        text : "",
        pharmacyName:"",
        patientEmail:"",
        showComplaintModal:false,
        openModal:false
        
      
		


  };

  hasRole = (requestRole) => {
    let currentRoles = JSON.parse(localStorage.getItem("keyRole"));

    if (currentRoles === null) return false;


    for (let currentRole of currentRoles) {
      if (currentRole === requestRole) return true;
    }
    return false;
  };


  

  componentDidMount() {

    
		

		Axios.get(API_URL + "/pharmacy/allPharmacies")

			.then((res) => {
				this.setState({ allPharmacies: res.data });
			})
			.catch((err) => {
				console.log(err);
			});

  if(this.hasRole("ROLE_PATIENT")){
    Axios.get("http://localhost:8080/pharmacy/allPatientsSubscribedPharmacies", {
    
     
    validateStatus: () => true, headers: { Authorization: GetAuthorisation() }
     })

     .then((res) => {
      this.setState({ subscribedPharmacies: res.data });
    })
    .catch((err) => {
      console.log(err);
    });
  }

  Axios.get("http://localhost:8080/users/patient", { validateStatus: () => true, headers: { Authorization : GetAuthorisation()} })
				.then((res) => {
					console.log(res.data);
              this.setState({
              patientEmail: res.data.EntityDTO.email,
						});
					
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

  handleSubscribe = (pharmacy) => {
		console.log(pharmacy);
    
		

    Axios.get("http://localhost:8080/users/subscribeToPharmacy/", {
    
      params: {
        pharmacyId: pharmacy.Id
    },
    validateStatus: () => true, headers: { Authorization: GetAuthorisation() }
     })
    .then((res) => {
        
      Axios.get("http://localhost:8080/pharmacy/allPatientsSubscribedPharmacies", {
    
     
              validateStatus: () => true, headers: { Authorization: GetAuthorisation() }
              })

              .then((res) => {
                this.setState({ subscribedPharmacies: res.data });
              })
              .catch((err) => {
                console.log(err);
              });
        
    })

	};


  handleUnsubscribe = (pharmacy) => {
		console.log(pharmacy);
    
		

    Axios.get("http://localhost:8080/users/unsubscribeFromPharmacy/", {
    
      params: {
        pharmacyId: pharmacy.Id
    },
    validateStatus: () => true, headers: { Authorization: GetAuthorisation() }
     })
    .then((res) => {

      Axios.get("http://localhost:8080/pharmacy/allPatientsSubscribedPharmacies", {
    
     
          validateStatus: () => true, headers: { Authorization: GetAuthorisation() }
          })

          .then((res) => {
            this.setState({ subscribedPharmacies: res.data });
          })
          .catch((err) => {
            console.log(err);
          });
        
        
        
    })

	};

  

  isSubscribed = (pharmacy) => {
		console.log(pharmacy);
    
		for (const [index, value] of this.state.subscribedPharmacies.entries()) {

      if(this.state.subscribedPharmacies[index].Id===pharmacy.Id){
        console.log("usao");
        return true;
      }

    }

    return false;

     

	};

  handleComplaintClick = (pharmacy) => {
		console.log(pharmacy);
		
				
					this.setState({
						pharmacyID: pharmacy.Id,
            pharmacyName: pharmacy.EntityDTO.name,
            showComplaintModal: true,
					});
				
			
			
	};

  handleComplaintChange = (event) => {
		this.setState({ text: event.target.value });
	};

  handleComplaintModalClose = () => {
		this.setState({ showComplaintModal: false });
	};

  handleComaplaint = () => {
		let ComplaintPharmacyDTO = {
			pharmacyId: this.state.pharmacyID,
			patientEmail: this.state.patientEmail,
			Date: new Date(),
			text: this.state.text,
			reply: "",
		};

		Axios.post("http://localhost:8080/complaint/pharmacy", ComplaintPharmacyDTO, { validateStatus: () => true, headers: { Authorization: GetAuthorisation() } })
			.then((resp) => {
				if (resp.status === 500) {
          this.setState({ hiddenFailAlert: false, failHeader: "Internal server error", failMessage: "Server error." });
        }else if(resp.status === 403){

          this.setState({
            openModal: true,
          });
					
				} else if (resp.status === 201) {
					
					
				}
				this.setState({ showComplaintModal: false });
			})
			.catch((err) => {
				console.log(err);
			});
	};

  handleModalCloseCantUse= () => {
		this.setState({ 
			openModal: false,
            
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
                    <table className="table" style={{ width: "100%", marginTop: "5em", marginLeft: "auto",marginRight: "auto" }}>
                        
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


                                        <div>
                                        <b>Pharmacy grade: </b> {pharmacy.EntityDTO.grade} {" "} 
                                        <i className="icon-star" style={{ color: "yellow"}}></i>
                                        
										                    </div>



                                      </td>
                                    
                                      <td className="align-middle"hidden={this.isSubscribed(pharmacy) || !this.hasRole("ROLE_PATIENT")}>
                                         
                                          <div style={{ marginLeft: "35%" }}>
                                            <button
                                              style={{ background: "#24a0ed" }}
                                              type="button"
                                              onClick={() => this.handleSubscribe(pharmacy)}
                                              className="btn btn-outline-secondary btn-block"
                                            >
                                              Subscribe
                                            </button>
                                          </div>
                                      
                                    </td>


                                    <td className="align-middle"hidden={!this.isSubscribed(pharmacy) || !this.hasRole("ROLE_PATIENT")}>
                                          <div style={{ marginLeft: "35%" }}>
                                            <button
                                              style={{ background: "#24a0ed" }}
                                              
                                              type="button"
                                              onClick={() => this.handleUnsubscribe(pharmacy)}
                                              className="btn btn-outline-secondary btn-block"
                                            >
                                              Unsubscribe
                                            </button>
                                          </div>
                                      
                                    </td>


                                    <td className="align-middle" hidden={!this.hasRole("ROLE_PATIENT")}>
                                          <div style={{ marginLeft: "45%" }}>
                                            <button
                                              style={{ background: "#781616" }}
                                              
                                              type="button"
                                              onClick={() => this.handleComplaintClick(pharmacy)}
                                              className="btn btn-outline-secondary btn-block"
                                            >
                                              Report
                                            </button>
                                          </div>
                                      
                                    </td>
                                     


                                    </tr>
                                ))
                            }
                        </tbody>
                    </table>
                </div>
        </div>

        <CreateComplaintModal
					buttonName="Send complaint"
					header="Give complaint"
					handleComplaintChange={this.handleComplaintChange}
					show={this.state.showComplaintModal}
					onCloseModal={this.handleComplaintModalClose}
					giveFeedback={this.handleComaplaint}
					name={this.state.StaffName + " " + this.state.StaffSurame}
					forWho="consultant"
					handleClickIcon={this.handleClickIcon}
					complaint={this.state.complaint}
				/>
        <ModalDialog
				show={this.state.openModal}
				onCloseModal={this.handleModalCloseCantUse}
				header="Error"
				text="You can't make a complaint on this pharmacy because you never interacted with it."
			/>
        </React.Fragment>
        
		);
	}
}

export default Pharmacies;
