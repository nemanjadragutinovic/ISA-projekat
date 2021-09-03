import React, { Component } from 'react';
import { Button, Modal } from 'react-bootstrap';

import Axios from 'axios';

import GetAuthorisation from "../../Funciton/GetAuthorisation";

import orderImage from "../../Images/orderImage.png";

const API_URL = "http://localhost:8080";

class OffersforOrderModal extends Component {
    state = {
      
    }

    componentDidMount() {
    }


    handleClickOnClose = () => {
        this.props.closeModal();
    }

    handleAcceptOffer = (offerId) => {
        let orderOfferDTO = {
            orderId: this.props.orderId,
            offerId :offerId,
            
        };
      console.log(orderOfferDTO);
        Axios
        .put(API_URL + "/offer/accept", orderOfferDTO, {
           
            headers: { Authorization: GetAuthorisation() },
        }).then((res) =>{
            if (res.status === 204) {
                alert("Offer is successfully accepted");
                this.handleClickOnClose();
            }else if(res.status === 400)
            {
                alert("It is not possible to accept Offer");
            }else if(res.status === 500)
            {
                alert("Error");
            }
            console.log(res.data);
        }).catch((err) => {
        });
    }



    render() { 
        return ( 
            <Modal
                show = {this.props.show}
                size = "lg"
                dialogClassName="modal-120w-100h"
                aria-labelledby="contained-modal-title-vcenter"
                centered
                >
                <Modal.Header >
                    <Modal.Title >
                        {this.props.header}
                    </Modal.Title>
                </Modal.Header>
                <Modal.Body>
                <div className="container">      
                <table className="table" style={{ width: "100%", marginTop: "3rem" }}>
							<tbody>
								{this.props.offers.map((offer) => (
									<tr className="rounded">
										<td width="190em">
											<img className="img-fluid" src={orderImage} width="150em" />
										</td>
										<td>
											<div>
												<b>Due to date : </b>{" "}
												{new Date(offer.EntityDTO.dateToDelivery).toLocaleTimeString("en-US", {
													day: "2-digit",
													month: "2-digit",
													year: "numeric",
													hour: "2-digit",
													minute: "2-digit",
												})}
											</div>
											<div>
												<b>Price : </b>{" "}
												{offer.EntityDTO.price}{" "}din
											</div>
										</td>
                                        <td className="align-middle">
										<div className="mt-2" >
											<button
												type="button"
												onClick={() => this.handleAcceptOffer(offer.Id)}
												className="btn btn-outline-secondary"
											>
												Accept
											</button>
										</div>
                                       
									</td>
										
									</tr>
								))}
							</tbody>
						</table>
                 </div>
                </Modal.Body>
                <Modal.Footer>
                    <Button onClick={() => this.handleClickOnClose()}>Close</Button>
                </Modal.Footer>
            </Modal>
         );
    }
}
 
export default OffersforOrderModal;