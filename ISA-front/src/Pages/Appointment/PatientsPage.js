/*eslint-enable react/jsx-no-comment-textnodes */
import React, { Component } from 'react';
import Header from '../../Components/Header';
import Axios from 'axios';
import GetAuthorisation from '../../Funciton/GetAuthorisation';
import PatientIcon from '../../Images/patient-icon.jpg'
import { Redirect } from "react-router-dom";
const API_URL="http://localhost:8080";
class PatientsPage extends Component {
    state = {
        name: "",
        surname: "",
        patients: [],
        redirect: false,
        redirectUrl: ''
    }

    handleNameChange = (event) => {
        this.setState({ name: event.target.value });
    }
    handleSurnameChange = (event) => {
        this.setState({ surname: event.target.value });
    }

    componentDidMount() {
        this.handleSearch();
    }

    handleSearch = () => {
        console.log(this.state);
        const SEARCH_PATIENT_URL =
        API_URL +
            "/users/search?name=" +
            this.state.name +
            "&surname=" +
            this.state.surname;

        Axios.get(SEARCH_PATIENT_URL, { validateStatus: () => true, headers: { Authorization: GetAuthorisation() } })
            .then((res) => {


                if (res.status === 401) {
                    this.setState({
                        redirect: true,
                        redirectUrl: "/unauthorized"
                    });
                } else {
                    this.setState({
                        patients: res.data
                    });
                }

                console.log(res.data);
            })
            .catch((err) => {
                console.log(err);
            });


            
    };


    handlePatientClick = (patientId) => {
        this.setState({
            redirect: true,
            redirectUrl: "/patient-profile/" + patientId
        });
    };

    compareNames = (a, b) => {
        if (a.EntityDTO.name < b.EntityDTO.name) {
            return -1;
        }
        if (a.EntityDTO.name > b.EntityDTO.name) {
            return 1;
        }
        return 0;
    }

    compareSurnames = (a, b) => {
        if (a.EntityDTO.surname < b.EntityDTO.surname) {
            return -1;
        }
        if (a.EntityDTO.surname > b.EntityDTO.surname) {
            return 1;
        }
        return 0;
    }

    handleSortByNameAscending = () => {
        this.state.patients.sort(this.compareNames);
        this.setState(this.state);
    };

    handleSortByNameDescending = () => {
        this.state.patients.sort(this.compareNames);
        this.state.patients.reverse();
        this.setState(this.state);
    };

    handleSortBySurnameAscending = () => {
        this.state.patients.sort(this.compareSurnames);
        this.setState(this.state);
    };

    handleSortBySurnameDescending = () => {
        this.state.patients.sort(this.compareSurnames);
        this.state.patients.reverse();
        this.setState(this.state);
    };

    render() {

        if (this.state.redirect) return <Redirect push to={this.state.redirectUrl} />;

        return (
            <React.Fragment>
                <Header />

                <div className="container" style={{ marginTop: "-5%" }}>
                    <h1 className="table" style={{ width: "100%", marginTop: "5em", marginLeft: "9.5em",marginRight: "auto" }}>PATIENTS</h1>
                    <div className="col-lg-8 mx-auto">
                    
                            <br />
                            <form className="form-inline mt-3" width="100%">
                                 <div className="form-group mb-2" width="100%">
                                    <div className="dropdown mr-3">
                                        
                                        <button
											className="btn btn-primary dropdown-toggle"
											type="button"
                                            style={{width:"12.5em"}}
											data-toggle="dropdown"
											aria-haspopup="true"
											aria-expanded="false"
										>
											Sort by
										</button>
                                    <div className="dropdown-menu" aria-labelledby="dropdownMenu2">
                                        <button className="dropdown-item" type="button" onClick={this.handleSortByNameAscending}>
                                            Name ascending
											</button>
                                        <button className="dropdown-item" type="button" onClick={this.handleSortByNameDescending}>
                                            Name descending
											</button>
                                        <button className="dropdown-item" type="button" onClick={this.handleSortBySurnameAscending}>
                                            Surname ascending
											</button>
                                        <button className="dropdown-item" type="button" onClick={this.handleSortBySurnameDescending}>
                                            Surname descending
											</button>
                                    </div>
                                    </div>
                                    <input
                                        placeholder="Name"
                                        className="form-control mr-3"
                                        style={{ width: "9em" }}
                                        type="text"
                                        onChange={this.handleNameChange}
                                        value={this.state.name}
                                    />
                                    <input
                                        placeholder="Surname"
                                        className="form-control mr-3"
                                        style={{ width: "9em" }}
                                        type="text"
                                        onChange={this.handleSurnameChange}
                                        value={this.state.surname}
                                    />
                                </div>
                                <div>
                                    <button
                                        onClick={this.handleSearch}
                                        className="btn btn-primary btn-xl"
                                        type="button"
                                    >
                                        <i className="icofont-search mr-1"></i>
                                        Search
                                        
                                    </button>
                                    
                                    
                                </div>
                            </form>
                            
                    </div>

                    <table className="table table-hover" style={{ width: "100%", marginTop: "3rem" }}>
                        <tbody>
                            {this.state.patients.map((patient) => (
                                <tr id={patient.Id} key={patient.Id} onClick={() => this.handlePatientClick(patient.Id)} className="rounded" style={{ cursor: "pointer" }}>
                                    <td width="130em">
                                    <img
                                            className="img-fluid"
                                            src={PatientIcon}
                                            width="60em"
                                        />
                                    </td>
                                    <td>
                                        <div>
                                            <b>Name: </b> {patient.EntityDTO.name + " " + patient.EntityDTO.surname}
                                        </div>
                                        <div>
                                            <b>Phone number: </b> {patient.EntityDTO.phoneNumber}
                                        </div>
                                        <div>
                                            <b>Email: </b> {patient.EntityDTO.email}
                                        </div>
                                    </td>
                                </tr>
                            ))}
                        </tbody>
                    </table>
                </div>
             
            </React.Fragment>);
    }
}

export default PatientsPage;