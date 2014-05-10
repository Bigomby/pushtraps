package communications;

import java.io.IOException;
import java.net.UnknownHostException;

import org.snmp4j.CommandResponder;
import org.snmp4j.MessageDispatcherImpl;
import org.snmp4j.Snmp;
import org.snmp4j.TransportMapping;
import org.snmp4j.mp.MPv1;
import org.snmp4j.mp.MPv2c;
import org.snmp4j.mp.MPv3;
import org.snmp4j.mp.MessageProcessingModel;
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

// TODO Comprobar que funciona

public class TrapReceiver {

	private static MultiThreadedMessageDispatcher dispatcher;
	private static Snmp snmp = null;
	private static Address listenAddress;
	private static ThreadPool threadPool;
	private static int activeAgents = 0;

	@SuppressWarnings({ "rawtypes", "static-access" })
	public static void init() throws UnknownHostException, IOException {
		threadPool = ThreadPool.create("Trap", 2);
		dispatcher = new MultiThreadedMessageDispatcher(threadPool,
				new MessageDispatcherImpl());
		listenAddress = GenericAddress.parse(System.getProperty(
				"snmp4j.listenAddress", "udp:0.0.0.0/5050"));
		TransportMapping transport;
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
		USM usm = new USM(
				SecurityProtocols.getInstance(),
				new OctetString(
						((MPv3) snmp
								.getMessageProcessingModel(MessageProcessingModel.MPv3))
								.createLocalEngineID()), 0);
		SecurityModels.getInstance().addSecurityModel(usm);
		snmp.listen();
	}
	
	// TODO Implementar un método que asocie agente con ip suscrita
	public static void add(CommandResponder commandResponder, String ip)
			throws UnknownHostException, IOException {
		
		if (activeAgents == 0) {
			init();
		}

		snmp.addCommandResponder(commandResponder);
		activeAgents++;
	}

	public static void remove(CommandResponder commandResponder)
			throws IOException {
		snmp.removeCommandResponder(commandResponder);
		activeAgents--;

		if (activeAgents == 0) {
			snmp.close();
		}
	}
}