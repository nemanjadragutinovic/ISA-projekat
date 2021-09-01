import React, { Component } from "react";
import { Button, CloseButton, Modal } from "react-bootstrap";
import Axios from "axios";
import DatePicker from "react-datepicker";
import GetAuthorisation from "../../Funciton/GetAuthorisation";
import SuccessAlert from "../Alerts/SuccessfulAlert";
import UnsuccessAlert from "../Alerts/UnsuccessfulAlert";
import { Redirect } from "react-router-dom";
import { confirmAlert } from 'react-confirm-alert';
import "../card-components/card.styles.css"
import ConfirmModal from "./ConfirmModal";
import AddAppointmentModal from "./AddAppointmentModal"
import PharmaciesForEmployee from "./PharmaciesForEmployeeModal";
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
		hiddenConfirmModal: false,
        showWorkTime: true,
        hiddenSuccessAlert: true,
        hiddenUnsuccessAlert: true, 
		showAddAppointment: false,
        showPharmaciesForEmployee: false,
        pharmacies:[]
	};

    handleAdd = () => {
        let workTimeDTO = {
            pharmacyId : this.props.pharmacyId,
            employee: this.props.employee, 
            startDate: this.state.selectedStartDate, 
            endDate:this.state.selectedEndDate,
            startTime: this.state.timeFrom, 
            endTime:this.state.timeTo
        };
        console.log(workTimeDTO);
        Axios
        .put(API_URL + "/users/addWorkTime", workTimeDTO, {
            validateStatus: () => true,
            headers: { Authorization:  GetAuthorisation() },
        }).then((res) =>{
            if (res.status === 200) {
                this.props.update();
                this.setState({showWorkTime: true, modalSize:'lg'});
                this.setState({
                    
                    hiddenSuccessAlert: false,
                    hiddenUnsuccessAlert:true,
                    successHeader: "Success",
                    successMessage: "You successfully add new work time.",
                })
                
                
                
            }else if(res.status===400){
                this.setState({ 
                    hiddenSuccessAlert: true,
                    hiddenUnsuccessAlert: false, 
                    failHeader: "Unsuccess", 
                    failMessage: "Dermatologist works at this date range"
                });
            }else if(res.status===500){
                this.setState({ 
                    hiddenSuccessAlert: true,
                    hiddenUnsuccessAlert: false, 
                    failHeader: "Unsuccess", 
                    failMessage: "Server error,please try again later "
                });
            }
            
        }).catch((err) => {
            console.log(err);
        });
    }

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

    handleAddAppointmentModal = () => {
		this.setState({ showAddAppointment: true,showAnotherModal:true });
      
	};

	handleCloseConfirmModal = () => {
		this.setState({ hiddenConfirmModal: false,showAnotherModal:false });
      
	};

    handleOpenPharmaciesForEmployeeModal = () => {
        Axios.get(API_URL + "/users/dermatologistspharmacies/"+this.props.employee,{
            headers: { Authorization:  GetAuthorisation() },  
        })
			.then((res) => {
				this.setState({ pharmacies: res.data });
				console.log(res.data);
			})
			.catch((err) => {
				console.log(err);
			});
            this.setState({ showPharmaciesForEmployee: true,showAnotherModal:true });
    }

    handleClosePharmaciesForEmployeeModal = () => {
		this.setState({ showPharmaciesForEmployee: false,showAnotherModal:false });
      
	};
    handleUpdateDermatologists = () => {
		this.setState({ hiddenConfirmModal: false,showAnotherModal:false });
        this.props.onCloseModal();
        this.props.updateDermatologists();
	};
    handleCloseAddAppModal = () => {
		this.setState({ showAddAppointment: false,showAnotherModal:false });
	};

    handleShowWorkTime = () => {
		this.setState({ showWorkTime: false });
	};

    handleBack = (event) =>{
        this.setState({showWorkTime: true,modalSize:'lg'});
    }

    handleStartDateChange = (date) => {
        this.setState({
            selectedStartDate:date,
        });

        if(date>this.state.selectedEndDate){
            this.setState({
                selectedEndDate:date,
            }); 
        }
    }


    handleEndDateChange = (date) => {
        this.setState({selectedEndDate:date});
    }

    handleTimeFromChange= (event) => {
        if(event.target.value < 1){
            this.setState({timeFrom:1});
        }
        else if(event.target.value > 23){
            this.setState({timeFrom:23});
        }
        
        if(event.target.value >= this.state.timeTo){
            this.setState({
                timeFrom:event.target.value,
                timeTo: event.target.value++
            });
        }else{
            this.setState({timeFrom:event.target.value});
        }
    }

    handleTimeToChange = (event) => {
        
        if(event.target.value < 2){
            this.setState({timeTo:2});
        }
       else  if(event.target.value > 24){
        this.setState({timeTo:24});
        }
            
        if(event.target.value <= this.state.timeFrom){
            this.setState({
                timeTo:event.target.value,
                timeFrom: event.target.value--
            });
        }else{
            this.setState({timeTo:event.target.value});
        }
    }

    handleCloseSuccessAlert = () => {
		this.setState({ hiddenSuccessAlert: true });
    };
    
    handleCloseUnsuccessAlert = () => {
		this.setState({ hiddenUnsuccessAlert: true });
	};

	render() {
        
		return (
			<Modal
				show={this.props.show}
				size={this.state.modalSize}
				centered
                hidden={this.state.showAnotherModal}
                
			>
				<Modal.Header>
					<Modal.Title style={{ marginLeft: "38%" }} >
						{this.props.header}
					</Modal.Title>
				</Modal.Header>
				<Modal.Body>
			
                <SuccessAlert
                            hidden={this.state.hiddenSuccessAlert}
                            header={this.state.successHeader}
                            message={this.state.successMessage}
                            handleCloseAlert={this.handleCloseSuccessAlert}
                        />
                        <UnsuccessAlert
                                hidden={this.state.hiddenUnsuccessAlert}
                                header={this.state.failHeader}
                                message={this.state.failMessage}
                                handleCloseAlert={this.handleCloseUnsuccessAlert}
                        />
					<div hidden={!this.state.showWorkTime}>
						

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
                    <div hidden={this.state.showWorkTime}>
                                <form >
                                    <div  className="control-group" >
                                        <div className="form-row">
                                            <button  onClick = {() => this.handleBack()} className="btn btn-link btn-xl" type="button">
                                              
                                                Back
                                            </button>                   
                                        </div>
                                        <table style={{width:'100%'},{marginLeft:'15%'}}>
                                            <tr>
                                                <td>
                                                    <label >Date from:</label>
                                                </td>
                                                <td>
                                                    <DatePicker className="form-control"  style={{width: "14em"}} minDate={new Date()} onChange={date => this.handleStartDateChange(date)} selected={this.state.selectedStartDate}/>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>
                                                    <label>Date to:</label>
                                                </td>
                                                <td>
                                                    <DatePicker  className="form-control" style={{width: "14em"}}  minDate={this.state.selectedStartDate} onChange={date => this.handleEndDateChange(date)} selected={this.state.selectedEndDate}/>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>
                                                    <label>Time from:</label>
                                                </td>
                                                <td>
                                                    <input placeholder="Time from" className="form-control ml-2" style={{width: "12em"}} type="number" min="1" max="23" onChange={this.handleTimeFromChange} value={this.state.timeFrom} />
                                                </td>
                                            </tr>

                                            <tr>
                                                <td>
                                                    <label>Time to:</label>
                                                </td>
                                                <td>
                                                    <input placeholder="Time to" className="form-control ml-2 " style={{width: "12em"}} type="number" min="2" max="24" onChange={this.handleTimeToChange} value={this.state.timeTo} />
                                                </td>
                                            </tr>
                                        </table>
                                        <div  className="form-group text-center">
                                            <Button className="mt-3"  onClick = {() => this.handleAdd()} >Add Work Time</Button>
                                        </div>
                                    </div>
                                </form>
                    </div>
			
                    <div class="modal-footer " style={{float: 'right'}}>

                    <Button hidden={!this.state.showWorkTime} onClick={() => this.handleShowWorkTime()} className="float-right" style={{float: 'right'}} >
							Add worktime
						</Button>
                    </div>
                <ConfirmModal show={this.state.hiddenConfirmModal}  handleCloseAlert={this.handleCloseConfirmModal} updateDermatologists={this.handleUpdateDermatologists} pharmacyId={this.props.pharmacyId} dermatologistId={this.props.employee} header="Removing dermatologist" />
                <AddAppointmentModal show={this.state.showAddAppointment} onCloseModal={this.handleCloseAddAppModal} pharmacyId={this.props.pharmacyId} header="Create appointment" dermatologistId={this.props.employee}  />
				<PharmaciesForEmployee show={this.state.showPharmaciesForEmployee} pharmacies={this.state.pharmacies} onCloseModal={this.handleClosePharmaciesForEmployeeModal}/>
                </Modal.Body>
				<Modal.Footer>
                <button hidden={!this.state.showWorkTime} type="button" class="btn btn-secondary mr-auto" type="button"  onClick={() => this.handleOpenConfirmModal()}>Remove Dermatologist</button>
                        <Button hidden={!this.state.showWorkTime}  onClick={() => this.handleAddAppointmentModal()}>
							Add Appointment
						</Button>
                        <Button hidden={!this.state.showWorkTime} onClick={() => this.handleOpenPharmaciesForEmployeeModal()}>
							Pharmacies
						</Button>
					<Button onClick={() => this.handleClickOnClose()}>Close</Button>
				</Modal.Footer>
			</Modal>



		);
	}
}

export default ScheduleModal;
