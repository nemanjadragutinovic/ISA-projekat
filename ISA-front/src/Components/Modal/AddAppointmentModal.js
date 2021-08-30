import React, { Component } from 'react';
import { Button, Modal } from 'react-bootstrap';
import Axios from 'axios';

import DatePicker from "react-datepicker";
import GetAuthorisation from "../../Funciton/GetAuthorisation";
import HeadingAlert from "../Alerts/UnsuccessfulAlert"
import HeadingSuccessAlert from "../Alerts/SuccessfulAlert"

const API_URL="http://localhost:8080";

class AddAppointmentModal extends Component {
    state = {
        selectedDate:new Date(),
        dermatologist:'',
        duration:'',
        periods:[],
        selectedPeriod: null,
        price:1,
        hiddenSuccessAlert: true,
		successHeader: "",
		successMessage: "",
		hiddenFailAlert: true,
		failHeader: "",
		failMessage: "",

    }


    handleCloseAlertSuccess = () => {
		this.setState({ hiddenSuccessAlert: true });
    };
    
    handleCloseAlertFail = () => {
		this.setState({ hiddenFailAlert: true });
	};

    handleAddAppointment = () => {
        
       if(this.state.selectedPeriod!=null){
            let appointmentDTO = {
                dermatologistId : this.props.dermatologistId,
                phId: this.props.pharmacyId, 
                startDateTime: this.state.selectedPeriod.startDate, 
                endDateTime:this.state.selectedPeriod.endDate,
                price: this.state.price, 
                
            };
        console.log(appointmentDTO)
        Axios
        .post(API_URL + "/appointment/createDermatologistsExaminatoin", appointmentDTO, {
            validateStatus: () => true,
            headers: { Authorization:  GetAuthorisation() },
        }).then((res) =>{
            console.log(res.data);
            if (res.status === 200) {
                this.setState({
                    hiddenSuccessAlert: false,
                    hiddenFailAlert:true,
                    successHeader: "Success",
                    successMessage: "You successfully add new appointment.",
                })
                let paramsAAA = {
                    dermatologistId: this.props.dermatologistId,
                    phId:this.props.pharmacyId,
                    date: this.state.selectedDate,
                    duration: this.state.duration,
                };

                Axios.post(API_URL + "/appointment/generateSuggestionsForTimePeriod", paramsAAA ,{
                    headers: { Authorization:  GetAuthorisation() },
                }).then((res) => {
                        this.setState({ periods: res.data, selectedPeriod:res.data[0]
                        });
                        console.log(res.data);
                    })
                    .catch((err) => {
                        console.log(err);
                    });
            }else if(res.status===400){
                this.setState({ 
                    hiddenSuccessAlert: true,
                    hiddenFailAlert: false, 
                    failHeader: "Unsuccess", 
                    failMessage: "Dermatologist has absence at selected day"
                });
                
            }else if(res.status===500){
                this.setState({ 
                    hiddenSuccessAlert: true,
                    hiddenFailAlert: false, 
                    failHeader: "Unsuccess", 
                    failMessage: "We have internal server problem. Please try later."
                });
            }
        }).catch((err) => {
            this.setState({ 
                hiddenSuccessAlert: true,
                hiddenFailAlert: false, 
                failHeader: "Unsuccess", 
                failMessage: "Please enter a valid data"
            });
        });       
        }
        else{
            this.setState({ 
                hiddenSuccessAlert: true,
                hiddenFailAlert: false, 
                failHeader: "Unsuccess", 
                failMessage: "Please enter a valid data"
            });        } 
    }

    handleCloseAppointment= () => {
        this.setState({
            selectedDate:new Date(),
            duration:'',
            periods:[],
            selectedPeriod:'',
            price:1,
            hiddenSuccessAlert: true,
            hiddenFailAlert: true, 
        });
        this.props.onCloseModal();
    }

    handleDermatologistChange = (event) => {
        this.setState({ dermatologist: event.target.value });
    };

    handlePeriodsChange = (event) => {
        this.setState({ selectedPeriod: this.state.periods[event.target.value] });
    }

    handleDateChange = (date) => {
        this.setState({
            selectedDate:date,
            selectedPeriod:null,
            periods:[],
            duration:0,
        });

    }
    
    
    handlePriceChange = (event) =>{

        if(event.target.value<1){
            this.setState({
                price:1,
            });
        }else{
            this.setState({
                price:event.target.value,
            });
        }
    }

    

    handleCloseModal = () => {
        this.setState({
            price:1,
        });    
    }

    handleSelectDurationChange = (event) => {
        this.setState({
            duration:event.target.value,
        });

        let paramsAAA = {
            dermatologistId: this.props.dermatologistId,
            phId:this.props.pharmacyId,
            date: this.state.selectedDate,
            duration: event.target.value
        };
        console.log(`Bearer ${localStorage.getItem("keyToken")}`);
        console.log(paramsAAA);
        if(event.target.value>=10 && event.target.value<=60){
            Axios.post(API_URL + "/appointment/generateSuggestionsForTimePeriod",paramsAAA, {
                headers: { Authorization:  GetAuthorisation() },
            }).then((res) => {
                    this.setState({ periods: res.data });
                    console.log(res.data);
                })
                .catch((err) => {
                    console.log(err);
                });
        }
    }

    render() { 
        return ( 
            <Modal
                onCloseModal={this.handleCloseModal}
                show = {this.props.show}
                size = "md"
                dialogClassName="modal"
                centered>
                <Modal.Header >
                    <Modal.Title style={{marginLeft:'28%'}} >
                        {this.props.header}
                    </Modal.Title>

                </Modal.Header>
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
                    <div >
                        <form >
                            <div  className="control-group" >
                                        <table style={{width:'100%'},{marginLeft:'7%'}}>
                                            
                                            <tr>
                                                <td>
                                                    <label>Select date:</label>
                                                </td>
                                                <td>
                                                    <DatePicker className="form-control"  style={{width: "20em"}} minDate={this.state.selectedDate} onChange={date => this.handleDateChange(date)} selected={this.state.selectedDate}/>
                                                    
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>
                                                    <label>Select duration:</label>
                                                </td>
                                                <td>
                                                    <input placeholder="Duration" className="form-control" style={{width: "15em"}} type="number" min="10" max="60" onChange={this.handleSelectDurationChange} value={this.state.duration} />
                                                </td>
                                            </tr>

                                            <tr>
                                                <td>
                                                    <label>Select period:</label>
                                                </td>
                                                <td>
                                                    <select onChange={this.handlePeriodsChange} className="form-control" ><option key="1" value=""> </option>{this.state.periods.map((period,index) => <option key={index} value={index}>{new Date(period.startDate).toLocaleTimeString()} - {new Date(period.endDate).toLocaleTimeString()}</option>)}</select>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>
                                                    <label>Price:</label>
                                                </td>
                                                <td>
                                                    <input placeholder="Price" className="form-control" style={{width: "15em"}} type="number" min="1" onChange={this.handlePriceChange} value={this.state.price} />
                                                </td>
                                            </tr>
                                        </table>
                                        <div  className="form-group text-center">
                                            <Button className="mt-3"  onClick = {() => this.handleAddAppointment()} >Add appointment</Button>
                                        </div>
                                    </div>
                                </form>
                    </div>
                </Modal.Body>
                <Modal.Footer>
                    <Button onClick={() => this.handleCloseAppointment()}>Close</Button>
                </Modal.Footer>
            </Modal>
         );
    }
}
 
export default AddAppointmentModal;