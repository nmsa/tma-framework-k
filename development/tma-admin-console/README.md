# TMA Admin Console

This project aims to:
* TBD.

## Installation

To build the container, you should run the following command on the worker node:
```sh
sh build.sh
```

To deploy the pod in the cluster, you should run the following command on the master node:

```sh
kubectl create -f tma-admin-console.yaml
```

## Execution

To run the Admin Console, execute the following commands:
```sh
kubectl exec -ti tma-admin-console-0 -- bash
java -jar bin/tma-admin-console-0.0.1-SNAPSHOT.jar
```
