import React, { Component } from "react";
import { Button, Modal } from "react-bootstrap";
import Star from "../../Images/rating.png";

class FirstGradeModal extends Component {
	

    state = {
		
		grade: 1,
        minGrade : 1
       
	};


    componentDidMount() {
		
        this.setState({ grade : this.props.employeeGrade,
            minGrade : 1  });
		
         
	}


    handleGradeChange = (event) => {
		
        if (event.target.value > this.props.maxGrade) this.setState({ grade: this.props.maxGrade });
        else if (event.target.value < 1) this.setState({ grade: 1 });
        else this.setState({ grade: event.target.value });
   };
    
    
    render() {
		return (
			<Modal
				show={this.props.show}
				dialogClassName="modal-80w-80h"
				aria-labelledby="contained-modal-title-vcenter"
				centered
				onHide={this.props.onCloseModal}
			>
				<Modal.Header closeButton>
					<Modal.Title id="contained-modal-title-vcenter">
						{this.props.header}
					</Modal.Title>
				</Modal.Header>
				<Modal.Body>
					<h5>{"Grade " + this.props.employeeName + " " + this.props.employeeSurname}</h5>

                            <div className="form-inline ">
                                <div style={{ marginTop: "1em" }} >
									<b>Grade:</b>
									<input
										
										className="form-control "
										style={{ width: "11em" }}
										type="number"
										min={this.state.minGrade}
										max={this.props.maxGrade}
										onChange={this.handleGradeChange}
										value={this.state.grade}
									/>
								</div>                    

                                <img
								
								className="img-fluid  ml-3"
								style={{ width: "11em", marginTop : "5px" }}
								src={Star}
								width="50em"
                                
								
							    />
                         </div>
                                <div class="container bg-light">
                                    <div class="col-md-12 text-center">
                                        <button hidden={!this.props.showFirstGrade} 
                                        onClick={() => this.props.setFirstGrade(this.state.grade)}
                                        type="button" class="btn btn-primary"
                                        >{this.props.buttonName}</button>
                                        
                                    </div>
                                </div>

                                <div class="container bg-light" style={{marginTop :"3em"}}>
                                    <div class="col-md-12 text-center">
                                        <button hidden={!this.props.showModifyGrade}
                                        onClick={() => this.props.setModifyGrade(this.state.grade)}
                                        type="button" class="btn btn-primary"
                                        >{this.props.buttonModifyGradeName}</button>
                                        
                                    </div>
                                </div>
				
                    
				</Modal.Body>
				<Modal.Footer>
					<Button onClick={this.props.onCloseModal}>Close</Button>
				</Modal.Footer>
			</Modal>
		);
	}
}

export default FirstGradeModal;
