import React, { Component } from "react";
import Axios from "axios";
import Header from '../Components/Header';
import GetAuthorisation from "../Funciton/GetAuthorisation";
import eReceiptsPicture from "../Images/notepad.png" ;
import UnsuccessfulAlert from "../Components/Alerts/UnsuccessfulAlert";
import SuccessfulAlert from "../Components/Alerts/SuccessfulAlert";
import {NavLink, Redirect } from "react-router-dom";
import HistoryPharmacistConsultation from "../Components/Consultations/HistoryPharmacistConsultation"
import EreceiptDrugsModal from "../Components/Modal/EreceiptDrugsModal"



const API_URL="http://localhost:8080";

class PatientsEReceipts extends Component {
	
  
    
    state = {
        
        pharmacyId: "",
        patientEReceipts : [],
        
        hideFutureButton : true,
        hideHistoryButton : false,
        historyConsultations : [],
        hideFutureConsultations : false,
        hideHistoryConsultations : true,

        allDrugsForSelectedReceipt: [] ,
        showDrugModal: false,
        searchStatus : "status",
        selectedSort : "",
        hiddenResetSearch : true
        
    };



  componentDidMount() {

    if (!this.hasRole("ROLE_PATIENT")) {
			this.props.history.push('/login');
    }

    
    Axios.get(API_URL + "/drug/all-patients-eReceipts", {
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
      

  hasRole = (requestRole) => {
    let currentRoles = JSON.parse(localStorage.getItem("keyRole"));

    if (currentRoles === null) return false;


    for (let currentRole of currentRoles) {
      if (currentRole === requestRole) return true;
    }
    return false;
  };


  


    
 
  moveToProcessedDrugs =() => {

	this.props.history.push('/allPatients-processed-drugs-e-receipts')
		
       
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
        
    


	handleSortByDateDescending =() => {

		
		if (!this.hasRole("ROLE_PATIENT")) {
			this.props.history.push('/login');
             }

        this.setState({ selectedSort: "DESCENDING",
        hiddenResetSearch : false });

         Axios.get(API_URL + "/drug/all-patients-eReceipts/SortByDateDescending", {
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


    ClickOnEReceipt = (eReceipt) => {
		this.setState({ allDrugsForSelectedReceipt: eReceipt.EntityDTO.drugs, showDrugModal : true });
	};

    

    closeDrugsModal = () => {
		this.setState({ allDrugsForSelectedReceipt: [], showDrugModal : false });
	};

    handleStatusChange = (event) => {
		this.setState({ searchStatus: event.target.value });
	};


    searchClick = () => {
		
        let url = ""

        if(this.state.selectedSort=== "ASCENDING"){
            url =  API_URL + "/drug/all-patients-eReceipts/SortByDateAscending/search/";
        }else if(this.state.selectedSort=== "DESCENDING"){
            url = API_URL + "/drug/all-patients-eReceipts/SortByDateDescending/search/";
        }else{
            url = API_URL + "/drug/all-patients-eReceipts/search/";
        }

        console.log(url);

        Axios.get(url + this.state.searchStatus, { headers: { Authorization: GetAuthorisation() } })
			.then((res) => {
				
				this.setState({ patientEReceipts: res.data, hiddenResetSearch : false});
                
			})
			.catch((err) => {
				console.log(err);
			});

	};



    resetSearchClick = () => {
		
        this.setState({hiddenResetSearch : true});
        
        this.componentDidMount();

	};

    

	render() {
	

		return (
      
      <React.Fragment>

      <Header/>
      
         <div className="container">




        <button type="button" class="btn btn-outline-primary btn-lg"
         onClick={() => this.moveToProcessedDrugs()}
         hidden={this.state.hideHistoryButton} 
         style={{  marginTop: "2em", marginLeft: "auto",marginRight: "auto" }}
         >
         Processed drugs with E-receipts
        </button>

        <div className="container form-inline " style={{  marginTop: "2em" }}>

            <div className="dropdown">
                <button className="btn btn-primary btn-lg dropdown-toggle"
                    type="button" id="dropdownMenu2"
                    data-toggle="dropdown" 
                    aria-haspopup="true" 
                    aria-expanded="false">
                    Sort
                </button>
                <div className="dropdown-menu" aria-labelledby="dropdownMenu2">              
                        <button className="dropdown-item" type="button" onClick={this.handleSortByDateAscending} >Sort by date ascending</button>
                        <button className="dropdown-item" type="button" onClick={this.handleSortByDateDescending} >Sort by date descending</button>                  
                </div>
            </div>

            <div className=" ml-3">
									<select
										placeholder="Status"
										onChange={this.handleStatusChange}
										value={this.state.searchStatus}
										style={{ width: "8em" }}
										className="form-control mr-3"
									>
										<option key="1" value="Status" selected disabled>
											Status
										</option>
										<option key="2" value="NEW">
											New
										</option>
										<option key="3" value="REJECTED">
											Rejected
										</option>
										<option key="4" value="PROCESSED">
											Processed
										</option>
									</select>
								</div>
								    <div className="ml-2">
                                        <button
                                            onClick={this.searchClick}
                                            className="btn btn-primary btn-xl "
                                            type="button"
                                        >
                                
                                            Search
                                        </button>
									
								    </div>

                                    <div className="ml-2">
                                        <button
                                            onClick={this.resetSearchClick}
                                            hidden={this.state.hiddenResetSearch}
                                            className="btn btn-secondary btn-xl "
                                            type="button"
                                        >
                                
                                            Reset search
                                        </button>
									
								    </div>



         </div>







         <h1 hidden={this.state.patientEReceipts.length === 0 || this.state.hideFutureConsultations } className="text-center  mt-3  " >Your e-receipts!</h1>
         <h1 hidden={this.state.patientEReceipts.length !== 0 || this.state.hideFutureConsultations} className="text-center  mt-3 text-danger"  >You don't have e-receipts!</h1>



   
        <div className="container" hidden={this.state.hideFutureConsultations }>
        <table className="table table-hover" style={{ width: "70%", marginTop: "5em", marginLeft: "auto",marginRight: "auto" }}>
						<tbody>
							{this.state.patientEReceipts.map((eReceipt) => (
								<tr
									id={eReceipt.Id}
									key={eReceipt.Id}
									style={{ cursor: "pointer" }}
									
								>

									<td width="130em">
										<img className="img-fluid" src={eReceiptsPicture} width="90em" />
									</td>
									
									<td>
										<div>
											<b>Creation Date: </b>{" "}
											{new Date(eReceipt.EntityDTO.creationDate).toLocaleDateString("en-US", {
												day: "2-digit",
												month: "2-digit",
												year: "numeric",
											})}
										</div>
										<div  >
											<b>Status</b>
										   <b style={{ color: "blue" }}> {eReceipt.EntityDTO.status}</b>
											
										</div>
										<div hidden={eReceipt.EntityDTO.status !== "PROCESSED"}>
											<b>Price: </b> {eReceipt.EntityDTO.price.toFixed(2)} <b>din</b>
										</div>
									</td>

                                   <td>
                                    <button
											type="button"
                                            className="btn btn-outline-primary"
											style={{marginTop : "0.3em"}}
											onClick={() => this.ClickOnEReceipt(eReceipt)}
										>
											Drugs
										</button>
                                    </td>
								</tr>
							))}
						</tbody>
					</table>      
         
        </div>



          
        </div>


		<HistoryPharmacistConsultation

			hideHistoryConsultations={this.state.hideHistoryConsultations}
			appointments= {this.state.historyConsultations}

											
		/>

			<EreceiptDrugsModal

                showDrugModal= {this.state.showDrugModal}  
                drugs= {this.state.allDrugsForSelectedReceipt}                      
                closeModal ={this.closeDrugsModal}                                
            />					



        </React.Fragment>
        
		);
	}
}

export default PatientsEReceipts;