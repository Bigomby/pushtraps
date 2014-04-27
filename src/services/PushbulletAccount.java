package services;

import java.util.Map;

/* Representa un usuario de Pushbullet. Se le pasa como parámetro la API_KEY del usuario y
almacena en un mapa los dispositivos asociados a la cuenta. */
public class PushbulletAccount {

	@SuppressWarnings("unused")
	private String api_key;
	private String alias;
	private Map<String,PushbulletDevice> devices;

	public PushbulletAccount(String alias, String api_key){	
		this.api_key = api_key;
		this.alias = alias;
		this.devices = PushbulletAPI.getDevices(api_key);
	}

	public Map<String,PushbulletDevice> getDevices(){
		return devices;
	}
	
	public String getAlias(){
		return alias;
	}
}