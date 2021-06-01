import React, { Component } from "react";
import Axios from "axios";
import Header from './Header';
import QrReader from 'react-qr-reader'
import { withRouter } from "react-router";
import GetAuthorisation from "../Funciton/GetAuthorisation";
import ModalDialog from './ModalDialog';
import { Redirect } from "react-router-dom";





class QrCode extends Component {


    state = {
		pharmacies: [],
		formShowed: false,
		name: "",
		city: "",
		openModal: false,
		openModalRefused: false,
		openModalCantUse: false,
		gradeFrom: "",
		gradeTo: "",
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

        Axios.get("http://localhost:8080/users/patient", { headers: { Authorization: GetAuthorisation() } })
			.then((res) => {
				if(res.data.EntityDTO.penalty > 2){
					this.setState({
						openModal: true ,
						redirectUrl : "/",
        			})
				}
			})
			.catch((err) => {
				console.log(err);
			});
    }


    constructor(props){
        super(props)
        this.state = {
            delay: 100,
            result: 'No result',
        }
        this.handleScan = this.handleScan.bind(this)
    }

    openImageDialog() {
        this.refs.qrReader1.openImageDialog()
    }

    handleModalCloseCantUse= () => {
		this.setState({ 
			openModalCantUse: false,
            redirect: true,
            redirectUrl:'/'
		});
	};
    handleModalClose = () => {
		this.setState({ 
			openModal: false,
			redirect:true, 
            redirectUrl:'/'
		});
	};

    handleScan(data){
	    let RefuseReceiptDTO = {
	    	id: data
	    }
	    
        console.log(RefuseReceiptDTO);

        if(data===null){
            data = "asdasdsa";
        }

        Axios.get("http://localhost:8080/drug/canPatientUseQR", {
            params: {
                id: data
            },
            validateStatus: () => true, headers: { Authorization: GetAuthorisation() }
             })
			.then((res) => {
				if(res.data){

					this.setState({
						result: data,
						redirect:true,
						redirectUrl : "/qrPharmacieswithDrugs/"+data
						})
					

                }else{
					this.setState({
						openModalCantUse: true ,
					})
				}

			})
			.catch((err) => {
				console.log(err);
			});
		
    
	
    }



    render() {
        if (this.state.redirect) return <Redirect push to={this.state.redirectUrl} />;
        const previewStyle = {
            height: 240,
            width: 320,
        }
        return (
            <React.Fragment>
                <Header />

                <div className="container" style={{ marginTop: "2%" }}>
					<h5 className=" text-left mb-0 mt-2 text-uppercase">Enter QR code</h5>
       		
                        <QrReader ref="qrReader1"
                            delay={this.state.delay}
                            style={previewStyle}
                            onError={this.handleError}
                            onScan={this.handleScan}
                            legacyMode={true}
                        />

						
            
            </div>

            <div className="container" style={{ marginTop: "4%" }}>
            <button 
					type="button"
					
					onClick={this.openImageDialog.bind(this)}
					className="btn btn-outline-secondary mt-3 "
				>
					Submit QR Code
				</button>
                </div>
           
                <ModalDialog
				show={this.state.openModalCantUse}
				onCloseModal={this.handleModalCloseCantUse}
				header="Error"
				text="You entered wrong QR code or it has been already used. Please try again"
			/>
            <ModalDialog
				show={this.state.openModal}
				onCloseModal={this.handleModalCloseCantUse}
				header="Error"
				text="You have 3 or more penalty points, you are not eligible to use QR code scanner."
			/>
        </React.Fragment>
    );

    }

}
export default withRouter(QrCode);