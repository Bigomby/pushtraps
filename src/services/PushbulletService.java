package services;

import java.util.HashMap;
import java.util.Map;

public class PushbulletService {
	
	static Map<String, PushbulletAccount> accounts;
	
	static{
		accounts = new HashMap<String, PushbulletAccount>();
	}
	
	public static void addAccount(String alias, String api_key){
		PushbulletAccount account = new PushbulletAccount(alias, api_key);
		accounts.put(alias, account);
	}
	
	public static Map<String, PushbulletAccount> getAccounts(){
		return accounts;
	}
}