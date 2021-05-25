import React, { Component } from "react";
import PharmacyLogoPicture from "../../Images/pharmacyLogo.jpg" ;
import Axios from "axios";
import GetAuthorisation from "../../Funciton/GetAuthorisation";




const API_URL="http://localhost:8080";

class FoundPharmaciesForDateRange extends Component {
	
    
    
    render() {
		return (
			<div hidden={this.props.hiddenPharmacies}>


                <button  type="button" class="btn btn-link btn-lg"
                        onClick={this.props.backToSelectedDateRange} 
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
                        <button className="dropdown-item" type="button" onClick={this.props.handleSortByPriceAscending} >Sort by price ascending</button>
                        <button className="dropdown-item" type="button" onClick={this.props.handleSortByPriceDescending} >Sort by price descending</button>
                        <button className="dropdown-item" type="button" onClick={this.props.handleSortByPharmacyGradeAscending} >Sort by pharmacy grade ascending</button>
                        <button className="dropdown-item" type="button"onClick={this.props.handleSortByPharmacyGradeDescending} >Sort by pharmacy grade descending</button>
                    </div>
                </div>


                    
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
                                                        style={{  marginTop: "25px" }}
                                                        onClick={() => this.props.showPharmacistForPharmacy(pharmacy)}
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