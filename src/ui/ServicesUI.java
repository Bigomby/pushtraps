package ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.Map;

import services.PushbulletAccount;
import services.PushbulletService;

public class ServicesUI {

	static final String CLS = "\033[2J\033[1;1H";
	static String error = "";
	static final String HEADER = "\033[2J\033[1;1H\n------ PushTraps -------\n------------------------\n\n";

	public static void servicesMenu() {

		Integer option;
		String s;
		boolean exit = false;

		System.out.print(HEADER);
		System.out.println(error);
		System.out.println("");
		System.out.println("Selecciona una acción a continuación:");
		System.out.println("1)	Ver servicios activos");
		System.out.println("2)	Añadir un servicio");
		System.out.println("3)	Editar un servicio existente");
		System.out.println("3)	Eliminar un servicio existente");
		System.out.println("0)	Atrás");
		System.out.println("");
		System.out.print("Introduce una opción: ");

		BufferedReader bufferRead = new BufferedReader(new InputStreamReader(
				System.in));
		try {
			s = bufferRead.readLine();
			option = Integer.parseInt(s);


			switch (option) {
			case 0:
				exit = true;
				break;
			case 1:
				showAccounts();
				break;
			case 2:
				addService();
				break;
			case 3:
				// TODO editServices();
				break;
			case 4:
				// TODO deleteService();
				break;
			default:
				error = "Opción no válida";
				servicesMenu();
				break;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(!exit) servicesMenu();
	}

	private static void addService() {

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

			PushbulletService.addAccount(alias, api_key);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("rawtypes")
	private static void showAccounts() {
		if (PushbulletService.getAccounts().isEmpty()) {
			error = "No hay servicios activos";
		} else {
			clearError();
			System.out.println(PushbulletService.getAccounts());
			Iterator it = PushbulletService.getAccounts().entrySet().iterator();

			System.out.print(HEADER);
			System.out.println(error);
			System.out.println("");
			System.out.println("Cuentas activas:");
			System.out.println("");
			while (it.hasNext()) {
				Map.Entry e = (Map.Entry) it.next();
				PushbulletAccount account = (PushbulletAccount) e.getValue();
				System.out.println("- " + account.getAlias());
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
	
	private static void clearError(){
		error = "";
	}
}