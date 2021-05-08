import Header from "../../Components/Header";
import React, { Component } from "react";
import Axios from "axios";
import { Redirect } from "react-router-dom";



class registerStaff extends Component {

    state = {
        email: "",
		password: "",
        errorHeader: "",
        redirect: false,
        selected:"",
        hidden: false
        



    };

    handleChangeType = (event) => {
        console.log("USAO");
		this.setState({ selected: "Pharmacy admin" });
        console.log(this.state.selected);
	};
    handleChangeHidden = (event) => {
        console.log("USAO1");
		this.setState({ hidden: true });
        
	};


render() {
        if (this.state.redirect) return <Redirect push to="/" />;
        return(
            <React.Fragment>
            <Header/>
            

            <h5 className=" text-center  mb-0 text-uppercase" style={{ marginTop: "2rem" }}>
						Register Staff
					</h5>

            <br/> 

            

            <div class="container" style={{maxWidth: "40%"}}>
                <form>

                        <div class="dropdown" hidden={this.state.hidden}>
                        <button class="btn btn-secondary dropdown-toggle" type="button" id="dropdownMenuButton" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                            Choose
                        </button>
                        <div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
                            <a class="dropdown-item" onClick={this.handleChangeType} value={"Pharmacy admin"}>Pharmacy admin</a>
                            <a class="dropdown-item" href="#">Dermatologist</a>
                            <a class="dropdown-item" href="#">Supplier</a>
                            <a class="dropdown-item" href="#">System Admin</a>
                        </div>
                        </div>

                        <br/> 
                    
                    <label >{this.state.selected}</label>
                        

                    <div class="form-group">
                        <label style={{textAlign:"start !important"}} for="exampleInputEmail1">Email address</label>
                        <input type="text" onChange={this.handleEmailChange} value={this.state.email} class="form-control" id="emailAddress" aria-describedby="emailHelp" placeholder="Email address"/>
                    </div>
                    <div class="form-group">
                        <label for="exampleInputName1">Name</label>
                        <input type="text" onChange={this.handleNameChange} value={this.state.name} class="form-control" id="name" placeholder="Name"/>
                    </div>
                    <div class="form-group">
                        <label for="exampleInputPassword1">Surname</label>
                        <input type="text" onChange={this.handleSurnameChange} value={this.state.surname} class="form-control" id="surname" placeholder="Surname"/>
                    </div>
                    <div class="form-group">
                        <label for="exampleInputPassword1">Address</label>
                        <input type="text" onChange={this.handleAddressChange} value={this.state.address} class="form-control" id="address" placeholder="Address"/>
                    </div>
                    <div class="form-group">
                        <label for="exampleInputPassword1">Phone number</label>
                        <input type="text" onChange={this.handlePhoneNumberChange} value={this.state.phoneNumber} class="form-control" id="phoneNumber" placeholder="Phone number"/>
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
export default registerStaff;