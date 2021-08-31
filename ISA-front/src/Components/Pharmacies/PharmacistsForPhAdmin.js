import React, { Component } from "react";
import { Button, Modal } from "react-bootstrap";
import Header from '../Header';
import Axios from "axios";
import GetAuthorisation from "../../Funciton/GetAuthorisation";
import CardList from "../card-components/CardList";
import AddDermatologistModal from "../Modal/AddDermatologistModal";
import PharmacistCardList from "../card-components/PharmacistCardList";
import AddPharmacistModal from "../Modal/AddPharmacistModal";
const API_URL = "http://localhost:8080";


class PharmacistsForPhAdmin extends Component {
    state = {
        pharmacists: [],
        pharmacists1: [],


        workTimes: [],
 
        showSearchForm: false,
        showAddPharmacist: false
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

        Axios.get(API_URL + "/users/pharmacistsInPharmacy/" + localStorage.getItem("keyPharmacyId"), {
            headers: { Authorization: GetAuthorisation() },
        })
            .then((res) => {
                this.setState({ pharmacists: res.data });
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

    handleOpenAddPharmacistModal = () => {
        this.setState({ showAddPharmacist: true });
    }

    handleAddPharmacistClose = () => {
        Axios.get(API_URL + "/users/pharmacistsInPharmacy/" + localStorage.getItem("keyPharmacyId"), {
            headers: { Authorization: GetAuthorisation() },
        })
            .then((res) => {
                this.setState({ pharmacists: res.data });
                console.log(res.data);

            })
            .catch((err) => {
                console.log(err);
            });
        this.setState({ showAddPharmacist: false });
    }

    handleUpdatePharmacists = () => {
        Axios.get(API_URL + "/users/pharmacistsInPharmacy/" + localStorage.getItem("keyPharmacyId"), {
            headers: { Authorization: GetAuthorisation() },
        })
            .then((res) => {
                this.setState({ pharmacists: res.data });
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

                    <button hidden={!this.state.showSearchForm} type="button" class="btn btn-outline-primary btn-xl ml-2" onClick={this.resetSearch}>

                        Reset search

                    </button>

                    <button type="button" class="btn btn-primary btn-xl ml-2" onClick={this.handleOpenAddPharmacistModal}>

                        Add pharmacist

                    </button>

                    <form className={"form-inline mt-3"} hidden={!this.state.showSearchForm} width="100%">

                        <div className="form-group" width="100%">

                            <input
                                placeholder="Name"
                                className="form-control mr-2"
                                style={{ width: "10em" }}
                                type="text"

                            />

                            <input
                                placeholder="LastName"
                                className="form-control mr-2"
                                style={{ width: "10em" }}
                                type="text"

                            />

                            <input
                                placeholder="Grade from"
                                className="form-control mr-2"
                                style={{ width: "10em" }}
                                type="number"
                                min="1"
                                max="5"

                            />
                            <input
                                placeholder="Grade to"
                                className="form-control mr-2"
                                style={{ width: "10em" }}
                                type="number"
                                min="1"
                                max="5"

                            />

                            <button className="btn btn-outline-primary btn-xl " type="button" >
                                Search
                            </button>
                        </div>



                    </form>

                </div>
                <div className="container">
                    <PharmacistCardList pharmacists={this.state.pharmacists} pharmacyId={this.state.pharmacyId} updatePharmacists={this.handleUpdatePharmacists} />
                    <AddPharmacistModal show={this.state.showAddPharmacist} onCloseModal={this.handleAddPharmacistClose} pharmacyId={this.state.pharmacyId} updatePharmacists={this.handleUpdatePharmacists}/>
                </div>


            </React.Fragment>
        );
    }
}

export default PharmacistsForPhAdmin