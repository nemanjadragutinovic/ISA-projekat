import Header from "../../Components/Header";
import React, { Component } from "react";
import Axios from "axios";
import { Redirect } from "react-router-dom";
import GetAuthorisation from "../../Funciton/GetAuthorisation";
import { Button, Modal } from 'react-bootstrap';
import DatePicker from "react-datepicker";

class AddPharmacistModal extends Component {

    state = {
        selectedStartDate: new Date(),
		selectedEndDate: new Date(),
		timeFrom: 1,
		timeTo: 2,
        errorHeader: "",
        errorMessage: "",
        hiddenErrorAlert: true,
        email: "",
        password: "",
        name: "",
        userRegister: "",
        surname: "",
        address: "",
        openModalData: false,
        phoneNumber: "",
        emailError: "none",
        passwordError: "none",
        nameError: "none",
        surnameError: "none",
        addressError: "none",
        phoneError: "none",
        employeeError: "none",
        emailNotValid: "none",
        openModal: false,
        
        pharmacy: "",
       
        selectValue: "",
     
        showWorkTime:true
    };

    constructor(props) {
        super(props);
        this.addressInput = React.createRef();
    }

    handleShowWorkTime = () => {
        
        let userDTO = {
            email: this.state.email,
            name: this.state.name,
            surname: this.state.surname,
            address: this.state.address,
            phoneNumber: this.state.phoneNumber,
            password: this.state.password,
        };
        if (this.validateForm(userDTO)) {
		this.setState({ showWorkTime: false });
        }
	};


    handleModalEmailClose = () => {
        this.setState({
            openModalEmail: false,
        });
    };

    componentDidMount() {

  

    }


    handleEmailChange = (event) => {
        this.setState({ email: event.target.value });
    };
    handlePharmacyChange = (event) => {
        this.setState({ pharmacy: event.target.value });
    };

    handlePasswordChange = (event) => {
        this.setState({ password: event.target.value });
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
    };



    validateForm = (userDTO) => {

        this.setState({
            emailError: "none",
            emailNotValid: "none",
            nameError: "none",
            surnameError: "none",
            addressError: "none",
            phoneError: "none",

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
        }

        return true;
    };



	handleClickOnClose = () => {
		this.setState({
			showWorkTime: true,
			selectedStartDate: new Date(),
			selectedEndDate: new Date(),
			timeFrom: 1,
			timeTo: 2,
			
		});
		this.props.onCloseModal();
	};

    validateEmployee = (employee) => {

        this.setState({
            employeeError: "none"
        });

        if (employee === "") {
            this.setState({ employeeError: "initial" });
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
                Axios.post("http://localhost:8080/reg/signup-pharmacist/"+this.props.pharmacyId, userDTO, { headers: { Authorization: GetAuthorisation() } })
                    .then((res) => {
                        if (res.status === 400) {
                            alert("Email already exists");
                        } else if (res.status === 500) {
                            console.log("USO")
                            alert("Error");
                        } else {
                            console.log(res.data);
                            console.log("Success");
                            let workTimeDTO = {
                                pharmacyId : this.props.pharmacyId,
                                employee: res.data, 
                                startDate: this.state.selectedStartDate, 
                                endDate:this.state.selectedEndDate,
                                startTime: this.state.timeFrom, 
                                endTime:this.state.timeTo
                            };
                            console.log(workTimeDTO);
                            Axios
                            .put("http://localhost:8080/users/addWorkTime", workTimeDTO, {
                                validateStatus: () => true,
                                headers: { Authorization:  GetAuthorisation() },
                            }).then((res) =>{
                              console.log(res.data);                            
                            
                            }).catch((err) => {
                                console.log(err);
                            });

                            alert("You have successfully registered pharmacist with password "+ res.data);
                            this.handleClickOnClose();
                        }
                    })
                    .catch((err) => {
                        console.log(err);
                    });
            }

        
         
    };
    handleCloseAlert = () => {
        this.setState({ hiddenErrorAlert: true });
    };
    handleSelectChange = (event) => {
        this.setState({ selectValue: event.target.value });
    };

    handleBack = (event) =>{

        this.setState({showWorkTime: true});
    }

    handleStartDateChange = (date) => {
        this.setState({
            selectedStartDate:date,
        });

        if(date>this.state.selectedEndDate){
            this.setState({
                selectedEndDate:date,
            }); 
        }
    }


    handleEndDateChange = (date) => {
        this.setState({selectedEndDate:date});
    }

    handleTimeFromChange= (event) => {
        if(event.target.value < 1){
            this.setState({timeFrom:1});
        }
        else if(event.target.value > 23){
            this.setState({timeFrom:23});
        }
        
        if(event.target.value >= this.state.timeTo){
            this.setState({
                timeFrom:event.target.value,
                timeTo: event.target.value++
            });
        }else{
            this.setState({timeFrom:event.target.value});
        }
    }

    handleTimeToChange = (event) => {
        
        if(event.target.value < 2){
            this.setState({timeTo:2});
        }
       else  if(event.target.value > 24){
        this.setState({timeTo:24});
        }
            
        if(event.target.value <= this.state.timeFrom){
            this.setState({
                timeTo:event.target.value,
                timeFrom: event.target.value--
            });
        }else{
            this.setState({timeTo:event.target.value});
        }
    }


    render() {

        return (

            <Modal show={this.props.show} centered size={"lg"}>
                
        
                <Modal.Header >
                    <Modal.Title style={{ marginLeft: '36%' }} >
                        Register Pharmacist
                    </Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <div className="container"  hidden={!this.state.showWorkTime}>
                        <div className="container" style={{ marginTop: "2%" }}>




                        



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
                                                <label>Surname:</label>
                                                <input
                                                    placeholder="Surname"
                                                    class="form-control"
                                                    type="text"
                                                    id="surname"
                                                    onChange={this.handleSurnameChange}
                                                    value={this.state.surname}
                                                />
                                            </div>
                                            <div className="text-danger" style={{ display: this.state.surnameError }}>
                                                Surname must be entered.
                                            </div>
                                        </div>

                                        <div className="control-group">
                                            <div className="form-group controls mb-0 pb-2" style={{ color: "#6c757d", opacity: 1 }}>
                                                <label>Address:</label>
                                                <input
                                                    placeholder="Address"
                                                    class="form-control"
                                                    type="text"
                                                    id="address"
                                                    onChange={this.handleAddressChange}
                                                    value={this.state.address}
                                                />
                                            </div>
                                            <div className="text-danger" style={{ display: this.state.addressError }}>
                                                Address must be entered.
                                            </div>
                                        </div>


                                        <div className="control-group">
                                            <div className="form-group controls mb-0 pb-2" style={{ color: "#6c757d", opacity: 1 }}>
                                                <label>Email address:</label>
                                                <input
                                                    placeholder="Email address"
                                                    className="form-control"
                                                    id="email"
                                                    type="text"
                                                    onChange={this.handleEmailChange}
                                                    value={this.state.email}
                                                />
                                            </div>
                                            <div className="text-danger" style={{ display: this.state.emailError }}>
                                                Email address must be entered.
                                            </div>
                                            <div className="text-danger" style={{ display: this.state.emailNotValid }}>
                                                Email address is not valid.
                                            </div>
                                        </div>

                                        <div className="control-group">
                                            <div className="form-group controls mb-0 pb-2" style={{ color: "#6c757d", opacity: 1 }}>
                                                <label>Phone number:</label>
                                                <input
                                                    placeholder="Phone number"
                                                    class="form-control"
                                                    id="phone"
                                                    type="text"
                                                    onChange={this.handlePhoneNumberChange}
                                                    value={this.state.phoneNumber}
                                                />
                                            </div>
                                            <div className="text-danger" style={{ display: this.state.phoneError }}>
                                                Phone number must be entered.
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
                                                onClick={this.handleShowWorkTime}
                                                className="btn btn-primary btn-xl"
                                                id="sendMessageButton"
                                                type="button"
                                            >
                                                Next
                                            </button>
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>
                       </div> 

                       <div hidden={this.state.showWorkTime}>
                                <form >
                                <h5 className=" text-center  mb-0 text-uppercase" style={{ marginTop: "2rem" }}>
                                Add Work Time
                            </h5>
                                    <div  className="control-group" >
                                        <div className="form-row">
                                            <button  onClick = {() => this.handleBack()} className="btn btn-link btn-xl" type="button">
                                              
                                                Back
                                            </button>                   
                                        </div>
                                        <table style={{width:'100%'},{marginLeft:'25%'}}>
                                            <tr>
                                                <td>
                                                    <label >Date from:</label>
                                                </td>
                                                <td>
                                                    <DatePicker className="form-control"  style={{width: "14em"}} minDate={new Date()} onChange={date => this.handleStartDateChange(date)} selected={this.state.selectedStartDate}/>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>
                                                    <label>Date to:</label>
                                                </td>
                                                <td>
                                                    <DatePicker  className="form-control" style={{width: "14em"}}  minDate={this.state.selectedStartDate} onChange={date => this.handleEndDateChange(date)} selected={this.state.selectedEndDate}/>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>
                                                    <label>Time from:</label>
                                                </td>
                                                <td>
                                                    <input placeholder="Time from" className="form-control ml-2" style={{width: "12em"}} type="number" min="1" max="23" onChange={this.handleTimeFromChange} value={this.state.timeFrom} />
                                                </td>
                                            </tr>

                                            <tr>
                                                <td>
                                                    <label>Time to:</label>
                                                </td>
                                                <td>
                                                    <input placeholder="Time to" className="form-control ml-2 " style={{width: "12em"}} type="number" min="2" max="24" onChange={this.handleTimeToChange} value={this.state.timeTo} />
                                                </td>
                                            </tr>
                                        </table>
                                        <div  className="form-group text-center">
                                            <Button className="mt-3"  onClick = {() => this.handleSignUp()} >Sign Up</Button>
                                        </div>
                                    </div>
                                </form>
                    </div>
			
                </Modal.Body>
                    <Modal.Footer>
                        <Button onClick={() => this.handleClickOnClose()}>Close</Button>
                    </Modal.Footer>
            </Modal>
         );
    }
}
export default AddPharmacistModal;