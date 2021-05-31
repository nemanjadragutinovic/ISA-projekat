import React, { Component } from "react";
import Axios from "axios";
import Header from '../Components/Header';
import GetAuthorisation from "../Funciton/GetAuthorisation";
import drugPicture from "../Images/medicament.jpg" ;
import UnsuccessfulAlert from "../Components/Alerts/UnsuccessfulAlert";
import SuccessfulAlert from "../Components/Alerts/SuccessfulAlert";
import {NavLink, Redirect } from "react-router-dom";
import HistoryPharmacistConsultation from "../Components/Consultations/HistoryPharmacistConsultation"
const API_URL="http://localhost:8080";

class FutureDrugsReservationsForPatients extends Component {
	
  
    
    state = {
        
        pharmacyId: "",
        drugsReservation : [],
        hiddenSuccessfulAlert: true,
        successfulHeader: "",
        successfulMessage: "",
        hiddenUnsuccessfulAlert: true,
        UnsuccessfulHeader: "",
        UnsuccessfulMessage: "",
        hideFutureButton : true,
        hideHistoryButton : false,
        historyConsultations : [],
        hideFutureConsultations : false,
        hideHistoryConsultations : true,


    };

    constructor(props) {
        super(props);
    }
  


  componentDidMount() {

    if (!this.hasRole("ROLE_PATIENT")) {
			this.props.history.push('/login');
    }

    
    Axios.get(API_URL + "/drug/futurePatientsDrugsReservation", {
			validateStatus: () => true,
			headers: { Authorization: GetAuthorisation() },
		})
			.then((res) => {
				if (res.status === 401) {
                    this.props.history.push('/login');
				} else {
					this.setState({ drugsReservation: res.data });
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



    
 
    moveToHistoryConsultation =() => {

       

		this.setState({ hideFutureButton : false,
			hideHistoryButton : true,
			hideFutureConsultations : true,
			hideHistoryConsultations : false,

		});



        Axios.get(API_URL + "/drug/futurePatientsDrugsReservations", {
			validateStatus: () => true,
			headers: { Authorization: GetAuthorisation() },
		})
			.then((res) => {
				if (res.status === 401) {
                    this.props.history.push('/login');
				} else {
					this.setState({ historyConsultations: res.data});
					console.log(res.data);
				}
			})
			.catch((err) => {
				console.log(err);
			});

       
    }


    moveToFutureConsultation =() => {

        this.setState({ hideFutureButton : true,
                        hideHistoryButton : false,
                        hideFutureConsultations : false,
                        hideHistoryConsultations : true,
        
        });

      this.componentDidMount();

       
    }

	handleCancelAppointment = (appointmentId) => {
  
		let appointmentIdObject = { id: appointmentId};
	
		Axios.post(API_URL + "/appointment/pharmacist/cancelAppointment",appointmentIdObject , {
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
	
			} 
			else if (res.status === 404) {
	
				this.setState({ hiddenUnsuccessfulAlert: false, 
				  UnsuccessfulHeader: "Internal server error", 
				  UnsuccessfulMessage: res.data });
	  
			} else if (res.status === 200) {
			  console.log("Success");
			  this.setState({
				hiddenSuccessfulAlert: false,
				successfulHeader: "Success",
				successfulMessage: "You have successfully canceled the desired appointment! ",
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
	
	  refreshAppointments (appointmentId) {
	
		let newAppointmentsList= [];
		for (let appointment of this.state.appointments) {
		  if (appointment.Id !== appointmentId) 
			  newAppointmentsList.push(appointment)
		}
	
		this.setState({
		 appointments : newAppointmentsList,
		});
	
	  }


	  isAvailableToCanceled =(date) => {

        var appointmentDate= new Date(date);
        appointmentDate.setDate(appointmentDate.getDate() -1);

        if(appointmentDate <= new Date()){
            return true;
        }

        return false;
    }


	

	

	render() {
	

		return (
      
      <React.Fragment>

      <Header/>
      
         <div className="container">



         <button type="button" class="btn btn-outline-primary btn-lg"
         onClick={() => this.moveToFutureConsultation()}
         hidden={this.state.hideFutureButton} 
         style={{  marginTop: "2em", marginLeft: "auto",marginRight: "auto" }}
         >
         Future reservation
        </button>


        <button type="button" class="btn btn-outline-primary btn-lg"
         onClick={() => this.moveToHistoryConsultation()}
         hidden={this.state.hideHistoryButton} 
         style={{  marginTop: "2em", marginLeft: "auto",marginRight: "auto" }}
         >
         History reservation
        </button>


         <h1 hidden={this.state.drugsReservation.length === 0 || this.state.hideFutureConsultations } className="text-center  mt-3  " >Your future drugs reservations!</h1>
         <h1 hidden={this.state.drugsReservation.length !== 0 || this.state.hideFutureConsultations} className="text-center  mt-3 text-danger"  >You don't have future drugs reservations!</h1>



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

        
   
        <div className="container" hidden={this.state.hideFutureConsultations }>
                    
          <table className="table table-hover" style={{ width: "70%", marginTop: "5em", marginLeft: "auto",marginRight: "auto" }}>
						<tbody>
							{this.state.drugsReservation.map((drugReservation) => (
								<tr
									id={drugReservation.Id}
									key={drugReservation.Id}
									style={{ cursor: "pointer" }}
								>

                   <td width="100px">  
                     <img  src={drugPicture} style={{ width: "85px", marginTop: "15px" }}></img>                                 
                                    
                    </td>
									
									<td>
										<div>
											<b>Start date: </b>{" "}
											{new Date(drugReservation.EntityDTO.startDate).toLocaleDateString("en-US", {
												day: "2-digit",
												month: "2-digit",
												year: "numeric",
											})}
										</div>
										<div>
											<b>Start time: </b>{" "}
											{new Date(drugReservation.EntityDTO.startDate).toLocaleTimeString("en-US", {
												hour: "2-digit",
												minute: "2-digit",
											})}
										</div>

                                        <div>
											<b>End date: </b>{" "}
											{new Date(drugReservation.EntityDTO.endDate).toLocaleDateString("en-US", {
												day: "2-digit",
												month: "2-digit",
												year: "numeric",
											})}
										</div>    

										<div>
											<b>End time: </b>{" "}
											{new Date(drugReservation.EntityDTO.endDate).toLocaleTimeString("en-US", {
												hour: "2-digit",
												minute: "2-digit",
											})}
										</div>
										<div>
											<b>Price :</b>{" "}
                                            <b style={{color : "red"}}> {(Math.round(drugReservation.EntityDTO.oneDrugPrice * drugReservation.EntityDTO.count * 100) / 100).toFixed(2)} </b>
                                            <b>  din</b>
                                             
										</div>

                  
										<div>
											<b>Pharmacy name : </b>{" "}
											{drugReservation.EntityDTO.pharmacy.EntityDTO.name }
										</div>

                                        <div>
											<b>Pharmacy address: </b>{" "}
											{drugReservation.EntityDTO.pharmacy.EntityDTO.address.street + ", " + drugReservation.EntityDTO.pharmacy.EntityDTO.address.city + ", " + drugReservation.EntityDTO.pharmacy.EntityDTO.address.country }
										</div>
										
									</td>

                                    
									<td>
                                    <button
											type="button"
                                            className="btn btn-outline-danger"
											style={{  marginTop: "2em" }}
											hidden={this.isAvailableToCanceled(new Date(drugReservation.EntityDTO.endDate))
                                                        || drugReservation.EntityDTO.drugReservationStatus === "CANCELED" 
                                                        || drugReservation.EntityDTO.drugReservationStatus === "EXPIRED" 
                                                        || drugReservation.EntityDTO.drugReservationStatus === "PROCESSED" 
                                            }
											onClick={() => this.handleCancelAppointment(drugReservation.Id)}
											
										>
											Cancel
										</button>
                                    </td>			



                                    
								</tr>
							))}
						</tbody>
					</table>
                </div>



          
        </div>


		<HistoryPharmacistConsultation

			hideHistoryConsultations={this.state.hideHistoryConsultations}
			appointments= {this.state.historyConsultations}

			handleSortByPriceAscending={this.handleSortByPriceAscending}
			handleSortByPriceDescending={this.handleSortByPriceDescending}
			handleSortByDateAscending={this.handleSortByDateAscending}
			handleSortByDateDescending={this.handleSortByDateDescending}
			handleSortByDurationAppointmentAscending={this.handleSortByDurationAppointmentAscending}
			handleSortByDurationAppointmentDescending={this.handleSortByDurationAppointmentDescending}								
		/>

								



        </React.Fragment>
        
		);
	}
}

export default FutureDrugsReservationsForPatients;