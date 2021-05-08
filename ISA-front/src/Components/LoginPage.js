import Header from './Header';
import React, { Component } from "react";
import Axios from "axios";
import { Redirect } from "react-router-dom";

class LoginPage extends Component {

    state = {
        email: "",
		password: "",
        errorHeader: "",
        redirect: false,
        



    };

    handleEmailChange = (event) => {
		this.setState({ email: event.target.value });
	};
    handlePasswordChange = (event) => {
		this.setState({ password: event.target.value });
	};

    handleLogin = () => {
        let loginDTO = { username: this.state.email, password: this.state.password };
        console.log(loginDTO);
        Axios.post("http://localhost:8080/auth/login", loginDTO, { validateStatus: () => true })
        .then((res) => {
            if (res.status === 401) {
                this.setState({ errorHeader: "Bad credentials!"});
            } else if (res.status === 500) {
                this.setState({ errorHeader: "Internal server error!"});
            } else {
                console.log(res.data);
                localStorage.setItem("keyToken", res.data.accessToken);
                localStorage.setItem("keyRole", JSON.stringify(res.data.roles));
                localStorage.setItem("expireTime", new Date(new Date().getTime() + res.data.expiresIn).getTime());

                this.setState({ redirect: true });
            }
        })
        .catch ((err) => {
			console.log(err);
		});
    };

    render() {
        if (this.state.redirect) return <Redirect push to="/" />;
        return(
            <React.Fragment>
                <Header/> 

                <h5 className=" text-center  mb-0 text-uppercase" style={{ marginTop: "2rem" }}>
						Login to Health Clinic
				</h5>

               <br/>  

               <div class="container" style={{maxWidth: "40%"}}>
                <form>
                    <div class="form-group">
                        <label style={{textAlign:"start !important"}} for="exampleInputEmail1">Email address</label>
                        <input type="text" onChange={this.handleEmailChange} value={this.state.email} class="form-control" id="emailAddress" aria-describedby="emailHelp" placeholder="Email address"/>
                    </div>
                    <div class="form-group">
                        <label for="exampleInputPassword1">Password</label>
                        <input type="Password" onChange={this.handlePasswordChange} value={this.state.name} class="form-control" id="password" placeholder="Password"/>
                    </div>
                    
                    
                    <div class="text-center">
                    <button type="button" class="btn btn-primary " style={{width: "20%"}} onClick={this.handleLogin} >Submit</button>
                    </div>

                </form>
            </div>   





            </React.Fragment>
        );
    }
}
export default LoginPage;