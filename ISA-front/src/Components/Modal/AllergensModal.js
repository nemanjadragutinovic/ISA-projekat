import React, { Component } from "react";
import { Button, Modal } from "react-bootstrap";
import Axios from "axios";
import GetAuthorisation from "../../Funciton/GetAuthorisation";
import UnsuccessfulAlert from "../Alerts/UnsuccessfulAlert";
import SuccessfulAlert from "../Alerts/SuccessfulAlert";

const API_URL="http://localhost:8080";

class AllergensModal extends Component {
	state = {
		allAllergens: [],
        newAllergen : ""
	};


    componentDidMount() {
		this.setState({ allAllergens: this.props.userAllergens });
	}


    handleChangeNewAllergen= (event) => {
		this.setState({ newAllergen: event.target.value });
	};


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
					<Modal.Title id="contained-modal-title-vcenter">{this.props.header}</Modal.Title>
				</Modal.Header>
				<Modal.Body>
					<SuccessfulAlert
						hidden={this.props.hiddenAllergenSuccessfulAlert}
						header={this.props.successfulAllergenHeader}
						message={this.props.successfulAllergenMessage}
						handleCloseAlert={this.props.handleCloseAllergenAlertSuccessful}
					/>
					<UnsuccessfulAlert
						hidden={this.props.hiddenAllergenErrorAlert}
						header={this.props.errorAllergenHeader}
						message={this.props.errorAllergenMessage}
						handleCloseAlert={this.props.handleCloseAllergenAlertError}
					/>


                    <div  className="form-group">
                    <div className="form-row justify-content-center mr-3">
                          
                            <div  className="mr-2">
							<input
								placeholder="Add allergen"
								class="form-control"
								type="text"
								onChange={this.handleChangeNewAllergen}
								value={this.state.newAllergen }
							/>
                            </div>
                            
                            <div  className="mr-2">
                            <Button onClick={() => this.props.AddAllergen(this.state.newAllergen)} style={{ width: "100%" }} variant="primary">
												Add allergen
							</Button>
                            </div>
                            </div>
						
					</div>



					<h4>{this.props.subheader}</h4>
					<table className="table" style={{ width: "100%" }}>
						<thead>
							<td className="col-md-3">Allergen name</td>
							<td className="col-md-1"></td>
						</thead>
						<tbody>
							{this.props.userAllergens.map((allergen) => (
								<tr id={allergen.Id} key={allergen.Id}>
									<td>{allergen.EntityDTO.name}</td>
									<td>
										
										<Button onClick={() => this.props.RemoveAllergen(allergen.EntityDTO.name)} variant="danger" >
											Remove
										</Button>
										
									</td>
								</tr>
							))}
						</tbody>
					</table>
				</Modal.Body>
				<Modal.Footer>
					
				</Modal.Footer>
			</Modal>
		);
	}
}

export default AllergensModal;