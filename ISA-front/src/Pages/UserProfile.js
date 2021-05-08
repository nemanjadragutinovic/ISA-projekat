import React, { Component } from "react";
import Axios from "axios";
import Header from '../Components/Header';
import GetAuthorisation from "../Funciton/GetAuthorisation";
import PharmacyLogoPicture from "../Images/pharmacyLogo.jpg" ;
const API_URL="http://localhost:8080";


class UserProfile extends Component {

    state = {
		id: "",
		email: "",
        name: "",
		surname: "",
		password: "",
		address: "",
		phoneNumber: "",
        redirect: false
		
	};

	constructor(props) {
		super(props);
	}

    hasRole = (requestRole) => {
        let currentRoles = JSON.parse(localStorage.getItem("keyRole"));
  
        if (currentRoles === null) return false;
  
  
        for (let currentRole of currentRoles) {
          if (currentRole === requestRole) return true;
        }
        return false;
      };

	
    handleEmailChange = (event) => {
		this.setState({ email: event.target.value });
	};

	handleNameChange = (event) => {
		this.setState({ name: event.target.value });
	};

	handleSurnameChange = (event) => {
		this.setState({ surname: event.target.value });
	};

	handlePhoneNumberChange = (event) => {
		this.setState({ phoneNumber: event.target.value });
	};



	componentDidMount() {
		if (!this.hasRole("ROLE_PATIENT")) {
			this.setState({ redirect: true });
			this.props.history.push('/login')
		
        } else {


			console.log(GetAuthorisation());
			console.log(localStorage.getItem("keyRole"));
			

			Axios.get(API_URL + "/users/patient", { validateStatus: () => true, headers: { Authorization : GetAuthorisation()} })
				.then((res) => {
					console.log(res.data);
					if (res.status === 401) {                       
                        this.setState({ redirect: true });				
					} else {

						console.log(res.data.EntityDTO.email)
						console.log(res.data.EntityDTO.name)

                        this.setState({
							id: res.data.Id,
							email: res.data.EntityDTO.email,
							name: res.data.EntityDTO.name,
							surname: res.data.EntityDTO.surname,
							address: res.data.EntityDTO.address,
							phoneNumber: res.data.EntityDTO.phoneNumber,
							
						});
		
					}
				})
				.catch((err) => {
					console.log(err);
					console.log("ovaj eror je u pitanju");

				});
		}
	}

	

	render() {

        
		return(

            <React.Fragment>

            <Header/>  

            <div id="userProfilePage">

            <h4 className=" text-center  mb-0 text-uppercase" style={{ marginTop: "2rem" }}>
						User profile
			</h4>

            <div className="row mt-5">

					<div className="col ">
                        <h5 className=" text-center text-uppercase">Personal Information</h5>
                        
                    </div>




            </div>


            

            </div>


            </React.Fragment>


        );
        
	}


}

export default UserProfile;