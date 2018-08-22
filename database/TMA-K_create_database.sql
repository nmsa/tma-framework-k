--------------------------------------------------------------------------------
--------------------------------------------------------------------------------
-- ATMOSPHERE - http://www.atmosphere-eubrazil.eu/
-- 
-- 
-- Trustworthiness Monitoring & Assessment Framework
-- Component: Knowledge - database
-- 
-- Repository: https://github.com/eubr-atmosphere/tma-framework
-- License: https://github.com/eubr-atmosphere/tma-framework/blob/master/LICENSE
--
-- @author José Pereira <josep@dei.uc.pt>
-- @author Nuno Antunes <nmsa@dei.uc.pt>
--------------------------------------------------------------------------------
--------------------------------------------------------------------------------


DROP TABLE IF EXISTS Data;
DROP TABLE IF EXISTS Configuration;

DROP TABLE IF EXISTS Actuator;
DROP TABLE IF EXISTS Description;
DROP TABLE IF EXISTS Probe;
DROP TABLE IF EXISTS Resource;
DROP TABLE IF EXISTS Action;

-- -- Table time was removed for normalization.
-- DROP TABLE IF EXISTS Time;

CREATE TABLE Actuator (
    actuatorId INT NOT NULL AUTO_INCREMENT,
    address VARCHAR(1024),
    pubKey VARCHAR(1024),
    PRIMARY KEY (actuatorId)
);

CREATE TABLE Description (
    descriptionId INT NOT NULL AUTO_INCREMENT,
    dataType CHAR(16),
    descriptionName CHAR(128),
    unit CHAR(16),
    PRIMARY KEY (descriptionId)
);

CREATE TABLE Probe (
    probeId INT NOT NULL AUTO_INCREMENT,
    probeName VARCHAR(128),
    password VARCHAR(128),
    salt VARCHAR(128) NOT NULL,
    token VARCHAR(256),
    tokenExpiration TIMESTAMP(6),
    PRIMARY KEY (probeId)
);


CREATE TABLE Resource (
    resourceId INT NOT NULL AUTO_INCREMENT,
    resourceName VARCHAR(128),
    resourceType VARCHAR(16),
    resourceAddress VARCHAR(1024),
    PRIMARY KEY (resourceId)
);

-- -- Table time was removed for normalization.
-- CREATE TABLE Time (
-- valueTime TIMESTAMP(6) NOT NULL,
-- PRIMARY KEY (valueTime)
-- );


CREATE TABLE Action (
    actionId INT NOT NULL AUTO_INCREMENT,
    actuatorId INT NOT NULL,
    resourceId INT NOT NULL,
    actionName VARCHAR(128),
    PRIMARY KEY (actionId)
);

CREATE TABLE Configuration (
    actionId INT NOT NULL,
    keyName VARCHAR(128),
    domain VARCHAR(1024),
    PRIMARY KEY (actionId)
);

CREATE TABLE Data (
    probeId INT NOT NULL,
    descriptionId INT NOT NULL,
    resourceId INT NOT NULL,
    valueTime TIMESTAMP(6) NOT NULL,
    value DOUBLE PRECISION,
    PRIMARY KEY (probeId,descriptionId,resourceId,valueTime)
);

ALTER TABLE Action ADD CONSTRAINT FK_Action_0 FOREIGN KEY (actuatorId) REFERENCES Actuator (actuatorId);
ALTER TABLE Action ADD CONSTRAINT FK_Action_1 FOREIGN KEY (resourceId) REFERENCES Resource (resourceId);

ALTER TABLE Configuration ADD CONSTRAINT FK_Configuration_0 FOREIGN KEY (actionId) REFERENCES Action (actionId);

ALTER TABLE Data ADD CONSTRAINT FK_Data_0 FOREIGN KEY (probeId) REFERENCES Probe (probeId);
ALTER TABLE Data ADD CONSTRAINT FK_Data_1 FOREIGN KEY (descriptionId) REFERENCES Description (descriptionId);
ALTER TABLE Data ADD CONSTRAINT FK_Data_2 FOREIGN KEY (resourceId) REFERENCES Resource (resourceId);

-- -- Table time was removed for normalization.
-- ALTER TABLE Data ADD CONSTRAINT FK_Data_3 FOREIGN KEY (valueTime) REFERENCES Time (valueTime);




