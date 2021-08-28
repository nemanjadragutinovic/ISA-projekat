import React, { Component } from 'react';
import { Button, Modal } from 'react-bootstrap';




class ConfirmModal extends Component {
    state = {
     
        modalSize:'lg'
       
    }

    componentDidMount() {
    }
 
    
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
                   <p>Are you sure to remove dermatologist?</p>
                </div>
                </Modal.Body>
                <Modal.Footer>
                <button type="button" class="btn btn-secondary" type="button" onClick={() => this.props.handleCloseAlert()}>Yes</button> 
                <button type="button" class="btn btn-secondary" type="button" onClick={() => this.props.handleCloseAlert()}>No</button> 
				
                </Modal.Footer>
            </Modal>
         );
    }
}
 
export default ConfirmModal;