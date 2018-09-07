# TMA-Knowledge Development
This component of TMA platform is composed by a DBMS MySQL and a Ceph block-storage persistent volume that stores all data of MySQL database.
The instructions provided below include all steps that are needed to set up this component in your local system for testing purposes.

## Prerequisites
The instructions were tested in `ubuntu`, but should work in other `debian`-based distributions, assuming that you are able to install the key dependencies.

The first step is to install the required components: `docker`, and `kubernetes`.
To install docker, you should execute the following command:
```sh
sudo su -
apt-get install docker.io
```
To install Kubernetes you should execute the following commands:

```sh
sudo su -
curl -s https://packages.cloud.google.com/apt/doc/apt-key.gpg | apt-key add 
echo -e "deb http://apt.kubernetes.io/ kubernetes-xenial main " >> /etc/apt/sources.list.d/kubernetes.list
apt-get update
apt-get install -y kubelet kubeadm kubectl kubernetes-cni
```

In order to use Kubernetes two machines (nodes) are required with different IP addresses for deploying all necessary pods.
These two nodes communicate through network plugin Flannel.
To inicialize the Kubernetes cluster, run the following command in the Master machine:

```sh
swapoff -a
kubeadm init --pod-network-cidr=10.244.0.0/16
```

The output of the command above gives the required commands to complete the setup of Kubernetes cluster. Those commands are:

```sh
mkdir -p $HOME/.kube
sudo cp -i /etc/kubernetes/admin.conf $HOME/.kube/config
sudo chown $(id -u):$(id -g) $HOME/.kube/config
```


Before joining the other node in this cluster, it is necessary to setup the network plugin that is responsible for the communications between Master and Worker nodes.
To do that, run:

```sh
kubectl apply -f https://raw.githubusercontent.com/coreos/flannel/master/Documentation/kube-flannel.yml
kubectl apply -f https://raw.githubusercontent.com/coreos/flannel/master/Documentation/k8s-manifests/kube-flannel-rbac.yml
ip route add 10.96.0.0/16 dev xxxxxx
```

Where xxxxxx is the network interface name.
After these commands, Master node will be at "Ready" state. For joining the other node, paste the last command of the output of the kubeadm init command in that node. One example of this command can be:
```sh
kubeadm join --token TOKEN MASTER_IP:6443
```

Where TOKEN is the token you were presented after initializing the master and MASTER_IP is the IP address of the master.
Now, the Kubernetes cluster are ready to deploy containers.


## Installation

After completing all steps of the previous section, the first step of project installation is to install  Ceph. Ceph needs to be installed on a separate machine. To deploy Ceph, you need to install Ceph in all three machines. To do that you just need to execute the following script in all machines:
```sh
cd ceph/
sh ceph_installation.sh
```
The output of this script should replace the information of key field of the ceph-secret.yaml file.
After that you should deploy this file in Kubernetes. To do that, you need to execute the following the command:
```sh
kubectl create -f ceph/ceph-secret.yaml
```
With Ceph correctly installed and connected to Kubernetes Cluster, it is time to deploy MySQL. To do that, you just need to execute the following command:
```sh
kubectl create -f mysql/mysql-deployment.yaml
```
The next step is to create database to store all information provided by other components of this platform. To do that, you should execute the following command:
```sh
kubectl exec -ti mysql-0 -- mysql -u root -ppassword -e "CREATE DATABASE knowledge /*\!40100 DEFAULT CHARACTER SET utf8 */;"
```
After the creation of database, the next step is create all tables and relations between themselves. To do that, you should execute the following SQL script.
```sh
kubectl exec -ti mysql-0 -- mysql -u root -ppassword knowledge < ../database/TMA-K_create_database.sql
```
## Testing
For testing purposes, there is a script called `example_data_mysql.sql` that inserts example data in Probe,Resource, and Description tables.
To do that, you just need to execute the following SQL script
```sh
kubectl exec -ti mysql-0 -- mysql -u root -ppassword knowledge < mysql/example_data_mysql.sql
```
If everything runs correctly, you should see the data inserted by script in database tables previously referred.

