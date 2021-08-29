import React from "react";
import './card-list.styles.css';
import Card from './Card'

class CardList extends React.Component {
	
    constructor(props){
        super(props);
    }

    render() {
	
        console.log(this.employee);
        console.log(this.props.pharmacyId)
		return (
      
      <React.Fragment>
                <div className='card-list'>{this.props.dermatologists.map((dermatologist)=>(
            <Card key={dermatologist.id} dermatologist={dermatologist} pharmacyId={this.props.pharmacyId}/>
            ))}
            </div>
	    </React.Fragment>
        

		);
	}
}

export default CardList;