package ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import services.Service;
import agents.Agent;
import connections.Connection;

public class UI {

	static final String CLS = "\033[2J\033[1;1H";
	static String info = "";
	static final String HEADER = "\033[2J\033[1;1H\n------ PushTraps -------\n------------------------\n\n";

	private ServicesUI servicesUI;
	private AgentsUI agentsUI;
	private ConnectionsUI connectionsUI;
	
	public UI(List<Service> services, List<Agent> agents, List<Connection> connections) {
		servicesUI = new ServicesUI(services);
		agentsUI = new AgentsUI(agents);
		connectionsUI = new ConnectionsUI(connections);
	}

	public void menu() {

		String s;
		Boolean exit = false;
		Integer option = null;

		while (!exit) {
			UI.printHeader();
			System.out.println("Selecciona una opción a continuación:");
			System.out.println("1)	Editar servicios");
			System.out.println("2)	Editar agentes");
			System.out.println("2)	Editar conexiones");
			System.out.println("0)	Salir");
			System.out.println("");
			System.out.print("Introduce una opción: ");

			try {
				BufferedReader bufferRead = new BufferedReader(
						new InputStreamReader(System.in));
				s = bufferRead.readLine();
				option = Integer.parseInt(s);

				switch (option) {
				case 0:
					System.out.print(CLS);
					exit = true;
					break;
				case 1:
					servicesUI.menu();
					break;
				case 2:
					agentsUI.menu();
					break;
				case 3:
					connectionsUI.menu();
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
	}
	
	static void printHeader() {
		System.out.print(UI.HEADER);
		System.out.println(UI.info);
		System.out.println("");
	}
	
	static void setInfo(String info){
		UI.info = info;
	}
	
	static void setError(String error){
		UI.info = "Error: " + error;
	}
	
	static void clearError() {
		info = "";
	}
}
