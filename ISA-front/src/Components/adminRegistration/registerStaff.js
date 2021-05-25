import Header from "../../Components/Header";
import React, { Component } from "react";
import Axios from "axios";
import { Redirect } from "react-router-dom";
import GetAuthorisation from "../../Funciton/GetAuthorisation";



class registerStaff extends Component {

    state = {
		errorHeader: "",
		errorMessage: "",
		hiddenErrorAlert: true,
		email: "",
		password: "",
		name: "",
		userRegister: "",
		surname: "",
		address: "",
		openModalData: false,
		phoneNumber: "",
		emailError: "none",
		passwordError: "none",
		nameError: "none",
		surnameError: "none",
		addressError: "none",
		phoneError: "none",
		emailNotValid: "none",
		openModal: false,
		pharmacies:[],
		selectedPharmacy:null,
		pharmacy:"",
		coords: [],
		selectValue:"",
        redirect: false,
        redirectUrl: '',
	};

	constructor(props) {
		super(props);
		this.addressInput = React.createRef();
	}

	
	handleModalDataClose = () => {
		this.setState({ 
			openModalData: false,
		});
	};

	handleModalEmailClose = () => {
		this.setState({ 
			openModalEmail: false,
		});
	};
	
	componentDidMount() {

		Axios.get("http://localhost:8080/pharmacy/allPharmacies")
			.then((res) => {
				this.setState({ pharmacies: res.data });
				console.log(res.data);
			})
			.catch((err) => {
				console.log(err);
			});

		Axios.get("/api/users/sysadmin/auth", { validateStatus: () => true, headers: { Authorization: GetAuthorisation() } })
            .then((res) => {
				console.log(res.statusm, "TEST")
                if (res.status === 401) {
                    this.setState({
                        redirect: true,
                        redirectUrl: "/unauthorized"
                    });
                } else {
                    console.log(res.data);
                }
            })
            .catch((err) => {
                console.log(err);
            });
	}
	

	handleEmailChange = (event) => {
		this.setState({ email: event.target.value });
	};
	handlePharmacyChange = (event) => {
		this.setState({ pharmacy: event.target.value });
	};
	
	handlePasswordChange = (event) => {
		this.setState({ password: event.target.value });
	};

	handleNameChange = (event) => {
		this.setState({ name: event.target.value });
	};

	handleSurnameChange = (event) => {
		this.setState({ surname: event.target.value });
	};

	handleAddressChange = (event) => {
		this.setState({ address: event.target.value });
	};

	handlePhoneNumberChange = (event) => {
		this.setState({ phoneNumber: event.target.value });
	};
    handleSelectedValueSysAdminChange = (event) => {
       
		this.setState({ selectValue: "sysadmin" });
        console.log(this.state.selectValue);
	};
    handleSelectedValueSupplierChange = (event) => {
      
		this.setState({ selectValue: "supplier" });
        console.log(this.state.selectValue);
	};
    handleSelectedValueDermathologistChange = (event) => {
       
		this.setState({ selectValue: "dermathologist" });
        console.log(this.state.selectValue);
	};
    handleSelectedValuePharmacyAdminChange = (event) => {
      
		this.setState({ selectValue: "pharmacyadmin" });
        console.log(this.state.selectValue);
	};
    

	validateForm = (userDTO) => {
		alert('test')
		this.setState({
			emailError: "none",
			emailNotValid: "none",
			nameError: "none",
			surnameError: "none",
			addressError: "none",
			phoneError: "none",
		});

		if (userDTO.email === "") {
			this.setState({ emailError: "initial" });
			return false;
		} else if (!userDTO.email.includes("@")) {
			this.setState({ emailNotValid: "initial" });
			return false;
		} else if (userDTO.name === "") {
			this.setState({ nameError: "initial" });
			return false;
		} else if (userDTO.surname === "") {
			this.setState({ surnameError: "initial" });
			return false;
		} else if (userDTO.address === "") {
			this.setState({ addressError: "initial" });
			return false;
		} else if (userDTO.phoneNumber === "") {
			this.setState({ phoneError: "initial" });
			return false;
		}
		
		return true;
	};

	handleModalClose = () => {
		this.setState({ openModal: false });
	};
	
	onPharmacyChange  = (pharmacy) => {
		this.state.selectedPharmacy = pharmacy;
		console.log(pharmacy, "PHARMACy");
	
	};
	
	handleSignUp = () => {

		if(this.state.surname !== "" &&
		this.state.name !== "" &&
		this.state.phoneNumber !== "" &&
		this.state.email !== ""){

		

		
			
			
			
				let userDTO = {
					email: this.state.email,
					name: this.state.name,
					surname: this.state.surname,
					address: this.state.address,
					phoneNumber: this.state.phoneNumber,
					password: this.state.password,
				};
				if (this.validateForm(userDTO)) {
					
					if(this.state.selectValue == "dermathologist"){
							
						Axios.post("http://localhost:8080/reg/signup-dermathologist", userDTO,{ headers: { Authorization: GetAuthorisation()}})
							.then((res) => {
								if (res.status === 409) {
									this.setState({
										errorHeader: "Resource conflict!",
										errorMessage: "Email already exist.",
										hiddenErrorAlert: false,
									});
								} else if (res.status === 500) {
									console.log("USO")
									this.setState({ openModalData: true });
								} else {
									console.log("Success");
									this.setState({ 
										openModal: true,
										userRegister: "You have successfully registered staff with password " + res.data,
                                        redirect: true
									});
								}
							})
							.catch((err) => {
								console.log(err);
							});
					}
					if(this.state.selectValue == "pharmacyadmin"){
						console.log(this.state.selectedPharmacy.Id);
						Axios.post("http://localhost:8080/reg/signup-pharmacyadmin/" + this.state.selectedPharmacy.Id, userDTO, { headers: { Authorization: GetAuthorisation()}})
							.then((res) => {
								console.log("Success");
								this.setState({
									 openModal: true,
									 userRegister: "You have successfully registered staff with password " + res.data,
                                     redirect: true
									});
							})
							.catch((err) => {
								console.log(err);
							});
						
					}
					if(this.state.selectValue == "sysadmin"){
							
						Axios.post("http://localhost:8080/reg/signup-sysadmin", userDTO, { headers: { Authorization: GetAuthorisation()}})
							.then((res) => {
								console.log("Success");
								this.setState({ 
									openModal: true,
									userRegister: "You have successfully registered staff with password " + res.data,
                                    redirect: true
								});
							})
							.catch((err) => {
								console.log(err);
							});
					}
					if(this.state.selectValue == "supplier"){
							
						Axios.post("http://localhost:8080/reg/signup-supplier", userDTO, { headers: { Authorization: GetAuthorisation()}})
							.then((res) => {
								console.log("Success");
								this.setState({
									openModal: true,
									userRegister: "You have successfully registered staff with password " + res.data,
                                    redirect: true
								 });
							})
							.catch((err) => {
								console.log(err);
							});
					}
				
				};
			
		}else{
			this.setState({
				openModalData: true,
			})
		}
		
	};
	handleCloseAlert = () => {
		this.setState({ hiddenErrorAlert: true });
	};
	handleSelectChange  = (event) => {
		this.setState({ selectValue: event.target.value });
	};


render() {
        if (this.state.redirect) return <Redirect push to="/" />;
        return(
            <React.Fragment>
            <Header/>
            

            <div className="container" style={{ marginTop: "8%" }}>

                       
					
					
					<h5 className=" text-center  mb-0 text-uppercase" style={{ marginTop: "2rem" }}>
						Register staff
					</h5>

                    

					<div className="row section-design">
						<div className="col-lg-8 mx-auto">
							<br />
							<form id="contactForm" name="sentMessage" novalidate="novalidate">

                                    <div class="dropdown " hidden={this.state.selectValue !== ""}>
                                <button class="btn btn-secondary dropdown-toggle " type="button" id="dropdownMenuButton" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                    Dropdown button
                                </button>
                                <div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
                                    <a class="dropdown-item" onClick={this.handleSelectedValueSysAdminChange} >System admin</a>
                                    <a class="dropdown-item" onClick={this.handleSelectedValueSupplierChange} >Supplier</a>
                                    <a class="dropdown-item" onClick={this.handleSelectedValueDermathologistChange} >Dermathologist</a>
                                    <a class="dropdown-item" onClick={this.handleSelectedValuePharmacyAdminChange} >Pharmacy Admin</a>
                                </div>
                                </div>

                                     <div className="text" hidden={this.state.selectValue == ""}>
										Selected: {this.state.selectValue}
									</div>

                                    <br />

								<div className="control-group" hidden={this.state.selectValue !== "pharmacyadmin"} >
									<div className="form-group controls mb-0 pb-2" style={{ color: "#6c757d", opacity: 1 }}>
										<label>Pharmacy:</label><br></br>
										<select
									       onChange={this.handlePharmacyChange}
											value={this.state.pharmacy}
									     >{this.state.pharmacies.map((pharmacy) => (
										  <option onClick={this.onPharmacyChange(pharmacy)}  id={pharmacy.Id} key={pharmacy.Id} value={pharmacy.EntityDTO.name}>{pharmacy.EntityDTO.name},{pharmacy.EntityDTO.address.street}, {pharmacy.EntityDTO.address.city},{" "}
											{pharmacy.EntityDTO.address.country}</option>
										))}	
										</select>	
									</div>
								</div>
								<div className="control-group">
									<div className="form-group controls mb-0 pb-2" style={{ color: "#6c757d", opacity: 1 }}>
										<label>Email address:</label>
										<input
											placeholder="Email address"
											className="form-control"
											id="email"
											type="text"
											onChange={this.handleEmailChange}
											value={this.state.email}
										/>
									</div>
									<div className="text-danger" style={{ display: this.state.emailError }}>
										Email address must be entered.
									</div>
									<div className="text-danger" style={{ display: this.state.emailNotValid }}>
										Email address is not valid.
									</div>
								</div>
								<div className="control-group">
									<div className="form-group controls mb-0 pb-2" style={{ color: "#6c757d", opacity: 1 }}>
										<label>Password:</label>
										<input
											placeholder="Password"
											className="form-control"
											id="password"
											type="text"
											onChange={this.handlePasswordChange}
											value={this.state.password}
										/>
									</div>
									<div className="text-danger" style={{ display: this.state.passwordError }}>
										Password must be entered.
									</div>
									<div className="text-danger" style={{ display: this.state.emailNotValid }}>
										Email address is not valid.
									</div>
								</div>
								<div className="control-group">
									<div className="form-group controls mb-0 pb-2" style={{ color: "#6c757d", opacity: 1 }}>
										<label>Name:</label>
										<input
											placeholder="Name"
											class="form-control"
											type="text"
											id="name"
											onChange={this.handleNameChange}
											value={this.state.name}
										/>
									</div>
									<div className="text-danger" style={{ display: this.state.nameError }}>
										Name must be entered.
									</div>
								</div>
								<div className="control-group">
									<div className="form-group controls mb-0 pb-2" style={{ color: "#6c757d", opacity: 1 }}>
										<label>Surname:</label>
										<input
											placeholder="Surname"
											class="form-control"
											type="text"
											id="surname"
											onChange={this.handleSurnameChange}
											value={this.state.surname}
										/>
									</div>
									<div className="text-danger" style={{ display: this.state.surnameError }}>
										Surname must be entered.
									</div>
								</div>

                                <div className="control-group">
									<div className="form-group controls mb-0 pb-2" style={{ color: "#6c757d", opacity: 1 }}>
										<label>Address:</label>
										<input
											placeholder="Address"
											class="form-control"
											type="text"
											id="address"
											onChange={this.handleAddressChange}
											value={this.state.address}
										/>
									</div>
									<div className="text-danger" style={{ display: this.state.addressError }}>
										Address must be entered.
									</div>
								</div>
								
								<div className="control-group">
									<div className="form-group controls mb-0 pb-2" style={{ color: "#6c757d", opacity: 1 }}>
										<label>Phone number:</label>
										<input
											placeholder="Phone number"
											class="form-control"
											id="phone"
											type="text"
											onChange={this.handlePhoneNumberChange}
											value={this.state.phoneNumber}
										/>
									</div>
									<div className="text-danger" style={{ display: this.state.phoneError }}>
										Phone number must be entered.
									</div>
								</div>

								<div className="form-group">
									<button
										style={{
											background: "#1977cc",
											marginTop: "15px",
											marginLeft: "40%",
											width: "20%",
										}}
										onClick={this.handleSignUp}
										className="btn btn-primary btn-xl"
										id="sendMessageButton"
										type="button"
									>
										Sign Up
									</button>
								</div>
							</form>
						</div>
					</div>
				</div>
				
			</React.Fragment>
        );
    }
}
export default registerStaff;