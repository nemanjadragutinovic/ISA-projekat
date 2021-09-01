import React, { Component } from 'react';
import { Button, Modal } from 'react-bootstrap';
import Axios from 'axios';
import MedicamentPicture from "../../Images/medicament.jpg";
import DatePicker from "react-datepicker";
import GetAuthorisation from "../../Funciton/GetAuthorisation";
import HeadingSuccessAlert from "../Alerts/SuccessfulAlert";
import HeadingAlert from "../Alerts/UnsuccessfulAlert";

const API_URL="http://localhost:8080";

class AddDrugModal extends Component {
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
		hiddenUnsuccessAlert: true,
		unsuccessHeader: "",
		unsuccessMessage: "",
        showadd:true
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
    
    handleCloseAlertUnuccess = () => {
		this.setState({ hiddenUnsuccessAlert: true });
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
                    successMessage: "You successfully add new dermatologist.",
                })
                
               /// this.setState({showWorkTime: false, modalSize:'lg'});
                
            }else if(res.status===400){
                this.setState({ 
                    hiddenSuccessAlert: true,
                    hiddenUnsuccessAlert: false, 
                    unsuccessHeader: "Unsuccess", 
                    unsuccessMessage: "Dermatologist works in selected period."
                });
            }else if(res.status===500){
                this.setState({ 
                    hiddenSuccessAlert: true,
                    hiddenUnsuccessAlert: false, 
                    unsuccessHeader: "Unsuccess", 
                    unsuccessMessage: "We have internal server error,please try later"
                });
            }
            
        }).catch((err) => {
            console.log(err);
            this.setState({ 
                hiddenSuccessAlert: true,
                hiddenUnsuccessAlert: false, 
                unsuccessHeader: "Unsuccess", 
                unsuccessMessage: "Error,please try later"
            });
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

    handleCloseAlertSuccess = () =>{
        this.setState({
            hiddenSuccessAlert: true,
            hiddenUnsuccessAlert: true,
            showWorkTime: false, 
            modalSize:'lg',
            selectedStartDate:new Date(),
            selectedEndDate:new Date(),
            timeFrom:1,
            timeTo:2,
        });
        this.props.updateDermatologistsWhoarentInPharmacy();
        this.handleBack();
      
    }

    handleCloseModal = () => {
        this.setState({
            hiddenSuccessAlert: true,
            hiddenUnsuccessAlert: true,
            showWorkTime: false, 
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
               <Modal.Header >
                    <Modal.Title  >
                        Add Drug
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
                                hidden={this.state.hiddenUnsuccessAlert}
                                header={this.state.unsuccessHeader}
                                message={this.state.unsuccessMessage}
                                handleCloseAlert={this.handleCloseAlertSuccess}
                        />
                <div className="container" >      
                <table className={"table"} style={{ width: "100%", marginTop: "3rem" }} hidden={this.state.showadd}>
							<tbody>
								{this.props.drugs.map((drug) => (
									<tr id={drug.Id} key={drug.Id} >
										<td width="130em" >
											<div style={{ marginTop: "40%" }}>
												<img className="img-fluid" src={MedicamentPicture} width="70em" />
											</div>
										</td>
										<td onClick={() => this.handleOnDrugSelect(drug.Id)}>
											<div>
												<b>Drug Name:</b> {drug.EntityDTO.name}
											</div>
											<div>
												<b>Name:</b> {drug.EntityDTO.fabricCode}
											</div>
									
											<div>
												<b>Manufacturer:</b> {drug.EntityDTO.producerName}
											</div>
											<div>
												<b>Quantity:</b> {drug.EntityDTO.quantity} <b> mg</b>
											</div>
											<div>
												<b>Format:</b> {drug.EntityDTO.drugFormat}
											</div>
										
										</td>
										<td className="align-middle">
											<div style={{ marginLeft: "40%" }}>
												<button
													type="button"
													onClick={() => this.handleGetGradeClick(drug)}

													className="btn btn-outline-secondary btn-block"
												>
													Add
												</button>
												
											</div>

										</td>
									</tr>
								))}
							</tbody>
						</table>
                    <div hidden={!this.state.showadd}>
                                <form >
                                    <div  className="control-group" >
                                        <div className="form-row">
                                            <button  onClick = {() => this.handleBack()} className="btn btn-link " type="button">
                                              
                                                Back
                                            </button>                   
                                        </div>
                                        <table style={{width:'100%'},{marginLeft:'15%'}}>
                                           
                                            <tr>
                                                <td>
                                                    <label>Number of drug units:</label>
                                                </td>
                                                <td>
                                                    <input placeholder="Time from" className="form-control " style={{width: "12em"}} type="number"  onChange={this.handleTimeFromChange} value={this.state.timeFrom} />
                                                </td>
                                            </tr>

                                            <tr>
                                                <td>
                                                    <label>Price:</label>
                                                </td>
                                                <td>
                                                    <input placeholder="Time to" className="form-control  " style={{width: "12em"}} type="number"  onChange={this.handleTimeToChange} value={this.state.timeTo} />
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
 
export default AddDrugModal;