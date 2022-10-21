
# TMA Admin Rest API

This tool allows you to:

-   Generate public-private key pair to be used in the encryption process;
-   Get the partners;
-   Add and delete an action and get actions;
-   Add a new actuator and get actuators;
-   Add and delete a configuration and get configurations;
-   Add a new description;
-   Add and delete a probe and get probes;
-   Add a new resource and get resources;
-   Configure Actions (check details on  [Message Format for Actions Registration](https://github.com/eubr-atmosphere/tma-framework-k/tree/master#message-format-for-actions-registration)).
-   Get the scores, associated to a metric, with a valueTime after the given timestamp.


# Index

-	[Installation](#Installation)
-	[Execution](#Execution)
-	[Properties](#Properties)
-	[API Calls](#API-Calls)
	*	[Introduction](#Introduction)
	*	[Partners](#Partners)
		+ 	[Get Partners](#Get-Partners)
	*	[Keys](#Keys)
		+ 	[Generate a new Public - Private key pair](#Generate-a-new-Public---Private-key-pair)
	*	[Actions](#Actions)
		+	[Add a new Action](#Add-a-new-Action)
		+	[Get Actions](#Get-Actions)
		+	[Delete Action](#Delete-Action)
	*	[Actuators](#Actuators)
		+	[Add a new Actuator](#Add-a-new-Actuator)
		+	[Get Actuators](#Get-Actuators)
	*	[Adaptation Rules](#Adaptation-Rules)
		+	[Add a new Rule](#Add-a-new-Rule)
		+	[Get Rules](#Get-Rules)
		+	[Get a Rule](#Get-Rule)
		+	[Delete Rule](#Delete-Rule)
	*	[Configurations](#Configurations)
		+	[Add a new Configuration](#Add-a-new-Configuration)
		+	[Get Configurations](#Get-Configurations)
		+	[Delete Configuration](#Delete-Configuration)
	*	[Descriptions](#Descriptions)
		+	[Add a new Description](#Add-a-new-Description)
		+	[Get Descriptions](#Get-Descriptions)
	*	[Metrics](#Metrics)
		+	[Add a new Metric](#Add-a-new-Metric)
		+	[Get Metrics](#Get-Metrics)
		+	[Get a Metric](#Get-a-Metric)
	*	[Plot Config](#Plot-Config)
		+	[Add a new Plot Config](#Add-a-new-Plot-Config)
		+	[Get Plot Configs](#Get-Plot-Configs)
		+	[Replace a Plot Config](#Replace-a-Plot-Config)
		+	[Delete Plot Config](#Delete-Plot-Config)
	*	[Probes](#Probes)
		+	[Add a new Probe](#Add-a-new-Probe)
		+   [Get Probes](#Get-Probes)
		+   [Delete Probe](#Delete-Probe)
	*	[Quality Models](#Quality-Models)
		+	[Add a new Quality Model](#Add-a-new-Quality-Model)
		+	[Add a new Configuration Profile](#Add-a-new-Configuration-Profile)
		+	[Get Quality Models](#Get-Quality-Models)
		+	[Get a Quality Model](#Get-a-Quality-Model)
		+	[Get a Configuration Profile](#Get-Configuration-Profile)
		+	[Get Metrics from a Configuration Profile](#Get-Metrics-from-a-Configuration-Profile)
	*	[Resources](#Resources)
		+	[Add a new Resource](#Add-a-new-Resource)
		+	[Get Resources](#Get-Resources)
		+	[Get Resources by Actuator](#Get-Resources-by-Actuator)
		+	[Get associated Quality Model and Configuration Profile](#Get-associated-Quality-Model-and-Configuration-Profile)
		+	[Get Data](#Get-Data)
		+	[Simulate Data](#Simulate-Data)
	*	[Configure Actions](#Configure-Actions)
	*	[Get Scores](#Get-Scores)
 -	[GUI](#GUI)
 -	[Implementation Details](#Implementation-Details)


# Installation

To build the container, you should run the following command on the Worker node:

```
sh build.sh
```

To deploy the pod in the cluster, you should run the following command on the master node:

```
kubectl create -f tma-admin-api.yaml
```

With TMA Admin correctly deployed and running, it is accessible through the IP of Kubernetes Master machine in port 32026. 

# Execution

There is an example on how to execute each of the features refered in the beggining in the corresponding section bellow.

# Properties

Properties are specified in the following directory:
```
...\tma-admin-api\src\main\resources\application.properties
```


# API Calls

## Introduction
	
Every call has the same base documentation.

#### Method - HTTP Method
URI:
```
URI here
```

Model:
Parameters - every necessary parameter name will be here
Body - every variable that goes inside the body will be here
```
curl command with variable names
```

Example:

```
curl command with examples instead of variable names
```

### Input

A short explanation of the input, normally with a table like this:

|Key|Type|Description|Example|
|--|--|--|--|
| | | |
| | | |

### Success numbers & Error numbers

The numbers will be the possible status code the can be returned.

In this API, 201 is the only status code associated with a sucessfull call.

When the call is unsucessfull however, the status code can either be 400, in case something is wrong with the input syntax, 415, in case something is wrong with the input type and 500 in case something is wrong with the server (Database error).

If the error is 404, it means the URI is not associated with any API Call.
If the error is 405, it means the HTTP Method is not allowed to the URI that the call was made.
If the error is -1, it means there was no status code.

Every API Call will return a json with this base configuration:

|Key|Type|Value description|
|--|--|--|
|message|String|Message about the status of the call|
|status|String|Status of the HTTP Request|
- - -

## Partners

### Get Partners

#### Method - GET

URI:

```
http://IP_MASTER:32026/getpartners
```

Model:
```
curl -X GET http://IP_MASTER:32026/getpartners
```

Example:

```
curl -X GET http://IP_MASTER:32026/getpartners
```


### Success 200

If the call is sucessfull the status code will be 200.

|Key|Type|Value description|
|--|--|--|
|partnerList|HashMap|Partner hashmap|
- - -

PartnerList is an HashMap with the partner name as a key and the id as the value.

Example:
|Key|Value|
|--|--|--|
|UPV|10|
- - -



## Keys

### Generate a new Public - Private key pair

#### Method - GET

URI:

```
http://IP_MASTER:32026/generatekeys
```

Model:
```
curl -X GET http://IP_MASTER:32026/generatekeys --output fileDirectory
```

Example:

```
curl -X GET http://IP_MASTER:32026/generatekeys --output /home/user/file.zip
```

### Input

The file will be the directory where you want your file to be saved. For example home/user/file.zip


### Success 201

If the call is sucessfull the status code will be 201 which means the keys were created. You will have a zip file in the directory you specified. If you unzip it, you will get 2 files, one called publicKey and another called privateKey, which hold the public key and the private key inside, respectively.

### Error 500

If the call is unsucessfull the status code will be 500, it is due to an error while creating the keys or while creating the zip file.


## Actions

### Add a new Action
---
#### Method - POST

URI:

```
http://IP_MASTER:32026/addaction
```

Model:
Body: json
```
curl -X POST http://IP_MASTER:32026/addaction -H 'Content-Type: application/json' -d 'json'
```

Example:

```
curl -X POST http://IP_MASTER:32026/addaction -H 'Content-Type: application/json' -d '{"actionName": "test","actuatorId": 99001,"resourceId": 99001}'
```

##### Note: the partnerId of the actuator and the resource have to match. In the example, both have the default 99.

### Input

The following table shows some information about the json configuration.

|Key|Type|Description|Example|
|--|--|--|--|
|actuatorId|String|Id of the actuator|99001|
|resourceId|String|Id of the resource|99001|
|actionName|String|name of the action|work|

### Success 201 & Error 400, 500

The 3 possible status are 201, 400, and 500. In the table bellow there is some information about each key.

|Field|Type|Description|
|--|--|--|
|message|String|Message about the status of the call|
|status|String|Status of the HTTP Request|

### Get Actions
---
#### Method - GET

URI:

```
http://IP_MASTER:32026/getactions
```

Model:
```
curl -X GET http://IP_MASTER:32026/getactions
```

Example:

```
curl -X GET http://IP_MASTER:32026/getactions
```


### Success 200

If the call is sucessfull the status code will be 200.

|Key|Type|Value description|
|--|--|--|
|actions|Array|Array of actions|


### Delete Action
---
#### Method - DELETE

URI:

```
http://IP_MASTER:32026/deleteaction?actionIdString=
```

Model:
```
curl -X DELETE http://IP_MASTER:32026/deleteaction?actionIdString=actionId
```

Example:

```
curl -X DELETE http://IP_MASTER:32026/deleteaction?actionIdString=22
```


### Success 200

If the call is sucessfull the status code will be 200.


- - -
## Actuators

### Add a new Actuator
- - -
#### Method - POST

URI:

```
http://IP_MASTER:32026/addactuator
```

Model:
Body: file and actuatorAddress
```
curl -X POST http://IP_MASTER:32026/addactuator -F file=@file -F address=actuatorAddress
```

Example:

```
curl -X POST http://IP_MASTER:32026/addactuator -F file=@/home/user/publicKey -F address=http://localhost:8080/k8sact
```

##### Note: if partner Id is a key in the json, it will be used to generate the actuatorId. If it isn't, the default partnerId will be 99.

### Input
The following table shows some information about the input.

|Input|Type|Description|Example|
|--|--|--|--|
|actuatorAddress|String|Address of the Actuator which we want to add|http://localhost:8080/k8sact|
|file|File|File with the public key|/home/user/publickey|
|partnerId|Int|Id of the partner|30|

### Success 201 & Error 400, 415, 500

The 4 possible status are 201, 400, 415 and 500. In the table bellow there is some information about each key.

|Key|Type|Value description|
|--|--|--|
|message|String|Message about the status of the call|
|status|String|Status of the HTTP Request|

### Get Actuators
---
#### Method - GET

URI:

```
http://IP_MASTER:32026/getactuators
```

Model:
```
curl -X GET http://IP_MASTER:32026/getactuators
```

Example:

```
curl -X GET http://IP_MASTER:32026/getactuators
```


### Success 200

If the call is sucessfull the status code will be 200.

|Key|Type|Value description|
|--|--|--|
|actuators|Array|Array of actuators|


---
## Configurations

### Add a new Configuration 
---
#### Method - POST

URI:

```
http://IP_MASTER:32026/addconfiguration
```

Model:
Body: json
```
curl -X POST http://IP_MASTER:32026/addconfiguration -H 'Content-Type: application/json' -d 'json'
```

Example:

```
curl -X POST http://IP_MASTER:32026/addconfiguration -H 'Content-Type: application/json' -d '{"actionId": 99001,"keyName": "test","domain": "fill"}'
```

### Input

The following table shows some information about the json configuration.

|Key|Type|Description|Example|
|--|--|--|--|
|keyName|String|String with the name of the Key|conf|
|domain|String|String with the Domain|2|
|actionId|String|Id of the action|99001|

### Success 201 & Error 400, 500

The 3 possible status are 201, 400, and 500. In the table bellow there is some information about each key.

|Field|Type|Description|
|--|--|--|
|message|String|Message about the status of the call|
|status|String|Status of the HTTP Request|


### Get Configurations
---
#### Method - GET

URI:

```
http://IP_MASTER:32026/getconfigurations?actionIdString=
```

Model:
```
curl -X GET http://IP_MASTER:32026/getconfigurations?actionIdString=actionId
```

Example:

```
curl -X GET http://IP_MASTER:32026/getconfigurations?actionIdString=22
```


### Success 200

If the call is sucessfull the status code will be 200.

|Key|Type|Value description|
|--|--|--|
|configurations|Array|Array of configurations|


### Delete Configuration
---
#### Method - DELETE

URI:

```
http://IP_MASTER:32026/deleteconfiguration?configurationIdString=
```

Model:
```
curl -X DELETE http://IP_MASTER:32026/deleteconfiguration?configurationIdString=configurationId
```

Example:

```
curl -X DELETE http://IP_MASTER:32026/deleteconfiguration?configurationIdString=22
```


### Success 200

If the call is sucessfull the status code will be 200.


- - -
## Descriptions

### Add a new Description
- - -
#### Method - POST

URI:

```
http://IP_MASTER:32026/adddescription
```

Model:
Body: json
```
curl -X POST http://IP_MASTER:32026/adddescription -H 'Content-Type: application/json' -d 'json'
```

Example:

```
curl -X POST http://IP_MASTER:32026/adddescription -H 'Content-Type: application/json' -d '{"dataType": "measurement","descriptionName": "test","unit" : "s"}'
```

##### Note: if partner Id is a key in the json, it will be used to generate the descriptionId. If it isn't, the default partnerId will be 99.

### Input

The following table shows some information about the json configuration.

|Key|Type|Description|Example|
|--|--|--|--|
|dataType|String|Type of the data|measurement|
|descriptionName|String|Name of the description|Desc|
|unit|String|Unit of the description|sec|
|partnerId|Int|Id of the partner|30|

### Success 201 & Error 400, 500

The 3 possible status are 201, 400, and 500. In the table bellow there is some information about each key.

|Field|Type|Description|
|--|--|--|
|message|String|Message about the status of the call|
|status|String|Status of the HTTP Request|


### Get Descriptions
---
#### Method - GET

URI:

```
http://IP_MASTER:32026/getDescriptions
```

Model:	

*Query Parameters* -> filter

```
curl -X GET http://IP_MASTER:32026/getDescriptions?filter=filterText
```

Example:
```
curl -X GET http://IP_MASTER:32026/getDescriptions?filter=CPU&
```

### Input

- *filter* -> String type. Optional parameter. When defined, returned descriptions must match it on their ids or names.

### Success 200

If the call is sucessfull the status code will be 200.

|Key|Type|Value description|
|--|--|--|
|descriptions|Array|Array of descriptions|

- - -
## Metrics

### Add a new Metric
- - -
#### Method - POST

URI:

```
http://IP_MASTER:32026/createMetric
```

Model:
Body: json
```
curl -X POST http://IP_MASTER:32026/createMetric -H 'Content-Type: application/json' -d 'json'
```

##### Note: The metric to be created can either be a leaf or parent metric. Depending on the case, the JSON formatting is different. Following, there is an example for each case.

Example for the creation of a leaf metric:
```
curl -X POST http://IP_MASTER:32026/createMetric -H 'Content-Type: application/json' -d '{"metricName":"Leaf","leafAttribute":{"metricAggregationOperator":0,"numSamples":"10","normalizationMethod":"MIN-MAX","normalizationKind":1,"minimumThreshold":"0","maximumThreshold":"100","description":{"descriptionId":"1"}}}'
```

Example for the creation of a parent metric:
```
curl -X POST http://IP_MASTER:32026/createMetric -H 'Content-Type: application/json' -d '{"metricName":"Parent","childMetrics":[{"metricId":4},{"metricId":1}],"attributeAggregationOperator":0}'
```

### Input

The following table shows some information about the json configuration.

|Key|Type|Description|Example|
|--|--|--|--|
|metricName|String|Name of the metric to be created|CPU consumption|
|leafAttribute|JSON object|Properties of the leaf metric|{"metricAggregationOperator":,"numSamples":, ...}|
|metricAggregationOperator|Int|Id of the aggregation operator to apply on the probed data of the leaf metric|0|
|numSamples|Int|Number of samples to consider when calculating the leaf metric value|10|
|normalizationMethod|String|Identifier of the method to use on the normalization of leaf metric data |MIN-MAX|
|normalizationKind|Int|Type of leaf metric (0 is BENEFIT and 1 is COST). If higher metric value the better, then it is a BENEFIT.|0|
|minimumThreshold|Double|Minimum value to consider when applying normalization|0|
|maximumThreshold|Double|Maximum value to consider when applying normalization|100|
|description|JSON object|Description properties to associate the leaf metric to|{"descriptionId":""}|
|descriptionId|Int|Id of the description the leaf metric is associated to|Desc|
|childMetrics|Array|List of children metrics from a parent|[{"metricId":4},{"metricId":1}]|
|metricId|Int|Id of a metric|30|
|attributeAggregationOperator|Int|Id of the aggregation operator to apply on the children metrics values|0|

### Success 201 & Error 400, 500

The 3 possible status are 201, 400, and 500. In the table bellow there is some information about each key.

|Field|Type|Description|
|--|--|--|
|message|String|Message about the status of the call|
|status|String|Status of the HTTP Request|

### Get Metrics
---
#### Method - GET

URI:

```
http://IP_MASTER:32026/getMetrics
```

Model:	

*Query Parameters* -> filter, createQualityModel

```
curl -X GET http://IP_MASTER:32026/getMetrics?filter=filterText&createQualityModel=booleanValue
```

Example:
```
curl -X GET http://IP_MASTER:32026/getMetrics?filter=CPU&createQualityModel=true
```

### Input

- *filter* -> String type. Optional parameter. When defined, metrics returned must match it on their ids or names.

- *createQualityModel* -> Boolean type. Optional parameter. When received, and defined as "true", metrics returned will be the ones not associated to quality models.

### Success 200

If the call is sucessfull the status code will be 200.

|Key|Type|Value description|
|--|--|--|
|metrics|Array|Array of metrics|

### Get a Metric
---
#### Method - GET

URI:

```
http://IP_MASTER:32026/getMetrics/{id}
```

Model:	

*Path parameter* -> id

```
curl -X GET http://IP_MASTER:32026/getMetrics/{id}
```

Example:
```
curl -X GET http://IP_MASTER:32026/getMetrics/1
```

### Input

- *id* -> Int type. A metric’s database id.

### Success 200

If the call is sucessfull the status code will be 200.

|Key|Type|Value description|
|--|--|--|
|metric|JSON object|Metric properties depending on being a leaf or parent metric. For both cases, the id and name are retrieved. Concerning a parent metric, additionally, the metrics tree and the children aggregation operator are returned. As for a leaf metric, information about associated raw data and how to process it are returned|

- - -
## Probes 

### Add a new Probe 
- - -
#### Method - POST

URI:

```
http://IP_MASTER:32026/addprobe
```

Model:
Body: json
```
curl -X POST http://IP_MASTER:32026/addprobe -H 'Content-Type: application/json' -d 'json'
```

Example:

```
curl -X POST http://IP_MASTER:32026/addprobe -H 'Content-Type: application/json' -d '{"probeName": "test","password": "aaa","salt" : "3","token" : "3","tokenExpiration" : 1564586073}'
```

##### Note: if partner Id is a key in the json, it will be used to generate the probeId. If it isn't, the default partnerId will be 99.

### Input

The following table shows some information about the json configuration.

|Key|Type|Description|Example|
|--|--|--|--|
|probeName|String|Name of the probe|probe|
|password|String|Password of the probe|elCe12w21e21e|
|salt|String|salt of the password|123123213|
|token|String|token of the probe|3|
|tokenExpiration|Int|Expiration of the Probe|1564586073|
|partnerId|Int|Id of the partner|30|

### Success 201 & Error 400, 500

The 3 possible status are 201, 400, and 500. In the table bellow there is some information about each key.

|Field|Type|Description|
|--|--|--|
|message|String|Message about the status of the call|
|status|String|Status of the HTTP Request|



### Get Probes
---
#### Method - GET

URI:

```
http://IP_MASTER:32026/getprobes
```

Model:
```
curl -X GET http://IP_MASTER:32026/getprobes
```

Example:

```
curl -X GET http://IP_MASTER:32026/getprobes
```


### Success 200

If the call is sucessfull the status code will be 200.

|Key|Type|Value description|
|--|--|--|
|probes|Array|Array of probes|


### Delete Probe
---
#### Method - DELETE

URI:

```
http://IP_MASTER:32026/deleteprobe?probeIdString=
```

Model:
```
curl -X DELETE http://IP_MASTER:32026/deleteprobe?probeIdString=probeId
```

Example:

```
curl -X DELETE http://IP_MASTER:32026/deleteprobe?probeIdString=22
```


### Success 200

If the call is sucessfull the status code will be 200.


- - -
## Quality Models

### Add a new Quality Model
- - -
#### Method - POST

URI:

```
http://IP_MASTER:32026/createQualityModel
```

Model:
Body: json
```
curl -X POST http://IP_MASTER:32026/createQualityModel -H 'Content-Type: application/json' -d 'json'
```

Example:
```
curl -X POST http://IP_MASTER:32026/createQualityModel -H 'Content-Type: application/json' -d '{"modelName":"Availability","metric":{"metricId":"5"}}'
```

### Input

The following table shows some information about the json configuration.

|Key|Type|Description|Example|
|--|--|--|--|
|modelName|String|Name of the quality model to be created|Availability|
|metric|JSON object|Properties of the metric associated as root to the quality model|{"metricId":"5"}|
|metricId|Int|Id of a metric|1|

### Success 201 & Error 400, 500

The 3 possible status are 201, 400, and 500. In the table bellow there is some information about each key.

|Field|Type|Description|
|--|--|--|
|message|String|Message about the status of the call|
|status|String|Status of the HTTP Request|

### Add a new Configuration Profile
- - -
#### Method - POST

URI:

```
http://IP_MASTER:32026/createConfigurationProfile
```

Model:
Body: json
```
curl -X POST http://IP_MASTER:32026/createConfigurationProfile -H 'Content-Type: application/json' -d 'json'
```

Example:
```
curl -X POST http://IP_MASTER:32026/createConfigurationProfile -H 'Content-Type: application/json' -d '{"preferences":[{"metricId":"1","weight":"0.4"},{"metricId":"2","weight":"0.6"},{"metricId":"3","weight":"1"}],"qualityModelId":1,"profileName":"ConfgProfile"}'
```

### Input

The following table shows some information about the json configuration.

|Key|Type|Description|Example|
|--|--|--|--|
|preferences|Array|List of metrics and their weights|[{"metricId":"1","weight":"0.4"},{"metricId":"2","weight":"0.6"}]|
|metricId|Int|Id of a metric from a quality model|1|
|weight|Double|Metric weight to apply in the configuration profile|0.35|
|qualityModelId|Int|Id of the quality model to associate the configuration profile|1|
|profileName|String|Name of the configuration Profile|Unbalanced Availability|

### Success 201 & Error 400, 500

The 3 possible status are 201, 400, and 500. In the table bellow there is some information about each key.

|Field|Type|Description|
|--|--|--|
|message|String|Message about the status of the call|
|status|String|Status of the HTTP Request|

### Get Quality Models
---
#### Method - GET

URI:

```
http://IP_MASTER:32026/getQualityModels
```

Model:	

*Query Parameters* -> qualityModelsFilter, metricsFilter

```
curl -X GET http://IP_MASTER:32026/getQualityModels?qualityModelsFilter=qualityModelsFilterText&metricsFilter=metricsFilterText
```

Example:
```
curl -X GET http://IP_MASTER:32026/getMetrics?filter=Availability&metricsFilter=1
```

### Input

- *qualityModelsFilter* -> String type. Optional parameter. When defined, quality models returned must match it on their ids or names.

- *metricsFilter* -> String type. Optional parameter. When defined, metrics returned must match it on their ids or names.

### Success 200

If the call is sucessfull the status code will be 200.

|Key|Type|Value description|
|--|--|--|
|qualityModels|Array|Array of quality models|

### Get a Quality Model
---
#### Method - GET

URI:

```
http://IP_MASTER:32026/getQualityModels/{id}
```

Model:	

*Path parameter* -> id

```
curl -X GET http://IP_MASTER:32026/getQualityModels/{id}
```

Example:
```
curl -X GET http://IP_MASTER:32026/getQualityModels/1
```

### Input

- *id* -> Int type. A quality model’s database id.

### Success 200

If the call is sucessfull the status code will be 200.

|Key|Type|Value description|
|--|--|--|
|qualityModel|JSON object|Quality model's id, name, associated metrics tree, and list of the associated configuration profiles|


### Get a Configuration Profile
---
#### Method - GET

URI:

```
http://IP_MASTER:32026/getConfigurationProfile/{id}
```

Model:	

*Path parameter* -> id

```
curl -X GET http://IP_MASTER:32026/getConfigurationProfile/{id}
```

Example:
```
curl -X GET http://IP_MASTER:32026/getConfigurationProfile/1
```

### Input

- *id* -> Int type. A configuration profile’s database id.

### Success 200

If the call is sucessfull the status code will be 200.

|Key|Type|Value description|
|--|--|--|
|configurationProfile|JSON object|Configuration profile's id, name, associated quality model’s id, and list of metrics ids and their weights|

### Get Metrics from a Configuration Profile
---
#### Method - GET

URI:

```
http://IP_MASTER:32026/getConfigurationProfile/{id}/listOfMetrics
```

Model:	

*Path parameter* -> id

```
curl -X GET http://IP_MASTER:32026/getConfigurationProfile/{id}/listOfMetrics
```

Example:
```
curl -X GET http://IP_MASTER:32026/getConfigurationProfile/1/listOfMetrics
```

### Input

- *id* -> Int type. A configuration profile’s database id.

### Success 200

If the call is sucessfull the status code will be 200.

|Key|Type|Value description|
|--|--|--|
|listOfMetrics|Array|List of metrics from a configuration profile|

- - -
## Resources

### Add a new Resource
- - -
#### Method - POST

URI:

```
http://IP_MASTER:32026/addresource
```

Model:
Body: json
```
curl -X POST http://IP_MASTER:32026/addresource -H 'Content-Type: application/json' -d 'json'
```

Example:

```
curl -X POST http://IP_MASTER:32026/addresource -H 'Content-Type: application/json' -d '{"resourceName": "test","resourceType": "VM","resourceAddress": "127.0.0.1:8080"}'
```

##### Note: if partner Id is a key in the json, it will be used to generate the resourceId. If it isn't, the default partnerId will be 99.

### Input

The following table shows some information about the json configuration.

|Key|Type|Description|Example|
|--|--|--|--|
|resourceName|String|name of the resource|new resource|
|resourceType|String|type of the resource|VM|
|resourceAddress|String|address of the resource|127.0.0.1|
|partnerId|Int|Id of the partner|30|

### Success 201 & Error 400, 500

The 3 possible status are 201, 400, and 500. In the table bellow there is some information about each key.

|Field|Type|Description|
|--|--|--|
|message|String|Message about the status of the call|
|status|String|Status of the HTTP Request|

### Get Resources
---
#### Method - GET

URI:

```
http://IP_MASTER:32026/getResources
```

Model:	

*Query Parameters* -> createRule

```
curl -X GET http://IP_MASTER:32026/getResources?createRule=booleanValue
```

Example:
```
curl -X GET http://IP_MASTER:32026/getResources?createRule=true
```

### Input

- *createRule* -> Boolean type. Optional parameter. When defined and as true, resources returned must have actions associated in the database.

### Success 200

If the call is sucessfull the status code will be 200.

|Key|Type|Value description|
|--|--|--|
|resources|Array|Array of currently monitored resources|


### Get Resources by Actuator
---
#### Method - GET

URI:

```
http://IP_MASTER:32026/getresources?actuatorIdString=
```

Model:

*Query Parameters* -> actuatorIdString

```
curl -X GET http://IP_MASTER:32026/getresources?actuatorIdString=actuatorId
```

Example:

```
curl -X GET http://IP_MASTER:32026/getresources?actuatorIdString=22
```

### Input

- *actuatorIdString* -> String type. An actuator id that will be used to find out the partner id and, consequently, used to find resources that share the same partner id.

### Success 200

If the call is sucessfull the status code will be 200.

|Key|Type|Value description|
|--|--|--|
|resources|Array|Array of resources that shares the same partnerId as the actuator|

### Get associated Quality Model and Configuration Profile
---
#### Method - GET

URI:

```
http://IP_MASTER:32026/getResources/{id}/weightedTree
```

Model:	

*Path parameter* -> id

```
curl -X GET http://IP_MASTER:32026/getResources/{id}/weightedTree
```

Example:
```
curl -X GET http://IP_MASTER:32026/getResources/1/weightedTree
```

### Input

- *id* -> Int type. A resource’s database id.

### Success 200

If the call is sucessfull the status code will be 200.

|Key|Type|Value description|
|--|--|--|
|qualityModel|JSON object|List of metrics from the quality model associated to the resource|
|configurationProfile|JSON object|List of metrics and their weights from the configuration profile associated to the resource|


### Get Data
---
#### Method - GET

URI:

```
http://IP_MASTER:32026/getResources/{id}/data
```

Model:	

*Path parameter* -> id

*Query parameters* -> metricId, dataType, startDate, endDate, addPlansInfo

```
curl -X GET http://IP_MASTER:32026/getResources/{id}/data?metricId=int&dataType=string&startDate=epochDate&endDate=epochDate&addPlansInfo=boolean
```

Example:
```
curl -X GET http://IP_MASTER:32026/getResources/1/data?metricId=1&dataType=raw&startDate=1666356646&endDate=1666356946&addPlansInfo=false
```

### Input
- *id* -> Int type. A resource's database id.
- *metricId* -> Int type. A metric’s id to which there are values collected from or calculated for the given resource.
- *dataType* -> String type. It can take "raw" and "metric" as values. The option "raw" requests data collected by probes and can only be applied if the metric to be consulted corresponds to a leaf metric. The other way the data will correspond to the values calculated by the TMA's Analyze component.
- *startDate* -> Long type. A time slot beggining timestamp in epoch as seconds.
- *endDate* -> Long type. A time slot ending in epoch as seconds.
- *addPlansInfo* -> Boolean type. When true requests the ids of applied adaptation plans in the time slot requested.

### Success 200

If the call is sucessfull the status code will be 200.

|Key|Type|Value description|
|--|--|--|
|plotData|JSON object|List of values (raw or metric) and their timestamps, within the requested time slot. If the proper option was set, there will also be a list with the ids of executed adaptation plans|

### Simulate Data
---
#### Method - PATCH

URI:

```
http://IP_MASTER:32026/simulateData
```

Model:

Body: json

```
curl -X POST http://IP_MASTER:32026/simulateData -H 'Content-Type: application/json' -d 'json'
```

Example:
```
curl -X POST http://IP_MASTER:32026/simulateData -H 'Content-Type: application/json' -d '{"resourceId":"1","metricToSimulate":{"metricId":3,"childMetrics":[{"metricId":1,"childMetrics":[]},{"metricId":2,"childMetrics":[]}]},"preferences":{"1":0.5,"2":0.5,"3":1},"startDate":1656988688,"endDate":1656988751}'
```

### Input

The following table shows some information about the json configuration.

|Key|Type|Description|Example|
|--|--|--|--|
|resourceId|Int|Id of the resource to which the simulation will be applied|1|
|metricToSimulate|JSON object|A metric's tree structure to which the simulation will be applied|{"metricId":3,"childMetrics":[{"metricId":1,"childMetrics":[]},...]}|
|metricId|Int|Id of a metric|1|
|childMetrics|Array|List of a parent's children metrics (and their subsequent children)|[{"metricId":1,"childMetrics":[]},{"metricId":2,"childMetrics":[]}]|
|preferences|JSON object|Mappping between the ids from the simulation metrics and the corresponding simulation weights |{"1":0.5,"2":0.5,"3":1}|
|startDate|Long|Time slot beggining timestamp in epoch as seconds|1656988688|
|endDate|Long|Time slot ending timestamp in epoch as seconds|1656988751|

### Success 200

If the call is sucessfull the status code will be 200.

|Key|Type|Value description|
|--|--|--|
|simulationData|Array|List of metric values and their timestamps, after aplying the simulation weights within the requested time slot|


## Configure Actions


#### Method - POST

URI:
```
http://IP_MASTER:32026/configureactions
```

Model:
Parameter - actuatorId.
Body - json;
```
curl -X POST 'http://IP_MASTER:32026/configureactions?actuatorIdString=actuatorId' -H 'Content-Type: application/json' -d 'json'
```

Example:

```
curl -X POST 'http://IP_MASTER:32026/configureactions?actuatorIdString=1' -H 'Content-Type: application/json' -d '{"actions":[{"action":"myScaleOutAction","resourceId":1,"configuration":[{"key":"lastScale","value": "int"},{"key": "replicaIncrease","value": "int"}]},{"action": "myScaleInAction","resourceId": 1,"configuration": [{"key":"lastScale","value":"int"},{"key":"replicaDecrease","value": "int"}]}]}'
```

### Input

The actuatorId needs to be a String with the ID of the actuator which we want to configure (example: 232).

The following table shows some information about the json configuration.

|Key|Type|Description|Example|
|--|--|--|--|
|actions|Array with jsons|Array with all the actions|[{"action": 2,"resourceId" : 2,"configuration": []}]|
|action|String|An action we want to add|do something|
|resourceId|Int|String with the ID of the resource|2|
|configuration|Array|Array of configurations to add to the action|[{"key":"conf","value":"3"},{"key":"conf","value":"3"}]|
|key|String|key of a configuration|conf|
|value|String|value of a configuration|2|

### Success 201 & Error 400, 500

The 3 possible status are 201, 400 and 500. The json that is returned will always have the same keys. In the table bellow there is some information about each key.

|Key|Type|Value description|
|--|--|--|
|message|String|Message about the status of the call|
|status|String|Status of the HTTP Request|


## Get Scores


#### Method - Get

URI:
```
http://IP_MASTER:32026/get_scores?metricId=&timestamp=
```

Model:
```
curl -X GET "http://IP_MASTER:32026/get_scores?metricId=metricId&timestamp=timestamp"
```

Example:

```
curl -X GET "http://IP_MASTER:32026/get_scores?metricId=4&timestamp=1562213821040"
```

### Input

The metricId needs to be an Integer with the ID of the metric which we want to get the scores from (example: 4).

The timestamp needs to be a long.

All the scores returned will have a valueTime after the given timestamp.

The following table shows information about the json configuration.

|Key|Type|Description|Example|
|--|--|--|--|
|scores|Array with jsons|Array with all the scores|[{"value": 0.011,"resourceId" : 2,"timestamp": 1562213835760}, {"value": 0.015,"resourceId" : 2,"timestamp": 1562213843670}]|

### Success 200 & Error 400, 500

The 3 possible status are 200, 400 and 500. In case the status is different than 200, the returned json will have the following configuration.

|Key|Type|Value description|
|--|--|--|
|message|String|Message about the status of the call|
|status|String|Status of the HTTP Request|
- - -

## Implementation Details

To implement this API it was used [SpringBoot](https://spring.io/projects/spring-boot)  with [log4j](https://logging.apache.org/log4j/2.x/) to help with the logging.

## Authors

* Jose D'Abruzzo Pereira
* Paulo Gonçalves
* Rui Silva
