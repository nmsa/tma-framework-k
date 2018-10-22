---------------------------------------------------------------------------------
---------------------------------------------------------------------------------
--- ATMOSPHERE - http://www.atmosphere-eubrazil.eu/
--- 
--- 
--- Trustworthiness Monitoring & Assessment Framework
--- Component: Knowledge - database
--- 
--- Repository: https://github.com/eubr-atmosphere/tma-framework
--- License: https://github.com/eubr-atmosphere/tma-framework/blob/master/LICENSE
---------------------------------------------------------------------------------
---------------------------------------------------------------------------------
---
--- Raw file as generated by astah. 
--- Check TMA-K_create_database.sql for the normalized MySQL version.
---
---
--- @author José Pereira <josep@dei.uc.pt>
--- @author Nuno Antunes <nmsa@dei.uc.pt>
--- 
--------------------------------------------------------------------------------
--------------------------------------------------------------------------------

CREATE TABLE Actuator (
 actuatorId INT NOT NULL,
 address VARCHAR(1024),
 pubKey VARCHAR(1024)
);

ALTER TABLE Actuator ADD CONSTRAINT PK_Actuator PRIMARY KEY (actuatorId);


CREATE TABLE Probe (
 probeId INT NOT NULL,
 probeName VARCHAR(128),
 password VARCHAR(128),
 salt VARCHAR(128) NOT NULL,
 token VARCHAR(256),
 tokenExpiration TIMESTAMP(6)
);

ALTER TABLE Probe ADD CONSTRAINT PK_Probe PRIMARY KEY (probeId);


CREATE TABLE QualityModel (
 qualityModelId VARCHAR(10) NOT NULL,
 modelName VARCHAR(10)
);

ALTER TABLE QualityModel ADD CONSTRAINT PK_QualityModel PRIMARY KEY (qualityModelId);


CREATE TABLE Resource (
 resourceId INT NOT NULL,
 resourceName VARCHAR(128),
 resourceType VARCHAR(16),
 resourceAddress VARCHAR(1024)
);

ALTER TABLE Resource ADD CONSTRAINT PK_Resource PRIMARY KEY (resourceId);


CREATE TABLE Time (
 valueTime TIMESTAMP(6) NOT NULL
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
 keyName VARCHAR(128),
 domain VARCHAR(1024)
);

ALTER TABLE Configuration ADD CONSTRAINT PK_Configuration PRIMARY KEY (actionId);


CREATE TABLE Metric (
 metricId INT NOT NULL,
 qualityModelId VARCHAR(10) NOT NULL,
 normalizationKind VARCHAR(10),
 metricName VARCHAR(10),
 metricAggregationOperator INT,
 threshold DOUBLE PRECISION,
 blockLevel INT
);

ALTER TABLE Metric ADD CONSTRAINT PK_Metric PRIMARY KEY (metricId,qualityModelId);


CREATE TABLE MetricData (
 metricId INT NOT NULL,
 valueTime TIMESTAMP(10) NOT NULL,
 qualityModelId VARCHAR(10) NOT NULL,
 value DOUBLE PRECISION,
 resourceId INT NOT NULL
);

ALTER TABLE MetricData ADD CONSTRAINT PK_MetricData PRIMARY KEY (metricId,valueTime,qualityModelId);


CREATE TABLE Description (
 descriptionId INT NOT NULL,
 dataType CHAR(16),
 descriptionName CHAR(128),
 unit CHAR(16)
);

ALTER TABLE Description ADD CONSTRAINT PK_Description PRIMARY KEY (descriptionId);


CREATE TABLE MetricComponents (
 descriptionId INT NOT NULL,
 metricId INT NOT NULL,
 qualityModelId VARCHAR(10) NOT NULL,
 attributeAggregationOperator INT,
 numSamples INT,
 weight DOUBLE PRECISION
);

ALTER TABLE MetricComponents ADD CONSTRAINT PK_MetricComponents PRIMARY KEY (descriptionId,metricId,qualityModelId);


CREATE TABLE Data (
 probeId INT NOT NULL,
 descriptionId INT NOT NULL,
 resourceId INT NOT NULL,
 valueTime TIMESTAMP(6) NOT NULL,
 value DOUBLE PRECISION
);

ALTER TABLE Data ADD CONSTRAINT PK_Data PRIMARY KEY (probeId,descriptionId,resourceId,valueTime);


ALTER TABLE Action ADD CONSTRAINT FK_Action_0 FOREIGN KEY (actuatorId) REFERENCES Actuator (actuatorId);
ALTER TABLE Action ADD CONSTRAINT FK_Action_1 FOREIGN KEY (resourceId) REFERENCES Resource (resourceId);


ALTER TABLE Configuration ADD CONSTRAINT FK_Configuration_0 FOREIGN KEY (actionId) REFERENCES Action (actionId);


ALTER TABLE Metric ADD CONSTRAINT FK_Metric_0 FOREIGN KEY (qualityModelId) REFERENCES QualityModel (qualityModelId);


ALTER TABLE MetricData ADD CONSTRAINT FK_MetricData_0 FOREIGN KEY (metricId,qualityModelId) REFERENCES Metric (metricId,qualityModelId);
ALTER TABLE MetricData ADD CONSTRAINT FK_MetricData_1 FOREIGN KEY (resourceId) REFERENCES Resource (resourceId);


ALTER TABLE MetricComponents ADD CONSTRAINT FK_MetricComponents_0 FOREIGN KEY (descriptionId) REFERENCES Description (descriptionId);
ALTER TABLE MetricComponents ADD CONSTRAINT FK_MetricComponents_1 FOREIGN KEY (metricId,qualityModelId) REFERENCES Metric (metricId,qualityModelId);


ALTER TABLE Data ADD CONSTRAINT FK_Data_0 FOREIGN KEY (probeId) REFERENCES Probe (probeId);
ALTER TABLE Data ADD CONSTRAINT FK_Data_1 FOREIGN KEY (descriptionId) REFERENCES Description (descriptionId);
ALTER TABLE Data ADD CONSTRAINT FK_Data_2 FOREIGN KEY (resourceId) REFERENCES Resource (resourceId);
ALTER TABLE Data ADD CONSTRAINT FK_Data_3 FOREIGN KEY (valueTime) REFERENCES Time (valueTime);


