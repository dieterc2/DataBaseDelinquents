
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.layout.GridData;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.layout.RowLayout;
import swing2swt.layout.BoxLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;


public class GUI {

	protected Shell shlMuDiningHall;
	private Text txtSearchMuDining;
	private Table table;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			GUI window = new GUI();
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
		shlMuDiningHall.open();
		shlMuDiningHall.layout();
		while (!shlMuDiningHall.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shlMuDiningHall = new Shell();
		shlMuDiningHall.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
		shlMuDiningHall.setSize(705, 465);
		shlMuDiningHall.setText("MU Dining Hall Catalog");
		shlMuDiningHall.setLayout(null);
		
		txtSearchMuDining = new Text(shlMuDiningHall, SWT.BORDER | SWT.SEARCH | SWT.CENTER);
		txtSearchMuDining.setForeground(SWTResourceManager.getColor(SWT.COLOR_DARK_RED));
		txtSearchMuDining.setFont(SWTResourceManager.getFont("Century Schoolbook", 11, SWT.ITALIC));
		txtSearchMuDining.setText("Search MU Dining.....");
		txtSearchMuDining.setBounds(332, 10, 236, 24);
		
		Button btnNewButton = new Button(shlMuDiningHall, SWT.NONE);
		btnNewButton.setForeground(SWTResourceManager.getColor(SWT.COLOR_LIST_BACKGROUND));
		btnNewButton.setFont(SWTResourceManager.getFont("Century Schoolbook", 12, SWT.NORMAL));
		btnNewButton.setText("Search");
		btnNewButton.setBounds(574, 10, 90, 25);
		
		Menu menu = new Menu(shlMuDiningHall, SWT.BAR);
		shlMuDiningHall.setMenuBar(menu);
		
		MenuItem mntmHome = new MenuItem(menu, SWT.NONE);
		mntmHome.setText("HOME");
		
		MenuItem mntmMenus = new MenuItem(menu, SWT.NONE);
		mntmMenus.setID(1);
		mntmMenus.setText("LOCATIONS");
		
		MenuItem mntmNewSubmenu = new MenuItem(menu, SWT.CASCADE);
		mntmNewSubmenu.setID(2);
		mntmNewSubmenu.setText("MENUS");
		
		Menu menu_1 = new Menu(mntmNewSubmenu);
		mntmNewSubmenu.setMenu(menu_1);
		
		MenuItem mntmNewItem = new MenuItem(menu_1, SWT.NONE);
		mntmNewItem.setText("Haines' Boulangerie");
		
		MenuItem mntmMEmporium = new MenuItem(menu_1, SWT.NONE);
		mntmMEmporium.setText("M Emporium");
		
		MenuItem mntmMeinStreetMongolian = new MenuItem(menu_1, SWT.NONE);
		mntmMeinStreetMongolian.setText("Mein Street Mongolian Grill");
		
		MenuItem mntmMiamiIce = new MenuItem(menu_1, SWT.NONE);
		mntmMiamiIce.setText("Miami Ice");
		
		MenuItem mntmPullyDiner = new MenuItem(menu_1, SWT.NONE);
		mntmPullyDiner.setText("Pully Diner");
		
		MenuItem mntmSerrano = new MenuItem(menu_1, SWT.NONE);
		mntmSerrano.setText("Serrano");
		
		MenuItem mntmSundialPizzaCompany = new MenuItem(menu_1, SWT.NONE);
		mntmSundialPizzaCompany.setText("Sundial Pizza Company");
		
		MenuItem mntmBellTowerPlace = new MenuItem(menu_1, SWT.NONE);
		mntmBellTowerPlace.setText("Bell Tower Place");
		
		MenuItem mntmDividends = new MenuItem(menu_1, SWT.NONE);
		mntmDividends.setText("Dividends");
		
		MenuItem mntmHarrisHall = new MenuItem(menu_1, SWT.NONE);
		mntmHarrisHall.setText("Harris Hall");
		
		MenuItem mntmLaMiaCucina = new MenuItem(menu_1, SWT.NONE);
		mntmLaMiaCucina.setText("La Mia Cucina");
		
		MenuItem mntmKingCafe = new MenuItem(menu_1, SWT.NONE);
		mntmKingCafe.setText("King Cafe");
		
		MenuItem mntmMaplestreetStation = new MenuItem(menu_1, SWT.NONE);
		mntmMaplestreetStation.setText("Maplestreet Station");
		
		MenuItem mntmMartinHall = new MenuItem(menu_1, SWT.NONE);
		mntmMartinHall.setText("Martin Hall");
		
		MenuItem mntmScoreboardMarket = new MenuItem(menu_1, SWT.NONE);
		mntmScoreboardMarket.setText("Scoreboard Market & Grill");
		
		MenuItem mntmTuffys = new MenuItem(menu_1, SWT.NONE);
		mntmTuffys.setText("Tuffy's");
		
		MenuItem mntmStreatsFoodTruck = new MenuItem(menu_1, SWT.NONE);
		mntmStreatsFoodTruck.setText("StrEATS Food Truck");
		
		MenuItem mntmWesternDiningCommons = new MenuItem(menu_1, SWT.NONE);
		mntmWesternDiningCommons.setText("Western Dining Commons");
		
		Button btnDiningHall = new Button(shlMuDiningHall, SWT.CHECK);
		btnDiningHall.setText("Dining Hall");
		btnDiningHall.setBounds(10, 18, 81, 16);
		
		Button btnCusine = new Button(shlMuDiningHall, SWT.CHECK);
		btnCusine.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
			}
		});
		btnCusine.setBounds(97, -1, 57, 16);
		btnCusine.setText("Cusine");
		
		Button btnMinPrice = new Button(shlMuDiningHall, SWT.CHECK);
		btnMinPrice.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
			}
		});
		btnMinPrice.setBounds(97, 18, 68, 16);
		btnMinPrice.setText("Min.Price");
		
		Button btnFoodItem = new Button(shlMuDiningHall, SWT.CHECK);
		btnFoodItem.setBounds(170, -1, 75, 16);
		btnFoodItem.setText("Food Item");
		
		Button btnOpenNow = new Button(shlMuDiningHall, SWT.CHECK);
		btnOpenNow.setBounds(170, 18, 75, 16);
		btnOpenNow.setText("Open Now");
		
		Button btnMealPlan = new Button(shlMuDiningHall, SWT.CHECK);
		btnMealPlan.setBounds(251, -1, 75, 16);
		btnMealPlan.setText("Meal Plan");
		
		Button btnSpecials = new Button(shlMuDiningHall, SWT.CHECK);
		btnSpecials.setBounds(251, 18, 63, 16);
		btnSpecials.setText("Specials");
		
		Button btnFilterSearch = new Button(shlMuDiningHall, SWT.TOGGLE);
		btnFilterSearch.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}
			@Override
			public void widgetSelected(SelectionEvent e) {
			}
		});
		btnFilterSearch.setBounds(10, -1, 81, 16);
		btnFilterSearch.setText("Filter Search");
		
		table = new Table(shlMuDiningHall, SWT.BORDER | SWT.FULL_SELECTION);
		table.setBounds(25, 55, 639, 341);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		
		TableColumn tblclmnDiningHall = new TableColumn(table, SWT.NONE);
		tblclmnDiningHall.setWidth(100);
		tblclmnDiningHall.setText("Dining Hall");
		
		TableColumn tblclmnLocation = new TableColumn(table, SWT.NONE);
		tblclmnLocation.setWidth(100);
		tblclmnLocation.setText("Location");
		
		TableItem tableItem = new TableItem(table, SWT.NONE);

	}
}
