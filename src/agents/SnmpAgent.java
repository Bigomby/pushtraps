package agents;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Iterator;
import java.util.List;

import org.snmp4j.CommandResponderEvent;

import communications.TrapReceiver;

import connections.Connection;

public class SnmpAgent implements Agent {

	List<Connection> connections;
	String ip;
	String alias;

	public SnmpAgent(String ip, String alias) {
		this.ip = ip;
		this.alias = alias;
	}

	public void add() throws UnknownHostException, IOException {
		TrapReceiver.add(this, ip);
	}

	public void remove() throws IOException, InterruptedException {
		TrapReceiver.remove(this);
	}

	public void processPdu(CommandResponderEvent event) {
		System.out.println("Recibido TRAP de: "
				+ event.getPeerAddress().toString());
		System.out.println(event.toString());
		send(event.toString());
	}

	public void send(String message) {
		Iterator<Connection> it = connections.iterator();
		Connection connection;

		while (it.hasNext()) {
			connection = it.next();
			connection.forward(ip, message);
		}
	}

	public void addConnection(Connection connection) {
		connections.add(connection);
	}

	public void removeConnection(Connection connectioin) {
		connections.remove(connectioin);
	}

	public String getType() {
		return "snmp";
	}
}