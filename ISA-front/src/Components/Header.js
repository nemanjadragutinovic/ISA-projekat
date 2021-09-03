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
            
                <Link to="/dermatologistsForPhAdmin">
                <ReactBootStrap.Nav.Link href="#deets" hidden={!this.hasRole("ROLE_PATIENT")}>Dermatologist</ReactBootStrap.Nav.Link>
                </Link>
                <Link to="/pharmacistsForPhAdmin">
                <ReactBootStrap.Nav.Link href="#deets" hidden={!this.hasRole("ROLE_PATIENT")}>Pharmacist</ReactBootStrap.Nav.Link>
                </Link>
                <Link to="/drugs">
                <ReactBootStrap.Nav.Link href="#deets" hidden={this.hasRole("ROLE_PHARMACYADMIN")}>Drugs</ReactBootStrap.Nav.Link>
                </Link>

                <Link to="/pharmacies">
                <ReactBootStrap.Nav.Link href="#deets" hidden={this.hasRole("ROLE_PHARMACYADMIN")}>Pharmacies</ReactBootStrap.Nav.Link>
                </Link>
                
                <Link to="/pharmacies">
                <ReactBootStrap.Nav.Link href="#deets" hidden={!this.hasRole("ROLE_PATIENT") || !this.hasRole("ROLE_SYSADMIN")}>Pharmacies</ReactBootStrap.Nav.Link>
                </Link>
              
                <Link to="/adminpharmacy">
                <ReactBootStrap.Nav.Link href="#deets" hidden={!this.hasRole("ROLE_PHARMACYADMIN")}>My Pharmacy</ReactBootStrap.Nav.Link>
                </Link>

                <Link to="/dermatologistsForPhAdmin">
                <ReactBootStrap.Nav.Link href="#deets" hidden={!this.hasRole("ROLE_PHARMACYADMIN")}>Dermatologist</ReactBootStrap.Nav.Link>
                </Link>

                <Link to="/pharmacistsForPhAdmin">
                <ReactBootStrap.Nav.Link href="#deets" hidden={!this.hasRole("ROLE_PHARMACYADMIN") }>Pharmacist</ReactBootStrap.Nav.Link>
                </Link>

              
                <Link to="/drugsForPhAdmin">
                <ReactBootStrap.Nav.Link href="#deets" hidden={!this.hasRole("ROLE_PHARMACYADMIN")}>Drugs</ReactBootStrap.Nav.Link>
                </Link>
              
                <Link to="/actions">
                <ReactBootStrap.Nav.Link href="#deets" hidden={!this.hasRole("ROLE_PHARMACYADMIN")}>Actions</ReactBootStrap.Nav.Link>
                </Link>
                
                <Link to="/ordersForPhAdmin">
                <ReactBootStrap.Nav.Link href="#deets" hidden={!this.hasRole("ROLE_PHARMACYADMIN")}>Orders</ReactBootStrap.Nav.Link>
                </Link>
                
                <ReactBootStrap.NavDropdown alignRight title="My reports" id="collasible-nav-dropdown" hidden={!this.hasRole("ROLE_PATIENT")} >

                    <ReactBootStrap.NavDropdown.Item href="/futureDermatologistAppointmentsForPatient">Dermatologist</ReactBootStrap.NavDropdown.Item>
                    <ReactBootStrap.NavDropdown.Divider />
                    <ReactBootStrap.NavDropdown.Item href="/futurePharmaciesConsultationsForPatient">Pharmacies</ReactBootStrap.NavDropdown.Item>
                    <ReactBootStrap.NavDropdown.Divider />
                    <ReactBootStrap.NavDropdown.Item href="/patientsSubscribedPharmacies">Subscribed pharmacies</ReactBootStrap.NavDropdown.Item>
                    <ReactBootStrap.NavDropdown.Divider />
                    <ReactBootStrap.NavDropdown.Item href="/futureDrugsReservationForPatient">Drugs reservations</ReactBootStrap.NavDropdown.Item>
                    <ReactBootStrap.NavDropdown.Divider />
                    <ReactBootStrap.NavDropdown.Item href="/allPatients-E-receipts">E-receipts</ReactBootStrap.NavDropdown.Item>
                </ReactBootStrap.NavDropdown>






                <ReactBootStrap.NavDropdown alignRight title="Apointment" id="collasible-nav-dropdown" hidden={!this.hasRole("ROLE_PATIENT")} >

                    <ReactBootStrap.NavDropdown.Item href="/pharmacistAppointment">Pharmacist</ReactBootStrap.NavDropdown.Item>
                    <ReactBootStrap.NavDropdown.Divider />
                    <ReactBootStrap.NavDropdown.Item href="/dermatologistAppointment">Dermatologist</ReactBootStrap.NavDropdown.Item>
                    
                </ReactBootStrap.NavDropdown>

                <Link to="/qrCode">
                <ReactBootStrap.Nav.Link href="#deets" hidden={!this.hasRole("ROLE_PATIENT")} >Scan QR code</ReactBootStrap.Nav.Link>
                </Link>

                <ReactBootStrap.NavDropdown alignRight title="Register" hidden={!this.hasRole("ROLE_SYSADMIN")} id="collasible-nav-dropdown">
                    <ReactBootStrap.NavDropdown.Item href="/registerStaff" >Stuff member</ReactBootStrap.NavDropdown.Item>
                    <ReactBootStrap.NavDropdown.Item href="/registerPharmacies" >Pharmacy</ReactBootStrap.NavDropdown.Item>
                    <ReactBootStrap.NavDropdown.Item href="/registerDrug" >Drug</ReactBootStrap.NavDropdown.Item>
                    
                    
                </ReactBootStrap.NavDropdown>
                <ReactBootStrap.NavDropdown alignRight title="Complaints" hidden={!this.hasRole("ROLE_SYSADMIN")} id="collasible-nav-dropdown">
                    <ReactBootStrap.NavDropdown.Item href="/staffComplains" >Employee complaints</ReactBootStrap.NavDropdown.Item>
                    <ReactBootStrap.NavDropdown.Item href="/pharmacyComplains" >Pharmacy complaints</ReactBootStrap.NavDropdown.Item>
                    
                    
                    
                </ReactBootStrap.NavDropdown>

                <Link to="/loyalityProgram">
                <ReactBootStrap.Nav.Link href="#deets" hidden={!this.hasRole("ROLE_SYSADMIN")} >Loyality Program</ReactBootStrap.Nav.Link>
                </Link>

                <Link to="/orders">
                <ReactBootStrap.Nav.Link href="#deets" hidden={!this.hasRole("ROLE_SUPPLIER")} >Orders</ReactBootStrap.Nav.Link>
                </Link>

                <Link to="/offers">
                <ReactBootStrap.Nav.Link href="#deets" hidden={!this.hasRole("ROLE_SUPPLIER")} >Offers</ReactBootStrap.Nav.Link>
                </Link>

                <Link to="/calendar">
                <ReactBootStrap.Nav.Link href="#deets" hidden={!this.hasRole("ROLE_DERMATHOLOGIST") && !this.hasRole("ROLE_PHARMACIST")} >Calendar</ReactBootStrap.Nav.Link>
                </Link>

                <Link to="/patients">
                <ReactBootStrap.Nav.Link href="#deets" hidden={!this.hasRole("ROLE_DERMATHOLOGIST") && !this.hasRole("ROLE_PHARMACIST")} >Patients</ReactBootStrap.Nav.Link>
                </Link>

                  
                <ReactBootStrap.NavDropdown alignRight title="User" id="collasible-nav-dropdown">
                    <ReactBootStrap.NavDropdown.Item href="/login" hidden={this.IsLogedIn()}>Login</ReactBootStrap.NavDropdown.Item>
                    <ReactBootStrap.NavDropdown.Divider hidden={this.IsLogedIn()} />
                    <ReactBootStrap.NavDropdown.Item href="/registration" hidden={this.IsLogedIn()}>Register</ReactBootStrap.NavDropdown.Item>


                   
                    

                    <ReactBootStrap.NavDropdown.Item href="/userProfile" hidden={(!this.hasRole("ROLE_DERMATHOLOGIST") && !this.hasRole("ROLE_PHARMACIST") && !this.hasRole("ROLE_PATIENT") && !this.hasRole("ROLE_SUPPLIER") && !this.hasRole("ROLE_PHARMACYADMIN"))}>My profile</ReactBootStrap.NavDropdown.Item>

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