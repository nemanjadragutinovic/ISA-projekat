

import { withRouter } from "react-router";
import Header from "../../Components/Header";
import Axios from "axios";
import GetAuthorisation from "../../Funciton/GetAuthorisation";
import React, { Component } from "react";
import { Redirect } from "react-router-dom";
import orderImage from "../../Images/orderImage.png";



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

				
			</React.Fragment>
		);
	}

}export default withRouter(OrdersPage);