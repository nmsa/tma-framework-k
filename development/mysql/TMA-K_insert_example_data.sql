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

-- Probes
INSERT INTO Probe(probeId, probeName, salt) VALUES 
(10001, 'Hot_resource_scalability', ''), 
(10002, 'Resource_scalability', ''), 
(10003, 'Service_availability', ''), 
(10004, 'Service_performance', ''), 
(15001, 'Fogbow Service Reachability Probe', ''), 
(15002, 'Fogbow Resource Availibity Probe', ''), 
(15003, 'Fogbow Service Success Rate Probe', ''), 
(15004, 'Fogbow Service Latency Probe', ''), 
(25001, 'LEMONADE Workflow Probe', ''), 
(30004, 'Probe Demo Python', ''), 
(30005, 'Probe Docker', ''), 
(30006, 'Probe Kubernetes', ''), 
(30007, 'Probe k8s-api', ''), 
(30008, 'probe-client', ''), 
(30009, 'Probe-Jmeter', ''), 
(30010, 'Probe Design-Time', ''), 
(30011, 'Probe JMX', ''), 
(40001, 'Distributed Network Probe', ''), 
(45001, 'Probe Vallum', ''), 
(35001, 'Probe Privacy', ''), 
(80001, 'Probe CloudEA', ''); 

-- Description
INSERT INTO Description(descriptionId, dataType, descriptionName, unit) VALUES 
(10001, 'measurement', 'used_cpu', '%'), 
(10002, 'measurement', 'free_cpu', '%'), 
(10003, 'measurement', 'used_memory', '%'), 
(10004, 'measurement', 'free_memory', '%'), 
(10005, 'measurement', 'total_cpu', 'number'), 
(10006, 'measurement', 'total_memory', 'byte'), 
(10007, 'measurement', 'clues availabilty', '%'), 
(10008, 'measurement', 'deployment Variation', '%'), 
(15001, 'measurement', ' Reachability', '%'), 
(15002, 'measurement', 'Resource Availibity', 'number'), 
(15003, 'measurement', 'Success Rate', 'number'), 
(15004, 'measurement', 'Latency', 'ms'), 
(25001, 'measurement', 'FNRP - False-Negative Rate Parity', 'number'), 
(25002, 'measurement', 'FPRP - False-Positive Rate Parity', 'number'), 
(25003, 'measurement', 'FDRP - False-Discovery Rate Parity', 'number'), 
(25004, 'measurement', 'FORP - False Omission Rate Parity', 'number'), 
(25005, 'measurement', 'EqParity - Equal Parity', 'number'), 
(25006, 'measurement', 'PropParity - Proportional Parity', 'number'), 
(25007, 'measurement', 'EvalConsensus - Evaluators consensus', 'number'), 
(25008, 'measurement', 'ConceptDrift', 'number'), 
(25009, 'measurement', 'Accuracy', 'number'), 
(25010, 'measurement', 'EvalTransparency - Evaluators transparency', 'number'), 
(25011, 'measurement', 'ReIdentificationRisk', 'number'), 
(25012, 'measurement', 'ExpectedExecTime - Expected execution time', 'seconds'), 
(25013, 'measurement', 'ExecTime - Execution time (real)', 'seconds'), 
(30041, 'measurement', 'POD Disk I/O', '%'), 
(30042, 'measurement', 'Received Requests', 'count'), 
(30043, 'measurement', 'Failed Requests', 'count'), 
(30044, 'measurement', 'Released Resources', 'count'), 
(30045, 'measurement', 'Failed Release Resources', 'count'), 
(30046, 'measurement', 'Service Latency', 'ms'), 
(30047, 'measurement', 'Equal Party', '%'), 
(30048, 'measurement', 'Proportional Parity', '%'), 
(30049, 'measurement', 'False Positive Rate', '%'), 
(30050, 'measurement', 'False Negative Rate', '%'), 
(30051, 'measurement', 'False Discovery Rate', '%'), 
(30052, 'measurement', 'False Omission Rate', '%'), 
(30053, 'measurement', 'Evaluator Consensus', '%'), 
(30054, 'measurement', 'Concept Drift', '%'), 
(30055, 'measurement', 'User Evaluation Transparency', '%'), 
(30056, 'measurement', 'Query Latency', '%'), 
(30057, 'measurement', 'Proportion Running Time', '%'), 
(30058, 'measurement', 'Demand', 'requests'), 
(30059, 'measurement', 'Rate Requests Under Contracted', '%'), 
(30060, 'measurement', 'CountInput_Function', 'count'), 
(30061, 'measurement', 'CountLine_Function', 'count'), 
(30062, 'measurement', 'CountLineBlank_Function', 'count'), 
(30063, 'measurement', 'CountLineCode_Function', 'count'), 
(30064, 'measurement', 'CountLineCodeDecl_Function', 'count'), 
(30065, 'measurement', 'CountLineCodeExe_Function', 'count'), 
(30066, 'measurement', 'CountLineComment_Function', 'count'), 
(30067, 'measurement', 'CountOutput_Function', 'count'), 
(30068, 'measurement', 'CountOutput_Function', 'count'), 
(30069, 'measurement', 'CountSemicolon_Function', 'count'), 
(30070, 'measurement', 'CountStmt_Function', 'count'), 
(30071, 'measurement', 'CountStmtDecl_Function', 'count'), 
(30072, 'measurement', 'CountStmtExe_Function', 'count'), 
(30073, 'measurement', 'Cyclomatic_Function', 'count'), 
(30074, 'measurement', 'CyclomaticModified_Function', 'count'), 
(30075, 'measurement', 'CyclomaticStrict_Function', 'count'), 
(30076, 'measurement', 'Essential_Function', 'count'), 
(30077, 'measurement', 'Knots_Function', 'count'), 
(30078, 'measurement', 'MaxEssentialKnots_Function', 'count'), 
(30079, 'measurement', 'MaxNesting_Function', 'count'), 
(30080, 'measurement', 'MinEssentialKnots_Function', 'count'), 
(30081, 'measurement', 'RatioCommentToCode_Function', 'ratio'), 
(40001, 'measurement', 'virtual link availability', '%'), 
(40002, 'measurement', 'virtual link latency', 'milisenconds'), 
(45001, 'measurement', 'Response Time Maria DB', 'seconds'), 
(45002, 'measurement', 'Response Time HDFS', 'seconds'), 
(45003, 'measurement', 'Throughput Maria DB', 'query/s'), 
(45004, 'measurement', 'Throughput HDFS', 'query/s'), 
(45005, 'measurement', 'Bandwidth', 'MB/s'), 
(45006, 'measurement', 'Re-indentification Risk', '%'), 
(45007, 'measurement', 'Information Loss', '%'), 
(80001, 'measurement', 'Existence of Best Practice', 'binary'), 
(80002, 'measurement', 'Existence of Security Definition', 'binary'), 
(80003, 'measurement', 'Existence of Check Area', 'binary'), 
(80004, 'measurement', 'Existence of Policy', 'binary'), 
(80005, 'measurement', 'Existence of Security Control', 'binary');

-- Resources
INSERT INTO Resource(resourceId, resourceName, resourceType, resourceAddress) VALUES 
(10001, 'upv-cloud.i3m.upv.es', 'Compute', 'n/a'), 
(10002, 'upv-cloud.i3m.upv.es', 'Volume', 'n/a'), 
(10003, 'upv-cloud.i3m.upv.es', 'Network', 'n/a'), 
(10004, 'Cluster', 'System', 'n/a'), 
(10005, 'CLUES', 'Service', 'http://localhost:8000/reports/cluesdata.json?secret=not_very_secret_token'), 
(15001, 'atm-prod.lsd.ufcg.edu.br', 'Compute', 'n/a'), 
(15002, 'atm-prod.lsd.ufcg.edu.br', 'Volume', 'n/a'), 
(15003, 'atm-prod.lsd.ufcg.edu.br', 'Network', 'n/a'), 
(15004, 'atm-prod.lsd.ufcg.edu.br', 'Site', 'n/a'), 
(25001, 'Workflow 1', 'workflow', 'n/a'), 
(25002, 'Workflow 2', 'workflow', 'n/a'), 
(25003, 'Workflow 3', 'workflow', 'n/a'), 
(25004, 'Workflow 4', 'workflow', 'n/a'), 
(25005, 'Workflow 5', 'workflow', 'n/a'), 
(25006, 'Workflow 6', 'workflow', 'n/a'), 
(25007, 'Workflow 7', 'workflow', 'n/a'), 
(25008, 'Workflow 8', 'workflow', 'n/a'), 
(25009, 'Workflow 9', 'workflow', 'n/a'), 
(25010, 'Workflow 10', 'workflow', 'n/a'), 
(25011, 'Workflow 11', 'workflow', 'n/a'), 
(25012, 'Workflow 12', 'workflow', 'n/a'), 
(25013, 'Workflow 13', 'workflow', 'n/a'), 
(25014, 'Workflow 14', 'workflow', 'n/a'), 
(25015, 'Workflow 15', 'workflow', 'n/a'), 
(25016, 'Workflow 16', 'workflow', 'n/a'), 
(25017, 'Workflow 17', 'workflow', 'n/a'), 
(25018, 'Workflow 18', 'workflow', 'n/a'), 
(25019, 'Workflow 19', 'workflow', 'n/a'), 
(25020, 'Workflow 20', 'workflow', 'n/a'), 
(25021, 'Workflow 21', 'workflow', 'n/a'), 
(25022, 'Workflow 22', 'workflow', 'n/a'), 
(25023, 'Workflow 23', 'workflow', 'n/a'), 
(25024, 'Workflow 24', 'workflow', 'n/a'), 
(25025, 'Workflow 25', 'workflow', 'n/a'), 
(25026, 'Workflow 26', 'workflow', 'n/a'), 
(25027, 'Workflow 27', 'workflow', 'n/a'), 
(25028, 'Workflow 28', 'workflow', 'n/a'), 
(25029, 'Workflow 29', 'workflow', 'n/a'), 
(25030, 'Workflow 30', 'workflow', 'n/a'), 
(25031, 'Workflow 31', 'workflow', 'n/a'), 
(25032, 'Workflow 32', 'workflow', 'n/a'), 
(25033, 'Workflow 33', 'workflow', 'n/a'), 
(25034, 'Workflow 34', 'workflow', 'n/a'), 
(25035, 'Workflow 35', 'workflow', 'n/a'), 
(25036, 'Workflow 36', 'workflow', 'n/a'), 
(25037, 'Workflow 37', 'workflow', 'n/a'), 
(25038, 'Workflow 38', 'workflow', 'n/a'), 
(25039, 'Workflow 39', 'workflow', 'n/a'), 
(25040, 'Workflow 40', 'workflow', 'n/a'), 
(25041, 'Workflow 41', 'workflow', 'n/a'), 
(25042, 'Workflow 42', 'workflow', 'n/a'), 
(25043, 'Workflow 43', 'workflow', 'n/a'), 
(25044, 'Workflow 44', 'workflow', 'n/a'), 
(25045, 'Workflow 45', 'workflow', 'n/a'), 
(25046, 'Workflow 46', 'workflow', 'n/a'), 
(25047, 'Workflow 47', 'workflow', 'n/a'), 
(25048, 'Workflow 48', 'workflow', 'n/a'), 
(25049, 'Workflow 49', 'workflow', 'n/a'), 
(25050, 'Workflow 50', 'workflow', 'n/a'), 
(40035, 'virtual link-035', 'link', 'n/a'), 
(40036, 'virtual link-036', 'link', 'n/a'), 
(40037, 'virtual link-037', 'link', 'n/a'), 
(40038, 'virtual link-038', 'link', 'n/a'), 
(40039, 'virtual link-039', 'link', 'n/a'), 
(40040, 'virtual link-040', 'link', 'n/a'), 
(40041, 'virtual link-041', 'link', 'n/a'), 
(40042, 'virtual link-042', 'link', 'n/a'), 
(40043, 'virtual link-043', 'link', 'n/a'), 
(40044, 'virtual link-044', 'link', 'n/a'), 
(40045, 'virtual link-045', 'link', 'n/a'), 
(40046, 'virtual link-046', 'link', 'n/a'), 
(40047, 'virtual link-047', 'link', 'n/a'), 
(40048, 'virtual link-048', 'link', 'n/a'), 
(40049, 'virtual link-049', 'link', 'n/a'), 
(40050, 'virtual link-050', 'link', 'n/a'), 
(40051, 'virtual link-051', 'link', 'n/a'), 
(40052, 'virtual link-052', 'link', 'n/a'), 
(40053, 'virtual link-053', 'link', 'n/a'), 
(40054, 'virtual link-054', 'link', 'n/a'), 
(40055, 'virtual link-055', 'link', 'n/a'), 
(40056, 'virtual link-056', 'link', 'n/a'), 
(40057, 'virtual link-057', 'link', 'n/a'), 
(40058, 'virtual link-058', 'link', 'n/a'), 
(40059, 'virtual link-059', 'link', 'n/a'), 
(40060, 'virtual link-060', 'link', 'n/a'), 
(40061, 'virtual link-061', 'link', 'n/a'), 
(40062, 'virtual link-062', 'link', 'n/a'), 
(40063, 'virtual link-063', 'link', 'n/a'), 
(40064, 'virtual link-064', 'link', 'n/a'), 
(40065, 'virtual link-065', 'link', 'n/a'), 
(40066, 'virtual link-066', 'link', 'n/a'), 
(40067, 'virtual link-067', 'link', 'n/a'), 
(40068, 'virtual link-068', 'link', 'n/a'), 
(40069, 'virtual link-069', 'link', 'n/a'), 
(40070, 'virtual link-070', 'link', 'n/a'), 
(40071, 'virtual link-071', 'link', 'n/a'), 
(40072, 'virtual link-072', 'link', 'n/a'), 
(40073, 'virtual link-073', 'link', 'n/a'), 
(40074, 'virtual link-074', 'link', 'n/a'), 
(40075, 'virtual link-075', 'link', 'n/a'), 
(40076, 'virtual link-076', 'link', 'n/a'), 
(40077, 'virtual link-077', 'link', 'n/a'), 
(40078, 'virtual link-078', 'link', 'n/a'), 
(40079, 'virtual link-079', 'link', 'n/a'), 
(40080, 'virtual link-080', 'link', 'n/a'), 
(40081, 'virtual link-081', 'link', 'n/a'), 
(40082, 'virtual link-082', 'link', 'n/a'), 
(40083, 'virtual link-083', 'link', 'n/a'), 
(40084, 'virtual link-084', 'link', 'n/a'), 
(40085, 'virtual link-085', 'link', 'n/a'), 
(40086, 'virtual link-086', 'link', 'n/a'), 
(40087, 'virtual link-087', 'link', 'n/a'), 
(40088, 'virtual link-088', 'link', 'n/a'), 
(40089, 'virtual link-089', 'link', 'n/a'), 
(40090, 'virtual link-090', 'link', 'n/a'), 
(40091, 'virtual link-091', 'link', 'n/a'), 
(40092, 'virtual link-092', 'link', 'n/a'), 
(40093, 'virtual link-093', 'link', 'n/a'), 
(40094, 'virtual link-094', 'link', 'n/a'), 
(40095, 'virtual link-095', 'link', 'n/a'), 
(40096, 'virtual link-096', 'link', 'n/a'), 
(40097, 'virtual link-097', 'link', 'n/a'), 
(40098, 'virtual link-098', 'link', 'n/a'), 
(40099, 'virtual link-099', 'link', 'n/a'), 
(40100, 'virtual link-100', 'link', 'n/a'), 
(40101, 'virtual link-101', 'link', 'n/a'), 
(40102, 'virtual link-102', 'link', 'n/a'), 
(40103, 'virtual link-103', 'link', 'n/a'), 
(40104, 'virtual link-104', 'link', 'n/a'), 
(40105, 'virtual link-105', 'link', 'n/a'), 
(40106, 'virtual link-106', 'link', 'n/a'), 
(40107, 'virtual link-107', 'link', 'n/a'), 
(40108, 'virtual link-108', 'link', 'n/a'), 
(40109, 'virtual link-109', 'link', 'n/a'), 
(40110, 'virtual link-110', 'link', 'n/a'), 
(40111, 'virtual link-111', 'link', 'n/a'), 
(40112, 'virtual link-112', 'link', 'n/a'), 
(40113, 'virtual link-113', 'link', 'n/a'), 
(40114, 'virtual link-114', 'link', 'n/a'), 
(40115, 'virtual link-115', 'link', 'n/a'), 
(40116, 'virtual link-116', 'link', 'n/a'), 
(40117, 'virtual link-117', 'link', 'n/a'), 
(40118, 'virtual link-118', 'link', 'n/a'), 
(40119, 'virtual link-119', 'link', 'n/a'), 
(40120, 'virtual link-120', 'link', 'n/a'), 
(40121, 'virtual link-121', 'link', 'n/a'), 
(40122, 'virtual link-122', 'link', 'n/a'), 
(40123, 'virtual link-123', 'link', 'n/a'), 
(40124, 'virtual link-124', 'link', 'n/a'), 
(40125, 'virtual link-125', 'link', 'n/a'), 
(40126, 'virtual link-126', 'link', 'n/a'), 
(40127, 'virtual link-127', 'link', 'n/a'), 
(40128, 'virtual link-128', 'link', 'n/a'), 
(40129, 'virtual link-129', 'link', 'n/a'), 
(40130, 'virtual link-130', 'link', 'n/a'), 
(40131, 'virtual link-131', 'link', 'n/a'), 
(40132, 'virtual link-132', 'link', 'n/a'), 
(40133, 'virtual link-133', 'link', 'n/a'), 
(40134, 'virtual link-134', 'link', 'n/a'), 
(40135, 'virtual link-135', 'link', 'n/a'), 
(40136, 'virtual link-136', 'link', 'n/a'), 
(40137, 'virtual link-137', 'link', 'n/a'), 
(40138, 'virtual link-138', 'link', 'n/a'), 
(40139, 'virtual link-139', 'link', 'n/a'), 
(40140, 'virtual link-140', 'link', 'n/a'), 
(40141, 'virtual link-141', 'link', 'n/a'), 
(40142, 'virtual link-142', 'link', 'n/a'), 
(40143, 'virtual link-143', 'link', 'n/a'), 
(40144, 'virtual link-144', 'link', 'n/a'), 
(40145, 'virtual link-145', 'link', 'n/a'), 
(40146, 'virtual link-146', 'link', 'n/a'), 
(40147, 'virtual link-147', 'link', 'n/a'), 
(40148, 'virtual link-148', 'link', 'n/a'), 
(40149, 'virtual link-149', 'link', 'n/a'), 
(40150, 'virtual link-150', 'link', 'n/a'), 
(40151, 'virtual link-151', 'link', 'n/a'), 
(40152, 'virtual link-152', 'link', 'n/a'), 
(40153, 'virtual link-153', 'link', 'n/a'), 
(40154, 'virtual link-154', 'link', 'n/a'), 
(40155, 'virtual link-155', 'link', 'n/a'), 
(40156, 'virtual link-156', 'link', 'n/a'), 
(40157, 'virtual link-157', 'link', 'n/a'), 
(40158, 'virtual link-158', 'link', 'n/a'), 
(40159, 'virtual link-159', 'link', 'n/a'), 
(40160, 'virtual link-160', 'link', 'n/a'), 
(40161, 'virtual link-161', 'link', 'n/a'), 
(40162, 'virtual link-162', 'link', 'n/a'), 
(40163, 'virtual link-163', 'link', 'n/a'), 
(40164, 'virtual link-164', 'link', 'n/a'), 
(40165, 'virtual link-165', 'link', 'n/a'), 
(40166, 'virtual link-166', 'link', 'n/a'), 
(40167, 'virtual link-167', 'link', 'n/a'), 
(40168, 'virtual link-168', 'link', 'n/a'), 
(40169, 'virtual link-169', 'link', 'n/a'), 
(40170, 'virtual link-170', 'link', 'n/a'), 
(40171, 'virtual link-171', 'link', 'n/a'), 
(40172, 'virtual link-172', 'link', 'n/a'), 
(40173, 'virtual link-173', 'link', 'n/a'), 
(40174, 'virtual link-174', 'link', 'n/a'), 
(40175, 'virtual link-175', 'link', 'n/a'), 
(40176, 'virtual link-176', 'link', 'n/a'), 
(40177, 'virtual link-177', 'link', 'n/a'), 
(40178, 'virtual link-178', 'link', 'n/a'), 
(40179, 'virtual link-179', 'link', 'n/a'), 
(40180, 'virtual link-180', 'link', 'n/a'), 
(40181, 'virtual link-181', 'link', 'n/a'), 
(40182, 'virtual link-182', 'link', 'n/a'), 
(40183, 'virtual link-183', 'link', 'n/a'), 
(40184, 'virtual link-184', 'link', 'n/a'), 
(40185, 'virtual link-185', 'link', 'n/a'), 
(40186, 'virtual link-186', 'link', 'n/a'), 
(40187, 'virtual link-187', 'link', 'n/a'), 
(40188, 'virtual link-188', 'link', 'n/a'), 
(40189, 'virtual link-189', 'link', 'n/a'), 
(40190, 'virtual link-190', 'link', 'n/a'), 
(40191, 'virtual link-191', 'link', 'n/a'), 
(40192, 'virtual link-192', 'link', 'n/a'), 
(40193, 'virtual link-193', 'link', 'n/a'), 
(40194, 'virtual link-194', 'link', 'n/a'), 
(40195, 'virtual link-195', 'link', 'n/a'), 
(40196, 'virtual link-196', 'link', 'n/a'), 
(40197, 'virtual link-197', 'link', 'n/a'), 
(40198, 'virtual link-198', 'link', 'n/a'), 
(40199, 'virtual link-199', 'link', 'n/a'), 
(40200, 'virtual link-200', 'link', 'n/a'), 
(40201, 'virtual link-201', 'link', 'n/a'), 
(40202, 'virtual link-202', 'link', 'n/a'), 
(40203, 'virtual link-203', 'link', 'n/a'), 
(40204, 'virtual link-204', 'link', 'n/a'), 
(40205, 'virtual link-205', 'link', 'n/a'), 
(40206, 'virtual link-206', 'link', 'n/a'), 
(40207, 'virtual link-207', 'link', 'n/a'), 
(40208, 'virtual link-208', 'link', 'n/a'), 
(40209, 'virtual link-209', 'link', 'n/a'), 
(40210, 'virtual link-210', 'link', 'n/a'), 
(40211, 'virtual link-211', 'link', 'n/a'), 
(40212, 'virtual link-212', 'link', 'n/a'), 
(40213, 'virtual link-213', 'link', 'n/a'), 
(40214, 'virtual link-214', 'link', 'n/a'), 
(40215, 'virtual link-215', 'link', 'n/a'), 
(40216, 'virtual link-216', 'link', 'n/a'), 
(40217, 'virtual link-217', 'link', 'n/a'), 
(40218, 'virtual link-218', 'link', 'n/a'), 
(40219, 'virtual link-219', 'link', 'n/a'), 
(40220, 'virtual link-220', 'link', 'n/a'), 
(40221, 'virtual link-221', 'link', 'n/a'), 
(40222, 'virtual link-222', 'link', 'n/a'), 
(40223, 'virtual link-223', 'link', 'n/a'), 
(40224, 'virtual link-224', 'link', 'n/a'), 
(40225, 'virtual link-225', 'link', 'n/a'), 
(40226, 'virtual link-226', 'link', 'n/a'), 
(40227, 'virtual link-227', 'link', 'n/a'), 
(40228, 'virtual link-228', 'link', 'n/a'), 
(40229, 'virtual link-229', 'link', 'n/a'), 
(40230, 'virtual link-230', 'link', 'n/a'), 
(40231, 'virtual link-231', 'link', 'n/a'), 
(40232, 'virtual link-232', 'link', 'n/a'), 
(40233, 'virtual link-233', 'link', 'n/a'), 
(40234, 'virtual link-234', 'link', 'n/a'), 
(40235, 'virtual link-235', 'link', 'n/a'), 
(40236, 'virtual link-236', 'link', 'n/a'), 
(40237, 'virtual link-237', 'link', 'n/a'), 
(40238, 'virtual link-238', 'link', 'n/a'), 
(40239, 'virtual link-239', 'link', 'n/a'), 
(40240, 'virtual link-240', 'link', 'n/a'), 
(45001, 'MariaDB', 'DBMS', 'n/a'), 
(45002, 'HDFS', 'Storage', 'n/a'), 
(80001, 'CloudEA', 'Cloud', 'n/a'), 
(80101, 'Clam', 'n/a', 'n/a'), 
(80102, 'McAfee', 'n/a', 'n/a'), 
(80103, 'TrendMicro', 'n/a', 'n/a'), 
(80104, 'CheckPoint', 'n/a', 'n/a'), 
(80105, 'McAfee TIE', 'n/a', 'n/a'), 
(80106, 'McAfee AR', 'n/a', 'n/a'), 
(80111, 'Policy SPP-A', 'n/a', 'n/a'), 
(80112, 'Policy SPP-B', 'n/a', 'n/a'), 
(80113, 'Policy SPP-C', 'n/a', 'n/a'), 
(80114, 'Policy SPP-D', 'n/a', 'n/a'), 
(80115, 'Policy SPP-E', 'n/a', 'n/a'), 
(80121, 'Check Area SS-1', 'n/a', 'n/a'), 
(80122, 'Check Area SS-2', 'n/a', 'n/a'), 
(80123, 'Check Area SS-3', 'n/a', 'n/a'), 
(80124, 'Check Area SS-4', 'n/a', 'n/a'), 
(80125, 'Check Area SS-5', 'n/a', 'n/a'), 
(80126, 'Check Area SS-6', 'n/a', 'n/a'), 
(80131, 'Applied Security Definition SICAD-1', 'n/a', 'n/a'), 
(80132, 'Applied Security Definition SICAD-2', 'n/a', 'n/a'), 
(80133, 'Applied Security Definition SICAD-3', 'n/a', 'n/a'), 
(80134, 'Applied Security Definition SICAD-4', 'n/a', 'n/a'), 
(80141, 'Applied Security Definition VBPAD-1', 'n/a', 'n/a'), 
(80142, 'Applied Security Definition VBPAD-2', 'n/a', 'n/a'), 
(80143, 'Applied Security Definition VBPAD-3', 'n/a', 'n/a'), 
(80144, 'Applied Security Definition VBPAD-4', 'n/a', 'n/a');

-- Actuators
INSERT INTO Actuator(actuatorId, address) VALUES 
(30001, 'http://10.3.2.148:8080/k8sActuator'), 
(15001, 'http://10.3.2.148:8080/mailActuator'), 
(25001, 'https://atm.lemonade.org.br/seed/actuators'), 
(35001, 'http://localhost:8080/kAnonymity'); 

-- Metrics
INSERT INTO Metric(metricId, metricName, blockLevel) VALUES 
(25001, 'Expected versus real execution time evaluation', -1), 
(80001, 'Compliance for Each Policy (VBPP)', '-1'), 
(80002, 'Compliance with Vendor Best Practices (VBP)', '-1'), 
(80003, 'Compliance for Each Policy (SICP)', '-1'), 
(80004, 'Compliance with Industry Defined Configuration (SIC)', '-1'), 
(80005, 'Compliance with each Security Standard (CSS)', '-1'), 
(80006, 'Compliance with all Security Standard (SS)', '-1'), 
(80007, 'Policies in Place for each Technology (P)', '-1'), 
(80008, 'Total Security Policies in Place (SP)', '-1'), 
(80009, 'Security Coverage of each Technology (SC)', '-1'), 
(80010, 'Total Security Coverage', '-1'), 
(80011, 'Availability Level (A)', '-1'), 
(80012, 'Availability Level (A)', '-1'), 
(80013, 'Confidentiality Level (C)', '-1'), 
(80014, 'Security (Thrustworthiness) Level', '-1');
