INSERT INTO Probe(probeId,probeName,password,salt,token) VALUES (1,"probe Wildfly WSVD", "n/a","n/a","n/a");
INSERT INTO Probe(probeId,probeName,password,salt,token) VALUES (2,"probe MySQL WSVD", "n/a","n/a","n/a");
INSERT INTO Probe(probeId,probeName,password,salt,token) VALUES (3,"probe Demo Java", "n/a","n/a","n/a");
INSERT INTO Probe(probeId,probeName,password,salt,token) VALUES (4,"probe Demo Python", "n/a","n/a","n/a");
INSERT INTO Probe(probeId,probeName,password,salt,token) VALUES (5,"probe Docker", "n/a","n/a","n/a");
INSERT INTO Probe(probeId,probeName,password,salt,token) VALUES (6,"probe Kubernetes", "n/a","n/a","n/a");
INSERT INTO Probe(probeId,probeName,password,salt,token) VALUES (7,"probe K8S", "n/a","n/a","n/a");
INSERT INTO Description(descriptionId,dataType,descriptionName,unit) VALUES (1,"CPU_Usage", "measurement","Mi");
INSERT INTO Description(descriptionId,dataType,descriptionName,unit) VALUES (2,"Memory", "measurement","Bytes");
INSERT INTO Description(descriptionId,dataType,descriptionName,unit) VALUES (3,"Disk", "event","n/a");
INSERT INTO Description(descriptionId,dataType,descriptionName,unit) VALUES (4,"CPU_Usage", "measurement","Mi");
INSERT INTO Description(descriptionId,dataType,descriptionName,unit) VALUES (5,"Memory", "measurement","Bytes");
INSERT INTO Description(descriptionId,dataType,descriptionName,unit) VALUES (6,"Disk", "event","n/a");
INSERT INTO Description(descriptionId,dataType,descriptionName,unit) VALUES (7,"CPU_Usage", "measurement","Mi");
INSERT INTO Description(descriptionId,dataType,descriptionName,unit) VALUES (8,"Memory", "measurement","Bytes");
INSERT INTO Description(descriptionId,dataType,descriptionName,unit) VALUES (9,"Disk", "event","n/a");
INSERT INTO Description(descriptionId,dataType,descriptionName,unit) VALUES (10,"CPU_Usage", "measurement","Mi");
INSERT INTO Description(descriptionId,dataType,descriptionName,unit) VALUES (11,"Memory", "measurement","Bytes");
INSERT INTO Description(descriptionId,dataType,descriptionName,unit) VALUES (12,"Disk", "event","n/a");
INSERT INTO Description(descriptionId,dataType,descriptionName,unit) VALUES (13,"Memory", "event","n/a");
INSERT INTO Description(descriptionId,dataType,descriptionName,unit) VALUES (14,"CPU_Usage", "measurement","Mi");
INSERT INTO Description(descriptionId,dataType,descriptionName,unit) VALUES (15,"Memory", "measurement","Bytes");
INSERT INTO Description(descriptionId,dataType,descriptionName,unit) VALUES (16,"Disk", "event","n/a");
INSERT INTO Description(descriptionId,dataType,descriptionName,unit) VALUES (17,"CPU_Usage", "measurement","Mi");
INSERT INTO Description(descriptionId,dataType,descriptionName,unit) VALUES (18,"Memory", "measurement","Bytes");
INSERT INTO Description(descriptionId,dataType,descriptionName,unit) VALUES (19,"Disk", "event","n/a");
INSERT INTO Description(descriptionId,dataType,descriptionName,unit) VALUES (20,"CPU_Usage", "measurement","Mi");
INSERT INTO Description(descriptionId,dataType,descriptionName,unit) VALUES (21,"Memory", "measurement","Bytes");
INSERT INTO Description(descriptionId,dataType,descriptionName,unit) VALUES (22,"Disk", "event","n/a");
INSERT INTO Description(descriptionId,dataType,descriptionName,unit) VALUES (23,"CPU_Usage", "measurement","Mi");
INSERT INTO Description(descriptionId,dataType,descriptionName,unit) VALUES (24,"Memory", "measurement","Bytes");
INSERT INTO Description(descriptionId,dataType,descriptionName,unit) VALUES (25,"Disk", "event","n/a");
INSERT INTO Description(descriptionId,dataType,descriptionName,unit) VALUES (26,"Memory", "event","n/a");
INSERT INTO Description(descriptionId,dataType,descriptionName,unit) VALUES (27, "cpu", "measurement", "m");
INSERT INTO Description(descriptionId,dataType,descriptionName,unit) VALUES (28, "memory", "measurement", "Ki");
INSERT INTO Description(descriptionId,dataType,descriptionName,unit) VALUES (29, "Mean_Response_Ti", "measurement","s");
INSERT INTO Description(descriptionId,dataType,descriptionName,unit) VALUES (30, "Throughput", "measurement","req/s");
INSERT INTO Description(descriptionId,dataType,descriptionName,unit) VALUES (31, "STD_Response_Tim", "measurement","s");
INSERT INTO Description(descriptionId,dataType,descriptionName,unit) VALUES (32, "AVG_Throughput", "measurement","req/s");

-- DESCRIPTION_ID_DEPLOYMENT_CHANGE
INSERT INTO Description(descriptionId,dataType,descriptionName,unit) VALUES (33, "event", "Deployment Change", "n/a");
-- DESCRIPTION_ID_ALLOCATED_CPUs
INSERT INTO Description(descriptionId,dataType,descriptionName,unit) VALUES (34, "measurement", "Allocated CPUs", "number");
-- DESCRIPTION_ID_NUMBER_AVAILABLE_CPUs
INSERT INTO Description(descriptionId,dataType,descriptionName,unit) VALUES (35, "measurement", "Available CPUs", "number");

INSERT INTO Description(descriptionId,dataType,descriptionName,unit) VALUES (36, "measurement", "Node CPU", "%");
INSERT INTO Description(descriptionId,dataType,descriptionName,unit) VALUES (37, "measurement", "Node Memory", "%");
INSERT INTO Description(descriptionId,dataType,descriptionName,unit) VALUES (38, "measurement", "Node Disk I/O", "%");

INSERT INTO Description(descriptionId,dataType,descriptionName,unit) VALUES (39, "measurement", "POD CPU", "%");
INSERT INTO Description(descriptionId,dataType,descriptionName,unit) VALUES (40, "measurement", "POD Memory", "%");
INSERT INTO Description(descriptionId,dataType,descriptionName,unit) VALUES (41, "measurement", "POD Disk I/O", "%");

INSERT INTO Description(descriptionId,dataType,descriptionName,unit) VALUES (42, "measurement", "Received Requests", "count");
INSERT INTO Description(descriptionId,dataType,descriptionName,unit) VALUES (43, "measurement", "Failed Requests", "count");
INSERT INTO Description(descriptionId,dataType,descriptionName,unit) VALUES (44, "measurement", "Released Resources", "count");
INSERT INTO Description(descriptionId,dataType,descriptionName,unit) VALUES (45, "measurement", "Failed Release Resources", "count");
INSERT INTO Description(descriptionId,dataType,descriptionName,unit) VALUES (46, "measurement", "Service Latency", "ms");

INSERT INTO Description(descriptionId,dataType,descriptionName,unit) VALUES (47, "measurement", "Equal Parity", "%");
INSERT INTO Description(descriptionId,dataType,descriptionName,unit) VALUES (48, "measurement", "Proportional Parity", "%");
INSERT INTO Description(descriptionId,dataType,descriptionName,unit) VALUES (49, "measurement", "False Positive Rate", "%");
INSERT INTO Description(descriptionId,dataType,descriptionName,unit) VALUES (50, "measurement", "False Negative Rate", "%");
INSERT INTO Description(descriptionId,dataType,descriptionName,unit) VALUES (51, "measurement", "False Discovery Rate", "%");
INSERT INTO Description(descriptionId,dataType,descriptionName,unit) VALUES (52, "measurement", "False Omission Rate", "%");

INSERT INTO Description(descriptionId,dataType,descriptionName,unit) VALUES (53, "measurement", "Evaluator Consensus", "%");
INSERT INTO Description(descriptionId,dataType,descriptionName,unit) VALUES (54, "measurement", "Concept Drift", "%");

INSERT INTO Description(descriptionId,dataType,descriptionName,unit) VALUES (55, "measurement", "User Evaluation Transparency", "%");

INSERT INTO Description(descriptionId,dataType,descriptionName,unit) VALUES (56, "measurement", "Query Latency", "%");
INSERT INTO Description(descriptionId,dataType,descriptionName,unit) VALUES (57, "measurement", "Proportion Running Time", "%");


INSERT INTO Resource(resourceId,resourceName,resourceType,resourceAddress) VALUES (1,"VM_VIRT_NODE", "VM","n/a");
INSERT INTO Resource(resourceId,resourceName,resourceType,resourceAddress) VALUES (2,"Pod Apache Flume", "POD","n/a");
INSERT INTO Resource(resourceId,resourceName,resourceType,resourceAddress) VALUES (3,"VM_VIRT_MASTER", "VM","n/a");
INSERT INTO Resource(resourceId,resourceName,resourceType,resourceAddress) VALUES (4,"Pod MySQL", "POD","n/a");
INSERT INTO Resource(resourceId,resourceName,resourceType,resourceAddress) VALUES (5,"Pod Apache Zookeeper", "POD","n/a");
INSERT INTO Resource(resourceId,resourceName,resourceType,resourceAddress) VALUES (6,"Pod Apache Kafka", "POD","n/a");
INSERT INTO Resource(resourceId,resourceName,resourceType,resourceAddress) VALUES (7,"Pod Monitor", "POD","n/a");
INSERT INTO Resource(resourceId,resourceName,resourceType,resourceAddress) VALUES (8,"Apache Kafka", "POD","n/a");
