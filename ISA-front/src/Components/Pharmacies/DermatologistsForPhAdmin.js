import React, { Component } from "react";

import Header from '../Header';
import Axios from "axios";
import GetAuthorisation from "../../Funciton/GetAuthorisation";

const API_URL="http://localhost:8080";

class DermatologistsPage extends Component {
	state = {
        dermatologistsInPharmacy: [],
      
    };


    componentDidMount() {

		Axios.get(API_URL + "pharmacy/dermatologist-for-pharmacy/", {
            params: { phId : localStorage.getItem("keyPharmacyId")},
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
        const myStyle = {
			color: "white",
			textAlign: "center",
		};
		return (
        <React.Fragment>
            <div>

            </div>

                   
                    <Header />

                

                    
                    <div className="container" style={{ marginTop: "10%" }} >
                   
                        <form
                            className={
                                this.state.formShowed ? "form-inline mt-3" : "form-inline mt-3 collapse"
                            }
                            width="100%"
                            id="formCollapse"
                            >
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
                                    placeholder="LastName"
                                    className="form-control mr-3"
                                    style={{ width: "9em" }}
                                    type="text"
                                    onChange={this.handleSurnameChange}
                                    value={this.state.searchSurname}
                                />

                                <input
                                    placeholder="Grade from"
                                    className="form-control mr-3"
                                    style={{ width: "9em" }}
                                    type="number"
                                    min="1"
                                    max="5"
                                    onChange={this.handleGradeFromChange}
                                    value={this.state.searchGradeFrom}
                                />
                                <input
                                    placeholder="Grade to"
                                    className="form-control"
                                    style={{ width: "9em" }}
                                    type="number"
                                    min="1"
                                    max="5"
                                    onChange={this.handleGradeToChange}
                                    value={this.state.searchGradeTo}
                                />
                                
                            </div>
                            <div style={{marginLeft:'1%'}}>

                                <button
                                    style={{ background: "#1977cc" },{marginLeft:'15%'},{marginBottom:'8%'}}
                                    onClick={this.handleSearchClick}
                                    className="btn btn-primary btn-x2"
                                    type="button"
                                        >
                                    <i className="icofont-search mr-1"></i>
                                    Search
                                </button>
                            </div>

                        </form>

                        

                        <table className="table" style={{ width: "100%", marginTop: "3rem" }}>
                            <tbody>
                                {this.state.dermatologists.map((dermatologist) => (
                                    <tr id={dermatologist.Id} key={dermatologist.Id}>
                                        <td width="130em">
                                            <img
                                                className="img-fluid"
                                               
                                                width="70em"
                                            />
                                        </td>

                                        <td>
                                            <div>
                                                <b>Name: </b> {dermatologist.EntityDTO.name}
                                            </div>
                                            <div>
                                                <b>Surname: </b> {dermatologist.EntityDTO.surname}
                                            </div>
                                            <div>
                                                <b>Email: </b> {dermatologist.EntityDTO.email}
                                            </div>
                                            <div>
                                                <b>Phone number: </b> {dermatologist.EntityDTO.phoneNumber}
                                            </div>
                                            <div>
                                                <b>Grade: </b> {dermatologist.EntityDTO.grade}
                                                <i
                                                    className="icofont-star"
                                                    style={{ color: "#1977cc" }}
                                                ></i>
                                            </div>
                                        </td>
                                        <td >
                                            <div style={{marginLeft:'55%'}}>
                                                <button style={{height:'30px'},{verticalAlign:'center'}} className="btn btn-outline-secondary" onClick={() => this.onWorkTimeClick(dermatologist.Id)} type="button"><i className="icofont-subscribe mr-1"></i>WorkTimes</button>
                                                <br></br>
                                                <button style={{height:'30px'},{verticalAlign:'center'},{marginTop:'2%'}} className="btn btn-outline-secondary mt-1" onClick={() => this.showPharmacies(dermatologist.Id)} type="button"><i className="icofont-subscribe mr-1"></i>Pharmacies</button>
                                                <br></br>
                                                <button style={{height:'30px'},{verticalAlign:'center'},{marginTop:'2%'}} className="btn btn-outline-secondary mt-1" onClick={() => this.removeDermatologistClick(dermatologist.Id)} type="button"><i className="icofont-subscribe mr-1"></i>Remove dermatologist</button>
                                            </div>
                                               
                                        </td>
                                    </tr>

                                ))}
                                <tr>
                                    <td></td>
                                    <td></td>
                                    <td></td>

                                </tr>
                            </tbody>
                        </table>

                    </div>
                    
                </React.Fragment>
		);
	}
}

export default DermatologistsPage