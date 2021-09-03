import React, { Component } from "react";


import Axios from "axios";
import GetAuthorisation from "../../Funciton/GetAuthorisation";
import Header from '../Header';
import 'react-confirm-alert/src/react-confirm-alert.css'; // Import css

const API_URL = "http://localhost:8080";

class AbsenceForPhAdmin extends Component {
	state = {
        absences: [],
        pharmacyId:'',
        showReasonModal:false,
    };

    componentDidMount() {
		let pharmacyId=localStorage.getItem("keyPharmacyId")
		this.setState({
			pharmacyId: pharmacyId
		})

        Axios
        .get(API_URL + "/absence/getPharmacyAbsences/"+ localStorage.getItem("keyPharmacyId"), {
			headers: { Authorization: GetAuthorisation() },
		}).then((res) =>{
            this.setState({absences : res.data});
            console.log(res.data);
        }).catch((err) => {console.log(err);});
    }

    

    render() {
        

		return (
        <React.Fragment>
            <div>

            </div>

                   
                    <Header />

        
                    <div className="container" style={{ marginTop: "10%" }} >
                
						<h5 className=" text-center  mt-2 text-uppercase">Absences </h5>

                        <table className="table mt-3" style={{width:"100%"}}>
                            <tbody>
                                {this.state.absences.map(absence => 
                                    <tr hidden={false} id={absence.Id} key={absence.Id} >
                                        <td width="130em">
                                          
                                        </td>
                                        <td >
                                            <div><b>For employe:</b> {absence.EntityDTO.forStaff}</div>

                                            <div><b>Date from:</b> {new Date(absence.EntityDTO.startDate).toDateString()}</div>
                                            <div><b>Date to:</b> {new Date(absence.EntityDTO.endDate).toDateString()}</div>
                                        </td>
                                        <td >
                                            <div  style={{marginLeft:'25%'}} className="mt-2">
                                                <button style={{height:'30px'},{verticalAlign:'center'}} className="btn btn-outline-secondary" onClick={() => this.onClickAcceptAbsence(absence.Id)} type="button"><i className="icofont-subscribe mr-1"></i>Accept</button>
                                                <br></br>
                                                <button style={{height:'30px'},{verticalAlign:'center'},{marginTop:'2%'}} className="btn btn-outline-secondary" onClick={() => this.onClickRejectAbsence(absence.Id)} type="button"><i className="icofont-subscribe mr-1"></i>Reject</button>
                                                <br></br>                                       
                                             </div>
                                        </td>
                                    </tr>
                                    )}
                            </tbody>
                        </table>
                    </div>
                    <div>
                   
                    </div>
                </React.Fragment>
		);
	}
}

export default AbsenceForPhAdmin