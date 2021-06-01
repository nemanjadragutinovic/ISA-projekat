import React, { Component } from "react";
import Header from '../../Components/Header';
import drugPicture from "../../Images/medicament.jpg" ;

const API_URL="http://localhost:8080";

class HistoryDrugsReservations extends Component {
	
  
    
  


  componentDidMount() {

    if (!this.hasRole("ROLE_PATIENT")) {
			this.props.history.push('/login');
    }

	}
      

  hasRole = (requestRole) => {
    let currentRoles = JSON.parse(localStorage.getItem("keyRole"));

    if (currentRoles === null) return false;


    for (let currentRole of currentRoles) {
      if (currentRole === requestRole) return true;
    }
    return false;
  };


 

	

	render() {
	

		return (
      
      <React.Fragment>

      
         <div className="container"  hidden={this.props.hideHistoryReservations }>


         <h1 hidden={this.props.drugsReservations.length === 0 || this.props.hideHistoryDrugsReservations  } className="text-center  mt-3  " >Your drugs reservations in history!</h1>
         <h1 hidden={this.props.drugsReservations.length !== 0 || this.props.hideHistoryDrugsReservations } className="text-center  mt-3 text-danger"  >You don't have drugs reservations in history!</h1>



        
   
        <div className="container" hidden={this.props.hideHistoryDrugsReservations }>
                    
          <table className="table table-hover" style={{ width: "70%", marginTop: "5em", marginLeft: "auto",marginRight: "auto" }}>
						<tbody>
							{this.props.drugsReservations.map((drugReservation) => (
								<tr
									id={drugReservation.Id}
									key={drugReservation.Id}
									style={{ cursor: "pointer" }}
								>

                   <td width="100px">  
                     <img  src={drugPicture} style={{ width: "85px", marginTop: "15px" }}></img>                                 
                                    
                    </td>
									
									<td>
										<div>
											<b>Start date: </b>{" "}
											{new Date(drugReservation.EntityDTO.startDate).toLocaleDateString("en-US", {
												day: "2-digit",
												month: "2-digit",
												year: "numeric",
											})}
										</div>
										<div>
											<b>Start time: </b>{" "}
											{new Date(drugReservation.EntityDTO.startDate).toLocaleTimeString("en-US", {
												hour: "2-digit",
												minute: "2-digit",
											})}
										</div>

                                        <div>
											<b>End date: </b>{" "}
											{new Date(drugReservation.EntityDTO.endDate).toLocaleDateString("en-US", {
												day: "2-digit",
												month: "2-digit",
												year: "numeric",
											})}
										</div>    

										<div>
											<b>End time: </b>{" "}
											{new Date(drugReservation.EntityDTO.endDate).toLocaleTimeString("en-US", {
												hour: "2-digit",
												minute: "2-digit",
											})}
										</div>
										<div>
											<b>Price :</b>{" "}
                                            <b style={{color : "red"}}> {(Math.round(drugReservation.EntityDTO.oneDrugPrice * drugReservation.EntityDTO.count * 100) / 100).toFixed(2)} </b>
                                            <b>  din</b>
                                             
										</div>

                  
										<div>
											<b>Pharmacy name : </b>{" "}
											{drugReservation.EntityDTO.pharmacy.EntityDTO.name }
										</div>

                                        <div>
											<b>Pharmacy address: </b>{" "}
											{drugReservation.EntityDTO.pharmacy.EntityDTO.address.street + ", " + drugReservation.EntityDTO.pharmacy.EntityDTO.address.city + ", " + drugReservation.EntityDTO.pharmacy.EntityDTO.address.country }
										</div>
										
									</td>

                                    
										                              
								</tr>
							))}
						</tbody>
					</table>
                </div>



          
        </div>


		

								



        </React.Fragment>
        
		);
	}
}

export default HistoryDrugsReservations;