package services;

import org.json.JSONObject;

import communications.PushbulletAPI;

public class Pushbullet implements Service {

	private final String serviceType = "Pushbullet";
	private String apiKey;

	private String alias;
	private String iden;
	private String model;


	// Recibe un objeto JSON y los almacena en los atributos
	public Pushbullet(JSONObject device) {
		this.iden = device.getString("iden");
		JSONObject extras = device.getJSONObject("extras");
		this.model = extras.getString("model");
	}

	// Envía un mensaje PUSH al dispositivo
	public void pushMessage(String title, String body) {
		PushbulletAPI.sendNote(apiKey, iden, title, body);
	}

	// Getters y setters
	public String getIden() {
		return iden;
	}

	public String getModel() {
		return model;
	}

	public String getType() {
		return serviceType;
	}

	public String getAlias() {
		return alias;
	}

	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}
}