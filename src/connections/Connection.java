package connections;

import java.util.Iterator;
import java.util.List;

import services.Service;
import agents.Agent;

public class Connection {

	private String alias;
	private List<Service> services;
	private boolean active = true;

	public Connection(String alias) {
		this.alias = alias;
	}

	public void forward(String ip, String message) {

		if (active) {
			Iterator<Service> it = services.iterator();
			Service service;

			while (it.hasNext()) {
				service = it.next();
				service.pushMessage(ip, message);
			}
		}
	}

	public void addAgent(Agent agent) {
		agent.addConnection(this);
	}

	public void removeAgent(Agent agent) {
		agent.removeConnection(this);
	}

	public void addService(Service service) {
		services.add(service);
	}

	public void removeService(Service service) {
		services.remove(service);
	}

	public void clean() {
		// TODO Eliminarse de los agentes
	}

	public void pause() {
		active = false;
	}

	public void start() {
		active = true;
	}

	public String getAlias() {
		return alias;
	}

	public String getStatus() {
		if (active) {
			return "Active";
		} else {
			return "Paused";
		}
	}
}