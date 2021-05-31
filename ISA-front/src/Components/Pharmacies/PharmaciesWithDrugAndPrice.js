import React, { Component } from "react";
import PharmacyLogoPicture from "../../Images/pharmacyLogo.jpg" ;
import Axios from "axios";
import GetAuthorisation from "../../Funciton/GetAuthorisation";




const API_URL="http://localhost:8080";

class PharmaciesWithDrugAndPrice extends Component {
	
    
    
    render() {
		return (
			<div hidden={!this.props.show}>


                <button  type="button" class="btn btn-link btn-lg"
                        onClick={this.props.back} 
                         style={{ width: "100px" , marginTop: "40px", marginLeft: "5em"}}>        
                          Back
                </button>


               

                <div className="container">
                    <h1 hidden={this.props.pharmacies.length!==0}   className=" text-center  mt-3  text-danger" >Drug is not available </h1>
                    <h1 hidden={this.props.pharmacies.length===0}   className=" text-center  mt-3 " >Pharmacies where is drug available </h1>
                    
                    <table className="table" style={{ width: "70%", marginTop: "2em", marginLeft: "auto",marginRight: "auto" }}>
                        
                        <tbody>
                            {
                                this.props.pharmacies.map((pharmacy) => (
                                    <tr key={pharmacy.Id} 
                                         id={pharmacy.Id} >
                                       
                                      <td width="100px">  
                                       
                                        <img src={PharmacyLogoPicture} width="70px"></img>                                 
                                     
                                      </td>


                                      <td>
                                        
                                        <div>  
                                        <b>Name: </b>{pharmacy.EntityDTO.name}
                                        </div>  

                                        <div>  
                                        <b>Location: </b> {pharmacy.EntityDTO.address.street}, {" "} {pharmacy.EntityDTO.address.city},{" "}
										                                      {pharmacy.EntityDTO.address.country}
                                        </div> 

                                        <div>  
                                        <b>Description: </b>{pharmacy.EntityDTO.description}
                                        </div>


                                        <div>
                                        <b>Drug price: </b> {pharmacy.drugPrice} {" "} 
                                        <b>din</b>
                                        
										</div>



                                      </td>

                                      <td>
                                                <button
                                                        type="button"
                                                        className="btn btn-outline-primary"
                                                        style={{  marginTop: "25px" }}
                                                        onClick={() => this.props.openReservationDialog(pharmacy)}
                                                >
                                                        Reserve drug
                                                </button>
                                    </td>  


                                    </tr>
                                ))
                            }
                        </tbody>
                    </table>
                </div>




                   
			</div>
		);
	}
}

export default PharmaciesWithDrugAndPrice;