import React, { Component } from "react";
import Header from './Header';
import Axios from "axios";
import { Redirect } from "react-router-dom";

class RegisterPage extends Component {

    state = {
        email: "",
		password: "",
		repeatPassword: "",
        name: "",
		surname: "",
		address: "",
		phoneNumber: "",
        emailError: "none",
		passwordError: "none",
		repeatPasswordError: "none",
		repeatPasswordSameError: "none",
		nameError: "none",
		surnameError: "none",
		addressError: "none",
		phoneError: "none",
		emailNotValid: "none",
        redirect:false
		
        



    };

    handleEmailChange = (event) => {
		this.setState({ email: event.target.value });
	};
    handlePasswordChange = (event) => {
		this.setState({ password: event.target.value });
	};
	
	handleRepeatPasswordChange = (event) => {
		this.setState({ repeatPassword: event.target.value });
	};

	handleNameChange = (event) => {
		this.setState({ name: event.target.value });
	};

	handleSurnameChange = (event) => {
		this.setState({ surname: event.target.value });
	};

	handleAddressChange = (event) => {
		this.setState({ address: event.target.value });
	};

	handlePhoneNumberChange = (event) => {
		this.setState({ phoneNumber: event.target.value });
        console.log("Nemanja");
	};

    validateForm = (userDTO) => {
		this.setState({
			emailError: "none",
			emailNotValid: "none",
			nameError: "none",
			surnameError: "none",
			addressError: "none",
			addressNotFoundError: "none",
			phoneError: "none",
			passwordError: "none",
			repeatPasswordError: "none",
			repeatPasswordSameError: "none",
		});

		if (userDTO.email === "") {
			this.setState({ emailError: "initial" });
			return false;
		} else if (!userDTO.email.includes("@")) {
			this.setState({ emailNotValid: "initial" });
			return false;
		} else if (userDTO.name === "") {
			this.setState({ nameError: "initial" });
			return false;
		} else if (userDTO.surname === "") {
			this.setState({ surnameError: "initial" });
			return false;
        } else if (userDTO.address === "") {
			this.setState({ addressError: "initial" });
			return false;    
		} else if (userDTO.phoneNumber === "") {
			this.setState({ phoneError: "initial" });
			return false;
		} else if (userDTO.password === "") {
			this.setState({ passwordError: "initial" });
			return false;
		} else if (this.state.repeatPassword === "") {
			this.setState({ repeatPasswordError: "initial" });
			return false;
		}else if (userDTO.password !== this.state.repeatPassword) {
			this.setState({ repeatPasswordSameError: "initial" });
			return false;
		}
		return true;
	};

    handleSignUp = () => {

           
            let userDTO = {
                email: this.state.email,
                name: this.state.name,
                surname: this.state.surname,
                address: this.state.address,
                phoneNumber: this.state.phoneNumber,
                password: this.state.password,
            };

            if (this.validateForm(userDTO)) {

            console.log(userDTO);
            Axios.post("http://localhost:8080/reg/patsignup", userDTO, { validateStatus: () => true })
            .then((res) => {
                
                this.setState({ redirect: true });
                
            })
        }

            
    };


    render() {
        if (this.state.redirect) return <Redirect push to="/" />;
        return(
            

        <React.Fragment>
            <Header/>  

            <h5 className=" text-center  mb-0 text-uppercase" style={{ marginTop: "2rem" }}>
						Registration
					</h5>

            <br/>        

            <div class="container" style={{maxWidth: "40%"}}>
                <form>
                    <div class="form-group">
                        <label style={{textAlign:"start !important"}} for="exampleInputEmail1">Email address</label>
                        <input type="text" onChange={this.handleEmailChange} value={this.state.email} class="form-control" id="emailAddress" aria-describedby="emailHelp" placeholder="Email address"/>
                    </div>
                    <div className="text-danger" style={{ display: this.state.emailError }}>
										Email address must be entered.
									</div>
									<div className="text-danger" style={{ display: this.state.emailNotValid }}>
										Email address is not valid.
									</div>
                    <div class="form-group">
                        <label for="exampleInputName1">Name</label>
                        <input type="text" onChange={this.handleNameChange} value={this.state.name} class="form-control" id="name" placeholder="Name"/>
                    </div>
                    <div className="text-danger" style={{ display: this.state.nameError }}>
										Name must be entered.
									</div>
                    <div class="form-group">
                        <label for="exampleInputPassword1">Surname</label>
                        <input type="text" onChange={this.handleSurnameChange} value={this.state.surname} class="form-control" id="surname" placeholder="Surname"/>
                    </div>
                    <div className="text-danger" style={{ display: this.state.surnameError }}>
										Surname must be entered.
									</div>
                    <div class="form-group">
                        <label for="exampleInputPassword1">Address</label>
                        <input type="text" onChange={this.handleAddressChange} value={this.state.address} class="form-control" id="address" placeholder="Address"/>
                    </div>
                    <div className="text-danger" style={{ display: this.state.addressError }}>
										Address must be entered.
									</div>
                    <div class="form-group">
                        <label for="exampleInputPassword1">Phone number</label>
                        <input type="text" onChange={this.handlePhoneNumberChange} value={this.state.phoneNumber} class="form-control" id="phoneNumber" placeholder="Phone number"/>
                    </div>
                    <div className="text-danger" style={{ display: this.state.phoneError }}>
										Phone number must be entered.
									</div>
                    <div class="form-group">
                        <label for="exampleInputPassword1">Password</label>
                        <input type="Password" onChange={this.handlePasswordChange} value={this.state.password} class="form-control" id="password" placeholder="Password"/>
                    </div>
                    <div className="text-danger" style={{ display: this.state.passwordError }}>
										Password must be entered.
									</div>
                    <div class="form-group">
                        <label for="exampleInputPassword1">Repeat password</label>
                        <input type="Password" onChange={this.handleRepeatPasswordChange} value={this.state.repeatPassword} class="form-control" id="passwordRepeat" placeholder="Repeat password"/>
                    </div>
                    <div className="text-danger" style={{ display: this.state.repeatPasswordError }}>
										Repeat password must be entered.
									</div>
									<div className="text-danger" style={{ display: this.state.repeatPasswordSameError }}>
										Passwords are not the same.
									</div>
                    
                    <div class="text-center">
                    <button type="button" class="btn btn-primary " style={{width: "20%"}} onClick={this.handleSignUp}>Submit</button>
                        </div>
                </form>
            </div>  

        </React.Fragment>



        );


    }


}

export default RegisterPage;