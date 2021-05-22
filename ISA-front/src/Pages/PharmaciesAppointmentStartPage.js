import React, { Component } from "react";
import Axios from "axios";
import Header from '../Components/Header';
import GetAuthorisation from "../Funciton/GetAuthorisation";
import DermatologistAppointmentPicture from "../Images/appointment.png" ;
import UnsuccessfulAlert from "../Components/Alerts/UnsuccessfulAlert";
import SuccessfulAlert from "../Components/Alerts/SuccessfulAlert";
import {NavLink, Redirect } from "react-router-dom";
import DatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";
import FoundPharmaciesForDateRange from "../Components/Pharmacies/FoundPharmaciesForDateRange"


const API_URL="http://localhost:8080";

class PharmaciesAppointmentStartPage extends Component {
	
  
    
    state = {
        
        selectedDate: new Date(),
		hours: new Date().getHours(),
		minutes: new Date().getMinutes(),
        pharmacies: [],
		hiddenPharmacies: true,

        hiddenUnsuccessfulAlert: true,
        UnsuccessfulHeader: "",
        UnsuccessfulMessage: "",

		isPharmaciesEmpty : false

    };

    constructor(props) {
        super(props);
    }
  


  componentDidMount() {

    if (!this.hasRole("ROLE_PATIENT")) {
			this.props.history.push('/login');
    }

  
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


    handleDateChange = (date) => {
		this.setState({ selectedDate: date });
	};

	handleMinutesChange = (event) => {
		if (event.target.value < 0) this.setState({ minutes: 0 });
        else if (event.target.value > 59) this.setState({ minutes: 59 });
		else this.setState({ minutes: event.target.value });
	};

	handleHoursChange = (event) => {
        if (event.target.value < 0) this.setState({ hours: 0 });
		else if (event.target.value > 23) this.setState({ hours: 23 });
		else this.setState({ hours: event.target.value });
	};
    
    
    CheckAvailabilityAppointment = () => {
        

		this.setState({
			
			consultationDate: new Date(
				this.state.selectedDate.getFullYear(),
				this.state.selectedDate.getMonth(),
				this.state.selectedDate.getDate(),
				this.state.hours,
				this.state.minutes,
				0,
				0
			).getTime(),

			hiddenUnsuccessfulAlert: true,
			UnsuccessfulHeader: "",
			UnsuccessfulMessage: "",
		});

		let acceptableDate = new Date().getTime() + 3600;
	
		let consultationDateSelected= new Date(
			this.state.selectedDate.getFullYear(),
			this.state.selectedDate.getMonth(),
			this.state.selectedDate.getDate(),
			this.state.hours,
			this.state.minutes,
			0,
			0
		).getTime();

		
	    
             console.log("sok");
			Axios.get( API_URL + "/pharmacy/getAllFreePharmacyAppointmetsForSelectedDate/" + consultationDateSelected  , {
			validateStatus: () => true,
			headers: { Authorization: GetAuthorisation() }},
			)
				.then((res) => {
					 if (res.status === 200) {
						this.setState({ pharmacies: res.data });
						if(this.state.pharmacies.length!==0){
							this.setState({hiddenPharmacies: false, isPharmaciesEmpty: false });
						}else{
							this.setState({isPharmaciesEmpty: true });
						}
						console.log(res.data);
					}
					 if(res.status === 401){
                        this.setState({ hiddenUnsuccessfulAlert: false, 
                            UnsuccessfulHeader: "Internal server error", 
                            UnsuccessfulMessage: res.data });
                    }
				})
				.catch((err) => {
					console.log(err);
                    this.setState({ hiddenUnsuccessfulAlert: false, 
                        UnsuccessfulHeader: "Error", 
                        UnsuccessfulMessage: "Some error" });
                    
				});
		
	};


   

	render() {
	

		return (
      
      <React.Fragment>

         <Header/>
      
         <div className="container" hidden={!this.state.hiddenPharmacies} >


         <UnsuccessfulAlert
				hidden={this.state.hiddenUnsuccessfulAlert}
				header={this.state.UnsuccessfulHeader}
				message={this.state.UnsuccessfulMessage}
				handleCloseAlert={this.handleCloseUnsuccessfulAlert}
		/>
       


         <h1  className="text-center  mt-3  " >Please select the desired date for consultations with a pharmacist!</h1>
         
		 <h5 className=" text-center  mt-3  text-danger" hidden={!this.state.isPharmaciesEmpty}>
						We couldn't find pharmacies with free pharmacists at desired time. Please change date and time.
		</h5>

         <div className="control-group" style={{ marginTop: "5em" }}>
						<div className="form-row justify-content-center">
                            <div className="mr-2">
								<div style={{ fontSize: "20px" }}>
                                    <b>Select date:</b>
								</div>

								<DatePicker
									className="form-control"
									minDate={new Date()}
									onChange={(date) => this.handleDateChange(date)}
									selected={this.state.selectedDate}
								/>
						    </div>
							<div className="mr-2">
								<div style={{ fontSize: "20px" }}>
									<b>Hours:</b>
								</div>
								<input
                                    type="number"
									min="00"
									max="23"
                                    className="form-control"
									onChange={this.handleHoursChange}
									value={this.state.hours}
									style={{ width: "12em" }}
									
								/>
							</div>
							<div className="mr-2">
								<div style={{ fontSize: "20px" }}>
                                    <b>Minutes:</b>
								</div>
								<input
									min="00"
                                    max="59"
                                    type="number"
									className="form-control"
									onChange={this.handleMinutesChange}
									value={this.state.minutes}
									style={{ width: "12em" }}
									
									
								/>
							</div>
						</div>

						<div class="container">
                            <div class="row">
                                <div class="col text-center">
						            <button type="button" class="btn btn-outline-primary btn-lg "
                                        onClick={() => this.CheckAvailabilityAppointment()}
                                        style={{  marginTop: "2em", marginLeft: "auto",marginRight: "auto" }}
                                        >
                                        <b>Check appointment </b>

                                     </button>

                                </div>
                             </div>
                        </div>


					</div>

          
        </div>


		<FoundPharmaciesForDateRange

				hiddenPharmacies= {this.state.hiddenPharmacies}
				pharmacies={this.state.pharmacies}


		/>

			




		


        </React.Fragment>
        
		);
	}
}

export default PharmaciesAppointmentStartPage;