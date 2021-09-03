import React, { Component } from "react";
import { Button, Modal } from "react-bootstrap";
import Header from '../Header';
import Axios from "axios";
import GetAuthorisation from "../../Funciton/GetAuthorisation";
import CardList from "../card-components/CardList";
import AddDermatologistModal from "../Modal/AddDermatologistModal";
const API_URL = "http://localhost:8080";


class DermatologistsForPhAdmin extends Component {
    state = {
        dermatologists: [],
        dermatologists1: [],
        searchName:'',
        searchSurname:'',
        searchGradeFrom:'',
        searchGradeTo:'',

        workTimes: [],
        forStaff: '',
        showSearchForm: false,
        showAddDermatologist: false
    };

    handleCloseAlertSuccess = () => {
        this.setState({ hiddenSuccessAlert: true });
    };

    handleCloseAlertFail = () => {
        this.setState({ hiddenFailAlert: true });
    };


    componentDidMount() {

        let pharmacyId = localStorage.getItem("keyPharmacyId")
        this.setState({
            pharmacyId: pharmacyId
        })

        Axios.get(API_URL + "/users/dermatologistsInPharmacy/" + localStorage.getItem("keyPharmacyId"), {
            headers: { Authorization: GetAuthorisation() },
        })
            .then((res) => {
                this.setState({ dermatologists: res.data });
                console.log(res.data);

            })
            .catch((err) => {
                console.log(err);
            });
    }



    handleAddDermatologist = () => {
        Axios.get(API_URL + "/users/dermatologistsNotInPharmacy/" + localStorage.getItem("keyPharmacyId"), {
            headers: { Authorization: GetAuthorisation() },
        }).then((res) => {
            this.setState({ dermatologists1: res.data, showAddDermatologist: true });
            console.log(res.data);
        })
            .catch((err) => {
                console.log(err);
            });

    }

    handleUpdateDermatologistsWhoarentInPharmacy = () => {
        Axios.get(API_URL + "/users/dermatologistsNotInPharmacy/" + localStorage.getItem("keyPharmacyId"), {
            headers: { Authorization: GetAuthorisation() },
        }).then((res) => {
            this.setState({ dermatologists1: res.data });
            console.log(res.data);
        })
            .catch((err) => {
                console.log(err);
            });

    }

    handleModalClose = () => {
        this.setState({ showWorkTimesModal: false });
    }



    handleFormShow = () => {
        this.setState({ showSearchForm: !this.state.showSearchForm });
    };


    handleNameChange = (event) => {
		this.setState({ searchName: event.target.value });
    };
    
    handleSurnameChange = (event) => {
		this.setState({ searchSurname: event.target.value });
    };
    
    handleGradeFromChange = (event) => {
		this.setState({ searchGradeFrom: event.target.value });
	};

	handleGradeToChange = (event) => {
		this.setState({ searchGradeTo: event.target.value });
    };

    handleSortByGradeAscending = () =>{

    }

    handleSortByGradeDescending = () =>{

    }
    
    handleSearch = () =>{
    
			if (
				!(
					this.state.searchGradeFrom === "" &&
					this.state.searchGradeTo === "" &&
					this.state.searchName === "" &&
					this.state.searchSurname === ""
				)
			) {
                let name = this.state.searchName;
				let surname = this.state.searchSurname;
				let gradeFrom = this.state.searchGradeFrom;
				let gradeTo = this.state.searchGradeTo;
				

				if (gradeFrom === "") gradeFrom = -1;
				if (gradeTo === "") gradeTo = -1;
				if (name === "") name = '';
				if (surname === "") surname = '';

                console.log(name);
                console.log(surname);
                console.log(gradeFrom);
                console.log(gradeTo);
				Axios.get(API_URL + "/users/dermatologistsSearch", {
                    params: {
                        name: name,
                        surname: surname,
                        gradeFrom: gradeFrom,
                        gradeTo: gradeTo,
                        pharmacyId: localStorage.getItem("keyPharmacyId")
                    },
                    validateStatus: () => true,
                    headers: { Authorization: GetAuthorisation() },
                })
					.then((res) => {
						this.setState({
							dermatologists: res.data,
							formShowed: false,
							showingSearched: true,
						});
						console.log(res.data);
					})
					.catch((err) => {
						console.log(err);
					});
			}
        }   
    

     handleResetSearch = () => {

        Axios.get(API_URL + "/users/dermatologistsInPharmacy/" + localStorage.getItem("keyPharmacyId"), {
            headers: { Authorization: GetAuthorisation() },
        })
			.then((res) => {
				this.setState({ 
                    dermatologists: res.data ,
                    formShowed: false,
					showingSearched: false,
					searchName: "",
                    searchSurname: "",
                    searchGradeFrom: "",
					searchGradeTo: "",

                });
                console.log(res.data);
            
			})
			.catch((err) => {
				console.log(err);
            });
        
	
    };
    handleAddDermatologistClose = () => {
        Axios.get(API_URL + "/users/dermatologistsInPharmacy/" + localStorage.getItem("keyPharmacyId"), {
            headers: { Authorization: GetAuthorisation() },
        })
            .then((res) => {
                this.setState({ dermatologists: res.data });
                console.log(res.data);

            })
            .catch((err) => {
                console.log(err);
            });
        this.setState({ showAddDermatologist: false });
    }

    handleUpdateDermatologists = () => {
        Axios.get(API_URL + "/users/dermatologistsInPharmacy/" + localStorage.getItem("keyPharmacyId"), {
            headers: { Authorization: GetAuthorisation() },
        })
            .then((res) => {
                this.setState({ dermatologists: res.data });
                console.log(res.data);

            })
            .catch((err) => {
                console.log(err);
            });
    }

    render() {
      
        return (
            <React.Fragment>



                <Header />




                <div className="container" style={{ marginTop: "5%", marginBottom: "5%", marginLeft: "6%" }} >


                    <button className="btn btn-primary " type="button" onClick={this.handleFormShow}>
                        {this.state.showSearchForm ? "Close search" : "Open search"}
                    </button>

                    <button hidden={!this.state.showSearchForm} type="button" class="btn btn-outline-primary btn-xl ml-2" onClick={this.handleResetSearch}>

                        Reset search

                    </button>

                    <button type="button" class="btn btn-primary btn-xl ml-2" onClick={this.handleAddDermatologist}>
                     
                        Add dermatologist
                    
                    </button>
                
                    <form className={"form-inline mt-3"} hidden={!this.state.showSearchForm} width="100%">

                        <div className="form-group" width="100%">

                        <div className="form-group mb-2" width="100%">
                                <input
                                    placeholder="Name"
                                    className="form-control mr-2"
                                    style={{ width: "10em" }}
                                    type="text"
                                    onChange={this.handleNameChange}
                                    value={this.state.searchName}
                                />

                                <input
                                    placeholder="LastName"
                                    className="form-control mr-2"
                                    style={{ width: "10em" }}
                                    type="text"
                                    onChange={this.handleSurnameChange}
                                    value={this.state.searchSurname}
                                />

                                <input
                                    placeholder="Grade from"
                                    className="form-control mr-2"
                                    style={{ width: "10em" }}
                                    type="number"
                                    min="1"
                                    max="5"
                                    onChange={this.handleGradeFromChange}
                                    value={this.state.searchGradeFrom}
                                />
                                <input
                                    placeholder="Grade to"
                                    className="form-control"
                                    style={{ width: "10em" }}
                                    type="number"
                                    min="1"
                                    max="5"
                                    onChange={this.handleGradeToChange}
                                    value={this.state.searchGradeTo}
                                />

                            <button
                                onClick={this.handleSearch}
                                className="btn btn-outline-primary btn-xl ml-2"
                                type="button"

                            >
                                Search
                            </button>
                        </div>

                    </div>

                    </form>
                
                </div>


                
                <div className="container">
                    <CardList dermatologists={this.state.dermatologists} pharmacyId={this.state.pharmacyId} updateDermatologists={this.handleUpdateDermatologists} />
                    <AddDermatologistModal show={this.state.showAddDermatologist} closeModal={this.handleAddDermatologistClose} dermatologists={this.state.dermatologists1} pharmacyId={this.state.pharmacyId} updateDermatologistsWhoarentInPharmacy={this.handleUpdateDermatologistsWhoarentInPharmacy} />
                </div>


            </React.Fragment>
        );
    }
}

export default DermatologistsForPhAdmin