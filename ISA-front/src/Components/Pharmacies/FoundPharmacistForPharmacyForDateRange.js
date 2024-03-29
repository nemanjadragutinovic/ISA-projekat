import React, { Component } from "react";
import PharmacistPicture from "../../Images/pharmacist.png" ;
import Axios from "axios";
import GetAuthorisation from "../../Funciton/GetAuthorisation";




const API_URL="http://localhost:8080";

class FoundPharmacistForPharmacyForDateRange extends Component {
	
    
    
    render() {
		return (
			<div hidden={this.props.hiddenPharmacist} >

                
               
                <button  type="button" class="btn btn-link btn-lg"
                        onClick={this.props.backToPharmacies} 
                         style={{ width: "100px" , marginTop: "40px", marginLeft: "5em"}}>        
                          Back
                </button>

                


				<div className="container">
                    


                    
                <div className="dropdown" style={{  marginTop: "3em" }}>
                        <button className="btn btn-primary btn-lg dropdown-toggle"
                            type="button" id="dropdownMenu2"
                            data-toggle="dropdown" 
                            aria-haspopup="true" 
                            aria-expanded="false">
                            Sort
                        </button>
                    <div className="dropdown-menu" aria-labelledby="dropdownMenu2">
                        <button className="dropdown-item" type="button" onClick={this.props.handleSortByPharmacistGradeAscending} >Sort by pharmacist grade ascending</button>
                        <button className="dropdown-item" type="button" onClick={this.props.handleSortByPharmacistGradeDescending} >Sort by pharmacist grade descending</button>
                    </div>
                </div>



                    <table className="table table-hover" style={{ width: "90%", marginTop: "5em", marginLeft: "auto",marginRight: "auto" }}>
                                  <tbody>
                                      {this.props.pharmacists.map((pharmacist) => (
                                          <tr
                                              id={pharmacist.Id}
                                              key={pharmacist.Id}
                                              style={{ cursor: "pointer" }}
                                          >
          
                                            <td width="100px">  
                                            <img  src={PharmacistPicture} style={{ width: "85px" }}></img>                                 
                                                            
                                            </td>
                                              
                                              <td>
                                                            
                                                    <div>
                                                        <b>Name: </b> {pharmacist.EntityDTO.name}
                                                    </div>
                                                    <div>
                                                        <b>Surname: </b>{pharmacist.EntityDTO.surname}
                                                    </div>

                                                    

                                                    <div>
                                                        <b>Grade: </b> {pharmacist.EntityDTO.grade}
                                                        <i className="icon-star" style={{ color: "yellow"}}></i>
                                                    </div>
                                            
                                                  
                                              </td>

                                            <td>
                                                <button
                                                        type="button"
                                                        className="btn btn-outline-primary"
                                                        style={{  marginTop: "25px" }}
                                                        onClick={() => this.props.reserveAppointmentForPharmacist(pharmacist)}
                                                >
                                                        Reserve consultation
                                                </button>
                                            </td>



                                          </tr>
                                      ))}
                                  </tbody>
                              </table>
                    </div>
			</div>
		);
	}
}

export default FoundPharmacistForPharmacyForDateRange;