import React, { Component } from "react";
import Header from "../../Components/Header";
import Axios from "axios";
import GetAuthorisation from "../../Funciton/GetAuthorisation";
import orderImage from "../../Images/orderImage.png";
import 'react-confirm-alert/src/react-confirm-alert.css';
import { confirmAlert } from 'react-confirm-alert'; 
import EditOrderModal from "../Modal/EditOrderModal";
const API_URL = "http://localhost:8080";

class OrdersPageForPhAdmin extends Component {
	state = {
        orders: [],
        pharmacyId:'',
        selectedOrder:'',
        drugsToAdd:[],
        addedDrugs:[],
        showEditOrder:false,
        drugs:[],
        showingSorted:false,
        drugsFromOrder:[],
        drugsForAdd:[],
        orderToEdit:'',
    };

    componentDidMount() {
		let pharmacyId=localStorage.getItem("keyPharmacyId")
		this.setState({
			pharmacyId: pharmacyId
		})
        Axios.get(API_URL + "/order/getAllOrdersForPharmacy/" + localStorage.getItem("keyPharmacyId"), {
			headers: { Authorization: GetAuthorisation() },
		})

			.then((res) => {
				console.log(res.data);
				this.setState({ orders: res.data });
			})
			.catch((err) => {
				console.log(err);
			});

    }

    handleRemoveOrder = (orderId) => {
        console.log(orderId)
        confirmAlert({
            message: 'Are you sure to remove order?',
            buttons: [
              {
                label: 'Yes',
                onClick: () => {
                   let IdDTO={
                       id:orderId
                   }
                    Axios
                    .put(API_URL + "/order/removeOrder",IdDTO, {
                        
                        validateStatus: () => true,
                        headers: { Authorization: GetAuthorisation() },
                    }).then((res) =>{
                        if (res.status === 200) {
                         alert("Order is successfully removed")
                            this.updateOrders();
                        }else if(res.status === 400)
                        {
                           alert("Order has offer")
                        }else if(res.status === 500)
                        {
                         alert("Server Error")
                        }
                        
                    }).catch(() => {

                    });
                }
              },
              {
                label: 'No',
                onClick: () => {
                    
                }
              }
            ]
        });
    }

    updateOrders = () =>{
        Axios.get(API_URL + "/order/getAllOrdersForPharmacy/" + localStorage.getItem("keyPharmacyId"), {
			headers: { Authorization: GetAuthorisation() },
		})

			.then((res) => {
				console.log(res.data);
				this.setState({ orders: res.data });
			})
			.catch((err) => {
				console.log(err);
			});
    }

    
    handleCloseAlertSuccess = () => {
		this.setState({ hiddenSuccessAlert: true });
    };
    
    handleCloseAlertFail = () => {
		this.setState({ hiddenFailAlert: true });
	};

   

    handleCloseEditOrder =()=>{
        this.setState({ showEditOrder: false });

    }

    
      addItem1 = item => {
        this.setState({
            drugsForAdd: [
            ...this.state.drugsForAdd,
            item 
          ]
        })
    }

    removeItem1(e) {
        var array = [...this.state.drugsFromOrder];
        var index = array.indexOf(e)
        if (index !== -1) {
          array.splice(index, 1);
          this.setState({drugsFromOrder: array});
        }
      }

    addItem = item => {

        this.setState({
            drugsFromOrder: [
            ...this.state.drugsFromOrder,
            item 
          ]
        })
    }

    removeItem(e) {
        var array = [...this.state.drugsForAdd]; 
        var index = array.indexOf(e)
        if (index !== -1) {
          array.splice(index, 1);
          this.setState({drugsForAdd: array});
        }
      }

    handleRemoveDrugFromList = (drug) =>{
        this.addItem1(drug);
        this.removeItem1(drug)
     }

     handleAddDrugToList = (drug) =>{
        this.addItem(drug);
        this.removeItem(drug)
     }

    handleEditOrder = (order) =>{

        
            Axios
            .get(API_URL + "/order/getAllDrugsInOrder/"+ order, {
                headers: { Authorization: GetAuthorisation() },
            }).then((res) =>{
                this.setState({drugsFromOrder : res.data});
                console.log(res.data);
            }).catch((err) => {console.log(err);});
    
            Axios
            .get(API_URL + "/order/getAllAddDrugs/"+ order, {
                headers: { Authorization: GetAuthorisation() },
            }).then((res) =>{
                this.setState({drugsForAdd : res.data});
                console.log(res.data);
            }).catch((err) => {console.log(err);});
    
    
            this.setState({
                orderToEdit: order,
                showEditOrder:true,
            })
        }
    

 
    handleReset = () =>{
        Axios.get(API_URL + "/order/getAllOrdersForPharmacy/" + localStorage.getItem("keyPharmacyId"), {
			headers: { Authorization: GetAuthorisation() },
		})

			.then((res) => {
				console.log(res.data);
				this.setState({ orders: res.data });
			})
			.catch((err) => {
				console.log(err);
			});
    }

    handleCreatedOrders = () =>{
        Axios.get(API_URL + "/order/findCreatedOrders/" + localStorage.getItem("keyPharmacyId"), {
			headers: { Authorization: GetAuthorisation() },
		})

			.then((res) => {
				console.log(res.data);
				this.setState({ orders: res.data });
			})
			.catch((err) => {
				console.log(err);
			});
    }

    handleProccessedOrders = () =>{
        Axios.get(API_URL + "/order/findProccesedOrders/" + localStorage.getItem("keyPharmacyId"), {
			headers: { Authorization: GetAuthorisation() },
		})

			.then((res) => {
				console.log(res.data);
				this.setState({ orders: res.data });
			})
			.catch((err) => {
				console.log(err);
			});
    }

    

    render() {
		return (
        <React.Fragment>
            <div>

            </div>
                    <Header />
                
					<div className="container" style={{ marginTop: "2%" }}>
					<h5 className=" text-center mb-0 mt-2 text-uppercase">Orders </h5>
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
											<button className="dropdown-item" type="button" onClick={this.handleProccessedOrders}>
												PROCESSED
											</button>
											<button className="dropdown-item" type="button" onClick={this.handleCreatedOrders}>
												CREATED
											</button>
										</div>
									</div>
								</div>
								
							</div>
						</div>
					</div>
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
											{new Date(order.EntityDTO.dateTo).toLocaleTimeString("en-US", {
													day: "2-digit",
													month: "2-digit",
													year: "numeric",
													hour: "2-digit",
													minute: "2-digit",
												})}
										</div>
										
										<div>
											<b>Creator: </b> {order.EntityDTO.creator}
										</div>

                                        <div>
											<b>Offers: </b> {order.EntityDTO.offersCount}
										</div>

                                        <div>
											<b>Status: </b> {order.EntityDTO.orderStatus}
										</div>
									</td>
									<td className="align-middle">
										
										<div className="mt-2" hidden={order.EntityDTO.offersCount>0}>
											<button
												type="button"
												onClick={() => this.handleRemoveOrder(order.Id)}
												className="btn btn-outline-secondary"
											>
												Remove Order
											</button>
										</div>
                                        <div className="mt-2" hidden={order.EntityDTO.offersCount>0}>
											<button
												type="button"
												onClick={() => this.handleEditOrder(order.Id)}
												className="btn btn-outline-secondary"
											>
												Edit Order
											</button>
										</div>
									</td>
								</tr>
							))}
						</tbody>
					</table>
                    <EditOrderModal show={this.state.showEditOrder} closeModal={this.handleCloseEditOrder}  drugsToAdd={this.state.drugsFromOrder} drugs={this.state.drugsForAdd}
                    pharmacyId={this.state.pharmacyId}
                    removeFromDrugToAdd={this.handleRemoveDrugFromList}
                    addToAddedDrugs={this.handleAddDrugToList}
                            orderToEdit={this.state.orderToEdit}
					        header="Create order"
                            update={this.updateOrders}
				        />
				</div>
                    
                </React.Fragment>
		);
	}
}

export default OrdersPageForPhAdmin