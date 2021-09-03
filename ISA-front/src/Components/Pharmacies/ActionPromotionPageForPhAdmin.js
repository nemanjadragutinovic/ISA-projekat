import React, { Component } from "react";
import Axios from "axios";
import Header from '../Header';
import MedicamentPicture from "../../Images/discount.jpg";
import GetAuthorisation from "../../Funciton/GetAuthorisation";

import AddActionPromotionModal from "../Modal/AddActionPromotionModal";


const API_URL = "http://localhost:8080";

class ActionPromotionPageForPhAdmin extends Component {



	state = {
		actions: [],
		pharmacyId:'',
		showAddAction:false,

		
	};

	componentDidMount() {
		let pharmacyId = localStorage.getItem("keyPharmacyId")
        this.setState({
            pharmacyId: pharmacyId
        })
		Axios.get(API_URL + "/pharmacy/getAllActionsInPharmacy/" + localStorage.getItem("keyPharmacyId"), {

			validateStatus: () => true,
			headers: { Authorization: GetAuthorisation() },
		})

			.then((res) => {
				console.log(res.data);
				this.setState({ actions: res.data });
			})
			.catch((err) => {
				console.log(err);
			});

	}

    handleOpenAddAction = () => {
		this.setState({ showAddAction: true});
	};
	handleActionClose = () => {
        Axios.get(API_URL + "/pharmacy/getAllActionsInPharmacy/" + localStorage.getItem("keyPharmacyId"), {
            headers: { Authorization: GetAuthorisation() },
        })
            .then((res) => {
                this.setState({ actions: res.data });
                console.log(res.data);

            })
            .catch((err) => {
                console.log(err);
            });
        this.setState({ showAddAction: false });
    }

	render() {


		return (

			<React.Fragment>

				<Header />

				<div id="allActions" >

					<div className="container" style={{ marginTop: "2em" }} >
					<h2 className=" text-center mb-0 mt-2 text-uppercase">Action and Promotion</h2>
                    <button className="btn btn-primary btn-xl" onClick={() => this.handleOpenAddAction()}>Add Action and Promotion</button>
						<table className={"table"} style={{ width: "75%", marginTop: "3rem"  ,marginLeft:"16%"}}>
							<tbody>
								{this.state.actions.map((action) => (
									<tr id={action.Id} key={action.Id} >
										<td width="130em" >
											<div style={{ marginTop: "5%" }}>
												<img className="img-fluid" src={MedicamentPicture} width="70em" />
											</div>
										</td>
                                    <td>
										<div hidden={action.EntityDTO.actionType!=="DRUGDISCOUNT"}>
											<b >Action for drug buying</b>
										</div>
                                        <div hidden={action.EntityDTO.actionType!=="EXAMINATIONDISCOUNT"}>
											<b >Action for examination</b>
										</div>
                                        <div hidden={action.EntityDTO.actionType!=="CONSULTATIONDISCOUNT"}>
											<b >Action for consultation</b>
										</div>
										<div>
											<b>Date from: </b> {new Date(action.EntityDTO.dateFrom).toDateString()}
										</div>
										<div>
											<b>Date to: </b> {new Date(action.EntityDTO.dateTo).toDateString()}
										</div>
                                        <div>
											<b>Discount: </b> {action.EntityDTO.discount} <b> % </b>
										</div>
									</td>
										
									</tr>
								))}
							</tbody>
						</table>





					</div>
					<AddActionPromotionModal show={this.state.showAddAction} pharmacyId={this.state.pharmacyId} onCloseModal={this.handleActionClose}/>
				</div>

			</React.Fragment>


		);
	}
}

export default ActionPromotionPageForPhAdmin;
