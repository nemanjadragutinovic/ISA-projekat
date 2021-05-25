

import { withRouter } from "react-router";
import Header from "../../Components/Header";
import Axios from "axios";
import GetAuthorisation from "../../Funciton/GetAuthorisation";
import React, { Component } from "react";
import { Redirect } from "react-router-dom";
import orderImage from "../../Images/orderImage.png";
import OfferModal from "../../Components/SupplierPages/OfferModal";



class OrdersPage extends Component {
	state = {
		price: "",
		selectedDate: new Date(),
		hours: new Date().getHours(),
        minutes: new Date().getMinutes(),
		replacingDrugs:[],
		pharmacyName:"",
		showOfferModal: false,
		orderId: "",
		showOrderModal: false,
		openModalSuccess: false,
		openModal: false,
		openModalDrugs: false,
		orders:[],
		address:"",
        redirect: false,
        redirectUrl: '',
	};

    componentDidMount() {
        Axios.get("http://localhost:8080/order/getAllOrders", { headers: { Authorization: GetAuthorisation() } })
                .then((res) => {
                    console.log(res.data);
                    this.setState({
                        orders: res.data
                    });
                })
                .catch((err) => {
                    console.log("GRESKA");
                    console.log(err);
                });
        
}

handlePriceChange = (event) => {
	this.setState({ price: event.target.value });
};

handleDateChange = (date) => {
	this.setState({ selectedDate: date });
};

handleMinutesChange = (event) => {
	if (event.target.value > 59) this.setState({ minutes: 59 });
	else if (event.target.value < 0) this.setState({ minutes: 0 });
	else this.setState({ minutes: event.target.value });
};

handleHoursChange = (event) => {
	if (event.target.value > 23) this.setState({ hours: 23 });
	else if (event.target.value < 0) this.setState({ hours: 0 });
	else this.setState({ hours: event.target.value });
};

handleOfferClick = (order) => {
	this.setState({ 
		price: "",
		showOfferModal: true,
		orderId: order.Id,
	});
	
};

handleModalClose = () => {
	this.setState({ 
		openModal: false,
	});
};

handleModalDrugsClose = () => {
	this.setState({ 
		openModalDrugs: false,
	});
};

handleModalSuccessClose = () => {
	this.setState({ 
		openModalSuccess: false,
	});
};

handleOrderClick = (order) => {
	console.log(order,"AA");
	this.setState({
		 showOrderModal: true,
		 address: order.pharmacy.EntityDTO.address.street +", "+ order.pharmacy.EntityDTO.address.city +", " +
		 order.pharmacy.EntityDTO.address.country,
		 pharmacyName: order.pharmacy.EntityDTO.name,
		 replacingDrugs: order.order
	});
};

handleOfferModalClose = () => {
	this.setState({ showOfferModal: false });
};

handleOrderModalClose = () => {
	this.setState({ showOrderModal: false });
};

handleDateChange = (date) => {
	this.setState({ selectedDate: date });
};

handleOffer = () => {
		
	if(this.state.price !==""){

		let offerDate = new Date(this.state.selectedDate.getFullYear(),this.state.selectedDate.getMonth(),this.state.selectedDate.getDate(),this.state.hours,this.state.minutes,0,0);
		let OfferDTO = {
							price: this.state.price,
							dateToDelivery: offerDate,
							id: this.state.orderId,
						}
		Axios.put("http://localhost:8080/offer/drugsCheck", OfferDTO ,{ headers: { Authorization: GetAuthorisation() } })
				.then((res) => {
					console.log("usao u drugs check");
					console.log(res.data);
					if(res.data){
						Axios.post("http://localhost:8080/offer", OfferDTO ,{ headers: { Authorization: GetAuthorisation() } })
							.then((res) => {
								this.setState({
									openModalSuccess: true,
									showOfferModal: false,
								});
								Axios.get("http://localhost:8080/order/getAllOrders", { headers: { Authorization: GetAuthorisation() } })
									.then((res) => {
										console.log(res.data);
										this.setState({
											orders: res.data
										});
									})
									.catch((err) => {
										console.log("GRESKA");
										console.log(err);
									});
							})
							.catch((err) => {
								console.log("CREATE GRESKA");
								console.log(err);
							});
					}else{
						this.setState({
							openModalDrugs: true,
						})
					}
					
				})
				.catch((err) => {
					console.log("GRESKA");
					console.log(err);
				});

		console.log(OfferDTO, "AJDE RADI")
	}else{
		this.setState({
			openModal: true,
		})
	}
		
};


    render() {
		if (this.state.redirect) return <Redirect push to={this.state.redirectUrl} />;

		return (
			<React.Fragment>
			
				<Header />

				<div className="container" style={{ marginTop: "2%" }}>
					<h5 className=" text-center mb-0 mt-2 text-uppercase">Orders </h5>

					<table className="table table-hover" style={{ width: "100%", marginTop: "3rem" }}>
						<tbody>
							{this.state.orders.map((order) => (
								<tr className="rounded">
									<td width="190em">
										<img className="img-fluid" src={orderImage}  width="150em" />
									</td>
									<td>
										<div>
											<b>Date : </b>{" "}
											{new Date(order.EntityDTO.date).toLocaleTimeString("en-US", {
													day: "2-digit",
													month: "2-digit",
													year: "numeric",
													hour: "2-digit",
													minute: "2-digit",
												})}
										</div>
										<div>
											<b>Pharmacy : </b>{" "}
											{order.EntityDTO.pharmacy.EntityDTO.name}
										</div>
										<div>
											<b>Address: </b> {order.EntityDTO.pharmacy.EntityDTO.address.street}, {order.EntityDTO.pharmacy.EntityDTO.address.city},{" "}
											{order.EntityDTO.pharmacy.EntityDTO.address.country}
										</div>
									</td>
									<td className="align-middle">
										
										<div className="mt-2" >
											<button
												type="button"
												onClick={() => this.handleOfferClick(order)}
												className="btn btn-outline-secondary"
											>
												Create an offer
											</button>
										</div>
									</td>
								</tr>
							))}
						</tbody>
					</table>

				</div>
				<OfferModal
					buttonName="Send offer"
					header="Make an offer"
					handlePriceChange={this.handlePriceChange}
					show={this.state.showOfferModal}
					price={this.state.price}
					onCloseModal={this.handleOfferModalClose}
					giveOffer={this.handleOffer}
					handleDateChange={this.handleDateChange}
					selectedDate={this.state.selectedDate}
				/>
				
			</React.Fragment>
		);
	}

}export default withRouter(OrdersPage);