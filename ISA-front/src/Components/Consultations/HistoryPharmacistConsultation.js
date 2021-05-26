import React, { Component } from "react";
import PharmacistPicture from "../../Images/pharmacist.png" ;
import Axios from "axios";
import GetAuthorisation from "../../Funciton/GetAuthorisation";




const API_URL="http://localhost:8080";

class HistoryPharmacistConsultation extends Component {
	
    
    
    render() {
		return (
		<div className="container" hidden={this.props.hideHistoryConsultations }>
                    

			<h1 hidden={this.props.appointments.length === 0 || this.props.hideHistoryConsultations } className="text-center  mt-3  " >Your history pharmacist consultations!</h1>
			<h1 hidden={this.props.appointments.length !== 0 || this.props.hideHistoryConsultations} className="text-center  mt-3 text-danger"  >You don't have history pharmacist consultations!</h1>


          <table className="table table-hover" style={{ width: "70%", marginTop: "5em", marginLeft: "auto",marginRight: "auto" }}>
						<tbody>
							{this.props.appointments.map((appointment) => (
								<tr
									id={appointment.Id}
									key={appointment.Id}
									style={{ cursor: "pointer" }}
								>

                   <td width="100px">  
                     <img  src={PharmacistPicture} style={{ width: "85px", marginTop: "15px" }}></img>                                 
                                    
                    </td>
									
									<td>
										<div>
											<b>Date: </b>{" "}
											{new Date(appointment.EntityDTO.startDateTime).toLocaleDateString("en-US", {
												day: "2-digit",
												month: "2-digit",
												year: "numeric",
											})}
										</div>
										<div>
											<b>Start time: </b>{" "}
											{new Date(appointment.EntityDTO.startDateTime).toLocaleTimeString("en-US", {
												hour: "2-digit",
												minute: "2-digit",
											})}
										</div>
										<div>
											<b>End time: </b>{" "}
											{new Date(appointment.EntityDTO.endDateTime).toLocaleTimeString("en-US", {
												hour: "2-digit",
												minute: "2-digit",
											})}
										</div>
										<div>
											<b>Price:</b>{" "}
                                         {appointment.EntityDTO.price }
                                             <b>  din</b>
										</div>

                  
										<div>
											<b>Pharmacist: </b>{" "}
											{appointment.EntityDTO.employee.EntityDTO.name + " " + appointment.EntityDTO.employee.EntityDTO.surname}
										</div>

                                         <div>
											<b>Pharmacist grade: </b> {appointment.EntityDTO.employee.EntityDTO.grade} {" "} 
											<i className="icon-star" style={{ color: "yellow"}}></i>
                      
										</div>
										
									</td>

                                    
                                    
								</tr>
							))}
						</tbody>
					</table>
                



          
        </div>
		);
	}
}

export default HistoryPharmacistConsultation;