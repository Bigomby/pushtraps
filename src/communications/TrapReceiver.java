package communications;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.snmp4j.CommandResponder;
import org.snmp4j.CommandResponderEvent;
import org.snmp4j.MessageDispatcherImpl;
import org.snmp4j.Snmp;
import org.snmp4j.TransportMapping;
import org.snmp4j.mp.MPv1;
import org.snmp4j.mp.MPv2c;
import org.snmp4j.mp.MPv3;
import org.snmp4j.security.SecurityModels;
import org.snmp4j.security.SecurityProtocols;
import org.snmp4j.security.USM;
import org.snmp4j.smi.Address;
import org.snmp4j.smi.GenericAddress;
import org.snmp4j.smi.OctetString;
import org.snmp4j.smi.TcpAddress;
import org.snmp4j.smi.UdpAddress;
import org.snmp4j.transport.DefaultTcpTransportMapping;
import org.snmp4j.transport.DefaultUdpTransportMapping;
import org.snmp4j.util.MultiThreadedMessageDispatcher;
import org.snmp4j.util.ThreadPool;

import agents.SnmpAgent;

public class TrapReceiver implements Runnable, CommandResponder {

	private MultiThreadedMessageDispatcher dispatcher;
	private static Snmp snmp = null;
	private Address listenAddress;
	private ThreadPool threadPool;
	private static Map<String, SnmpAgent> snmpAgents;
	private static Thread t;
	private static int activeAgents = 0;

	static {
		snmpAgents = new HashMap<String, SnmpAgent>();
	}

	public static void main(String[] args) {
		new TrapReceiver();
	}

	TrapReceiver() {
		run();

	}

	@SuppressWarnings({ "rawtypes" })
	public void run() {
		threadPool = ThreadPool.create("Trap", 2);
		dispatcher = new MultiThreadedMessageDispatcher(threadPool,
				new MessageDispatcherImpl());
		listenAddress = GenericAddress.parse(System.getProperty(
				"snmp4j.listenAddress", "udp:0.0.0.0/20162"));
		TransportMapping transport;

		try {
			if (listenAddress instanceof UdpAddress) {
				transport = new DefaultUdpTransportMapping(
						(UdpAddress) listenAddress);
			} else {
				transport = new DefaultTcpTransportMapping(
						(TcpAddress) listenAddress);
			}

			snmp = new Snmp(dispatcher, transport);
			snmp.getMessageDispatcher().addMessageProcessingModel(new MPv1());
			snmp.getMessageDispatcher().addMessageProcessingModel(new MPv2c());
			snmp.getMessageDispatcher().addMessageProcessingModel(new MPv3());
			USM usm = new USM(SecurityProtocols.getInstance(), new OctetString(
					MPv3.createLocalEngineID()), 0);
			SecurityModels.getInstance().addSecurityModel(usm);
			snmp.addCommandResponder(this);
			System.out.println("A la escucha de TRAPs");
			snmp.listen();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static void add(SnmpAgent snmpAgent, String ip)
			throws UnknownHostException, IOException {

		if (activeAgents == 0) {
			t = new Thread(new TrapReceiver());
			t.setDaemon(true);
			t.start();
		}

		snmpAgents.put(ip, snmpAgent);
		activeAgents++;
	}

	public static void remove(SnmpAgent snmpAgent) throws IOException,
			InterruptedException {

		snmpAgents.remove(snmpAgent).getIP();
		activeAgents--;

		if (activeAgents == 0) {
			snmp.close();
			t.join();
		}
	}

	public void processPdu(CommandResponderEvent event) {

		String ip;

		System.out.println("Recibido un TRAP");

		String[] address = event.getPeerAddress().toString().split(":");
		ip = address[0];

		Iterator<Entry<String, SnmpAgent>> it = snmpAgents.entrySet()
				.iterator();

		while (it.hasNext()) {
			Map.Entry<String, SnmpAgent> agent = (Map.Entry<String, SnmpAgent>) it
					.next();
			if (agent.getKey() == ip) {
				System.out.println("Enviando TRAP...");
				agent.getValue().sendTrap(event.getPDU(), ip);
			} else {
				System.out
						.println("No se ha encontrado ningún agente para esta IP");
			}
		}
	}
}