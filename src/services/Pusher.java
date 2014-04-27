package services;

public interface Pusher{
	public void pushMessage(String title, String body);
	public String getServiceType();
	public String getAlias();
}