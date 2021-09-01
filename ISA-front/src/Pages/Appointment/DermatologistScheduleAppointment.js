import React, { Component } from "react";
import Header from "../../Components/Header";
import Axios from "axios";
import ModalDialog from "../../Components/ModalDialog";
import GetAuthorisation from '../../Funciton/GetAuthorisation';
import { withRouter } from "react-router";
import { Redirect } from "react-router-dom";
const API_URL="http://localhost:8080";

class DermatologistScheduleAppointment extends Component {
	state = {
		id: "",
		appointments: [],
		openModalSuccess: false,
		redirect: false,
        redirectUrl: ''
	};

	componentDidMount() {
		const id = this.props.match.params.id;
		this.setState({
			id:id
		});

		Axios.get(API_URL + "/appointment/dermatologist-created-appointment", { validateStatus: () => true, headers: { Authorization: GetAuthorisation() } })
			.then((res) => {
				if (res.status === 401) {
					this.setState({
						redirect: true,
						redirectUrl: "/unauthorized"
					});
				} else if (res.status == 200) {
					this.setState({ appointments: res.data });
					console.log(res.data);
				}
			})
			.catch((err) => {
				console.log(err);
			});
	}

	handleAppointmentClick = (appointmentId) => {
		Axios.post(API_URL + "/appointment/schedule-appointment",
			{ appointmentId: appointmentId, patientId: this.state.id },
			{ headers: { Authorization: GetAuthorisation() } }
		)
			.then((res) => {
				this.setState({ openModalSuccess: true });
				console.log(res.data);
			})
			.catch((err) => {
				console.log(err);
				alert("Appointment can't be scheduled");
			});
	};

	handleModalSuccessClose = () => {
		this.setState({
			openModalSuccess: false,
			redirect: true,
			redirectUrl: "/patient-profile/" + this.state.id
		});
	};

	render() {

		if (this.state.redirect) return <Redirect push to={this.state.redirectUrl} />;

		return (
			<React.Fragment>
				<Header />

				<div className="container" style={{ marginTop: "10%" }}>
					<h3 className=" text-center mb-0 mt-2 text-uppercase">Schedule Appointment</h3>

					<h5 className="mb-0 mt-5">
						Click on appointment to schedule
					</h5>

					<table
						className="table table-hover"
						style={{ width: "70%", marginTop: "3rem" }}
					>
						<tbody>
							{this.state.appointments.map((appointment) => (
								<tr
									id={appointment.Id}
									key={appointment.Id}
									onClick={() => this.handleAppointmentClick(appointment.Id)}
									className="rounded"
									style={{ cursor: "pointer" }}
								>
									<td width="190em">
									</td>
									<td>
										<div>
											<b>Date: </b>{" "}
											{new Date(
												appointment.EntityDTO.startDateTime
											).toLocaleDateString("en-US", {
												day: "2-digit",
												month: "2-digit",
												year: "numeric",
											})}
										</div>
										<div>
											<b>Time from: </b>{" "}
											{new Date(
												appointment.EntityDTO.startDateTime
											).toLocaleTimeString("en-US", {
												hour: "2-digit",
												minute: "2-digit",
											})}
										</div>
										<div>
											<b>Time to: </b>{" "}
											{new Date(
												appointment.EntityDTO.endDateTime
											).toLocaleTimeString("en-US", {
												hour: "2-digit",
												minute: "2-digit",
											})}
										</div>
										<div>
											<b>Price: </b> {appointment.EntityDTO.price} <b>din</b>
										</div>
									</td>
								</tr>
							))}
						</tbody>
					</table>
				</div>
				<ModalDialog
					show={this.state.openModalSuccess}
					onCloseModal={this.handleModalSuccessClose}
					header="Successfully scheduled appointment for patient"
					text="Start examination for scheduled appointment."
				/>
			</React.Fragment>
		);
	}
}

export default withRouter(DermatologistScheduleAppointment);
