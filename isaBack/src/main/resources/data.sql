--DRUGS
insert into Drug (id, name,producer_Name,fabric_Code) values ('dac2b818-5838-11eb-ae93-0242ac130002','Ibuprofen', 'Hemofarm', '1162531');
insert into Drug (id, name,producer_Name,fabric_Code) values ('2c797174-5839-11eb-ae93-0242ac130002','Analgin', 'Hemofarm', '1162513');
insert into Drug (id, name,producer_Name,fabric_Code) values ('2fe1cd8e-5839-11eb-ae93-0242ac130002','Codaron', 'Galenika', '3162089');


--AUTHORITY
insert into authority (id, name) values ('7852aa5e-7040-4d99-8255-537a0b226c75','ROLE_PATIENT');
insert into authority (id, name) values ('563e9925-cff6-42b7-99fa-6b1235f67655','ROLE_SYSADMIN');
insert into authority (id, name) values ('09af8857-2f78-4eec-970f-059d3ed4589f','ROLE_DERMATHOLOGIST');
insert into authority (id, name) values ('ef9a3723-a72e-44ec-83ac-9d748fd0240f','ROLE_SUPPLIER');
insert into authority (id, name) values ('a1e3bac1-6093-4705-b835-eed75c3e5f21','ROLE_PHARMACIST');
insert into authority (id, name) values ('ea16767c-2c1f-49fb-ac98-c7739c0036e8','ROLE_PHARMACYADMIN');

--PHARMACY

insert into pharmacy (id, name, city, street,consultation_price,country, post_code,description) values ('775d8e36-9859-11eb-a8b3-0242ac130003', 'apoteka Jankovic','Novi Sad','Futoski put 54',1200,'Serbia', '21000', 'Zdravlje je prioritet!');            
insert into pharmacy (id, name, city, street,consultation_price,country, post_code,description) values ('775d9322-9859-11eb-a8b3-0242ac130003', 'Benu','Novi Sad','Vojvodjanske brigade 30',1500,'Serbia', '21000', 'U svemu najbolji!');  

--DERMATOLOGIST-USERS
insert into users (id, email, password, name, surname, phone_Number, active, address, version) values ('07a2c302-b584-11eb-8529-0242ac130003','dermatolog1@gmail.com', '$2a$10$TyNl6ipLWyDE/TfFM3uRse0SVPP4Rz7.mdZdDK3zqjKZqtKtJ3pf2','Marija','Jovanovic','064555787',true,'Partizanska 11', 1);                                
insert into users (id, email, password, name, surname, phone_Number, active, address, version) values ('aef9fa80-b584-11eb-8529-0242ac130003','dermatolog2@gmail.com', '$2a$10$TyNl6ipLWyDE/TfFM3uRse0SVPP4Rz7.mdZdDK3zqjKZqtKtJ3pf2','Milica','Peric','06388929',true,'Nikolajevska 12', 1);  


--USERS
insert into users (id, email, password, name, surname, phone_Number, active, address, version) values ('22793162-52d3-11eb-ae93-0242ac130002','patient1@gmail.com', '$2a$10$TyNl6ipLWyDE/TfFM3uRse0SVPP4Rz7.mdZdDK3zqjKZqtKtJ3pf2','Njeke','Zeke','0623333',true,'Kisacka 22', 1);

--PATIENTS
insert into patient (id) values ('22793162-52d3-11eb-ae93-0242ac130002');

--DERMATOLOGIST
insert into dermatologist (id) values ('07a2c302-b584-11eb-8529-0242ac130003');
insert into dermatologist (id) values ('aef9fa80-b584-11eb-8529-0242ac130003');


--USERS-AUTHORITY

--rolePatients
insert into user_authority (user_id, authority_id) values ('22793162-52d3-11eb-ae93-0242ac130002', '7852aa5e-7040-4d99-8255-537a0b226c75');

--roleDermatologist
insert into user_authority (user_id, authority_id) values ('07a2c302-b584-11eb-8529-0242ac130003', '09af8857-2f78-4eec-970f-059d3ed4589f');
insert into user_authority (user_id, authority_id) values ('aef9fa80-b584-11eb-8529-0242ac130003', '09af8857-2f78-4eec-970f-059d3ed4589f');

---DERMATOLOGIST-PHARMACIES

insert into dermatologist_pharmacy (dermatologist_id, pharmacy_id) values ('07a2c302-b584-11eb-8529-0242ac130003' ,'775d8e36-9859-11eb-a8b3-0242ac130003');
insert into dermatologist_pharmacy (dermatologist_id, pharmacy_id) values ('aef9fa80-b584-11eb-8529-0242ac130003' ,'775d9322-9859-11eb-a8b3-0242ac130003');

--APOINTMENTS

insert into appointment (id, appointment_status, appointment_type, start_date_time, end_date_time, price,pharmacy_id, employee_id) values ('f183329c-b58c-11eb-8529-0242ac130003','FREE','EXAMINATION', '2021-05-19 11:30:00', '2021-05-19 12:00:00', 1300, '775d8e36-9859-11eb-a8b3-0242ac130003', '07a2c302-b584-11eb-8529-0242ac130003');         
insert into appointment (id, appointment_status, appointment_type, start_date_time, end_date_time, price,pharmacy_id, employee_id) values ('f18331d4-b58c-11eb-8529-0242ac130003','FREE','EXAMINATION', '2021-05-19 12:30:00', '2021-05-19 13:00:00', 1300, '775d8e36-9859-11eb-a8b3-0242ac130003', '07a2c302-b584-11eb-8529-0242ac130003');  




