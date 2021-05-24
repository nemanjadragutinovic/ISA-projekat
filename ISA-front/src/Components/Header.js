import React from 'react';
import * as ReactBootStrap from "react-bootstrap";
import {
    BrowserRouter as Router,
    Link
  } from "react-router-dom";






class Header extends React.Component{


      handleLogout = () => {
      localStorage.removeItem("keyToken");
      localStorage.removeItem("keyRole");
      localStorage.removeItem("expireTime");
    };

    hasRole = (requestRole) => {
      let currentRoles = JSON.parse(localStorage.getItem("keyRole"));

      if (currentRoles === null) return false;


      for (let currentRole of currentRoles) {
        if (currentRole === requestRole) return true;
      }
      return false;
    };

    IsLogedIn = () => {
      let currentRoles = JSON.parse(localStorage.getItem("keyRole"));

      if (currentRoles === null) return false;

      
      return true;
    };

    render(){
      
      
        
    
    
          return(
                  
              

                <div id="header">

          


                <ReactBootStrap.Navbar collapseOnSelect expand="xl" bg="primary" variant="dark">
                <Link to="/">
                <ReactBootStrap.Navbar.Brand  style={{fontSize : "35px"}}>Health clinic</ReactBootStrap.Navbar.Brand>
                </Link>
                <ReactBootStrap.Navbar.Toggle aria-controls="responsive-navbar-nav" />
                
                <ReactBootStrap.Navbar.Collapse  id="responsive-navbar-nav">
              
                <ReactBootStrap.Nav className="ml-auto" >
            
            
            
                <Link to="/drugs">
                <ReactBootStrap.Nav.Link href="#deets">Drugs</ReactBootStrap.Nav.Link>
                </Link>

                <Link to="/pharmacies">
                <ReactBootStrap.Nav.Link href="#deets">Pharmacies</ReactBootStrap.Nav.Link>
                </Link>
            

                
                <ReactBootStrap.NavDropdown alignRight title="My reports" id="collasible-nav-dropdown">
                    <ReactBootStrap.NavDropdown.Item href="/futureDermatologistAppointmentsForPatient">Dermatologist</ReactBootStrap.NavDropdown.Item>
                    <ReactBootStrap.NavDropdown.Divider />
                    <ReactBootStrap.NavDropdown.Item href="/futurePharmaciesConsultationsForPatient">Pharmacies</ReactBootStrap.NavDropdown.Item>
                    
                </ReactBootStrap.NavDropdown>




                <ReactBootStrap.NavDropdown alignRight title="Apointment" id="collasible-nav-dropdown">
                    <ReactBootStrap.NavDropdown.Item href="/pharmacistAppointment">Pharmacist</ReactBootStrap.NavDropdown.Item>
                    <ReactBootStrap.NavDropdown.Divider />
                    <ReactBootStrap.NavDropdown.Item href="/dermatologistAppointment">Dermatologist</ReactBootStrap.NavDropdown.Item>
                    
                </ReactBootStrap.NavDropdown>

                <ReactBootStrap.NavDropdown alignRight title="Register" hidden={!this.hasRole("ROLE_SYSADMIN")} id="collasible-nav-dropdown">
                    <ReactBootStrap.NavDropdown.Item href="/registerStaff" >Stuff member</ReactBootStrap.NavDropdown.Item>
                    <ReactBootStrap.NavDropdown.Item href="/registerPharmacies" >Pharmacy</ReactBootStrap.NavDropdown.Item>
                    <ReactBootStrap.NavDropdown.Item href="/registerDrug" >Drug</ReactBootStrap.NavDropdown.Item>
                    
                    
                </ReactBootStrap.NavDropdown>

                <Link to="/loyalityProgram">
                <ReactBootStrap.Nav.Link href="#deets" hidden={!this.hasRole("ROLE_SYSADMIN")} >Loyality Program</ReactBootStrap.Nav.Link>
                </Link>

                <Link to="/orders">
                <ReactBootStrap.Nav.Link href="#deets" hidden={!this.hasRole("ROLE_SUPPLIER")} >Orders</ReactBootStrap.Nav.Link>
                </Link>

                  
                <ReactBootStrap.NavDropdown alignRight title="User" id="collasible-nav-dropdown">
                    <ReactBootStrap.NavDropdown.Item href="/login" hidden={this.IsLogedIn()}>Login</ReactBootStrap.NavDropdown.Item>
                    <ReactBootStrap.NavDropdown.Divider hidden={this.IsLogedIn()} />
                    <ReactBootStrap.NavDropdown.Item href="/registration" hidden={this.IsLogedIn()}>Register</ReactBootStrap.NavDropdown.Item>

                    
                    <ReactBootStrap.NavDropdown.Item href="/userProfile" hidden={!this.hasRole("ROLE_PATIENT")}>My profile</ReactBootStrap.NavDropdown.Item>
                    <ReactBootStrap.NavDropdown.Divider hidden={!this.IsLogedIn()} />
                    <ReactBootStrap.NavDropdown.Item onClick={this.handleLogout} href="/login" hidden={!this.IsLogedIn("*")}>Log out</ReactBootStrap.NavDropdown.Item>

                </ReactBootStrap.NavDropdown>
            
            
                </ReactBootStrap.Nav>
            
              </ReactBootStrap.Navbar.Collapse>
            
            </ReactBootStrap.Navbar>
            
            
                    </div>



              )
    }


}

export default Header;