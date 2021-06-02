import React, { Component } from "react";
import { Button, Modal } from "react-bootstrap";
import UnsuccessfulAlert from "../../Components/Alerts/UnsuccessfulAlert";
import eReceiptsPicture from "../../Images/notepad.png" ;

class DrugsEReceiptsModal extends Component {
	

	render() {
		return (
			<Modal
				show={this.props.showModal}
				size="lg"
				dialogClassName="modal-100w-100h"
				aria-labelledby="contained-modal-title-vcenter"
				centered
				onHide={this.props.closeModal}
			>
				<Modal.Header closeButton>
					<Modal.Title id="contained-modal-title-vcenter" >Drugs</Modal.Title>
				
				</Modal.Header>
				<Modal.Body>
					
                <table className="table" >
						<tbody>
							{this.props.eReceipts.map((eRecept) => (
								<tr id={eRecept.Id}
                                     key={eRecept.Id}>
									
                                    <td width="130em">
										<img className="img-fluid" src={eReceiptsPicture} width="90em" />
									</td>
									<td>
                                         <div>
											<b>Creation Date: </b>{" "}
											{new Date(eRecept.EntityDTO.creationDate).toLocaleDateString("en-US", {
												day: "2-digit",
												month: "2-digit",
												year: "numeric",
											})}
										</div>
                                        
                                        <div  >
											<b>Price: </b>{" "}
										    <b style={{ color: "red", marginLeft : "2px" }}> {eRecept.EntityDTO.price} </b>
                                            <b>din</b>
											
										</div>

                                        <div  >
											<b>Pharmacy name: </b>{" "}
										    {eRecept.EntityDTO.pharmacyName}
											
										</div>

                                        <div  >
											<b>status:</b>
										   <b style={{ color: "blue" }}> {eRecept.EntityDTO.status}</b>
											
										</div>
									</td>
								</tr>
							))}
						</tbody>
					</table>

				</Modal.Body>
				<Modal.Footer>
                <div className="form-group text-center">
						<button
							hidden={this.props.hideSuccessfulModalButton}
							style={{ background: "#1977cc", marginTop: "15px" }}
							onClick={() => this.props.closeModal()}
							className="btn btn-primary btn-xl"
							id="sendMessageButton"
							type="button"
						>
							Ok
						</button>
					</div>
				</Modal.Footer>
			</Modal>

            
		);
	}
}

export default DrugsEReceiptsModal;