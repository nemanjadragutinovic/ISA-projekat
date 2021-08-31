
import './card-list.styles.css';
import Card from './Card'
import React, { Component } from "react";
import PharmacistCard from './PharmacistCard';

class PharmacistCardList extends Component {
	
    componentDidMount() {
        
    }

    handleUpdatePharmacists =()=>{
        this.props.updatePharmacists()
    }
    render() {
	
        
        console.log(this.props.pharmacyId)
		return (
      
      <React.Fragment>
                <div className='card-list'>{this.props.pharmacists.map((pharmacist)=>(
            <PharmacistCard key={pharmacist.id} pharmacist={pharmacist} pharmacyId={this.props.pharmacyId} updatePharmacists={this.handleUpdatePharmacists}/>
            ))}
            </div>
	    </React.Fragment>
        

		);
	}
}

export default PharmacistCardList;