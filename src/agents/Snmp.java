package agents;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.List;

import org.snmp4j.CommandResponder;
import org.snmp4j.CommandResponderEvent;

import communications.TrapReceiver;
import connections.Connection;

public class Snmp implements Agent, CommandResponder {

	List<Connection> connections;
	String ip;

	public Snmp(String ip) {
		this.ip = ip;
	}

	public void add() throws UnknownHostException, IOException {
		TrapReceiver.add(this, ip);
	}

	public void remove() throws IOException {
		TrapReceiver.remove(this);
	}

	public void processPdu(CommandResponderEvent event) {
		// TODO Método para procesar los TRAPs
		System.out.println("Recibido TRAP de: "
				+ event.getPeerAddress().toString());
	}

	public void send(String message) {
		// TODO Método para enviar TRAPs a servicios

	}

	public void addConnection(Connection connection) {
		// TODO Método para añadir conexiones a agentes

	}

	public void removeConnection(Connection connectioin) {
		// TODO Método para eliminar conexiones de agentes

	}
}