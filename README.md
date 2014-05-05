# PushTraps

Java application that listens and forwards SNMP TRAPs to multiple third party push services like email, Twitter or Pushbullet.

By now, the following services are supported:

- Pushbullet
- Twitter
- Email

## How it works

We have three components:

- SNMP agents
- Push services
- Connections

### SNMP Agents

You can set the app for listen SNMP TRAPS from differents IP addresses. When a TRAP is received, it forwards it to configured push services.

The app uses [SNMP4J library](http://www.snmp4j.org/).

### Push services

Services where the TRAPs are forwarded. The supported devices are listed above.

### Connections

After you configure an agent and a service you need to set a connection. Connections let you forward TRAPs from a specific agent to a specific push service.
