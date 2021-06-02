import React, { Component } from "react";
import Axios from "axios";
import Header from '../Components/Header';
import GetAuthorisation from "../Funciton/GetAuthorisation";
import drugPicture from "../Images/medicament.jpg" ;
import UnsuccessfulAlert from "../Components/Alerts/UnsuccessfulAlert";
import SuccessfulAlert from "../Components/Alerts/SuccessfulAlert";
import {NavLink, Redirect } from "react-router-dom";
import HistoryPharmacistConsultation from "../Components/Consultations/HistoryPharmacistConsultation"
import DrugsEReceiptsModal from "../Components/Modal/DrugsEReceiptsModal"



const API_URL="http://localhost:8080";

class PatientsProccessedDrugsEReceipts extends Component {
	
  
    
    state = {
        
        pharmacyId: "",
        processedDrugsList : [],
        
        
        allEReceiptsForSelectedDrug: [] ,
        showEReceiptModal: false,
       
        
    };



  componentDidMount() {

    if (!this.hasRole("ROLE_PATIENT")) {
			this.props.history.push('/login');
    }

    
    Axios.get(API_URL + "/drug/patientsProccessedDrugs-eReceipts", {
			validateStatus: () => true,
			headers: { Authorization: GetAuthorisation() },
		})
			.then((res) => {
				if (res.status === 401) {
                    this.props.history.push('/login');
				} else {
					this.setState({ processedDrugsList: res.data });
					console.log(res.data);
				}
			})
			.catch((err) => {
				console.log(err);
			});
	}
      

  hasRole = (requestRole) => {
    let currentRoles = JSON.parse(localStorage.getItem("keyRole"));

    if (currentRoles === null) return false;


    for (let currentRole of currentRoles) {
      if (currentRole === requestRole) return true;
    }
    return false;
  };


  


    
 
  moveToAllEReceipts =() => {

        
	this.props.history.push('/allPatients-E-receipts')

       
    }



	handleSortByDateAscending =() => {


		if (!this.hasRole("ROLE_PATIENT")) {
			this.props.history.push('/login');
                 }

        this.setState({ selectedSort: "ASCENDING" ,
        hiddenResetSearch : false});
                        
    
         Axios.get(API_URL + "/drug/all-patients-eReceipts/SortByDateAscending", {
			validateStatus: () => true,
			headers: { Authorization: GetAuthorisation() },
		})
			.then((res) => {
				if (res.status === 401) {
                    this.props.history.push('/login');
				} else {
					this.setState({ patientEReceipts: res.data });
					console.log(res.data);
				}
			})
			.catch((err) => {
				console.log(err);
			});
	}
        
    



    ClickOnDrug = (drug) => {
		this.setState({ allEReceiptsForSelectedDrug: drug.EntityDTO.ereceipts, showEReceiptModal : true });
	};

    

    closeEReceiptModal = () => {
		this.setState({ allEReceiptsForSelectedDrug: [], showEReceiptModal : false });
	};

    

    

	render() {
	

		return (
      
      <React.Fragment>

      <Header/>
      
         <div className="container">




        <button type="button" class="btn btn-outline-primary btn-lg"
         onClick={() => this.moveToAllEReceipts()}
         hidden={this.state.hideHistoryButton} 
         style={{  marginTop: "2em", marginLeft: "auto",marginRight: "auto" }}
         >
         All E-receipts
        </button>

        

        

         <h1 hidden={this.state.processedDrugsList.length === 0 } className="text-center  mt-3  " >Your processed drugs!</h1>
         <h1 hidden={this.state.processedDrugsList.length !== 0 } className="text-center  mt-3 text-danger"  >You don't processed drugs!</h1>



   
        <div className="container" >
        <table className="table table-hover" style={{ width: "70%", marginTop: "5em", marginLeft: "auto",marginRight: "auto" }}>
						<tbody>
							{this.state.processedDrugsList.map((drug) => (
								<tr
									id={drug.Id}
									key={drug.Id}
									style={{ cursor: "pointer" }}
									
								>
									
                                    <td width="130em">
										<img className="img-fluid" src={drugPicture} width="90em" />
									</td>

									<td>
										
										<div  >
											<b>Name :</b>{" "}
										   {drug.EntityDTO.drugName}
											
										</div>
										
                                        <div  >
											<b>Format</b>
										   <b style={{ color: "blue" }}> {drug.EntityDTO.drugFormat}</b>
											
										</div>

									</td>

                                   <td>
                                    <button
											type="button"
                                            className="btn btn-outline-primary"
											style={{marginTop : "0.3em"}}
											onClick={() => this.ClickOnDrug(drug)}
										>
											Processed e-receipts
										</button>
                                    </td>
								</tr>
							))}
						</tbody>
					</table>      
         
        </div>



        <DrugsEReceiptsModal

            showModal= {this.state.showEReceiptModal}  
            eReceipts= {this.state.allEReceiptsForSelectedDrug}                      
            closeModal ={this.closeEReceiptModal}                                
            />	
        


							

        </div>

        </React.Fragment>
        
		);
	}
}

export default PatientsProccessedDrugsEReceipts;