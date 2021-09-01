import React, { Component } from 'react';
import { Button, Modal } from 'react-bootstrap';
import Axios from 'axios';

import DatePicker from "react-datepicker";
import GetAuthorisation from "../../Funciton/GetAuthorisation";
import { Redirect } from "react-router-dom";

const API_URL="http://localhost:8080";
class EditDrugPriceModal extends Component {
    state = {
        newPrice:1, 
        selectedStartDate:new Date(),
        selectedEndDate:new Date(),
        hiddenSuccessAlert: true,
		successHeader: "",
		successMessage: "",
		hiddenFailAlert: true,
		failHeader: "",
		failMessage: "",   
        

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
        this.setState({
            selectedEndDate:date,
        });
    }


    handleNewPriceChange= (event) => {
        this.setState({newPrice:event.target.value});
    }

    handleAdd = () => {
        let editPriceDTO = {
            pharmacyId: this.props.pharmacyId,
            drugInstanceId: this.props.drugId, 
            startDate:this.state.selectedStartDate,
            endDate:this.state.selectedEndDate,
            price:this.state.newPrice,
            
        };
        console.log(editPriceDTO);
        if(editPriceDTO.startDate>=editPriceDTO.endDate && editPriceDTO.price<1){
            Axios
            .put(API_URL + "/drug/editDrugPriceInPharmacy", editPriceDTO, {
                validateStatus: () => true,
                headers: { Authorization:  GetAuthorisation() },
            }).then((res) =>{
               
                console.log(res.data);
            }).catch((err) => {
            });
        }else{
          alert("Data is not valid.")
        }
    }




    handleClickOnClose = () => {
        this.props.closeModal();
    }

 
    render() { 
      

        return ( 
            
            <Modal
                show = {this.props.show}     
                centered
                >
                <Modal.Header >
                    <Modal.Title >
                        Edit Drug Price
                    </Modal.Title>
                </Modal.Header>
                <Modal.Body>
                
                        <form >
                            <div  className="control-group" >
                                        <table style={{marginLeft:'18%'}}>
                                            <tr>
                                                <td>
                                                    <label >Date from:</label>
                                                </td>
                                                <td>
                                                    <DatePicker className="form-control"  style={{width: "15em"}} minDate={new Date()} onChange={date => this.handleStartDateChange(date)} selected={this.state.selectedStartDate}/>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>
                                                    <label >Date to:</label>
                                                </td>
                                                <td>
                                                    <DatePicker className="form-control"  style={{width: "15em"}} minDate={new Date()} onChange={date => this.handleEndDateChange(date)} selected={this.state.selectedEndDate}/>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>
                                                    <label>New price:</label>
                                                </td>
                                                <td>
                                                    <input placeholder="New price" className="form-control" style={{width: "12em"}} type="number" min="1" onChange={this.handleNewPriceChange} value={this.state.newPrice} />
                                                </td>
                                            </tr>

                                        </table>
                                        <div  className="form-group text-center">
                                            <Button className="mt-2"  onClick = {() => this.handleAdd()} >Edit price</Button>
                                        </div>
                                    </div>
                                </form>
                </Modal.Body>
                <Modal.Footer>
                    <Button onClick={() => this.handleClickOnClose()}>Close</Button>
                </Modal.Footer>
            </Modal>
         );
    }
}
 
export default EditDrugPriceModal;