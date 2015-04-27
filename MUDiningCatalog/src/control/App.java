package control;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import DAO.*;

public abstract class App {

	
	public static void submitApp(String username, String appName, ArrayList<String> developers, String link, Double price, String version){
		
		AppDAORelational appDAO = DAOFactory.getAppDAO();
		DevelopersDAORelational devDAO = DAOFactory.getDevelopersDAO();
		String query;
		
		username = "\"" + username + "\"";
		appName = "\"" + appName + "\"";
		link = "\"" + link + "\"";
		version = "\"" + version + "\"";
		
		query = "INSERT INTO applist.app VALUES("+appName+","+price+","+link+","+version+","
						+username+")";
		appDAO.insert(query);
		
		for(int i=0; i<developers.size(); i++){
			String developerName = "\"" + developers.get(i) + "\"";
			query = "INSERT INTO applist.developers VALUES("+developerName+","+appName+")";
			devDAO.insert(query);
		}		
	}
	
	public static void removeApp(String appName){

		AppDAORelational appDAO = DAOFactory.getAppDAO();
		
		String query = "DELETE FROM applist.app WHERE name = "+appName;
		
		appDAO.delete(query);
		
	}
	
	public static String retrieveAppsJSON(){

		AppDAORelational appDAO = DAOFactory.getAppDAO();
		
		String query = "SELECT name, price, link, username FROM applist.app";
		
		try{
			// Execute query
			ResultSet rs = appDAO.select(query);
			
			// Retrieve data from the database
			String dbResult = "[";
			while(rs.next()){
				String name = rs.getString("name");
				double price = rs.getDouble("price");
				String link = rs.getString("link");
				String username = rs.getString("username");
				
				if(!dbResult.equals("[")){
					dbResult += ",";
				}
				dbResult += "{\"appName\":"+"\""+name+"\""+",";
				dbResult += "\"developers\":"+"\""+username+"\""+",";
				dbResult += "\"description\":"+"\"No description\""+",";
				dbResult += "\"field3\":"+"\""+link+"\""+",";
				dbResult += "\"field4\":"+"\""+"USD $"+Double.toString(price)+"\""+",";
				dbResult += "\"field5\":"+"\"No rating\""+"}";
			}
			dbResult += "]";
			
			return dbResult;
		}
		catch(SQLException e){
			e.printStackTrace();
			return null;
		}
	}

}
