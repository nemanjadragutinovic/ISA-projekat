import React, { Component } from 'react';
import { Button, Modal } from 'react-bootstrap';
import Axios from 'axios';

import DatePicker from "react-datepicker";
import MedicamentPicture from "../../Images/medicament.jpg";
import GetAuthorisation from "../../Funciton/GetAuthorisation";
import { Redirect } from "react-router-dom";
const API_URL="http://localhost:8080";
class EditOrderModal extends Component {
    state = {
        drugsToAdd: this.props.drugsToAdd ,
        drugs: this.props.drugs,
        page: 1,
        modalSize:'lg',    
        selectedDate:new Date(),
        pharmacyId:'',
        drugForAdd:'',
        drugRemove:'',
        selectedCount:'',
        showDate:false,
  
    }


    componentDidMount() {
	

    }

  

    handleAdd = () => {

        if(this.state.selectedCount<1){
           alert("Count is must be more than 0.");         }
        else{
       
            let drug= this.state.drugForAdd;
            drug.amount= this.state.selectedCount

           
            this.props.addToList(drug);
    
            this.setState({
                page:1,
                selectedCount:'',
                drugForAdd:''
            })   
        }
    }

    handleRemove = (drugRemove) => {
           
            console.log(drugRemove)
    

            this.props.removeFromList(drugRemove);
            this.setState({
                page:1,
                selectedCount:'',
            })   
        }
    

    handleCreateOrder = () =>{
        let editOrderDTO = {
            orderId:this.props.orderToEdit,
            listOfDrugs: this.props.drugsToAdd,
            dateTo:this.state.selectedDate
        }
      console.log(editOrderDTO)
        Axios
        .put(API_URL + "/order/updateOrder", editOrderDTO, {
            validateStatus: () => true,
            headers: { Authorization: GetAuthorisation() },})
        .then((res) =>{
            console.log(res.status)
            if (res.status === 201) {
                alert("Oreder is succesfully edited");
                this.handleClickOnClose();
            }else {
                alert("It is not possibe to create new order");
            }
        }).catch((err) => {
        });
    }

    isValidData = (addDrugToPharmacyDTO) =>{
        if(addDrugToPharmacyDTO.drugId===''){
            return false
        }

        if(addDrugToPharmacyDTO.dateTo===new Date()){
            return false
        }

        if(addDrugToPharmacyDTO.amount<1){
            return false
        }

        if(addDrugToPharmacyDTO.price<1){
            return false
        }

        return true
    }

    handleSelectedDateChange = (date) => {
        this.setState({
            selectedDate:date,
        });

    }


    handleSelectedCountChange = (event) => {
        this.setState({selectedCount:event.target.value});
    }

    handleClickOnCreateOrder = () =>{
        if(this.props.drugsToAdd.length<1){
          alert("Drug list is empty");       
        }else{
            this.setState({
                page:3,
            })
        }
    }

    handleClickOnClose = () => {
        this.setState({
            drugsToAdd: [] ,
            drugs:[],
            page: 1,    
            selectedDate:new Date(),
            pharmacyId:'',
            drugForAdd:'',
            selectedCount:'',
        });
        this.handleUpdte();
        this.props.closeModal();
        this.props.update();
    }

    handleUpdte = () => {
        Axios.get(API_URL + "/drug/drugsInPharmacy/" + localStorage.getItem("keyPharmacyId"), {
			headers: { Authorization: GetAuthorisation() },
		})

			.then((res) => {
				console.log(res.data);
				this.setState({ drugs: res.data });
			})
			.catch((err) => {
				console.log(err);
			});

    }

    onAddClick = (id) =>{
        this.setState({page : 2,drugForAdd:id});
  

    }

 

     handleBack = (event) =>{
        this.setState({page: 1,selectedCount:''});
    

    }



    render() { 
      

        return ( 
            <Modal
                show = {this.props.show}
                size='lg'
                centered
                >
                <Modal.Header >
                    <Modal.Title style={{marginLeft:'37%'}} >
                        Create Order
                    </Modal.Title>
                </Modal.Header>
                <Modal.Body>
           
                <div className="container">  

                    <table hidden={this.state.page!== 1}  style={{width:'100%'}} className="table">
                                
                                {this.props.drugs.map((drug) => (
                                
                                <tr id={drug.Id} key={drug.Id}>
                                <td width="130em">
                                    <img className="img-fluid" src={MedicamentPicture} width="70em"/>
                                </td>
                                <td>	<div>
												<b>Drug Name:</b> {drug.drugName}
											</div>
											<div>
												<b>Name:</b> {drug.fabricCode}
											</div>
									
											<div>
												<b>Manufacturer:</b> {drug.producerName}
											</div>
											<div>
												<b>Quantity:</b> {drug.quantity} <b> mg</b>
											</div>
                                </td>
                                <td >
                                    <div>
                                        <button style={{height:'30px'},{verticalAlign:'center'}} className="btn btn-primary btn-xl mt-2" onClick={() => this.onAddClick(drug)} type="button"><i className="icofont-subscribe mr-1"></i>Add</button>
                                    </div>
                                </td>
                            </tr>
                            ))}
                </table> 
                <div className="container" style={{ marginTop: "5%"}} hidden={this.state.page!== 1}> 
                <h5>Selected drugs</h5>
                    <table hidden={this.state.page!== 1} className="table" style={{ width: "100%", marginTop: "2rem" }}>
                    
                        <tbody>
                            {this.props.drugsToAdd.map((drug) => (
                                <tr id={drug.Id} key={drug.Id}>
                                    <td width="130em">
                                        <img className="img-fluid" src={MedicamentPicture} width="70em"/>
                                    </td>
                                    <td>
                                            <div><b>Drug:</b> {drug.drugName}</div>
                                            <div><b>Name:</b> {drug.fabricCode}</div>
                                            <div><b>Manufacturer:</b> {drug.producerName}</div>
                                            <div><b>Amount:</b> {drug.amount}</div>
                                    </td>
                                    <td >
                                        <div>
                                            <button style={{height:'30px'},{verticalAlign:'center'}} className="btn btn-outline-primary mt-2" onClick={() => this.handleRemove(drug)} type="button">Remove</button>
                                        </div>
                                    </td>
                                </tr>
                            ))}
                        </tbody>
                    </table>
                    </div>
                    <div hidden={this.state.page!==2}>
                                <form >
                                    <div  className="control-group" >
                                        <div className="form-row">
                                            <button  onClick = {() => this.handleBack()} className="btn btn-link btn-xl" type="button">
                                                Back
                                            </button>                   
                                        </div>
                                        <table style={{width:'100%'},{marginLeft:'32%'}}>
                                            <tr>
                                                <td>
                                                    <label>Amount of drug:</label>
                                                </td>
                                                <td>
                                                    <input  className="form-control" style={{width: "12em"}} type="number" min="1" onChange={this.handleSelectedCountChange} value={this.state.selectedCount} />
                                                </td>
                                            </tr>
                                        </table>
                                        <div  className="form-group text-center">
                                            <Button className="mt-3"  onClick = {() => this.handleAdd()} >Add drug to order list</Button>
                                        </div>
                                    </div>
                                </form>
                    </div>
                    <div hidden={this.state.page!==3}>
                                <form >
                                    <div  className="control-group" >
                                        <div className="form-row">
                                            <button  onClick = {() => this.handleBack()} className="btn btn-link btn-xl" type="button">
                                              
                                                Back
                                            </button>                   
                                        </div>
                                        <table style={{width:'100%'},{marginLeft:'32%'}}>
                                            <tr>
                                                <td>
                                                    <label>Date:</label>
                                                </td>
                                                <td>
                                                    <DatePicker  className="form-control" style={{width: "15em"}}  minDate={new Date()} onChange={date => this.handleSelectedDateChange(date)} selected={this.state.selectedDate}/>
                                                </td>
                                            </tr>
                                        </table>
                                        <div  className="form-group text-center">
                                            <Button className="mt-3"  onClick = {() => this.handleCreateOrder()} >Create order</Button>
                                        </div>
                                    </div>
                                </form>
                    </div>
                </div>
                </Modal.Body>
                <Modal.Footer>
                    <Button  hidden={this.state.page===3} onClick={() => this.handleClickOnCreateOrder()}>Create order</Button>
                    <Button onClick={() => this.handleClickOnClose()}>Close</Button>
                </Modal.Footer>
            </Modal>
         );
    }
}
 
export default EditOrderModal;