package ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import services.Service;
import agents.Agent;
import connections.Connection;

public class ConnectionsUI {

	private List<Connection> connections;
	private List<Agent> agents;
	private List<Service> services;

	public ConnectionsUI(List<Connection> connections, List<Agent> agents,
			List<Service> services) {
		this.connections = connections;
		this.agents = agents;
		this.services = services;
	}

	void menu() {
		Integer option;
		String s;
		boolean exit = false;

		while (!exit) {
			UI.printHeader();
			System.out.println("Selecciona una acción a continuación:");
			System.out.println("1)	Ver conexiones establecidas");
			System.out.println("2)	Establecer una conexión");
			System.out.println("3)	Eliminar una conexión");
			System.out.println("4)	Pausar una conexión");
			System.out.println("5)	Reanudar una conexión");
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
				case 4:
					pause();
					break;
				case 5:
					start();
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

	private void list() {
		if (connections.isEmpty()) {
			UI.setError("No hay conexiones activas");
		} else {
			UI.clearError();
			Connection connection;
			int i;

			UI.printHeader();
			System.out.println(connections.size() + " conexiones activas:");
			System.out.println("");

			for (i = 0; i < connections.size(); i++) {
				connection = connections.get(i);
				System.out.println((i + 1) + ") Agente: " + "  Alias: "
						+ connection.getAlias());
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

	private void add() {

		BufferedReader bufferRead = new BufferedReader(new InputStreamReader(
				System.in));
		Agent agent;
		Connection connection;
		Service service;
		String s;
		int i;

		try {
			UI.printHeader();
			System.out.print("Introduce un alias para la conexión: ");
			String alias = bufferRead.readLine();
			String[] options;

			connection = new Connection(alias);

			// Selección de agente

			UI.printHeader();
			System.out.println("Lista de agentes disponibiles:");

			System.out.println(agents.size() + " agentes activos:");
			System.out.println("");

			for (i = 0; i < agents.size(); i++) {
				agent = agents.get(i);
				System.out.println((i + 1) + ") Agente: " + agent.getType()
						+ "  Alias: " + agent.getAlias() + " IP: "
						+ agent.getIP());
			}
			System.out
					.print("Selecciona los agentes separados por coma que deseas agregar a la conexión: ");

			s = bufferRead.readLine();

			options = s.split(",");

			for (i = 0; i < options.length; i++) {
				connection.addAgent(agents.get(Integer.parseInt(options[i])));
			}

			// Selección de servicio

			UI.printHeader();
			System.out.println("Lista de servicios disponibiles:");

			System.out.println(services.size() + " servicios activos:");
			System.out.println("");

			for (i = 0; i < services.size(); i++) {
				service = services.get(i);
				System.out.println((i + 1) + ") Servicio: " + service.getType()
						+ "  Alias: " + service.getAlias());
			}
			System.out
					.print("Selecciona los servicios separados por coma que deseas agregar a la conexión: ");

			s = bufferRead.readLine();

			options = s.split(",");

			for (i = 0; i < options.length; i++) {
				connection
						.addService(services.get(Integer.parseInt(options[i])));
			}

		} catch (NumberFormatException e) {
			UI.setError("Opción no válida");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void remove() {
		try {
			Connection connection;
			int i;
			int option;

			UI.printHeader();
			System.out.println(connections.size() + " conexiones activas:");
			System.out.println("");

			for (i = 0; i < connections.size(); i++) {
				connection = connections.get(i);
				System.out.println((i + 1) + ") Conexión: "
						+ connection.getAlias());
			}

			System.out.println("");
			System.out.print("Elige la conexión para eliminar: ");

			BufferedReader bufferRead = new BufferedReader(
					new InputStreamReader(System.in));
			option = Integer.parseInt(bufferRead.readLine());

			connections.get(option - 1).clean();
			connections.remove(option - 1);

		} catch (NumberFormatException e) {
			UI.setError("Opción no válida");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void pause() {
		try {
			Connection connection;
			int i;
			int option;

			UI.printHeader();
			System.out.println(connections.size() + " conexiones activas:");
			System.out.println("");

			for (i = 0; i < connections.size(); i++) {
				connection = connections.get(i);
				System.out.println((i + 1) + ") Conexión: "
						+ connection.getAlias() + " Estado: " + connection.getStatus());
			}

			System.out.println("");
			System.out.print("Elige la conexión para pausar: ");

			BufferedReader bufferRead = new BufferedReader(
					new InputStreamReader(System.in));
			option = Integer.parseInt(bufferRead.readLine());

			connections.get(option - 1).pause();

		} catch (NumberFormatException e) {
			UI.setError("Opción no válida");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void start() {
		try {
			Connection connection;
			int i;
			int option;

			UI.printHeader();
			System.out.println(connections.size() + " conexiones activas:");
			System.out.println("");

			for (i = 0; i < connections.size(); i++) {
				connection = connections.get(i);
				System.out.println((i + 1) + ") Conexión: "
						+ connection.getAlias() + " Estado: " + connection.getStatus());
			}

			System.out.println("");
			System.out.print("Elige la conexión para reactivar: ");

			BufferedReader bufferRead = new BufferedReader(
					new InputStreamReader(System.in));
			option = Integer.parseInt(bufferRead.readLine());

			connections.get(option - 1).start();

		} catch (NumberFormatException e) {
			UI.setError("Opción no válida");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
