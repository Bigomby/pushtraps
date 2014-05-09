package ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import communications.PushbulletAPI;

import services.Pushbullet;
import services.Service;

public class ServicesUI {

	List<Service> services;
	
	ServicesUI(List<Service> services){
		this.services = services;
	}
	
	/* Interfaz de usuario que muestra el menú principal de servicios */
	public void menu() {

		Integer option;
		String s;
		boolean exit = false;

		while (!exit) {
			UI.printHeader();
			System.out.println("Selecciona una acción a continuación:");
			System.out.println("1)	Ver servicios activos");
			System.out.println("2)	Añadir un servicio");
			System.out.println("3)	Probar un servicio existente");
			System.out.println("4)	Eliminar un servicio existente");
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
					showPushers();
					break;
				case 2:
					addService();
					break;
				case 3:
					testPusher();
					break;
				case 4:
					// TODO deleteService();
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

	/* Añade servicios de notificadores */
	private void addService() {

		Integer option;
		String s;

		UI.printHeader();
		System.out.println("Selecciona un servicio a continuación:");
		System.out.println("1)	Pushbullet");
		System.out.println("2)	e-mail");
		System.out.println("3)	Twitter");
		System.out.println("0)	Atrás");
		System.out.println("");
		System.out.print("Introduce una opción: ");

		try {
			BufferedReader bufferRead = new BufferedReader(
					new InputStreamReader(System.in));
			s = bufferRead.readLine();

			option = Integer.parseInt(s);

			switch (option) {
			case 0:
				break;
			case 1:
				addPushbullet();
				break;
			case 2:
				// TODO addMailAccount();
				break;
			case 3:
				// TODO addTwitterAccount();
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

	/* Añade un dispositivo de Pushbullet */
	private void addPushbullet() {

		String apiKey;
		BufferedReader bufferRead;
		ArrayList<Pushbullet> devices;
		Pushbullet device;
		String s;
		int option;
		int i = 0;
		String alias;

		UI.clearError();

		try {
			System.out.print("Introduce la \"API_KEY\" del usuario: ");
			bufferRead = new BufferedReader(new InputStreamReader(System.in));
			apiKey = bufferRead.readLine();
			devices = PushbulletAPI.getDevices(apiKey);

			if (devices.size() == 0) {
				UI.setError("No se han encontrado dispositivos.");
			} else {
				UI.printHeader();
				System.out.println(devices.size()
						+ " dispositivos disponibles:");
				System.out.println("");

				for (i = 0; i < devices.size(); i++) {
					device = devices.get(i);
					System.out.println((i + 1) + ") " + device.getModel()
							+ "  Iden: " + device.getIden());
				}

				System.out.println("");
				System.out.print("Elige un dispositivo: ");
				bufferRead = new BufferedReader(
						new InputStreamReader(System.in));
				s = bufferRead.readLine();

				System.out.println("");
				System.out.print("Introduce un alias para el dispositivo: ");
				bufferRead = new BufferedReader(
						new InputStreamReader(System.in));
				alias = bufferRead.readLine();

				option = Integer.parseInt(s);
				devices.get(option - 1).setApiKey(apiKey); 
				devices.get(option - 1).setAlias(alias);
				services.add(devices.get(option-1));
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/* Muestra por pantalla la lista de cuentas de Pushbullet configuradas */
	private void showPushers() {
		if (services.isEmpty()) {
			UI.setError("No hay servicios activos");
		} else {
			UI.clearError();
			Service service;
			int i;

			UI.printHeader();
			System.out.println(services.size()
					+ " servicios activos:");
			System.out.println("");

			for (i = 0; i < services.size(); i++) {
				service = services.get(i);
				System.out.println((i+1) + ") Servicio: " + service.getServiceType()
						+ "  Alias: " + service.getAlias());
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

	/* Realiza una prueba de un notificador enviado un mensaje de prueba */
	private void testPusher() {
		final String title = "PushTraps - Prueba";
		final String body = "Esto es una notificación de prueba de Pushtraps. Si puedes ver este mensaje,"
				+ " la aplicación está funcionando";
		BufferedReader bufferRead;
		Service service;
		int i;
		int option;

		try {
			UI.printHeader();
			System.out.println(services.size()
					+ " servicios activos:");
			System.out.println("");

			for (i = 0; i < services.size(); i++) {
				service = services.get(i);
				System.out.println((i+1) + ") Servicio: " + service.getServiceType()
						+ "  Alias: " + service.getAlias());
			}
			
			System.out.println("");
			System.out.print("Elige el servicio para probar: ");
			
			bufferRead = new BufferedReader(new InputStreamReader(System.in));
			option = Integer.parseInt(bufferRead.readLine());
			service = services.get(option-1);
			service.pushMessage(title, body);
			UI.setInfo("Enviado mensaje de prueba a " + service.getAlias());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}