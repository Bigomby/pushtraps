package ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;

import services.PushbulletAccount;
import services.PushbulletDevice;
import services.PushbulletService;
import services.Pusher;
import services.Pushers;
import services.Services;

public class ServicesUI {

	static final String CLS = "\033[2J\033[1;1H";
	static String error = "";
	static final String HEADER = "\033[2J\033[1;1H\n------ PushTraps -------\n------------------------\n\n";

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
				addPushbulletAccount();
				break;
			case 2:
				// addMailService();
				break;
			case 3:
				// addTwitterService();
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

	private static void addPushbulletAccount() {

		String api_key;
		String alias;
		BufferedReader bufferRead;

		clearError();

		try {
			System.out.print("Introduce un alias para la cuenta: ");
			bufferRead = new BufferedReader(new InputStreamReader(System.in));
			alias = bufferRead.readLine();

			System.out.print("Introduce la \"API_KEY\" del servicio: ");
			bufferRead = new BufferedReader(new InputStreamReader(System.in));
			api_key = bufferRead.readLine();

			PushbulletService service = (PushbulletService) Services
					.getServices().get("pushbullet");
			service.addAccount(alias, api_key);
			addPushbulletDevice(alias);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void addPushbulletDevice(String alias) {

		PushbulletService service = (PushbulletService) Services.getServices()
				.get("pushbullet");
		PushbulletAccount account = service.getAccount(alias);
		Iterator<PushbulletDevice> it = account.getDevices().iterator();
		PushbulletDevice device;
		String s;
		int option;

		System.out.print(HEADER);
		System.out.println(error);
		System.out.println("");
		System.out.println("Dispositivos disponibles:");
		System.out.println("");

		while (it.hasNext()) {
			device = (PushbulletDevice) it.next();
			System.out.println("Dispositivo: " + device.getModel() + "  Iden: "
					+ device.getIden());
		}

		try {
			System.out.println("");
			System.out.print("Elige un dispositivo");
			BufferedReader bufferRead = new BufferedReader(
					new InputStreamReader(System.in));

			s = bufferRead.readLine();
			option = Integer.parseInt(s);

			System.out.println("");
			System.out.print("Introduce un alias:");
			s = bufferRead.readLine();

			Pushers.addPusher(account.getDevices().get(option - 1));

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/* Muestra por pantalla la lista de cuentas de Pushbullet configuradas */

	// TODO Hacer que el método funcione con servicios genéricos, no sólo con
	// Pushbullet
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
				System.out.println(i + ") Servicio: " + pusher.getServiceType()
						+ "  Alias: " + pusher.getAlias());
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
		Iterator<Pusher> it = Pushers.getPushers().iterator();
		Pusher pusher;
		int i = 1;
		int option;

		System.out.print(HEADER);
		System.out.println(error);
		System.out.println("");
		System.out.println("Servicios activos:");
		System.out.println("");

		while (it.hasNext()) {
			pusher = (Pusher) it.next();
			System.out.println(i + ") Servicio: " + pusher.getServiceType()
					+ "  Alias: " + pusher.getAlias());
			i++;
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

	private static void clearError() {
		error = "";
	}
}