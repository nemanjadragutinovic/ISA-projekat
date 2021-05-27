import React, { Component } from "react";
import Axios from "axios";
import Header from './Header';
import MedicamentPicture from "../Images/medicament.jpg" ;
import DrugSpecificationModal from "./DrugSpecification";
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
		


  };


  

  componentDidMount() {
		

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

   



	render() {
	

		return (
      
      <React.Fragment>

      <Header/>
      
      <div id="allDrugs">

            
           
           
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

          <table className={this.state.loggedPatient === true ? "table table-hover" : "table"} style={{ width: "100%", marginTop: "3rem" }}>
						<tbody>
							{this.state.drugs.map((drug) => (
								<tr id={drug.Id} key={drug.Id} style={this.state.loggedPatient === true ? { cursor: "pointer" } : {}}>
									<td width="130em">
										<img className="img-fluid" src={MedicamentPicture} width="70em" />
									</td>
									<td onClick={() => this.props.onDrugSelect(drug)}>
										<div>
											<b>Name:</b> {drug.EntityDTO.drugInstanceName}
										</div>
										<div>
											<b>Kind:</b> {drug.EntityDTO.drugKind}
										</div>
										<div>
											<b>Grade:</b> {drug.EntityDTO.avgGrade}
											<i className="icofont-star" style={{ color: "#1977cc" }}></i>
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
        </React.Fragment>
        
		);
	}
}

export default Drugs;
