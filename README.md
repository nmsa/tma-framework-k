# Knowledge Component @ TMA Framework

The `TMA_Knowledge` component provides an interface to the administrator of the system to add a new actuator. It will allow the TMA Framework to know which actuators can actuate on the system. Additionally, the administrator can also register all the possible actions through a different interface.

![Knowledge Usage Sequence Diagram](https://github.com/eubr-atmosphere/tma-framework/blob/master/architecture/diagrams/TMA-E/TMA-E_Registration.png)

Initially, the administrator has to generate a public key to each actuator that will be used to authenticate when performing the adaptation operations. This has to be done only once, and it will be used to TMA to interact with the system.

Each administrator also needs to register each actuator through an authentication synchronous message, in which he/she will receive both the credentials and the public key. All communication is performed over REST services. 

The administrator needs to register all actions that can be performed by an actuator. This will allow TMA to decide what to do based on the list of available operations.

## TMA Knowledge Message Format

The message to be submitted to the `TMA_Knowledge` follow the `JSON` schema specified in [tma-k_schema](interface/atmosphere_tma-k_schema.json), which is currently in the version `0.1`. This should be used to notify TMA about the existence of the Actuator.

The figure below presents a representation of this schema, which is also explained below.

*![Knowledge Register Schema](interface/tma-k_register.png)Format of the data to be provided to the knowledge component.*

Each message includes:

* `address` -- an objet with the information about the ActuatorAPI
	* `baseUrl` --- the base URL of where the operations are available
* `PubKey` -- generated once by the ActuatorAPI

-

Additional, the following message should be sent to `TMA_Knowledge` to store all the possible actions. It follows the `JSON` schema specified in [tma-k_schema-actions](interface/atmosphere_tma-k_schema-actions.json), which is currently in the version `0.1`.

The figure below presents a representation of this schema, which is also explained below.

*![Knowledge Register Schema](interface/tma-k_actions.png)Format of the data to be provided to the knowledge component to add the actions.*

Each message includes:

* `actions` -- a list of actions provided by the Actuator
	* `action` -- name of the action to be performed
	* `path` -- path of the service to execute the operation
	* `configuration` -- list of expected attributes to execute the operation

TBD: Configuration attributes!