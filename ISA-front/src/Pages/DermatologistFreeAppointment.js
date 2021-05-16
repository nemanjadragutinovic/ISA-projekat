import React, { Component } from "react";
import Axios from "axios";
import Header from '../Components/Header';
import { withRouter } from "react-router";
import { Redirect } from "react-router-dom";
import GetAuthorisation from "../Funciton/GetAuthorisation";

const API_URL="http://localhost:8080";

class DermatologistFreeAppointment extends Component {
	
  
    
    state = {
        
        pharmacyId: "",
        appointments : [],
        



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
      
    Axios.get(API_URL + "/api/appointment/dermatologist/find-by-pharmacy/" + id, {
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




	render() {
	

		return (
      
      <React.Fragment>

      <Header/>
      
         <div>

          
        </div>
        </React.Fragment>
        
		);
	}
}

export default withRouter(DermatologistFreeAppointment);