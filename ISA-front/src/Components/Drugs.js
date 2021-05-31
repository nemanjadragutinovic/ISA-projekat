import React, { Component } from "react";
import Axios from "axios";
import Header from './Header';
import MedicamentPicture from "../Images/medicament.jpg" ;
import DrugSpecificationModal from "./DrugSpecification";
import GetAuthorisation from "../../src/Funciton/GetAuthorisation"
import PharmaciesWithDrugAndPrice from '../Components/Pharmacies/PharmaciesWithDrugAndPrice';
import ReservationDrugModal from "./Modal/ReservationDrugsModal";

const API_URL="http://localhost:8080";

class Drugs extends Component {
	
  
    
    state = {
      drugs: [],
      specificationModalShow: false,
      ingredients: [],
      replacingDrugs: [],
      drugGrades: [],
      newGrades: [],
      drugAmount: "",
      drugQuantity: "",
      drugManufacturer: "",
      drugName: "",
      onReciept: false,
      drugKind: "",
      drugFormat: "",
      sideEffects: "",
      points: "",
      formShowed: false,
      searchName: "",
      searchGradeFrom: "",
      searchGradeTo: "",
      drugKinds: [],
      showFeedbackModal: false,
      showModifyFeedbackModal: false,
      selectedDrugId: "",
      drugNameModal: "",
      grade: 1,
      hiddenFailAlert: true,
      failHeader: "",
      failMessage: "",
      hiddenSuccessAlert: true,
      successHeader: "",
      successMessage: "",
      loggedPatient: false,
      unauthorizedRedirect: false,
	  pharmacies: [],
	  showPharmaciesPage : false,
		
	  showReservationDialog : false,
	  pharmacyId : "",
	  maxCount : "",
	  price : 0 ,
	  drugId : "",	
	  hiddenErrorAlert: true,
	  errorHeader : "",
	  errorMessage : "",

	  hiddenSuccessfulAlert : true,
	  SuccessfulHeader : "",
	  SuccessfulMessage : ""
	  


  };


  

  componentDidMount() {
		

		this.state.loggedPatient= this.hasRole("ROLE_PATIENT");



		Axios.get("http://localhost:8080/drug/getDrugsWithGrade")

			.then((res) => {
				this.setState({ drugs: res.data });
			})
			.catch((err) => {
				console.log(err);
			});

		Axios.get("http://localhost:8080/drug/drugkind")
			.then((res) => {
				this.setState({
					drugKinds: res.data,
				});
				console.log(res.data);
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

  handleDrugKindChange = (event) => {
		this.setState({ drugKind: event.target.value });
	};

	hangleFormToogle = () => {
		this.setState({ formShowed: !this.state.formShowed });
	};

	handleNameChange = (event) => {
		this.setState({ searchName: event.target.value });
	};

	handleGradeFromChange = (event) => {
		this.setState({ searchGradeFrom: event.target.value });
	};

	handleGradeToChange = (event) => {
		this.setState({ searchGradeTo: event.target.value });
	};

  handleResetSearch = () => {
		Axios.get("http://localhost:8080/drug/getDrugsWithGrade")

			.then((res) => {
				this.setState({
					drugs: res.data,
					formShowed: false,
					showingSearched: false,
					searchName: "",
					searchGradeFrom: "",
					searchGradeTo: "",
					drugKind: "",
				});
			})
			.catch((err) => {
				console.log(err);
			});

		Axios.get("http://localhost:8080/drug/drugkind")
			.then((res) => {
				this.setState({
					drugKinds: res.data,
				});
				console.log(res.data);
			})
			.catch((err) => {
				console.log(err);
			});
	};


  handleSearchClick = () => {
		{
			let gradeFrom = this.state.searchGradeFrom;
			let gradeTo = this.state.searchGradeTo;
			let name = this.state.searchName;
			let drugKind = this.state.drugKind;

			if (gradeFrom === "") gradeFrom = -1;
			if (gradeTo === "") gradeTo = -1;
			if (name === "") name = "";
			if (drugKind === "") drugKind = "";

			Axios.get("http://localhost:8080/drug/searchDrugs", {
				params: {
					name: name,
					gradeFrom: gradeFrom,
					gradeTo: gradeTo,
					drugKind: drugKind,
				},
			})
				.then((res) => {
					this.setState({
						drugs: res.data,
						formShowed: false,
						showingSearched: true,
					});
				})
				.catch((err) => {
					console.log(err);
				});
		}
	};


 

  handleModalClose = () => {
		this.setState({ specificationModalShow: false });
	};

  handleCloseAlertFail = () => {
		this.setState({ hiddenFailAlert: true });
	};

	handleCloseAlertSuccess = () => {
		this.setState({ hiddenSuccessAlert: true });
	};

	handleClickIcon = (grade) => {
		this.setState({ grade });
	};

	handleCloseSuccessfulAlert = () => {
		this.setState({ hiddenSuccessfulAlert: true });
	};

	
	handleDrugClick = (drug) => {
		console.log(drug);
		this.setState({
			drugAmount: drug.EntityDTO.recommendedAmount,
			drugQuantity: drug.EntityDTO.quantity,
			drugManufacturer: drug.EntityDTO.manufacturer.EntityDTO.name,
			drugName: drug.EntityDTO.name,
			drugKind: drug.EntityDTO.drugKind,
			drugFormat: drug.EntityDTO.drugFormat,
			sideEffects: drug.EntityDTO.sideEffects,
			onReciept: drug.EntityDTO.onReciept,
			points: drug.EntityDTO.loyalityPoints,
			ingredients: drug.EntityDTO.ingredients,
			replacingDrugs: drug.EntityDTO.replacingDrugs,
			specificationModalShow: true,
		});
	};
	

	handleOnDrugSelect = (selectedDrugId) => {
		
		if (this.hasRole("ROLE_PATIENT")) {
			
		console.log("idemoo")
		
		this.setState({ drugId: selectedDrugId });


		Axios.get(API_URL + "/pharmacy/findPharmaciesByDrugIdWithDrugPrice/" + selectedDrugId,{
			validateStatus: () => true,
			headers: { Authorization: GetAuthorisation() },
		})
			.then((res) => {
				
                if (res.status === 401) {
                    this.props.history.push('/login');
                }else{
                    this.setState({ pharmacies: res.data,
									showPharmaciesPage : true});
                }

				this.refreshPharmacies();

				console.log(this.state.pharmacies);
                
			})
			.catch((err) => {
				console.log(err);
			});


	}

	};


	backToDrugsPage = () => {
		this.setState({ showPharmaciesPage : false,
						pharmacies : [] });
	};



	openReservationDialog = (pharmacy) => {
		
		this.setState({ showReservationDialog : true,
						pharmacyId : pharmacy.Id,
						maxCount : pharmacy.availableDrugCount,
						price : pharmacy.drugPrice});


			console.log(pharmacy.Id + "id " +pharmacy.availableDrugCount+ "kolicina " + pharmacy.drugPrice + "cena " );

	};
	
	closeReservationDrugModal = () => {
		this.setState({ showReservationDialog : false,
			hiddenErrorAlert : true,
			errorHeader	: "",
			errorMessage : "" });
	};
	
	handleCloseErrorAlert = () => {
		this.setState({hiddenErrorAlert : true,
			errorHeader	: "",
			errorMessage : ""			
						});
	};
	

	reserveDrugs = (drugCount, endDate, forwardedPrice) => {
		this.setState({hiddenErrorAlert : true,
			errorHeader	: "",
			errorMessage : ""			
						});

		
		let DrugReservationDTO = {
			drugId: this.state.drugId,
			pharmacyId: this.state.pharmacyId,
			drugsCount: drugCount,
			priceForOneDrug: forwardedPrice,
			endDate: endDate,
		};

		

		console.log(DrugReservationDTO)

		Axios.post(API_URL + "/drug/reserveDrug", DrugReservationDTO, 
		{ validateStatus: () => true, headers: { Authorization: GetAuthorisation() } })
			.then((res) => {
				if (res.status === 400) {
					this.setState({ hiddenErrorAlert: false, errorHeader: "Bad request", errorMessage: res.data });
				}else if (res.status === 401) {
						this.props.history.push('/login');	
				} else if (res.status === 500) {
					this.setState({ hiddenErrorAlert: false, errorHeader: "Internal server error", errorMessage: "Server error." });
				} else if (res.status === 201) {
					console.log(res.data);
					this.setState({ showReservationDialog: false,
						hiddenSuccessfulAlert : false,
						SuccessfulHeader : "Successful reservation ",
						SuccessfulMessage : "You reserved drugs " });
				}

				
				this.handleOnDrugSelect(this.state.drugId)
			})
			.catch((err) => {
				this.setState({ hiddenErrorAlert: false, errorHeader: "Internal server error", errorMessage: "Some error" });
				console.log(err);
			});			
	};

	
		refreshPharmacies () {

			let newPharmaciesList= [];
			for (let pharmacy of this.state.pharmacies) {
			  if (pharmacy.availableDrugCount !== 0) 
			  		newPharmaciesList.push(pharmacy)
			}
		
			this.setState({
				pharmacies : newPharmaciesList,
			});
		
		  }
	

	render() {
	

		return (
      
      <React.Fragment>

      <Header/>
      
      <div id="allDrugs" hidden={this.state.showPharmaciesPage}>

            
           
           
            <div style={{ width: "70%", marginTop: "5em", marginLeft: "auto",marginRight: "auto" }} width="100%">

            <h5 className=" text-center mb-0 mt-2 text-uppercase">Drugs</h5>
					<button className="btn btn-outline-primary btn-xl" type="button" onClick={this.hangleFormToogle}>
						<i className="icofont-rounded-down mr-1"></i>
						Search drugs
					</button>

            <form className={this.state.formShowed ? "form-inline mt-3" : "form-inline mt-3 collapse"} width="100%" id="formCollapse">
						<div className="form-group mb-2" width="100%">
							<input
								placeholder="Name"
								className="form-control mr-3"
								style={{ width: "9em" }}
								type="text"
								onChange={this.handleNameChange}
								value={this.state.searchName}
							/>
              <select
								placeholder="Drug kind"
								onChange={this.handleDrugKindChange}
								style={{ width: "9em" }}
								className="form-control mr-3"
							>
								<option value="" selected disabled>
									Drug Kind
								</option>
								{this.state.drugKinds.map((kind) => (
									<option value={kind.EntityDTO.type}>{kind.EntityDTO.type}</option>
								))}
							</select>

							<input
								placeholder="Grade from"
								className="form-control mr-3"
								style={{ width: "9em" }}
								type="number"
								min="0"
								max="5"
								onChange={this.handleGradeFromChange}
								value={this.state.searchGradeFrom}
							/>
							<input
								placeholder="Grade to"
								className="form-control mr-3"
								style={{ width: "9em" }}
								type="number"
								min="0"
								max="5"
								onChange={this.handleGradeToChange}
								value={this.state.searchGradeTo}
							/>
							
							<button
								style={{ background: "#1977cc" }}
								onClick={this.handleSearchClick}
								className="btn btn-primary btn-x2"
								type="button"
							>
								<i className="icofont-search mr-1"></i>
								Search
							</button>
						</div>
					</form>

          <div className={this.state.showingSearched ? "form-group mt-2" : "form-group mt-2 collapse"}>
						<button type="button" className="btn btn-outline-secondary" onClick={this.handleResetSearch}>
							<i className="icofont-close-line mr-1"></i>Reset
						</button>
					</div>
		

		
			<div hidden={!this.hasRole("ROLE_PATIENT")} style={{  marginTop: "3em" }} >
				<b><g1 >Click on drug to reserve it!</g1></b>
			</div>
			


          <table className={this.state.loggedPatient === true ? "table table-hover" : "table"} style={{ width: "100%", marginTop: "3rem" }}>
						<tbody>
							{this.state.drugs.map((drug) => (
								<tr id={drug.Id} key={drug.Id} style={this.state.loggedPatient === true ? { cursor: "pointer" } : {}}>
									<td width="130em">
										<img className="img-fluid" src={MedicamentPicture} width="70em" />
									</td>
									<td  onClick={() => this.handleOnDrugSelect(drug.Id)}>
										<div>
											<b>Name:</b> {drug.EntityDTO.drugInstanceName}
										</div>
										<div>
											<b>Kind:</b> {drug.EntityDTO.drugKind}
										</div>
										<div>
											<b>Grade:</b> {drug.EntityDTO.avgGrade}
											<i className="icon-star" style={{ color: "yellow"}}></i>
										</div>
									</td>
									<td className="align-middle">
										<div style={{ marginLeft: "55%" }}>
											<button
												type="button"
												onClick={() => this.handleDrugClick(drug)}
												className="btn btn-outline-secondary btn-block"
											>
												Specification
											</button>
										</div>
										
									</td>
								</tr>
							))}
						</tbody>
					</table>
             

					

          
          </div>




        
        </div>
        <DrugSpecificationModal
						onCloseModal={this.handleModalClose}
						drugAmount={this.state.drugAmount}
						header="Drug specification"
						show={this.state.specificationModalShow}
						drugQuantity={this.state.drugQuantity}
						drugKind={this.state.drugKind}
						drugFormat={this.state.drugFormat}
						drugManufacturer={this.state.drugManufacturer}
						drugName={this.state.drugName}
						onReciept={this.state.onReciept}
						sideEffects={this.state.sideEffects}
						points={this.state.points}
						ingredients={this.state.ingredients}
						replacingDrugs={this.state.replacingDrugs}
					/>
       
	   
	   	<PharmaciesWithDrugAndPrice


					pharmacies= {this.state.pharmacies}
					show={this.state.showPharmaciesPage}			
					back={this.backToDrugsPage}	
					openReservationDialog={this.openReservationDialog}		
					
					hiddenSuccessfulAlert={this.state.hiddenSuccessfulAlert}
					SuccessfulHeader={this.state.SuccessfulHeader}
					SuccessfulMessage={this.state.SuccessfulMessage}
					handleCloseSuccessfulAlert={this.handleCloseSuccessfulAlert}			

		   />

			<ReservationDrugModal


			
			show={this.state.showReservationDialog}			
			maxCount={this.state.maxCount}
			drugPrice={this.state.price}		
			closeModal={this.closeReservationDrugModal}					
			reserveDrugs= {this.reserveDrugs}					
			hiddenErrorAlert={this.state.hiddenErrorAlert}						
			errorHeader={this.state.errorHeader}	
			errorMessage={this.state.errorMessage}	
			handleCloseErrorAlert={this.handleCloseErrorAlert}						

			/>					



   
	   
	    </React.Fragment>
        

		);
	}
}

export default Drugs;
