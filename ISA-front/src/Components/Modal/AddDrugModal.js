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
        
        drugId:'',  
        pharmacyId:'',
        hiddenSuccessAlert: true,
		successHeader: "",
		successMessage: "",
		hiddenUnsuccessAlert: true,
		unsuccessHeader: "",
		unsuccessMessage: "",
        showadd:false,
        price:1,
        count:1
    }

    componentDidMount() {
        
    }

    onAddClick = (id) =>{
        console.log(this.props.pharmacyId);
        this.setState({
            showadd: true,
            drugId:id
          
        });
    }

    handleCloseAlertSuccess = () => {
		this.setState({ hiddenSuccessAlert: true });
    };
    
    handleCloseAlertUnuccess = () => {
		this.setState({ hiddenUnsuccessAlert: true });
	};

   
    handleAdd = () => {
        let addDTO = {
            drugId: this.state.drugId, 
            pharmacyId : this.props.pharmacyId, 
            count:this.state.count, 
            price: this.state.price 
           
         
        };
        console.log(addDTO);
        if(this.state.price>=1 && this.state.count>=1){
        Axios
        .put(API_URL + "/drug/addDrugInPharmacy", addDTO, {
            validateStatus: () => true,
            headers: { Authorization:  GetAuthorisation() },
        }).then((res) =>{
            console.log(res.status);
            console.log(res.data);
            if (res.status === 201) {
                this.setState({
                    hiddenSuccessAlert: false,
                    hiddenUnsuccessAlert:true,
                    successHeader: "Success",
                    successMessage: "You successfully add drug in pharmacy.",
                })
                
           
                
            }else {
    
                this.setState({ 
                    hiddenSuccessAlert: true,
                    hiddenUnsuccessAlert: false, 
                    unsuccessHeader: "Unsuccess", 
                    unsuccessMessage: "Error on server."
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
        });}
        else{
            alert("Data is not valid.")

        }
    }

    handleBack = (event) =>{
        this.setState({showadd: false});
    }


    handlePriceChange= (event) => {
        
            this.setState({price:event.target.value});
        
    }

    handleCountChange = (event) => {
            this.setState({count:event.target.value});
    }

    handleCloseAlertSuccess = () =>{
        this.setState({
            hiddenSuccessAlert: true,
            hiddenUnsuccessAlert: true,
            showadd: false, 
           
        });
        this.props.updateDrugsWhicharentInPharmacy();
        this.handleBack();
      
    }

    

    handleCloseModal = () => {
        this.setState({
            hiddenSuccessAlert: true,
            hiddenUnsuccessAlert: true,
            showadd: false, 
            price: 1,
            count: 1
           
        });
        this.props.closeModal();
    }

    render() { 
        return ( 
            <Modal
                show = {this.props.show}
                size = "lg"
                dialogClassName="modal-180w-180h"
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
													onClick={() => this.onAddClick(drug.Id)}

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
                                                    <input  className="form-control " style={{width: "12em"}} type="number" min="1" onChange={this.handleCountChange} value={this.state.count} />
                                                </td>
                                            </tr>

                                            <tr>
                                                <td>
                                                    <label>Price:</label>
                                                </td>
                                                <td>
                                                    <input  className="form-control  " style={{width: "12em"}} type="number" min="1" onChange={this.handlePriceChange} value={this.state.price} />
                                                </td>
                                            </tr>
                                        </table>
                                        <div  className="form-group text-center">
                                            <Button className="mt-3"  onClick = {() => this.handleAdd()} >Add drug in pharmacy</Button>
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