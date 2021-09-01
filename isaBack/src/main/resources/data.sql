--DRUGS
insert into Drug (id, name,producer_Name,fabric_Code) values ('dac2b818-5838-11eb-ae93-0242ac130002','Ibuprofen', 'Hemofarm', '1162531');
insert into Drug (id, name,producer_Name,fabric_Code) values ('2c797174-5839-11eb-ae93-0242ac130002','Analgin', 'Hemofarm', '1162513');
insert into Drug (id, name,producer_Name,fabric_Code) values ('2fe1cd8e-5839-11eb-ae93-0242ac130002','Codaron', 'Galenika', '3162089');
insert into Drug (id, name,producer_Name,fabric_Code) values ('0db9e1a0-3760-4835-8785-6dcc9c199ee0','Kloksan', 'Galenika', '11221122');
insert into Drug (id, name,producer_Name,fabric_Code) values ('4d62cbe3-69a1-40a0-8e9c-9479132dadbe','Herbalife sekta', 'Galenika', '4456621');


--AUTHORITY
insert into authority (id, name) values ('7852aa5e-7040-4d99-8255-537a0b226c75','ROLE_PATIENT');
insert into authority (id, name) values ('563e9925-cff6-42b7-99fa-6b1235f67655','ROLE_SYSADMIN');
insert into authority (id, name) values ('09af8857-2f78-4eec-970f-059d3ed4589f','ROLE_DERMATHOLOGIST');
insert into authority (id, name) values ('ef9a3723-a72e-44ec-83ac-9d748fd0240f','ROLE_SUPPLIER');
insert into authority (id, name) values ('a1e3bac1-6093-4705-b835-eed75c3e5f21','ROLE_PHARMACIST');
insert into authority (id, name) values ('ea16767c-2c1f-49fb-ac98-c7739c0036e8','ROLE_PHARMACYADMIN');

--PHARMACY nemanjaaaa 

insert into pharmacy (id,name, city, street,country, post_code,latitude,longitude,description,consultation_price) values ('775d8e36-9859-11eb-a8b3-0242ac130003','apoteka Jankovic','Novi Sad','Futoski put 54','Serbia', '21000',45.24555195890135, 19.797846045982986, 'Zdravlje je prioritet!',1250);            
insert into pharmacy (id,name, city, street,country, post_code,latitude,longitude,description,consultation_price) values ('775d9322-9859-11eb-a8b3-0242ac130003','Benu','Beograd','Vojvodjanske brigade 30','Serbia', '21000',45.24945171512143, 19.843664046981377, 'U svemu najbolji!',1400);  


--PHADMIN

insert into users (id, email, password, name, surname, phone_Number, first_Login, active, address, user_type) values ('62ee0102-ba54-11eb-8529-0242ac130003','phadmin@gmail.com', '$2a$10$TyNl6ipLWyDE/TfFM3uRse0SVPP4Rz7.mdZdDK3zqjKZqtKtJ3pf2','Petar','Petrovic','062850007',false,true,'Temerinska 110', 'PHARMACYADMIN');

--USERS

--patients-users
insert into users (id, email, password, name, surname, phone_Number,first_Login, active, address,user_type) values ('22793162-52d3-11eb-ae93-0242ac130002','patient1@gmail.com', '$2a$10$TyNl6ipLWyDE/TfFM3uRse0SVPP4Rz7.mdZdDK3zqjKZqtKtJ3pf2','Njeke','Zeke','0623333',false,true,'Kisacka 22','PATIENT');
insert into users (id, email, password, name, surname, phone_Number,first_Login, active, address,user_type) values ('32793162-52d3-11eb-ae93-0242ac130002','patient2@gmail.com', '$2a$10$TyNl6ipLWyDE/TfFM3uRse0SVPP4Rz7.mdZdDK3zqjKZqtKtJ3pf2','Aljosa','Karamazov','0623153',false,true,'Veternicka rampa 12','PATIENT');
insert into users (id, email, password, name, surname, phone_Number,first_Login, active, address,user_type) values ('88dc12eb-6f5a-4f1e-8058-b38f84293574','patient3@gmail.com', '$2a$10$TyNl6ipLWyDE/TfFM3uRse0SVPP4Rz7.mdZdDK3zqjKZqtKtJ3pf2','Ana','Ivanovna','06232223',false,true,'Kosovskih junaka 1389','PATIENT');

--sysadmins-users
insert into users (id, email, password, name, surname, phone_Number,first_Login, active, address,user_type) values ('44444d47-1a8a-4ae1-b109-af7b56e94788','sysadmin@gmail.com','$2a$10$TyNl6ipLWyDE/TfFM3uRse0SVPP4Rz7.mdZdDK3zqjKZqtKtJ3pf2','Zec','Njekezovic','0612345',false,true,'Kosovska 22','SYSADMIN');

--dermatologist-users
insert into users (id, email, password, name, surname, phone_Number,first_Login, active, address,user_type) values ('07a2c302-b584-11eb-8529-0242ac130003','dermatolog1@gmail.com', '$2a$10$TyNl6ipLWyDE/TfFM3uRse0SVPP4Rz7.mdZdDK3zqjKZqtKtJ3pf2','Marija','Jovanovic','064555787',false,true,'Partizanska 11','DERMATOLOGIST');                                
insert into users (id, email, password, name, surname, phone_Number,first_Login, active, address,user_type) values ('aef9fa80-b584-11eb-8529-0242ac130003','dermatolog2@gmail.com', '$2a$10$TyNl6ipLWyDE/TfFM3uRse0SVPP4Rz7.mdZdDK3zqjKZqtKtJ3pf2','Milica','Peric','06388929',false,true,'Nikolajevska 12','DERMATOLOGIST'); 
insert into users (id, email, password, name, surname, phone_Number,first_Login, active, address,user_type) values ('b4db0d1e-0826-11ec-9a03-0242ac130003','dermatolog3@gmail.com', '$2a$10$TyNl6ipLWyDE/TfFM3uRse0SVPP4Rz7.mdZdDK3zqjKZqtKtJ3pf2','Petar','Jovanovic','064555787',false,true,'Partizanska 11','DERMATOLOGIST');                                
insert into users (id, email, password, name, surname, phone_Number,first_Login, active, address,user_type) values ('c417fca6-0826-11ec-9a03-0242ac130003','dermatolog4@gmail.com', '$2a$10$TyNl6ipLWyDE/TfFM3uRse0SVPP4Rz7.mdZdDK3zqjKZqtKtJ3pf2','Milan','Peric','06388929',false,true,'Nikolajevska 12','DERMATOLOGIST'); 

--pharmacist-users
insert into users (id, email, password, name, surname, phone_Number,first_Login, active, address,user_type) values ('80c86094-ba60-11eb-8529-0242ac130003','pharmacist1@gmail.com', '$2a$10$TyNl6ipLWyDE/TfFM3uRse0SVPP4Rz7.mdZdDK3zqjKZqtKtJ3pf2','Nikola','Jovic','064555787',false,true,'Skolska 12','PHARMACIST');                                
insert into users (id, email, password, name, surname, phone_Number,first_Login, active, address,user_type) values ('80c862c4-ba60-11eb-8529-0242ac130003','pharmacist2@gmail.com', '$2a$10$TyNl6ipLWyDE/TfFM3uRse0SVPP4Rz7.mdZdDK3zqjKZqtKtJ3pf2','Marko','Skepic','06388929',false,true,'Novosadskog Sajma 13','PHARMACIST');
insert into users (id, email, password, name, surname, phone_Number,first_Login, active, address,user_type) values ('56a92e1c-bd9c-11eb-8529-0242ac130003','pharmacist3@gmail.com', '$2a$10$TyNl6ipLWyDE/TfFM3uRse0SVPP4Rz7.mdZdDK3zqjKZqtKtJ3pf2','Jelena','Jovanovic','0649879878',false,true,'Toplice Milana 12','PHARMACIST');


--pharmacyAdmin-users
insert into users (id, email, password, name, surname, phone_Number,first_Login, active, address,user_type) values ('17a2c302-b584-11eb-8529-0242ac130003','pharmacyadmin@gmail.com', '$2a$10$TyNl6ipLWyDE/TfFM3uRse0SVPP4Rz7.mdZdDK3zqjKZqtKtJ3pf2','Marija','Jovanovic','064555787',false ,true,'Partizanska 11','PHARMACYADMIN');

--supplier-users
insert into users (id, email, password, name, surname, phone_Number,first_Login, active, address,user_type) values ('54444d47-1a8a-4ae1-b109-af7b56e94788','supp1@gmail.com', '$2a$10$TyNl6ipLWyDE/TfFM3uRse0SVPP4Rz7.mdZdDK3zqjKZqtKtJ3pf2','Njekez','Pekez','064555787',false ,true,'Partizanska 221','SUPPLIER');
insert into users (id, email, password, name, surname, phone_Number,first_Login, active, address,user_type) values ('64444d47-1a8a-4ae1-b109-af7b56e94788','supp2@gmail.com', '$2a$10$TyNl6ipLWyDE/TfFM3uRse0SVPP4Rz7.mdZdDK3zqjKZqtKtJ3pf2','Nje','Kee','064555787',false ,true,'Partizanska 1','SUPPLIER');       


--PATIENTS
insert into patient (id, points,penalty,refresh_penalties) values ('22793162-52d3-11eb-ae93-0242ac130002',12,0,false);
insert into patient (id, points,penalty,refresh_penalties) values ('32793162-52d3-11eb-ae93-0242ac130002',0,3,false);
insert into patient (id, points,penalty,refresh_penalties) values ('88dc12eb-6f5a-4f1e-8058-b38f84293574',25,1,false);

--PHARMACISTS
insert into pharmacist (id, pharmacy_id) values ('80c86094-ba60-11eb-8529-0242ac130003','775d8e36-9859-11eb-a8b3-0242ac130003');
insert into pharmacist (id, pharmacy_id) values ('80c862c4-ba60-11eb-8529-0242ac130003','775d8e36-9859-11eb-a8b3-0242ac130003'); 
insert into pharmacist (id, pharmacy_id) values ('56a92e1c-bd9c-11eb-8529-0242ac130003','775d9322-9859-11eb-a8b3-0242ac130003'); 


--SYSTEM ADMIN
insert into system_admin(id) values ('44444d47-1a8a-4ae1-b109-af7b56e94788');


insert into pharmacy_admin(id,pharmacy_id) values ('62ee0102-ba54-11eb-8529-0242ac130003','775d8e36-9859-11eb-a8b3-0242ac130003');

--USER-AUTHORITY

insert into user_authority (user_id, authority_id) values ('22793162-52d3-11eb-ae93-0242ac130002', '7852aa5e-7040-4d99-8255-537a0b226c75');
insert into user_authority (user_id, authority_id) values ('32793162-52d3-11eb-ae93-0242ac130002', '7852aa5e-7040-4d99-8255-537a0b226c75');
insert into user_authority (user_id, authority_id) values ('88dc12eb-6f5a-4f1e-8058-b38f84293574', '7852aa5e-7040-4d99-8255-537a0b226c75');
insert into user_authority (user_id, authority_id) values ('44444d47-1a8a-4ae1-b109-af7b56e94788', '563e9925-cff6-42b7-99fa-6b1235f67655');
insert into user_authority (user_id, authority_id) values ('17a2c302-b584-11eb-8529-0242ac130003', 'ea16767c-2c1f-49fb-ac98-c7739c0036e8');
insert into user_authority (user_id, authority_id) values ('54444d47-1a8a-4ae1-b109-af7b56e94788', 'ef9a3723-a72e-44ec-83ac-9d748fd0240f');
insert into user_authority (user_id, authority_id) values ('64444d47-1a8a-4ae1-b109-af7b56e94788', 'ef9a3723-a72e-44ec-83ac-9d748fd0240f');
insert into user_authority (user_id, authority_id) values ('80c86094-ba60-11eb-8529-0242ac130003', 'a1e3bac1-6093-4705-b835-eed75c3e5f21');
insert into user_authority (user_id, authority_id) values ('56a92e1c-bd9c-11eb-8529-0242ac130003', 'a1e3bac1-6093-4705-b835-eed75c3e5f21');

--SUPLIER
insert into supplier(id) values ('54444d47-1a8a-4ae1-b109-af7b56e94788');
insert into supplier(id) values ('64444d47-1a8a-4ae1-b109-af7b56e94788');

--DERMATOLOGIST
insert into dermatologist (id) values ('07a2c302-b584-11eb-8529-0242ac130003');
insert into dermatologist (id) values ('aef9fa80-b584-11eb-8529-0242ac130003');
insert into dermatologist (id) values ('b4db0d1e-0826-11ec-9a03-0242ac130003');
insert into dermatologist (id) values ('c417fca6-0826-11ec-9a03-0242ac130003');
--PHARMACY ADMIN
insert into pharmacy_admin(id, pharmacy_id) values ('17a2c302-b584-11eb-8529-0242ac130003', '775d8e36-9859-11eb-a8b3-0242ac130003');


--USERS-AUTHORITY

--rolePatients
insert into user_authority (user_id, authority_id) values ('22793162-52d3-11eb-ae93-0242ac130002', '7852aa5e-7040-4d99-8255-537a0b226c75');

--roleDermatologist
insert into user_authority (user_id, authority_id) values ('07a2c302-b584-11eb-8529-0242ac130003', '09af8857-2f78-4eec-970f-059d3ed4589f');
insert into user_authority (user_id, authority_id) values ('aef9fa80-b584-11eb-8529-0242ac130003', '09af8857-2f78-4eec-970f-059d3ed4589f');


--role-Phadmin

insert into user_authority (user_id, authority_id) values ('62ee0102-ba54-11eb-8529-0242ac130003', 'ea16767c-2c1f-49fb-ac98-c7739c0036e8');


---DERMATOLOGIST-PHARMACIES

insert into dermatologist_pharmacy (dermatologist_id, pharmacy_id) values ('07a2c302-b584-11eb-8529-0242ac130003' ,'775d8e36-9859-11eb-a8b3-0242ac130003');
insert into dermatologist_pharmacy (dermatologist_id, pharmacy_id) values ('07a2c302-b584-11eb-8529-0242ac130003' ,'775d9322-9859-11eb-a8b3-0242ac130003');
insert into dermatologist_pharmacy (dermatologist_id, pharmacy_id) values ('aef9fa80-b584-11eb-8529-0242ac130003' ,'775d8e36-9859-11eb-a8b3-0242ac130003');

--APOINTMENTS

--axaminations-appointments
insert into appointment (id, appointment_status, appointment_type, start_date_time, end_date_time, price,pharmacy_id, employee_id) values ('f183329c-b58c-11eb-8529-0242ac130003','FREE','EXAMINATION', '2021-08-28 11:30:00', '2021-08-28 12:00:00', 1950, '775d8e36-9859-11eb-a8b3-0242ac130003', '07a2c302-b584-11eb-8529-0242ac130003');         
insert into appointment (id, appointment_status, appointment_type, start_date_time, end_date_time, price,pharmacy_id, employee_id) values ('f18331d4-b58c-11eb-8529-0242ac130003','FREE','EXAMINATION', '2021-08-28 12:30:00', '2021-08-28 13:00:00', 1800, '775d8e36-9859-11eb-a8b3-0242ac130003','07a2c302-b584-11eb-8529-0242ac130003');  


insert into appointment (id, appointment_status, appointment_type, start_date_time, end_date_time, price,pharmacy_id, employee_id,patient_id) values ('62934792-be2a-11eb-8529-0242ac130003','SCHEDULED','EXAMINATION', '2021-09-01 18:30:00', '2021-09-01 19:00:00', 1800, '775d8e36-9859-11eb-a8b3-0242ac130003','07a2c302-b584-11eb-8529-0242ac130003','22793162-52d3-11eb-ae93-0242ac130002');  
insert into appointment (id, appointment_status, appointment_type, start_date_time, end_date_time, price,pharmacy_id, employee_id,patient_id) values ('62934846-be2a-11eb-8529-0242ac130003','FINISHED','EXAMINATION', '2021-03-28 11:30:00', '2021-03-28 14:30:00', 1400, '775d8e36-9859-11eb-a8b3-0242ac130003','07a2c302-b584-11eb-8529-0242ac130003','22793162-52d3-11eb-ae93-0242ac130002'); 

--consultations-appointments

insert into appointment (id, appointment_status, appointment_type, start_date_time, end_date_time, price,pharmacy_id, employee_id) values ('09a10e8e-ba61-11eb-8529-0242ac130003','SCHEDULED','CONSULTATION', '2021-09-28 11:30:00', '2021-09-28 12:00:00', 1200, '775d8e36-9859-11eb-a8b3-0242ac130003', '80c86094-ba60-11eb-8529-0242ac130003');         
insert into appointment (id, appointment_status, appointment_type, start_date_time, end_date_time, price,pharmacy_id, employee_id) values ('09a110d2-ba61-11eb-8529-0242ac130003','SCHEDULED','CONSULTATION', '2021-09-28 14:30:00', '2021-09-28 15:30:00', 1300, '775d8e36-9859-11eb-a8b3-0242ac130003','80c86094-ba60-11eb-8529-0242ac130003');
insert into appointment (id, appointment_status, appointment_type, start_date_time, end_date_time, price,pharmacy_id, employee_id) values ('09a111c2-ba61-11eb-8529-0242ac130003','FREE','CONSULTATION', '2021-08-28 11:30:00', '2021-08-28 12:00:00', 1200, '775d8e36-9859-11eb-a8b3-0242ac130003', '80c862c4-ba60-11eb-8529-0242ac130003');       
insert into appointment (id, appointment_status, appointment_type, start_date_time, end_date_time, price,pharmacy_id, employee_id) values ('09a11460-ba61-11eb-8529-0242ac130003','FREE','CONSULTATION', '2021-08-28 14:30:00', '2021-08-28 15:30:00', 1300, '775d8e36-9859-11eb-a8b3-0242ac130003','80c862c4-ba60-11eb-8529-0242ac130003');

insert into appointment (id, appointment_status, appointment_type, start_date_time, end_date_time, price,pharmacy_id, employee_id) values ('56a93182-bd9c-11eb-8529-0242ac130003','FREE','CONSULTATION', '2021-06-28 11:30:00', '2021-06-28 12:00:00', 1200, '775d9322-9859-11eb-a8b3-0242ac130003', '56a92e1c-bd9c-11eb-8529-0242ac130003');       
insert into appointment (id, appointment_status, appointment_type, start_date_time, end_date_time, price,pharmacy_id, employee_id) values ('56a93312-bd9c-11eb-8529-0242ac130003','FREE','CONSULTATION', '2021-06-28 14:30:00', '2021-06-28 15:30:00', 1300, '775d9322-9859-11eb-a8b3-0242ac130003','56a92e1c-bd9c-11eb-8529-0242ac130003');


insert into appointment (id, appointment_status, appointment_type, start_date_time, end_date_time, price,pharmacy_id, employee_id, patient_id) values ('802a3992-be1a-11eb-8529-0242ac130003','FINISHED','CONSULTATION', '2021-04-28 11:30:00', '2021-04-28 12:00:00', 1200, '775d9322-9859-11eb-a8b3-0242ac130003', '56a92e1c-bd9c-11eb-8529-0242ac130003', '22793162-52d3-11eb-ae93-0242ac130002');       
insert into appointment (id, appointment_status, appointment_type, start_date_time, end_date_time, price,pharmacy_id, employee_id, patient_id) values ('802a3be0-be1a-11eb-8529-0242ac130003','SCHEDULED','CONSULTATION', '2021-10-28 14:30:00', '2021-10-28 15:30:00', 1300, '775d9322-9859-11eb-a8b3-0242ac130003','56a92e1c-bd9c-11eb-8529-0242ac130003', '22793162-52d3-11eb-ae93-0242ac130002');

--MANUFACTURER
insert into manufacturer (id, name) values ('20ddef44-5838-11eb-ae93-0242ac130002', 'Hemofarm');
insert into manufacturer (id, name) values ('574c3c20-5838-11eb-ae93-0242ac130002', 'Galenika');
insert into manufacturer (id, name) values ('5c49beb4-5838-11eb-ae93-0242ac130002', 'Ekosan');
insert into manufacturer (id, name) values ('61297672-5838-11eb-ae93-0242ac130002', 'Hemotehna');

--DRUGS-INSTANCE
insert into drug_instance(drug_format, drug_instance_name, loyality_points, on_reciept, quantity, recommended_amount, side_effects, manufacturer_id, id, drug_kind) values('CAPSULE', 'Brufen',1, false, 500, '3x1 na dan', 'Nema nezeljenih dejstava', '20ddef44-5838-11eb-ae93-0242ac130002','2fe1cd8e-5839-11eb-ae93-0242ac130002', 'HUMAN');
insert into drug_instance(drug_format, drug_instance_name, loyality_points, on_reciept, quantity, recommended_amount, side_effects, manufacturer_id, id, drug_kind) values('CAPSULE', 'IBALGIN',1, false, 400, '2x1 na dan', 'Nema nezeljenih dejstava', '574c3c20-5838-11eb-ae93-0242ac130002','2c797174-5839-11eb-ae93-0242ac130002', 'HUMAN');
insert into drug_instance(drug_format, drug_instance_name, loyality_points, on_reciept, quantity, recommended_amount, side_effects, manufacturer_id, id, drug_kind) values('CAPSULE', 'Blokmax',1, false, 500, '2x1 na dan', 'Nema nezeljenih dejstava', '5c49beb4-5838-11eb-ae93-0242ac130002','dac2b818-5838-11eb-ae93-0242ac130002', 'BIOLOGICAL');
insert into drug_instance(drug_format, drug_instance_name, loyality_points, on_reciept, quantity, recommended_amount, side_effects, manufacturer_id, id, drug_kind) values('CAPSULE', 'Kloksan',1, false, 500, '2x1 na dan', 'Nema nezeljenih dejstava', '61297672-5838-11eb-ae93-0242ac130002','0db9e1a0-3760-4835-8785-6dcc9c199ee0', 'HERBAL');
insert into drug_instance(drug_format, drug_instance_name, loyality_points, on_reciept, quantity, recommended_amount, side_effects, manufacturer_id, id, drug_kind) values('CAPSULE', 'Herbalife sekta',1, false, 500, '2x1 na dan', 'Nema nezeljenih dejstava', '61297672-5838-11eb-ae93-0242ac130002','4d62cbe3-69a1-40a0-8e9c-9479132dadbe', 'BLOOD');


--DRUGS-IN-PHARMACY

insert into drug_in_pharmacy(id, pharmacy_id, drug_instance_id, date_from, date_to, price, count) values ('39438ef2-c180-11eb-8529-0242ac130003','775d8e36-9859-11eb-a8b3-0242ac130003','2fe1cd8e-5839-11eb-ae93-0242ac130002','2021-5-11','2021-09-22', 340, 50);
insert into drug_in_pharmacy(id, pharmacy_id, drug_instance_id, date_from, date_to, price, count) values ('3943c304-c180-11eb-8529-0242ac130003','775d9322-9859-11eb-a8b3-0242ac130003','2fe1cd8e-5839-11eb-ae93-0242ac130002','2021-5-11','2021-09-22', 320, 30);
insert into drug_in_pharmacy(id, pharmacy_id, drug_instance_id, date_from, date_to, price, count) values ('3943c4c6-c180-11eb-8529-0242ac130003','775d8e36-9859-11eb-a8b3-0242ac130003','2c797174-5839-11eb-ae93-0242ac130002','2021-5-11','2021-09-22', 180, 38);
insert into drug_in_pharmacy(id, pharmacy_id, drug_instance_id, date_from, date_to, price, count) values ('3943c5a2-c180-11eb-8529-0242ac130003','775d9322-9859-11eb-a8b3-0242ac130003','2c797174-5839-11eb-ae93-0242ac130002','2021-5-11','2021-09-22', 190, 50);
insert into drug_in_pharmacy(id, pharmacy_id, drug_instance_id, date_from, date_to, price, count) values ('4943c4c6-c180-11eb-8529-0242ac130003','775d8e36-9859-11eb-a8b3-0242ac130003','0db9e1a0-3760-4835-8785-6dcc9c199ee0','2021-5-11','2021-09-22', 120, 10);
insert into drug_in_pharmacy(id, pharmacy_id, drug_instance_id, date_from, date_to, price, count) values ('5943c5a2-c180-11eb-8529-0242ac130003','775d9322-9859-11eb-a8b3-0242ac130003','4d62cbe3-69a1-40a0-8e9c-9479132dadbe','2021-5-11','2021-09-22', 550, 20);


--INGREDIENTS
insert into ingredient(id, name) values ('4b852c00-b7bd-11eb-8529-0242ac130003', 'Sastojak1');
insert into ingredient(id, name) values ('4b852fe8-b7bd-11eb-8529-0242ac130003', 'Sastojak2');

--DRUG KIND ID
insert into drug_kind_id (id, type) values ('33345278-52d3-13eb-ae93-0242ac130002','HERBAL');
insert into drug_kind_id (id, type) values ('34345278-52d3-13eb-ae93-0242ac130002','BIOLOGICAL');
insert into drug_kind_id (id, type) values ('35345278-52d3-13eb-ae93-0242ac130002','HOMEOPATIC');
insert into drug_kind_id (id, type) values ('36345278-52d3-13eb-ae93-0242ac130002','HUMAN');
insert into drug_kind_id (id, type) values ('37345278-52d3-13eb-ae93-0242ac130002','BLOOD');
insert into drug_kind_id (id, type) values ('38345278-52d3-13eb-ae93-0242ac130002','RADIOFARMACEUTICAL');
insert into drug_kind_id (id, type) values ('39345278-52d3-13eb-ae93-0242ac130002','RADIONUCLIDE');
insert into drug_kind_id (id, type) values ('40345278-52d3-13eb-ae93-0242ac130002','TRADICIONAL');

--DRUG FORMAT
insert into drug_format_id (id, type) values ('38445278-52d3-13eb-ae93-0242ac130002','VACCINE');
insert into drug_format_id (id, type) values ('39545278-52d3-13eb-ae93-0242ac130002','CAPSULE');
insert into drug_format_id (id, type) values ('40645278-52d3-13eb-ae93-0242ac130002','INJECTION');
insert into drug_format_id (id, type) values ('50645278-52d3-13eb-ae93-0242ac130002','GEL');
insert into drug_format_id (id, type) values ('60645278-52d3-13eb-ae93-0242ac130002','CREME');


--WORKTIMES

--pharmacist-work-times
insert into work_time (id, start_date,end_date, start_time,end_time, employee_id, pharmacy_id) values ('2fb41318-60d4-11eb-ae93-0242ac130002', '2021-06-20','2021-09-20', 8, 17, '07a2c302-b584-11eb-8529-0242ac130003','775d8e36-9859-11eb-a8b3-0242ac130003');
insert into work_time (id, start_date,end_date, start_time,end_time, employee_id, pharmacy_id) values ('9750255c-ba62-11eb-8529-0242ac130003', '2021-06-20','2021-09-20', 8, 17, '80c862c4-ba60-11eb-8529-0242ac130003','775d8e36-9859-11eb-a8b3-0242ac130003'); 
insert into work_time (id, start_date,end_date, start_time,end_time, employee_id, pharmacy_id) values ('56a934e8-bd9c-11eb-8529-0242ac130003', '2021-06-20','2021-09-20', 8, 17, '56a92e1c-bd9c-11eb-8529-0242ac130003','775d9322-9859-11eb-a8b3-0242ac130003'); 

--PHARMACIES-GRADES
insert into pharmacy_grade (id,patient_id, pharmacy_id, grade, date) values ('489fd674-bb0b-11eb-8529-0242ac130003','22793162-52d3-11eb-ae93-0242ac130002', '775d8e36-9859-11eb-a8b3-0242ac130003',4, '2021-05-022');
--insert into pharmacy_grade (id,patient_id, pharmacy_id, grade, date) values ('489fd89a-bb0b-11eb-8529-0242ac130003','22793162-52d3-11eb-ae93-0242ac130002', '775d9322-9859-11eb-a8b3-0242ac130003',2, '2021-05-022');
insert into pharmacy_grade (id,patient_id, pharmacy_id, grade, date) values ('589fd674-bb0b-11eb-8529-0242ac130003','32793162-52d3-11eb-ae93-0242ac130002', '775d8e36-9859-11eb-a8b3-0242ac130003',5, '2021-05-022');
--insert into pharmacy_grade (id,patient_id, pharmacy_id, grade, date) values ('589fd89a-bb0b-11eb-8529-0242ac130003','32793162-52d3-11eb-ae93-0242ac130002', '775d9322-9859-11eb-a8b3-0242ac130003',5, '2021-05-022');



--EMPLOYEES-GRADES

--pharmacist--grades
insert into employee_grade (id,patient_id, employee_id, grade, date) values ('ce880dac-bb46-11eb-8529-0242ac130003','22793162-52d3-11eb-ae93-0242ac130002', '80c86094-ba60-11eb-8529-0242ac130003',4, '2021-05-022');
insert into employee_grade (id,patient_id, employee_id, grade, date) values ('ce880eba-bb46-11eb-8529-0242ac130003','22793162-52d3-11eb-ae93-0242ac130002', '80c862c4-ba60-11eb-8529-0242ac130003',3, '2021-05-022');
--insert into employee_grade (id,patient_id, employee_id, grade, date) values ('56a93678-bd9c-11eb-8529-0242ac130003','22793162-52d3-11eb-ae93-0242ac130002', '56a92e1c-bd9c-11eb-8529-0242ac130003',3, '2021-05-022');

--dermatologists--grades
--insert into employee_grade (id,patient_id, employee_id, grade, date) values ('8d2d65ee-be59-11eb-8529-0242ac130003','22793162-52d3-11eb-ae93-0242ac130002', '07a2c302-b584-11eb-8529-0242ac130003',5, '2021-05-022');
insert into employee_grade (id,patient_id, employee_id, grade, date) values ('8d2d6a12-be59-11eb-8529-0242ac130003','22793162-52d3-11eb-ae93-0242ac130002', 'aef9fa80-b584-11eb-8529-0242ac130003',4, '2021-05-022');


--LOYALITYPROGRAM
insert into loyaltyprogram (id, points_for_appointment, points_for_consulting, points_to_enter_regular_cathegory, points_to_enter_loyal_cathegory, points_to_enter_vip_cathegory, appointment_discount_regular, drug_discount_regular, consultation_discount_regular, appointment_discount_loyal,drug_discount_loyal, consultation_discount_loyal, appointment_discount_vip,  drug_discount_vip, consultation_discount_vip, version) values ('8c834328-9b5a-42c2-9e04-a1acc75f881d', 1, 1, 0, 10, 20, 0, 0, 0, 5, 10, 15, 15, 20, 25, 1);

--ORDERS
insert into orders (id, date, type, pharmacy_id, pharmacy_admin_id) values ('11111111-67ac-11eb-ae93-0242ac130022', '2021-07-07' , 'CREATED' , '775d8e36-9859-11eb-a8b3-0242ac130003' , '17a2c302-b584-11eb-8529-0242ac130003');
insert into orders (id, date, type, pharmacy_id, pharmacy_admin_id) values ('21111111-67ac-11eb-ae93-0242ac130022', '2021-08-07' , 'CREATED' , '775d8e36-9859-11eb-a8b3-0242ac130003' , '17a2c302-b584-11eb-8529-0242ac130003');

--drug--orders
insert into drug_order (id, amount, drug_instance_id) values ('99e26092-67ad-11eb-ae93-0242ac130002', 2, '2fe1cd8e-5839-11eb-ae93-0242ac130002' );
insert into drug_order (id, amount, drug_instance_id) values ('44e26092-67ad-11eb-ae93-0242ac130002', 1, '2c797174-5839-11eb-ae93-0242ac130002' );
insert into drug_order (id, amount, drug_instance_id) values ('55e26092-67ad-11eb-ae93-0242ac130002', 2, 'dac2b818-5838-11eb-ae93-0242ac130002' );

--order-drug-for-order
insert into order_drug_for_order (order_id, drug_order_id) values ('11111111-67ac-11eb-ae93-0242ac130022', '99e26092-67ad-11eb-ae93-0242ac130002');
insert into order_drug_for_order (order_id, drug_order_id) values ('11111111-67ac-11eb-ae93-0242ac130022', '44e26092-67ad-11eb-ae93-0242ac130002');
insert into order_drug_for_order (order_id, drug_order_id) values ('11111111-67ac-11eb-ae93-0242ac130022', '55e26092-67ad-11eb-ae93-0242ac130002');

insert into order_drug_for_order (order_id, drug_order_id) values ('21111111-67ac-11eb-ae93-0242ac130022', '44e26092-67ad-11eb-ae93-0242ac130002');
insert into order_drug_for_order (order_id, drug_order_id) values ('21111111-67ac-11eb-ae93-0242ac130022', '55e26092-67ad-11eb-ae93-0242ac130002');


--suplier drug storage
insert into supplier_drug_storage(drug_instance_id, supplier_id, count, version) values ('dac2b818-5838-11eb-ae93-0242ac130002', '54444d47-1a8a-4ae1-b109-af7b56e94788', 20, 1);
insert into supplier_drug_storage(drug_instance_id, supplier_id, count, version) values ('2c797174-5839-11eb-ae93-0242ac130002', '54444d47-1a8a-4ae1-b109-af7b56e94788', 40, 1);
insert into supplier_drug_storage(drug_instance_id, supplier_id, count, version) values ('2fe1cd8e-5839-11eb-ae93-0242ac130002', '54444d47-1a8a-4ae1-b109-af7b56e94788', 60, 1);

--offers

insert into offers (id, date_to_delivery, status, price, supplier_id) values ('33331111-67ac-11eb-ae93-0242ac130002', '2021-06-06', 'WAITING', 5555, '54444d47-1a8a-4ae1-b109-af7b56e94788' );
insert into offers (id, date_to_delivery, status, price, supplier_id) values ('44441111-67ac-11eb-ae93-0242ac130002', '2021-07-07', 'WAITING', 7520, '64444d47-1a8a-4ae1-b109-af7b56e94788' );
insert into offers (id, date_to_delivery, status, price, supplier_id) values ('55551111-67ac-11eb-ae93-0242ac130002', '2021-08-08', 'WAITING', 6633 ,'54444d47-1a8a-4ae1-b109-af7b56e94788');

insert into offers_for_order (order_id, offers_id) values ('11111111-67ac-11eb-ae93-0242ac130022', '33331111-67ac-11eb-ae93-0242ac130002');
insert into offers_for_order (order_id, offers_id) values ('11111111-67ac-11eb-ae93-0242ac130022', '44441111-67ac-11eb-ae93-0242ac130002');
insert into offers_for_order (order_id, offers_id) values ('11111111-67ac-11eb-ae93-0242ac130022', '55551111-67ac-11eb-ae93-0242ac130002');

insert into patient_pharmacy_subscribe (patient_id, pharmacies_id) values ('22793162-52d3-11eb-ae93-0242ac130002', '775d8e36-9859-11eb-a8b3-0242ac130003');
insert into patient_pharmacy_subscribe (patient_id, pharmacies_id) values ('22793162-52d3-11eb-ae93-0242ac130002', '775d9322-9859-11eb-a8b3-0242ac130003');

insert into drug_feedback (id,date, grade, drug_id, patient_id) values ('38db5e5c-c4a7-11eb-8529-0242ac130003','2020-03-03',4,'2fe1cd8e-5839-11eb-ae93-0242ac130002', '22793162-52d3-11eb-ae93-0242ac130002');
insert into drug_feedback (id,date, grade, drug_id, patient_id) values ('38db6320-c4a7-11eb-8529-0242ac130003','2020-05-03',5,'2c797174-5839-11eb-ae93-0242ac130002', '22793162-52d3-11eb-ae93-0242ac130002');

insert into complaint_staff(id, date, email, profession, reply, staff_name, staff_surname, text, version, patient_id, user_id, active) values ('1dc48297-aa8c-473f-9194-cfd4b8f3bb6e','2020-03-03','abc@gmail.com','dermatolog','nista','Njekez','User','jako lose radite svooj posao',1,'22793162-52d3-11eb-ae93-0242ac130002','07a2c302-b584-11eb-8529-0242ac130003', true);
insert into complaint_staff(id, date, email, profession, reply, staff_name, staff_surname, text, version, patient_id, user_id, active) values ('2dc48297-aa8c-473f-9194-cfd4b8f3bb6e','2020-03-03','abc@gmail.com','dermatolog','nista','Njekez','User','jako lose radite svooj posao',1,'22793162-52d3-11eb-ae93-0242ac130002','07a2c302-b584-11eb-8529-0242ac130003', true);

insert into complaint_pharmacy(id, date, reply, text, version, patient_id, pharmacy_id, active) values ('5a501523-7ddf-4fff-9434-ce4df4c85d3b', '2020-03-03', 'nista', 'najgora apoteka svih vremena dno dna', 1, '22793162-52d3-11eb-ae93-0242ac130002', '775d8e36-9859-11eb-a8b3-0242ac130003', true);

insert into ereceipt (id, price, creation_date, status, patient_id, version) values ('1d4ac49a-67ad-11eb-ae93-0242ac130002', -1, '2021-06-12', 'NEW', '22793162-52d3-11eb-ae93-0242ac130002',1);
insert into ereceipt (id, price, creation_date, status, patient_id, version) values ('bac4bc07-4d4e-4123-8ed7-00e50adca2a2', -1, '2021-05-18', 'REJECTED', '88dc12eb-6f5a-4f1e-8058-b38f84293574',1);
insert into ereceipt (id, price, creation_date, status, patient_id, version) values ('65c38e7d-438b-4284-8e2b-ad7fea10d856', -1, '2021-04-20', 'REJECTED', '32793162-52d3-11eb-ae93-0242ac130002',1);
insert into ereceipt (id, price, creation_date, status, patient_id, version) values ('3b5c5c32-9e7e-43f9-a357-7511472c75a1', -1, '2021-07-05', 'REJECTED', '22793162-52d3-11eb-ae93-0242ac130002',1);
insert into ereceipt (id, price, creation_date, status, patient_id, version,pharmacy_id) values ('19ba9848-2818-492c-9f65-2436c4d6c27c', 280, '2021-08-01', 'PROCESSED', '88dc12eb-6f5a-4f1e-8058-b38f84293574',1,'775d8e36-9859-11eb-a8b3-0242ac130003');
insert into ereceipt (id, price, creation_date, status, patient_id, version,pharmacy_id) values ('03ff49bd-71eb-45cf-b5bb-8a2b4e9cdc6b', 430, '2021-05-22', 'PROCESSED', '22793162-52d3-11eb-ae93-0242ac130002',1,'775d8e36-9859-11eb-a8b3-0242ac130003');
insert into ereceipt (id, price, creation_date, status, patient_id, version,pharmacy_id) values ('f84b0350-ad00-4fca-9273-fbd0ced2457c', 730, '2021-06-21', 'PROCESSED', '22793162-52d3-11eb-ae93-0242ac130002',1,'775d9322-9859-11eb-a8b3-0242ac130003');
insert into ereceipt (id, price, creation_date, status, patient_id, version) values ('fc7c6dcb-164c-41ea-96bc-faaa8caeef10', -1, '2021-04-11', 'NEW', '88dc12eb-6f5a-4f1e-8058-b38f84293574',1);
insert into ereceipt (id, price, creation_date, status, patient_id, version) values ('fed6f795-7e1f-4e5c-b81b-2c54b859928a', -1, '2021-07-26', 'NEW', '22793162-52d3-11eb-ae93-0242ac130002',1);
insert into ereceipt (id, price, creation_date, status, patient_id, version) values ('71efc25b-4bd7-44f5-ad06-205c10e4c22d', -1, '2021-09-24', 'NEW', '88dc12eb-6f5a-4f1e-8058-b38f84293574',1);

insert into ereceipt_items(e_receipt_id, drug_instance_id, amount) values ('1d4ac49a-67ad-11eb-ae93-0242ac130002','2fe1cd8e-5839-11eb-ae93-0242ac130002',2);
insert into ereceipt_items(e_receipt_id, drug_instance_id, amount) values ('1d4ac49a-67ad-11eb-ae93-0242ac130002','2c797174-5839-11eb-ae93-0242ac130002',2);


insert into ereceipt_items(e_receipt_id, drug_instance_id, amount) values ('fc7c6dcb-164c-41ea-96bc-faaa8caeef10','0db9e1a0-3760-4835-8785-6dcc9c199ee0',2);

insert into ereceipt_items(e_receipt_id, drug_instance_id, amount) values ('fed6f795-7e1f-4e5c-b81b-2c54b859928a','2fe1cd8e-5839-11eb-ae93-0242ac130002',5);
insert into ereceipt_items(e_receipt_id, drug_instance_id, amount) values ('fed6f795-7e1f-4e5c-b81b-2c54b859928a','2c797174-5839-11eb-ae93-0242ac130002',6);
insert into ereceipt_items(e_receipt_id, drug_instance_id, amount) values ('fed6f795-7e1f-4e5c-b81b-2c54b859928a','4d62cbe3-69a1-40a0-8e9c-9479132dadbe',7);

--insert into ereceipt_items(e_receipt_id, drug_instance_id, amount) values ('71efc25b-4bd7-44f5-ad06-205c10e4c22d','2fe1cd8e-5839-11eb-ae93-0242ac130002',10);

insert into ereceipt_items(e_receipt_id, drug_instance_id, amount) values ('03ff49bd-71eb-45cf-b5bb-8a2b4e9cdc6b','2fe1cd8e-5839-11eb-ae93-0242ac130002',2);
insert into ereceipt_items(e_receipt_id, drug_instance_id, amount) values ('f84b0350-ad00-4fca-9273-fbd0ced2457c','dac2b818-5838-11eb-ae93-0242ac130002',4);


--DRUGS-RESERVATIONS

insert into drug_reservation(id, count, price_for_one_drug, end_date,start_date, drug_reservation_status, drug_instance_id, patient_id, pharmacy_id) values ('bc3196de-c2e2-11eb-8529-0242ac130003', 4, 250, '2021-05-02 14:44:03','2021-04-28 14:44:03',  'PROCESSED',  'dac2b818-5838-11eb-ae93-0242ac130002', '22793162-52d3-11eb-ae93-0242ac130002','775d8e36-9859-11eb-a8b3-0242ac130003');
--insert into drug_reservation(id, count, price_for_one_drug, end_date,start_date, drug_reservation_status,  drug_instance_id, patient_id, pharmacy_id) values ('bc3198f0-c2e2-11eb-8529-0242ac130003', 2, 280, '2021-02-18 17:04:03', '2021-01-16 17:04:03',  'PROCESSED', 'dac2b818-5838-11eb-ae93-0242ac130002', '22793162-52d3-11eb-ae93-0242ac130002','775d8e36-9859-11eb-a8b3-0242ac130003');

insert into drug_reservation(id, count, price_for_one_drug, end_date,start_date, drug_reservation_status,  drug_instance_id, patient_id, pharmacy_id) values ('bc3198f0-c2e2-11eb-8529-0242ac130003', 2, 280, '2021-04-18 17:04:03', '2021-04-16 17:04:03',  'PROCESSED', 'dac2b818-5838-11eb-ae93-0242ac130002', '22793162-52d3-11eb-ae93-0242ac130002','775d9322-9859-11eb-a8b3-0242ac130003');
insert into drug_reservation(id, count, price_for_one_drug, end_date,start_date, drug_reservation_status,  drug_instance_id, patient_id, pharmacy_id) values ('71a394d4-c6e6-11eb-b8bc-0242ac130003', 8, 280, '2021-03-18 17:04:03', '2021-03-16 17:04:03',  'ACTIVE', '2fe1cd8e-5839-11eb-ae93-0242ac130002', '22793162-52d3-11eb-ae93-0242ac130002','775d8e36-9859-11eb-a8b3-0242ac130003');
