import React, { Component } from 'react';
import { Button, Modal } from 'react-bootstrap';
import Axios from 'axios';
import GetAuthorisation from "../../Funciton/GetAuthorisation";
import DatePicker from "react-datepicker";

import { Redirect } from "react-router-dom";

const API_URL="http://localhost:8080";
class AddActionPromotionModal extends Component {
    state = {
        selectedStartDate:new Date(),
        selectedEndDate:new Date(),
        discount:'',
        type:'DRUGDISCOUNT',   
    }

    componentDidMount() {
        this.state.selectedStartDate.setDate(this.state.selectedStartDate.getDate()+1)
        this.state.selectedEndDate.setDate(this.state.selectedStartDate.getDate()+1)
        
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

    handleDiscountChange= (event) => {
        this.setState({discount:event.target.value});
        
    }

    handleAdd = () => {
        let actionPromotionDTO = {
            dateFrom: this.state.selectedStartDate, 
            dateTo:this.state.selectedEndDate,
            discount:this.state.discount,
            actionType: this.state.type, 
        };
        console.log(actionPromotionDTO);
        Axios
        .post(API_URL + "/pharmacy/addNewActionPromotion/"+this.props.pharmacyId , actionPromotionDTO, {
            headers: { Authorization:  GetAuthorisation() },
        }).then((res) =>{
           if(res.status==201){
               alert("New Action is created.")
               this.props.handleClickOnClose();
           }else if(res.status==400){
               alert("Date range is occupied")
           }else{
               alert("Server error.")
           }


            this.props.handleClickOnClose();

        }).catch((err) => {
         
        });
    }

    handleClickOnClose = () => {
        this.setState({
            selectedStartDate:new Date(),
            selectedEndDate:new Date(),
            discount:'',
            
        });
        this.props.onCloseModal();
    }

   

    handleTypeChange = (event) => {
		this.setState({ type: event.target.value });
	};

    render() { 
   

        return ( 
            <Modal
                show = {this.props.show}
                centered
                >
                <Modal.Header >
                    <Modal.Title >
                    Add Action and Promotion
                    </Modal.Title>

                </Modal.Header>
                <Modal.Body>
                 
                
                             <form >
                                    <div  className="control-group" >
                                        <table style={{width:'100%'},{marginLeft:'27%'}}>
                                            <tr>
                                                <td>
                                                    <label >Date from:</label>
                                                </td>
                                                <td>
                                                    <DatePicker className="form-control"  style={{width: "15em"}} minDate={this.state.minDate} onChange={date => this.handleStartDateChange(date)} selected={this.state.selectedStartDate}/>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>
                                                    <label>Date to:</label>
                                                </td>
                                                <td>
                                                    <DatePicker  className="form-control" style={{width: "15em"}}  minDate={this.state.selectedStartDate} onChange={date => this.handleEndDateChange(date)} selected={this.state.selectedEndDate}/>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>
                                                    <label>Percent of discount:</label>
                                                </td>
                                                <td>
                                                    <input placeholder="Discount (1-99)" className="form-control" style={{width: "12em"}} type="number" min="1" max="99" onChange={this.handleDiscountChange} value={this.state.discount} />
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>
                                                    <label>Discount for</label>
                                                </td>
                                                <td>
                                                <select onChange={this.handleTypeChange} value={this.state.type} style={{ width: "11em" }} className="form-control mr-3">
								                    <option key="1" value="DRUGDISCOUNT">Drugs</option>
                                                    <option key="2" value="EXAMINATIONDISCOUNT">Examinations</option>
								                    <option key="3" value="CONSULTATIONDISCOUNT">Consultations</option>
							                    </select>
                                                </td>
                                            </tr>
                                        </table>
                                        <div  className="form-group text-center">
                                            <Button className="mt-3"  onClick = {() => this.handleAdd()} >Add action and promotion</Button>
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
 
export default AddActionPromotionModal;