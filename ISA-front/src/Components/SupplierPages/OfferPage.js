import { withRouter } from "react-router";
import Header from "../../Components/Header";
import Axios from "axios";
import GetAuthorisation from "../../Funciton/GetAuthorisation";
import React, { Component } from "react";
import { Redirect } from "react-router-dom";
import orderImage from "../../Images/orderImage.png";
import OfferModal from "../../Components/SupplierPages/OfferModal";
import EditOfferModal from "../../Components/SupplierPages/EditOfferModal";




class Offers extends Component {
	state = {
		price: "",
        selectedDate:new Date(),
		hours: new Date().getHours(),
        minutes: new Date().getMinutes(),
		openModal: false,
		openModalData: false,
		showOfferModal: false,
		openModalDate: false,
		offers: [],
		offerId: "",
        redirect: false,
        redirectUrl: '',
	};


    componentDidMount() {
        Axios.get("http://localhost:8080/offer", { headers: { Authorization: GetAuthorisation() } })
                .then((res) => {
                    console.log(res.data);
                    this.setState({
                        offers: res.data,
                    });
                })
                .catch((err) => {
                    console.log(err);
                });

        
    }

	handleDateChange = (date) => {
		this.setState({ selectedDate: date});
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
    

	handleModalClose = () => {
		this.setState({ 
			openModal: false,
		});
	};

	handleModalDataClose = () => {
		this.setState({ 
			openModalData: false,
		});
	};

	handleModalDate = () => {
		this.setState({ 
			openModalDate: false,
		});
	};

	handlePriceChange = (event) => {
		this.setState({ price: event.target.value });
	};

	handleOfferModalClose = () => {
		this.setState({ showOfferModal: false });
	};


    handleSortByAccepted = () => {
        console.log("usao u accept");

		Axios.get("http://localhost:8080/offer/accepted", { headers: { Authorization: GetAuthorisation() } })
					.then((res) => {
						console.log(res.data);
						this.setState({
							offers: res.data,
						});
					})
					.catch((err) => {
						console.log(err);
					});
	};

	handleSortByRejected = () => {
		
		Axios.get("http://localhost:8080/offer/rejected", { headers: { Authorization: GetAuthorisation() } })
					.then((res) => {
						console.log(res.data);
						this.setState({
							offers: res.data,
						});
					})
					.catch((err) => {
						console.log(err);
					});
	};

	handleSortByWaiting = () => {
		
		Axios.get("http://localhost:8080/offer/waiting", { headers: { Authorization: GetAuthorisation() } })
					.then((res) => {
						console.log(res.data);
						this.setState({
							offers: res.data,
						});
					})
					.catch((err) => {
						console.log(err);
					});
	};

	handleReset = () => {
		
        Axios.get("http://localhost:8080/offer", { headers: { Authorization: GetAuthorisation() } })
        .then((res) => {
            console.log(res.data);
            this.setState({
                offers: res.data,
            });
        })
        .catch((err) => {
            console.log(err);
        });
	};

	handleOfferClick = (offer) => {
		console.log(offer)
		this.setState({ 
			price: offer.EntityDTO.price,
			offerId: offer.Id,
			date: offer.EntityDTO.dateToDelivery,
			selectedDate: new Date(offer.EntityDTO.dateToDelivery),
			showOfferModal: true 
		});
	
	};

	handleOffer = () => {
		
		if(this.state.price !==""){
			
		Axios.get("http://localhost:8080/offer/checkUpdate/" + this.state.offerId, { headers: { Authorization: GetAuthorisation() } })
					.then((res) => {
						
						if(res.data){

							
							let offerDate = new Date(this.state.selectedDate.getFullYear(),this.state.selectedDate.getMonth(),this.state.selectedDate.getDate(),this.state.hours,this.state.minutes,0,0);
		
							let OfferDTO = {
								price: this.state.price,
								dateToDelivery: offerDate,
								id: this.state.offerId,
							}

							Axios.put("http://localhost:8080/offer/update", OfferDTO ,{ headers: { Authorization: GetAuthorisation() } })
								.then((res) => {
									console.log(res.data);
									this.setState({ 
										showOfferModal: false 
									});
									Axios.get("http://localhost:8080/offer", { headers: { Authorization: GetAuthorisation() } })
									.then((res) => {
										console.log(res.data);
										this.setState({
											offers: res.data,
										});
									})
									.catch((err) => {
										console.log(err);
									});

								})
								.catch((err) => {
									console.log("GRESKA11");
									console.log(err);
								});
							}
					
					})
					.catch((err) => {
						console.log("GRESKA2");
						console.log(err);
					});
		}else{
			this.setState({
				openModalData: true,
			})

		}

	};
   


    render() {
		if (this.state.redirect) return <Redirect push to={this.state.redirectUrl} />;

		return (
			<React.Fragment>
				
				<Header />

				<div className="container" style={{ marginTop: "2%" }}>
					<h5 className=" text-center mb-0 mt-2 text-uppercase">Offers </h5>
						
					<div className="form-group">
						<div className="form-group controls mb-0 pb-2">
							<div className="form-row mt-3">
								<div className="form-col">
									<div className="dropdown">
										<button
											className="btn btn-primary dropdown-toggle"
											type="button"
											id="dropdownMenu2"
											data-toggle="dropdown"
											aria-haspopup="true"
											aria-expanded="false"
										>
											Search
										</button>
										<div className="dropdown-menu" aria-labelledby="dropdownMenu2">
											<button className="dropdown-item" type="button" onClick={this.handleReset}>
												ALL
											</button>
											<button className="dropdown-item" type="button" onClick={this.handleSortByAccepted}>
												ACCEPTED
											</button>
											<button className="dropdown-item" type="button" onClick={this.handleSortByRejected}>
												REJECTED
											</button>
											<button className="dropdown-item" type="button" onClick={this.handleSortByWaiting}>
												WAITING
											</button>
										</div>
									</div>
								</div>
								
							</div>
						</div>
					</div>
						
						
						<table className="table table-hover" style={{ width: "100%", marginTop: "3rem" }}>
							<tbody>
								{this.state.offers.map((offer) => (
									<tr className="rounded">
										<td width="190em">
											<img className="img-fluid" src={orderImage} width="150em" />
										</td>
										<td>
											<div>
												<b>Due to date : </b>{" "}
												{new Date(offer.EntityDTO.dateToDelivery).toLocaleTimeString("en-US", {
													day: "2-digit",
													month: "2-digit",
													year: "numeric",
													hour: "2-digit",
													minute: "2-digit",
												})}
											</div>
											<div>
												<b>Status : </b>{" "}
												{offer.EntityDTO.offerStatus}
											</div>
											<div>
												<b>Price : </b>{" "}
												{offer.EntityDTO.price}{" "}din
											</div>
										</td>

										<td className="align-middle">
											<button
												hidden={offer.EntityDTO.offerStatus !== "WAITING"}
												type="button"
												onClick={() => this.handleOfferClick(offer)}
												className="btn btn-outline-secondary"
											>
												Edit offer
											</button>
										</td>
										
									</tr>
								))}
							</tbody>
						</table>
				</div>
				
				<EditOfferModal
					buttonName="Edit offer"
					header="Edit an offer"
					handlePriceChange={this.handlePriceChange}
					show={this.state.showOfferModal}
					price={this.state.price}
					date={this.state.date}
					onCloseModal={this.handleOfferModalClose}
					giveOffer={this.handleOffer}
					date={this.state.date}
					handleDateChange={this.handleDateChange}
					selectedDate={this.state.selectedDate}
				/>
				
				
			</React.Fragment>
		);
	}



}export default withRouter(Offers);