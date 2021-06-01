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

        handleSortByName = () => {
            
            
            Axios.get("http://localhost:8080/pharmacy/qrSort-name/"  + this.props.match.params.id , { headers: { Authorization: GetAuthorisation() } })
                .then((res) => {
                    console.log(res.data);
                    this.setState({ pharmacies: res.data, showingSorted: true, sortIndicator: 1 });
                })
                .catch((err) => {
                    console.log(err);
                });
        };

        handleSortByNameReverse = () => {
            
    
            ;
            
            Axios.get("http://localhost:8080/pharmacy/qrSort-nameReverse/"  + this.props.match.params.id , { headers: { Authorization: GetAuthorisation() } })
                .then((res) => {
                    console.log(res.data);
                    this.setState({ pharmacies: res.data, showingSorted: true, sortIndicator: 2 });
                })
                .catch((err) => {
                    console.log(err);
                });
        };

        handleSortByPrice = () => {
            
            
            Axios.get("http://localhost:8080/pharmacy/qrSort-price/"  + this.props.match.params.id, { headers: { Authorization: GetAuthorisation() } })
                .then((res) => {
                    console.log(res.data);
                    this.setState({ pharmacies: res.data, showingSorted: true, sortIndicator: 1 });
                })
                .catch((err) => {
                    console.log(err);
                });
        };
    
        handleSortByPriceReverse = () => {
            
            
            Axios.get("http://localhost:8080/pharmacy/qrSort-priceReverse/"  + this.props.match.params.id, { headers: { Authorization: GetAuthorisation() } })
                .then((res) => {
                    console.log(res.data);
                    this.setState({ pharmacies: res.data, showingSorted: true, sortIndicator: 2 });
                })
                .catch((err) => {
                    console.log(err);
                });
        };

        handleSortByGrade = () => {
           
            
            Axios.get("http://localhost:8080/pharmacy/qrSort-grade/"  + this.props.match.params.id, { headers: { Authorization: GetAuthorisation() } })
                .then((res) => {
                    console.log(res.data);
                    this.setState({ pharmacies: res.data, showingSorted: true, sortIndicator: 5 });
                })
                .catch((err) => {
                    console.log(err);
                });
        };
        
        handleSortByGradeReverse = () => {
           
            
            Axios.get("http://localhost:8080/pharmacy/qrSort-gradeReverse/"  + this.props.match.params.id, { headers: { Authorization: GetAuthorisation() } })
                .then((res) => {
                    console.log(res.data);
                    this.setState({ pharmacies: res.data, showingSorted: true, sortIndicator: 6 });
                })
                .catch((err) => {
                    console.log(err);
                });
        };

        handleSortByCity = () => {
            
            
            Axios.get("http://localhost:8080/pharmacy/qrSort-address/"  + this.props.match.params.id, { headers: { Authorization: GetAuthorisation() } })
                .then((res) => {
                    console.log(res.data);
                    this.setState({ pharmacies: res.data, showingSorted: true, sortIndicator: 3 });
                })
                .catch((err) => {
                    console.log(err);
                });
        };
        
        
        handleSortByCityReverse = () => {
           
            
            Axios.get("http://localhost:8080/pharmacy/qrSort-addressReverse/"  + this.props.match.params.id, { headers: { Authorization: GetAuthorisation() } })
                .then((res) => {
                    console.log(res.data);
                    this.setState({ pharmacies: res.data, showingSorted: true, sortIndicator: 4 });
                })
                .catch((err) => {
                    console.log(err);
                });
        };



    render() {
        if (this.state.redirect) return <Redirect push to={this.state.redirectUrl} />;
        
        return (
            <React.Fragment>
                   
                    <Header />

                    <div className="container" style={{ marginTop: "2%" }}>
					    <h5 className=" text-center mb-0 mt-2 text-uppercase">Pharmacies to buy eRecipe</h5>
                        <h5 className=" text-center mb-0 mt-2 text-uppercase" hidden={this.state.pharmacies.length!==0}>Currently there are no drugs in stock! Come back later.</h5>

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
											Sort by
										</button>
										<div className="dropdown-menu" aria-labelledby="dropdownMenu2">
											<button className="dropdown-item" type="button" onClick={this.handleSortByName}>
												Name - A to Z
											</button>
											<button className="dropdown-item" type="button" onClick={this.handleSortByNameReverse}>
												Name - Z to A
											</button>
                                            <button className="dropdown-item" type="button" onClick={this.handleSortByPrice}>
												Price - low to high
											</button>
											<button className="dropdown-item" type="button" onClick={this.handleSortByPriceReverse}>
												Price - high to low
											</button>
                                            <button className="dropdown-item" type="button" onClick={this.handleSortByGrade}>
												Grade - low to high
											</button>
											<button className="dropdown-item" type="button" onClick={this.handleSortByGradeReverse}>
												Grade - high to low
											</button>
                                            <button className="dropdown-item" type="button" onClick={this.handleSortByCity}>
												Address - A to Z
											</button>
											<button className="dropdown-item" type="button" onClick={this.handleSortByCityReverse}>
												Address - Z to A
											</button>
											
										</div>
									</div>
								</div>
								
							</div>
						</div>
					</div>

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