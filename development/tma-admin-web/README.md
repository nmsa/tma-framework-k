
# TMA Admin Web


# Index

 -   [Installation](#Installation)
 -   [Execution](#Execution)
 -   [Properties](#Properties)
 -	 [GUI](#GUI)
 -   [Implementation Details](#Implementation-Details)


# Installation

To build the container, you should run the following command on the Worker node:

```
sh build.sh
```

To deploy the pod in the cluster, you should run the following command on the master node:

```
kubectl create -f tma-admin-web.yaml
```

With TMA Admin correctly deployed and running, it is accessible through the IP of Kubernetes Master machine in port 32027. 



# Properties

Properties are specified in the following directory:
```
...\tma-admin-web\src\main\resources\META-INF\resources\commonFiles\js\properties.js
```


# Execution

There is an example on how to execute each of the features refered in the beggining in the corresponding section bellow.

# GUI

There is an integrated GUI that can be accessed on http://IP_MASTER:32027/

# Implementation Details

To implement this GUI it was used [SpringBoot](https://spring.io/projects/spring-boot)  with [log4j](https://logging.apache.org/log4j/2.x/) to help with the logging and [Angular](https://angular.io/).

## Authors

* Jose Alexandre D'Abruzzo
* Paulo Alexandre da Silva Gonçalves
* Rui Filipe Rama Silva