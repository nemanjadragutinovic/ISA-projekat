import React, { Component } from 'react';
import { Button, Modal } from 'react-bootstrap';
import Picture from "../../Images/dermatologist.png" ;
import Axios from 'axios';

import DatePicker from "react-datepicker";
import GetAuthorisation from "../../Funciton/GetAuthorisation";
import HeadingSuccessAlert from "../Alerts/SuccessfulAlert";
import HeadingAlert from "../Alerts/UnsuccessfulAlert";

const API_URL="http://localhost:8080";

class AddDermatologistModal extends Component {
    state = {
        dermatologists:[],
        showWorkTime:false,
        dermatologistIdToAdd:'',
        modalSize:'lg',    
        selectedStartDate:new Date(),
        selectedEndDate:new Date(),
        timeFrom:1,
        timeTo:2,    
        pharmacyId:'',
        hiddenSuccessAlert: true,
		successHeader: "",
		successMessage: "",
		hiddenFailAlert: true,
		failHeader: "",
		failMessage: "",
    }

    componentDidMount() {
        
    }

    onAddClick = (id) =>{
        console.log(this.props.pharmacyId);
        this.setState({
            showWorkTime: true,
            dermatologistIdToAdd:id,
            modalSize:'md'
        });
    }

    handleCloseAlertSuccess = () => {
		this.setState({ hiddenSuccessAlert: true });
    };
    
    handleCloseAlertFail = () => {
		this.setState({ hiddenFailAlert: true });
	};

    handleAdd = () => {
        let newDTO = {
            pharmacyId : this.props.pharmacyId,
            dermatologistId: this.state.dermatologistIdToAdd, 
            startDate: this.state.selectedStartDate, 
            endDate:this.state.selectedEndDate,
            startTime: this.state.timeFrom, 
            endTime:this.state.timeTo
        };
        console.log(newDTO);
        Axios
        .put(API_URL + "/users/addDermatologistInPharmacy", newDTO, {
            validateStatus: () => true,
            headers: { Authorization:  GetAuthorisation() },
        }).then((res) =>{
            if (res.status === 200) {
                this.setState({
                    hiddenSuccessAlert: false,
                    hiddenFailAlert:true,
                    successHeader: "Success",
                    successMessage: "You successfully add new appointment.",
                })
                this.props.updateDermatologist();
                this.setState({showWorkTime: false, modalSize:'lg'});
                this.handleSuccessClose();
            }else if(res.status===400){
                this.setState({ 
                    hiddenSuccessAlert: true,
                    hiddenFailAlert: false, 
                    failHeader: "Unsuccess", 
                    failMessage: "Dermatologist has worktime in other pharmacy at this date range"
                });
            }else if(res.status===500){
                this.setState({ 
                    hiddenSuccessAlert: true,
                    hiddenFailAlert: false, 
                    failHeader: "Unsuccess", 
                    failMessage: "We have internal server error,please try later"
                });
            }
            
        }).catch((err) => {
            console.log(err);
        });
    }

    handleBack = (event) =>{
        this.setState({showWorkTime: false,modalSize:'lg'});
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
                timeTo: ++event.target.value
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
                timeFrom: --event.target.value
            });
        }else{
            this.setState({timeTo:event.target.value});
        }
    }

    handleSuccessClose = () =>{
        this.setState({
            showAddWorkTime: false, 
            modalSize:'lg',
            selectedStartDate:new Date(),
            selectedEndDate:new Date(),
            timeFrom:1,
            timeTo:2,
        });
        this.props.closeModal();
        this.props.addedDermatologistMessage();
    }

    handleCloseModal = () => {
        this.setState({
            showAddWorkTime: false, 
            modalSize:'lg',
            selectedStartDate:new Date(),
            selectedEndDate:new Date(),
            timeFrom:1,
            timeTo:2,
        });
        this.props.closeModal();
    }

    render() { 
        return ( 
            <Modal
                show = {this.props.show}
                size = {this.state.modalSize}
                dialogClassName="modal-100w-100h"
                aria-labelledby="contained-modal-title-vcenter"
                centered
                >
            
                <Modal.Body>
                <HeadingSuccessAlert
                            hidden={this.state.hiddenSuccessAlert}
                            header={this.state.successHeader}
                            message={this.state.successMessage}
                            handleCloseAlert={this.handleCloseAlertSuccess}
                        />
                        <HeadingAlert
                                hidden={this.state.hiddenFailAlert}
                                header={this.state.failHeader}
                                message={this.state.failMessage}
                                handleCloseAlert={this.handleCloseAlertFail}
                        />
                <div className="container">      
                    <table hidden={this.state.showWorkTime} className="table" style={{ width: "100%", marginTop: "3rem" }}>
                        <tbody>
                            {this.props.dermatologists.map((dermatologist) => (
                                <tr id={dermatologist.Id} key={dermatologist.Id}>
                                    <td width="130em">
                                    <img
                                            className="img-fluid"
                                            src={Picture}
                                            width="70em"
                                        />
                                    </td>
                                    <td>
                                        <div>
                                            <b>Name: </b> {dermatologist.EntityDTO.name}
                                        </div>
                                        <div>
                                            <b>Surname: </b> {dermatologist.EntityDTO.surname}
                                        </div>
                                        <div>
                                            <b>Email: </b> {dermatologist.EntityDTO.email}
                                        </div>
                                        <div>
                                            <b>Phone number: </b> {dermatologist.EntityDTO.phoneNumber}
                                        </div>
                                    </td>
                                    <td >
                                        <div style={{marginLeft:'25%'}}>
                                            <button style={{height:'30px'},{verticalAlign:'center'},{marginTop:'17%'}} className="btn btn-primary btn-xl" onClick={() => this.onAddClick(dermatologist.Id)} type="button"><i className="icofont-subscribe mr-1"></i>Add</button>
                                        </div>
                                    </td>
                                </tr>
                            ))}
                        </tbody>
                    </table>
                    <div hidden={!this.state.showWorkTime}>
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
                                            <Button className="mt-3"  onClick = {() => this.handleAdd()} >Add dermatologist</Button>
                                        </div>
                                    </div>
                                </form>
                    </div>
                                     
                   
                </div>
                </Modal.Body>
                <Modal.Footer>
                    <Button onClick={() => this.handleCloseModal()}>Close</Button>
                </Modal.Footer>
            </Modal>
         );
    }
}
 
export default AddDermatologistModal;