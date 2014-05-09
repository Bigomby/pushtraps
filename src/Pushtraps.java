import java.util.LinkedList;
import java.util.List;

import services.Service;
import ui.UI;
import agents.Agent;
import connections.Connection;

/*
 * Programa principal de Pushtraps:
 * 
 * Instancia los tres componentes del programa:
 *   - Services: para enviar notificaciones a los gestores
 *   - Agents: para recibir eventos de los agentes
 *   - Connections: para conectar Agentes con Pushers
 *
 *	Después llama a la interfaz de usuario para la configuración.
 *
 */
public class Pushtraps {
	
	public static void main(String[] args) {
		
		List<Service> services = new LinkedList<Service>();
		List<Agent> agents = new LinkedList<Agent>();
		List<Connection> connections = new LinkedList<Connection>();

		UI ui = new UI(services, agents, connections);
		ui.menu();
	}
}
