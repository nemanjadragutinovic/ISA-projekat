import React, { Component } from "react";
import { Redirect } from "react-router-dom";
import { withRouter } from "react-router";
import Header from "../../Components/Header";
import Axios from "axios";
import GetAuthorisation from "../../Funciton/GetAuthorisation";
import { getRoles } from "@testing-library/dom";



class RegisterPharmacies extends Component {
	state = {
		name: "",
		description: "",
		address: "",
		city: "",
		street: "",
		country: "",
		postcode: "",
		consulationPrice: "",
		openModalData: false,
		nameError: "none",
		CityError: "none",
		StreetError: "none",
		CountryError: "none",
		PostcodeError: "none",
		consulationPriceError: "none",
		descriptionError: "none",
		openModal: false,
		coords: [],
        redirect: false,
        redirectUrl: '',
	};

	constructor(props) {
		super(props);
		this.addressInput = React.createRef();
	}

	
	componentDidMount() {
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

	handleModalDataClose = () => {
		this.setState({ 
			openModalData: false,
		});
	};

	onYmapsLoad = (ymaps) => {
		this.ymaps = ymaps;
		new this.ymaps.SuggestView(this.addressInput.current, {
			provider: {
				suggest: (request, options) => this.ymaps.suggest(request),
			},
		});
	};

	handleNameChange = (event) => {
		this.setState({ name: event.target.value });
	};
	handleCityChange = (event) => {
		this.setState({ city: event.target.value });
	};
	handleStreetChange = (event) => {
		this.setState({ street: event.target.value });
	};
	handleCountryChange = (event) => {
		this.setState({ country: event.target.value });
	};
	handlePostcodeChange = (event) => {
		this.setState({ postcode: event.target.value });
	};

	handleAddressChange = (event) => {
		this.setState({ address: event.target.value });
	};

	handleConsulationPriceChange = (event) => {
		this.setState({ consulationPrice: event.target.value });
	};
	
	handleDescriptionChange = (event) => {
		this.setState({ description: event.target.value });
	};

	validateForm = (userDTO) => {
		this.setState({
			nameError: "none",
			CityError: "none",
			StreetError: "none",
			CountryError: "none",
			PostcodeError: "none",
			consulationPriceError: "none",
		});

		if (userDTO.name === "") {
			this.setState({ nameError: "initial" });
			return false;
		} else if (userDTO.address.city === "") {
			this.setState({ CityError: "initial" });
			return false;
		} else if (userDTO.address.street === "") {
			this.setState({ StreetError: "initial" });
			return false;
		} else if (userDTO.address.country === "") {
			this.setState({ CountryError: "initial" });
			return false;
		} else if (userDTO.address.postcode === "") {
			this.setState({ PostcodeError: "initial" });
			return false;
		} else if (userDTO.consulationPrice === "") {
			this.setState({ consulationPriceError: "initial" });
			return false;
		}
		
		return true;
	};

	handleModalClose = () => {
		this.setState({ openModal: false });
	};

	handleSignUp = () => {

		

		
			
			
			var address = {city:this.state.city, street:this.state.street, country: this.state.country, postCode:this.state.postcode};



			let pharmacyDTO = {
				name: this.state.name,
				address: address,
				description: this.state.description,
			};


				console.log(pharmacyDTO);
				if (this.validateForm(pharmacyDTO)) {

					console.log("usao u zahtev");
					console.log(GetAuthorisation());
					console.log(localStorage.getItem("keyRole"));
				
					Axios.post("http://localhost:8080/pharmacy", pharmacyDTO, {  headers: { Authorization: GetAuthorisation()}})
						.then((res) => {
							console.log("Success");
							this.setState({ openModal: true });
						})
						.catch((err) => {
							console.log(err);
						});
				}
			
		
		
	};
	
	handleSelectChange  = (event) => {
		this.setState({ selectValue: event.target.value });
	};


    render() {
		if (this.state.redirect) return <Redirect push to={this.state.redirectUrl} />;

		return (
			<React.Fragment>
				
				<Header />

				<div className="container" style={{ marginTop: "8%" }}>
					<h5 className=" text-center  mb-0 text-uppercase" style={{ marginTop: "2rem" }}>
						Registrater pharmacy
					</h5>

					<div className="row section-design">
						<div className="col-lg-8 mx-auto">
							<br />
							<form id="contactForm" name="sentMessage" novalidate="novalidate">
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
										<label>City:</label>
										<input
											placeholder="City"
											class="form-control"
											type="text"
											id="name"
											onChange={this.handleCityChange}
											value={this.state.city}
										/>
									</div>
									<div className="text-danger" style={{ display: this.state.CityError }}>
										City must be entered.
									</div>
								</div>
								<div className="control-group">
									<div className="form-group controls mb-0 pb-2" style={{ color: "#6c757d", opacity: 1 }}>
										<label>Street:</label>
										<input
											placeholder="Street"
											class="form-control"
											type="text"
											id="name"
											onChange={this.handleStreetChange}
											value={this.state.street}
										/>
									</div>
									<div className="text-danger" style={{ display: this.state.StreetError }}>
										Street must be entered.
									</div>
								</div>
								<div className="control-group">
									<div className="form-group controls mb-0 pb-2" style={{ color: "#6c757d", opacity: 1 }}>
										<label>Country:</label>
										<input
											placeholder="Country"
											class="form-control"
											type="text"
											id="name"
											onChange={this.handleCountryChange}
											value={this.state.country}
										/>
									</div>
									<div className="text-danger" style={{ display: this.state.CountryError }}>
										Country must be entered.
									</div>
								</div>
								<div className="control-group">
									<div className="form-group controls mb-0 pb-2" style={{ color: "#6c757d", opacity: 1 }}>
										<label>Postcode:</label>
										<input
											placeholder="Postcode"
											class="form-control"
											type="text"
											id="name"
											onChange={this.handlePostcodeChange}
											value={this.state.postcode}
										/>
									</div>
									<div className="text-danger" style={{ display: this.state.PostcodeError }}>
										Postcode must be entered.
									</div>
								</div>
								<div className="control-group">
									<div className="form-group controls mb-0 pb-2" style={{ color: "#6c757d", opacity: 1 }}>
										<label>Description:</label>
										<input
											placeholder="Description"
											class="form-control"
											type="text"
											id="name"
											onChange={this.handleDescriptionChange}
											value={this.state.description}
										/>
									</div>
									<div className="text-danger" style={{ display: this.state.descriptionError }}>
									  Description must be entered.
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

export default withRouter(RegisterPharmacies);


