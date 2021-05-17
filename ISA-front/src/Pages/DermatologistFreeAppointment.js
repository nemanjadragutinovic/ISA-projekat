import React, { Component } from "react";
import Axios from "axios";
import Header from '../Components/Header';
import { withRouter } from "react-router";
import { Redirect } from "react-router-dom";
import GetAuthorisation from "../Funciton/GetAuthorisation";
import FreeDermatologistAppointmentPicture from "../Images/appointment.png" ;
import UnsuccessfulAlert from "../Components/Alerts/UnsuccessfulAlert";
import SuccessfulAlert from "../Components/Alerts/SuccessfulAlert";

const API_URL="http://localhost:8080";

class DermatologistFreeAppointment extends Component {
	
  
    
    state = {
        
        pharmacyId: "",
        appointments : [],
        hiddenSuccessfulAlert: true,
        successfulHeader: "",
        successfulMessage: "",
        hiddenUnsuccessfulAlert: true,
        UnsuccessfulHeader: "",
        UnsuccessfulMessage: "",



    };

  
  

  setPharmacyIdFromUrl = (id) => {
        
    this.setState({
    
        pharmacyId : id
      });


    };

  componentDidMount() {

    if (!this.hasRole("ROLE_PATIENT")) {
			this.props.history.push('/login');
    }



    const pathParams= window.location.pathname;
    const paramsList= pathParams.split("/");
    const id = paramsList[2];

    console.log(id);

    this.setPharmacyIdFromUrl(id);
      
    Axios.get(API_URL + "/appointment/dermatologist/allAppointmentsForchosenPharmacy/" + id, {
			validateStatus: () => true,
			headers: { Authorization: GetAuthorisation() },
		})
			.then((res) => {
				if (res.status === 401) {
          this.props.history.push('/login');
				} else {
					this.setState({ appointments: res.data });
					console.log(res.data);
				}
			})
			.catch((err) => {
				console.log(err);
			});
	}
      

  hasRole = (requestRole) => {
    let currentRoles = JSON.parse(localStorage.getItem("keyRole"));

    if (currentRoles === null) return false;


    for (let currentRole of currentRoles) {
      if (currentRole === requestRole) return true;
    }
    return false;
  };


  handleCloseSuccessfulAlert = () => {
		this.setState({ hiddenSuccessfulAlert: true });
	};

	handleCloseUnsuccessfulAlert= () => {
		this.setState({ hiddenUnsuccessfulAlert: true });
	};




  scheduleSelectedAppointment = (appointmentId) => {
  
    let appointmentIdObject = { id: appointmentId};

    Axios.post(API_URL + "/appointment/dermatologist/reserveFreeDermatologistAppointment",appointmentIdObject , {
			validateStatus: () => true,
			headers: { Authorization: GetAuthorisation() },
		})
      .then((res) => {
        if (res.status === 400) {
          this.setState({ hiddenUnsuccessfulAlert: false,
            UnsuccessfulHeader: "Bad request", 
            UnsuccessfulMessage: res.data });

        } else if (res.status === 500) {

          this.setState({ hiddenUnsuccessfulAlert: false, 
            UnsuccessfulHeader: "Internal server error", 
            UnsuccessfulMessage: "Server error." });

        } else if (res.status === 200) {
          console.log("Success");
          this.setState({
            hiddenSuccessfulAlert: false,
            successfulHeader: "Success",
            successfulMessage: "You have successfully scheduled the desired appointment! ",
            hiddenEditInfo: true,
          });

          this.refreshAppointments(appointmentId);

        }
      })
      .catch((err) => {
        console.log(err);
        this.setState({ hiddenUnsuccessfulAlert: false,
          UnsuccessfulHeader: "Error", 
          UnsuccessfulMessage: "Something was wrong" });
      
      });



  }

  refreshAppointments(appointmentId){

    let newAppointmentsList= [];
    for (let appointment of this.state.appointments) {
      if (appointment.Id !== appointmentId) 
          newAppointmentsList.push(appointment)
    }

    this.setState({
     appointments : newAppointmentsList,
    });

  }


	render() {
	

		return (
      
      <React.Fragment>

      <Header/>
      
         <div className="container">


         <h1 hidden={this.state.appointments.length === 0} className="text-center  mt-3  " >Please select appointment from the list!</h1>
         <h1 hidden={this.state.appointments.length !== 0} className="text-center  mt-3 text-danger"  >Sorry, we don't have free dermatologist appointments for selected pharmacy!</h1>



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



        <div className="container">
                    
          <table className="table table-hover" style={{ width: "70%", marginTop: "5em", marginLeft: "auto",marginRight: "auto" }}>
						<tbody>
							{this.state.appointments.map((appointment) => (
								<tr
									id={appointment.Id}
									key={appointment.Id}
									onClick={() => this.scheduleSelectedAppointment(appointment.Id)}
									style={{ cursor: "pointer" }}
								>

                   <td width="100px">  
                     <img  src={FreeDermatologistAppointmentPicture} style={{ width: "85px", marginTop: "15px" }}></img>                                 
                                    
                    </td>
									
									<td>
										<div>
											<b>Date: </b>{" "}
											{new Date(appointment.EntityDTO.startDateTime).toLocaleDateString("en-US", {
												day: "2-digit",
												month: "2-digit",
												year: "numeric",
											})}
										</div>
										<div>
											<b>Start time: </b>{" "}
											{new Date(appointment.EntityDTO.startDateTime).toLocaleTimeString("en-US", {
												hour: "2-digit",
												minute: "2-digit",
											})}
										</div>
										<div>
											<b>End time: </b>{" "}
											{new Date(appointment.EntityDTO.endDateTime).toLocaleTimeString("en-US", {
												hour: "2-digit",
												minute: "2-digit",
											})}
										</div>
										<div>
											<b>Price:</b>{" "}
                      {appointment.EntityDTO.price }
                      <b>  din</b>
										</div>

                  
										<div>
											<b>Dermatologist: </b>{" "}
											{appointment.EntityDTO.employee.EntityDTO.name + " " + appointment.EntityDTO.employee.EntityDTO.surname}
										</div>
										
									</td>
								</tr>
							))}
						</tbody>
					</table>
                </div>



          
        </div>
        </React.Fragment>
        
		);
	}
}

export default withRouter(DermatologistFreeAppointment);