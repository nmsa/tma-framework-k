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
-- Normalized MySQL version of the script for the knowledge 
-- database creation. 
--
-- @author José Pereira <josep@dei.uc.pt>
-- @author Nuno Antunes <nmsa@dei.uc.pt>
-- 
-- ------------------------------------------------------------------------------
-- ------------------------------------------------------------------------------


DROP TABLE IF EXISTS Data;
DROP TABLE IF EXISTS ConfigurationData;

DROP TABLE IF EXISTS Configuration;

DROP TABLE IF EXISTS MetricData;
DROP TABLE IF EXISTS MetricComposition;
DROP TABLE IF EXISTS MetricComponent;

DROP TABLE IF EXISTS CompositeAttribute;
DROP TABLE IF EXISTS Configuration;
DROP TABLE IF EXISTS LeafAttribute;

DROP TABLE IF EXISTS ActionPlan;
DROP TABLE IF EXISTS Plan;

DROP TABLE IF EXISTS Metric;
DROP TABLE IF EXISTS QualityModel;

DROP TABLE IF EXISTS Action;
DROP TABLE IF EXISTS Actuator;
DROP TABLE IF EXISTS Description;
DROP TABLE IF EXISTS Probe;
DROP TABLE IF EXISTS Resource;

DROP TABLE IF EXISTS ConfigurationProfile;
DROP TABLE IF EXISTS Preference;

DROP TABLE IF EXISTS AdaptationRules;
DROP TABLE IF EXISTS PlotConfig;


-- -- Table time was removed for normalization.
-- DROP TABLE IF EXISTS Time;


CREATE TABLE Actuator (
    actuatorId INT NOT NULL AUTO_INCREMENT,
    address VARCHAR(1024),
    pubKey VARCHAR(1024),
    PRIMARY KEY (actuatorId)
);

CREATE TABLE AdaptationRules (
 id INT NOT NULL,
 rulesFile BLOB NOT NULL,
 PRIMARY KEY (id)
);

CREATE TABLE Metric (
    metricId INT NOT NULL AUTO_INCREMENT,
    metricName VARCHAR(64),
    blockLevel INT,
    PRIMARY KEY (metricId)
);

CREATE TABLE PlotConfig (
 plotConfigId INT NOT NULL AUTO_INCREMENT,
 configObject BLOB NOT NULL,
 plotConfigName VARCHAR(1024) NOT NULL,
 PRIMARY KEY (plotConfigId)
);

CREATE TABLE QualityModel (
    qualityModelId INT NOT NULL AUTO_INCREMENT,
    metricId INT NOT NULL,
    modelName VARCHAR(64),
    modelDescriptionReference INT,
    businessThreshold DOUBLE PRECISION,
    PRIMARY KEY (qualityModelId),
    FOREIGN KEY (metricId) REFERENCES Metric (metricId)
);

CREATE TABLE ConfigurationProfile (
    configurationProfileID INT NOT NULL AUTO_INCREMENT,
    profileName VARCHAR(64) NOT NULL,
    qualityModelId INT NOT NULL,
    PRIMARY KEY (configurationProfileID),
    FOREIGN KEY (qualityModelId) REFERENCES QualityModel (qualityModelId)
);

CREATE TABLE Preference (
    configurationProfileID INT NOT NULL,
    metricId INT NOT NULL,
    weight DOUBLE PRECISION,
    threshold DOUBLE PRECISION,

    PRIMARY KEY (configurationProfileID, metricId),

    FOREIGN KEY (configurationProfileID) REFERENCES ConfigurationProfile (configurationProfileID),
    FOREIGN KEY (metricId) REFERENCES Metric (metricId)
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
    configurationProfileID INT NOT NULL,
    active TINYINT NOT NULL,
    PRIMARY KEY (resourceId),
    FOREIGN KEY (configurationProfileID) REFERENCES ConfigurationProfile (configurationProfileID)
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
    PRIMARY KEY (actionId),
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
    configurationId INT NOT NULL AUTO_INCREMENT,
    actionId INT NOT NULL,
    keyName VARCHAR(128),
    domain VARCHAR(1024),

    PRIMARY KEY (configurationId, actionId),

    FOREIGN KEY (actionId) REFERENCES Action (actionId)
);


CREATE TABLE Description (
    descriptionId INT NOT NULL AUTO_INCREMENT,
    dataType VARCHAR(16),
    descriptionName VARCHAR(255),
    unit VARCHAR(16),
    PRIMARY KEY (descriptionId)
);


CREATE TABLE LeafAttribute (
    metricId INT NOT NULL,
    descriptionId INT NOT NULL,
    metricAggregationOperator INT,
    numSamples INT,
    normalizationMethod VARCHAR(64),
    normalizationKind INT,
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

    PRIMARY KEY (metricId,valueTime,resourceId),

    FOREIGN KEY (metricId) REFERENCES Metric (metricId),
    FOREIGN KEY (resourceId) REFERENCES Resource (resourceId)
);


CREATE TABLE Plan (
    planId INT NOT NULL AUTO_INCREMENT,
    metricId INT NOT NULL,
    valueTime TIMESTAMP(6) NOT NULL,
    status INT,
    resourceId INT,

    PRIMARY KEY (planId),

    FOREIGN KEY (metricId,valueTime,resourceId) REFERENCES MetricData (metricId,valueTime,resourceId)
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
    configurationId INT NOT NULL,
    value VARCHAR(1024),

    PRIMARY KEY (planId, actionId, configurationId),

    FOREIGN KEY (planId, actionId) REFERENCES ActionPlan (planId,actionId),
    FOREIGN KEY (actionId, configurationId) REFERENCES Configuration (actionId,configurationId)
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
    FOREIGN KEY (resourceId) REFERENCES Resource (resourceId)
);

CREATE VIEW CompositeAttributeView AS SELECT childMetric AS metricId, attributeAggregationOperator as attributeAggregationOperator FROM CompositeAttribute where parentMetric = childMetric 
UNION 
SELECT childMetric AS metricId, attributeAggregationOperator as attributeAggregationOperator FROM CompositeAttribute ca where not exists (select * from LeafAttribute la where ca.childMetric = la.metricId);

CREATE VIEW MetricAttributeView AS SELECT m.metricId as metricId, ca.parentMetric as compositeAttributeId, m.metricName as name FROM Metric m join CompositeAttribute ca on m.metricId = ca.childMetric;
