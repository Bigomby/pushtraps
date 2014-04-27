package services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSocketFactory;

import org.json.JSONArray;
import org.json.JSONObject;

import com.sun.org.apache.xml.internal.security.utils.Base64;

public class PushbulletAPI {

	public static ArrayList<PushbulletDevice> getDevices(String api_key) {

		ArrayList <PushbulletDevice> devices = new ArrayList<PushbulletDevice>();

		try {

			int i = 0;

			final SSLSocketFactory sslSocketFactory = (SSLSocketFactory) SSLSocketFactory
					.getDefault();
			final URL url = new URL("https://api.pushbullet.com/api/devices");
			final HttpsURLConnection connection = (HttpsURLConnection) url
					.openConnection();
			connection.setSSLSocketFactory(sslSocketFactory);

			connection.setRequestMethod("GET");

			connection.setUseCaches(false);
			connection.setDoInput(true);
			connection.setDoOutput(true);

			final String authStr = api_key + ":";
			final String authEncoded = Base64.encode(authStr.getBytes());
			connection.setRequestProperty("Authorization", "Basic "
					+ authEncoded);

			final InputStream is = connection.getInputStream();
			final BufferedReader rd = new BufferedReader(new InputStreamReader(
					is));
			String line;
			final StringBuffer response = new StringBuffer();

			while ((line = rd.readLine()) != null) {
				response.append(line);
				response.append('\r');
			}
			rd.close();

			JSONObject jsonResponse = new JSONObject(response.toString());
			JSONArray jsonDevices = jsonResponse.getJSONArray("devices");

			while (!jsonDevices.isNull(i)) {
				JSONObject jsonDevice = jsonDevices.getJSONObject(i);
				PushbulletDevice device = new PushbulletDevice(jsonDevice);
				devices.add(device);
				i++;
			}
		} catch (MalformedURLException e) {
			System.out.println("Error en URL" + e);
		} catch (ProtocolException e) {
			System.out.println("Error en protocolo" + e);
		} catch (IOException e) {
			System.out.println("Error en E/S: " + e);
		}

		return devices;
	}

	public static void sendNote(String title, String body){
		// TODO
	}
}
