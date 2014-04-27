package services;

import java.util.HashMap;
import java.util.Map;

public class PushbulletService implements Service {
	
	static Map<String, PushbulletAccount>accounts = new HashMap<String, PushbulletAccount>();
	
	public void addAccount(String alias, String api_key){
		PushbulletAccount account = new PushbulletAccount(alias, api_key);
		accounts.put(alias, account);
	}
	
	public Map<String, PushbulletAccount> getAccounts(){
		return accounts;
	}
}