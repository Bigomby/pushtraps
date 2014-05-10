package ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import agents.Agent;
import agents.SnmpAgent;

public class SnmpUI {

	private List<Agent> agents;

	SnmpUI(List<Agent> agents) {
		this.agents = agents;
	}

	void add() throws IOException {
		String ip;
		BufferedReader bufferRead;

		UI.clearError();

		System.out.print("Introduce la dirección IP del agente: ");
		bufferRead = new BufferedReader(new InputStreamReader(System.in));
		ip = bufferRead.readLine();
		// TODO Comprobar dirección IP válida
		
		SnmpAgent snmpAgent = new SnmpAgent(ip);
		agents.add(snmpAgent);
		
	}
}
