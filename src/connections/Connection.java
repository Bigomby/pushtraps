package connections;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import services.Service;
import agents.Agent;

public class Connection {

	private String alias;
	private List<Agent> agents;
	private List<Service> services;
	private boolean active = true;

	public Connection(String alias) {
		this.alias = alias;
		agents = new LinkedList<Agent>();
		services = new LinkedList<Service>();
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
		agents.add(agent);
		if(this instanceof Connection){
			agent.addConnection(this);
		} else {
			System.out.println(this.toString());
		}

	}

	public void removeAgent(Agent agent) {
		agents.remove(agent);
		agent.removeConnection(this);
	}

	public void addService(Service service) {
		services.add(service);
	}

	public void removeService(Service service) {
		services.remove(service);
	}

	public void clean() {
		Iterator<Agent> it = agents.iterator();
		Agent agent;
		
		while (it.hasNext()) {
			agent = it.next();
			agent.removeConnection(this);
		}
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