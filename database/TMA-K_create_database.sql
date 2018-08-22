CREATE TABLE Actuator (
 actuatorId INT NOT NULL,
 address VARCHAR(1024),
 pubKey VARCHAR(1024)
);

ALTER TABLE Actuator ADD CONSTRAINT PK_Actuator PRIMARY KEY (actuatorId);


CREATE TABLE Description (
 descriptionId INT NOT NULL,
 dataType CHAR(16),
 descriptionName CHAR(128),
 unit CHAR(16)
);

ALTER TABLE Description ADD CONSTRAINT PK_Description PRIMARY KEY (descriptionId);


CREATE TABLE Probe (
 probeId INT NOT NULL,
 probeName VARCHAR(128),
 password VARCHAR(128),
 salt VARCHAR(128) NOT NULL,
 token VARCHAR(256),
 tokenExpiration TIMESTAMP(10)
);

ALTER TABLE Probe ADD CONSTRAINT PK_Probe PRIMARY KEY (probeId);


CREATE TABLE Resource (
 resourceId INT NOT NULL,
 resourceName VARCHAR(128),
 resourceType VARCHAR(16),
 resourceAddress VARCHAR(1024)
);

ALTER TABLE Resource ADD CONSTRAINT PK_Resource PRIMARY KEY (resourceId);


CREATE TABLE Time (
 valueTime TIMESTAMP(10) NOT NULL
);

ALTER TABLE Time ADD CONSTRAINT PK_Time PRIMARY KEY (valueTime);


CREATE TABLE Action (
 actionId INT NOT NULL,
 actuatorId INT NOT NULL,
 resourceId INT NOT NULL,
 actionName VARCHAR(128)
);

ALTER TABLE Action ADD CONSTRAINT PK_Action PRIMARY KEY (actionId);


CREATE TABLE Configuration (
 actionId INT NOT NULL,
 key VARCHAR(128),
 domain VARCHAR(1024)
);

ALTER TABLE Configuration ADD CONSTRAINT PK_Configuration PRIMARY KEY (actionId);


CREATE TABLE Data (
 probeId INT NOT NULL,
 descriptionId INT NOT NULL,
 resourceId INT NOT NULL,
 valueTime TIMESTAMP(10) NOT NULL,
 value DOUBLE PRECISION
);

ALTER TABLE Data ADD CONSTRAINT PK_Data PRIMARY KEY (probeId,descriptionId,resourceId,valueTime);


ALTER TABLE Action ADD CONSTRAINT FK_Action_0 FOREIGN KEY (actuatorId) REFERENCES Actuator (actuatorId);
ALTER TABLE Action ADD CONSTRAINT FK_Action_1 FOREIGN KEY (resourceId) REFERENCES Resource (resourceId);


ALTER TABLE Configuration ADD CONSTRAINT FK_Configuration_0 FOREIGN KEY (actionId) REFERENCES Action (actionId);


ALTER TABLE Data ADD CONSTRAINT FK_Data_0 FOREIGN KEY (probeId) REFERENCES Probe (probeId);
ALTER TABLE Data ADD CONSTRAINT FK_Data_1 FOREIGN KEY (descriptionId) REFERENCES Description (descriptionId);
ALTER TABLE Data ADD CONSTRAINT FK_Data_2 FOREIGN KEY (resourceId) REFERENCES Resource (resourceId);
ALTER TABLE Data ADD CONSTRAINT FK_Data_3 FOREIGN KEY (valueTime) REFERENCES Time (valueTime);


