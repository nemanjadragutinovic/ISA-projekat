import React, { Component } from "react";
import { Button, Modal } from "react-bootstrap";
import DatePicker from "react-datepicker";
import UnsuccessfulAlert from "../Alerts/UnsuccessfulAlert";


class ReservationDrugModal extends Component {
	
    state = {
		selectedDate: new Date(new Date().getTime() + 172800000),
		drugCount: 1,
        drugPrice : 0
	};


    componentDidMount() {
		
        this.setState({ drugPrice : this.props.drugPrice  });
		
         
	}


	handleDateChange = (date) => {
		this.setState({ selectedDate: date });
	};

	handleDrugCountChange = (event) => {
		
	     if (event.target.value > this.props.maxCount) this.setState({ drugCount: this.props.maxCount });
         else if (event.target.value < 1) this.setState({ drugCount: 1 });
         else this.setState({ drugCount: event.target.value });
	};


	render() {
		return (
			<Modal
				show={this.props.show}
				size="lg"
				dialogClassName="modal-80w-80h"
				aria-labelledby="contained-modal-title-vcenter"
				centered
				onHide={this.props.closeModal}
			>
				<Modal.Header closeButton>
					<Modal.Title id="contained-modal-title-vcenter" >Drug reservation</Modal.Title>
				
				</Modal.Header>
				<Modal.Body>
					
                        <UnsuccessfulAlert
                                hidden={this.props.hiddenErrorAlert}
                                header={this.props.errorHeader}
                                message={this.props.errorMessage}
                                handleCloseAlert={this.props.handleCloseErrorAlert}
                            />


               
								<div  >
									<b>The reservation of the drug lasts until the date::</b>
								</div>
							
							
								<div >
									<DatePicker
										className="form-control mr-3"
										minDate={new Date(new Date().getTime() + 172800000)}
										onChange={(date) => this.handleDateChange(date)}
										selected={this.state.selectedDate}
									/>
								</div>
							
							
								<div  style={{ fontSize: "0.8em" }}>
									Minimum period for reservation is 48h before, because you cannot take the drug 24 hours before or less.
								</div>
							
							
								<div style={{ marginTop: "1em" }} >
									<b>Drug count:</b>
									<input
										
										className="form-control "
										style={{ width: "11em" }}
										type="number"
										min="1"
										max={this.props.maxCount}
										onChange={this.handleDrugCountChange}
										value={this.state.drugCount}
									/>
								</div>
							
							
								<div style={{ marginTop: "1em" }}>
									<h5>
										Total price:{" "}
										<b color="red">{(Math.round(this.state.drugCount * this.props.drugPrice * 100) / 100).toFixed(2)} din</b>
									</h5>
								</div>
							
							<div className="form-group text-center">
								<Button className="mt-3" onClick={() => this.props.reserveDrugs(this.state.drugCount, this.state.selectedDate,this.props.drugPrice  )}>
									Reserve drugs
								</Button>
							</div>


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

export default ReservationDrugModal;