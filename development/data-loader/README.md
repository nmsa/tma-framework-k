# TMA Data Loader

This project aims to:
* Consume the items from a topic monitor of the apache kafka;
* Processes the data received in order to be inserted in the `knowledge` database;
* Insert the data in the table of the `knowledge` database;

## Prerequisites

This component requires the software available in [java-client-lib](https://github.com/eubr-atmosphere/tma-framework-m/tree/master/development/libraries/java-client-lib).

## Installation

This is a simple module to insert data in the `knowledge` database.

To build the jar, you should run the following command on the worker node:
```sh
sh build.sh
```

The data loader will consume the items from the topic `topic-monitor`. To create the topic, you should run on the master node:
```sh
kubectl exec -ti kafka-0 -- kafka-topics.sh --create --topic topic-monitor --zookeeper zk-0.zk-hs.default.svc.cluster.local:2181 --partitions 1 --replication-factor 1
```

To deploy the pod in the cluster, you should run the following command on the master node:

```sh
kubectl create -f tma-dataloader.yaml
```

## Authors
* José D'Abruzzo Pereira 
* Rui Silva
* Paulo Gonçalves

