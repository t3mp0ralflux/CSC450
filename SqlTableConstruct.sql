use webeval;
drop table if exists questions;
drop table if exists responses;
drop table if exists users;

CREATE TABLE questions (
    Id       INT           NOT NULL,
    question VARCHAR (255) NULL,
    PRIMARY KEY  (Id ASC)
);

CREATE TABLE responses 
	(
    Id             INT          NOT NULL,
    QuestionNumber NUMERIC (18) NULL,
	Response       VARCHAR (50) NULL,
    UserName       VARCHAR (50) NULL,
    PRIMARY KEY (Id ASC)
);

CREATE TABLE users (
    Id       NVARCHAR (12) NOT NULL,
    Username NVARCHAR (50) NULL,
    PRIMARY KEY (Id ASC)
);

insert into questions values(1, 'Please describe a system' );
insert into questions values(2, 'How can nurses use this system to meet the aims of “Support Safety and Quality of Nursing”?' );
insert into questions values(3, 'How can nurses use this system to meet the aims of “Facilitate Continuity of Care and Care Coordination”?' );
insert into questions values(4, 'How can nurses use this system to meet the aims of “Partner with Patients and Families to participate in Health care”?' );
insert into questions values(5, 'Tell me about possible “Unexpected Outcomes” of this system' );
insert into questions values(6, 'Any suggestions to improve “Unexpected Outcomes” this system?' );

insert into Users values(850404362, 'belangerb');
insert into Users values('choij', 'choij');
