package dininghallcatalog;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class Catalog {

	/** The username for the default account. */
	private final String userName = "root";
	
	/** The password for the defualt account. */
	private final String password = "root";
	
	/** Name and location of the database. */
	static final String DB_URL = "jdbc:sqlite:M:/Project.db";
	/** Connection for the database. */
	private Connection conn = null;
	/** Result Set for DB queries. */
	public static ResultSet rs = null;
	
	
	//Shells for new windows
	protected Shell shlDatabaseDel;
	protected Shell shlSearchResults;
	protected Shell shlAdmin;
	protected Shell shlAdminLogin;
	protected Shell shlError;
	
	//User interactive text input fields
	private Text txtUsername, txtPassword, txtName, txtType, txtLocation, txtAdmin, txtCuisine, searchText;
	
	//Possible user selected filters
	private final String BUDGET = "Budget";
	private final String CUISINE = "Cuisine Type";
	private final String RESTAURANT = "Restaurant Type";
	private final String FOOD = "Food Item";
	private final String HILO = "Average Price";
	/**
	 *  Setup a new connection to the database.
	 *
	 * @throws SQLException
	 */
	public Connection getConnection() throws SQLException {
		conn = DriverManager.getConnection(DB_URL, userName, password);
		return conn;
	}

	/**
	 *  Execute a specified SQL query
	 * @param conn
	 * @param command
	 * @throws SQLException
	 */
	public void executeUpdate(Connection conn, String command) throws SQLException {
		PreparedStatement query = null;
		try {
			query = conn.prepareStatement(command);
			rs = query.executeQuery();
		} finally {
			if (query != null){
				query.close();
			}
		}
	}
	
	/**
	 *  Connect to the database for user interactivity.
	 * @throws ClassNotFoundException 
	 */
	public void run() throws ClassNotFoundException {
		Class.forName("org.sqlite.JDBC");
		try {
			this.conn = this.getConnection();
			System.out.println("Successfully connected to database!");
		} catch (SQLException e){
			System.out.println("Error: Could not connect to the database.");
			e.printStackTrace();
			return;
		}
	}

	
	/**
	 * Launch the application.
	 * @param args
	 * @throws ClassNotFoundException 
	 * @throws SQLException 
	 */
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		Catalog app = new Catalog();
		app.run();
		try {
			Catalog window = new Catalog();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shlDatabaseDel.open();
		shlDatabaseDel.layout();
		while (!shlDatabaseDel.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		display.dispose();
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		//Entire GUI window shell.
		shlDatabaseDel = new Shell();
		shlDatabaseDel.setSize(805, 475);
		shlDatabaseDel.setText("Database Delinquents: Miami Dining Catalog");
		
		/*
		 * Dininghall composite that displays the names of all the dining halls on the left hand side of the GUI
		 */
		Composite diningHallComposite = new Composite(shlDatabaseDel, SWT.BORDER);
		diningHallComposite.setBounds(2, 10, 118, 416);
		//Label used for naming the dining hall composite
		Label lblDiningHalls = new Label(diningHallComposite, SWT.NONE);
		lblDiningHalls.setLocation(0, 0);
		lblDiningHalls.setSize(118, 19);
		lblDiningHalls.setAlignment(SWT.CENTER);
		lblDiningHalls.setFont(SWTResourceManager.getFont("Century Schoolbook", 12, SWT.BOLD));
		lblDiningHalls.setText("Dining Halls");
		//List used for holding all of the dining halls
		final List list = new List(diningHallComposite, SWT.BORDER | SWT.WRAP);
		list.setBounds(0, 21, 114, 391);
	/**
	 *  Populates a list of ALL dininghalls to be displayed on the left hand side of the GUI.
	 */
		try {
			conn = DriverManager.getConnection(DB_URL);
			PreparedStatement p = conn.prepareStatement("Select * From dining_hall Order By dh_name");
			p.clearParameters();
		    rs = p.executeQuery();
			while (rs.next()){
				list.add(rs.getString("dh_name"));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	
		
		/*
		 * Composite for displaying all of the selected dininghalls information and the users search results
		 */
		Composite detailsComposite = new Composite(shlDatabaseDel, SWT.BORDER);
		detailsComposite.setBounds(124, 71, 655, 355);
		// Left hand list for menu item names
		final List listItemName = new List(detailsComposite, SWT.BORDER | SWT.V_SCROLL);
		listItemName.setBounds(0, 109, 125, 232);
		// Middle list for menu descriptions
		final List listItemDescription = new List(detailsComposite, SWT.BORDER | SWT.V_SCROLL);
		listItemDescription.setBounds(125, 109, 125, 232);
		// Right hand list for menu prices
		final List listPrice = new List(detailsComposite, SWT.BORDER | SWT.V_SCROLL);
		listPrice.setBounds(250, 109, 125, 232);
		// This label does not change, it represents the menus item column
		Label permItemlbl = new Label(detailsComposite, SWT.NONE);
		permItemlbl.setBounds(2, 91, 68, 15);
		permItemlbl.setText("Item Name");
		// This label does not change, it represents the menus item description column
		Label permItemDesclbl = new Label(detailsComposite, SWT.NONE);
		permItemDesclbl.setBounds(127, 91, 87, 15);
		permItemDesclbl.setText("Item Description");
		//This label does not change, it represents the menus item price column
		Label permPricelbl = new Label(detailsComposite, SWT.NONE);
		permPricelbl.setBounds(305, 91, 55, 15);
		permPricelbl.setText("Price");
		// This label is used to display the name of the dining hall currently selected.
		final Label lblHallName = new Label(detailsComposite, SWT.NONE);
		lblHallName.setFont(SWTResourceManager.getFont("Century Schoolbook", 13, SWT.BOLD));
		lblHallName.setBounds(10, 10, 233, 27);
		//This label does not change, perm. display of the cuisine
		Label permCuisinelbl = new Label(detailsComposite, SWT.NONE);
		permCuisinelbl.setFont(SWTResourceManager.getFont("Century Schoolbook", 10, SWT.BOLD));
		permCuisinelbl.setBounds(13, 43, 55, 15);
		permCuisinelbl.setText("Cuisine:");
		// Perm. display of the restaurant type field 
		Label permTypelbl = new Label(detailsComposite, SWT.NONE);
		permTypelbl.setFont(SWTResourceManager.getFont("Century Schoolbook", 10, SWT.BOLD));
		permTypelbl.setBounds(13, 64, 139, 15);
		permTypelbl.setText("Type of Resturant:");
		// This label is used to display the cuisine of the selected dininghall
		final Label lblCuisine = new Label(detailsComposite, SWT.NONE);
		lblCuisine.setFont(SWTResourceManager.getFont("Century Schoolbook", 9, SWT.ITALIC));
		lblCuisine.setBounds(74, 44, 96, 15);
		// Label used to display the resturant type of the selected dininghall
		final Label lblType = new Label(detailsComposite, SWT.NONE);
		lblType.setFont(SWTResourceManager.getFont("Century Schoolbook", 9, SWT.ITALIC));
		lblType.setBounds(158, 64, 80, 15);
		// Perm. display of the dininghalls hours
		Label permHourslbl = new Label(detailsComposite, SWT.NONE);
		permHourslbl.setFont(SWTResourceManager.getFont("Century Schoolbook", 10, SWT.BOLD));
		permHourslbl.setBounds(408, 14, 46, 15);
		permHourslbl.setText("Hours:");
		// Perm. display of the dining halls location
		Label permLoclbl = new Label(detailsComposite, SWT.NONE);
		permLoclbl.setFont(SWTResourceManager.getFont("Century Schoolbook", 10, SWT.BOLD));
		permLoclbl.setBounds(176, 43, 68, 15);
		permLoclbl.setText("Location:");
		// Label for displaying the selected dininghalls hours
		final Label lblHours = new Label(detailsComposite, SWT.WRAP);
		lblHours.setFont(SWTResourceManager.getFont("Century Schoolbook", 9, SWT.ITALIC));
		lblHours.setBounds(421, 35, 220, 202);
		// Label for displaying the selected dininghalls location
		final Label lblLoc = new Label(detailsComposite, SWT.NONE);
		lblLoc.setFont(SWTResourceManager.getFont("Century Schoolbook", 9, SWT.NORMAL));
		lblLoc.setBounds(250, 43, 161, 15);
	/**
	 * Handles updating the selected dininghalls details and information
	 */
		list.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(org.eclipse.swt.widgets.Event arg0){
				final String dhst = list.getItem(list.getSelectionIndex());
				try {
					conn = DriverManager.getConnection(DB_URL);
					PreparedStatement p = conn.prepareStatement("SELECT * FROM dining_hall, operational_hours "
							+ "WHERE dh_name = \'" + dhst + "\' AND dh_name = dh_name1 ORDER BY dh_name");
					p.clearParameters();
					rs = p.executeQuery();
					String hour = "";
					lblHallName.setText(dhst);
					listItemName.removeAll();
				    listItemDescription.removeAll();
				    listPrice.removeAll();
				    while (rs.next()){
				    	lblType.setText(rs.getString("dh_type"));
				    	lblCuisine.setText(rs.getString("cuisine"));
				    	lblLoc.setText(rs.getString("location"));
				    	if(!hour.contains(rs.getString("time_open") + "-" 
				    			+ rs.getString("time_close") + " On " + rs.getString("wk_day") + "\n"))
				    		hour += rs.getString("time_open") + "-" + rs.getString("time_close") + " On " 
				    			+ rs.getString("wk_day") + "\n";
				    	if ("ala carte".equals(rs.getString("dh_type"))) {
							PreparedStatement q = conn.prepareStatement("Select * From dining_hall, a_la_carte_menu Where dh_name = \'"
											+ dhst
											+ "\' AND dh_name = dh_name2");
							ResultSet rt;
							rt = q.executeQuery();
							// Updates the dininghalls menu items for alacarte dininhalls
							while (rt.next()) {
								if (listItemName.indexOf(rt
										.getString("food_name")) != -1)
									continue;
								listItemName.add(rt.getString("food_name"));
								listItemDescription.add(rt
										.getString("food_desc"));
								listPrice.add("$" + rt.getInt("price1"));
							}
						} else {
							// Updates the dininghalls menu for buffets
							PreparedStatement q = conn
									.prepareStatement("Select * From dining_hall, buffet Where dh_name = \'"
											+ dhst
											+ "\' AND dh_name = dh_name3");
							ResultSet rt;
							rt = q.executeQuery();
							while (rt.next()) {
								if (listItemName.indexOf(rt
										.getString("meal_time")) != -1)
									continue;
								listItemName.add(rt.getString("meal_time"));
								listPrice.add("$" + rt.getString("price2"));
							}
						}

					}
					lblHours.setText(hour);
			}catch(Exception e){
			}
			}
		});
		
		/*
		 * Composite for displaying all search related components of the GUI 
		 */
			Composite searchComposite = new Composite(shlDatabaseDel, SWT.NONE);
			searchComposite.setBounds(124, 10, 655, 61);
			//Generic label for the search composite
			Label lblAlreadyKnowWhat = new Label(searchComposite, SWT.NONE);
			lblAlreadyKnowWhat.setFont(SWTResourceManager.getFont("Century Schoolbook", 10, SWT.BOLD));
			lblAlreadyKnowWhat.setBounds(0, 4, 264, 16);
			lblAlreadyKnowWhat.setText("Already know what you're looking for?");
			// Search field related label
			Label lblIWantTo = new Label(searchComposite, SWT.NONE);
			lblIWantTo.setBounds(24, 26, 109, 15);
			lblIWantTo.setText("I want to search by :");
			// Search field related label
			Label lblFor = new Label(searchComposite, SWT.NONE);
			lblFor.setBounds(291, 26, 28, 15);
			lblFor.setText("for :");
			// Combination dropdown box with all the filter types a user can select
			final Combo filterMenu = new Combo(searchComposite, SWT.NONE);
			filterMenu.setFont(SWTResourceManager.getFont("Segoe UI", 7, SWT.ITALIC));
			filterMenu.setBounds(139, 25, 136, 20);
			filterMenu.setText("Select a filter to search by");
			filterMenu.add("Budget");
			filterMenu.add("Food Item");
			filterMenu.add("Cuisine Type");
			filterMenu.add("Restaurant Type");
			filterMenu.add(HILO);
			/**
			 * Handles user filter selection and updates the search text box with appropriate default text.
			 */
			filterMenu.addListener(SWT.Selection, new Listener() {
				@Override
				public void handleEvent(org.eclipse.swt.widgets.Event arg0) {
					// If the user selects "Budget", update the search text box
					if (filterMenu.getSelectionIndex() == 0){
						searchText.setText("Enter the maximum item price (Ex: '5' = $5)");
					}
					// If the user selects "Food Item", update the search text box
					if (filterMenu.getSelectionIndex() == 1){
						searchText.setText("Enter the food item price your looking for.");
					}
					// If the user selects "Cuisine Type", update the search text box
					if (filterMenu.getSelectionIndex() == 2){
						searchText.setText("Enter the cuisine type. (Ex: 'Italian')");
					}
					// IF the user selects "Restaurant Type", update the search text box
					if (filterMenu.getSelectionIndex() == 3){
						searchText.setText("Enter the restaurant type.(Ex: 'buffet')");
					}
				}
			});
			// Gets the user inputed search text
			searchText = new Text(searchComposite, SWT.BORDER);
			searchText.setText("Enter what it is you are looking for.");
			searchText.setFont(SWTResourceManager.getFont("Segoe UI", 7, SWT.ITALIC));
			searchText.setBounds(320, 23, 183, 21);
			/**
			 * Clears the default text from the search text box when a user selects it.
			 */
			searchText.addFocusListener(new FocusListener() {
				@Override
				public void focusLost(FocusEvent arg0) {
				}
				// When a user clicks on the search text box, any default values are erased for easy typing.
				@Override
				public void focusGained(FocusEvent arg0) {
					searchText.setText("");
				}
			});
		// Search button for executing a user specified search
		Button searchButton = new Button(searchComposite, SWT.NONE);
		/**
		 * Launches a new window to display the user specified search results.
		 */
		searchButton.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(org.eclipse.swt.widgets.Event arg0) {
				shlSearchResults = new Shell();
				shlSearchResults.setSize(700,400);
				shlSearchResults.setText("Search Results");
				// Get the user specified range
				String maxVal = searchText.getText();
				// Generate the search result page..
				// Search results dipslay label.. lets the user know what results they are seeing.
				final Label searchExecuted = new Label(shlSearchResults, SWT.NONE);
				searchExecuted.setBounds(100, 5, 500, 15);
				
				// List of items
				final List itemMatchNames = new List(shlSearchResults, SWT.BORDER | SWT.V_SCROLL);
				itemMatchNames.setBounds(100, 50, 125, 232);
				// List of item descriptions
				final List itemMatchDesc = new List(shlSearchResults, SWT.BORDER | SWT.V_SCROLL);
				itemMatchDesc.setBounds(225, 50, 125, 232);
				// List of item prices
				final List itemMatchPrices = new List(shlSearchResults, SWT.BORDER | SWT.V_SCROLL);
				itemMatchPrices.setBounds(350, 50, 125, 232);
				// List of locations item is sold at
				final List itemLocations = new List(shlSearchResults, SWT.BORDER | SWT.V_SCROLL);
				itemLocations.setBounds(475, 50, 125, 232);
				// This label does not change, it represents the menus item column
				Label itemNameLbl = new Label(shlSearchResults, SWT.NONE);
				itemNameLbl.setBounds(125, 30, 68, 15);
				itemNameLbl.setText("Item Name");
				// This label does not change, it represents the menus item description column
				Label itemDescLbl = new Label(shlSearchResults, SWT.NONE);
				itemDescLbl.setBounds(250, 30, 87, 15);
				itemDescLbl.setText("Item Description");
				//This label does not change, it represents the menus item price column
				Label itemPriceLbl = new Label(shlSearchResults, SWT.NONE);
				itemPriceLbl.setBounds(375, 30, 55, 15);
				itemPriceLbl.setText("Price");
				Label itemLocLbl = new Label(shlSearchResults, SWT.NONE);
				itemLocLbl.setBounds(490, 30, 55, 15);
				itemLocLbl.setText("Locations");
				// Open the new window
				shlSearchResults.open();
				if(filterMenu.getText().equals(HILO)){
					String cName = searchText.getText();
					searchExecuted.setText("Average price of all items: ");
					try{
						conn = DriverManager.getConnection(DB_URL);
						listItemName.removeAll();
					    listItemDescription.removeAll();
					    listPrice.removeAll();
						PreparedStatement p = conn.prepareStatement("SELECT AVG(price1) FROM a_la_carte_menu");
						p.clearParameters();
						ResultSet rf = null;
						rf = p.executeQuery();
						while(rf.next()){
							searchExecuted.setText("Average price of all items: " + rf.getInt("AVG(price1)"));
						}
						
					} catch (Exception e){
						
					}
				}
				if (filterMenu.getText().equals(BUDGET)){
					searchExecuted.setText("All items for $" + maxVal + " or less. Click on an item to see where it is served.");
					try {
						conn = DriverManager.getConnection(DB_URL);
						// Retrieve all items under the user specified price
						PreparedStatement p = conn.prepareStatement("SELECT * FROM a_la_carte_menu WHERE price1 < \'" + maxVal + "\' ORDER BY price1");
						p.clearParameters();
						ResultSet rf = null;
						rf = p.executeQuery();
						// Add the items to the list
					    while (rf.next()) {
					    	if (itemMatchNames.indexOf(rf.getString("food_name")) != -1)
								continue;
					    	itemMatchNames.add(rf.getString("food_name"));
					    	itemMatchDesc.add(rf.getString("food_desc"));
					    	itemMatchPrices.add("$" + rf.getInt("price1"));
					    }
					    // Add a listener so that when the user selects an item, they are shown where the item is served at.
					    itemMatchNames.addListener(SWT.Selection, new Listener() {
							@Override
							public void handleEvent(org.eclipse.swt.widgets.Event arg0) {
								// String to use with the SQL query. Only dininghalls with this item should be returned.
								final String helper = itemMatchNames.getItem(itemMatchNames.getSelectionIndex());
								try {
		//***************************** Query not 100% correct. ************************
									PreparedStatement b = conn.prepareStatement("SELECT * FROM dining_hall, a_la_carte_menu WHERE food_name ="
											+ " \'" + helper + "\'");
									b.clearParameters();
									ResultSet rc = null;
									rc = b.executeQuery();
									while (rc.next()) {
										itemLocations.add(rc.getString("dh_name"));
									}
								} catch (SQLException e) {
									e.printStackTrace();
								}
							}
					    	
					    });
				}catch(SQLException e1){
				}
				}
	//***************** Search function does not work yet. *******************************
				if (filterMenu.getText().equals(FOOD)){
					String itemName = searchText.getText();
					searchExecuted.setText("All items that match your search of: " + itemName + ". Click to see locations.");
					
					try {
						conn = DriverManager.getConnection(DB_URL);
						listItemName.removeAll();
					    listItemDescription.removeAll();
					    listPrice.removeAll();
						PreparedStatement p = conn.prepareStatement("SELECT * FROM a_la_carte_menu WHERE food_name LIKE  \'%" + itemName + "%\'");
						p.clearParameters();
						ResultSet rf = null;
						rf = p.executeQuery();
					    while (rf.next()) {
					    	if (itemMatchNames.indexOf(rf.getString("food_name")) != -1)
								continue;
					    	itemMatchNames.add(rf.getString("food_name"));
					    	itemMatchDesc.add(rf
									.getString("food_desc"));
					    	itemMatchPrices.add("$" + rf.getInt("price1"));
					    }
					    itemMatchNames.addListener(SWT.Selection, new Listener(){

							@Override
							public void handleEvent(Event arg0) {
								// String to use with the SQL query. Only dininghalls with this item should be returned.
								final String helper = itemMatchNames.getItem(itemMatchNames.getSelectionIndex());
								try {
		//***************************** Query not 100% correct. ************************
									PreparedStatement b = conn.prepareStatement("SELECT * FROM dining_hall, a_la_carte_menu WHERE food_name ="
											+ " \'" + helper + "\'");
									b.clearParameters();
									ResultSet rc = null;
									rc = b.executeQuery();
									while (rc.next()) {
										itemLocations.add(rc.getString("dh_name"));
									}
								} catch (SQLException e) {
									e.printStackTrace();
								}
								
							}
					    	
					    });
				}catch(SQLException e1){
				}
				}
	//***************** Search function needs implemented *******************************
				if (filterMenu.getText().equals(CUISINE)){
					String cName = searchText.getText();
					searchExecuted.setText("All dining halls that match your desired cuisine.");
					try{
						conn = DriverManager.getConnection(DB_URL);
						listItemName.removeAll();
					    listItemDescription.removeAll();
					    listPrice.removeAll();
						PreparedStatement p = conn.prepareStatement("SELECT * FROM dining_hall WHERE cuisine LIKE  \'%" + cName + "%\'");
						p.clearParameters();
						ResultSet rf = null;
						rf = p.executeQuery();
						while (rf.next()) {
					    	if (itemLocations.indexOf(rf.getString("dh_name")) != -1)
								continue;
					    	itemLocations.add(rf.getString("dh_name"));
					    }
					} catch (Exception e){
						
					}
				
				}
   //***************** Search function needs implemented *******************************
				if (filterMenu.getText().equals(RESTAURANT)){
					String typey = searchText.getText();
					searchExecuted.setText("All dining halls that match your desired restaurant type.");
					try{
						conn = DriverManager.getConnection(DB_URL);
						listItemName.removeAll();
					    listItemDescription.removeAll();
					    listPrice.removeAll();
						PreparedStatement p = conn.prepareStatement("SELECT * FROM dining_hall WHERE dh_type LIKE  \'%" + typey + "%\'");
						p.clearParameters();
						ResultSet rf = null;
						rf = p.executeQuery();
						while (rf.next()) {
					    	if (itemLocations.indexOf(rf.getString("dh_name")) != -1)
								continue;
					    	itemLocations.add(rf.getString("dh_name"));
					    }
					} catch (Exception e){
						
					}
				}
			}
		});
		searchButton.setBounds(509, 23, 75, 20);
		searchButton.setText("Search!");
		
		// Admin/Login functions
		Button btnAdmin = new Button(searchComposite, SWT.NONE);
		btnAdmin.setBounds(574, 2, 81, 21);
		btnAdmin.setText("Admin Login");
		/**
		 * Displays a new window for Logging in/ admin functionality
		 */
		btnAdmin.addListener(SWT.Selection, new Listener (){
			@Override
			public void handleEvent(org.eclipse.swt.widgets.Event arg0) {
				shlAdminLogin = new Shell();
				shlAdminLogin.setSize(400, 200);
				shlAdminLogin.setText("Login");
				final Label lblUsername = new Label(shlAdminLogin, SWT.WRAP);
				lblUsername.setFont(SWTResourceManager.getFont("Century Schoolbook", 9, SWT.BOLD));
				lblUsername.setBounds(0, 10, 70, 24);
				lblUsername.setText("Username:");
				txtUsername = new Text(shlAdminLogin, SWT.BORDER | SWT.SEARCH);
				txtUsername.setBounds(70, 10, 300, 24);
				final Label lblPassword = new Label(shlAdminLogin, SWT.WRAP);
				lblPassword.setFont(SWTResourceManager.getFont("Century Schoolbook", 9, SWT.BOLD));
				lblPassword.setText("Password:");
				lblPassword.setBounds(0, 60, 70, 24);
				txtPassword = new Text(shlAdminLogin, SWT.BORDER | SWT.PASSWORD);
				txtPassword.setBounds(70, 60, 300, 24);
				shlAdminLogin.open();
				
				// User Login button
				Button btnCheckLogin = new Button(shlAdminLogin, SWT.NONE);
				btnCheckLogin.setBounds(175, 110, 50, 24);
				btnCheckLogin.setText("Login");
				btnCheckLogin.addListener(SWT.Selection, new Listener (){
					@Override
					public void handleEvent(org.eclipse.swt.widgets.Event arg0) {
						try{
							conn = DriverManager.getConnection(DB_URL);
							// Check user login information against the DB
							PreparedStatement p = conn.prepareStatement("Select * From admin_detail Where username = \'" + txtUsername.getText() + "\' AND password = \'" + txtPassword.getText() + "\'");
							p.clearParameters();
						    rs = p.executeQuery();
						    if(rs.next()){
						    	// Admin login window.
						    	shlAdminLogin.close();
						    	shlAdmin = new Shell();
								shlAdmin.setSize(400, 300);
								shlAdmin.setText("Welcome Admin");
								shlAdmin.open();
								
								// Admin inputed dininghall name for a new DH
								txtName = new Text(shlAdmin, SWT.BORDER | SWT.SEARCH);
								txtName.setBounds(70, 10, 300, 24);
								// New dininghall name label
								final Label lblName = new Label(shlAdmin, SWT.WRAP);
								lblName.setFont(SWTResourceManager.getFont("Century Schoolbook", 9, SWT.BOLD));
								lblName.setBounds(0, 10, 70, 24);
								lblName.setText("Name:");
								// Admin inputed type for a new DH
								txtType = new Text(shlAdmin, SWT.BORDER | SWT.SEARCH);
								txtType.setBounds(70, 60, 300, 24);
								// New dininghall type label
								final Label lblType = new Label(shlAdmin, SWT.WRAP);
								lblType.setFont(SWTResourceManager.getFont("Century Schoolbook", 9, SWT.BOLD));
								lblType.setBounds(0, 60, 70, 24);
								lblType.setText("Type:");
								// Admin inputed location for a new DH
								txtLocation = new Text(shlAdmin, SWT.BORDER | SWT.SEARCH);
								txtLocation.setBounds(70, 110, 300, 24);
								// New dininghall location label
								final Label lblLocation = new Label(shlAdmin, SWT.WRAP);
								lblLocation.setFont(SWTResourceManager.getFont("Century Schoolbook", 9, SWT.BOLD));
								lblLocation.setBounds(0, 110, 70, 24);
								lblLocation.setText("Location:");
								// Admin inputed cuisine type for a new DH
								txtCuisine = new Text(shlAdmin, SWT.BORDER | SWT.SEARCH);
								txtCuisine.setBounds(70, 160, 300, 24);
								// New Dininghall cuisine label
								final Label lblCuisine = new Label (shlAdmin, SWT.WRAP);
								lblCuisine.setFont(SWTResourceManager.getFont("Century Schoolbook", 9, SWT.BOLD));
								lblCuisine.setBounds(0, 160, 70, 24);
								lblCuisine.setText("Cuisine:");
								// Admin ID input field
								txtAdmin = new Text(shlAdmin, SWT.BORDER | SWT.SEARCH);
								txtAdmin.setBounds(70, 210, 300, 24);
								// Admin ID Label
								final Label lblAdmin = new Label(shlAdmin, SWT.WRAP);
								lblAdmin.setFont(SWTResourceManager.getFont("Century Schoolbook", 9, SWT.BOLD));
								lblAdmin.setBounds(0, 210, 70, 24);
								lblAdmin.setText("Admin Id:");
								
								// Displays the status of the addition request
								final Label additionStatus = new Label(shlAdmin, SWT.WRAP);
								additionStatus.setText("");
								additionStatus.setBounds(150, 235, 50, 24);
								// Button for adding a new dining hall
								Button btnAddDiningHall = new Button(shlAdmin, SWT.NONE);
								btnAddDiningHall.setBounds(100, 235, 50, 24);
								btnAddDiningHall.setText("Add");
								btnAddDiningHall.addListener(SWT.Selection, new Listener (){
									@Override
									public void handleEvent(org.eclipse.swt.widgets.Event arg0) {
										try {
											Statement statement = conn.createStatement();
											statement.setQueryTimeout(30);
											// Adds the dining hall
											statement.executeUpdate("Insert INTO dining_hall Values (\'" + txtName.getText() + "\', \'" + txtType.getText() + "\', \'" + txtCuisine.getText() + "\', " + txtLocation.getText() + "\', " + txtAdmin.getText() + ", \'None\', \'Test\')");
											// Display the status 
											additionStatus.setText("Successfully added " + txtName.getText() + " dining hall!");
										} catch (SQLException e) {
											e.printStackTrace();
										}
									}});
								// Displays the status of the deletion request..
								final Label deletionStatus = new Label(shlAdmin, SWT.WRAP);
								deletionStatus.setText("");
								deletionStatus.setBounds(350, 235, 50, 24);
								// Delete dining hall Button for the admin
								Button btnDeleteDiningHall = new Button(shlAdmin, SWT.NONE);
								btnDeleteDiningHall.setBounds(300, 235, 50, 24);
								btnDeleteDiningHall.setText("Delete");
								btnDeleteDiningHall.addListener(SWT.Selection, new Listener (){
									@Override
									public void handleEvent(org.eclipse.swt.widgets.Event arg0) {
										try {
											Statement statement = conn.createStatement();
											statement.setQueryTimeout(30);
											//Deletes the hall
											statement.executeUpdate("Delete FROM dining_hall WHERE dh_name = \'" + txtName.getText() + "\'");
											//Updates the status for the user
											deletionStatus.setText("Deleted " + txtName.getText() + " sucessfully!");
										} catch (SQLException e) {
											e.printStackTrace();
										}
									}});
						    }else{
						    	shlError = new Shell();
								shlError.setSize(200, 200);
								shlError.setText("Error");
								shlError.open();
								final Label lblError = new Label(shlError, SWT.WRAP);
								lblError.setFont(SWTResourceManager.getFont("Century Schoolbook", 9, SWT.BOLD));
								lblError.setBounds(0, 100, 200, 24);
								lblError.setText("Wrong Username or Password!");
						    }
						}catch(Exception E){
							System.out.println(E);
						}
					}
				
				});
				
			}
		});
	}
}
