import React from 'react';
import * as ReactBootStrap from "react-bootstrap";
import Header from './Header';
import TopBar from './TopBar';
import { Link } from "react-router-dom";


class HomePage extends React.Component {


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
    return (
     
      <React.Fragment>
     
          <Header/>
          <div >

          
          
          
          
          
          <section id="homePageSection" className="d-flex ">
					<div className="container" style={{textAlign: "center"}}>
						<h1>Welcome to Health Clinic </h1>

            <Link  to="/login" hidden={this.hasSpecificRole("ROLE_PATIENT")} className="btn-Login-Register">
							Login
						</Link>

						<Link  to="/registration" hidden={this.hasSpecificRole("ROLE_PATIENT")} className="btn-Login-Register">
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