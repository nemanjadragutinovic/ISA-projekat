import React, { Component } from 'react';
import { Button, Modal } from 'react-bootstrap';
import Axios from 'axios';
import GetAuthorisation from "../../Funciton/GetAuthorisation";
const API_URL="http://localhost:8080";
class EditStorage extends Component {
    state = {
        count:1, 
    }

    handleCountChange= (event) => {
        this.setState({count:event.target.value});
    }

    handleAdd = () => {
        let editStorageDTO = {
            pharmacyId: this.props.pharmacyId,
            drugInstanceId: this.props.drugId, 
            count:this.state.count
        };
        console.log(editStorageDTO);
            Axios
            .put(API_URL + "/drug/editPharmacyStorage", editStorageDTO, {
                validateStatus: () => true,
                headers: { Authorization: GetAuthorisation() },
            }).then((res) =>{
                if (res.status === 200) {
                   alert("Number of drugs is successfully edited.");
                    this.handleClose();
                }else if(res.status===400){
                   alert("It is not possible to change number of drugs.");
                }
                else{
                    alert("Error.");
                }
                console.log(res.data);
            }).catch((err) => {
                alert("Error.");
            });
        }
    

    handleClose = () => {
     
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
                        Edit number of drugs in storage
                    </Modal.Title>
                </Modal.Header>
                <Modal.Body>
                        <form >
                            <div  className="control-group" >
                                        <table style={{width:'100%'},{marginLeft:'17%'}}>
                                            <tr>
                                                <td>
                                                    <label>New count:</label>
                                                </td>
                                                <td>
                                                    <input className="form-control" style={{width: "12.8em"}} type="number" min="1" onChange={this.handleCountChange} value={this.state.count} />
                                                </td>
                                            </tr>

                                        </table>
                                        <div  className="form-group text-center">
                                            <Button className="mt-3"  onClick = {() => this.handleAdd()} >Modify storage</Button>
                                        </div>
                                    </div>
                                </form>
                </Modal.Body>
                <Modal.Footer>
                    <Button onClick={() => this.handleClose()}>Close</Button>
                </Modal.Footer>
            </Modal>
         );
    }
}
 
export default EditStorage;