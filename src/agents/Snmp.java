package agents;

import java.io.IOException;
import java.net.UnknownHostException;

import org.snmp4j.CommandResponder;
import org.snmp4j.CommandResponderEvent;

import communications.TrapReceiver;

public class Snmp implements CommandResponder {

	public Snmp() throws UnknownHostException, IOException {
		if (!TrapReceiver.isRunning()) {
			TrapReceiver.init();
		}

		TrapReceiver.add(this);

	}

	public void processPdu(CommandResponderEvent event) {
		System.out.println("Recibido TRAP de: "
				+ event.getPeerAddress().toString());
	}
}