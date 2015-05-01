import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.*;
import java.sql.Statement;
import java.io.*;

public class Project
{
  public static void main(String[] args) throws ClassNotFoundException, IOException
  {
    // load the sqlite-JDBC driver using the current class loader
    Class.forName("org.sqlite.JDBC");

    Connection conn = null;
    try
    {
      // create a database connection
      conn= DriverManager.getConnection("jdbc:sqlite:Project.db");
      Statement statement = conn.createStatement();
      statement.setQueryTimeout(30);  // set timeout to 30 sec.
	String order, stmt1, column, tempcolumn, mealplan, username, password;
	username = "";
	password = "";
	Double price;
	Console c = System.console();
	Boolean isTrue = true, bDH, bBuffet, bHours, bPrice, bCuisine, bAdmin;
	bDH = false; bBuffet = false; bHours = false; bPrice = false; bCuisine = false; bAdmin = false;
      while(isTrue){
	bDH = false; bBuffet = false; bHours = false; bPrice = false; bCuisine = false; bAdmin = false;
	tempcolumn = "yes";
	column = "";
	while(!tempcolumn.equals("no")){
		tempcolumn = c.readLine("What would you like to know about Miami's Dining Halls(Enter help for options): ");
		if(tempcolumn.equals("help")){
			System.out.println("The options are dh for Dining Hall, buffet, hours, and when you are done please type no");
		}
		else if(!tempcolumn.equals("no")){	
			if(tempcolumn.equals("dh")){
				bDH = true;
			}
			else if(tempcolumn.equals("hours")){
				bHours = true;
			}
			else if(tempcolumn.equals("buffet")){
				bBuffet = true;
			}
			else if(tempcolumn.equals("price")){
				bPrice = true;
			}
			else if(tempcolumn.equals("cuisine")){
				bCuisine = true;
			}
			else if(tempcolumn.equals("admin")){
				bAdmin = true;
				tempcolumn = "no";
			}
		}
	}
	if(bAdmin){
		username = c.readLine("Username: ");
		password = c.readLine("Password: ");
	}
	mealplan = "None";
	if(bPrice)
		mealplan = c.readLine("Enter the Meal Plan you have(Express, Diplomat, None): ");
    
  	/*sprice = c.readLine("Enter Max Price you want to search for: ");
	price = Double.parseDouble(sprice);
	order = c.readLine("Order by title or price: ");
	if(order.equals("title")){
		stmt1 = "Select * From Book, BookAuthors Where price <= ? AND BookId = Id Order By title";
      	}
	else{
		stmt1 = "Select * From Book, BookAuthors Where price <= ? AND BookId = Id Order By price";
	}
	*/
	String temp = "None";
	if(bBuffet)
		temp = c.readLine("Do you want to see the average price of buffets if so enter avg");
	if(temp.equals("min"))
		stmt1 = "Select * From dining_hall, operational_hours, payment_type, admin_detail, buffet Where dh_name = dh_name1 AND dh_name3 = dh_name AND customer_status1 = customer_status Group By Min(price2)";
	else if(temp.equals("max"))
		stmt1 = "Select * From dining_hall, operational_hours, payment_type, admin_detail, buffet Where dh_name = dh_name1 AND dh_name3 = dh_name AND customer_status1 = customer_status Group By Max(price2)";
	else if(temp.equals("avg"))
		stmt1 = "Select *, AVG(price2) From dining_hall, operational_hours, payment_type, admin_detail, buffet Where dh_name = dh_name1 AND dh_name3 = dh_name AND customer_status1 = customer_status Group By dh_name";
	else
		stmt1 = "Select * From dining_hall, operational_hours, payment_type, admin_detail, buffet Where dh_name = dh_name1 AND dh_name3 = dh_name AND customer_status1 = customer_status Order By dh_name";
	
	PreparedStatement p = conn.prepareStatement(stmt1);
	p.clearParameters();
	//p.setString(1, sprice);
    ResultSet rs = p.executeQuery();

	int count = 0;
	while(rs.next())
      	{
        // read the result set
		if(!bAdmin){
        count++;
		System.out.println("\nResult " + count);
		System.out.println("Name: " + rs.getString("dh_name"));
		if(bDH){
			System.out.println("Type: " + rs.getString("dh_type"));
			System.out.println("Location: " + rs.getString("location"));
		}
		if(bHours)
			System.out.println("Hours: " + rs.getString("time_open") + "-" + rs.getString("time_close") + " On " + rs.getString("wk_day"));
		if(bBuffet){
			if(temp.equals("avg"))
				System.out.println("Average Price: " + rs.getString("AVG(price2)"));
			else
				System.out.println("Cuisine: " + rs.getString("cuisine") + " Price: " + rs.getInt("price2"));
		}
    //if(bMenu)
	//System.out.println("Price: " + rs.getDouble("price"));
		if(bPrice)
			System.out.println("Price: " + rs.getDouble("price")*rs.getInt("discount")/100);
		if(bCuisine)
		System.out.println("Cuisine: " + rs.getString("cuisine"));
        }
		else{
			if(rs.getString("username").equals(username) && rs.getString("password").equals(password))
			{
				String d_name, d_type, loc, c_stat, d_admin;
				d_name = c.readLine("Enter Name of Dining Hall: ");
				d_type = c.readLine("Enter the Type of Dining Hall: ");
				d_admin = c.readLine("Enter the Admin's id: ");
				loc = c.readLine("Enter the Location: ");
				c_stat = c.readLine("Enter the customer status: ");
				statement.executeUpdate("Insert INTO dining_hall Values ('" + d_name + "', '" + d_type + "', '" + loc + "', " + d_admin + ", '" + c_stat + "')");
				break;
			}
			else{
				System.out.println("Wrong username or password");
			}
		}
		}
		System.out.println("");
      }
    }
    catch(SQLException e)
    {
      // if the error message is "out of memory", 
      // it probably means no database file is found
      System.err.println(e.getMessage());
    }
    catch(Exception e){
	System.out.println("Good Bye.");
	}
    finally
    {
      try
      {
        if(conn != null)
          conn.close();
      }
      catch(SQLException e)
      {
        // connection close failed.
        System.err.println(e);
      }
    }
  }
}