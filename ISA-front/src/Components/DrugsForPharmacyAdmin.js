import React, { Component } from "react";
import Axios from "axios";
import Header from './Header';
import MedicamentPicture from "../Images/medicament.jpg";
import DrugSpecificationModal from "./DrugSpecification";
import GetAuthorisation from "../../src/Funciton/GetAuthorisation"
import PharmaciesWithDrugAndPrice from '../Components/Pharmacies/PharmaciesWithDrugAndPrice';
import ReservationDrugModal from "./Modal/ReservationDrugsModal";
import FirstGradeModal from "../Components/Modal/FirstGradeModal";
import UnsuccessfulAlert from "../Components/Alerts/UnsuccessfulAlert";
import SuccessfulAlert from "../Components/Alerts/SuccessfulAlert";
import AddDrugModal from "./Modal/AddDrugModal";


const API_URL = "http://localhost:8080";

class DrugsForPharmacyAdmin extends Component {



	state = {
		drugs: [],
		drugs1: [],
		drugAmount: "",
		drugQuantity: "",
		drugManufacturer: "",
		drugName: "",
		onReciept: false,
		drugKind: "",
		drugFormat: "",
		sideEffects: "",
		points: "",

		searchName: "",
		searchGradeFrom: "",
		searchGradeTo: "",

		grade: 1,
		hiddenFailAlert: true,
		failHeader: "",
		failMessage: "",
		hiddenSuccessAlert: true,
		successHeader: "",
		successMessage: "",

		pharmacyId: "",
		maxCount: "",
		price: 0,
		drugId: "",
		hiddenErrorAlert: true,
		errorHeader: "",
		errorMessage: "",

		hiddenSuccessfulAlert: true,
		SuccessfulHeader: "",
		SuccessfulMessage: "",
		hiddenUnsuccessfulAlert: true,
		UnsuccessfulHeader: "",
		UnsuccessfulMessage: "",

		showAddDrug: false ,

		searchMan: ""

	};

	componentDidMount() {
		let pharmacyId = localStorage.getItem("keyPharmacyId")
        this.setState({
            pharmacyId: pharmacyId
        })
		Axios.get(API_URL + "/drug/drugsInPharmacy/" + localStorage.getItem("keyPharmacyId"), {

			validateStatus: () => true,
			headers: { Authorization: GetAuthorisation() },
		})

			.then((res) => {
				console.log(res.data);
				this.setState({ drugs: res.data });
			})
			.catch((err) => {
				console.log(err);
			});

	}


	handleManChange = (event) => {
		this.setState({ searchMan: event.target.value });
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
		Axios.get(API_URL + "/drug/drugsInPharmacy/" + localStorage.getItem("keyPharmacyId"), {

			validateStatus: () => true,
			headers: { Authorization: GetAuthorisation() },
		})
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


	};


	handleSearchClick = () => {
		{
			let gradeFrom = this.state.searchGradeFrom;
			let gradeTo = this.state.searchGradeTo;
			let name = this.state.searchName;
			let searchMan = this.state.searchMan;

			if (gradeFrom === "") gradeFrom = -1;
			if (gradeTo === "") gradeTo = -1;
			if (name === "") name = "";
			if (searchMan === "") searchMan = "";

			Axios.get("http://localhost:8080/drug/searchDrugsInPharmacy", {
				params: {
					name: name,
					gradeFrom: gradeFrom,
					gradeTo: gradeTo,
					manufacturer: searchMan,
					pharmacyId: localStorage.getItem("keyPharmacyId")
				},
				validateStatus: () => true,
				headers: { Authorization: GetAuthorisation() },
			})
				.then((res) => {
					console.log(res.data);
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

	handleCloseUnsuccessfulAlert = () => {
		this.setState({ hiddenUnsuccessfulAlert: true });
	};


	handleCloseErrorAlert = () => {
		this.setState({
			hiddenErrorAlert: true,
			errorHeader: "",
			errorMessage: ""
		});
	};

	handleAddDrug = () => {
        {/*Axios.get(API_URL + "/users/dermatologistsNotInPharmacy/" + localStorage.getItem("keyPharmacyId"), {
            headers: { Authorization: GetAuthorisation() },
        }).then((res) => {
            this.setState({ dermatologists1: res.data, showAddDermatologist: true });
            console.log(res.data);
        })
            .catch((err) => {
                console.log(err);
            });
		*/}
		this.setState({ showAddDrug: true });
    }

	handleAddDrugClose = () => {
        Axios.get(API_URL + "/drug/drugsInPharmacy/" + localStorage.getItem("keyPharmacyId"), {
            headers: { Authorization: GetAuthorisation() },
        })
            .then((res) => {
                this.setState({ drugs: res.data });
                console.log(res.data);

            })
            .catch((err) => {
                console.log(err);
            });
        this.setState({ showAddDrug: false });
    }


	handleUpdateDrugsWhicharentInPharmacy = () => {
      {/*  Axios.get(API_URL + "/users/dermatologistsNotInPharmacy/" + localStorage.getItem("keyPharmacyId"), {
            headers: { Authorization: GetAuthorisation() },
        }).then((res) => {
            this.setState({ dermatologists1: res.data });
            console.log(res.data);
        })
            .catch((err) => {
                console.log(err);
            });*/}

    }
	render() {


		return (

			<React.Fragment>

				<Header />

				<div id="allDrugs" >

					<div className="container" style={{ marginTop: "1em" }} >
						<h2 className=" text-center mb-0 mt-2 text-uppercase">Drugs</h2>
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


					<div style={{ width: "70%", marginTop: "3em", marginLeft: "auto", marginRight: "auto" }} width="100%">


						<button className="btn btn-outline-primary btn-xl" type="button" onClick={this.hangleFormToogle}>

							Search drugs
						</button>

						<button className="btn btn-primary btn-xl" type="button" onClick={this.handleAddDrug} style={{ marginLeft: "2%" }}>

							Add drug
						</button>

						<button className="btn btn-primary btn-xl" type="button" onClick={this.hangleFormToogle} style={{ marginLeft: "2%" }}>

							Create order
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

								<input
									placeholder="Manufacturer"
									className="form-control mr-3"
									style={{ width: "9em" }}
									type="text"
									onChange={this.handleManChange}
									value={this.state.searchMan}
								/>


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





						<table className={"table"} style={{ width: "100%", marginTop: "3rem" }}>
							<tbody>
								{this.state.drugs.map((drug) => (
									<tr id={drug.Id} key={drug.Id} >
										<td width="130em" >
											<div style={{ marginTop: "40%" }}>
												<img className="img-fluid" src={MedicamentPicture} width="70em" />
											</div>
										</td>
										<td onClick={() => this.handleOnDrugSelect(drug.Id)}>
											<div>
												<b>Drug Name:</b> {drug.EntityDTO.name}
											</div>
											<div>
												<b>Name:</b> {drug.EntityDTO.fabricCode}
											</div>
											<div>
												<b>Price:</b> {drug.EntityDTO.price}
											</div>
											<div>
												<b>Manufacturer:</b> {drug.EntityDTO.producerName}
											</div>
											<div>
												<b>Quantity:</b> {drug.EntityDTO.quantity} <b> mg</b>
											</div>
											<div>
												<b>Format:</b> {drug.EntityDTO.drugFormat}
											</div>
											<div>
												<b>Count:</b> {drug.EntityDTO.count}
											</div>
											<div>
												<b>Grade:</b> {drug.EntityDTO.avgGrade}
												<i className="icon-star" style={{ color: "yellow" }}></i>
											</div>
										</td>
										<td className="align-middle">
											<div style={{ marginLeft: "40%" }}>
												<button
													type="button"
													onClick={() => this.handleGetGradeClick(drug)}

													className="btn btn-outline-secondary btn-block"
												>
													Edit Storage
												</button>
												<button
													type="button"
													onClick={() => this.handleGetGradeClick(drug)}

													className="btn btn-outline-secondary btn-block"
												>
													Edit Drug Price
												</button>

												<button
													type="button"
													onClick={() => this.handleDrugClick(drug)}
													className="btn btn-outline-secondary btn-block"
												>
													Remove Drug
												</button>
											</div>



										</td>
									</tr>
								))}
							</tbody>
						</table>





					</div>


					<AddDrugModal show={this.state.showAddDrug} closeModal={this.handleAddDrugClose} drugs={this.state.drugs1} pharmacyId={this.state.pharmacyId} updateDrugsWhicharentInPharmacy={this.handleUpdateDrugsWhicharentInPharmacy} />


				</div>

			</React.Fragment>


		);
	}
}

export default DrugsForPharmacyAdmin;
