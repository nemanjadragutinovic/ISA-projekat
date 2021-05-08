import React from 'react';
import * as ReactBootStrap from "react-bootstrap";
import { Link } from "react-router-dom";

class TopBar extends React.Component{

    handleLogout = () => {
        localStorage.removeItem("keyToken");
        localStorage.removeItem("keyRole");
        localStorage.removeItem("expireTime");
      };
  
      
  
      IsLogedIn = () => {
        let currentRoles = JSON.parse(localStorage.getItem("keyRole"));
  
        if (currentRoles === null) return false;
  
        
        return true;
      };
    

    render(){
        const myStyle = {
			margin: 10,
		};
        return(
            

          <div id="topbar" className="ml-auto" >
				
					

					<div className=" ml-auto container register-login">
						<Link to="/registration" hidden={this.IsLogedIn()}>
							Register
						</Link>
            <i ></i>{" "}
						<Link to="/login" hidden={this.IsLogedIn()}>
							Login
						</Link>
            <i className="icofont-envelope"></i>{" "}
						<Link onClick={this.handleLogout} to="/login" hidden={!this.IsLogedIn()}>
							Logout
						</Link>
						
					</div>
				
			</div>



        )
    }


}

export default TopBar;