package ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import services.Pushbullet;
import services.Service;

import communications.PushbulletAPI;

public class PushbulletUI {

	private List<Service> services;

	PushbulletUI(List<Service> services) {
		this.services = services;
	}

	void add() {

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
				services.add(devices.get(option - 1));
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
