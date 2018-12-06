# TMA Admin Console

This project allows you to:

* Generate public-private key pair to be used in the encryption process;
* Add a new probe to the database;
* Add a new actuator to the database;
* Add a new resource to the database;
* Configure the actions that an actuator can perform (check details on [Message Format for Actions Registration](https://github.com/eubr-atmosphere/tma-framework-k/tree/master#message-format-for-actions-registration)).

Additionally, this project also allows you to test both the encryption and decryption of a message.

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

To run the `Admin Console`, execute the following commands:

```sh
kubectl exec -ti tma-admin-console-0 -- bash
java -jar bin/tma-admin-console-0.0.1-SNAPSHOT.jar
```

When running the console, it will display the options that you can perform in this console. Follow the instructions provided by the console.


## Key Generation

`Admin Console` allows you creating both public and private keys to be used in your actuator. The algorithm used to generate them is RSA/SHA1PRNG, and the key size is 2048 bytes.


## Authors

* Jose D'Abruzzo Pereira
