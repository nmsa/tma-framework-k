# MySQL
MySQL is an open-source relational DBMS. This database system has a good performance in almost every scenarios of application. It is compatible with almost every operating systems and program languages. One of the most important characteristics of this software is its documentation that is very good and simple.

## Prerequisites
To deploy MySQL, you need to initialize the Kubernetes cluster and follow the instructions present in `README` file of the development folder of this repository.

## Installation
The first step is to build the Docker image of MySQL. To do that, the following command needs to be executed in Kubernetes Worker node:
```sh
sh build.sh
```
All of the following commands must be executed in Kubernetes Master machine.
The second step is to create a secret that encodes MySQL root password. To do that, the following command needs to be executed:
```sh
kubectl create secret generic mysql-pass --from-literal=password=passtobereplaced
```
The third step of installing MySQL in Kubernetes cluster is to execute the yaml files that create and deploy MySQL container into Kubernetes Cluster. To do that, you should execute the following commands:
 ```sh
kubectl create -f volume_mysql.yaml
kubectl create -f mysql-deployment.yaml
```
After some seconds, MySQL pod should be in "Running" status.
When MySQL pod is in the "Running" status, the next step is to create a database to store all necessary data. That can be done by executing the following command: 
```sh
kubectl exec -ti mysql-0 -- bash -c "mysql -u root --password=\$MYSQL_ROOT_PASSWORD -e \"CREATE DATABASE knowledge /*\!40100 DEFAULT CHARACTER SET utf8 */;\""
```
With the knowledge database created, it is time to create all necessary tables and their relations on it. To do that, you should execute the following command:
 ```sh
kubectl exec -ti mysql-0 -- bash -c "mysql -u root --password=\$MYSQL_ROOT_PASSWORD knowledge < /mysql/TMA-K_create_database.sql"
```
## Testing
For testing purposes, there is a SQL script that inserts examples of data in the knowledge database tables.
To insert the example data, you should execute the following command:
```sh
kubectl exec -ti mysql-0 -- bash -c "mysql -u root --password=\$MYSQL_ROOT_PASSWORD knowledge < /mysql/TMA-K_insert_example_data.sql"
```
This script inserts data in Probe, Resource, and Description tables. More specifically, `TMA-K_insert_example_data.sql` inserts 6 rows in Probe table, 26 rows in Description table, and 7 rows in Resource table.
