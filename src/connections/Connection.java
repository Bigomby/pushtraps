package connections;

import java.util.Iterator;
import java.util.List;

import services.Service;
import agents.Agent;



public class Connection {
	
	private List<Agent> agents;
	private List<Service> services;
	
	public void forward(String ip, String message){
		Iterator<Service> it = services.iterator();
		Service service;
		
		while(it.hasNext()){
			service = it.next();
			service.pushMessage(ip, message);
		}
	}

	void addAgent(Agent agent){
		// TODO Crear el método para añadir agentes 
	}

	Agent getAgent(int index) {
		return agents.get(index);
	}
	
	void removeAgent(Agent agent){
		// TODO Crear el método para eliminar agentes
	}
	
	void addService(Service service){
		// TODO Crear el método para añadir servicios
	}
	
	void removeService(Service service){
		// TODO Crear el método para eliminar servicios
	}
	
	void remove(){
		// TODO Crear el método para eliminar la conexión
	}
	
	void pause(){
		// TODO Crear el método para pausar la conexión
	}
	
	void start(){
		// TODO Crear el método para iniciar la conexión
	}

}
