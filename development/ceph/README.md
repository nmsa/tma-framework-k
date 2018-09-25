# Ceph
Ceph is an open-source program that provides object storage on a distributed cluster. Ceph has no single point of fail, it can be scalable until Exabyte.
Ceph is also able to replicate the data and has fault-tolerant mechanisms to avoid loss of data. Ceph has efficient auto-management and monitoring mechanisms.
## Prerequisites
To integrate Ceph with Kubernetes, you need to initialize the Kubernetes cluster and follow the instructions present in `README` file of that development folder of this repository.
## Installation
The commands below of the process of installation Ceph are automated in `ceph_installation.sh` and in `ceph_configuration.sh` should be executed in a third machine separated from Kubernetes Cluster.
To install Ceph, you should execute the following command in all machines:
```sh
apt-get install ceph
```
The first step is to pull Ceph Docker image. To do that, execute the following commands:
```sh
docker pull docker.avvo.com/ceph-demo:luminous
```
Before deploying Ceph service, it is necessary to remove some files of Ceph directories and set the permissions of those directories. This can be done by executing the following commands:
```sh
rm -rf /etc/ceph/*
rm -rf /var/lib/ceph/*
chmod 777 /etc/ceph
chmod 777 /var/lib/ceph
```
The next step is to deploy Ceph services using Docker containers. To do that, execute the following command:
```sh
docker run --net=host -v /etc/ceph:/etc/ceph -v /var/lib/ceph:/var/lib/ceph \
  -e MON_IP="Machine IP" -e CEPH_PUBLIC_NETWORK="Network IP/Mask" docker.avvo.com/ceph-demo:luminous
```
Executing this command, you will be able to execute all Ceph commands on a terminal of Ceph machine. The previous command maps two volumes that have the commands to manage Ceph cluster. In the second part of command, it is necessary to define some environment variables. In the first one, you need to indicate the IP of Ceph machine and in the second variable you need to indicate the network IP and its mask.
Now, it is necessary to create a Ceph image to store all data of MySQL database. To do that, execute the following commands:
```sh
rbd create tma_k_db_mysql -s 1024
```
The image is created with size of 1GB.
Before mapping the image it is necessary to remove some Ceph image functionalities that are not supported by Ubuntu kernel.
To do that, execute the following commands:
```sh
rbd feature disable tma_k_db_mysql fast-diff
rbd feature disable tma_k_db_mysql object-map
rbd feature disable tma_k_db_mysql deep-flatten
```
## Testing
The first step of testing Ceph is to map the image previously created. To do that, execute the following commands:
```sh
rbd map tma_k_db_mysql
```
The output of this command is the directory when the image is mapped.
After mapping the image, it is necessary to define the type of file system that the image has. To do that, execute the following command:
```sh
mkfs.ext4 "Output of the previous command"
```
In order to MySQL pod be able to connect with its image, we have to unmap it. To do that, run the following commands:
```sh
rbd unmap tma_k_db_mysql
```

