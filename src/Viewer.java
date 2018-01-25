import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.*;

/**
 * Viewer class that implements JButton objects into a 2d Object array viewable
 * on a JTable. Objects are clickable to display information related to the
 * object in question. JButtons have their own instance so data can be
 * manipulated to display individualized data.
 * 
 *
 *
 */
public class Viewer extends JFrame implements ActionListener {
	private static JMenuItem addEmployee, removeEmployee, updateEmployee, searchEmployee, addVenue, removeVenue,
			updateVenue, searchVenue, saveFile, refreshTable, addBlacklisted, searchBlacklistedEmployee;

	JTextField whatToUpdateField; // = new JTextField(25);
	JTextField updateField;// = new JTextField(25);
	JTextField updateIDField;// = new JTextField(25);
	JTextField addID, addFName, addLName, addPassword, addPhone, addEmail, addVenAddress, addVenName, addVenTables,
			addEID, addVID;
	JMenuBar menuBar;
	JMenu empMenu, venMenu, fileMenu, blackListMenu;
	DefaultTableModel tableModel;
	Scheduler generateSchedule = new Scheduler();
	ArrayList<Event> scheduler;
	Object[][] data;
	String inputID;
	DefaultTableModel model;
	Employee employee;
	ArrayList<Employee> empList;

	Object[] empInfo, venInfo, blackInfo;

	static JTable table;

	/**
	 * Constructor that adjusts look and feel, calls the schedule generator
	 * function and loads the information into a 2D Object array. This data
	 * array is then loaded into a a JTable. It calls the Overridden classes for
	 * editing cells in order to make employee names on the left hand side
	 * interactable.
	 * 
	 */
	public Viewer() {
		// FORM TITLE
		super("Table Schedule View");

		TableColumn columnModel;
		menuBar = new JMenuBar();
		empMenu = new JMenu("Employees");
		venMenu = new JMenu("Venues");
		fileMenu = new JMenu("File");
		blackListMenu = new JMenu("Blacklist");
		JPanel panel = new JPanel();

		empList = new ArrayList<Employee>();

		// Get employee data and stores into a list. Sets look and feel to
		// Nimbus. This can be changed if we don't like it.
		try {
			empList = Database.getEmployees();
			UIManager.setLookAndFeel("com.seaglasslookandfeel.SeaGlassLookAndFeel");

		} catch (Exception e) {
			// If Nimbus is not available, you can set the GUI to another look
			// and feel.
		}

		// Initializes array row length to total employee size from database.
		// Columns set to
		Object[] colDays = { "Name", "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday" };
		data = new Object[empList.size()][colDays.length];
		// Sets data in each of the data Object's cells
		ArrayList<ArrayList<Event>> schedulerList = new ArrayList<ArrayList<Event>>();
		for (int i = 0; i < colDays.length; i++) {
			scheduler = generateSchedule.scheduleGenerator();
			schedulerList.add(scheduler);
		}
		for (int i = 0; i < empList.size(); i++) { // empList.size()
			Employee employee = empList.get(i);

			for (int fillDays = 0; fillDays < colDays.length; fillDays++) {
				scheduler = schedulerList.get(fillDays);
				Venue venue = null;
				for (Event event : scheduler) {
					if (event.getEmployee().equals(employee)) {
						venue = event.getVenue();
					}
				}
				if (venue != null)
					data[i][fillDays] = venue.getID();
				else
					data[i][fillDays] = "";
			}
			data[i][0] = empList.get(i);
		}

		model = new DefaultTableModel(data, colDays);
		table = new JTable(model);
		// create new table with an overriden tooltip
		HashMap<String, Venue> venueDict = new HashMap<String, Venue>();
		try {
			for (Venue venue : Database.getVenues()) {
				venueDict.put(venue.getID(), venue);
			}
		} catch (Exception e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		table = new JTable(data, colDays) {

			// Implement table cell tool tips.
			public String getToolTipText(MouseEvent e) {
				java.awt.Point p = e.getPoint();
				String tip = null;
				int row = rowAtPoint(p);
				int column = columnAtPoint(p);

				try {
					// comment row, exclude heading
					if (row > 0 && column > 0) {
						String venueId = data[row][column].toString();
						Venue venue = venueDict.get(venueId);
						tip = "<html>" + venue.getName() + "<br>Tables: " + venue.getTables() + "</html>";
					}
				} catch (RuntimeException e1) {
					// catch null pointer exception if mouse is over an empty
					// line
				}

				return tip;
			}
		};

		// Table resizing
		table.setAutoResizeMode(table.AUTO_RESIZE_OFF);
		columnModel = table.getColumnModel().getColumn(0);
		columnModel.setPreferredWidth(180);

		for (int i = 1; i < colDays.length; i++) {
			table.setRowHeight(20);
			columnModel = table.getColumnModel().getColumn(i);
			columnModel.setPreferredWidth(150);
		}

		// Sets a particular Column as JButtons
		table.getColumnModel().getColumn(0).setCellRenderer(new ButtonRenderer());

		// SET CUSTOM EDITOR TO TEAMS COLUMN

		table.getColumnModel().getColumn(0).setCellEditor(new ButtonEditor(new JTextField()));

		// SCROLLPANE,SET SZE,SET CLOSE OPERATION
		JScrollPane pane = new JScrollPane(table);

		// Any and all things related to the swing components being attached to
		// the frame

		// File Menu Items
		saveFile = new JMenuItem("Export to Excel");
		refreshTable = new JMenuItem("Refresh");

		// Employee Menu Items
		addEmployee = new JMenuItem("Add Employee");
		removeEmployee = new JMenuItem("Remove Employee");
		updateEmployee = new JMenuItem("Update Employee");
		searchEmployee = new JMenuItem("Search Employee");

		// Venue Menu Items
		addVenue = new JMenuItem("Add Venue");
		removeVenue = new JMenuItem("Remove Venue");
		updateVenue = new JMenuItem("Update Venue");
		searchVenue = new JMenuItem("Search Venue");

		// Blacklist Menu Items
		addBlacklisted = new JMenuItem("Add Blacklisted Employee");
		searchBlacklistedEmployee = new JMenuItem("Search Blacklisted Employees");

		// Add File Menu Items to the File Menu
		fileMenu.add(refreshTable);
		fileMenu.add(saveFile);

		// Add Employee Menu Items to the Employee Menu
		empMenu.add(searchEmployee);
		empMenu.add(updateEmployee);
		empMenu.add(addEmployee);
		empMenu.add(removeEmployee);

		// Add Venue Menu Items to the Employee Menu
		venMenu.add(searchVenue);
		venMenu.add(updateVenue);
		venMenu.add(addVenue);
		venMenu.add(removeVenue);

		// Add Blacklist Menu Items to the Blacklist Menu

		blackListMenu.add(searchBlacklistedEmployee);
		blackListMenu.add(addBlacklisted);

		// Action Listeners for different Menu Items

		saveFile.addActionListener(this);

		addEmployee.addActionListener(this);
		removeEmployee.addActionListener(this);
		updateEmployee.addActionListener(this);
		searchEmployee.addActionListener(this);

		addVenue.addActionListener(this);
		removeVenue.addActionListener(this);
		updateVenue.addActionListener(this);
		searchVenue.addActionListener(this);

		addBlacklisted.addActionListener(this);
		searchBlacklistedEmployee.addActionListener(this);

		// Add Menus to the Menu Bar
		menuBar.add(fileMenu);
		menuBar.add(empMenu);
		menuBar.add(venMenu);
		menuBar.add(blackListMenu);

		// Adds the JTable with the Scroll pane to a Panel so Menus can be
		// displayed on the Frame
		panel.setLayout(new BorderLayout());
		panel.add(pane, BorderLayout.CENTER);

		table.repaint();
		// Frame settings
		setSize(1280, 720);
		setLocation(800, 300);
		setLayout(new BorderLayout());
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);

		// Adding different Swing components to the Frame
		setJMenuBar(menuBar);
		add(panel, BorderLayout.CENTER);

	}

	/**
	 * Overrides actionPerformed to allow easier action listeners for all menu
	 * items.
	 * 
	 * @param menuItem
	 *            Menu Item that is passed and retrieves a specific function
	 *            call if the Menu Item equals a some JMenuItem.
	 */
	@Override
	public void actionPerformed(ActionEvent menuItem) {
		Venue venue = null;
		Employee employee = null;

		// Actions for File Menu Items

		if (menuItem.getSource().equals(refreshTable)) {

		}

		if (menuItem.getSource().equals(saveFile)) {
			String fileName = JOptionPane.showInputDialog("File name:");
			ExcelExporter ee = new ExcelExporter(fileName);
			ee.addSheet("Schedule", ExcelExporter.scheduleHeaders, data);
			ee.finish();
			JOptionPane.showMessageDialog(null, "File " + fileName + " successfully saved!");
		}

		// Actions for Employee Menu Items
		if (menuItem.getSource().equals(searchEmployee)) {

			inputID = JOptionPane.showInputDialog("Enter a Employee ID: ");

			try {
				employee = Database.searchEmployeeID(inputID);
				if (employee == null) {
					JOptionPane.showMessageDialog(null, "No employee with that ID was found", "Invaild ID",
							JOptionPane.INFORMATION_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(null,
							"ID: " + employee.getId().toUpperCase() + "\n" + "Name: " + employee.getFullName() + "\n"
									+ "Phone: " + employee.getPhone() + "\n" + "Email: " + employee.getEmail(),
							"Employee Info", JOptionPane.INFORMATION_MESSAGE);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		if (menuItem.getSource().equals(addEmployee)) {
			addID = new JTextField(10);
			addFName = new JTextField(15);
			addLName = new JTextField(15);
			addPassword = new JTextField(15);
			addPhone = new JTextField(15);
			addEmail = new JTextField(25);

			Object[] empInfo = { "User ID: ", addID, "First Name: ", addFName, "Last Name: ", addLName,
					"Choose a Password: ", addPassword, "Phone Number: ", addPhone, "Email: ", addEmail };
			createEmployeeInfoBox(empInfo, "Add a New Employee");
			JOptionPane.showMessageDialog(null,
					"ID: " + addID.getText() + "\n" + "Name: " + addFName.getText() + " " + addLName.getText() + "\n"
							+ "Phone: " + addPhone.getText() + "\n" + "Email: " + addEmail.getText(),
					"New Employee Added", JOptionPane.INFORMATION_MESSAGE);
		}

		if (menuItem.getSource().equals(removeEmployee)) {
			inputID = JOptionPane.showInputDialog("Enter the Employee ID for the employee you wish to remove: ");
			try {
				employee = Database.searchEmployeeID(inputID);
				Database.removeEmployee(inputID);
				JOptionPane.showMessageDialog(null, "Employee " + employee.getFullName() + " has been removed.",
						"Removed employee", JOptionPane.INFORMATION_MESSAGE);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		if (menuItem.getSource().equals(updateEmployee)) {
			inputID = JOptionPane.showInputDialog("ID for the Employee you would like to update: ");

			try {
				employee = Database.searchEmployeeID(inputID);
				addFName = new JTextField(employee.getFirstName());
				addLName = new JTextField(employee.getLastName());
				addPassword = new JTextField(employee.getPassword());
				addPhone = new JTextField(employee.getPhone());
				addEmail = new JTextField(employee.getEmail());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			Object[] empInfo = { "First Name: ", addFName, "Last Name: ", addLName, "Choose a Password: ", addPassword,
					"Phone Number: ", addPhone, "Email: ", addEmail };
			updateEmployeeInfoBox(empInfo, "Update Employee Information");
			JOptionPane.showMessageDialog(null,
					"ID: " + inputID + "\n" + "Name: " + addFName.getText() + " " + addLName.getText() + "\n"
							+ "Phone: " + addPhone.getText() + "\n" + "Email: " + addEmail.getText(),
					"Employee Updated", JOptionPane.INFORMATION_MESSAGE);

		}

		// Actions for Venue Menu Items
		if (menuItem.getSource().equals(searchVenue)) {
			inputID = JOptionPane.showInputDialog("Enter a Venue ID Name: ");

			try {
				venue = Database.searchVenueID(inputID);
				if (venue == null) {
					JOptionPane.showMessageDialog(null, "No venue with that ID was found", "Invaild ID",
							JOptionPane.INFORMATION_MESSAGE);
					System.out.println("Invalid ID");
				} else {
					JOptionPane.showMessageDialog(null,
							"ID: " + venue.getID().toUpperCase() + "\n" + "Name: " + venue.getName() + "\n"
									+ "Table Amount: " + venue.getTables() + "\n" + "Address: " + venue.getAddress(),
							"Venue Info", JOptionPane.INFORMATION_MESSAGE);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		if (menuItem.getSource().equals(addVenue)) {
			addID = new JTextField(10);
			addVenName = new JTextField(15);
			addVenTables = new JTextField(15);
			addVenAddress = new JTextField(25);

			Object[] venInfo = { "Venue ID: ", addID, "Venue Name: ", addVenName, "Table Amount: ", addVenTables,
					"Address: ", addVenAddress };
			createVenueInfoBox(venInfo, "Add a New Venue");
			JOptionPane.showMessageDialog(null,
					"ID: " + addID.getText() + "\n" + "Name: " + addVenName.getText() + "\n" + "Tables: "
							+ addVenTables.getText() + "\n" + "Address: " + addVenAddress.getText(),
					"New Venue Added", JOptionPane.INFORMATION_MESSAGE);

		}

		if (menuItem.getSource().equals(removeVenue)) {
			inputID = JOptionPane.showInputDialog("Enter a Venue ID: ");
			try {
				venue = Database.searchVenueID(inputID);
				if (venue == null) {
					JOptionPane.showMessageDialog(null, "No venue with that ID was found", "Invaild ID",
							JOptionPane.INFORMATION_MESSAGE);
				} else {
					Database.removeVenue(inputID);
					JOptionPane.showMessageDialog(null, "Venue " + venue.getName() + " has been removed.", "Venue Info",
							JOptionPane.INFORMATION_MESSAGE);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		if (menuItem.getSource().equals(updateVenue)) {
			inputID = JOptionPane.showInputDialog("ID for the Venue you would like to update: ");

			try {
				venue = Database.searchVenueID(inputID);
				addVenName = new JTextField(venue.getName());
				addVenTables = new JTextField(String.valueOf(venue.getTables()));
				addVenAddress = new JTextField(venue.getAddress());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			Object[] venInfo = { "Venue Name: ", addVenName, "Table Amount: ", addVenTables, "Address: ",
					addVenAddress };

			updateVenueInfoBox(venInfo, "Update Venue Information");
			JOptionPane.showMessageDialog(null,
					"ID: " + inputID + "\n" + "Name: " + addVenName.getText() + "\n" + "Table Amount: "
							+ addVenTables.getText() + "\n" + "Address: " + addVenAddress.getText(),
					"Venue Updated", JOptionPane.INFORMATION_MESSAGE);

		}

		// Actions for Blacklisting Employees
		if (menuItem.getSource().equals(addBlacklisted)) {
			addEID = new JTextField(10);
			addVID = new JTextField(10);

			Object[] blackInfo = { "Employee ID: ", addEID, "Venue ID: ", addVID };
			createBlacklistInfoBox(blackInfo, "Add a New Blacklist");
			JOptionPane.showMessageDialog(null,
					"Employee " + addEID.getText() + " is now blacklisted from Venue " + addVID.getText(),
					"Blacklist Added", JOptionPane.INFORMATION_MESSAGE);
		}

		if (menuItem.getSource().equals(searchBlacklistedEmployee)) {
			System.out.println("Search Blacklisted");

		}
	}

	/**
	 * 
	 * @param empInfo
	 *            Loads in an object array for a popup window with editable text
	 *            fields in order to create a new Employee employee.
	 * @param boxTitle
	 *            Title of the new popup window.
	 */
	public void createEmployeeInfoBox(Object[] empInfo, String boxTitle) {
		try {
			JOptionPane.showConfirmDialog(null, empInfo, boxTitle, JOptionPane.OK_CANCEL_OPTION);
			Database.addEmployee(addID.getText(), addFName.getText(), addLName.getText(), addPassword.getText(),
					addPhone.getText(), addEmail.getText(), false);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showMessageDialog(null,
					"I'm sorry, something went wrong. Please try to add the employee again!",
					"Add Employee Error", JOptionPane.INFORMATION_MESSAGE);
		}

	}

	/**
	 * 
	 * @param empInfo
	 *            Loads in an object array for a popup window with editable text
	 *            fields in order to update an employee.
	 * @param boxTitle
	 */
	public void updateEmployeeInfoBox(Object[] empInfo, String boxTitle) {
		try {
			JOptionPane.showConfirmDialog(null, empInfo, boxTitle, JOptionPane.OK_CANCEL_OPTION);
			Database.removeEmployee(inputID);
			Database.addEmployee(inputID, addFName.getText(), addLName.getText(), addPassword.getText(),
					addPhone.getText(), addEmail.getText(), false);
			JOptionPane.showMessageDialog(null,
					"Successfully updated " + addFName.getText() + " " + addLName.getText() + " in the database!",
					"Updated Employee Successfully", JOptionPane.INFORMATION_MESSAGE);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showMessageDialog(null,
					"I'm sorry, something went wrong. Please try to update the employee again!",
					"Update Employee Error", JOptionPane.INFORMATION_MESSAGE);
		}
	}

	/**
	 * 
	 * @param venInfo
	 *            Loads in an object array for a popup window with editable text
	 *            fields in order to create a new Venue venue.
	 * @param boxTitle
	 */
	public void createVenueInfoBox(Object[] venInfo, String boxTitle) {
		try {
			JOptionPane.showConfirmDialog(null, venInfo, boxTitle, JOptionPane.OK_CANCEL_OPTION);
			Database.addVenue(addID.getText(), addVenName.getText(), addVenTables.getText(), addVenAddress.getText());
			JOptionPane.showMessageDialog(null,
					"Successfully added " + addFName.getText() + " " + addLName.getText() + " in the database!",
					"Added Employee Successfully", JOptionPane.INFORMATION_MESSAGE);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showMessageDialog(null,
					"I'm sorry, something went wrong. Please try to add the venue again!",
					"Add Venue Error", JOptionPane.INFORMATION_MESSAGE);
		}
	}

	/**
	 * 
	 * @param blackInfo
	 *            Loads in an object array for a popup window with editable text
	 *            fields in order to create a new Blacklist between an employee
	 *            and venue.
	 * @param boxTitle
	 */
	public void createBlacklistInfoBox(Object[] blackInfo, String boxTitle) {
		try {
			JOptionPane.showConfirmDialog(null, blackInfo, boxTitle, JOptionPane.OK_CANCEL_OPTION);
			Database.addBlacklisted(addEID.getText(), addVID.getText());
			JOptionPane.showMessageDialog(null,
					"Successfully added a blacklist for employee " + addEID.getText() + " and " + addVID.getText() + " in the database!",
					"Added Blacklist Successfully", JOptionPane.INFORMATION_MESSAGE);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showMessageDialog(null,
					"I'm sorry, something went wrong. Please try to add the blacklist again!",
					"Add Blacklist Error", JOptionPane.INFORMATION_MESSAGE);
		}
	}

	/**
	 * 
	 * @param venInfo
	 *            Loads in an object array for a popup window with editable text
	 *            fields in order to update a venue.
	 * @param boxTitle
	 */
	public void updateVenueInfoBox(Object[] venInfo, String boxTitle) {
		try {
			JOptionPane.showConfirmDialog(null, venInfo, boxTitle, JOptionPane.OK_CANCEL_OPTION);
			Database.removeVenue(inputID);
			Database.addVenue(inputID, addVenName.getText(), addVenTables.getText(), addVenAddress.getText());
			JOptionPane.showMessageDialog(null,
					"Successfully updated " + addVenName.getText() + " in the database!",
					"Updated Venue Successfully", JOptionPane.INFORMATION_MESSAGE);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showMessageDialog(null,
					"I'm sorry, something went wrong. Please try to update the venue again!",
					"Update Venue Error", JOptionPane.INFORMATION_MESSAGE);
		}
	}

}

/**
 * Necessary Renderer class to override JTables distaste for JButtons.
 * 
 */
class ButtonRenderer extends JButton implements TableCellRenderer {
	// CONSTRUCTOR
	public ButtonRenderer() {
		setOpaque(true);
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object obj, boolean selected, boolean focused, int row,
			int col) {

		// SET PASSED OBJECT AS BUTTON TEXT
		Employee employee = (Employee) obj;
		setText((employee == null) ? "" : employee.getFullName());

		return this;
	}

}

/**
 * Creates a JTable filled with instanced employee information for the button
 * clicked. Includes Name, ID, Phone number, and email.
 *
 */
class ButtonEditor extends DefaultCellEditor {
	protected JButton btn;
	private String lbl;
	private boolean clicked;
	Object[][] empInfoRows;
	Object[] empColNames = { "ID", "Name", "Phone Number", "Email" };
	JTable empInfoTable;
	JDialog empInfoBox;

	public ButtonEditor(JTextField txt) {
		super(txt);

		btn = new JButton(txt.getText());
		btn.setOpaque(true);
	}

	/**
	 * sets the label of the cell as an Employee's name if it exists. When the
	 * button is clicked, it displayed all information related to that employee.
	 */
	@Override
	public Component getTableCellEditorComponent(JTable table, Object obj, boolean selected, int row, int col) {

		// SET TEXT TO BUTTON,SET CLICKED TO TRUE,THEN RETURN THE BTN OBJECT
		Employee employee = (Employee) obj;
		lbl = (employee == null) ? "" : employee.getFullName();
		btn.setText(lbl);
		clicked = true;

		// WHEN BUTTON IS CLICKED
		for (ActionListener al : btn.getActionListeners())
			btn.removeActionListener(al);

		Object[][] empInfoRows = {
				{ employee.getId(), employee.getFullName(), employee.getPhone(), employee.getEmail() } };
		empInfoTable = new JTable(empInfoRows, empColNames);

		btn.addActionListener(new ActionListener() {

			// Creates a dialog box displaying Employee information
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null,
						"ID: " + employee.getId().toUpperCase() + "\n" + "Name: " + employee.getFullName() + "\n"
								+ "Phone: " + employee.getPhone() + "\n" + "Email: " + employee.getEmail(),
						"Employee Info", JOptionPane.INFORMATION_MESSAGE);
			}
		});

		return btn;
	}

	// DON'T DEAD OPEN INSIDE
	@Override
	public Object getCellEditorValue() {
		if (clicked) {
			// NUH UH
		}
		clicked = false;
		return super.getCellEditorValue();
	}

	@Override
	public boolean stopCellEditing() {
		// SET CLICKED TO FALSE FIRST
		clicked = false;
		return super.stopCellEditing();
	}

	// DON'T DEAD OPEN INSIDE
	@Override
	protected void fireEditingStopped() {
		// DON'T EVEN
	}
}