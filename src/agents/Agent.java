package agents;

import connections.Connection;

/*
 * Interfaz que se usa para enmascarar a todas las implementaciones de los agentes.
 */

public interface Agent {
	void listen() throws Exception;
	
	void exit() throws Exception;

	void addConnection(Connection connection);

	void removeConnection(Connection connectioin);
	
	String getType();
	
	String getAlias();
	
	String getIP();
}
