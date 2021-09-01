import React, { Component } from "react";
import { Button, Modal } from "react-bootstrap";
import MedicamentPicture from "../Images/medicament.jpg" ;


class AvailableDrugModal extends Component {
  render() {
    return (
      <Modal
        show={this.props.show}
        size="lg"
        dialogClassName="modal-80w-80h"
        aria-labelledby="contained-modal-title-vcenter"
        centered
        scrollable
        onHide={this.props.onCloseModal}
      >
        <Modal.Header closeButton>
          <Modal.Title id="contained-modal-title-vcenter">
            {this.props.header}
          </Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <h5>{this.props.subheader}</h5>
          <table className="table table-hover" style={{ width: "100%" }}>
            <tbody>
              {this.props.alternativeDrugs.map((drugInstanceName) => (
                <tr id={drugInstanceName.Id} key={drugInstanceName.Id} style={{ cursor: "pointer"}} onClick={() => this.props.handleDrugDetails(drugInstanceName)}>
                    <td width="130em">
						<img className="img-fluid" src={MedicamentPicture} width="70em" />
				    </td>
                    <td>
						<div>
							<b>Name:</b> {drugInstanceName.EntityDTO.drugInstanceName}
						</div>
						
						<div>
							<b>Quantity:</b> {drugInstanceName.EntityDTO.quantity} <b>mg</b>
						</div>
					</td>					
                </tr>
              ))}
            </tbody>
          </table>
        </Modal.Body>
        <Modal.Footer>
          <Button onClick={this.props.onCloseModal}>Close</Button>
        </Modal.Footer>
      </Modal>
    );
  }
}

export default AvailableDrugModal;
