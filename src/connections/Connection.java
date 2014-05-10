package connections;

import java.util.Iterator;
import java.util.List;

import services.Service;
import agents.Agent;

public class Connection {

	private List<Agent> agents;
	private List<Service> services;
	private boolean active = true;

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

	void addAgent(Agent agent) {
		agents.add(agent);
	}

	Agent getAgent(int index) {
		return agents.get(index);
	}

	void removeAgent(Agent agent) {
		agents.remove(agent);
	}

	void addService(Service service) {
		services.add(service);
	}

	void removeService(Service service) {
		services.remove(service);
	}

	void remove() throws Throwable {
		this.finalize();
	}

	void pause() {
		active = false;
	}

	void start() {
		active = true;
	}

}
