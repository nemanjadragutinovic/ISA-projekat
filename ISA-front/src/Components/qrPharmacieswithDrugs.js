import React, { Component } from "react";
import Axios from "axios";
import Header from './Header';
import pharmacyLogo from "../Images/pharmacyLogo.jpg"
import { withRouter } from "react-router";
import GetAuthorisation from "../Funciton/GetAuthorisation";
import ModalDialog from "./ModalDialog";
import { Redirect } from "react-router-dom";



class qrPharmacieswithDrugs extends Component {

    state = {
        eReciptId: "",
      pharmacies: [],
      formShowed: false,
      name: "",
      city: "",
      gradeFrom: "",
      gradeTo: "",
      openModal: false,
      openModalAllergen: false,
      openModalRefused: false,
      distanceFrom: "",
      distanceTo: "",
      showingSearched: false,
      showingSorted: false,
      currentLatitude: null,
      currentLongitude: null,
      sortIndicator: 0,
      redirect:false,
      redirectUrl:''
  };

  componentDidMount() {

    const pathParams= window.location.pathname;
    const paramsList= pathParams.split("/");
    const id = paramsList[2];

    console.log(this.props.match.params.id + "xaxaax");

    


    Axios.get("http://localhost:8080/pharmacy/qrPharmacieswithDrugs/" + id, { headers: { Authorization: GetAuthorisation() } })
			.then((res) => {
				console.log(res.data)
				this.setState({ pharmacies: res.data });
			})
			.catch((err) => {
				console.log(err);
			});

   
        }



        handleDrugClick = (id, price) => {
            let PharmacyERecipeDTO = {
                pharmacyId: id,
                eRecipeId: this.props.match.params.id,
                price: price,
            }
            
            Axios.post("http://localhost:8080/pharmacy/buyDrugsWithQr", PharmacyERecipeDTO , { headers: { Authorization: GetAuthorisation() } })
                .then((res) => {
                    console.log(res.data);
                    this.setState({ openModal: true });

                })
                .catch((err) => {
                    console.log(err);
                });
            
            console.log(PharmacyERecipeDTO);
        };

        handleModalClose = () => {
            this.setState({ openModal: false });
        };



    render() {
        if (this.state.redirect) return <Redirect push to={this.state.redirectUrl} />;
        
        return (
            <React.Fragment>
                   
                    <Header />

                    <div className="container" style={{ marginTop: "2%" }}>
					    <h5 className=" text-center mb-0 mt-2 text-uppercase">Pharmacies to buy eRecipe</h5>

                        <table className="table" style={{ width: "100%", marginTop: "3rem" }}>
						<tbody>
							{this.state.pharmacies.map((pharmacy) => (
								<tr id={pharmacy.Id} key={pharmacy.Id} >
									<td width="130em">
										<img className="img-fluid" src={pharmacyLogo} width="70em" />
									</td>
									<td>
										<div>
											<b>Name: </b> {pharmacy.EntityDTO.pharmacy.EntityDTO.name}
										</div>
										<div>
											<b>Address: </b> {pharmacy.EntityDTO.pharmacy.EntityDTO.address.street}, {pharmacy.EntityDTO.pharmacy.EntityDTO.address.city},{" "}
											{pharmacy.EntityDTO.pharmacy.EntityDTO.address.country}
										</div>
										<div>
											<b>Grade: </b> {pharmacy.EntityDTO.grade}
											<i className="icofont-star" style={{ color: "#1977cc" }}></i>
										</div>
										<div>
											<b>Price for all drugs: </b> {pharmacy.EntityDTO.price}
										</div>
									</td>
									<td className="align-middle">
										<div>
											<button type="button" onClick={() => this.handleDrugClick(pharmacy.Id, pharmacy.EntityDTO.price)} className="btn btn-outline-secondary">
												Buy drugs
											</button>
										</div>
										
									</td>
								</tr>
							))}
						</tbody>
					</table>
				</div>
                <ModalDialog
					show={this.state.openModal}
					href="/"
					onCloseModal={this.handleModalClose}
					header="Success"
					text="You have successfully bought drugs."
				/>
                    


                    </React.Fragment>
    );
  }
}
export default withRouter(qrPharmacieswithDrugs);