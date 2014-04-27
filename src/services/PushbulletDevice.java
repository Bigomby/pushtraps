package services;

import org.json.*;

public class PushbulletDevice implements Pusher {
	
	private final String type = "Pushbullet";

	private String iden;
	private String manufacturer;
	private String model;
	private String android_version;
	private String sdk_version;
	private String app_version;
	private String nickname;

	// Recibe un objeto JSON y los almacena en los atributos
	public PushbulletDevice (JSONObject device){
		this.iden = device.getString("iden");
		JSONObject extras = device.getJSONObject("extras");
		this.manufacturer = extras.getString("manufacturer");
		this.model = extras.getString("model");
		this.android_version = extras.get("android_version").toString();
		this.sdk_version = extras.get("sdk_version").toString();
		this.app_version = extras.get("app_version").toString();
	}

	// Envía un mensaje PUSH al dispositivo
	public void pushMessage(String message){
		//TODO
	}

	// Getters
	public String getIden(){
		return iden;
	}
	public String getManufacturer(){
		return manufacturer;
	}
	public String getModel(){
		return model;
	}
	public String getAndroidVersion(){
		return android_version;
	}
	public String getSdkVersion(){
		return sdk_version;
	}
	public String getAppVersion(){
		return app_version.toString();
	}
	public String getNickname(){
		return nickname;
	}
	public String getServiceType(){
		return type;
	}
}