package services;

import connections.Connection;

/*
 * Interfaz que se usa para enmascarar a todas las implementaciones de los servicios.
 */
public interface Service {
	public void pushMessage(String title, String body);

	public String getServiceType();

	public String getAlias();

	void addConnection(Connection connection);

	void removeConnection(Connection connectioin);
}