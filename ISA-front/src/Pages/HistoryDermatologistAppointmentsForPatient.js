import React, { Component } from "react";
import Axios from "axios";
import Header from '../Components/Header';
import GetAuthorisation from "../Funciton/GetAuthorisation";
import DermatologistAppointmentPicture from "../Images/appointment.png" ;
import UnsuccessfulAlert from "../Components/Alerts/UnsuccessfulAlert";
import SuccessfulAlert from "../Components/Alerts/SuccessfulAlert";
import {NavLink, Redirect } from "react-router-dom";

const API_URL="http://localhost:8080";

class HistoryDermatologistAppointmentsForPatient extends Component {
	
  
    
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

    constructor(props) {
        super(props);
    }
  


  componentDidMount() {

    if (!this.hasRole("ROLE_PATIENT")) {
			this.props.history.push('/login');
    }

    
    Axios.get(API_URL + "/appointment/dermatologist/findAllHistoryPatientsAppointmets", {
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



    moveToFutureExamination =() => {

       this.props.history.push("/futureDermatologistAppointmentsForPatient");
    }



	handleSortByPriceAscending =() => {

		console.log("sortiranjeee");
		var appointmentType= "EXAMINATION";

		Axios.get(API_URL + "/appointment/dermatologist/findAllHistoryPatientsAppointmets/sortByPriceAscending/" + appointmentType , {
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


	handleSortByPriceDescending =() => {

		var appointmentType= "EXAMINATION";

		Axios.get(API_URL + "/appointment/dermatologist/findAllHistoryPatientsAppointmets/sortByPriceDescending/" + appointmentType , {
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

	handleSortByDateAscending =() => {


		console.log("sortiranjeee");
		var appointmentType= "EXAMINATION";

		Axios.get(API_URL + "/appointment/dermatologist/findAllHistoryPatientsAppointmets/sortByDateAscending/" + appointmentType , {
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


	handleSortByDateDescending =() => {

		
		var appointmentType= "EXAMINATION";

		Axios.get(API_URL + "/appointment/dermatologist/findAllHistoryPatientsAppointmets/sortByDateDescending/" + appointmentType , {
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


	handleSortByDurationAppointmentAscending =() => {

		var appointmentType= "EXAMINATION";

		Axios.get(API_URL + "/appointment/dermatologist/findAllHistoryPatientsAppointmets/sortByDurationAscending/" + appointmentType , {
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

	
	handleSortByDurationAppointmentDescending =() => {

		var appointmentType= "EXAMINATION";

		Axios.get(API_URL + "/appointment/dermatologist/findAllHistoryPatientsAppointmets/sortByDurationDescending/" + appointmentType , {
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




	render() {
	

		return (
      
      <React.Fragment>

      <Header/>
      
         <div className="container">



         <button type="button" class="btn btn-outline-primary btn-lg"
         onClick={() => this.moveToFutureExamination()}
         style={{  marginTop: "2em", marginLeft: "auto",marginRight: "auto" }}
         >
         Future Examination
        </button>


		<div className="container" style={{  marginTop: "2em" }}>

		<div className="dropdown">
			<button className="btn btn-primary btn-lg dropdown-toggle"
				type="button" id="dropdownMenu2"
				data-toggle="dropdown" 
				aria-haspopup="true" 
				aria-expanded="false">
				Sort
			</button>
			<div className="dropdown-menu" aria-labelledby="dropdownMenu2">
					<button className="dropdown-item" type="button" onClick={this.handleSortByPriceAscending} >Sort by price ascending</button>
					<button className="dropdown-item" type="button" onClick={this.handleSortByPriceDescending} >Sort by price descending</button>
					<button className="dropdown-item" type="button" onClick={this.handleSortByDateAscending} >Sort by date ascending</button>
					<button className="dropdown-item" type="button" onClick={this.handleSortByDateDescending} >Sort by date descending</button>
					<button className="dropdown-item" type="button" onClick={this.handleSortByDurationAppointmentAscending} >Sort by duration appointment ascending</button>
					<button className="dropdown-item" type="button" onClick={this.handleSortByDurationAppointmentDescending} >Sort by duration appointment descending</button>
			</div>
			</div>

		</div>	


         <h1 hidden={this.state.appointments.length === 0} className="text-center  mt-3  " >Your history appointments!</h1>
         <h1 hidden={this.state.appointments.length !== 0} className="text-center  mt-3 text-danger"  >You don't have future appointments!</h1>



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
									style={{ cursor: "pointer" }}
								>

                   <td width="100px">  
                     <img  src={DermatologistAppointmentPicture} style={{ width: "85px", marginTop: "15px" }}></img>                                 
                                    
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

                                         <div>
											<b>Dermatologist grade: </b> {appointment.EntityDTO.employee.EntityDTO.grade} {" "} 
											<i className="icon-star" style={{ color: "yellow"}}></i>
                      
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

export default HistoryDermatologistAppointmentsForPatient;