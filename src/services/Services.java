package services;

import java.util.HashMap;
import java.util.Map;

public class Services {

	private static Map<String, Service> services;
	
	static{
		services = new HashMap<String, Service>();
		services.put("pushbullet", new PushbulletService());
		// TODO services.put("twitter", new TwitterService());
		// TODO services.put("email", new EmailService());
	}
	
	public static Map<String, Service> getServices(){
		return services;
	}
}
