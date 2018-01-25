/*Drop tables*/
drop table if exists scheduled;
drop table if exists blacklist;
drop table if exists venue;
drop table if exists employee;

/*Create tables*/
create table employee (
	employeeID varchar(10),
	fName varchar(15),
	lName varchar(15),
	password varchar(15),
	phone varchar(15),
	email varchar(25),
	isManager boolean,
	primary key(employeeID)
);

create table venue (
	venueID varchar(10),
	name varchar(20),
	tableNum integer,
	address varchar(50),
	primary key(venueID)
);

create table blacklist (
	employeeID varchar(10),
	venueID varchar(10),
	primary key(employeeID, venueID),
	foreign key(employeeID) references employee(employeeID) 
		on delete cascade 
		on update cascade,
	foreign key(venueID) references venue(venueID)
		on delete cascade
		on update cascade
);

create table scheduled (
	employeeID varchar(10),
    venueID varchar(10),
    primary key(employeeID, venueID),
    foreign key(employeeID) references employee(employeeID)
		on delete cascade
        on update cascade,
	foreign key(venueID) references venue(venueID)
		on delete cascade
		on update cascade
);