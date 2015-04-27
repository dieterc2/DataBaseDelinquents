package Servlets;
public abstract class App {

	
	public boolean submitApp(String appName, String developers, String platforms, String versions, String price, String link){
		return true;
	}
	
	public boolean removeApp(String appName){
		return true;
	}
	
	public String retrieveApps(String JSON){
		String dbJSON = null;
		
		return dbJSON;
	}

}
