import React, { Component } from "react";
import Axios from "axios";
import Header from '../Components/Header';
import { withRouter } from "react-router";
import { Redirect } from "react-router-dom";


const API_URL="http://localhost:8080";

class DermatologistFreeAppointment extends Component {
	
  
    
    state = {
        
        pharmacyId: "",
		


    };

  
  

  setPharmacyIdFromUrl = (id) => {
        
    this.setState({
    
        pharmacyId : id
      });


    };

  componentDidMount() {

    const pathParams= window.location.pathname;
    const paramsList= pathParams.split("/");
    const id = paramsList[2];

    console.log(id);

    this.setPharmacyIdFromUrl(id);
      
      

}




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