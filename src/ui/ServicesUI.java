package ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import services.Service;

public class ServicesUI {

	List<Service> services;
	PushbulletUI pushbulletUI;
	TwitterUI twitterUI;
	EmailUI emailUI;

	ServicesUI(List<Service> services) {
		this.services = services;

		pushbulletUI = new PushbulletUI(services);
		twitterUI = new TwitterUI();
		emailUI = new EmailUI();

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
			System.out.println("");
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
					list();
					break;
				case 2:
					add();
					break;
				case 3:
					test();
					break;
				case 4:
					remove();
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
	private void add() {

		Integer option;
		String s;

		UI.printHeader();
		System.out.println("Selecciona un servicio a continuación:");
		System.out.println("1)	Pushbullet");
		System.out.println("2)	e-mail");
		System.out.println("3)	Twitter");
		System.out.println("");
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
				pushbulletUI.add();
				break;
			case 2:
				emailUI.add();
				break;
			case 3:
				twitterUI.add();
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

	/* Muestra por pantalla la lista de cuentas de Pushbullet configuradas */
	private void list() {
		if (services.isEmpty()) {
			UI.setError("No hay servicios activos");
		} else {
			UI.clearError();
			Service service;
			int i;

			UI.printHeader();
			System.out.println(services.size() + " servicios activos:");
			System.out.println("");

			for (i = 0; i < services.size(); i++) {
				service = services.get(i);
				System.out.println((i + 1) + ") Servicio: "
						+ service.getType() + "  Alias: "
						+ service.getAlias());
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
	private void test() {
		final String title = "PushTraps - Prueba";
		final String body = "Esto es una notificación de prueba de Pushtraps. Si puedes ver este mensaje,"
				+ " la aplicación está funcionando";
		BufferedReader bufferRead;
		Service service;
		int i;
		int option;

		try {
			UI.printHeader();
			System.out.println(services.size() + " servicios activos:");
			System.out.println("");

			for (i = 0; i < services.size(); i++) {
				service = services.get(i);
				System.out.println((i + 1) + ") Servicio: "
						+ service.getType() + "  Alias: "
						+ service.getAlias());
			}

			System.out.println("");
			System.out.print("Elige el servicio para probar: ");

			bufferRead = new BufferedReader(new InputStreamReader(System.in));
			option = Integer.parseInt(bufferRead.readLine());
			service = services.get(option - 1);
			service.pushMessage(title, body);
			UI.setInfo("Enviado mensaje de prueba a " + service.getAlias());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	void remove() {

		try {
			Service service;
			int i;
			int option;

			UI.printHeader();
			System.out.println(services.size() + " servicios activos:");
			System.out.println("");

			for (i = 0; i < services.size(); i++) {
				service = services.get(i);
				System.out.println((i + 1) + ") Servicio: "
						+ service.getType() + "  Alias: "
						+ service.getAlias());
			}

			System.out.println("");
			System.out.print("Elige el servicio para eliminar: ");

			BufferedReader bufferRead = new BufferedReader(
					new InputStreamReader(System.in));
			option = Integer.parseInt(bufferRead.readLine());
			
			services.remove(option-1);
			// Habría que ver qué pasa si no lo elimino de las conexiones

		} catch (NumberFormatException e) {
			UI.setError("Opción no válida");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}