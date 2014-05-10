package agents;

import java.io.IOException;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
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
		Date date;
		DateFormat formatter = new SimpleDateFormat("HH:mm:ss");
		String generic = null;

		if (pdu.getType() == PDU.V1TRAP) {
			trap = new PDUv1((PDUv1) pdu);
			date = new Date(trap.getTimestamp());

			switch (trap.getSpecificTrap()) {
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

			String title = "Trap recibida de: " + ip;
			StringBuffer body = new StringBuffer("TRAP generada en: "
					+ formatter.format(date));
			body.append("TRAP generada en: " + formatter.format(date));
			body.append("OID de Empresa: " + trap.getEnterprise());
			body.append("Tipo genérico: " + generic);
			body.append("Tipo específico: " + trap.getSpecificTrap());
			
			Iterator<Connection> it = connections.iterator();
			
			while(it.hasNext()){
				it.next().forward(title, body.toString());
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