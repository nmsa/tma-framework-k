# MySQL
MySQL is an open-source relation DBMS. This database system has a good performance in almost every scenarios of application. It is compatible with almost every operating systems and program languages. One of the most important characteristics of this software is its documentation that is very good and simple.

## Prerequisites
To deploy MySQL, you need to initialize the Kubernetes cluster and following the instructions present in README file of the development folder of this repository.
Other requirement is Ceph needs to be correctly installed in all machines of Kubernetes cluster and running in its machine. All steps needed to install and connect Ceph with Kubernetes are present in README file of ceph folder of this repository.

## Installation
The first step of installing MySQL in Kubernetes cluster is executing the yaml file that creates and deploys MySQL container in Kubernetes Cluster. To do that, you should execute the following command:
 ```sh
kubectl create -f mysql-deployment.yaml
```
After some seconds, MySQL pod should be in "Running" status.
When MySQL pod is in "Running" status, the next step is create a database to store all necessary data. That can be done by executing the following command in Master node of Kubernetes Cluster.
```sh
kubectl exec -ti mysql-0 -- mysql -u root -ppassword -e "CREATE DATABASE knowledge /*\!40100 DEFAULT CHARACTER SET utf8 */;"
```
With knowledge database created, it is time to create all necessary tables and their relations in database previously created. To do that, you should execute the following command:
 ```sh
kubectl exec -ti mysql-0 -- mysql -u root -ppassword knowledge < ../database/TMA-K_create_database.sql
```
## Testing
For testing purposes, there is a SQL script that inserts in knowledge database tables example data.
To insert the example data, you should execute the following command:
```sh
kubectl exec -ti mysql-0 -- mysql -u root -ppassword knowledge < mysql/example_data_mysql.sql
```
This script inserts data in Probe, Resource, and Description tables, more specifically,  example_data_mysql.sql inserts 6 rows in Probe table, 26 rows in Description table, and 7 rows in Resource table.

