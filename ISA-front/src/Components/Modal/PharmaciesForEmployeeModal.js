import React, { Component } from "react";
import Axios from "axios";
import { Button, Modal } from 'react-bootstrap';
import PharmacyLogoPicture from "../../Images/pharmacyLogo.jpg";
import GetAuthorisation from "../../Funciton/GetAuthorisation";



const API_URL = "http://localhost:8080";

class PharmaciesForEmployee extends Component {

  render() {
    return (

      <Modal show={this.props.show} size="md" dialogClassName="modal" centered>
        <Modal.Header >
          <Modal.Title  >
            {"Pharmacies"}
          </Modal.Title>
        </Modal.Header>

        <Modal.Body>
          <div className="container">
            <table className="table" style={{ width: "100%" }}>
              <tbody>
                {
                  this.props.pharmacies.map((pharmacy) => (

                    <tr key={pharmacy.Id} id={pharmacy.Id} >

                      <td width="100px">

                        <img src={PharmacyLogoPicture} width="70px"></img>

                      </td>

                      <td>

                        <div>
                          <b>Name: </b>{pharmacy.EntityDTO.name}
                        </div>

                        <div>
                          <b>Location: </b> {pharmacy.EntityDTO.address.street}, {" "} {pharmacy.EntityDTO.address.city},{" "}
                          {pharmacy.EntityDTO.address.country}
                        </div>

                        <div>
                          <b>Description: </b>{pharmacy.EntityDTO.description}
                        </div>

                      </td>
                    </tr>
                  ))
                }
              </tbody>
            </table>
          </div>
        </Modal.Body>
        <Modal.Footer>
          <Button onClick={() => this.props.onCloseModal()}>Close</Button>
        </Modal.Footer>
      </Modal>
    );
  }

}

export default PharmaciesForEmployee;