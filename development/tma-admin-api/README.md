
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
	*	[Partner](#Partner)
		+ 	[Get Partners](#Get-Partners)
	*	[Keys](#Keys)
		+ 	[Generate a new Public - Private key pair](#Generate-a-new-Public---Private-key-pair)
	*	[Actions](#Actions)
		+	[Add a new Action](#Add-a-new-Action)
		+	[Get Actions](#Get-Actions)
		+	[Remove an Action](#Remove-an-Action)
	*	[Actuators](#Actuators)
		+	[Add a new Actuator](#Add-a-new-Actuator)
		+	[Get Actuators](#Get-Actuators)
	*	[Configurations](#Configurations)
		+	[Add a new Configuration](#Add-a-new-Configuration)
		+	[Get Configurations](#Get-Configurations)
		+	[Remove an Configuration](#Remove-an-Configuration)
	*	[Configuration Profiles](#Configuration-Profiles)
		+	[Add a new Configuration Profile](#Add-a-new-Configuration-Profile)
		+	[Get Configuration Profile](#Get-Configuration-Profile)
		+	[Get Metrics from a Configuration Profile](#Get-Metrics-from-a-Configuration-Profile)
	*	[Descriptions](#Descriptions)
		+	[Add a new Description](#Add-a-new-Description)
		+	[Get Descriptions](#Get-Descriptions)
	*	[Metrics](#Metrics)
		+	[Add a Metric](#Add-a-new-Metric)
		+	[Get Metrics](#Get-Metrics)
		+	[Get a Metric](#Get-a-Metric)
	*	[Plot Config](#Plot-Config)
		+	[Add a Plot Config](#Add-a-new-Plot-Config)
		+	[Get Plot Configs](#Get-Plot-Configs)
		+	[Replace a Plot Config](#Replace-a-Plot-Config)
		+	[Remove a Plot Config](#Remove-a-Plot-Config)
	*	[Probes](#Probes)
		+	[Add a new Probe](#Add-a-new-Probe)
		+   [Get Probes](#Get-Probes)
		+   [Remove a Probe](#Remove-a-Probe)
	*	[Quality Models](#Quality-Models)
		+	[Add a Quality Model](#Add-a-new-Quality-Model)
		+	[Get Quality Models](#Get-Quality-Models)
		+	[Get a Quality Model](#Get-a-Quality-Model)
	*	[Resources](#Resources)
		+	[Add a new Resource](#Add-a-new-Resource)
		+	[Get Resources](#Get-Resources)
		+	[Get associated Quality Model and Configuration Profile](#Get-associated-Quality-Model-and-Configuration-Profile)
		+	[Get Data](#Get-Data)
		+	[Configure Actions](#Configure-Actions)
		+	[Get Scores](#Get-Scores)
		+	[Simulate Data](#Simulate-Data)
	*	[Rules](#Rules)
		+	[Add a new Rule](#Add-a-new-Rule)
		+	[Get Rules](#Get-Rules)
		+	[Get a Rule](#Get-Rule)
		+	[Remove a Rule](#Remove-a-Rule)
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
http://IP_MASTER:32026/getresources?actuatorIdString=
```

Model:
```
curl -X GET http://IP_MASTER:32026/getresources?actuatorIdString=actuatorId
```

Example:

```
curl -X GET http://IP_MASTER:32026/getresources?actuatorIdString=22
```


### Success 200

If the call is sucessfull the status code will be 200.

|Key|Type|Value description|
|--|--|--|
|resources|Array|Array of resources|



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
