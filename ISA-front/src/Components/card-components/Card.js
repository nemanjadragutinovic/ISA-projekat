import React, { Component } from "react";
import Modal from 'react-bootstrap/Modal'
import './card.styles.css'
import Picture from "../../Images/dermatologist.png" ;
import ScheduleModal from "../Modal/ScheduleModal";
import Header from '../Header';
import Axios from "axios";
import GetAuthorisation from "../../Funciton/GetAuthorisation";

const API_URL="http://localhost:8080";
class Card extends Component {
	
    state = {
        openScheduleModal: false,
        workTimes:[],
        employee: ''
  };

  componentDidMount() {
        
}

  
  handleUpdateScheduleModal = () => {
        
    console.log("aaaaaaaaaaaaaa");
    
    Axios.get(API_URL + "/users/scheduleForEmployee/" + this.props.dermatologist.Id, {
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
	
        console.log(this.employee);
        console.log(this.props.pharmacyId)
		return (
      
      <React.Fragment>
        <button className="card-style" onClick={() => this.handleScheduleModal(this.props.dermatologist.Id)}>
        <img className="img-fluid" src={Picture}  />
        <h2>{this.props.dermatologist.EntityDTO.name} {this.props.dermatologist.EntityDTO.surname}</h2>
        <p>Email: {this.props.dermatologist.EntityDTO.email}</p>
        <p>Phone: {this.props.dermatologist.EntityDTO.phoneNumber}</p>
        <p>Grade: {this.props.dermatologist.EntityDTO.grade} </p>
        
        
        </button>
        
        <ScheduleModal show={this.state.openScheduleModal} update={this.handleUpdateScheduleModal} updateDermatologists={this.handleUpdateDermatologists} onCloseModal={this.handleModalClose} workTimes={this.state.workTimes}  employee ={this.props.dermatologist.Id} pharmacyId={this.props.pharmacyId} header="WorkTimes" />
	    </React.Fragment>
        

		);
	}
}

export default Card;
