# PushTraps

Java application that listens and forwards SNMP TRAPs to multiple third party push services like email, Twitter or Pushbullet.

By now, the following services are supported:

- Pushbullet
- Twitter // TODO
- Email // TODO

You can download the application on https://github.com/Bigomby/pushtraps/releases

## How it works

We have three components:

- SNMP agents
- Push services
- Connections

### SNMP Agents

You can set the app for listen SNMP TRAPS from differents IP addresses. When a TRAP is received, is forwarded to configured push services.

The app uses [SNMP4J library](http://www.snmp4j.org) for the communication with the SNAMPs agents.

### Push services

Services to forward the TRAPs. The currently supported devices are listed above.

### Connections

After you set an agent and a service you need to set a connection. Connections let you forward TRAPs from a specific agent to a specific push service.
