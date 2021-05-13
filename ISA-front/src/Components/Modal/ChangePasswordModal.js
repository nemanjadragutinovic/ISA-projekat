import React, { Component } from "react";
import { Button, Modal } from "react-bootstrap";
import HeadingAlert from "../Alerts/UnsuccessfulAlert";

class ChangePasswordModal extends Component {
	state = {
		oldPassword: "",
		newPassword: "",
		newPasswordRepeated: "",
	};

	handleOldPassword = (event) => {
		this.setState({ oldPassword: event.target.value });
	};

	handleNewPasswordFirst = (event) => {
		this.setState({ newPassword: event.target.value });
	};

	handleNewPasswordRepeat= (event) => {
		this.setState({ newPasswordRepeated: event.target.value });
	};

	render() {
		return (
			<Modal
				show={this.props.show}
				size="lg"
				dialogClassName="modal-80w-80h"
				aria-labelledby="contained-modal-title-vcenter"
				centered
				onHide={this.props.onCloseModal}
			>
				<Modal.Header closeButton>
					<Modal.Title id="contained-modal-title-vcenter">{this.props.header}</Modal.Title>
				</Modal.Header>
				<Modal.Body>
					<HeadingAlert
						hidden={this.props.hiddenPasswordErrorAlert}
						header={this.props.errorPasswordHeader}
						message={this.props.errorPasswordMessage}
						handleCloseAlert={this.props.handleCloseAlertPassword}
					/>
					<div className="control-group">
						<div className="form-group " >
							<label>Old password:</label>
							<input
								placeholder="Old password"
								class="form-control"
								type="password"
								onChange={this.handleOldPassword}
								value={this.state.oldPassword}
							/>
						</div>
						<div className="text-danger" style={{ display: this.props.emptyOldPasswordError }}>
							Old password must be entered.
						</div>
						
					</div>
					<div className="control-group">
						<div className="form-group" >
							<label>New password:</label>
							<input
								placeholder="New password"
								class="form-control"
								type="password"
								onChange={this.handleNewPasswordFirst}
								value={this.state.newPassword}
							/>
						</div>
						<div className="text-danger" style={{ display: this.props.emptyNewPasswordError }}>
							New password must be entered!
						</div>
					</div>
					<div className="control-group">
						<div className="form-group " >
							<label>Repeat new password:</label>
							<input
								placeholder="Repeat new password"
								class="form-control"
								type="password"
								onChange={this.handleNewPasswordRepeat}
								value={this.state.newPasswordRepeated}
							/>
						</div>
						<div className="text-danger" style={{ display: this.props.emptyNewPasswordRepeatedError }}>
							Repeat the new password!
						</div>
						<div className="text-danger" style={{ display: this.props.newPasswordAndRepeatedNotSameError }}>
							Passwords must be same!
						</div>
					</div>
					<div className="form-group text-center">
						<button
							style={{ background: "#1977cc", marginTop: "15px" }}
							onClick={() => this.props.changePassword(this.state.oldPassword, this.state.newPassword, this.state.newPasswordRepeated)}
							className="btn btn-primary btn-xl"
							id="sendMessageButton"
							type="button"
						>
							Change password
						</button>
					</div>
				</Modal.Body>
				<Modal.Footer>
					
				</Modal.Footer>
			</Modal>

            
		);
	}
}

export default ChangePasswordModal;
