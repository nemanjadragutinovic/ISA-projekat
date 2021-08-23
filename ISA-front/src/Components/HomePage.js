import React from 'react';
import * as ReactBootStrap from "react-bootstrap";
import Header from './Header';
import TopBar from './TopBar';
import { Link } from "react-router-dom";
import GetAuthorisation from "../../src/Funciton/GetAuthorisation"
import Axios from "axios";

const API_URL="http://localhost:8080";


class HomePage extends React.Component {



  componentDidMount(){

        //if(this.hasSpecificRole("ROLE_PATIENT")){
         // this.checkPatientPenalty();
          //this.checkPatientsDrugsReservations()
      //  }


  }


    checkPatientPenalty(){

      Axios.get(API_URL + "/users/refreshPatientPenalty", {
        validateStatus: () => true,
        headers: { Authorization: GetAuthorisation() },
      })
        .then((res) => {
          if (res.status === 401) {
            this.props.history.push('/login');
          }
           
        })
        .catch((err) => {
          console.log(err);
        });

    }


    checkPatientsDrugsReservations(){

      Axios.get(API_URL + "/drug/refreshPatientDrugsReservations", {
        validateStatus: () => true,
        headers: { Authorization: GetAuthorisation() },
      })
        .then((res) => {
          if (res.status === 401) {
            this.props.history.push('/login');
          }
           
        })
        .catch((err) => {
          console.log(err);
        });

    }




      hasSpecificRole = (reqRole) => {
      let roles = JSON.parse(localStorage.getItem("keyRole"));

      if (roles === null) return false;

      for (let role of roles) {
        if (role === reqRole) return true;
      }
      return false;
      };

      hasAnyRole = (reqRole) => {
        let roles = JSON.parse(localStorage.getItem("keyRole"));
  
        if (roles === null) return false;

        return true;
        };

    



    
    render() {
      console.log(`Bearer ${localStorage.getItem("keyToken")}`);
    return (
      
      <React.Fragment>
     
          <Header/>
          <div >

          
          
          
          
          
          <section id="homePageSection" className="d-flex ">
					<div className="container" style={{textAlign: "center"}}>
						<h1>Welcome to Health Clinic </h1>

            <Link  to="/login" hidden={this.hasAnyRole()} className="btn-Login-Register">
							Login
						</Link>

						<Link  to="/registration" hidden={this.hasAnyRole()} className="btn-Login-Register">
							Register
						</Link>
      
					</div>
				
				  </section>

             
        
          </div>
          
       
      </React.Fragment>
    );
  }
}
  
  export default HomePage;