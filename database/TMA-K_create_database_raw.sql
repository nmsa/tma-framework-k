-- ------------------------------------------------------------------------------
-- ------------------------------------------------------------------------------
-- ATMOSPHERE - http://www.atmosphere-eubrazil.eu/
-- 
-- 
-- Trustworthiness Monitoring & Assessment Framework
-- Component: Knowledge - database
-- 
-- Repository: https://github.com/eubr-atmosphere/tma-framework
-- License: https://github.com/eubr-atmosphere/tma-framework/blob/master/LICENSE
-- ------------------------------------------------------------------------------
-- ------------------------------------------------------------------------------
--
-- Raw file as generated by astah. 
-- Check TMA-K_create_database.sql for the normalized MySQL version.
--
-- @author José Pereira <josep@dei.uc.pt>
-- @author Nuno Antunes <nmsa@dei.uc.pt>
-- 
-- ------------------------------------------------------------------------------
-- ------------------------------------------------------------------------------


DROP TABLE Actuator CASCADE CONSTRAINTS;
DROP TABLE Metric CASCADE CONSTRAINTS;
DROP TABLE Probe CASCADE CONSTRAINTS;
DROP TABLE QualityModel CASCADE CONSTRAINTS;
DROP TABLE Resource CASCADE CONSTRAINTS;
DROP TABLE Time CASCADE CONSTRAINTS;
DROP TABLE Action CASCADE CONSTRAINTS;
DROP TABLE CompositeAttribute CASCADE CONSTRAINTS;
DROP TABLE Configuration CASCADE CONSTRAINTS;
DROP TABLE Description CASCADE CONSTRAINTS;
DROP TABLE LeafAttribute CASCADE CONSTRAINTS;
DROP TABLE MetricData CASCADE CONSTRAINTS;
DROP TABLE Plan CASCADE CONSTRAINTS;
DROP TABLE ActionPlan CASCADE CONSTRAINTS;
DROP TABLE ConfigurationData CASCADE CONSTRAINTS;
DROP TABLE Data CASCADE CONSTRAINTS;
DROP TABLE ConfigurationProfile CASCADE CONSTRAINTS;
DROP TABLE Preference CASCADE CONSTRAINTS;


-- -- Table time was removed for normalization.
-- DROP TABLE IF EXISTS Time;

CREATE TABLE Actuator (
    actuatorId INT NOT NULL PRIMARY KEY,
    address VARCHAR(1024),
    pubKey VARCHAR(1024)
);


CREATE TABLE ConfigurationProfile (
    configurationProfileID INT NOT NULL PRIMARY KEY,
    profileName VARCHAR(64) NOT NULL
);


CREATE TABLE Metric (
    metricId INT NOT NULL PRIMARY KEY,
    metricName VARCHAR(64),
    blockLevel INT
);


CREATE TABLE Preference (
    configurationProfileID INT NOT NULL,
    metricId INT NOT NULL,
    weight DOUBLE PRECISION,
    threshold DOUBLE PRECISION,

    PRIMARY KEY (configurationProfileID,metricId),

    FOREIGN KEY (configurationProfileID) REFERENCES ConfigurationProfile (configurationProfileID),
    FOREIGN KEY (metricId) REFERENCES Metric (metricId)
);

CREATE TABLE Probe (
    probeId INT NOT NULL PRIMARY KEY,
    probeName VARCHAR(128),
    password VARCHAR(128),
    salt VARCHAR(128) NOT NULL,
    token VARCHAR(256),
    tokenExpiration TIMESTAMP(6)
);


CREATE TABLE QualityModel (
    qualityModelId INT NOT NULL,
    metricId INT NOT NULL,
    modelName VARCHAR(64),
    modelDescriptionReference INT,
    businessThreshold DOUBLE PRECISION,
    PRIMARY KEY (qualityModelId, metricId),
    FOREIGN KEY (metricId) REFERENCES Metric (metricId)
);


CREATE TABLE Resource (
    resourceId INT NOT NULL PRIMARY KEY,
    resourceName VARCHAR(128),
    resourceType VARCHAR(16),
    resourceAddress VARCHAR(1024)
);


CREATE TABLE Time (
    valueTime TIMESTAMP(6) NOT NULL PRIMARY KEY
);


CREATE TABLE Action (
    actionId INT NOT NULL PRIMARY KEY,
    resourceId INT NOT NULL,
    actionName VARCHAR(128),
    actuatorId INT NOT NULL,

    FOREIGN KEY (resourceId) REFERENCES Resource (resourceId),
    FOREIGN KEY (actuatorId) REFERENCES Actuator (actuatorId)
);


CREATE TABLE CompositeAttribute (
    parentMetric INT NOT NULL,
    childMetric INT NOT NULL,
    attributeAggregationOperator INT,

    PRIMARY KEY (parentMetric,childMetric),

    FOREIGN KEY (parentMetric) REFERENCES Metric (metricId),
    FOREIGN KEY (childMetric) REFERENCES Metric (metricId)
);


CREATE TABLE Configuration (
    actionId INT NOT NULL PRIMARY KEY,
    keyName VARCHAR(128),
    domain VARCHAR(1024),

    FOREIGN KEY (actionId) REFERENCES Action (actionId)
);


CREATE TABLE Description (
    descriptionId INT NOT NULL PRIMARY KEY,
    dataType CHAR(16),
    descriptionName CHAR(128),
    unit CHAR(16)
);


CREATE TABLE LeafAttribute (
    descriptionId INT NOT NULL,
    metricId INT NOT NULL,
    metricAggregationOperator INT,
    numSamples INT,
    normalizationMethod VARCHAR(64),
    normalizationKind VARCHAR(64),
    minimumThreshold DOUBLE PRECISION,
    maximumThreshold DOUBLE PRECISION,
    PRIMARY KEY (metricId),
    FOREIGN KEY (descriptionId) REFERENCES Description (descriptionId),
    FOREIGN KEY (metricId) REFERENCES Metric (metricId)
);


CREATE TABLE MetricData (
    metricId INT NOT NULL,
    valueTime TIMESTAMP(6) NOT NULL,
    value DOUBLE PRECISION,
    resourceId INT,

    PRIMARY KEY (metricId,valueTime),

    FOREIGN KEY (metricId) REFERENCES Metric (metricId),
    FOREIGN KEY (resourceId) REFERENCES Resource (resourceId)
);


CREATE TABLE Plan (
    planId INT NOT NULL PRIMARY KEY,
    metricId INT NOT NULL,
    valueTime TIMESTAMP(6) NOT NULL,
    status INT,

    FOREIGN KEY (metricId,valueTime) REFERENCES MetricData (metricId,valueTime)
);


CREATE TABLE ActionPlan (
    planId INT NOT NULL,
    actionId INT NOT NULL,
    executionOrder INT,
    status INT,

    PRIMARY KEY (planId,actionId),

    FOREIGN KEY (planId) REFERENCES Plan (planId),
    FOREIGN KEY (actionId) REFERENCES Action (actionId)
);


CREATE TABLE ConfigurationData (
    planId INT NOT NULL,
    actionId INT NOT NULL,
    value VARCHAR(1024),

    PRIMARY KEY (planId,actionId),

    FOREIGN KEY (planId,actionId) REFERENCES ActionPlan (planId,actionId),
    FOREIGN KEY (actionId) REFERENCES Configuration (actionId)
);


CREATE TABLE Data (
    probeId INT NOT NULL,
    descriptionId INT NOT NULL,
    resourceId INT NOT NULL,
    valueTime TIMESTAMP(6) NOT NULL,
    value DOUBLE PRECISION,
    PRIMARY KEY (probeId,descriptionId,resourceId,valueTime),
    FOREIGN KEY (probeId) REFERENCES Probe (probeId),
    FOREIGN KEY (descriptionId) REFERENCES Description (descriptionId),
    FOREIGN KEY (resourceId) REFERENCES Resource (resourceId),
    FOREIGN KEY (valueTime) REFERENCES Time (valueTime)
);


