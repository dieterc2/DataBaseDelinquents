package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AppDAORelational {
	
	private Connection con;
	
	public AppDAORelational(){
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} 
		catch (ClassNotFoundException e) {
			System.out.println("No jdbc found.");
			e.printStackTrace();
		}
	 
		this.con = null;
	 
		try {
			this.con = DriverManager
			.getConnection("jdbc:mysql://localhost:3306","root", "");
	 
		} 
		catch (SQLException e) {
			System.out.println("Connection failed.");
			e.printStackTrace();
		}
	}
	
	public void insert(String query){
		try{
			Statement sttm = this.con.createStatement();
			sttm.executeUpdate(query);
		}
		catch(SQLException e){
			e.printStackTrace();
		}
	}

	public ResultSet select(String query){
		
		try{
			Statement sttm = this.con.createStatement();
			ResultSet rs = sttm.executeQuery(query);
			return rs;
		}
		catch(SQLException e){
			e.printStackTrace();
			return null;
		}
	}
	
	public void update(String query){
		
		try{
			Statement sttm = this.con.createStatement();
			sttm.executeUpdate(query);
		}
		catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	public void delete(String query){
		
		try{
			Statement sttm = this.con.createStatement();
			sttm.executeUpdate(query);
		}
		catch(SQLException e){
			e.printStackTrace();
		}
	}
}
