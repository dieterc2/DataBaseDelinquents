package dininghallcatalog;


import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.Button;

import dininghalls.model.DiningHall;
import dininghalls.model.DiningHallGroup;
import org.eclipse.jface.viewers.ListViewer;

public class Catalog {

	/** The username for the default account. */
	private final String userName = "root";
	
	/** The password for the defualt account. */
	private final String password = "root";
	
	/** Name of the database. */
	static final String DB_URL = "jdbc:sqlite:C:/Users/Jon/sqlite/Project.db";
	
	private ResultSet resultSet;
	private DiningHallGroup hall_group;
	private ListViewer listViewer;
	
	/**
	 *  Setup a new connection to the database.
	 *
	 * @throws SQLException
	 */
	public Connection getConnection() throws SQLException {
		Connection conn = null;
		conn = DriverManager.getConnection(DB_URL, userName, password);
		return conn;
	}

	public void executeUpdate(Connection conn, String command) throws SQLException {
		PreparedStatement query = null;
		resultSet = null;
		try {
			query = conn.prepareStatement(command);
			resultSet = query.executeQuery();
			while (resultSet.next()){
				System.out.println(resultSet.getString("dh_name"));
			}
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
			Connection conn = null;
			conn = this.getConnection();
			System.out.println("Successfully connected to database!");
		} catch (SQLException e){
			System.out.println("Error: Could not connect to the database.");
			e.printStackTrace();
			return;
		}
	}

	protected Shell shlDatabaseDel;
	private Table table;

	
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
		setDefaultValues();
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
	 *  Create the data to be displayed in the window.
	 */
	private void setDefaultValues(){
		hall_group = new DiningHallGroup();
		String populateHalls = "SELECT * FROM dining_hall";
		try {
			executeUpdate(getConnection(), populateHalls);
			while (resultSet.next()){
				hall_group.addHall(new DiningHall(resultSet.getString("dh_name"), resultSet.getString("dh_type"), 
					resultSet.getString("location"), resultSet.getInt("adm_id1")));
			}
		}catch (SQLException e) {
			System.out.println("Error trying to populate dining halls.");
			e.printStackTrace();
		}
	}
	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shlDatabaseDel = new Shell();
		shlDatabaseDel.setSize(805, 475);
		shlDatabaseDel.setText("Database Delinquents: Miami Dining Catalog");
		
		Composite diningHallComposite = new Composite(shlDatabaseDel, SWT.BORDER);
		diningHallComposite.setBounds(2, 10, 118, 416);
		
		Label lblDiningHalls = new Label(diningHallComposite, SWT.NONE);
		lblDiningHalls.setLocation(0, 0);
		lblDiningHalls.setSize(118, 19);
		lblDiningHalls.setAlignment(SWT.CENTER);
		lblDiningHalls.setFont(SWTResourceManager.getFont("Century Schoolbook", 12, SWT.BOLD));
		lblDiningHalls.setText("Dining Halls");
		
		final org.eclipse.swt.widgets.List list = new org.eclipse.swt.widgets.List(diningHallComposite, SWT.SINGLE | SWT.BORDER);
		list.setItems(new String[] {});
		list.setFont(SWTResourceManager.getFont("Century Schoolbook", 10, SWT.NORMAL));
		list.setBounds(5, 25, 105, 385);
		GridData gridData = new GridData();
		gridData.horizontalSpan = 1;
		list.setLayoutData(gridData);
		
		listViewer = new ListViewer(diningHallComposite, SWT.NONE);
		org.eclipse.swt.widgets.List list_1 = listViewer.getList();
		list_1.setBounds(10, 33, 94, 369);
		List<DiningHall> hallList = new ArrayList<DiningHall>(hall_group.getHalls());
		String[] hallNames = new String[hallList.size()];
		for (int i = 0; i < hallNames.length; i++){
			hallNames[i] = hallList.get(i).getName();
			list.add(hallNames[i]);
		}
		
		
		
		Composite searchComposite = new Composite(shlDatabaseDel, SWT.NONE);
		searchComposite.setBounds(124, 10, 655, 61);
		
		Label lblAlreadyKnowWhat = new Label(searchComposite, SWT.NONE);
		lblAlreadyKnowWhat.setFont(SWTResourceManager.getFont("Century Schoolbook", 10, SWT.BOLD));
		lblAlreadyKnowWhat.setBounds(0, 4, 264, 16);
		lblAlreadyKnowWhat.setText("Already know what you're looking for?");
		
		Button btnStartASearch = new Button(searchComposite, SWT.NONE);
		btnStartASearch.setBounds(270, 0, 75, 25);
		btnStartASearch.setText("Start a Search");
		
		Composite detailsComposite = new Composite(shlDatabaseDel, SWT.BORDER);
		detailsComposite.setBounds(124, 71, 655, 355);
		
		ScrolledComposite menuComposite = new ScrolledComposite(detailsComposite, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		menuComposite.setBounds(0, 106, 655, 249);
		menuComposite.setExpandHorizontal(true);
		menuComposite.setExpandVertical(true);
		
		table = new Table(menuComposite, SWT.FULL_SELECTION);
		
		TableColumn tblclmnItemName = new TableColumn(table, SWT.NONE);
		tblclmnItemName.setWidth(125);
		tblclmnItemName.setText("Item Name");
		
		TableColumn tblclmnItemDescription = new TableColumn(table, SWT.NONE);
		tblclmnItemDescription.setWidth(176);
		tblclmnItemDescription.setText("Item Description");
		
		TableColumn tblclmnNutritionalInformation = new TableColumn(table, SWT.NONE);
		tblclmnNutritionalInformation.setWidth(218);
		tblclmnNutritionalInformation.setText("Nutritional Information");
		
		TableColumn tblclmnPrice = new TableColumn(table, SWT.NONE);
		tblclmnPrice.setWidth(111);
		tblclmnPrice.setText("Price");
		menuComposite.setContent(table);
		menuComposite.setMinSize(table.computeSize(SWT.DEFAULT, SWT.DEFAULT));
		
		Label permItemlbl = new Label(detailsComposite, SWT.NONE);
		permItemlbl.setBounds(2, 91, 68, 15);
		permItemlbl.setText("Item Name");
		
		Label permItemDesclbl = new Label(detailsComposite, SWT.NONE);
		permItemDesclbl.setBounds(127, 91, 87, 15);
		permItemDesclbl.setText("Item Description");
		
		Label permNutritionlbl = new Label(detailsComposite, SWT.NONE);
		permNutritionlbl.setBounds(304, 91, 123, 15);
		permNutritionlbl.setText("Nutritional Information");
		
		Label permPricelbl = new Label(detailsComposite, SWT.NONE);
		permPricelbl.setBounds(523, 91, 55, 15);
		permPricelbl.setText("Price");
		
		Label lblHallName = new Label(detailsComposite, SWT.NONE);
		lblHallName.setFont(SWTResourceManager.getFont("Century Schoolbook", 13, SWT.BOLD));
		lblHallName.setBounds(10, 10, 256, 27);
		
		Label permCuisinelbl = new Label(detailsComposite, SWT.NONE);
		permCuisinelbl.setFont(SWTResourceManager.getFont("Century Schoolbook", 10, SWT.BOLD));
		permCuisinelbl.setBounds(13, 43, 55, 15);
		permCuisinelbl.setText("Cuisine:");
		
		Label permTypelbl = new Label(detailsComposite, SWT.NONE);
		permTypelbl.setFont(SWTResourceManager.getFont("Century Schoolbook", 10, SWT.BOLD));
		permTypelbl.setBounds(13, 64, 139, 15);
		permTypelbl.setText("Type of Resturant:");
		
		Label lblCuisine = new Label(detailsComposite, SWT.NONE);
		lblCuisine.setFont(SWTResourceManager.getFont("Century Schoolbook", 9, SWT.BOLD | SWT.ITALIC));
		lblCuisine.setBounds(74, 44, 96, 15);
		
		Label lblType = new Label(detailsComposite, SWT.NONE);
		lblType.setFont(SWTResourceManager.getFont("Century Schoolbook", 9, SWT.BOLD | SWT.ITALIC));
		lblType.setBounds(158, 64, 80, 15);
		
		Label permHourslbl = new Label(detailsComposite, SWT.NONE);
		permHourslbl.setFont(SWTResourceManager.getFont("Century Schoolbook", 10, SWT.BOLD));
		permHourslbl.setBounds(252, 43, 46, 15);
		permHourslbl.setText("Hours:");
		
		Label permLoclbl = new Label(detailsComposite, SWT.NONE);
		permLoclbl.setFont(SWTResourceManager.getFont("Century Schoolbook", 10, SWT.BOLD));
		permLoclbl.setBounds(252, 64, 68, 15);
		permLoclbl.setText("Location:");
		
		Label lblHours = new Label(detailsComposite, SWT.NONE);
		lblHours.setFont(SWTResourceManager.getFont("Century Schoolbook", 9, SWT.ITALIC));
		lblHours.setBounds(304, 43, 324, 15);
		
		Label lblArmstrongStudentCenter = new Label(detailsComposite, SWT.NONE);
		lblArmstrongStudentCenter.setFont(SWTResourceManager.getFont("Century Schoolbook", 9, SWT.NORMAL));
		lblArmstrongStudentCenter.setBounds(325, 64, 253, 15);
		//initDataBindindings();
	}
}
