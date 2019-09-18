# MySQL
`MySQL` is an open-source relational DBMS. This database system has a good performance in almost every scenarios of application. It is compatible with almost every operating systems and program languages. One of the most important characteristics of this software is its documentation that is very good and simple.

## Prerequisites
To deploy `MySQL`, you need to initialize the `Kubernetes` cluster and follow the instructions present in `README` file of the development folder of this repository.
Another requirement is to have `Ceph` correctly installed in all machines of `Kubernetes` cluster and running in its machine. All the steps needed to install and to connect `Ceph` with `Kubernetes` are described in the `README` file of `Ceph` folder of this repository.

## Installation

After completing all steps of the previous section, the first step of project installation is to install `Ceph`. `Ceph` needs to be installed on a separate machine. To deploy `Ceph`, you need to install `Ceph` in all three machines. To do that you just need to execute the following script in `Ceph` machine:

```sh
cd ceph/
sh ceph_installation.sh
```

To install `Ceph` in `Kubernetes` Master and Worker machines, run the following command:

```sh
apt-get -y install ceph
```

Next, in the `Ceph` machine execute the following commands:

```sh
sh ceph_configuration.sh
```
The output of the previous script should be inserted in `key` field of the `ceph-secret.yaml` file. 
After that, you should deploy this file in `Kubernetes`. To do that, you need to execute the following the command:

```sh
kubectl create -f ceph-secret.yaml
```

With `Ceph` correctly installed and connected to `Kubernetes` cluster, it is time to deploy `MySQL`. The first step is to build `MySQL` `Docker` image. To do that, you just need to execute the following commands on Worker node of `Kubernetes` cluster:

```sh
cd ../mysql/
sh build.sh
```

Now, `MySQL` is ready to be executed inside of a `Kubernetes` pod. To do that execute the following script in `Kubernetes` Master node:

```sh
sh setup_database.sh
```

## Testing
For testing purposes, there is a script called `TMA-K_insert_example_data.sql` that inserts example data in Probe, Resource, and Description tables.
To do that, you just need to execute the following SQL script:

```sh
kubectl exec -ti mysql-0 -- bash -c "mysql -u root --password=\$MYSQL_ROOT_PASSWORD knowledge < /mysql/TMA-K_insert_example_data.sql"
```

The script inserts data in Probe, Resource and Description tables. More specifically, `TMA-K_insert_example_data.sql` inserts 6 rows in Probe table, 26 rows in Description table and 7 rows in Resource table.

If everything runs correctly, you should see the data inserted by script in database tables previously referred.