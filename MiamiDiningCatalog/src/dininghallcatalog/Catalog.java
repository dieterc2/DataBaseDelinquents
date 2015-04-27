package dininghallcatalog;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Button;

public class Catalog {

	/** The username for the default account. */
	private final String userName = "root";
	
	/** The password for the defualt account. */
	private final String password = "root";
	
	/** Name of the database. */
	static final String DB_URL = "jdbc:sqlite:C:/Users/Jon/sqlite/MUDining.db";
	
	public static ResultSet rs = null;
	
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
		rs = null;
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
		Connection conn = null;
		try {
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
		String testQuery = "SELECT * FROM dining_hall";
		app.executeUpdate(app.getConnection(), testQuery);
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
		
		List list = new List(diningHallComposite, SWT.BORDER);
		list.setBounds(0, 21, 114, 391);
		try {
			while (rs.next()){
				list.add(rs.getString("dh_name"));
			}
		} catch (SQLException e) {
			System.out.println("Shit broke.");
			e.printStackTrace();
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

	}
}
