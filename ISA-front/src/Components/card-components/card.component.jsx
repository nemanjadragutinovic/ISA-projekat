import React from 'react';
import './card.styles.css'
export const Card = (props) =>(
    <div className="card-style" onClick>
        <h1>{props.dermatologist.EntityDTO.name} {props.dermatologist.EntityDTO.surname}</h1>
    </div>
    
)