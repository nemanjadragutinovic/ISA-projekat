import React, { Component } from "react";
import Axios from "axios";
import Header from '../Components/Header';
import GetAuthorisation from "../Funciton/GetAuthorisation";
import PharmacyLogoPicture from "../Images/pharmacyLogo.jpg" ;
import UnsuccessfulAlert from "../Components/Alerts/UnsuccessfulAlert";
import SuccessfulAlert from "../Components/Alerts/SuccessfulAlert";
import ChangePasswordModal from "../Components/Modal/ChangePasswordModal";
import AllergensModal from "../Components/Modal/AllergensModal";



const API_URL="http://localhost:8080";

class UserProfile extends Component {

    state = {
		id: "",
		email: "",
        name: "",
		surname: "",
		password: "",
		address: "",
		phoneNumber: "",
		nameError: "none",
		surnameError: "none",
		addressError: "none",
		phoneError: "none",
		phoneValidateError: "none",
		hiddenEditInfo: true,
        redirect: false,
		hiddenSuccessfulAlert: true,
		successfulHeader: "",
		successfulMessage: "",
		hiddenUnsuccessfulAlert: true,
		UnsuccessfulHeader: "",
		UnsuccessfulMessage: "",
		isPasswordModalOpened: false,
		hiddenPasswordErrorAlert: true,
		errorPasswordHeader: "",
		errorPasswordMessage: "",
		emptyOldPasswordError: "none",
		emptyNewPasswordError: "none",
		emptyNewPasswordRepeatedError: "none",
		newPasswordAndRepeatedNotSameError: "none",

		
		userAllergens: [],
		hiddenAllergenSuccessfulAlert: true,
		successfulAllergenHeader: "",
		successfulAllergenMessage: "",
		hiddenAllergenErrorAlert: true,
		errorAllergenHeader: "",
		errorAllergenMessage: "",
		openAllergenModal: false,
		patientPoints : "",
		patientPenalties : "",
		patientLoyaltyCategory : "",
		examinationDiscount : "",
		consultationDiscount : "",
		drugDiscount : "",
		showAnotherInformation: false,
		hideButtonForAnotherInformation : false


	};

	constructor(props) {
		super(props);
	}

    hasRole = (requestRole) => {
        let currentRoles = JSON.parse(localStorage.getItem("keyRole"));
  
        if (currentRoles === null) return false;
  
  
        for (let currentRole of currentRoles) {
          if (currentRole === requestRole) return true;
        }
        return false;
      };

	
    handleEmailChange = (event) => {
		this.setState({ email: event.target.value });
	};

	handleNameChange = (event) => {
		this.setState({ name: event.target.value });
	};

	handleSurnameChange = (event) => {
		this.setState({ surname: event.target.value });
	};

	handlePhoneNumberChange = (event) => {
		this.setState({ phoneNumber: event.target.value });
	};

	handleAddressChange = (event) => {
		this.setState({ address: event.target.value });
	};

	handleCloseSuccessfulAlert = () => {
		this.setState({ hiddenSuccessfulAlert: true });
	};

	handleCloseUnsuccessfulAlert= () => {
		this.setState({ hiddenUnsuccessfulAlert: true });
	};


	handleChangePasswordModalOpen = () => {
		this.setState({ hiddenEditInfo: true, 
			isPasswordModalOpened: true });
	};

	handleChangePasswordModalClose = () => {
		this.setState({ isPasswordModalOpened: false });
	};

	handleCloseAlertPassword = () => {
		this.setState({ hiddenPasswordErrorAlert: true });
	};

	handleAllergenModal = () => {
		this.setState({ hiddenEditInfo: true, openAllergenModal: true });
	};


	
	handleAllergensModalClose = () => {
		this.setState({
			
			hiddenAllergenSuccessfulAlert: true,
			successfulAllergenHeader: "",
			successfulAllergenMessage: "",
			hiddenAllergenErrorAlert: true,
			errorAllergenHeader: "",
			errorAllergenMessage: "",
			openAllergenModal: false,
		});
	};

	handleCloseAllergenAlertError = () => {
		this.setState({ hiddenAllergenErrorAlert: true });
	};

	handleCloseAllergenAlertSuccessful = () => {
		this.setState({ hiddenAllergenSuccessfulAlert: true });
	};


	componentDidMount() {
		if (!this.hasRole("ROLE_PATIENT") && !this.hasRole("ROLE_SUPPLIER") && !this.hasRole("ROLE_PHARMACYADMIN")) {
			this.setState({ redirect: true });
			this.props.history.push('/login')
		
        } else {


			console.log(GetAuthorisation());
			console.log(localStorage.getItem("keyRole"));
			
			if(this.hasRole("ROLE_PATIENT")){
			Axios.get(API_URL + "/users/patient", { validateStatus: () => true, headers: { Authorization : GetAuthorisation()} })
				.then((res) => {
					console.log(res.data);
					if (res.status === 401) {                       
                        this.setState({ redirect: true });				
					} else {

						console.log(res.data.EntityDTO.email)
						console.log(res.data.EntityDTO.name)
						
					
                        this.setState({
							id: res.data.Id,
							email: res.data.EntityDTO.email,
							name: res.data.EntityDTO.name,
							surname: res.data.EntityDTO.surname,
							address: res.data.EntityDTO.address,
							phoneNumber: res.data.EntityDTO.phoneNumber,
							userAllergens: res.data.EntityDTO.allergens,
							
							patientPoints : res.data.EntityDTO.points,
							patientPenalties : res.data.EntityDTO.penalty,
							patientLoyaltyCategory :  res.data.EntityDTO.loyalityProgramForPatientDTO.loyalityCategory,
							examinationDiscount : res.data.EntityDTO.loyalityProgramForPatientDTO.examinationDiscount,
							consultationDiscount : res.data.EntityDTO.loyalityProgramForPatientDTO.consultationDiscount,
							drugDiscount : res.data.EntityDTO.loyalityProgramForPatientDTO.drugDiscount,


							
						});
						
						

						console.log(this.state.userAllergens)
						

					}
				})
				.catch((err) => {
					console.log(err);
					console.log("ovaj eror je u pitanju");

				});

			}else if(this.hasRole("ROLE_SUPPLIER")){

				console.log("usao u supplera 2");

				Axios.get(API_URL + "/users/supplier", { validateStatus: () => true, headers: { Authorization : GetAuthorisation()} })
				.then((res) => {
					console.log(res.data);
					if (res.status === 401) {                       
                        this.setState({ redirect: true });				
					} else {

						//console.log(res.data.EntityDTO.email)
						//console.log(res.data.EntityDTO.name)
						
					
                        this.setState({
							
							email: res.data.email,
							name: res.data.name,
							surname: res.data.surname,
							address: res.data.address,
							phoneNumber: res.data.phoneNumber,
							
							

							
						});
						
						

						

					}
				})
				.catch((err) => {
					console.log(err);
					console.log("ovaj eror je u pitanju");

				});

			}else if(this.hasRole("ROLE_PHARMACYADMIN")){

				console.log("usao admina apoteke");
				Axios.get(API_URL + "/users/phadmin", { validateStatus: () => true, headers: { Authorization : GetAuthorisation()} })
				.then((res) => {
					console.log(res.data);
					if (res.status === 401) {                       
                        this.setState({ redirect: true });				
					} else {

						
                        this.setState({
							
							email: res.data.email,
							name: res.data.name,
							surname: res.data.surname,
							address: res.data.address,
							phoneNumber: res.data.phoneNumber,
							
							

							
						});
						
						

						

					}
				})
				.catch((err) => {
					console.log(err);
					console.log("erradminapoteke");

				});

			}

			

			
		}
	}

	

	handleChangeInfo = () => {
		


		let userDTO = {
			name: this.state.name,
			surname: this.state.surname,
			address: this.state.address,
			phoneNumber: this.state.phoneNumber,
		};



		if (this.validateForm(userDTO)){

			console.log(userDTO.name  )

			if(this.hasRole("ROLE_PATIENT")){
			Axios.put(API_URL + "/users/patient", userDTO, {
				validateStatus: () => true,
				headers: { Authorization: GetAuthorisation() },
			})
				.then((res) => {
					if (res.status === 400) {
						this.setState({ hiddenUnsuccessfulAlert: false,
							UnsuccessfulHeader: "Bad request", 
							UnsuccessfulMessage: "Invalid argument." });

					} else if (res.status === 500) {

						this.setState({ hiddenUnsuccessfulAlert: false, 
							UnsuccessfulHeader: "Internal server error", 
							UnsuccessfulMessage: "Server error." });

					} else if (res.status === 204) {
						console.log("Success");
						this.setState({
							hiddenSuccessfulAlert: false,
							successfulHeader: "Success",
							successfulMessage: "You updated your information.",
							hiddenEditInfo: true,
						});
					}
				})
				.catch((err) => {
					console.log(err);
					this.setState({ hiddenUnsuccessfulAlert: false,
						UnsuccessfulHeader: "Error", 
						UnsuccessfulMessage: "Something was wrong" });
				
				});

			}else if(this.hasRole("ROLE_SUPPLIER")){

				Axios.put(API_URL + "/users/supplier", userDTO, {
					validateStatus: () => true,
					headers: { Authorization: GetAuthorisation() },
				})
					.then((res) => {
						if (res.status === 400) {
							this.setState({ hiddenUnsuccessfulAlert: false,
								UnsuccessfulHeader: "Bad request", 
								UnsuccessfulMessage: "Invalid argument." });
	
						} else if (res.status === 500) {
	
							this.setState({ hiddenUnsuccessfulAlert: false, 
								UnsuccessfulHeader: "Internal server error", 
								UnsuccessfulMessage: "Server error." });
	
						} else if (res.status === 204) {
							console.log("Success");
							this.setState({
								hiddenSuccessfulAlert: false,
								successfulHeader: "Success",
								successfulMessage: "You updated your information.",
								hiddenEditInfo: true,
							});
						}
					})
					.catch((err) => {
						console.log(err);
						this.setState({ hiddenUnsuccessfulAlert: false,
							UnsuccessfulHeader: "Error", 
							UnsuccessfulMessage: "Something was wrong" });
					
					});

			}else if(this.hasRole("ROLE_PHARMACYADMIN")){

				Axios.put(API_URL + "/users/phadmin", userDTO, {
					validateStatus: () => true,
					headers: { Authorization: GetAuthorisation() },
				})
					.then((res) => {
						if (res.status === 400) {
							this.setState({ hiddenUnsuccessfulAlert: false,
								UnsuccessfulHeader: "Bad request", 
								UnsuccessfulMessage: "Invalid argument." });
	
						} else if (res.status === 500) {
	
							this.setState({ hiddenUnsuccessfulAlert: false, 
								UnsuccessfulHeader: "Internal server error", 
								UnsuccessfulMessage: "Server error." });
	
						} else if (res.status === 204) {
							console.log("Success");
							this.setState({
								hiddenSuccessfulAlert: false,
								successfulHeader: "Success",
								successfulMessage: "You updated your information.",
								hiddenEditInfo: true,
							});
						}
					})
					.catch((err) => {
						console.log(err);
						this.setState({ hiddenUnsuccessfulAlert: false,
							UnsuccessfulHeader: "Error", 
							UnsuccessfulMessage: "Something was wrong" });
					
					});

			}

		}

		
	};


	handleEditInfoClick= () => {
		this.setState({
			hiddenEditInfo: false
		});

		
	};

	validateForm = (userDTO) => {
	
		this.setState({
			nameError: "none",
			surnameError: "none",
			addressError: "none",
			phoneError: "none",
			phoneValidateError: "none",
		});

		const regexPhone = /^([+]?[0-9]{3,6}[-]?[\/]?[0-9]{3,5}[-]?[\/]?[0-9]*)$/;
		console.log(regexPhone.test(userDTO.phoneNumber));
		if (userDTO.name === "") {
			this.setState({ nameError: "initial" });
			return false;
		} else if (userDTO.surname === "") {
			this.setState({ surnameError: "initial" });
			return false;
		} else if (userDTO.address=== "") {
			this.setState({ addressError: "initial" });
			return false;
		} else if (userDTO.phoneNumber === "") {
			this.setState({ phoneError: "initial" });
			return false;
		} else if (regexPhone.test(userDTO.phoneNumber) === false) {
			this.setState({ phoneValidateError: "initial" });
			return false;
		}
		return true;
	};




	changePassword = (oldPassword, newPassword, newPasswordRepeated) => {
		console.log(oldPassword, newPassword, newPasswordRepeated);

		this.setState({
			hiddenEditInfo: true,
			hiddenPasswordErrorAlert: true,
			errorPasswordHeader: "",
			errorPasswordMessage: "",
			emptyOldPasswordError: "none",
			emptyNewPasswordError: "none",
			emptyNewPasswordRepeatedError: "none",
			newPasswordAndRepeatedNotSameError: "none",
			hiddenSuccessAlert: true,
			successHeader: "",
			successMessage: "",


		});

		if (oldPassword === "") {
			this.setState({ emptyOldPasswordError: "initial" });
		} else if (newPassword === "") {
			this.setState({ emptyNewPasswordError: "initial" });
		} else if (newPasswordRepeated === "") {
			this.setState({ emptyNewPasswordRepeatedError: "initial" });
		} else if (newPasswordRepeated !== newPassword) {
			this.setState({ newPasswordAndRepeatedNotSameError: "initial" });
		} else {
			let newPasswordDTO= { oldPassword, newPassword };
			Axios.post(API_URL + "/users/changePassword", newPasswordDTO, {
				validateStatus: () => true,
				headers: { Authorization: GetAuthorisation() },
			})
				.then((res) => {
					 if (res.status === 400) {
						this.setState({
							hiddenPasswordErrorAlert: false,
							errorPasswordHeader: "Invalid new password",
							errorPasswordMessage: "Invalid new password.",
						});
					} else if (res.status === 403) {
						this.setState({
							hiddenPasswordErrorAlert: false,
							errorPasswordHeader: "Bad credentials",
							errorPasswordMessage: "You entered wrong old password.",
						});
					} else if (res.status === 500) {
						this.setState({
							hiddenPasswordErrorAlert: false,
							errorPasswordHeader: "Internal server error",
							errorPasswordMessage: "Server error.",
						});
					} else if (res.status === 204) {
						this.setState({
							
							hiddenSuccessfulAlert: false,
							successfulHeader: "Success",
							successfulMessage: "Your password was changed!",
							isPasswordModalOpened: false,
							hiddenEditInfo: true,
						});
					}
					console.log(res);
				})
				.catch((err) => {
					console.log(err);
					this.setState({ hiddenUnsuccessfulAlert: false,
						UnsuccessfulHeader: "Error", 
						UnsuccessfulMessage: "Something was wrong" });
									
				});
		}
	};


	handleAddAllergen = (allergenName) => {
		this.setState({
			hiddenAllergenSuccessfulAlert: true,
			successfulAllergenHeader: "",
			successfulAllergenMessage: "",
			hiddenAllergenErrorAlert: true,
			errorAllergenHeader: "",
			errorAllergenMessage: "",
		});
		console.log(allergenName);
		let patientsAllergenDTO = { allergenName: allergenName, patientId: this.state.id };
		console.log(patientsAllergenDTO);
		
		Axios.post(API_URL + "/users/addPatientsAllergen", patientsAllergenDTO, {
			validateStatus: () => true,
			headers: { Authorization: GetAuthorisation() },
		})
			.then((res) => {
				if (res.status === 400) {
					this.setState({
						hiddenAllergenErrorAlert: false,
						errorAllergenHeader: "Bad request",
						errorAllergenMessage: "Bad request when adding allergen.",
					});
				} else if (res.status === 500) {
					this.setState({
						hiddenAllergenErrorAlert: false,
						errorAllergenHeader: "Internal server error",
						errorAllergenMessage: "Server error.",

					});
				} else if (res.status === 200) {
					
					this.setState({
						
						hiddenAllergenSuccessfulAlert: false,
						successfulAllergenHeader: "Successful",
						successfulAllergenMessage: "Allergen added.",
					});

					this.componentDidMount()
				}
			})
			.catch((err) => {
				console.log(err);
				this.setState({ hiddenUnsuccessfulAlert: false,
					UnsuccessfulHeader: "Error", 
					UnsuccessfulMessage: "Something was wrong" });
			});


	};






	handleRemoveAllergen = (allergenName) => {
		this.setState({
			hiddenAllergenSuccessfulAlert: true,
			successfulAllergenHeader: "",
			successfulAllergenMessage: "",
			hiddenAllergenErrorAlert: true,
			errorAllergenHeader: "",
			errorAllergenMessage: "",
		});
		console.log(allergenName);
		let patientsAllergenDTO = { allergenName: allergenName, patientId: this.state.id };
		console.log(patientsAllergenDTO);

		Axios.put(API_URL + "/users/removePatientsAllergen", patientsAllergenDTO, {
			validateStatus: () => true,
			headers: { Authorization: GetAuthorisation() },
		})
			.then((res) => {
				if (res.status === 400) {
					this.setState({
						hiddenAllergenErrorAlert: false,
						errorAllergenHeader: "Bad request",
						errorAllergenMessage: "Bad request when removing allergen.",
					});
				} else if (res.status === 500) {
					this.setState({
						hiddenAllergenErrorAlert: false,
						errorAllergenHeader: "Internal server error",
						errorAllergenMessage: "Server error.",

					});
				} else if (res.status === 200) {
					
					this.setState({
						
						hiddenAllergenSuccessfulAlert: false,
						successfulAllergenHeader: "Successful",
						successfulAllergenMessage: "Allergen removed.",
					});

					this.componentDidMount()
				}
			})
			.catch((err) => {
				console.log(err);
				this.setState({ hiddenUnsuccessfulAlert: false,
					UnsuccessfulHeader: "Error", 
					UnsuccessfulMessage: "Something was wrong" });
			});
	};



	handleClickAnotherInformation= () => {
		this.setState({
			showAnotherInformation: true,
			hideButtonForAnotherInformation : true
		});

		
	};


	handleClickAnotherInformationClose= () => {
		this.setState({
			showAnotherInformation: false,
			hideButtonForAnotherInformation : false
		});

		
	};



	render() {

        
		return(

            <React.Fragment>

            <Header/>  

            <div id="userProfilePage" className="container">


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



		<button type="button" class="btn btn-outline-primary "
         onClick={() => this.handleClickAnotherInformation()}
		 hidden={this.state.hideButtonForAnotherInformation}
         style={{  marginTop: "2em", marginLeft: "auto",marginRight: "auto" }}
         >

		 Show another information
        
        </button>



		<button type="button" class="btn btn-outline-primary "
         onClick={() => this.handleClickAnotherInformationClose()}
		 hidden={!this.state.hideButtonForAnotherInformation}
         style={{  marginTop: "2em", marginLeft: "auto",marginRight: "auto" }}
         >

		Close another information
        </button>
			


            <h4 className=" text-center  mb-0 text-uppercase" style={{ marginTop: "2rem" }}>
						User profile
			</h4>

            <div className="row mt-5 ">

					<div className="col  container  offset-1 shadow p-3  bg-white ">
                        <h5 className=" text-center text-uppercase">Personal Information</h5>
                        

						<form id="informationForm" >
								<div className="control-group">
									<div className="form-group controls mb-0 pb-2" >
										<b>Email address:</b>
										<input
											readOnly
											placeholder="Email address"
											className="form-control-plaintext"
											id="name"
											type="text"
											onChange={this.handleEmailChange}
											value={this.state.email}
										/>
									</div>
								</div>
								<div className="control-group">
									<div className="form-group controls mb-0 pb-2" >
										<b>Name:</b>
										<input
											readOnly={this.state.hiddenEditInfo}
											className={!this.state.hiddenEditInfo === false ? "form-control-plaintext" : "form-control"}
											placeholder="Name"
											type="text"
											onChange={this.handleNameChange}
											value={this.state.name}
										/>
									</div>
									<div className="text-danger" style={{ display: this.state.nameError }}>
										Name must be entered.
									</div>
								</div>
								<div className="control-group">
									<div className="form-group controls mb-0 pb-2">
										<b>Surname:</b>
										<input
											readOnly={this.state.hiddenEditInfo}
											className={!this.state.hiddenEditInfo === false ? "form-control-plaintext" : "form-control"}
											placeholder="Surname"
											type="text"
											onChange={this.handleSurnameChange}
											value={this.state.surname}
										/>
									</div>
									<div className="text-danger" style={{ display: this.state.surnameError }}>
										Surname must be entered.
									</div>
								</div>
								<div className="control-group">
									
									<div className="form-group controls mb-0 pb-2" >
											<b>Adress:</b>
											<input
												readOnly={this.state.hiddenEditInfo}
												className={!this.state.hiddenEditInfo === false ? "form-control-plaintext" : "form-control"}
												placeholder="Adress"
												type="text"
												onChange={this.handleAddressChange}
												value={this.state.address}
											/>
									</div>
									
									<div className="text-danger" style={{ display: this.state.addressError }}>
										Address must be entered.
									</div>
									
								</div>
								<div className="control-group">
									<div className="form-group controls mb-0 pb-2" >
										<b>Phone number:</b>
										<input
											placeholder="Phone number"
											readOnly={this.state.hiddenEditInfo}
											className={!this.state.hiddenEditInfo === false ? "form-control-plaintext" : "form-control"}
											type="text"
											onChange={this.handlePhoneNumberChange}
											value={this.state.phoneNumber}
										/>
									</div>
									<div className="text-danger" style={{ display: this.state.phoneError }}>
										Phone number must be entered.
									</div>
									<div className="text-danger" style={{ display: this.state.phoneValidateError }}>
										Incorect phone number.
									</div>
								</div>
								<div className="form-group text-center" hidden={this.state.hiddenEditInfo}>
									<button
										style={{  marginTop: "15px" }}
										onClick={this.handleChangeInfo}
										className="btn btn-outline-primary btn-xl"
										id="sendMessageButton"
										type="button"
									>
										Change information
									</button>
								</div>
								<br />

								<div className="form-group">
										<div className="form-row justify-content-center mr-3">
											
											<div className="mr-2" hidden={!this.state.hiddenEditInfo}>
												<button
													onClick={this.handleEditInfoClick}
													className="btn btn-outline-primary btn-xl"
													id="sendMessageButton"
													type="button"
												>
													Edit Info
												</button>
											</div>	

											<div className="mr-2" hidden={!this.state.hiddenEditInfo}>
												<button
													onClick={this.handleChangePasswordModalOpen}
													className="btn btn-outline-primary btn-xl"
													id="sendMessageButton"
													type="button"
												>
													Change password
												</button>
											</div>
											

											<div className="mr-2" hidden={!this.state.hiddenEditInfo} hidden={this.hasRole("ROLE_SUPPLIER")} >
												<button
													onClick={this.handleAllergenModal}
													className="btn btn-outline-primary btn-xl"
													id="sendMessageButton"
													type="button"
												>
													Allergens
												</button>
											</div>
																					
										</div>
									
								</div>

							


					</form>
					

                    </div>




				<div hidden= {!this.state.showAnotherInformation}>
					<div className="col  container  offset-1 shadow p-3  bg-white ">
                        <h5 className=" text-center text-uppercase">Another information</h5>
                        


								
							<div className="form-row"  style={{ marginTop : "2em"}}>
									<div className="form-col ml-2"
										style={{
												
												fontSize: "1.4em",
											}}
									
									 >
										<b>Number of penalties: { " " } </b> 
		
									</div>

									<div className="form-col ml-4 rounded pr-3 pl-3" 
									
									style={{
												color: "white",
												background: "red",
												fontSize: "1.7em",
												fontWeight: "400"
											}}
									
									>
									
										{" "}{this.state.patientPenalties} {" "}
		
									</div>
									
							</div>



								
							<div className="form-row "  style={{ marginTop : "10px"}}>
									<div className="form-col  ml-2"
										style={{
												
												fontSize: "1.4em",
											}}
									
									 >
										<b>Number of points: { " " } </b> 
		
									</div>

									<div className="form-col ml-4 rounded pr-3 pl-3" 
									
									style={{
												color: "white",
												background: "green",
												fontSize: "1.4em",
												marginTop : "2px",
												fontWeight: "400"
												
											}}
									
									>
									
										{" "}{this.state.patientPoints} {" "}
		
									</div>
									
							</div>
								
							<div className="form-row "  style={{ marginTop : "30px"}}>
									<div className="form-col  ml-2"
										style={{
												
												fontSize: "1.4em",
											}}
									
									 >
										<b>Loyalty category: { " " } </b> 
		
									</div>

									<div className="form-col ml-4 rounded pr-3 pl-3" 
									
									style={{
												color: "white",
												background: "violet",
												fontSize: "1.4em",
												marginTop : "2px",
												fontWeight: "400"
												
											}}
									
									>
									
										{" "}{this.state.patientLoyaltyCategory} {" "}
		
									</div>
									
							</div>	

								

							<div className="form-row "  style={{ marginTop : "10px"}}>
									<div className="form-col  ml-2"
										style={{
												
												fontSize: "1.4em",
											}}
									
									 >
										<b>Examination discount: { " " } </b> 
		
									</div>

									<div className="form-col ml-4 rounded pr-3 pl-3" 
									
									style={{
												color: "white",
												background: "violet",
												fontSize: "1.4em",
												marginTop : "2px",
												fontWeight: "400"
												
											}}
									
									>
									
										{" "}{this.state.examinationDiscount} <b>{" %"}</b>
		
									</div>
									
							</div>

							<div className="form-row "  style={{ marginTop : "10px"}}>
									<div className="form-col  ml-2"
										style={{
												
												fontSize: "1.4em",
											}}
									
									 >
										<b>Consultation discount: { " " } </b> 
		
									</div>

									<div className="form-col ml-4 rounded pr-3 pl-3" 
									
									style={{
												color: "white",
												background: "violet",
												fontSize: "1.4em",
												marginTop : "2px",
												fontWeight: "400"
												
											}}
									
									>
									
										{" "}{this.state.consultationDiscount} <b>{" %"}</b>
		
									</div>
									
							</div>					
					


							<div className="form-row "  style={{ marginTop : "10px"}}>
									<div className="form-col  ml-2"
										style={{
												
												fontSize: "1.4em",
											}}
									
									 >
										<b>Drugs discount: { " " } </b> 
		
									</div>

									<div className="form-col ml-4 rounded pr-3 pl-3" 
									
									style={{
												color: "white",
												background: "violet",
												fontSize: "1.4em",
												marginTop : "2px",
												fontWeight: "400"
												
											}}
									
									>
									
										{" "}{this.state.drugDiscount} <b>{" %"}</b>
		
									</div>
									
							</div>					





                    </div>


				</div>	
					

            </div>


            

            </div>



			<ChangePasswordModal
					header="Change password"
					hiddenPasswordErrorAlert={this.state.hiddenPasswordErrorAlert}
					errorPasswordHeader={this.state.errorPasswordHeader}
					errorPasswordMessage={this.state.errorPasswordMessage}
					emptyOldPasswordError={this.state.emptyOldPasswordError}
					emptyNewPasswordError={this.state.emptyNewPasswordError}
					emptyNewPasswordRepeatedError={this.state.emptyNewPasswordRepeatedError}
					newPasswordAndRepeatedNotSameError={this.state.newPasswordAndRepeatedNotSameError}
					show={this.state.isPasswordModalOpened}
					changePassword={this.changePassword}
					onCloseModal={this.handleChangePasswordModalClose}
					handleCloseAlertPassword={this.handleCloseAlertPassword}
				/>

				<AllergensModal
					hiddenAllergenSuccessfulAlert={this.state.hiddenAllergenSuccessfulAlert}
					successfulAllergenHeader={this.state.successfulAllergenHeader}
					successfulAllergenMessage={this.state.successfulAllergenMessage}
					handleCloseAllergenAlertSuccessful={this.handleCloseAllergenAlertSuccessful}

					hiddenAllergenErrorAlert={this.state.hiddenAllergenErrorAlert}
					errorAllergenHeader={this.state.errorAllergenHeader}
					errorAllergenMessage={this.state.errorAllergenMessage}
					handleCloseAllergenAlertError={this.handleCloseAllergenAlertError}

					userAllergens={this.state.userAllergens}
					show={this.state.openAllergenModal}
					RemoveAllergen={this.handleRemoveAllergen}
					AddAllergen={this.handleAddAllergen}
					onCloseModal={this.handleAllergensModalClose}
					header="Patients allergens"
					subheader="Remove patients allergens"


				/>


            </React.Fragment>


        );
        
	}


}

export default UserProfile;