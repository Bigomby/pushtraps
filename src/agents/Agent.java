package agents;

import connections.Connection;

/*
 * Interfaz que se usa para enmascarar a todas las implementaciones de los agentes.
 */

public interface Agent {
	void send(String message);

	void addConnection(Connection connection);

	void removeConnection(Connection connectioin);
	
	String getType();
}
