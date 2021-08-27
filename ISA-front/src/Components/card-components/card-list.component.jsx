import React from "react";
import './card-list.styles.css';
import Card from './Card'
export const CardList = props =>(
   
    <div className='card-list'>{props.dermatologists.map((dermatologist)=>(
            <Card key={dermatologist.id} dermatologist={dermatologist} pharmacy={props.pharmacy}/>
            ))}
    </div>
);