package communications;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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
	private static Map<String, List<SnmpAgent>> snmpAgents;
	private static Thread t;

	static {
		snmpAgents = new HashMap<String, List<SnmpAgent>>();
	}

	private static int activeAgents = 0;

	@SuppressWarnings({ "rawtypes" })
	public void run() {
		threadPool = ThreadPool.create("Trap", 2);
		dispatcher = new MultiThreadedMessageDispatcher(threadPool,
				new MessageDispatcherImpl());
		listenAddress = GenericAddress.parse(System.getProperty(
				"snmp4j.listenAddress", "udp:0.0.0.0/5050"));
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

		List<SnmpAgent> activeSnmp = snmpAgents.get(ip);

		if (activeSnmp != null) {
			activeSnmp.add(snmpAgent);
		} else {
			List<SnmpAgent> newSnmpList = new LinkedList<SnmpAgent>();
			newSnmpList.add(snmpAgent);
			snmpAgents.put(ip, newSnmpList);
		}

		activeAgents++;
	}

	public static void remove(SnmpAgent snmpAgent) throws IOException, InterruptedException {

		Iterator<String> it = snmpAgents.keySet().iterator();

		while (it.hasNext()) {
			String ip = it.next();
			List<SnmpAgent> activeSnmps = snmpAgents.get(ip);
			activeSnmps.remove(snmpAgent);
		}

		activeAgents--;

		if (activeAgents == 0) {
			snmp.close();
			t.join();
		}
	}

	public void processPdu(CommandResponderEvent event) {
		System.out.println(event.toString());

		String activeIp;
		String ip = event.getPeerAddress().toString();
		Iterator<String> it = snmpAgents.keySet().iterator();

		while (it.hasNext()) {
			activeIp = it.next();

			if (ip == activeIp) {
				Iterator<SnmpAgent> it2 = snmpAgents.get(ip).iterator();

				while (it2.hasNext()) {
					SnmpAgent activeSnmp = it2.next();
					activeSnmp.send(event.toString());
				}
			}
		}
	}
}