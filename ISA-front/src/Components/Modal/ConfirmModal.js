import React, { Component } from 'react';
import { Button, Modal } from 'react-bootstrap';

import Axios from "axios";
import GetAuthorisation from "../../Funciton/GetAuthorisation";
import SuccessAlert from "../Alerts/SuccessfulAlert";
import UnsuccessAlert from "../Alerts/UnsuccessfulAlert";
const API_URL="http://localhost:8080";

class ConfirmModal extends Component {
    state = {
        hiddenSuccessAlert: true,
        hiddenUnsuccessAlert:true,
        modalSize:'lg'
       
    }

    componentDidMount() {
    }
 
    handleRemoveDermatologist = () =>{
        console.log(`Bearer ${localStorage.getItem("keyToken")}`);
           console.log(this.props.dermatologistId);        
       

                    let removeDTO = {
                        dermatologistId: this.props.dermatologistId,
                        pharmacyId: this.props.pharmacyId,
                    };
                    
                    Axios.put(API_URL + "/users/removeDermatologistFromPharmacy",removeDTO, {
                      
                        validateStatus: () => true,
                        headers: { Authorization:  GetAuthorisation() },
                    }).then((res) =>{
                        this.setState({
                            hiddenSuccessAlert: false,
                            hiddenUnsuccessAlert:true,
                            successHeader: "Success",
                            successMessage: "You successfully remove dermatologist."
                        });
                    }).catch((err) => {
                       
                        console.log(err);
                        this.setState({ 
                            hiddenSuccessAlert: true,
                            hiddenUnsuccessAlert: false, 
                            failHeader: "Unsuccess", 
                            failMessage: "It is not possible to remove the dermatologist"});
                    });
            
       
    };

    handleCloseAlertSuccess = () => {
		this.setState({ hiddenSuccessAlert: true });
        this.props.handleCloseAlert();
        this.props.updateDermatologists();
    };
    
    handleCloseUnsuccessAlert = () => {
		this.setState({ hiddenFailAlert: true });
        this.props.handleCloseAlert();
	};

    render() { 
        console.log(this.props.show);
        return ( 
            <Modal
                show = {this.props.show}
                size = {this.state.modalSize}
                dialogClassName="modal-80w-100h"
                aria-labelledby="contained-modal-title-vcenter"
                centered
               
                >
                <Modal.Header >
                    <Modal.Title style={{marginLeft:'37%'}} id="contained-modal-title-vcenter">
                        {this.props.header}
                    </Modal.Title>
                </Modal.Header>
                <Modal.Body>
                <div className="container">      
                <SuccessAlert
                            hidden={this.state.hiddenSuccessAlert}
                            header={this.state.successHeader}
                            message={this.state.successMessage}
                            handleCloseAlert={this.handleCloseAlertSuccess}
                        />
                        <UnsuccessAlert
                                hidden={this.state.hiddenUnsuccessAlert}
                                header={this.state.failHeader}
                                message={this.state.failMessage}
                                handleCloseAlert={this.handleCloseUnsuccessAlert}
                        />
                   <p>Are you sure to remove dermatologist?</p>
                </div>
                </Modal.Body>
                <Modal.Footer>
                <button type="button" class="btn btn-secondary" type="button" onClick={() => this.handleRemoveDermatologist()}>Yes</button> 
                <button type="button" class="btn btn-secondary" type="button" onClick={() => this.props.handleCloseAlert()}>No</button> 
				
                </Modal.Footer>
            </Modal>
         );
    }
}
 
export default ConfirmModal;