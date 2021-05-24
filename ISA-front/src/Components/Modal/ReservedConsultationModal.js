import React, { Component } from "react";
import { Button, Modal } from "react-bootstrap";
import UnsuccessfulAlert from "../../Components/Alerts/UnsuccessfulAlert";

class ReservedConsultationModal extends Component {
	

	render() {
		return (
			<Modal
				show={this.props.show}
				size="lg"
				dialogClassName="modal-80w-80h"
				aria-labelledby="contained-modal-title-vcenter"
				centered
				onHide={this.props.onCloseModal}
			>
				<Modal.Header closeButton>
					<Modal.Title id="contained-modal-title-vcenter" >{this.props.modalTitle}</Modal.Title>
				
				</Modal.Header>
				<Modal.Body>
					
                <b1  className="text-center  mt-3  " hidden={this.props.hideSuccessfulModalText} >You successful reserved pharmacist consultation! All information about it will be sent on your email!</b1>

				<div className="text-danger" >
										{this.props.errorMessageForReservation }
				</div>


				</Modal.Body>
				<Modal.Footer>
                <div className="form-group text-center">
						<button
							hidden={this.props.hideSuccessfulModalButton}
							style={{ background: "#1977cc", marginTop: "15px" }}
							onClick={() => this.props.closeModal()}
							className="btn btn-primary btn-xl"
							id="sendMessageButton"
							type="button"
						>
							Ok
						</button>
					</div>
				</Modal.Footer>
			</Modal>

            
		);
	}
}

export default ReservedConsultationModal;