import React, { Component } from "react";
import Header from '../Header';
import Axios from "axios";
import { YMaps, Map,Placemark } from "react-yandex-maps";

import { Button, Modal } from "react-bootstrap";


import GetAuthorisation from "../../Funciton/GetAuthorisation";

const API_URL="http://localhost:8080";


class PharmacyForPhAdmin extends Component {
	state = {
		pharmacy: "",
		phId: "",
		nameOfPharmacy: "Benu",
		description: "",
        grade: "",
        adress:'',
		
		lat: "",
		long: "",
        isEditable: false,
		consultationPrice:0,
		nameError: "none",
		descriptionError: "none",
		addressError: "none",
		consultationPriceError: "none",
		showModal : false,
		showModalErr   : false

    };
    
	constructor(props) {
		super(props);
		this.addressInput = React.createRef();
	}

	handleChangeInfo = () => {

	
		let street;
		let city;
		let country;
		let latitude;
		let longitude;
		let address1;
		let postCode ='10000';

        address1= this.addressInput.current.value;
		var com=address1.split(",");
		console.log(com[0]);
		this.ymaps
			.geocode(this.addressInput.current.value)
			.then(function (res) {
				var addressObject = res.geoObjects.get(0),
					coords = addressObject.geometry.getCoordinates();
				latitude = coords[0];
				longitude = coords[1];
				country = addressObject.getCountry();
				street = addressObject.getThoroughfare();
				city = addressObject.getLocalities().join(", ");
			})
			.then((res) => {
				let phId = this.state.phId;
				let pharmacyDTO = {
					name: this.state.nameOfPharmacy,
					address: { city, street, country,postCode, latitude, longitude },
					description: this.state.description,
					consultationPrice: this.state.consultationPrice,
				};


				if (this.validateForm(pharmacyDTO)) {
					Axios.put(API_URL + "/pharmacy/editPharmacy/"+phId,pharmacyDTO ,{

						validateStatus: () => true, headers: { Authorization: GetAuthorisation() } })
						.then((res) => {
							if (res.status === 400) {
								this.setState({ showModalErr : true });
							} else if (res.status === 204) {
								console.log("Success");
								this.setState({
								showModal : true
								});
							}

						})
						.catch((err) => {
							console.log(err);
						});
				}
			});
	};

	validateForm = (pharmacyDTO) => {
		this.setState({
			nameError: "none",
			descriptionError: "none",
			addressError: "none",
			consultationPriceError: "none",
		});

		if (pharmacyDTO.name === "") {
			this.setState({ nameError: "initial" });
			return false;
		} else if (pharmacyDTO.description === "") {
			this.setState({ descriptionError: "initial" });
			return false;
		} else if (this.addressInput.current.value === "") {
			this.setState({ addressError: "initial" });
			return false;
		} else if (pharmacyDTO.consultationPrice === "") {
			this.setState({ consultationPriceError: "initial" });
			return false;
		}
		return true;
	};

	isEditableInfoFields = () => {
		this.setState({isEditable: true });
	};

	componentDidMount() {
        this.addressInput = React.createRef();

		Axios.get(API_URL + "/pharmacy/pharmacyInfo/"+localStorage.getItem("keyPharmacyId"),{
			headers: { Authorization: GetAuthorisation() },
		})
			.then((response) => {
				this.setState({
					pharmacy: response.data,
					phId: response.data.Id,
					nameOfPharmacy: response.data.EntityDTO.name,
					description: response.data.EntityDTO.description,
                    
                    address: response.data.EntityDTO.address,
				
					lat: response.data.EntityDTO.address.latitude,
					long: response.data.EntityDTO.address.longitude,
                    grade: response.data.EntityDTO.grade,
                    consultationPrice: response.data.EntityDTO.price
				});
				console.log(this.state.address.latitude);
				console.log(this.state.address.longitude        );
			})
			.catch((err) => {
				console.log(err);
			});
			
		
    }
    

	handleNameChange = (event) => {
		this.setState({ nameOfPharmacy: event.target.value });
	};

	handleDescriptionChange= (event) => {
		this.setState({ description: event.target.value });
	};

	handleConsultationPriceChange = (event) => {
		this.setState({ consultationPrice: event.target.value });
	};

	
	handleCloseModal = () => {
		this.setState({ showModal: false ,showModalErr: false});
	};


	
	

	addressSuggestView = (ymaps) => {
		this.ymaps = ymaps;

		if (this.state.address !== "") {
			this.ymaps
				.geocode([this.state.address.latitude, this.state.address.longitude])
				.then(function (res) {
					var firstGeoObject = res.geoObjects.get(0);
					document.getElementById("suggest").setAttribute("value", firstGeoObject.getAddressLine());
					
				});
				const suggestView = new ymaps.SuggestView("suggest");
			
		}
	};
		
	render() {
		
		
		const nameOfPharmacy=this.state.nameOfPharmacy;
		return (
			<React.Fragment>
				
				<Header />

				<Modal show={this.state.showModal} onHide={this.handleCloseModal}>
       			 <Modal.Header closeButton>
          			<Modal.Title>Modal heading</Modal.Title>
        		</Modal.Header>
        		<Modal.Body>Successfully updated pharmacy information </Modal.Body>
        		<Modal.Footer>
          		<Button variant="secondary" onClick={this.handleCloseModal}>
           			 Close
         		 </Button>
        		</Modal.Footer>
      			</Modal>

				  
				<div className="container" style={{ marginTop: "8%" }}>
					<div className="row" style={{ marginTop: "3%" }}>
                        <div className="col-xs-4" style={{width:'45%'}}>
							<div className="col shadow p-3 bg-white rounded" style={{backgroundcolor:'lavender'}}>

							<h5 className=" text-center text-uppercase">{nameOfPharmacy}</h5>
							<form id="contactForm" name="sentMessage">
								<div className="control-group">
									<div className="form-group controls mb-0 pb-2" style={{ color: "#6c757d", opacity: 1 }}>
										<label>Name:</label>
										<input type="text" className={this.state.isEditable === false ? "form-control-plaintext" : "form-control"}
											placeholder="Name"   readOnly={!this.state.isEditable} onChange={this.handleNameChange}
											value={this.state.nameOfPharmacy}
										/>
									</div>
									<div className="text-danger" style={{ display: this.state.nameError }}>
										Name must be entered.
									</div>
								</div>
                                <div className="control-group">
									<div className="form-group controls mb-0 pb-2" style={{ color: "#6c757d", opacity: 1 }}>
										<label>Address:</label>
										<input type="text-area" id="suggest"	className={this.state.isEditable === false ? "form-control-plaintext" : "form-control"}
											readOnly={!this.state.isEditable}  placeholder="Address" 	ref={this.addressInput} 
										/>
									</div>
									<div className="text-danger" style={{ display: this.state.addressError }}>
										Address must be entered.
									</div>
								</div>
								<div className="control-group">
									<div className="form-group controls mb-0 pb-2" style={{ color: "#6c757d", opacity: 1 }}>
										<label>Description:</label>
										<input type="text" className={this.state.isEditable === false ? "form-control-plaintext" : "form-control"}
											onChange={this.handleDescriptionChange} readOnly={!this.state.isEditable} 	placeholder="Description"
											value={this.state.description}
										/>
									</div>
									<div className="text-danger" style={{ display: this.state.descriptionError }}>
										Description must be entered.
									</div>
								</div>
								
								<Modal show={this.state.showModalErr} onHide={this.handleCloseModal}>
       			 					<Modal.Header closeButton>
          							<Modal.Title>Modal heading</Modal.Title>
        							</Modal.Header>
        							<Modal.Body>Bad request </Modal.Body>
        							<Modal.Footer>
          								<Button variant="secondary" onClick={this.handleCloseModal}>
           			 							Close
         		 						</Button>
        							</Modal.Footer>
      								</Modal>  
								<div className="control-group">
									<div className="form-group controls mb-0 pb-2" style={{ color: "#6c757d", opacity: 1 }}>
										<label>Consultation Price:</label>
										<input
											placeholder="Consultation price"
											readOnly={!this.state.isEditable}
											className={this.state.isEditable === false ? "form-control-plaintext" : "form-control"}
											type="text"
											onChange={this.handleConsultationPriceChange}
											value={this.state.consultationPrice}
										/>
									</div>
									<div className="text-danger" style={{ display: this.state.consultationPriceError }}>
										Consultation price must be entered.
									</div>
								</div>
								<div className="form-group text-center" hidden={!this.state.isEditable}>
									<button
										style={{ background: "#1977cc", marginTop: "15px" }}
										onClick={this.handleChangeInfo}
										className="btn btn-primary btn-xl"
										id="sendMessageButton"
										type="button"
									>
										Change information
									</button>
								</div>
								<br />

								<div className="form-group">
									<div className="form-group controls mb-0 pb-2">
										<div className="form-row justify-content-center">
											<div className="form-col" hidden={this.state.isEditable}>
												<button
													onClick={this.isEditableInfoFields}
													className="btn btn-outline-primary btn-xl"
													id="sendMessageButton"
													type="button"
												>
													Edit Info
												</button>
											</div>
										</div>
									</div>
								</div>
							</form>
							</div>
                        </div>
						<div className="col-xs-8" style={{marginLeft:'3%'}}>
					
						<YMaps query={{load: "package.full",lang: "en_US",apikey: "b0ea2fa3-aba0-4e44-a38e-4e890158ece2"}}>
        								<Map width="33em" height="470px"
          								onLoad={this.addressSuggestView}
          								defaultState={{ center: [this.state.lat, this.state.long], zoom: 15 }}
         								 modules={["SuggestView"]}
        								>
										<Placemark geometry={[this.state.lat, this.state.long]}  properties={{ balloonContent: this.state.nameOfPharmacy }}/>
										</Map>
     									 </YMaps>
						
							
						</div>
					</div>

				</div>

			</React.Fragment>
		);
	}
}

export default PharmacyForPhAdmin;
