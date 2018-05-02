CREATE Schema `webeval` ;

use webeval2;

CREATE TABLE `questions` (
  `Id` int(11) NOT NULL,
  `question` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
CREATE TABLE `responses` (
  `Id` int(11) DEFAULT NULL,
  `QuestionNumber` int(11) NOT NULL,
  `Response` varchar(255) NOT NULL,
  `UserName` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`QuestionNumber`,`Response`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
CREATE TABLE `users` (
  `Id` varchar(50) NOT NULL,
  `Username` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


insert into questions values(1, "Please Describe a System");
insert into questions values(2, "How can nurses use this system to meet the aims of “Support Safety and Quality of Nursing”?");
insert into questions values(3, "How can nurses use this system to meet the aims of “Facilitate Continuity of Care and Care Coordination”?");
insert into questions values(4, "How can nurses use this system to meet the aims of “Partner with Patients and Families to participate in Health care”?");
insert into questions values(5, "Tell me about possible “Unexpected Outcomes” of this system");
insert into questions values(6, "Any suggestions to improve “Unexpected Outcomes” this system?");

insert into responses values(1,	1,	"20180428BelangerB01",	"BelangerB");
insert into responses values(3, 1, "20180428test01", "test");
insert into responses values(2, 5, "20180428BelangerB05", "BelangerB");
insert into responses values(2, 5, "20180428text05", "test");

insert into users values(850404362, "BelangerB");
insert into users values("test", "test");
insert into users values("choij", "choij");