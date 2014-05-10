package agents;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.snmp4j.PDU;
import org.snmp4j.PDUv1;

import communications.TrapReceiver;

import connections.Connection;

public class SnmpAgent implements Agent {

	List<Connection> connections;
	String ip;
	String alias;

	public SnmpAgent(String ip, String alias) throws UnknownHostException,
			IOException {
		this.ip = ip;
		this.alias = alias;
		connections = new LinkedList<Connection>();
	}

	public void listen() throws UnknownHostException, IOException {
		TrapReceiver.add(this, ip);
	}

	public void exit() throws IOException, InterruptedException {
		TrapReceiver.remove(this);
	}

	public void sendTrap(PDU pdu, String ip) {

		PDUv1 trap = null;
		String generic = null;
		long minutes;

		if (pdu.getType() == PDU.V1TRAP) {
			trap = new PDUv1((PDUv1) pdu);
			minutes = trap.getTimestamp() /1000 / 60;

			switch (trap.getGenericTrap()) {
			case 0:
				generic = "COLDSTART";
				break;
			case 1:
				generic = "WARMSTART";
				break;
			case 2:
				generic = "LINKDOWN";
				break;
			case 3:
				generic = "COLDSTART";
				break;
			case 4:
				generic = "AUTHENTICATIONFAILURE";
				break;
			case 6:
				generic = "ENTERPRISE_SPECIFIC";
				break;
			default:
				break;
			}

			String title = "Trap recibida de: " + alias;

			String body = new StringBuilder()
					.append("Dirección IP: " + ip + "\n")
					.append("Uptime: " + minutes + " minutos\n")
					.append("OID de Empresa: " + trap.getEnterprise() + "\n")
					.append("Tipo genérico: " + generic + "\n")
					.append("Tipo específico: " + trap.getSpecificTrap() + "\n")
					.toString();

			Iterator<Connection> it = connections.iterator();

			while (it.hasNext()) {
				it.next().forward(title, body);
			}
		}
	}

	public void addConnection(Connection connection) {
		connections.add(connection);
		try {
			listen();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void removeConnection(Connection connection) {
		connections.remove(connection);
		if (connections.isEmpty()) {
			try {
				exit();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public String getType() {
		return "snmp";
	}

	public String getAlias() {
		return alias;
	}

	public String getIP() {
		return ip;
	}
}