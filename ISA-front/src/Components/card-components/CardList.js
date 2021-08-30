
import './card-list.styles.css';
import Card from './Card'
import React, { Component } from "react";

class CardList extends Component {
	
    componentDidMount() {
        
    }

    handleUpdateDermatologists =()=>{
        this.props.updateDermatologists()
    }
    render() {
	
        console.log(this.employee);
        console.log(this.props.pharmacyId)
		return (
      
      <React.Fragment>
                <div className='card-list'>{this.props.dermatologists.map((dermatologist)=>(
            <Card key={dermatologist.id} dermatologist={dermatologist} pharmacyId={this.props.pharmacyId} updateDermatologists={this.handleUpdateDermatologists}/>
            ))}
            </div>
	    </React.Fragment>
        

		);
	}
}

export default CardList;