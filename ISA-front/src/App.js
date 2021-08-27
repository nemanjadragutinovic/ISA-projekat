import logo from './logo.svg';
import './App.css';
import HomePage from "./Components/HomePage"
import Drugs from "./Components/Drugs"
import Pharmacies from "./Components/Pharmacies"
import RegisterPage from "./Components/RegistrationPage"
import LoginPage from "./Components/LoginPage"
import UserProfilePage from "./Pages/UserProfile"
import registerStaff from "./Components/adminRegistration/registerStaff"
import RegisterDrug from './Components/adminRegistration/RegisterDrug';

import LoyalityProgram from "./Components/LoyalityProgram/LoyalityProgram"
import RegisterPharmacies from './Components/adminRegistration/RegisterPharmacies';
import OrdersPage from './Components/SupplierPages/OrdersPage';

import HomePageForDermatologistAppointments from "./Pages/HomePageForDermatologistAppointments"
import DermatologistFreeAppointment from "./Pages/DermatologistFreeAppointment"
import FutureDermatologistAppointmentsForPatient from "./Pages/FutureDermatologistAppointmentsForPatient"
import HistoryDermatologistAppointmentsForPatient from "./Pages/HistoryDermatologistAppointmentsForPatient"
import PharmaciesAppointmentStartPage from "./Pages/PharmaciesAppointmentStartPage"
import FuturePharmaciesConsultationsForPatient from "./Pages/FuturePharmaciesConsultationsForPatient"
import OfferPage from './Components/SupplierPages/OfferPage';
import PatientsSubscribedPharmacies from './Components/Pharmacies/PatientsSubscribedPharmacies'
import AdminStaffComplains from './Components/AdminStaffComplains';
import AdminPharmacyComplains from './Components/AdminPharmacyComplains';
import QrCode from './Components/QrCode';
import FutureDrugsReservationsForPatients from "./Pages/FutureDrugsReservationsForPatient"
import qrPharmacieswithDrugs from './Components/qrPharmacieswithDrugs';
import PatientsEReceipts from "./Pages/PatientsEReceipts"
import PatientsProccessedDrugsEReceipts from "./Pages/PatientsProccessedDrugsEReceipts"
import activateAccount from './Components/activateAccount';
import PatientsPage from './Pages/Appointment/PatientsPage';


import {
  BrowserRouter as Router,
  Route,
  Link,
  Switch
} from "react-router-dom";



function App() {
  return (
    <div className="App">
    
    
    <Router>
      
        <Switch>
        <Route exact to ="/"  path ="/" component={HomePage}/>
        <Route  to ="/drugs" path ="/drugs"  component={Drugs}/>
        <Route  to ="/pharmacies" path ="/pharmacies"  component={Pharmacies}/>       
        <Route  to ="/registration" path ="/registration"  component={RegisterPage}/>
        <Route  to ="/login" path ="/login"  component={LoginPage}/>
        <Route  to ="/userProfile" path ="/userProfile"  component={UserProfilePage}/>
        
        <Route  to ="/registerStaff" path ="/registerStaff"  component={registerStaff}/>
        <Route  to ="/registerPharmacies" path ="/registerPharmacies"  component={RegisterPharmacies}/>
        <Route  to ="/dermatologistAppointment" path ="/dermatologistAppointment"  component={HomePageForDermatologistAppointments}/>
        <Route path="/dermatologistFreeAppointment/:id" children={<DermatologistFreeAppointment />} />
        <Route  to ="/futureDermatologistAppointmentsForPatient" path ="/futureDermatologistAppointmentsForPatient"  component={FutureDermatologistAppointmentsForPatient}/>
        <Route  to ="/historyDermatologistAppointmentsForPatient" path ="/historyDermatologistAppointmentsForPatient"  component={HistoryDermatologistAppointmentsForPatient}/>
        <Route  to ="/registerDrug" path ="/registerDrug"  component={RegisterDrug}/>
        
        

        <Route  to ="/pharmacistAppointment" path ="/pharmacistAppointment"  component={PharmaciesAppointmentStartPage}/>
        <Route  to ="/loyalityProgram" path ="/loyalityProgram"  component={LoyalityProgram}/>
        <Route  to ="/orders" path ="/orders"  component={OrdersPage}/>
        <Route  to ="/futurePharmaciesConsultationsForPatient" path ="/futurePharmaciesConsultationsForPatient"  component={FuturePharmaciesConsultationsForPatient}/>
        <Route  to ="/offers" path ="/offers"  component={OfferPage}/>
        <Route  to ="/patientsSubscribedPharmacies" path ="/patientsSubscribedPharmacies"  component={PatientsSubscribedPharmacies}/>
        <Route  to ="/staffComplains" path ="/staffComplains"  component={AdminStaffComplains}/>
        <Route  to ="/pharmacyComplains" path ="/pharmacyComplains"  component={AdminPharmacyComplains}/>
        <Route  to ="/qrCode" path ="/qrCode"  component={QrCode}/>
        <Route  to ="/futureDrugsReservationForPatient" path ="/futureDrugsReservationForPatient"  component={FutureDrugsReservationsForPatients}/>     
        <Route to="/qrPharmacieswithDrugs/:id" path ="/qrPharmacieswithDrugs/:id"  component={qrPharmacieswithDrugs}/>
        <Route  to ="/allPatients-E-receipts" path ="/allPatients-E-receipts"  component={PatientsEReceipts}/>
        <Route  to ="/allPatients-processed-drugs-e-receipts" path ="/allPatients-processed-drugs-e-receipts"  component={PatientsProccessedDrugsEReceipts}/>

        <Route to="/activeAccount/:id" path ="/activeAccount/:id"  component={activateAccount}/>
        <Route to="/patients" path ="/patients"  component={PatientsPage}/>



        </Switch>
    </Router>
     
    </div>
  );
}

export default App;
