import Header from './Header';
import React, { Component } from "react";
import Axios from "axios";
import { Redirect } from "react-router-dom";
import ChangePasswordModal from './Modal/ChangePasswordModal';

class LoginPage extends Component {

    state = {
        errorHeader: "",
		errorMessage: "",
		hiddenErrorAlert: true,
		email: "",
		password: "",
		redirect: false,
		emailError: "none",
		passwordError: "none",
		openPasswordModal: false,
		oldPasswordEmptyError: "none",
		newPasswordEmptyError: "none",
		newPasswordRetypeEmptyError: "none",
		newPasswordRetypeNotSameError: "none",
		errorPasswordHeader: "",
		errorPasswordMessage: "none",
		hiddenPasswordErrorAlert: true
        



    };

    handleEmailChange = (event) => {
		this.setState({ email: event.target.value });
	};
    handlePasswordChange = (event) => {
		this.setState({ password: event.target.value });
	};
    handlePasswordModalClose = () => {
        this.setState({ openPasswordModal: false });
    };
    changePassword = (oldPassword, newPassword, newPasswordRetype) => {
        console.log(oldPassword, newPassword, newPasswordRetype);
    
        this.setState({
            hiddenPasswordErrorAlert: true,
            errorPasswordHeader: "",
            errorPasswordMessage: "none",
            hiddenEditInfo: true,
            oldPasswordEmptyError: "none",
            newPasswordEmptyError: "none",
            newPasswordRetypeEmptyError: "none",
            newPasswordRetypeNotSameError: "none",
            hiddenSuccessAlert: true,
            successHeader: "",
            successMessage: "",
        });

    }

    validateForm = () => {
        if (this.state.email === "") {
            this.setState({ emailError: "inline" });
            return false;
        } else if (this.state.password === "") {
            this.setState({ passwordError: "inline" });
            return false;
        }
    
        return true;
    };

    handleCloseAlert = () => {
        this.setState({ hiddenErrorAlert: true });
    };
    
    handlePasswordModalClose = () => {
        this.setState({ openPasswordModal: false });
    };
    
    handleCloseAlertPassword = () => {
        this.setState({ hiddenPasswordErrorAlert: true });
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
            }else if(res.status === 302){

                this.setState({ openPasswordModal: true });
            }
            
            else {
                console.log(res.data);
                localStorage.setItem("keyToken", res.data.accessToken);
                localStorage.setItem("keyRole", JSON.stringify(res.data.roles));
                localStorage.setItem("expireTime", new Date(new Date().getTime() + res.data.expiresIn).getTime());

                console.log(res.data.roles);

                this.setState({ redirect: true });
            }
        })
        .catch ((err) => {
			console.log(err);
		});
    };

    changePassword = (oldPassword, newPassword, newPasswordRetype) => {
        console.log(oldPassword, newPassword, newPasswordRetype);
    
        this.setState({
            hiddenPasswordErrorAlert: true,
            errorPasswordHeader: "",
            errorPasswordMessage: "none",
            hiddenEditInfo: true,
            oldPasswordEmptyError: "none",
            newPasswordEmptyError: "none",
            newPasswordRetypeEmptyError: "none",
            newPasswordRetypeNotSameError: "none",
            hiddenSuccessAlert: true,
            successHeader: "",
            successMessage: "",
        });
    
        if (oldPassword === "") {
            this.setState({ oldPasswordEmptyError: "initial" });
        } else if (newPassword === "") {
            this.setState({ newPasswordEmptyError: "initial" });
        } else if (newPasswordRetype === "") {
            this.setState({ newPasswordRetypeEmptyError: "initial" });
        } else if (newPasswordRetype !== newPassword) {
            this.setState({ newPasswordRetypeNotSameError: "initial" });
        } else {
            let passwordChangeDTO = { oldPassword, newPassword };
            Axios.post("http://localhost:8080/users/changeFirstPassword", passwordChangeDTO, {
                validateStatus: () => true
            })
                .then((res) => {
                    if (res.status === 403) {
                        this.setState({
                            hiddenPasswordErrorAlert: false,
                            errorPasswordHeader: "Bad credentials",
                            errorPasswordMessage: "You entered wrong password.",
                        });
                    } else if (res.status === 400) {
                        this.setState({
                            hiddenPasswordErrorAlert: false,
                            errorPasswordHeader: "Invalid new password",
                            errorPasswordMessage: "Invalid new password.",
                        });
                    } else if (res.status === 500) {
                        this.setState({
                            hiddenPasswordErrorAlert: false,
                            errorPasswordHeader: "Internal server error",
                            errorPasswordMessage: "Server error.",
                        });
                    } else if (res.status === 204) {
                        this.setState({
                            hiddenSuccessAlert: false,
                            successHeader: "Success",
                            successMessage: "You successfully changed your password.",
                            openPasswordModal: false,
                        });
                    }
                    console.log(res);
                })
                .catch((err) => {
                    console.log(err);
                });
        }
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




            <ChangePasswordModal
				handleCloseAlertPassword={this.handleCloseAlertPassword}
				hiddenPasswordErrorAlert={this.state.hiddenPasswordErrorAlert}
				errorPasswordHeader={this.state.errorPasswordHeader}
				errorPasswordMessage={this.state.errorPasswordMessage}
				emptyOldPasswordError={this.state.oldPasswordEmptyError}
				emptyNewPasswordError={this.state.newPasswordEmptyError}
				emptyNewPasswordRepeatedError={this.state.newPasswordRetypeEmptyError}
				newPasswordAndRepeatedNotSameError={this.state.newPasswordRetypeNotSameError}
				show={this.state.openPasswordModal}
				changePassword={this.changePassword}
				onCloseModal={this.handlePasswordModalClose}
				header="Change password"
			/>
            </React.Fragment>
        );
    }
}
export default LoginPage;