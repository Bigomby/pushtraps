package services;

import java.util.ArrayList;

public class Pushers {
	
	private static ArrayList<Pusher> pushers;
	
	static{
		pushers = new ArrayList<Pusher>();
	}
	
	public static void addPusher(Pusher pusher){
		pushers.add(pusher);
	}
	
	public static ArrayList<Pusher> getPushers(){
		return pushers;
	}
}
