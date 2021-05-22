import React, { Component } from "react";
import PharmacyLogoPicture from "../../Images/pharmacyLogo.jpg" ;

class FoundPharmaciesForDateRange extends Component {
	render() {
		return (
			<div hidden={this.props.hiddenPharmacies}>
				<div className="container">
                    
                    <table className="table table-hover" style={{ width: "90%", marginTop: "5em", marginLeft: "auto",marginRight: "auto" }}>
                                  <tbody>
                                      {this.props.pharmacies.map((pharmacy) => (
                                          <tr
                                              id={pharmacy.Id}
                                              key={pharmacy.Id}
                                              style={{ cursor: "pointer" }}
                                          >
          
                                            <td width="100px">  
                                            <img  src={PharmacyLogoPicture} style={{ width: "85px", marginTop: "15px" }}></img>                                 
                                                            
                                            </td>
                                              
                                              <td>
                                                            
                                                    <div>
                                                        <b>Name: </b> {pharmacy.EntityDTO.name}
                                                    </div>
                                                    <div>
                                                        <b>Address: </b> {pharmacy.EntityDTO.address.street}, {pharmacy.EntityDTO.address.city},{" "}
                                                        {pharmacy.EntityDTO.address.country}
                                                    </div>

                                                    <div>
                                                        <b>Consultation price:</b>{" "}
                                                        
                                                        {pharmacy.EntityDTO.price}                
                                                        <b> din</b>
                                                    </div>

                                                    <div>
                                                        <b>Grade: </b> {pharmacy.EntityDTO.grade}
                                                        <i className="icon-star" style={{ color: "yellow"}}></i>
                                                    </div>
                                            
                                                  
                                              </td>

                                            <td>
                                                <button
                                                        type="button"
                                                        className="btn btn-outline-primary"
                                                        onClick={() => this.showPharmacistForPharmacy(pharmacy.Id)}
                                                >
                                                        Show pharmacists
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

export default FoundPharmaciesForDateRange;