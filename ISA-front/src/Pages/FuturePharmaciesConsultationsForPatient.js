import React, { Component } from "react";
import Axios from "axios";
import Header from '../Components/Header';
import GetAuthorisation from "../Funciton/GetAuthorisation";
import PharmacistAppointmentPicture from "../Images/pharmacist.png" ;
import UnsuccessfulAlert from "../Components/Alerts/UnsuccessfulAlert";
import SuccessfulAlert from "../Components/Alerts/SuccessfulAlert";
import {NavLink, Redirect } from "react-router-dom";
import HistoryPharmacistConsultation from "../Components/Consultations/HistoryPharmacistConsultation"
import FirstGradeModal from "../Components/Modal/FirstGradeModal";



const API_URL="http://localhost:8080";

class FuturePharmaciesConsultationsForPatient extends Component {
	
  
    
    state = {
        
        pharmacyId: "",
        appointments : [],
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


		selectedEmployee : [],
		employeeId : "",
		employeeGrade : 0,
		employeeName : "",
		employeeSurname : "",
		showGradeModal: false,
		showFirstGrade : false,
		showModifyGrade : false,
		maxGrade : 5

    };

    constructor(props) {
        super(props);
    }
  


  componentDidMount() {

    if (!this.hasRole("ROLE_PATIENT")) {
			this.props.history.push('/login');
    }

    
    Axios.get(API_URL + "/appointment/pharmacist/findAllFuturePatientsAppointmets", {
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



    
 
    moveToHistoryConsultation =() => {

       

		this.setState({ hideFutureButton : false,
			hideHistoryButton : true,
			hideFutureConsultations : true,
			hideHistoryConsultations : false,

		});



        Axios.get(API_URL + "/appointment/pharmacist/findAllHistoryPatientsAppointmets", {
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


	
	handleSortByPriceAscending =() => {

		console.log("sortiranjeee");

		var appointmentType= "CONSULTATION";

		Axios.get(API_URL + "/appointment/pharmacist/findAllHistoryPatientsAppointmets/sortByPriceAscending/" + appointmentType , {
			validateStatus: () => true,
			headers: { Authorization: GetAuthorisation() },
		})
			.then((res) => {
				if (res.status === 401) {
                    this.props.history.push('/login');
				} else {
					this.setState({ historyConsultations: res.data });
					console.log(res.data);
				}
			})
			.catch((err) => {
				console.log(err);
			});
        
    }


	handleSortByPriceDescending =() => {

		var appointmentType= "CONSULTATION";

		Axios.get(API_URL + "/appointment/pharmacist/findAllHistoryPatientsAppointmets/sortByPriceDescending/" + appointmentType , {
			validateStatus: () => true,
			headers: { Authorization: GetAuthorisation() },
		})
			.then((res) => {
				if (res.status === 401) {
                    this.props.history.push('/login');
				} else {
					this.setState({ historyConsultations: res.data });
					console.log(res.data);
				}
			})
			.catch((err) => {
				console.log(err);
			});
        
    }

	handleSortByDateAscending =() => {


		console.log("sortiranjeee");
		var appointmentType= "CONSULTATION";

		Axios.get(API_URL + "/appointment/pharmacist/findAllHistoryPatientsAppointmets/sortByDateAscending/" + appointmentType , {
			validateStatus: () => true,
			headers: { Authorization: GetAuthorisation() },
		})
			.then((res) => {
				if (res.status === 401) {
                    this.props.history.push('/login');
				} else {
					this.setState({ historyConsultations: res.data });
					console.log(res.data);
				}
			})
			.catch((err) => {
				console.log(err);
			});
        
    }


	handleSortByDateDescending =() => {

		
		var appointmentType= "CONSULTATION";

		Axios.get(API_URL + "/appointment/pharmacist/findAllHistoryPatientsAppointmets/sortByDateDescending/" + appointmentType , {
			validateStatus: () => true,
			headers: { Authorization: GetAuthorisation() },
		})
			.then((res) => {
				if (res.status === 401) {
                    this.props.history.push('/login');
				} else {
					this.setState({ historyConsultations: res.data });
					console.log(res.data);
				}
			})
			.catch((err) => {
				console.log(err);
			});
        
    }


	handleSortByDurationAppointmentAscending =() => {

		var appointmentType= "CONSULTATION";

		Axios.get(API_URL + "/appointment/pharmacist/findAllHistoryPatientsAppointmets/sortByDurationAscending/" + appointmentType , {
			validateStatus: () => true,
			headers: { Authorization: GetAuthorisation() },
		})
			.then((res) => {
				if (res.status === 401) {
                    this.props.history.push('/login');
				} else {
					this.setState({ historyConsultations: res.data });
					console.log(res.data);
				}
			})
			.catch((err) => {
				console.log(err);
			});

        
    }

	
	handleSortByDurationAppointmentDescending =() => {

		var appointmentType= "CONSULTATION";

		Axios.get(API_URL + "/appointment/pharmacist/findAllHistoryPatientsAppointmets/sortByDurationDescending/" + appointmentType , {
			validateStatus: () => true,
			headers: { Authorization: GetAuthorisation() },
		})
			.then((res) => {
				if (res.status === 401) {
                    this.props.history.push('/login');
				} else {
					this.setState({ historyConsultations: res.data });
					console.log(res.data);
				}
			})
			.catch((err) => {
				console.log(err);
			});

        
    }

	
	handleGetGradeClick = (employee) => {
		console.log(employee);




		Axios.get(API_URL + "/grade/employee/" + employee.Id , {
			validateStatus: () => true,
			headers: { Authorization: GetAuthorisation() },
		})
			.then((res) => {
				if (res.status === 401) {
                    this.props.history.push('/login');
				} else if(res.status === 404){


					console.log("Nema ocenu");

					let entityDTO = {
						showGradeModal : true,
							showFirstGrade : true,	
						employeeId : employee.EntityDTO.Id,
						employeeGrade : employee.EntityDTO.grade,
						employeeName : employee.EntityDTO.name,
						employeeSurname : employee.EntityDTO.surname
					};


					this.setState({ showGradeModal : true,
							showFirstGrade : true,	
						employeeId : employee.Id,
						employeeGrade : employee.EntityDTO.grade,
						employeeName : employee.EntityDTO.name,
						employeeSurname : employee.EntityDTO.surname});

						console.log(employee.Id);
						console.log(entityDTO);

				}else {
						
					this.setState({ showGradeModal : true,
						showModifyGrade : true,	
						employeeId : res.data.employee.id,
						employeeGrade : res.data.grade,
						employeeName : res.data.employee.name,
						employeeSurname : res.data.employee.surname});

						console.log(res.data.grade);
						console.log(res.data);
					
				}
			})
			.catch((err) => {
				console.log(err);
			});


	};


	setFirstGrade = (grade) => {
		
		console.log("sou")

		let entityDTO = {
			employeeId: this.state.employeeId ,
			grade: grade,
		};

		console.log(entityDTO);

		Axios.post(API_URL + "/grade/employee/createGrade",entityDTO , {
			validateStatus: () => true,
			headers: { Authorization: GetAuthorisation() },
		})
			.then((res) => {
				if (res.status === 401) {
                    this.props.history.push('/login');
				} else if(res.status === 404){
					this.setState({ hiddenUnsuccessfulAlert: false, UnsuccessfulHeader : "Bad request", UnsuccessfulMessage : "You are not authorized to create grade! ",
					 showGradeModal :false,
					 showFirstGrade : false,
					showModifyGrade : false});

				}else if(res.status === 500){
					this.setState({ hiddenUnsuccessfulAlert: false, UnsuccessfulHeader : "Error", UnsuccessfulMessage : "internal server error! ",
					 showGradeModal :false,
					 showFirstGrade : false,
					 showModifyGrade : false  });

				}else {
						
					this.setState({ hiddenSuccessfulAlert:  false, successfulHeader:   "Successful", successfulMessage:  "You successful created grade for employee! ",
					 showGradeModal :false,
					 showFirstGrade : false,
					 showModifyGrade : false   });

					this.moveToHistoryConsultation();
					
				}
			})
			.catch((err) => {
				console.log(err);
			});






	};

	setModifyGrade = (grade) => {
		
		console.log("sou")

		let entityDTO = {
			employeeId: this.state.employeeId ,
			grade: grade,
		};

		console.log(entityDTO);


		Axios.post(API_URL + "/grade/employee/updateGrade",entityDTO , {
			validateStatus: () => true,
			headers: { Authorization: GetAuthorisation() },
		})
			.then((res) => {
				if (res.status === 401) {
                    this.props.history.push('/login');
				} else if(res.status === 404){
					this.setState({ hiddenUnsuccessfulAlert: false, UnsuccessfulHeader : "Bad request", UnsuccessfulMessage : "You are not authorized to create grade! ",
					 showGradeModal :false,
					 showFirstGrade : false,
					showModifyGrade : false});

				}else if(res.status === 500){
					this.setState({ hiddenUnsuccessfulAlert: false, UnsuccessfulHeader : "Error", UnsuccessfulMessage : "internal server error! ",
					 showGradeModal :false,
					 showFirstGrade : false,
					 showModifyGrade : false  });

				}else {
						
					this.setState({ hiddenSuccessfulAlert:  false, successfulHeader:   "Successful", successfulMessage:  "You successful update grade for employee! ",
					 showGradeModal :false,
					 showFirstGrade : false,
					 showModifyGrade : false   });

					this.moveToHistoryConsultation();
					
				}
			})
			.catch((err) => {
				console.log(err);
			});



	};
	
	closeFirstGradeModal = () => {

		this.setState({ showFirstGrade : false,
			showModifyGrade : false,
			showGradeModal : false});

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
         Future consultations
        </button>


        <button type="button" class="btn btn-outline-primary btn-lg"
         onClick={() => this.moveToHistoryConsultation()}
         hidden={this.state.hideHistoryButton} 
         style={{  marginTop: "2em", marginLeft: "auto",marginRight: "auto" }}
         >
         History consultations
        </button>


         <h1 hidden={this.state.appointments.length === 0 || this.state.hideFutureConsultations } className="text-center  mt-3  " >Your future pharmacist consultations!</h1>
         <h1 hidden={this.state.appointments.length !== 0 || this.state.hideFutureConsultations} className="text-center  mt-3 text-danger"  >You don't have future pharmacist consultations!</h1>



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
							{this.state.appointments.map((appointment) => (
								<tr
									id={appointment.Id}
									key={appointment.Id}
									style={{ cursor: "pointer" }}
								>

                   <td width="100px">  
                     <img  src={PharmacistAppointmentPicture} style={{ width: "85px", marginTop: "15px" }}></img>                                 
                                    
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
											<b>Pharmacist: </b>{" "}
											{appointment.EntityDTO.employee.EntityDTO.name + " " + appointment.EntityDTO.employee.EntityDTO.surname}
										</div>

                                         <div>
											<b>Pharmacist grade: </b> {appointment.EntityDTO.employee.EntityDTO.grade} {" "} 
											<i className="icon-star" style={{ color: "yellow"}}></i>
                      
										</div>
										
									</td>

                                    
									<td>
                                    <button
											type="button"
                                            className="btn btn-outline-danger"
											style={{  marginTop: "2em" }}
											hidden={this.isAvailableToCanceled(new Date(appointment.EntityDTO.startDateTime))}
											onClick={() => this.handleCancelAppointment(appointment.Id)}
											
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
			handleGetGradeClick={this.handleGetGradeClick}								
		/>

								
		<FirstGradeModal 

				show={this.state.showGradeModal}
				showFirstGrade={this.state.showFirstGrade}
				showModifyGrade={this.state.showModifyGrade}
				employeeGrade={this.state.employeeGrade}							
				maxGrade={this.state.maxGrade}
				employeeName={this.state.employeeName }
				employeeSurname={this.state.employeeSurname }
				header={"Grade"}
				buttonFirstGradeName={"Grade"}
				buttonModifyGradeName={" Update grade"}								
				setFirstGrade={this.setFirstGrade}	
				setModifyGrade={this.setModifyGrade}
				onCloseModal={this.closeFirstGradeModal}						

		/>											


        </React.Fragment>
        
		);
	}
}

export default FuturePharmaciesConsultationsForPatient;