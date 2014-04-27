package ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;

import services.PushbulletAPI;
import services.PushbulletDevice;
import services.Pusher;
import services.Pushers;

public class ServicesUI {

	static final String CLS = "\033[2J\033[1;1H";
	static String error = "";
	static final String HEADER = "\033[2J\033[1;1H\n------ PushTraps -------\n------------------------\n\n";

	/* Interfaz de usuario que muestra el menú principal de servicios */
	public static void servicesMenu() {

		Integer option;
		String s;
		boolean exit = false;

		while (!exit) {
			System.out.print(HEADER);
			System.out.println(error);
			System.out.println("");
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
					addPusher();
					break;
				case 3:
					testPusher();
					break;
				case 4:
					// TODO deleteService();
					break;
				default:
					error = "Opción no válida";
					break;
				}
			} catch (NumberFormatException e) {
				error = "Opción no válida";
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		clearError();
	}

	/* Añade servicios de notificadores */
	private static void addPusher() {

		Integer option;
		String s;

		System.out.print(HEADER);
		System.out.println(error);
		System.out.println("");
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
				addPushbulletDevice();
				break;
			case 2:
				// TODO addMailAccount();
				break;
			case 3:
				// TODO addTwitterAccount();
				break;
			default:
				error = "Opción no válida";
				break;
			}
		} catch (NumberFormatException e) {
			error = "Opción no válida";
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/* Añade un dispositivo de Pushbullet */
	private static void addPushbulletDevice() {

		String api_key;
		BufferedReader bufferRead;
		ArrayList<PushbulletDevice> devices;
		PushbulletDevice device;
		String s;
		int option;
		int i = 0;

		clearError();

		try {
			System.out.print("Introduce la \"API_KEY\" del usuario: ");
			bufferRead = new BufferedReader(new InputStreamReader(System.in));
			api_key = bufferRead.readLine();
			devices = PushbulletAPI.getDevices(api_key);

			System.out.print(HEADER);
			System.out.println(error);
			System.out.println("");
			System.out.println(devices.size() + " dispositivos disponibles:");
			System.out.println("");

			for (i = 0; i < devices.size(); i++) {
				device = devices.get(i);
				System.out.println((i + 1) + ") " + device.getModel()
						+ "  Iden: " + device.getIden());
			}

			System.out.println("");
			System.out.print("Elige un dispositivo: ");
			bufferRead = new BufferedReader(new InputStreamReader(System.in));

			s = bufferRead.readLine();
			option = Integer.parseInt(s);
			Pushers.addPusher(devices.get(option - 1));

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/* Muestra por pantalla la lista de cuentas de Pushbullet configuradas */
	private static void showPushers() {
		if (Pushers.getPushers().isEmpty()) {
			error = "No hay servicios activos";
		} else {
			clearError();
			Iterator<Pusher> it = Pushers.getPushers().iterator();
			Pusher pusher;
			int i = 1;

			System.out.print(HEADER);
			System.out.println(error);
			System.out.println("");
			System.out.println("Servicios activos:");
			System.out.println("");

			while (it.hasNext()) {
				pusher = (Pusher) it.next();
				System.out.println((i + 1) + ") Servicio: "
						+ pusher.getServiceType() + "  Alias: "
						+ pusher.getAlias());
				i++;
			}
			System.out.println("");
			System.out.println("Pulsa intro para volver...");
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
	private static void testPusher() {
		final String title = "PushTraps - Prueba";
		final String body = "Esto es una notificación de prueba de Pushtraps. Si puedes ver este mensaje,"
				+ " la aplicación está funcionando";
		BufferedReader bufferRead;
		Pusher pusher;
		int i = 1;
		int option;

		System.out.print(HEADER);
		System.out.println(error);
		System.out.println("");
		System.out.println(Pushers.getPushers().size()
				+ "notificadores activos:");
		System.out.println("");

		for (i = 0; i < Pushers.getPushers().size(); i++) {
			pusher = Pushers.getPushers().get(i);
			System.out
					.println((i + 1) + ") " + "Servicio: "
							+ pusher.getServiceType() + " Cuenta: "
							+ pusher.getAlias());
		}

		System.out.print("Elige el servicio para probar: ");
		bufferRead = new BufferedReader(new InputStreamReader(System.in));
		try {
			option = Integer.parseInt(bufferRead.readLine());
			pusher = Pushers.getPushers().get(option);
			pusher.pushMessage(title, body);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/* Limpia el estado de error */
	private static void clearError() {
		error = "";
	}
}