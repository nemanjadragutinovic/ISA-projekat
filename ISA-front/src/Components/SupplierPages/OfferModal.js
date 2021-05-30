import React, { Component } from "react";
import { Button, Modal } from "react-bootstrap";
import DatePicker from "react-datepicker"
import datepicker from "react-datepicker/dist/react-datepicker.css";


class OfferModal extends Component {
	render() {
		return (
			<Modal
				show={this.props.show}
				dialogClassName="modal-80w-150h"
				aria-labelledby="contained-modal-title-vcenter"
				centered
				onHide={this.props.onCloseModal}
			>
				<Modal.Header closeButton>
					<Modal.Title id="contained-modal-title-vcenter">
						{this.props.header}
					</Modal.Title>
				</Modal.Header>
				<Modal.Body>
					<h5>Give offer</h5>
					<tr>
						<td>
							<div>
								<label>Price:</label>
							</div>
							<div>
								<input
									placeholder="Price"
									className="form-control"
									id="complaints"
									type="number"
									value={this.props.price}
									onChange={this.props.handlePriceChange}
								/>
							</div>
							
						</td>
						<td>
							<div>
								<label>Due to date:</label>
							</div>
							<div>
								<DatePicker
									className="form-control"
									minDate={new Date()}
									onChange={(date) => this.props.handleDateChange(date)}
									selected={this.props.selectedDate}
								/>
							</div>
							
						</td>
					</tr>
					<tr>
							<div>
								<Button className="mt-3" onClick={this.props.giveOffer}>
									{this.props.buttonName}
								</Button>
							</div>
					</tr>
				</Modal.Body>
				<Modal.Footer>
					<Button onClick={this.props.onCloseModal}>Close</Button>
				</Modal.Footer>
			</Modal>
		);
	}
}

export default OfferModal;
