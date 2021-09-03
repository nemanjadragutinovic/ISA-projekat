import React, { Component } from "react";
import Axios from "axios";
import Header from './Header';
import PharmacyLogoPicture from "../Images/pharmacyLogo.jpg" ;
import GetAuthorisation from "../Funciton/GetAuthorisation";
import UnsuccessfulAlert from "../Components/Alerts/UnsuccessfulAlert";
import SuccessfulAlert from "../Components/Alerts/SuccessfulAlert";
import FirstGradeModal from "../Components/Modal/FirstGradeModal";
import CreateComplaintModal from "../Components/CreateComplaintModal";
import ModalDialog from './ModalDialog';
import { Redirect } from "react-router-dom";
import "../App.js";
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
        openModal:false,
        
      
        redirect: false,
        redirectUrl: "",
        selectedPharmacy : [],
        pharmacyId : "",
        pharmacyGrade : 0,
        pharmacyName : "",
        showGradeModal: false,
        showFirstGrade : false,
        showModifyGrade : false,
        maxGrade : 5,


        hiddenSuccessfulAlert : true,
        SuccessfulHeader : "",
        SuccessfulMessage : "",
        hiddenUnsuccessfulAlert: true,
        UnsuccessfulHeader: "",
        UnsuccessfulMessage: "",

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


  hasRole = (requestRole) => {
    let currentRoles = JSON.parse(localStorage.getItem("keyRole"));

    if (currentRoles === null) return false;


    for (let currentRole of currentRoles) {
      if (currentRole === requestRole) return true;
    }
    return false;
  };


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


  handleCloseSuccessfulAlert = () => {
		this.setState({ hiddenSuccessfulAlert: true });
  }



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


	handleCloseUnsuccessfulAlert = () => {
		this.setState({ hiddenUnsuccessfulAlert: true });
	};

  handleGetGradeClick = (pharmacy) => {
    console.log(pharmacy);


    
	
			Axios.get(API_URL + "/grade/pharmacy/" + pharmacy.Id , {
				validateStatus: () => true,
				headers: { Authorization: GetAuthorisation() },
			})
				.then((res) => {
					if (res.status === 401) {
						this.props.history.push('/login');
					} else if(res.status === 404){
	
	
						console.log("Nema ocenu");
	
						let entityDTO = {
							showGradeModal : true,
								showFirstGrade : true,	
								pharmacyId  : pharmacy.Id,
								pharmacyGrade  : pharmacy.EntityDTO.grade,
								pharmacyName  : pharmacy.EntityDTO.name,
							
						};
	
	
						this.setState({ showGradeModal : true,
								showFirstGrade : true,	
								pharmacyId  : pharmacy.Id,
								pharmacyGrade  : pharmacy.EntityDTO.grade,
								pharmacyName  : pharmacy.EntityDTO.name,
							});
	
							console.log(pharmacy.Id);
							console.log(entityDTO);
	
					}else {
						
						console.log(res.data);

						this.setState({ showGradeModal : true,
							showModifyGrade : true,	
							pharmacyId : pharmacy.Id,
							pharmacyGrade : res.data.grade,
							pharmacyName : pharmacy.EntityDTO.name
							});
	
							console.log(res.data.grade);
							console.log(res.data);
						
					}
				})
				.catch((err) => {
					console.log(err);
				});

    


  };

 
  setFirstGrade = (grade) => {
		
    console.log("sou")
    

    let entityDTO = {
      pharmacyId : this.state.pharmacyId  ,
      grade: grade,
    };

    console.log(entityDTO);

    Axios.post(API_URL + "/grade/pharmacy/createGrade",entityDTO , {
      validateStatus: () => true,
      headers: { Authorization: GetAuthorisation() },
    })
      .then((res) => {
        if (res.status === 401) {
          this.props.history.push('/login');
        } else if(res.status === 404){
          console.log(res)
          this.setState({ hiddenUnsuccessfulAlert: false, UnsuccessfulHeader : "Bad request", UnsuccessfulMessage : res.data,
           showGradeModal :false,
           showFirstGrade : false,
          showModifyGrade : false});

        }else if(res.status === 500){
          this.setState({ hiddenUnsuccessfulAlert: false, UnsuccessfulHeader : "Error", UnsuccessfulMessage : "internal server error! ",
           showGradeModal :false,
           showFirstGrade : false,
           showModifyGrade : false  });

        }else {
            
          this.setState({ hiddenSuccessfulAlert:  false, successfulHeader:   "Successful", successfulMessage:  "You successful created grade for pharmacy! ",
           showGradeModal :false,
           showFirstGrade : false,
           showModifyGrade : false   });

          this.componentDidMount();
          
        }
      })
      .catch((err) => {
        console.log(err);
      });





  };

  setModifyGrade = (grade) => {
    
    console.log("sou")
	
    let entityDTO = {
      pharmacyId : this.state.pharmacyId  ,
      grade: grade,
    };

    console.log(entityDTO);


    Axios.post(API_URL + "/grade/pharmacy/updateGrade",entityDTO , {
      validateStatus: () => true,
      headers: { Authorization: GetAuthorisation() },
    })
      .then((res) => {
        if (res.status === 401) {
          this.props.history.push('/login');
        } else if(res.status === 404){
          this.setState({ hiddenUnsuccessfulAlert: false, UnsuccessfulHeader : "Bad request", UnsuccessfulMessage : res.data,
           showGradeModal :false,
           showFirstGrade : false,
          showModifyGrade : false});

        }else if(res.status === 500){
          this.setState({ hiddenUnsuccessfulAlert: false, UnsuccessfulHeader : "Error", UnsuccessfulMessage : "internal server error! ",
           showGradeModal :false,
           showFirstGrade : false,
           showModifyGrade : false  });

        }else {
            
          this.setState({ hiddenSuccessfulAlert:  false, successfulHeader:   "Successful", successfulMessage:  "You successful update grade for pharmacy! ",
           showGradeModal :false,
           showFirstGrade : false,
           showModifyGrade : false   });
          
           this.componentDidMount();
          
        }
      })
      .catch((err) => {
        console.log(err);
      });




  };


    closeFirstGradeModal = () => {
    
      this.setState({ showFirstGrade : false,
        showModifyGrade : false,
        showGradeModal : false});

    }


    
    handleSortByNameAscending = () => {
    
      

      Axios.get(API_URL + "/pharmacy/allPharmacies/sortByNameAscending", {
        validateStatus: () => true,
        headers: { Authorization: GetAuthorisation() },
      })
        .then((res) => {
          this.setState({ allPharmacies: res.data });
          console.log(res.data );
        })
        .catch((err) => {
          console.log(err);
        });



    }

    handleSortByNameDescending = () => {
    
      

      Axios.get(API_URL + "/pharmacy/allPharmacies/sortByNameDescending", {
        validateStatus: () => true,
        headers: { Authorization: GetAuthorisation() },
      })
        .then((res) => {
          this.setState({ allPharmacies: res.data });
          console.log(res.data );
        })
        .catch((err) => {
          console.log(err);
        });



    }



    handleSortByCityAscending = () => {
    
      

      Axios.get(API_URL + "/pharmacy/allPharmacies/sortByCityAscending", {
        validateStatus: () => true,
        headers: { Authorization: GetAuthorisation() },
      })
        .then((res) => {
          this.setState({ allPharmacies: res.data });
          console.log(res.data );
        })
        .catch((err) => {
          console.log(err);
        });



    }

    handleSortByCityDescending = () => {
    
      

      Axios.get(API_URL + "/pharmacy/allPharmacies/sortByCityDescending", {
        validateStatus: () => true,
        headers: { Authorization: GetAuthorisation() },
      })
        .then((res) => {
          this.setState({ allPharmacies: res.data });
          console.log(res.data );
        })
        .catch((err) => {
          console.log(err);
        });



    }

    handleClickOnPharmacy = (id) => {
      this.setState({
        redirect: true,
        redirectUrl: "/pharmacy/" + id,
      });
     
    };

    handleSortByGradeAscending = () => {
    
      

      Axios.get(API_URL + "/pharmacy/allPharmacies/sortByGradeAscending", {
        validateStatus: () => true,
        headers: { Authorization: GetAuthorisation() },
      })
        .then((res) => {
          this.setState({ allPharmacies: res.data });
          console.log(res.data );
        })
        .catch((err) => {
          console.log(err);
        });



    }

    handleSortByGradeDescending = () => {
    
      

      Axios.get(API_URL + "/pharmacy/allPharmacies/sortByGradeDescending", {
        validateStatus: () => true,
        headers: { Authorization: GetAuthorisation() },
      })
        .then((res) => {
          this.setState({ allPharmacies: res.data });
          console.log(res.data );
        })
        .catch((err) => {
          console.log(err);
        });



    }



	render() {
    if (this.state.redirect) return <Redirect push to={this.state.redirectUrl} />;

		return (
      
      <React.Fragment>

      <Header/>
      
      <div id="allPharmacies">

            <div className="container" style={{ marginTop: "1em" }} >
                <SuccessfulAlert
                      hidden={this.state.hiddenSuccessfulAlert}
                      header={this.state.successfulHeader}
                      message={this.state.successfulMessage}
                      handleCloseAlert={this.handleCloseSuccessfulAlert}    
                  />
                  <UnsuccessfulAlert
                      hidden={this.state.hiddenUnsuccessfulAlert}
                      header={this.state.UnsuccessfulHeader}
                      message={this.state.UnsuccessfulMessage}
                      handleCloseAlert={this.handleCloseUnsuccessfulAlert}
                  />
            </div>
           
           
            <div className="container"  style={{ width: "70%", marginTop: "5em", marginLeft: "auto",marginRight: "auto" }} width="100%">

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
        
        
        
        
             <div style={{  marginTop: "1em" }} hidden={!this.hasRole("ROLE_PATIENT")}>

                <div className="dropdown">
                  <button className="btn btn-primary btn-lg dropdown-toggle"
                    type="button" id="dropdownMenu2"
                    data-toggle="dropdown" 
                    aria-haspopup="true" 
                    aria-expanded="false">
                    Sort
                  </button>
                  <div className="dropdown-menu" aria-labelledby="dropdownMenu2">
                      <button className="dropdown-item" type="button" onClick={this.handleSortByNameAscending} >Sort by name ascending</button>
                      <button className="dropdown-item" type="button" onClick={this.handleSortByNameDescending} >Sort by name descending</button>
                      <button className="dropdown-item" type="button" onClick={this.handleSortByCityAscending} >Sort by city ascending</button>
                      <button className="dropdown-item" type="button" onClick={this.handleSortByCityDescending} >Sort by city descending</button>
                      <button className="dropdown-item" type="button" onClick={this.handleSortByGradeAscending} >Sort by grade ascending</button>
                      <button className="dropdown-item" type="button" onClick={this.handleSortByGradeDescending} >Sort by grade descending</button>
                  </div>
                </div>

            </div>    
                
        
        
        
        
        
        </div>

         


        <div className="container">
                    <h1 >All pharmacies</h1>
                    <table className="table table-hover" style={{ width: "100%", marginTop: "5em", marginLeft: "auto",marginRight: "auto" }}>
                        
                        <tbody>
                            {
                                this.state.allPharmacies.map((pharmacy) => (
                                  
                                    <tr key={pharmacy.Id} id={pharmacy.Id}  onClick={() => this.handleClickOnPharmacy(pharmacy.Id)}>

                                      
                                       

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
                                          <div style={{ marginLeft: "25%" }}>
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


                                    <td className="align-middle">

                                      <div style={{ marginLeft: "5%"  }}>
                                          <button
                                            type="button"
                                            style={{ background: "#24a0ed" }}
                                            onClick={() => this.handleGetGradeClick(pharmacy)}
                                            hidden={!this.hasRole("ROLE_PATIENT")}
                                            className="btn btn-outline-secondary "
                                          >
                                            Pharmacy grade
                                          </button>
                                      </div>	                                     


                                    </td>


                                    <td className="align-middle" hidden={!this.hasRole("ROLE_PATIENT")}>
                                          <div >
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





              <FirstGradeModal 

                show={this.state.showGradeModal}
                showFirstGrade={this.state.showFirstGrade}
                showModifyGrade={this.state.showModifyGrade}
                employeeGrade={this.state.pharmacyGrade }							
                maxGrade={this.state.maxGrade}
                employeeName={this.state.pharmacyName  }
                employeeSurname={""}
                header={"Grade"}
                buttonFirstGradeName={"Grade"}
                buttonModifyGradeName={" Update grade"}								
                setFirstGrade={this.setFirstGrade}	
                setModifyGrade={this.setModifyGrade}
                onCloseModal={this.closeFirstGradeModal}						

              />





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
