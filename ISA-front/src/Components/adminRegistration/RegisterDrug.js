import GetAuthorisation from "../../Funciton/GetAuthorisation";
import Axios from "axios";
import React, { Component } from "react";
import { withRouter } from "react-router";
import Header from "../../Components/Header";
import { Redirect } from "react-router-dom";




class RegisterDrug extends Component {

    state = {
		name: "",
		producerName:"",
		instanceName: "",
		description: "",
		drugCode: "",
		drugKind: "",
		drugFormat:"",
		loyaltyPoints:"",
		quantity:"",
		drugChange: "",
		openModalData: false,
		drugKinds: [],
		drugFormats: [],
		drugs: [],
		drugReplacements: [],
		drugReplacementsEntity: [],
		manufacturers: [],
		manufacturer:null,
		ingredients: [],
		sideEffects: "",
		onReciept: "",
		recommendAmount: "",
		drugIngredient: "",
		selectedManufacturer: null,
		selectDrugReplacement: null,
		nameError: "none",
		consulationPriceError: "none",
		openModal: false,
		coords: [],
        redirect: false,
        redirectUrl: '',
	};


    componentDidMount() {

		Axios.get("http://localhost:8080/drug/manufacturers")
			.then((res) => {

				if (res.status === 401) {
                    this.setState({
                        redirect: true,
                        redirectUrl: "/unauthorized"
                    });
                } else {
				this.setState({ 
					manufacturers: res.data,
					manufacturer: res.data[0],
				 });
				console.log(res.data);
				}
			})
			.catch((err) => {
				console.log(err);
			});

		Axios.get("http://localhost:8080/drug/drugkind")
			.then((res) => {

				if (res.status === 401) {
                    this.setState({
                        redirect: true,
                        redirectUrl: "/unauthorized"
                    });
                } else {

					this.setState({ 
						drugKinds: res.data ,
						drugKind: res.data[0].EntityDTO.type
					});
					console.log(res.data);
				}
			})
			.catch((err) => {
				console.log(err);
                console.log("uso u err kod get drug kind");
			});
		
		Axios.get("http://localhost:8080/drug/drugformat")
			.then((res) => {
				this.setState({ 
					drugFormats: res.data,
					drugFormat: res.data[0].EntityDTO.type
				});
            
			})
			.catch((err) => {
				console.log(err);
			});
			
		Axios.get("http://localhost:8080/drug")
			.then((res) => {
				this.setState({ drugs: res.data });
                console.log(res.data);	
				this.state.drugChange = res.data[0].EntityDTO.drugInstanceName;
            
			})
			.catch((err) => {
				console.log(err);
                console.log("USAO U GET DRUG ERROR");
			});

		
		
    }

    constructor(props) {
		super(props);
	}

	onManufacturerChange  = (manufacturer) => {
		this.state.selectedManufacturer = manufacturer;
	
	};
	
	
	handleModalClose = () => {
		this.setState({ openModal: false });
	};
	
	addIngredient = (event) => {
		
		if (this.state.drugIngredient === "") {
			return;
		}
  		event.preventDefault();
  		this.state.ingredients.push(this.state.drugIngredient);
		document.getElementById("demo").innerHTML = this.state.ingredients;
  		
	};
	
	
	addReplacement = (event) => {
		if (this.state.drugChange === "") {
			return;
		}
		
		event.preventDefault();
		if(this.state.drugReplacements.includes(this.state.drugChange))
			return;
			
  		this.state.drugReplacementsEntity.push(this.state.selectDrugReplacement);
  		this.state.drugReplacements.push(this.state.drugChange);
		document.getElementById("replacement").innerHTML = this.state.drugReplacements;
		console.log(this.state.drugReplacements);
		console.log(this.state.drugReplacementsEntity, "ENTITY DRUG");
  		
	};
	
	handleDrugChange = (event) => {
		this.setState({ drugChange: event.target.options[event.target.selectedIndex].text,
		selectDrugReplacement:  this.state.drugs[event.target.value]});
	};
	
	onDrugReplacementEntityChange  = (drug) => {
	
	};
	
	handleManufacturerChange = (event) => {
		this.setState({ manufacturer: this.state.manufacturers[event.target.value]});
	};
	
	resetReplacement = (event) => {
  		event.preventDefault();
  		this.setState({drugReplacements: []});
  		this.setState({drugReplacementsEntity: []});
		document.getElementById("replacement").innerHTML = "";
  		
	};
	
	createIngredient = (event) => {

        console.log("usao u fju1");

		
		for (const [index, value] of this.state.ingredients.entries()) {
			let ingredientDTO = {
				name: this.state.ingredients[index],
			};
			
            console.log("usao u fju2");

			Axios.post("http://localhost:8080/ingredients", ingredientDTO, { headers: { Authorization: GetAuthorisation()}})
				.then((res) => {
					console.log("Success");
				})
				.catch((err) => {
					console.log(err);
                    console.log("greska create ingridijent");
				});
		}
  		
	};
	
	
	resetIngredient = (event) => {
  		event.preventDefault();
  		this.setState({ingredients: []});
		document.getElementById("demo").innerHTML = "";
  		
	};
	
	handleOnRecieptChange = (event) => {
		this.setState({ onReciept: event.target.value })
	};

	handleDrugIngredientChange = (event) => {
		this.setState({ drugIngredient: event.target.value });
	};
	
	handleLoyaltyPointsChange = (event) => {
		this.setState({ loyaltyPoints: event.target.value });
	};
	
	handleQuantityChange = (event) => {
		this.setState({ quantity: event.target.value });
	};

	handleRecommendAmountChange = (event) => {
		this.setState({ recommendAmount: event.target.value });
	};
	
	handleNameChange = (event) => {
		this.setState({ name: event.target.value });
	};
	handleProducerNameChange = (event) => {
		this.setState({ producerName: event.target.value });
	};
	
	handleInstanceNameChange = (event) => {
		this.setState({ instanceName: event.target.value });
	};
	
	handleSideEffectsChange = (event) => {
		this.setState({ sideEffects: event.target.value });
	};

	handleDrugCodeChange = (event) => {
		this.setState({ drugCode: event.target.value });
	};
	
	handleDrugKindChange = (event) => {
		this.setState({ drugKind: event.target.value });
	};
	
	handleDrugFormatChange = (event) => {
		this.setState({ drugFormat: event.target.value });
		console.log(event.target.value);
	};
	
	handleDescriptionChange = (event) => {
		this.setState({ description: event.target.value });
	};

	validateForm = (userDTO) => {
		this.setState({
			nameError: "none",
			addressError: "none",
			consulationPriceError: "none",
		});

		if (userDTO.name === "") {
			this.setState({ nameError: "initial" });
			return false;
		} else if (userDTO.consulationPrice === "") {
			this.setState({ consulationPriceError: "initial" });
			return false;
		}
		
		return true;
	};

	handleModalDataClose = () => {
		this.setState({ 
			openModalData: false,
		});
	};

	handleModalClose = () => {
		this.setState({ openModal: false });
	};
	
	handleSignUp = () => {
		if(this.state.name !== "" &&
		this.state.instanceName !== "" &&
		this.state.drugCode !== ""&&
		this.state.loyaltyPoints !== ""&&
		this.state.quantity !== ""&&
		this.state.sideEffects !== ""&&
		this.state.recommendAmount !== ""){

		let drugInstanceDTO = {
			name: this.state.name,
			producerName : this.state.producerName,
			code: this.state.drugCode,
			drugInstanceName: this.state.instanceName,
			drugFormat: this.state.drugFormat,
			quantity: this.state.quantity,
			sideEffects: this.state.sideEffects,
			recommendedAmount: this.state.recommendAmount,
			loyalityPoints: this.state.loyaltyPoints,
			onReciept: document.querySelector('.messageCheckbox').checked,
			drugKind: this.state.drugKind,
		};
		
		console.log(drugInstanceDTO);
		
		Axios.put("http://localhost:8080/drug/add", drugInstanceDTO, { headers: { Authorization: GetAuthorisation()}})
			.then((res) => {
			
				for (const [index, value] of this.state.ingredients.entries()) {
					let ingredientDTO = {
						name: this.state.ingredients[index],
						id: res.data,
					};
					

						
					Axios.put("http://localhost:8080/drug/ingredient", ingredientDTO, { headers: { Authorization: GetAuthorisation()}})
						.then((res) => {
							console.log("Success add ingredient");					
						})
						.catch((err) => {
							console.log(err);
						});
				}
				
				let drugManufacturerDTO = {
					drug_id: res.data,
					manufacturer_id: this.state.manufacturer.Id,
				}
				console.log(drugManufacturerDTO)
				Axios.put("http://localhost:8080/drug/manufacturer", drugManufacturerDTO,  { headers: { Authorization: GetAuthorisation()}})
						.then((res) => {
							console.log("Success");					
						})
						.catch((err) => {
							console.log(err);
						});
				console.log("Success");
				this.setState({ openModal: true });
				
				
				for (const [index, value] of this.state.drugReplacementsEntity.entries()) {
					console.log("ubicu se,", this.state.drugReplacementsEntity[index].EntityDTO.drugInstanceName)
					let ReplaceDrugIdDTO = {
						id: res.data,
						replacement_id: this.state.drugReplacementsEntity[index].Id,
					};
					
					Axios.put("http://localhost:8080/drug/replacement", ReplaceDrugIdDTO, { headers: { Authorization: GetAuthorisation()}})
						.then((res) => {
							console.log("Success");	
							console.log("odradio replacement");					
						})
						.catch((err) => {
							console.log(err);
						});
				}

                this.setState({ redirect: true });
				
				
			})
			.catch((err) => {
				console.log(err);
			});
		}else{
			this.setState({
				openModalData: true,
			})
		}
		
		
	};
	
	handleSelectChange  = (event) => {
		this.setState({ selectValue: event.target.value });
	};

    render() {
        if (this.state.redirect) return <Redirect push to="/" />;
        return (
            <React.Fragment>

<Header />

<div className="container" style={{ marginTop: "2%" }}>
    <h5 className=" text-center  mb-0 text-uppercase" style={{ marginTop: "2rem" }}>
        Register drug
    </h5>

    <div className="row section-design">
        <div className="col-lg-8 mx-auto">
            <br />
            <form id="contactForm" name="sentMessage" novalidate="novalidate">
                <div className="control-group">
                    <div className="form-group controls mb-0 pb-2" style={{ color: "#6c757d", opacity: 1 }}>
                        <label>Name:</label>
                        <input
                            placeholder="Name"
                            class="form-control"
                            type="text"
                            id="name"
                            onChange={this.handleNameChange}
                            value={this.state.name}
                        />
                    </div>
                    <div className="text-danger" style={{ display: this.state.nameError }}>
                        Name must be entered.
                    </div>
                </div>
				<div className="control-group">
                    <div className="form-group controls mb-0 pb-2" style={{ color: "#6c757d", opacity: 1 }}>
                        <label>Producer name:</label>
                        <input
                            placeholder="Producer name"
                            class="form-control"
                            type="text"
                            id="name"
                            onChange={this.handleProducerNameChange}
                            value={this.state.producerName}
                        />
                    </div>
                    <div className="text-danger" style={{ display: this.state.nameError }}>
                        Producer name must be entered.
                    </div>
                </div>
                <div className="control-group">
                    <div className="form-group controls mb-0 pb-2" style={{ color: "#6c757d", opacity: 1 }}>
                        <label>Instance name:</label>
                        <input
                            placeholder="Instance name"
                            class="form-control"
                            type="text"
                            id="name"
                            onChange={this.handleInstanceNameChange}
                            value={this.state.instanceName}
                        />
                    </div>
                    <div className="text-danger" style={{ display: this.state.nameError }}>
                        Instance name must be entered.
                    </div>
                </div>
                <div className="control-group">
                    <div className="form-group controls mb-0 pb-2" style={{ color: "#6c757d", opacity: 1 }}>
                        <label>Quantity:</label>
                        <input
                            placeholder="Quantity"
                            class="form-control"
                            type="number"
                            id="name"
                            onChange={this.handleQuantityChange}
                            value={this.state.quantity}
                        />
                    </div>
                    <div className="text-danger" style={{ display: this.state.nameError }}>
                        Quantity must be entered.
                    </div>
                </div>
                
                <div className="control-group">
                    <div className="form-group controls mb-0 pb-2" style={{ color: "#6c757d", opacity: 1 }}>
                        <label>Drug code:</label>
                        <input
                            placeholder="Drug code"
                            class="form-control"
                            id="consulationPrice"
                            type="text"
                            onChange={this.handleDrugCodeChange}
                            value={this.state.drugCode}
                        />
                    </div>
                    <div className="text-danger" style={{ display: this.state.consulationPriceError }}>
                        Code must be entered.
                    </div>
                </div>
                
                <div className="control-group">	
                
                    <div className="form-group controls mb-0 pb-2" style={{ color: "#6c757d", opacity: 1 }}>
                        <label>Drug kind:</label>
                        <select
                           onChange={this.handleDrugKindChange}
                            value={this.state.drugKind}
                         >{this.state.drugKinds.map((kind) => (
                          <option value={kind.EntityDTO.type}>{kind.EntityDTO.type}</option>
                        ))}	
                        </select>
                        <label>On reciept </label>
                        <input
                        
                            class="messageCheckbox"
                            type="checkbox"
                            id="name"
                            onChange={this.handleOnRecieptChange}
                            value={this.state.onReciept}
                        />
                    </div>
                    
                </div>
                <div className="control-group">
                    <div className="form-group controls mb-0 pb-2" style={{ color: "#6c757d", opacity: 1 }}>
                        <label>Side effects:</label>
                        <input
                            placeholder="Side effects"
                            class="form-control"
                            type="text"
                            id="name"
                            onChange={this.handleSideEffectsChange}
                            value={this.state.sideEffects}
                        />
                    </div>
                    <div className="text-danger" style={{ display: this.state.nameError }}>
                        Side effects must be entered.
                    </div>
                </div>
                <div className="control-group">
                    <div className="form-group controls mb-0 pb-2" style={{ color: "#6c757d", opacity: 1 }}>
                        <label>Recommend amount:</label>
                        <input
                            placeholder="Recommend amount"
                            class="form-control"
                            type="text"
                            id="name"
                            onChange={this.handleRecommendAmountChange}
                            value={this.state.recommendAmount}
                        />
                    </div>
                    <div className="text-danger" style={{ display: this.state.nameError }}>
                        Recommend amount must be entered.
                    </div>
                </div>
                <div className="control-group">
                    <div className="form-group controls mb-0 pb-2" style={{ color: "#6c757d", opacity: 1 }}>
                        <label>Drug ingredient:</label>
                        <input
                            placeholder="Drug ingredient"
                            class="form-control"
                            type="text"
                            id="name"
                            onChange={this.handleDrugIngredientChange}
                            value={this.state.drugIngredient}
                        />
                        <button
                            onClick={this.addIngredient}
                        >
                            Add
                        </button>
                        <button
                            onClick={this.resetIngredient}
                        >
                            Reset ingredients
                        </button>
                        <button
                        type="button"
                            onClick={this.createIngredient}
                        >
                            Create
                        </button>
                        <p id="demo"></p>
                    </div>
                    <div className="text-danger" style={{ display: this.state.nameError }}>
                        Drug ingredient must be entered.
                    </div>
                </div>
                <div className="control-group">
                    <div className="form-group controls mb-0 pb-2" style={{ color: "#6c757d", opacity: 1 }}>
                        <label>Replacement drugs:</label>
                        <select
                            
                            onChange={this.handleDrugChange}
                         >{this.state.drugs.map((drug,index) => (
                          <option onClick={this.onDrugReplacementEntityChange(drug)} value={index}>{drug.EntityDTO.drugInstanceName}</option>
                        ))}	
                        </select>
                        <button
                            onClick={this.addReplacement}
                        >
                            Add replacement
                        </button>
                        <button
                            onClick={this.resetReplacement}
                        >
                            Reset replacement
                        </button>
                        <p id="replacement"></p>
                    </div>
                </div>
                
                <div className="control-group">	
                
                    <div className="form-group controls mb-0 pb-2" style={{ color: "#6c757d", opacity: 1 }}>
                        <label>Drug Format:</label>
                        <select
                           onChange={this.handleDrugFormatChange}
                            value={this.state.drugFormat}
                         >{this.state.drugFormats.map((format) => (
                          <option value={format.EntityDTO.type}>{format.EntityDTO.type}</option>
                        ))}	
                        </select>
                    </div>
                    
                </div>
                
                <div className="control-group">	
                
                    <div className="form-group controls mb-0 pb-2" style={{ color: "#6c757d", opacity: 1 }}>
                        <label>Drug manufacturer:</label>
                        <select
                           onChange={this.handleManufacturerChange}
                          >{this.state.manufacturers.map((manufacturer, index) => (
                          <option value={index}>{manufacturer.EntityDTO.name}</option>
                        ))}	
                        </select>
                    </div>
                    
                </div>
                
                <div className="control-group">
                    <div className="form-group controls mb-0 pb-2" style={{ color: "#6c757d", opacity: 1 }}>
                        <label>Loyalty points:</label>
                        <input
                            placeholder="Loyalty points"
                            class="form-control"
                            type="number"
                            id="name"
                            onChange={this.handleLoyaltyPointsChange}
                            value={this.state.loyaltyPoints}
                        />
                    </div>
                    <div className="text-danger" style={{ display: this.state.nameError }}>
                        Loyalty points must be entered.
                    </div>
                </div>
                <div className="form-group">
                    <button
                        style={{
                            background: "#1977cc",
                            marginTop: "15px",
                            marginLeft: "40%",
                            width: "20%",
                        }}
                        onClick={this.handleSignUp}
                        className="btn btn-primary btn-xl"
                        id="sendMessageButton"
                        type="button"
                    >
                        Sign Up
                    </button>
                </div>
            </form>
        </div>
    </div>
</div>


            </React.Fragment>


        );


	}
}

export default withRouter(RegisterDrug);