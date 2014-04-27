import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import ui.ServicesUI;

public class Server {

	static final String CLS = "\033[2J\033[1;1H";
	static String error = "";
	static final String HEADER = "\033[2J\033[1;1H\n------ PushTraps -------\n------------------------\n\n";

	public static void main(String[] arg) {
		menu();
	}

	private static void menu() {

		String s;
		Integer option;

		System.out.print(HEADER);
		System.out.println(error);
		System.out.println("");
		System.out.println("Selecciona una opción a continuación:");
		System.out.println("1)	Editar servicios");
		System.out.println("2)	Editar agentes");
		System.out.println("2)	Editar conexiones");
		System.out.println("0)	Salir");
		System.out.println("");
		System.out.print("Introduce una opción: ");

		try {
			BufferedReader bufferRead = new BufferedReader(
					new InputStreamReader(System.in));
			s = bufferRead.readLine();
			option = Integer.parseInt(s);

			switch (option) {
			case 0:
				System.out.print(CLS);
				break;
			case 1:
				ServicesUI.servicesMenu();
				menu();
				break;
			case 2:
				// TODO editAgents();
				break;
			case 3:
				// TODO editConnections();
				break;
			default:
				error = "Opción no válida";
				menu();
				break;
			}
		} catch (NumberFormatException e) {
			error = "Opción no válida";
			menu();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}