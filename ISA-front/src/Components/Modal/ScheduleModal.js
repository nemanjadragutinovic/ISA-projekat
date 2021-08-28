import React, { Component } from "react";
import { Button, Modal } from "react-bootstrap";
import Axios from "axios";
import DatePicker from "react-datepicker";
import GetAuthorisation from "../../Funciton/GetAuthorisation";
import SuccessAlert from "../Alerts/SuccessfulAlert";
import UnsuccessAlert from "../Alerts/UnsuccessfulAlert";
import { Redirect } from "react-router-dom";
import { confirmAlert } from 'react-confirm-alert';
import "../card-components/card.styles.css"
import ConfirmModal from "./ConfirmModal";
const API_URL="http://localhost:8080";
class ScheduleModal extends Component {
	state = {
		workTimes: [],
		showAnotherModal: false,
		selectedStartDate: new Date(),
		selectedEndDate: new Date(),
		timeFrom: 1,
		timeTo: 2,
		modalSize: "lg",
		hiddenConfirmModal: true,
		
	};

  
	handleClickOnClose = () => {
		this.setState({
			showAnotherModal: false,
			modalSize: "lg",
			selectedStartDate: new Date(),
			selectedEndDate: new Date(),
			timeFrom: 1,
			timeTo: 2,
			
		});
		this.props.onCloseModal();
	};

	handleOpenConfirmModal = () => {
		this.setState({ hiddenConfirmModal: true,showAnotherModal:true });
      
	};

	handleCloseConfirmModal = () => {
		this.setState({ hiddenConfirmModal: false,showAnotherModal:false });
	};



	render() {
		return (
			<Modal
				show={this.props.show}
				size={this.state.modalSize}
				dialogClassName="modal-120w-100h"
				aria-labelledby="contained-modal-title-vcenter"
				centered
                hidden={this.state.showAnotherModal}
                
			>
				<Modal.Header>
					<Modal.Title style={{ marginLeft: "38%" }} id="contained-modal-title-vcenter">
						{this.props.header}
					</Modal.Title>
				</Modal.Header>
				<Modal.Body>
			
					
					<div hidden={this.state.showAnotherModal}>
						

						<table border="1" style={{ width: "100%" }}>
							<tr>
								<th>Pharmacy</th>
								<th>StartDate</th>
								<th>EndDate</th>
								<th>StartTime</th>
								<th>EndTime</th>
							</tr>
							{this.props.workTimes.map((workTime) => (
								<tr>
									<td>{workTime.EntityDTO.pharmacyName}</td>
									<td>{new Date(workTime.EntityDTO.startDate).toDateString()}</td>
									<td>{new Date(workTime.EntityDTO.endDate).toDateString()}</td>
									<td>{workTime.EntityDTO.startTime}</td>
									<td>{workTime.EntityDTO.endTime}</td>
								</tr>
							))}
						</table>

                       

                        
                        
					</div>

			
                    <div class="modal-footer " style={{float: 'right'}}>

                    <Button className="float-right" style={{float: 'right'}} >
							Add worktime
						</Button>
                    </div>
                <ConfirmModal show={this.state.hiddenConfirmModal}  handleCloseAlert={this.handleCloseConfirmModal} header="Removing dermatologist" />
				</Modal.Body>
				<Modal.Footer>
                <button type="button" class="btn btn-secondary mr-auto" type="button"  onClick={() => this.handleOpenConfirmModal()}>Remove Dermatologist</button>
                        <Button >
							Add Apointment
						</Button>
                        <Button >
							Pharmacies
						</Button>
					<Button onClick={() => this.handleClickOnClose()}>Close</Button>
				</Modal.Footer>
			</Modal>



		);
	}
}

export default ScheduleModal;
