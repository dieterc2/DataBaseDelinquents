CREATE TABLE dining_hall (
  dh_name			varchar(30) not null,
  dh_type			varchar(30) not null,
  location  		varchar(30),
  adm_id1   		int not null,
  customer_status1	varchar(30),
  Primary Key (dh_name, dh_type),
  Foreign Key (adm_id1)references admin_detail(adm_id)
  Foreign Key (customer_status1) references payment_type(customer_status)
);

CREATE TABLE operational_hours(
  dh_name1    varchar(30) not null,
  time_open  varchar(10),
  time_close varchar(10),
  wk_day	 varchar(15) not null,
  Primary Key (dh_name1, wk_day, time_open),
  foreign key (dh_name1) references dining_hall(dh_name)
);

CREATE TABLE admin_detail (
  username varchar(20),
  adm_id  int not null,
  password varchar(20),
  Primary Key (adm_id)
);

CREATE TABLE a_la_carte_menu(
  dh_name2   varchar(30) not null,
  food_name varchar(30) not null,
  food_desc varchar(100),
  price1     int,
  Primary Key (dh_name2, food_name),
  foreign key (dh_name2) references dining_hall(dh_name)
);

CREATE TABLE buffet(
  dh_name3   varchar(30) not null,
  cuisine varchar(30) not null,
  price2     int,
  Primary Key (dh_name3, cuisine),
  foreign key (dh_name3) references dining_hall(dh_name)
);

CREATE TABLE payment_type (
  customer_status   varchar(30) not null,
  mp_name  			varchar(40),
  discount			int,
  Primary Key (customer_status, mp_name)
);

INSERT INTO dining_hall VALUES ('Western Dining Commons', 'buffet','480 Western Drive', 1, 'None');
INSERT INTO dining_hall VALUES ('Haines Boulangerir', 'ala carte', '550 East Spring Street', 1, 'None');
INSERT INTO dining_hall VALUES ('Mein Street', 'ala carte', '550 East Spring Street', 1, 'None');
INSERT INTO dining_hall VALUES ('Pulley Diner', 'ala carte', '550 East Spring Street', 1, 'None');
INSERT INTO dining_hall VALUES ('Serrano', 'ala carte', '550 East Spring Street', 1, 'None');
INSERT INTO dining_hall VALUES ('Sundial Pizza', 'ala carte', '550 East Spring Street', 1, 'None');
INSERT INTO dining_hall VALUES ('Miami Ice', 'ala carte', '550 East Spring Street', 1, 'None');
INSERT INTO dining_hall VALUES ('Bell Tower Place', 'ala carte', '401 East High Street', 1, 'None');
INSERT INTO dining_hall VALUES ('Dividends', 'ala carte', '800 East High Street', 1, 'None');
INSERT INTO dining_hall VALUES ('Harris Dining Center', 'buffet', '500 Harris Drive', 1, 'None');
INSERT INTO dining_hall VALUES ('King Cafe', 'ala carte', '151 South Campus Avenue', 1, 'None');
INSERT INTO dining_hall VALUES ('Delish', 'ala carte', '571 Maple Street', 1, 'None');
INSERT INTO dining_hall VALUES ('Encounter', 'ala carte', '571 Maple Street', 1, 'None');
INSERT INTO dining_hall VALUES ('Red Brick Pizza', 'ala carte', '571 Maple Street', 1, 'None');
INSERT INTO dining_hall VALUES ('Patisserie', 'ala carte', '571 Maple Street', 1, 'None');
INSERT INTO dining_hall VALUES ('Pacific Rim', 'ala carte', '571 Maple Street', 1, 'None');
INSERT INTO dining_hall VALUES ('Americas', 'ala carte', '571 Maple Street', 1, 'None');
INSERT INTO dining_hall VALUES ('First Stop', 'ala carte', '571 Maple Street', 1, 'None');
INSERT INTO dining_hall VALUES ('Martin Dining Center', 'buffet', '5397 Bonham Road', 1, 'None');
INSERT INTO dining_hall VALUES ('The Pro Shop', 'ala carte', ' 750 South Oak Street', 1, 'None');
INSERT INTO dining_hall VALUES ('Tuffys', 'ala carte', ' East Spring Street', 1, 'None');
INSERT INTO operational_hours VALUES ('Western Dining Commons', '7:30AM', '10:30PM', 'Weekdays');
INSERT INTO operational_hours VALUES ('Western Dining Commons', '11:00PM', '2:00PM', 'Weekdays');
INSERT INTO operational_hours VALUES ('Western Dining Commons', '5:00PM', '8:00PM', 'Weekdays');
INSERT INTO operational_hours VALUES ('Western Dining Commons', '5:00PM', '7:00PM', 'Saturday');
INSERT INTO operational_hours VALUES ('Western Dining Commons', '10:00AM', '2:00PM', 'Sunday');
INSERT INTO operational_hours VALUES ('Western Dining Commons', '5:00PM', '8:00PM', 'Sunday');
INSERT INTO operational_hours VALUES ('Haines Boulangerie', '7:30AM', '9:00PM', 'Weekdays');
INSERT INTO operational_hours VALUES ('Haines Boulangerie', '8:00AM', '8:00PM', 'Saturday');
INSERT INTO operational_hours VALUES ('Haines Boulangerie', '8:00AM', '8:00PM', 'Sunday');
INSERT INTO operational_hours VALUES ('Mein Street', '11:00AM', '8:00PM', 'All Week');
INSERT INTO operational_hours VALUES ('Miami Ice', '7:00AM', '10:00PM', 'Weekdays');
INSERT INTO operational_hours VALUES ('Miami Ice', '8:00AM', '10:00PM', 'Saturday');
INSERT INTO operational_hours VALUES ('Miami Ice', '8:00AM', '10:00PM', 'Sunday');
INSERT INTO operational_hours VALUES ('Pulley Diner', '12:00AM', '11:59PM', 'All Week');
INSERT INTO operational_hours VALUES ('Serrano', '11:00AM', '11:00PM', ' All Week');
INSERT INTO operational_hours VALUES ('Sundial', '11:00AM', '11:59PM', 'All Week');
INSERT INTO operational_hours VALUES ('Bell Tower Place', '8:00AM', '8:00PM', 'Mon-Thr');
INSERT INTO operational_hours VALUES ('Bell Tower Place', '8:00AM', '2:00PM', 'Friday');
INSERT INTO operational_hours VALUES ('Dividends', '7:45AM', '4:00PM', 'Mon-Thr');
INSERT INTO operational_hours VALUES ('Dividends', '7:45AM', '2:30PM', 'Friday');
INSERT INTO operational_hours VALUES ('Harris Dining Center', '7:30AM', '10:30AM', 'Weekdays');
INSERT INTO operational_hours VALUES ('Harris Dining Center', '11:00AM', '2:00PM', 'Weekdays');
INSERT INTO operational_hours VALUES ('Harris Dining Center', '5:00PM', '8:00PM', 'Weekdays');
INSERT INTO operational_hours VALUES ('Harris Dining Center', '11:00AM', '2:00PM', 'Saturday');
INSERT INTO operational_hours VALUES ('Harris Dining Center', '11:00AM', '2:00PM', 'Sunday');
INSERT INTO operational_hours VALUES ('Harris Dining Center', '5:00PM', '8:00PM', 'Sunday');
INSERT INTO operational_hours VALUES ('King Cafe', '7:00AM', '11:00PM', 'Mon-Thr');
INSERT INTO operational_hours VALUES ('King Cafe', '7:00AM', '5:00PM', 'Friday');
INSERT INTO operational_hours VALUES ('King Cafe', '11:00AM', '3:00PM', 'Saturday');
INSERT INTO operational_hours VALUES ('King Cafe', '10:00AM', '11:00PM', 'Sunday');
INSERT INTO operational_hours VALUES ('Americas', '11:00AM', '7:00PM', 'Weekdays');
INSERT INTO operational_hours VALUES ('Americas', '4:00PM', '9:00PM', 'Sunday');
INSERT INTO operational_hours VALUES ('Delish', '11:00AM', '9:00PM', 'Weekdays');
INSERT INTO operational_hours VALUES ('Delish', '11:00AM', '7:00PM', 'Saturday');
INSERT INTO operational_hours VALUES ('Delish', '11:00AM', '9:00PM', 'Sunday');
INSERT INTO operational_hours VALUES ('Encounter', '11:00AM', '9:00PM', 'Weekdays');
INSERT INTO operational_hours VALUES ('First Stop', '8:00AM', '9:00PM', 'Weekdays');
INSERT INTO operational_hours VALUES ('First Stop', '10:00AM', '7:00PM', 'Saturday');
INSERT INTO operational_hours VALUES ('First Stop', '10:00AM', '7:00PM', 'Sunday');
INSERT INTO operational_hours VALUES ('Pacific Rim', '11:00AM', '7:00PM', 'Weekdays');
INSERT INTO operational_hours VALUES ('Patisserie', '7:00AM', '9:00PM', 'Weekdays');
INSERT INTO operational_hours VALUES ('Patisserie', '9:00AM', '4:00PM', 'Saturday');
INSERT INTO operational_hours VALUES ('Patisserie', '9:00AM', '4:00PM', 'Sunday');
INSERT INTO operational_hours VALUES ('Red Brick', '11:00AM', '7:00PM', 'All Week');
INSERT INTO operational_hours VALUES ('Martin Dining Center', '7:30AM', '10:30AM', 'Weekdays');
INSERT INTO operational_hours VALUES ('Martin Dining Center', '11:00AM', '2:00PM', 'Weekdays');
INSERT INTO operational_hours VALUES ('Martin Dining Center', '5:00PM', '8:00PM', 'Weekdays');
INSERT INTO operational_hours VALUES ('Martin Dining Center', '11:00AM', '2:00PM', 'Saturday');
INSERT INTO operational_hours VALUES ('Martin Dining Center', '11:00AM', '2:00PM', 'Sunday');
INSERT INTO operational_hours VALUES ('Martin Dining Center', '5:00PM', '8:00PM', 'Sunday');
INSERT INTO operational_hours VALUES ('The Pro Shop', '6:00AM', '11:00PM', 'Weekdays');
INSERT INTO operational_hours VALUES ('The Pro Shop', '8:00AM', '11:00PM', 'Saturday');
INSERT INTO operational_hours VALUES ('The Pro Shop', '10:00AM', '11:00PM', 'Sunday');
INSERT INTO operational_hours VALUES ('Tuffys', '10:30AM', '2:30PM', 'Weekdays');

INSERT INTO admin_detail VALUES ('big cheese', 1, 'password');

INSERT INTO payment_type VALUES ('Express','buffet', 30);
INSERT INTO payment_type VALUES ('Diplomat','buffet', 50);
INSERT INTO payment_type VALUES ('Diplomat','ala carte', 30);
INSERT INTO payment_type VALUES ('None','either', 0);

INSERT INTO buffet VALUES('Harris Dining Center', 'Breakfast', 10);
INSERT INTO buffet VALUES('Harris Dining Center', 'Lunch', 15);
INSERT INTO buffet VALUES('Delish', 'Breakfast', 9);
INSERT INTO buffet VALUES('Delish', 'Dinner', 12);

INSERT INTO a_la_carte_menu VALUES('Delish', 'Sandwich', 'Delicious', 8);
INSERT INTO a_la_carte_menu VALUES('Delish', 'Chips', 'Good', 6);
INSERT INTO a_la_carte_menu VALUES('Delish', 'Drink', 'fluids', 3);
INSERT INTO a_la_carte_menu VALUES('Delish', 'Soup', 'tasty', 4);

