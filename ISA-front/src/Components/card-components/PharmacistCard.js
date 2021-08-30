import React, { Component } from "react";
import Modal from 'react-bootstrap/Modal'
import './card.styles.css'
import Picture from "../../Images/pharmacist.png" ;
import Axios from "axios";
import GetAuthorisation from "../../Funciton/GetAuthorisation";
import ScheduleModalPharmacist from "../Modal/ScheduleModalPharmacist";

const API_URL="http://localhost:8080";
class PharmacistCard extends Component {
	
    state = {
        openScheduleModal: false,
        workTimes:[],
        employee: ''
  };

  componentDidMount() {
        
}

  
  handleUpdateScheduleModal = () => {
        
    console.log("aaaaaaaaaaaaaa");
    
    Axios.get(API_URL + "/users/scheduleForEmployee/" + this.props.pharmacist.Id, {
        headers: { Authorization: GetAuthorisation() },
    })
    .then((res) => {
     
        this.setState({ workTimes: res.data});
        
        console.log(res.data);
       
    })
    .catch((err) => {
        console.log(err);
    });
    };

    handleScheduleModal = (id) => {
        
        console.log("aaaaaaaaaaaaaa");
        console.log(id);
        Axios.get(API_URL + "/users/scheduleForEmployee/" + id, {
			headers: { Authorization: GetAuthorisation() },
		})
        .then((res) => {
            this.setState({ workTimes: res.data , employee:id});
            console.log(res.data);
            console.log(id);
        })
        .catch((err) => {
            console.log(err);
        });
        this.setState({
            openScheduleModal: true
        });
        };

        handleModalClose = () => {
            this.setState({openScheduleModal: false});
        }

        handleUpdateDermatologists =() =>{
            this.setState({openScheduleModal: false});
            this.props.updateDermatologists();
        }
	render() {
	
        console.log(this.props.pharmacist);
        console.log(this.props.pharmacyId)
		return (
      
      <React.Fragment>
        <button className="card-style" onClick={() => this.handleScheduleModal(this.props.pharmacist.Id)}>
        <img className="img-fluid" src={Picture}  />
        <h2>{this.props.pharmacist.EntityDTO.name} {this.props.pharmacist.EntityDTO.surname}</h2>
        <p>Email: {this.props.pharmacist.EntityDTO.email}</p>
        <p>Phone: {this.props.pharmacist.EntityDTO.phoneNumber}</p>
        <p>Grade: {this.props.pharmacist.EntityDTO.grade} <i className="icon-star" style={{ color: "yellow"}}></i> </p>
        
        
        </button>
        
        <ScheduleModalPharmacist show={this.state.openScheduleModal} update={this.handleUpdateScheduleModal} updateDermatologists={this.handleUpdateDermatologists} onCloseModal={this.handleModalClose} workTimes={this.state.workTimes}  employee ={this.props.pharmacist.Id} pharmacyId={this.props.pharmacyId} header="WorkTimes" />
	    </React.Fragment>
        
        
		);
	}
}

export default PharmacistCard;
