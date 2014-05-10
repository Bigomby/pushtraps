package ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import agents.Agent;

public class AgentsUI {

	private List<Agent> agents;
	SnmpUI snmpUI;

	public AgentsUI(List<Agent> agents) {
			this.agents = agents;
			
			snmpUI = new SnmpUI(agents);
	}

	void menu() {

		Integer option;
		String s;
		boolean exit = false;

		while (!exit) {
			UI.printHeader();
			System.out.println("Selecciona una acción a continuación:");
			System.out.println("1)	Ver agentes configurados");
			System.out.println("2)	Añadir un agente");
			System.out.println("3)	Eliminar un agente");
			System.out.println("");
			System.out.println("0)	Atrás");
			System.out.println("");
			System.out.print("Introduce una opción: ");

			BufferedReader bufferRead = new BufferedReader(
					new InputStreamReader(System.in));

			try {
				s = bufferRead.readLine();
				option = Integer.parseInt(s);

				switch (option) {
				case 0:
					exit = true;
					break;
				case 1:
					list();
					break;
				case 2:
					add();
					break;
				case 3:
					remove();
					break;
				default:
					UI.setError("Opción no válida");
					break;
				}
			} catch (NumberFormatException e) {
				UI.setError("Opción no válida");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		UI.clearError();
	}

	private void remove() {
		try {
			Agent agent;
			int i;
			int option;

			UI.printHeader();
			System.out.println(agents.size() + " agentes activos:");
			System.out.println("");

			for (i = 0; i < agents.size(); i++) {
				agent = agents.get(i);
				System.out.println((i + 1) + ") Agente: "
						+ agent.getType() + "  Alias: "
						+ agent.getAlias());
			}

			System.out.println("");
			System.out.print("Elige el agente para eliminar: ");

			BufferedReader bufferRead = new BufferedReader(
					new InputStreamReader(System.in));
			option = Integer.parseInt(bufferRead.readLine());
			
			agents.remove(option-1);
			// Habría que ver qué pasa si no lo elimino de las conexiones

		} catch (NumberFormatException e) {
			UI.setError("Opción no válida");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	private void add() {
		Integer option;
		String s;

		UI.printHeader();
		System.out.println("Selecciona un agente a continuación:");
		System.out.println("1)	Snmp");
		System.out.println("");
		System.out.println("0)	Atrás");
		System.out.println("");
		System.out.print("Introduce una opción: ");

		try {
			BufferedReader bufferRead = new BufferedReader(
					new InputStreamReader(System.in));
			s = bufferRead.readLine();

			option = Integer.parseInt(s);

			switch (option) {
			case 0:
				break;
			case 1:
				snmpUI.add();
				break;
			default:
				UI.setError("Opción no válida");
				break;
			}
		} catch (NumberFormatException e) {
			UI.setError("Opción no válida");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void list() {
		if (agents.isEmpty()) {
			UI.setError("No hay agents activos");
		} else {
			UI.clearError();
			Agent agent;
			int i;

			UI.printHeader();
			System.out.println(agents.size() + " agentes activos:");
			System.out.println("");

			for (i = 0; i < agents.size(); i++) {
				agent = agents.get(i);
				System.out.println((i + 1) + ") Agente: "
						+ agent.getType() + "  Alias: "
						+ agent.getAlias());
			}

			System.out.println("");
			System.out.println("Pulsa intro para volver... ");
			BufferedReader buffer = new BufferedReader(new InputStreamReader(
					System.in));
			try {
				buffer.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
