import React, { Component } from "react";
import { Redirect } from "react-router-dom";
import Header from "../../Components/Header";
import { withRouter } from "react-router";
import Axios from "axios";
import GetAuthorisation from "../../Funciton/GetAuthorisation";
class LoyaltyProgram extends Component {

    state = {
		id: "8c834328-9b5a-42c2-9e04-a1acc75f881d",
		pointsForAppointment: "",
		pointsForConsulting: "",
		pointsToEnterRegularCathegory: "",
		pointsToEnterLoyalCathegory: "",
		pointsToEnterVipCathegory: "",
		appointmentDiscountRegular: "",
		drugDiscountRegular: "",
		consultationDiscountRegular: "",
		appointmentDiscountLoyal: "",
		drugDiscountLoyal: "",
		consultationDiscountLoyal: "",
		appointmentDiscountVip: "",
		drugDiscountVip: "",
		consultationDiscountVip: "",
		nameError: "none",
		surnameError: "none",
		addressError: "none",
		phoneError: "none",
		openSuccessModal: false,
		loyalityRegularColor: "#1977cc",
		loyalitySilverColor: "#808080",
		loyalityGoldColor: "#FFCC00",
		openModalData: false,
		redirect: false,
		redirectUrl: '',
	};

    componentDidMount() {
		
		console.log(localStorage.getItem("keyRole"))
		
		Axios.get("http://localhost:8080/loyaltyProgram/8c834328-9b5a-42c2-9e04-a1acc75f881d", {validateStatus: () => true, headers: { Authorization: GetAuthorisation() } })
			.then((res) => {

				if (res.status === 401) {
					this.setState({
						redirect: true,
						redirectUrl: "/unauthorized"
					});
				} else {

					console.log(res.data);
					this.setState({
						id: res.data.Id,
						pointsForAppointment: res.data.EntityDTO.pointsForAppointment,
						pointsForConsulting: res.data.EntityDTO.pointsForConsulting,
						pointsToEnterRegularCathegory: res.data.EntityDTO.pointsToEnterRegularCathegory,
						pointsToEnterLoyalCathegory: res.data.EntityDTO.pointsToEnterLoyalCathegory,
						pointsToEnterVipCathegory: res.data.EntityDTO.pointsToEnterVipCathegory,
						appointmentDiscountRegular: res.data.EntityDTO.appointmentDiscountRegular,
						drugDiscountRegular: res.data.EntityDTO.drugDiscountRegular,
						consultationDiscountRegular: res.data.EntityDTO.consultationDiscountRegular,
						appointmentDiscountLoyal: res.data.EntityDTO.appointmentDiscountLoyal,
						drugDiscountLoyal: res.data.EntityDTO.drugDiscountLoyal,
						consultationDiscountLoyal: res.data.EntityDTO.consultationDiscountLoyal,
						appointmentDiscountVip: res.data.EntityDTO.appointmentDiscountVip,
						drugDiscountVip: res.data.EntityDTO.drugDiscountVip,
						consultationDiscountVip: res.data.EntityDTO.consultationDiscountVip,
					});
					console.log(res.data);
				}
			})
			.catch((err) => {
				console.log(err);
			});
	}

	handleAppointmentDiscountRegularChange = (event) => {
		this.setState({ appointmentDiscountRegular: event.target.value });
	};
	handleDrugDiscountRegularChange = (event) => {
		this.setState({ drugDiscountRegular: event.target.value });
	};
	handleConsultationDiscountRegularChange = (event) => {
		this.setState({ consultationDiscountRegular: event.target.value });
	};
	handleAppointmentDiscountSilverChange = (event) => {
		this.setState({ appointmentDiscountLoyal: event.target.value });
	};
	handleDrugDiscountSilverChange = (event) => {
		this.setState({ drugDiscountLoyal: event.target.value });
	};
	handleConsultationDiscountSilverChange = (event) => {
		this.setState({ consultationDiscountLoyal: event.target.value });
	};
	handleAppointmentDiscountGoldChange = (event) => {
		this.setState({ appointmentDiscountVip: event.target.value });
	};
	handleDrugDiscountGoldChange = (event) => {
		this.setState({ drugDiscountVip: event.target.value });
	};
	handleConsultationDiscountGoldChange = (event) => {
		this.setState({ consultationDiscountVip: event.target.value });
		console.log(this.state.consultationDiscountVip);
	};

	handlePointsForAppointmentChange = (event) => {
		this.setState({ pointsForAppointment: event.target.value });
	};

	handlePointsForConsultingChange = (event) => {
		this.setState({ pointsForConsulting: event.target.value });
	};

	handlePointsToEnterRegularCathegoryChange = (event) => {
		this.setState({ pointsToEnterRegularCathegory: event.target.value });
	};

	handlePointsToEnterSilverCathegoryChange = (event) => {
		this.setState({ pointsToEnterLoyalCathegory: event.target.value });
	};
	
	handlePointsToEnterGoldCathegoryChange = (event) => {
		this.setState({ pointsToEnterLoyalCathegory: event.target.value });
	};
	
	handleSuccessModalClose = () => {
		this.setState({ openSuccessModal: false });
	};
	
	validateForm = (userDTO) => {
		this.setState({
			nameError: "none",
			surnameError: "none",
			cityError: "none",
			addressError: "none",
			phoneError: "none",
		});

		if (userDTO.name === "") {
			this.setState({ nameError: "initial" });
			return false;
		} else if (userDTO.surname === "") {
			this.setState({ surnameError: "initial" });
			return false;
		} else if (this.addressInput.current.value === "") {
			this.setState({ addressError: "initial" });
			return false;
		} else if (userDTO.phoneNumber === "") {
			this.setState({ phoneError: "initial" });
			return false;
		}
		return true;
	};



	handleChangeInfo = () => {

		if(this.state.pointsForAppointment !== "" && 
		this.state.pointsForConsulting !== "" &&
		this.state.pointsToEnterRegularCathegory !== "" &&
		this.state.pointsToEnterSilverCathegory !== "" &&
		this.state.pointsToEnterGoldCathegory !== "" &&
		this.state.appointmentDiscountRegular !== "" &&
		this.state.drugDiscountRegular !== "" &&
		this.state.consultationDiscountRegular !== "" &&
		this.state.appointmentDiscountSilver !== "" &&
		this.state.drugDiscountSilver !== "" &&
		this.state.consultationDiscountSilver !== "" &&
		this.state.appointmentDiscountGold !== "" &&
		this.state.drugDiscountGold !== "" &&
		this.state.consultationDiscountGold !== ""){

		let loyaltyProgramDTO = {
					pointsForAppointment: this.state.pointsForAppointment*1,
					pointsForConsulting: this.state.pointsForConsulting*1,
					pointsToEnterRegularCathegory: this.state.pointsToEnterRegularCathegory*1,
					pointsToEnterLoyalCathegory: this.state.pointsToEnterLoyalCathegory*1,
					pointsToEnterVipCathegory: this.state.pointsToEnterVipCathegory*1,
					appointmentDiscountRegular: this.state.appointmentDiscountRegular*1,
					drugDiscountRegular: this.state.drugDiscountRegular*1,
					consultationDiscountRegular: this.state.consultationDiscountRegular*1,
					appointmentDiscountLoyal: this.state.appointmentDiscountLoyal*1,
					drugDiscountLoyal: this.state.drugDiscountLoyal*1,
					consultationDiscountLoyal: this.state.consultationDiscountLoyal*1,
					appointmentDiscountVip: this.state.appointmentDiscountVip*1,
					drugDiscountVip: this.state.drugDiscountVip*1,
					consultationDiscountVip: this.state.consultationDiscountVip*1,
				};
				
				Axios.put("http://localhost:8080/loyaltyProgram/update", loyaltyProgramDTO, { headers: { Authorization: GetAuthorisation() } })
					.then((res) => {
						console.log("Success");
						this.setState({ openSuccessModal: true });
					})
					.catch((err) => {
						console.log("GRESKA");
						console.log(err);
					});
		}else{
			this.setState({
				openModalData: true,
			})
		}
				
	};
	
	handleModalDataClose = () => {
		this.setState({ 
			openModalData: false,
		});
	};



    render() {
		
		if (this.state.redirect) return <Redirect push to={this.state.redirectUrl} />;
		return (
			<React.Fragment>
				
				<Header />

				<div className="container" style={{ marginTop: "4%" }}>
					<h3 className=" text-center  mb-0 text-uppercase" style={{ marginTop: "2rem" }}>
						LOYALITY PROGRAM
					</h3>

					<div className="row section-design">
						<div className="col-lg-8 mx-auto">
							<br />
							<form id="contactForm" name="sentMessage">
								<table className="table table-hover" style={{ width: "100%", marginTop: "3rem" }}>
									<tbody>
										<tr style={{ width: "100%", marginTop: "3rem" }}>
											<td>
											<div className="form-col" style={{ fontSize: "1.5em" }}>
												Number of points  to enter cathegory:{" "}
											</div>
											</td>
										</tr>
										<tr>
											<td>
												<div
													className="form-col ml-2 rounded pr-2 pl-2"
													style={{
														color: "blue",
														width: 150,
														
														fontSize: "1.5em",
													}}
												>
													Regular
												</div>
											</td>
											<td>	
												<div
													className="form-col ml-2 rounded pr-2 pl-2"
													style={{
														color: "white",
														width: 100,
														fontSize: "1.5em",
													}}
												>
												<input
													className="form-control"
													type="text"
													disabled="true"
													onChange={this.handlePointsToEnterRegularCathegoryChange}
													value={this.state.pointsToEnterRegularCathegory}
												/>
												</div>
											</td>
											
										</tr>
										<tr>
											<td>
												<div
													className="form-col ml-2 rounded pr-2 pl-2"
													style={{
														color: "green",
														width: 150,
														
														fontSize: "1.5em",
													}}
												>
													Loyal
												</div>
											</td>
											<td>
												<div
													className="form-col ml-2 rounded pr-2 pl-2"
													style={{
														color: "white",
														width: 100,
														fontSize: "1.5em",
													}}
												>
												<input
													className="form-control"
													type="text"
													onChange={this.handlePointsToEnterSilverCathegoryChange}
													value={this.state.pointsToEnterLoyalCathegory}
												/>
												</div>
											</td>
											
										</tr>
										<tr>
											<td>
												<div
													className="form-col ml-2 rounded pr-2 pl-2"
													style={{
														color: "red",
														width: 100,
														
														fontSize: "1.5em",
													}}
												>
													Vip
												</div>
											</td>
											<td>
												<div
													className="form-col ml-2 rounded pr-2 pl-2"
													style={{
														color: "white",
														width: 100,
														fontSize: "1.5em",
													}}
												>
												<input
													className="form-control"
													type="number"
													min="0"
													max="100"
													onChange={this.handlePointsToEnterGoldCathegoryChange}
													value={this.state.pointsToEnterVipCathegory}
												/>
												</div>
											</td>
											
										</tr>
						</tbody>
					</table>
					<table className="table table-hover" style={{ width: "100%", marginTop: "3rem" }}>
						<tbody>
							<tr>
								<td>
									<div className="form-col" style={{ fontSize: "1.5em" }}>
										Number of points for consulting:{" "}
									</div>
									</td>
									<td>
									<div
													className="form-col ml-2 rounded pr-2 pl-2"
													style={{
														color: "white",
														width: 100,
														fontSize: "1.5em",
													}}
												>
										<input
											className="form-control"
											type="number"
											min="0"
											max="50"
											onChange={this.handlePointsForConsultingChange}
											value={this.state.pointsForConsulting}
										/>
										</div>
								</td>
							</tr>
							<tr>
								<td>
									<div className="form-col" style={{ fontSize: "1.5em" }}>
										Number of points for appointment:{" "}
									</div>
								</td>
								<td>
									<div
													className="form-col ml-2 rounded pr-2 pl-2"
													style={{
														color: "white",
														width: 100,
														fontSize: "1.5em",
													}}
												>
									<input
										className="form-control"
										type="number"
										min="0"
										max="50"
										onChange={this.handlePointsForAppointmentChange}
										value={this.state.pointsForAppointment}
									/>
									</div>
								</td>
							</tr>
							<tr>	
								<br/>
								<h3>Regular discount percentages</h3>
							</tr>
							<tr>
								<td>
									<div className="form-col" style={{ fontSize: "1.5em" }}>
										Discount for appointment:{" "}
									</div>
								</td>
								<td>
									<div
													className="form-col ml-2 rounded pr-2 pl-2"
													style={{
														color: "white",
														width: 100,
														fontSize: "1.5em",
													}}
												>
									<input
										className="form-control"
										type="number"
										min="0"
										max="100"
										onChange={this.handleAppointmentDiscountRegularChange}
										value={this.state.appointmentDiscountRegular}
									/>
									</div>
								</td>
							</tr>
							<tr>
								<td>
									<div className="form-col" style={{ fontSize: "1.5em" }}>
										Discount for drug:{" "}
									</div>
								</td>
								<td>
									<div
													className="form-col ml-2 rounded pr-2 pl-2"
													style={{
														color: "white",
														width: 100,
														fontSize: "1.5em",
													}}
												>
									<input
										className="form-control"
										type="number"
										min="0"
										max="100"
										onChange={this.handleDrugDiscountRegularChange}
										value={this.state.drugDiscountRegular}
									/></div>
								</td>
							</tr>
							<tr>
								<td>
									<div className="form-col" style={{ fontSize: "1.5em" }}>
										Discount for consultation:{" "}
									</div>
								</td>
								<td>
									<div
													className="form-col ml-2 rounded pr-2 pl-2"
													style={{
														color: "white",
														width: 100,
														fontSize: "1.5em",
													}}
												>
									<input
										className="form-control"
										type="number"
										min="0"
										max="100"
										onChange={this.handleConsultationDiscountRegularChange}
										value={this.state.consultationDiscountRegular}
									/></div>
								</td>
							</tr>
							<tr>	
								<br/>
								<h3>Loyal discount percentages</h3>
							</tr>
							<tr>
								<td>
									<div className="form-col" style={{ fontSize: "1.5em" }}>
										Discount for appointment:{" "}
									</div>
								</td>
								<td>
									<div
													className="form-col ml-2 rounded pr-2 pl-2"
													style={{
														color: "white",
														width: 100,
														fontSize: "1.5em",
													}}
												>
									<input
										className="form-control"
										type="number"
										min="0"
										max="100"
										onChange={this.handleAppointmentDiscountSilverChange}
										value={this.state.appointmentDiscountLoyal}
									/></div>
								</td>
							</tr>
							<tr>
								<td>
									<div className="form-col" style={{ fontSize: "1.5em" }}>
										Discount for drug:{" "}
									</div>
								</td>			
								<td>
									<div
													className="form-col ml-2 rounded pr-2 pl-2"
													style={{
														color: "white",
														width: 100,
														fontSize: "1.5em",
													}}
												>								
									<input
										className="form-control"
										type="number"
										min="0"
										max="100"
										onChange={this.handleDrugDiscountSilverChange}
										value={this.state.drugDiscountLoyal}
									/></div>
								</td>
							</tr>
							<tr>
								<td>
									<div className="form-col" style={{ fontSize: "1.5em" }}>
										Discount for consultation:{" "}
									</div>
								</td>
								<td>
									<div
										className="form-col ml-2 rounded pr-2 pl-2"
										style={{
											color: "white",
											width: 100,
											fontSize: "1.5em",
										}}
									>
									<input
										className="form-control"
										type="number"
										min="0"
										max="100"
										onChange={this.handleConsultationDiscountSilverChange}
										value={this.state.consultationDiscountLoyal}
									/></div>
								</td>
							</tr>
							<tr>	
								<br/>
								<h3>Vip discount percentages</h3>
							</tr>
							<tr>
								<td>
									<div className="form-col" style={{ fontSize: "1.5em" }}>
										Discount for appointment:{" "}
									</div>
								</td>
								<td>
									<div
													className="form-col ml-2 rounded pr-2 pl-2"
													style={{
														color: "white",
														width: 100,
														fontSize: "1.5em",
													}}
												>
									<input
										className="form-control"
										type="number"
										min="0"
										max="100"
										onChange={this.handleAppointmentDiscountGoldChange}
										value={this.state.appointmentDiscountVip}
									/></div>
								</td>
							</tr>
							<tr>
								<td>
									<div className="form-col" style={{ fontSize: "1.5em" }}>
										Discount for drug:{" "}
									</div>
								</td>			
								<td>	
									<div
													className="form-col ml-2 rounded pr-2 pl-2"
													style={{
														color: "white",
														width: 100,
														fontSize: "1.5em",
													}}
												>
									<input
										className="form-control"
										type="number"
										min="0"
										max="100"
										onChange={this.handleDrugDiscountGoldChange}
										value={this.state.drugDiscountVip}
									/></div>
								</td>
							</tr>
							<tr>
								<td>
									<div className="form-col" style={{ fontSize: "1.5em" }}>
										Discount for consultation:{" "}
									</div>
								</td>
								<td>
									<div
													className="form-col ml-2 rounded pr-2 pl-2"
													style={{
														color: "white",
														width: 100,
														fontSize: "1.5em",
													}}
												>
									<input
										className="form-control"
										type="number"
										min="0"
										max="100"
										onChange={this.handleConsultationDiscountGoldChange}
										value={this.state.consultationDiscountVip}
									/></div>
								</td>
							</tr>
								<div className="form-group text-center">
									<button
										style={{ background: "#1977cc", marginTop: "15px" }}
										onClick={this.handleChangeInfo}
										className="btn btn-primary btn-xl"
										id="sendMessageButton"
										type="button"
									>
										Change information
									</button>
								</div>
								<br />

								
						</tbody>
					</table>
							</form>
						</div>
					</div>
				</div>
				
			</React.Fragment>
		);
	}



}
export default withRouter(LoyaltyProgram);