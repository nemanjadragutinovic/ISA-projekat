import React, { Component } from "react";
import Header from "../../Components/Header";
import Axios from "axios";
import GetAuthorisation from '../../Funciton/GetAuthorisation';
import { withRouter } from "react-router";
import { Redirect } from "react-router-dom";
import ModalDialog from "../../Components/ModalDialog";
import PatientIcon from '../../Images/patient-icon.jpg'
const API_URL="http://localhost:8080";
const TIME_INTERVAL= 24 * 60 * 60 * 1000;



class PatientProfile extends Component {
	state = {
		id: "",
		email: "",
		password: "",
		name: "",
		surname: "",
		address: "",
		phoneNumber: "",
		appointments: [],
		redirect: false,
		redirectUrl: '',
		openModalSuccess: false,
		patientsHistory: false,
	};

	hasRole = (reqRole) => {
		let roles = JSON.parse(localStorage.getItem("keyRole"));

		if (roles === null) return false;

		if (reqRole === "*") return true;

		for (let role of roles) {
			if (role === reqRole) return true;
		}
		return false;
	};

	fetchAppointments = () => {
		const id = this.props.match.params.id;
		Axios.get(API_URL + "/appointment/patient/" + id, { validateStatus: () => true, headers: { Authorization: GetAuthorisation() } })
			.then((res) => {

				if (res.status === 401) {
					this.setState({
						redirect: true,
						redirectUrl: "/unauthorized"
					});
				} else if (res.status == 200 || res.status == 201) {
					let patientsHistory = false;
					if (res.status == 201)
						patientsHistory = true;
					this.setState({ appointments: res.data, patientsHistory: patientsHistory }, () => this.handleSortByDate());
				}

				console.log(res.data);
			})
			.catch((err) => {
				console.log(err);
			});
	};

	componentDidMount() {
		const id = this.props.match.params.id;
		this.setState({
			id: id
		});

		Axios.get(API_URL + "/users/patient/" + id, { validateStatus: () => true, headers: { Authorization: GetAuthorisation() } })
			.then((res) => {
				console.log(res.data);

				if (res.status === 401) {
					this.setState({
						redirect: true,
						redirectUrl: "/unauthorized"
					});
				} else if (res.status == 200) {
					this.setState({
						id: res.data.id,
						email: res.data.EntityDTO.email,
						name: res.data.EntityDTO.name,
						surname: res.data.EntityDTO.surname,
						address: res.data.EntityDTO.address,
						phoneNumber: res.data.EntityDTO.phoneNumber
					});
				}

				console.log(this.state)
			})
			.catch((err) => {
				console.log(err);
			});

		this.fetchAppointments();
	}

	handleExamine = (appointmentId) => {
		this.setState({
			redirect: true,
			redirectUrl: "/appointment-report/" + appointmentId
		});
	};

	handleDidNotShowUp = (appointmentId) => {
		console.log(appointmentId)
		Axios.put(API_URL + "/appointment/patient-did-not-come",
			{ id: appointmentId },
			{ headers: { Authorization: GetAuthorisation() } })
			.then((res) => {
				this.setState({ openModalSuccess: true });
				this.fetchAppointments();
				console.log(res.data);
			})
			.catch((err) => {
				console.log(err);
			});
	};

	handleSchedule = () => {
		if (this.hasRole("ROLE_DERMATHOLOGIST")) {
			this.setState({
				redirect: true,
				redirectUrl: "/dermathologist-schedule-appointment/" + this.props.match.params.id
			});
		} else if (this.hasRole("ROLE_PHARMACIST")) {
			this.setState({
				redirect: true,
				redirectUrl: "/new-appointment/" + this.props.match.params.id
			});
		}
	};

	handleCreateAndSchedule = () => {
		this.setState({
			redirect: true,
			redirectUrl: "/create-and-schedule-appointment/" + this.props.match.params.id
		});
	};

	isCurrentDate = (appointmentDate) => {
		appointmentDate = new Date(appointmentDate);
		appointmentDate.setHours(0, 0, 0, 0);
		let currentDate = new Date();
		currentDate.setHours(0, 0, 0, 0);
		return appointmentDate.getTime() === currentDate.getTime();
	};

	canInteractWithAppointment = (startDateTime) => {
		let currentDateTime = new Date();
		startDateTime = new Date(startDateTime);
		if (currentDateTime.getTime() + TIME_INTERVAL < startDateTime.getTime())
			return false;
		else if (currentDateTime.getTime() > startDateTime.getTime())
			return false;
		else
			return true;
	}

	handleModalSuccessClose = () => {
		this.setState({
			openModalSuccess: false,
		});
	};

	handleSortByDate = () => {
		console.log(this.state.appointments);
		this.state.appointments.sort(this.compareDates);
		this.setState(this.state);
		console.log(this.state.appointments);
	};

	compareDates = (a, b) => {
		if (a.EntityDTO.startDateTime < b.EntityDTO.startDateTime) {
			return 1;
		}
		if (a.EntityDTO.startDateTime > b.EntityDTO.startDateTime) {
			return -1;
		}
		return 0;
	}

	render() {

		if (this.state.redirect) return <Redirect push to={this.state.redirectUrl} />;

		return (
			<React.Fragment>
			
				<Header />

				<div className="container" style={{ marginTop: "8%" }}>
					<h5 className=" text-center  mb-0 text-uppercase" style={{ marginTop: "2rem" }}>
						Patient information
					</h5>

					<div className="row section-design">
						<div className="col-lg-8 mx-auto">
							<br />
							<form id="contactForm" name="sentMessage">
							<img
									style={{ float: "left" }}
									className="img-fluid"
									src={PatientIcon}
									width="150em"
								/>
								<div className="control-group">
									<div
										className="form-group controls mb-0 pb-2"
									>
										<div>
											<b>Name: </b> {this.state.name}
										</div>
									</div>
								</div>
								<div className="control-group">
									<div
										className="form-group controls mb-0 pb-2"
									>
										<div>
											<b>Surname: </b> {this.state.surname}
										</div>
									</div>
								</div>
								<div className="control-group">
									<div
										className="form-group controls mb-0 pb-2"
									>
										<div>
											<b>Email address: </b> {this.state.email}
										</div>
									</div>
								</div>
								<div className="control-group">
									<div
										className="form-group controls mb-0 pb-2"
									>
										<div>
											<b>Phone number: </b> {this.state.phoneNumber}
										</div>
									</div>
								</div>
								<div className="control-group">
									<div
										className="form-group controls mb-0 pb-2"
									>
										<div>
											<b>Address: </b> {this.state.address}
										</div>
									</div>
								</div>
								<br />
								<button
									type="button"
									style={{ width: "80%" }}
									onClick={() =>
										this.handleSchedule()
									}
									className="btn btn-primary"
								>
									Schedule
								</button>
								<br />
								<br />
								<button
									hidden={!this.hasRole("ROLE_DERMATHOLOGIST")}
									type="button"
									style={{ width: "80%" }}
									onClick={() =>
										this.handleCreateAndSchedule()
									}
									className="btn btn-primary"
								>
									Create and schedule appointment
								</button>

							</form>
						</div>
						<div className="control-group mt-4">
							<div
								className="form-group controls mb-0 pb-2"
								style={{ color: "#6c757d", opacity: 1 }}
							>
								<div>
									<h3 hidden={this.state.patientsHistory}>You can't observe patient's appointment history because you did not have appointment for this patient already.</h3>
								</div>
							</div>
						</div>
						<br />


						<table className="table" style={{ width: "70%", marginTop: "3rem" }}>
							<tbody>
								{this.state.appointments.map((appointment) => (
									<tr
										id={appointment.Id}
										key={appointment.Id}
										className="rounded"
									>
										<td
											width="190em"
										>
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
											<div hidden={appointment.EntityDTO.appointmentStatus != "FINISHED"}>
												{appointment.EntityDTO.appointmentStatus == "FINISHED" && appointment.EntityDTO.appointmentReportDTO != undefined && appointment.EntityDTO.appointmentReportDTO != null ? (
													<div style={{whiteSpace: "pre"}}>
														<div>
															<b>Anamensis</b>{" "}
															{appointment.EntityDTO.appointmentReportDTO.anamnesis}
														</div>
														<div>
															<b>Diagnosis</b>{" "}
															{appointment.EntityDTO.appointmentReportDTO.diagnosis}
														</div>
														<div>
															<b>Therapy</b><br />
															<div>
																{appointment.EntityDTO.appointmentReportDTO.therapy}
															</div>	
														</div>
													</div>
												) : (
														<div></div>
													)}

											</div>
										</td>
										<td className="align-right" style={{ width: "35%" }}>
											<div hidden={!this.canInteractWithAppointment(appointment.EntityDTO.startDateTime)}>
												<button
													type="button"
													style={{ width: "60%", float: "right" }}
													hidden={
														appointment.EntityDTO.appointmentStatus != "SCHEDULED"
													}
													onClick={() =>
														this.handleExamine(appointment.Id)
													}
													className="btn btn-primary"
												>
													Start examination
												</button>
												<br />
												<br />
												<button
													type="button"
													style={{ width: "60%", float: "right", verticalAlign: "bottom" }}
													hidden={
														appointment.EntityDTO.appointmentStatus != "SCHEDULED"
													}
													onClick={() =>
														this.handleDidNotShowUp(appointment.Id)
													}
													className="btn btn-danger"
												>
													Patient did not come
												</button>
											</div>
										</td>
									</tr>
								))}
							</tbody>
						</table>

					</div>
				</div>
				<ModalDialog
					
					show={this.state.openModalSuccess}
					onCloseModal={this.handleModalSuccessClose}
					header="Successfully added penalty to patient"
					text="You can start examination for another patient."
				/>
			</React.Fragment>
		);
	}
}

export default withRouter(PatientProfile);

