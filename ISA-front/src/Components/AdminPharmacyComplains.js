import React, { Component } from "react";
import Axios from "axios";
import Header from './Header';
import ReportImage from "../Images/report.png" ;
import GetAuthorisation from "../Funciton/GetAuthorisation";
import CreateComplaintModal from "../Components/CreateComplaintModal";



class AdminPharmacyComplains extends Component {



    state = {
        complaints : [],
        date : new Date(),
        text : "",
        staffName : "",
        staffSurname : "",
        profession : "",
        reply : "",
        email : "",
        complaintId : "",
        showComplaintModal : false,
      
    
    
    };



    componentDidMount() {

        Axios.get("http://localhost:8080/complaint/getPharmacyComplaints", {
                validateStatus: () => true,
                headers: { Authorization: GetAuthorisation() },
            })
                .then((res) => {
                    if (res.status === 401) {
              this.props.history.push('/login');
                    } else {
                        this.setState({ complaints: res.data });
                        console.log(res.data);
                    }
                })
                .catch((err) => {
                    console.log(err);
                });
    
    
    
      }

      handleReplyClick = (complaint) => {
		console.log(complaint);
		this.setState({
			
            text : complaint.EntityDTO.text,
            
            
            reply : complaint.EntityDTO.reply,
            email : complaint.EntityDTO.patientEmail,
            complaintId : complaint.Id,
			showComplaintModal: true,
		});
	};

  handleComplaintChange = (event) => {
		this.setState({ reply: event.target.value });
	};
  handleComplaintModalClose = () => {
		this.setState({ showComplaintModal: false });
	};

  handleReplyComaplaint = () => {
		let ComplaintStaffDTO = {
			reply : this.state.reply,
            pharmacyId : this.state.complaintId
		};

		Axios.post("http://localhost:8080/complaint/replyToPharmacyComplaint", ComplaintStaffDTO, { validateStatus: () => true, headers: { Authorization: GetAuthorisation() } })
			.then((resp) => {
				if (resp.status === 500) {
					this.setState({ hiddenFailAlert: false, failHeader: "Internal server error", failMessage: "Server error." });
				} else if (resp.status === 201) {
					

          Axios.get("http://localhost:8080/complaint/getPharmacyComplaints", {
			validateStatus: () => true,
			headers: { Authorization: GetAuthorisation() },
		})
			.then((res) => {
				if (res.status === 401) {
          this.props.history.push('/login');
				} else {
					this.setState({ complaints: res.data });
					console.log(res.data);
				}
			})
			.catch((err) => {
				console.log(err);
			});
					
				}
				this.setState({ showComplaintModal: false });
			})
			.catch((err) => {
				console.log(err);
			});
	};


    render() {
	

		return (
            <React.Fragment>
                <Header/>
                    <h5 className=" text-center  mb-0 text-uppercase" style={{ marginTop: "2rem" }}>
						Pharmacy complaints
				    </h5>

                    <table className="table" style={{ width: "50%", marginTop: "5em", marginLeft: "auto",marginRight: "auto" }}>
						<tbody>
							{this.state.complaints.map((complaint) => (
								<tr id={complaint.Id} key={complaint.Id} >
									<td width="130em">
										<img className="img-fluid" src={ReportImage} width="70em" />
									</td>

                  <td>
                                        
                                        <div>  
                                        <b>Patient's email: </b>{complaint.EntityDTO.patientEmail}
                        
                                        </div>
                                        <div>  
                                        <b>Pharmacy name: </b>{complaint.EntityDTO.name}
                        
                                        </div> 
                                        <div>  
                                        <b>Complaint: </b>{complaint.EntityDTO.text}
                        
                                        </div>  

                                        

                                      </td>

                                      <td className="align-middle">
                                      <div style={{ marginLeft: "55%" }}>
                                        <button
                                          type="button"
                                          onClick={() => this.handleReplyClick(complaint)}
                                          className="btn btn-outline-secondary btn-block"
                                        >
                                          Reply
                                        </button>
                                      </div>
                                      
                                    </td>
									
									
								</tr>
							))}
						</tbody>
					</table>

                    <CreateComplaintModal
					buttonName="Send reply"
					header="Reply to complaint"
					handleComplaintChange={this.handleComplaintChange}
					show={this.state.showComplaintModal}
					onCloseModal={this.handleComplaintModalClose}
					giveFeedback={this.handleReplyComaplaint}
					name={this.state.staffName + " " + this.state.staffSurname}
					forWho="consultant"
					handleClickIcon={this.handleClickIcon}
					complaint={this.state.complaint}
				/>

            </React.Fragment>

    );
}

}
export default AdminPharmacyComplains;