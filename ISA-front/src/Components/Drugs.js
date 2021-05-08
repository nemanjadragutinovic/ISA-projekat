import React, { Component } from "react";
import Axios from "axios";
import Header from './Header';
import MedicamentPicture from "../Images/medicament.jpg" ;
const API_URL="http://localhost:8080";

class Drugs extends Component {
	
  
    
    state = {
		allDrugs: [],
    drugSearchName: "",
    drugSearchProducerName: "",
    drugSearchFabricCode: "",
    showSearchedForm: false,
    showResetSearced: false,
    inputError : "none"
		


  };


  

  componentDidMount() {
		

		Axios.get(API_URL + "/drug/allDrugs")

			.then((res) => {
				this.setState({ allDrugs: res.data });
			})
			.catch((err) => {
				console.log(err);
			});


  
         
	}


   handleSearchForm = () => {    
   
    if(this.state.inputError=== "initial")
      this.setState({inputError :"none"});
      
   
    this.setState({showSearchedForm : !this.state.showSearchedForm,
                    showResetSearced: false});

                    if(this.state.showSearchedForm===false){

                      this.setState({
    
                        showResetSearced: true,
                        drugSearchName: "",
                        drugSearchProducerName: "",
                        drugSearchFabricCode: "", 
                        inputError : "none"     
                      
                      });

                    }

                    
   }

   handleSearchNameChange = (event) => {
		this.setState({ drugSearchName: event.target.value });
	};

  handleSearchProducerNameChange= (event) => {
		this.setState({ drugSearchProducerName: event.target.value });
	};

  handleSearchFabricCodeChange= (event) => {
		this.setState({ drugSearchFabricCode: event.target.value });
	};

  


   SearchDrugs = () => {


  if (this.state.drugSearchName === "" && this.state.drugSearchProducerName === "" &&  this.state.drugSearchFabricCode=== "" ) {
        this.setState({ inputError : "initial" });
           return false;
  } 

  this.setState({ inputError : "none" });

    const searchDTO = {

      name : this.state.drugSearchName,
      producerName : this.state.drugSearchProducerName,
      fabricCode : "",


     };
          
		Axios.post(API_URL + "/drug/searchDrugs", searchDTO)

		.then((res) => {
      this.setState({
        allDrugs: res.data,
        showResetSearced : true,
        showSearchedForm : false,      
      
      });
     
    })
    .catch((err) => {
      console.log(err);
      
    });

		
	};

  resetSearch = () => {

   
    this.setState({
    
     showResetSearced : false,
     showSearchedForm : false, 
     drugSearchName: "",
     drugSearchProducerName: "",
     drugSearchFabricCode: "", 
     inputError : "none"     
   
   });

          
    Axios.get(API_URL + "/drug/allDrugs")

    .then((res) => {
      this.setState({ allDrugs: res.data });
    })
    .catch((err) => {
      console.log(err);
    });


		
	};



	render() {
	

		return (
      
      <React.Fragment>

      <Header/>
      
      <div id="allDrugs">

            
           
           
            <div style={{ width: "70%", marginTop: "5em", marginLeft: "auto",marginRight: "auto" }} width="100%">

            <div className="inline" >
            <button type="button" class="btn btn-primary btn-lg mr-3" onClick={this.handleSearchForm}>
               
                {this.state.showSearchedForm ? "Close search" : "Open search"}
                                
            </button>

            <button hidden={!this.state.showResetSearced} type="button" class="btn btn-outline-primary btn-lg mr-3" onClick={this.resetSearch}>
               
               Reset search
                               
           </button>

            </div>




            <form hidden={!this.state.showSearchedForm} className="form-inline" width="100%" id="searchForm">
						
              
              <input
								placeholder="Name"
                class="form-control mr-2"
								type="text"
								onChange={this.handleSearchNameChange}
								value={this.state.drugSearchName}
							/>             
              
              <input
								placeholder="Producer name"
                class="form-control mr-2"
								type="text"
								onChange={this.handleSearchProducerNameChange}
								value={this.state.drugSearchProducerName}
							/>
              

              
             
              
							<button								
								onClick={this.SearchDrugs}
								className="btn btn-outline-primary btn-lg "
								type="button"
                
							>								
								Search
							</button>
             

					</form>

          <div className="text-danger" style={{ display: this.state.inputError, fontSize: "17px"}}>
										Enter something in field!
					</div>
          </div>




        <div className="container">
                    <h1 >All drugs</h1>
                    <table className="table" style={{ width: "70%", marginTop: "5em", marginLeft: "auto",marginRight: "auto" }}>
                        
                        <tbody>
                            {
                                this.state.allDrugs.map((drug) => (
                                    <tr key={drug.Id} id={drug.Id} >
                                       

                                      <td width="100px">  
                                       
                                        <img src={MedicamentPicture } width="70px"></img>                                 
                                     
                                      </td>


                                      <td>
                                        
                                        <div>  
                                        <b>Name: </b>{drug.EntityDTO.name}
                                        </div>  

                                        <div>  
                                        <b>Producer name: </b> {drug.EntityDTO.producerName}
                                        </div> 

                                        <div>  
                                        <b>Fabric code: </b>{drug.EntityDTO.fabricCode}
                                        </div>

                                      </td>


                                    </tr>
                                ))
                            }
                        </tbody>
                    </table>
                </div>
        </div>
        </React.Fragment>
        
		);
	}
}

export default Drugs;
