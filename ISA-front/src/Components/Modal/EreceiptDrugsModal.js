import React, { Component } from "react";
import { Button, Modal } from "react-bootstrap";
import UnsuccessfulAlert from "../../Components/Alerts/UnsuccessfulAlert";
import drugPicture from "../../Images/medicament.jpg" ;

class EreceiptDrugsModal extends Component {
	

	render() {
		return (
			<Modal
				show={this.props.showDrugModal}
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
							{this.props.drugs.map((drug) => (
								<tr id={drug.Id} key={drug.Id}>
									<td width="130em">
										<img className="img-fluid" src={drugPicture} width="90em" />
									</td>
									<td>
										<div>
											<b>Name:</b> {drug.EntityDTO.drugName}
										</div>
										<div>
											<b>Count:</b> {drug.EntityDTO.drugCount}
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

export default EreceiptDrugsModal;