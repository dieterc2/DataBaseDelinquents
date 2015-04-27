package DAO;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
/**
 * Servlet implementation class AppDAO
 */
@WebServlet("/AppDAO")
public class AppDAO extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AppDAO() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		PrintWriter out = response.getWriter();
		response.setContentType("application/json");
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("No jdbc found.");
			e.printStackTrace();
			return;
		}
	 
		Connection con = null;
	 
		try {
			con = DriverManager
			.getConnection("jdbc:mysql://localhost:3306","root", "");
	 
		} catch (SQLException e) {
			System.out.println("Connection failed.");
			e.printStackTrace();
			return;
		}
		
		try{
			Statement sttm = con.createStatement();
			String query = "SELECT name, price, link, username FROM applist.app";
			
			// Execute query
			ResultSet rs = sttm.executeQuery(query);
			
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
			// Send result to javascript
			out.println(dbResult);
			con.close();
		}
		catch(SQLException e){
			System.out.println("Statement failed.");
			e.printStackTrace();
			return;
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
