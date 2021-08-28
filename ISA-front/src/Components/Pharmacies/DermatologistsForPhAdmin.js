import React, { Component } from "react";
import { Button, Modal } from "react-bootstrap";
import Header from '../Header';
import Axios from "axios";
import GetAuthorisation from "../../Funciton/GetAuthorisation";
import { CardList } from "../card-components/card-list.component";
const API_URL="http://localhost:8080";


class DermatologistsForPhAdmin extends Component {
	state = {
        dermatologists: [],
       
       
        
        workTimes:[],
        forStaff:'',
        formShowed:false,
       
    };

    handleCloseAlertSuccess = () => {
		this.setState({ hiddenSuccessAlert: true });
    };
    
    handleCloseAlertFail = () => {
		this.setState({ hiddenFailAlert: true });
	};

     
    componentDidMount() {

        let pharmacyId=localStorage.getItem("keyPharmacyId")
		this.setState({
			pharmacyId: pharmacyId
		})

		Axios.get(API_URL + "/users/dermatologistsInPharmacy/" +localStorage.getItem("keyPharmacyId"), {
			headers: { Authorization:  GetAuthorisation() },
		})
			.then((res) => {
				this.setState({ dermatologists: res.data });
                console.log(res.data);
            
			})
			.catch((err) => {
				console.log(err);
			});
    }




 
    handleModalClose = () => {
        this.setState({showWorkTimesModal: false});
    }

   

    handleFormShow = () => {
		this.setState({ formShowed: !this.state.formShowed });
    };
    
 
    

    
    render() {
        const myStyle = {
			color: "white",
			textAlign: "center",
		};
		return (
        <React.Fragment>
            

                   
                    <Header />

                

                    
                    <div className="container" style={{ marginTop: "5%" , marginBottom:"5%",marginLeft:"6%"}} >
                  
                       

                        <button
                            className="btn btn-outline-primary btn-xl"
                            type="button"
                            onClick={this.handleFormShow}
                        >
                            
                            Search dermatologists
                        </button>
                        <form
                            className={
                                this.state.formShowed ? "form-inline mt-3" : "form-inline mt-3 collapse"
                            }
                            width="100%"
                            id="formCollapse"
                            >
                            <div className="form-group mb-2" width="100%">
                                <input
                                    placeholder="Name"
                                    className="form-control mr-2"
                                    style={{ width: "9em" }}
                                    type="text"
                                    
                                />

                                <input
                                    placeholder="LastName"
                                    className="form-control mr-2"
                                    style={{ width: "9em" }}
                                    type="text"
                                   
                                />

                                <input
                                    placeholder="Grade from"
                                    className="form-control mr-2"
                                    style={{ width: "9em" }}
                                    type="number"
                                    min="1"
                                    max="5"
                                    
                                />
                                <input
                                    placeholder="Grade to"
                                    className="form-control mr-2"
                                    style={{ width: "9em" }}
                                    type="number"
                                    min="1"
                                    max="5"
                                  
                                />

                           
                            </div>
                            <div style={{marginLeft:'1%'}}>

                                <button
                                    style={{ background: "#1977cc" },{marginLeft:'15%'},{marginBottom:'8%'}}
                                   
                                    className="btn btn-primary"
                                    type="button"
                                        >
                                    
                                    Search
                                </button>
                            </div>

                        </form>
                        <Button style={{marginLeft:"1%"}} >
							Add dermatologist
						</Button>
                       

                         
                    </div>
                    <div className="container">
                    <CardList dermatologists={this.state.dermatologists} pharmacyId={this.state.pharmacyId} /> 
           
                    </div>
                    
 
                </React.Fragment>
		);
	}
}

export default DermatologistsForPhAdmin