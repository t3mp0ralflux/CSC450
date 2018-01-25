import java.sql.*;
import java.util.ArrayList;

// based on www.vogella.com/tutorials/MySQLJava/article.html

/**
 * 
 * Database class to connect to the remote database and execute queries for
 * employee, venue, and any other data sets stored into class objects. Includes
 * functions to manipulate data and update the current database
 *
 */
public final class Database {
	// Data needed to access server and database
	private final static String domain = "65.184.201.211";
	private final static String port = "3306";
	private final static String database_name = "csc450";
	private final static String sql_username = "ctd";
	private final static String sql_passwd = "eG*OSrpn4NZy";

	private static Connection connection;
	private static Statement statement;
	private static PreparedStatement preparedStatement;
	private static ResultSet resultSet;

	// Employee functions
	/*
	 * Connects to the database and generates an array list of all the employees
	 * in the database
	 */
	public static ArrayList<Employee> getEmployees() throws Exception {
		ArrayList<Employee> employees = new ArrayList<Employee>();
		try {
			/* Open connection to the database */
			connect();

			/* Executes query and saves result into result set */
			preparedStatement = connection.prepareStatement("select * from employee;");
			resultSet = preparedStatement.executeQuery();

			/*
			 * While there is an row in result set, create a new employee and
			 * add it to the array list
			 */
			while (resultSet.next()) {
				String id = resultSet.getString("employeeID");
				String firstName = resultSet.getString("fName");
				String lastName = resultSet.getString("lName");
				String password = resultSet.getString("password");
				String phone = resultSet.getString("phone");
				String email = resultSet.getString("email");
				boolean isManager = resultSet.getBoolean("isManager");

				Employee employee = new Employee(id, firstName, lastName, password, phone, email);
				employees.add(employee);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			/* Close connection to the database */
			close();
		}
		return employees;
	}

	/*
	 * Connects to the database and generates a string of one of the column of
	 * one of the employees from the database
	 */
	public static String getEmployeeInfo(String fName, String lName, String column) throws Exception {
		String info = new String();
		try {
			/* Open connection to the database */
			connect();

			/* Executes query and saves result into result set */
			preparedStatement = connection.prepareStatement("call GetEmployeeInfo(?, ?, ?);");
			preparedStatement.setString(1, fName);
			preparedStatement.setString(2, lName);
			preparedStatement.setString(3, column);
			resultSet = preparedStatement.executeQuery();

			resultSet.next();

			/* Save data into variable */
			info = resultSet.getString(column);
		} catch (Exception e) {
			throw e;
		} finally {
			/* Close connection to the database */
			close();
		}
		return info;
	}

	/* Connects to the database and adds a new employee into the database */
	public static void addEmployee(String eID, String fName, String lName, String password, String phone, String email,
			Boolean isManager) throws Exception {
		try {
			/* Open connection to the database */
			connect();

			/* Executes query */
			preparedStatement = connection.prepareStatement("call AddEmployee(?, ?, ?, ?, ?, ?, ?);");
			preparedStatement.setString(1, eID);
			preparedStatement.setString(2, fName);
			preparedStatement.setString(3, lName);
			preparedStatement.setString(4, password);
			preparedStatement.setString(5, phone);
			preparedStatement.setString(6, email);
			preparedStatement.setBoolean(7, isManager);

			preparedStatement.executeUpdate();
		} catch (Exception e) {
			throw e;
		} finally {
			/* Close connection to the database */
			close();
		}
	}

	/* Connects to the database and updates one field of one employee's info */
	public static void updateEmployee(String column, String value, String ID) throws Exception {
		try {
			/* Open connection to the database */
			connect();

			/* Executes query */
			preparedStatement = connection.prepareStatement("call UpdateEmployee(?, ?, ?);");
			preparedStatement.setString(1, column);
			preparedStatement.setString(2, value);
			preparedStatement.setString(3, ID);

			preparedStatement.executeUpdate();
		} catch (Exception e) {
			throw e;
		} finally {
			/* Close connection to the database */
			close();
		}
	}

	/*
	 * Connects to the database and updates whether and employee is a manager or
	 * not
	 */
	public static void updateManager(Boolean isMan, String eID) throws Exception {
		try {
			/* Open connection to the database */
			connect();

			/* Executes query */
			preparedStatement = connection.prepareStatement("call UpdateManager(?, ?);");
			preparedStatement.setBoolean(1, isMan);
			preparedStatement.setString(2, eID);

			preparedStatement.executeUpdate();
		} catch (Exception e) {
			throw e;
		} finally {
			/* Close connection to the database */
			close();
		}
	}

	/*
	 * Connects to the database and searches for an employee by their full name
	 * and returns an employee, or null if no employee if found
	 */
	public static Employee searchEmployee(String fName, String lName) throws Exception {
		/* Set employee as null to begin with */
		Employee employee = null;
		try {
			/* Open connection to the database */
			connect();

			/* Executes query and saves result into result set */
			preparedStatement = connection.prepareStatement("call SearchEmployee(?, ?);");
			preparedStatement.setString(1, fName);
			preparedStatement.setString(2, lName);
			resultSet = preparedStatement.executeQuery();

			/*
			 * If an employee if found, create an employee, otherwise keep
			 * employee as null
			 */
			if (resultSet.next() == true) {
				String eID = resultSet.getString("employeeID");
				String password = resultSet.getString("password");
				String phone = resultSet.getString("phone");
				String email = resultSet.getString("email");

				employee = new Employee(eID, fName, lName, password, phone, email);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			/* Close connection to the database */
			close();
		}
		return employee;
	}

	/*
	 * Connects to the database and searches for an employee by their ID and
	 * returns an employee, or null if no employee if found
	 */
	public static Employee searchEmployeeID(String eID) throws Exception {
		/* Set employee as null to begin with */
		Employee employee = null;
		try {
			/* Open connection to the database */
			connect();

			/* Executes query and saves result into result set */
			preparedStatement = connection.prepareStatement("call SearchEmployeeID(?);");
			preparedStatement.setString(1, eID);
			resultSet = preparedStatement.executeQuery();

			/*
			 * If an employee if found, create an employee, otherwise keep
			 * employee as null
			 */
			if (resultSet.next() == true) {
				String fName = resultSet.getString("fName");
				String lName = resultSet.getString("lName");
				String password = resultSet.getString("password");
				String phone = resultSet.getString("phone");
				String email = resultSet.getString("email");

				employee = new Employee(eID, fName, lName, password, phone, email);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			/* Close connection to the database */
			close();
		}
		return employee;
	}

	/* Connects to the database and removes an employee by their ID */
	public static void removeEmployee(String eID) throws Exception {
		try {
			/* Open connection to the database */
			connect();

			/* Executes query */
			preparedStatement = connection.prepareStatement("call RemoveEmployee(?)");
			preparedStatement.setString(1, eID);

			preparedStatement.executeUpdate();
		} catch (Exception e) {
			throw e;
		} finally {
			/* Close connection to the database */
			close();
		}
	}

	// Venue functions
	/*
	 * Connects to the database and generates an array list of all the venues in
	 * the database
	 */
	public static ArrayList<Venue> getVenues() throws Exception {
		ArrayList<Venue> venues = new ArrayList<Venue>();
		try {
			/* Open connection to the database */
			connect();

			/* Executes query and saves result into result set */
			preparedStatement = connection.prepareStatement("select * from venue;");
			resultSet = preparedStatement.executeQuery();

			/*
			 * While there is an row in result set, create a new employee and
			 * add it to the array list
			 */
			while (resultSet.next()) {
				String id = resultSet.getString("venueID");
				String name = resultSet.getString("name");
				int tables = resultSet.getInt("tableNum");
				String address = resultSet.getString("address");

				Venue venue = new Venue(id, name, tables, address);
				venues.add(venue);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			/* Close connection to the database */
			close();
		}
		return venues;
	}

	/*
	 * Connects to the database and generates a string of one of the column of
	 * one of the venues from the database
	 */
	public static String getVenueInfo(String name, String column) throws Exception {
		String info = new String();
		try {
			/* Open connection to the database */
			connect();

			/* Executes query and saves result into result set */
			preparedStatement = connection.prepareStatement("call GetVenueInfo(?, ?);");
			preparedStatement.setString(1, name);
			preparedStatement.setString(3, column);
			resultSet = preparedStatement.executeQuery();

			resultSet.next();

			/* Save data into variable */
			info = resultSet.getString(column);
		} catch (Exception e) {
			throw e;
		} finally {
			/* Close connection to the database */
			close();
		}
		return info;
	}

	/* Connects to the database and adds a new venue into the database */
	public static void addVenue(String vID, String name, String tables, String address) throws Exception {
		try {
			/* Open connection to the database */
			connect();

			/* Executes query */
			preparedStatement = connection.prepareStatement("call AddVenue(?, ?, ?, ?)");
			preparedStatement.setString(1, vID);
			preparedStatement.setString(2, name);
			preparedStatement.setString(3, tables);
			preparedStatement.setString(4, address);

			preparedStatement.executeUpdate();
		} catch (Exception e) {
			throw e;
		} finally {
			/* Close connection to the database */
			close();
		}
	}

	/* Connects to the database and updates one field of one venue's info */
	/**
	 * 
	 * @param column
	 *            is what item you want to replace
	 * @param value
	 *            is what you are changing the item to
	 * @param ID
	 *            is the employee or venue id that you are wishing to change.
	 * @throws Exception
	 */
	public static void updateVenue(String column, String value, String ID) throws Exception {
		try {
			/* Open connection to the database */
			connect();

			/* Executes query */
			preparedStatement = connection.prepareStatement("call UpdateVenue(?, ?, ?);");
			preparedStatement.setString(1, column);
			preparedStatement.setString(2, value);
			preparedStatement.setString(3, ID);

			preparedStatement.executeUpdate();
		} catch (Exception e) {
			throw e;
		} finally {
			/* Close connection to the database */
			close();
		}
	}

	/*
	 * Connects to the database and searches for a venue by its name and returns
	 * an venue, or null if no venue if found
	 */
	public static Venue searchVenue(String name) throws Exception {
		Venue venue = null;
		try {
			/* Open connection to the database */
			connect();

			/* Executes query and saves result into result set */
			preparedStatement = connection.prepareStatement("call SearchVenue(?)");
			preparedStatement.setString(1, name);
			resultSet = preparedStatement.executeQuery();

			/*
			 * If an venue if found, create a venue, otherwise keep employee as
			 * null
			 */
			if (resultSet.next() == true) {
				String vID = resultSet.getString("venueID");
				int tables = resultSet.getInt("tableNum");
				String address = resultSet.getString("address");

				venue = new Venue(vID, name, tables, address);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			/* Close connection to the database */
			close();
		}
		return venue;
	}

	/*
	 * Connects to the database and searches for a venue by its ID and returns
	 * an venue, or null if no venue if found
	 */
	public static Venue searchVenueID(String vID) throws Exception {
		Venue venue = null;
		try {
			/* Open connection to the database */
			connect();

			/* Executes query and saves result into result set */
			preparedStatement = connection.prepareStatement("call SearchVenueID(?)");
			preparedStatement.setString(1, vID);
			resultSet = preparedStatement.executeQuery();

			/*
			 * If an venue if found, create a venue, otherwise keep employee as
			 * null
			 */
			if (resultSet.next() == true) {
				String name = resultSet.getString("name");
				int tables = resultSet.getInt("tableNum");
				String address = resultSet.getString("address");

				venue = new Venue(vID, name, tables, address);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			/* Close connection to the database */
			close();
		}
		return venue;
	}

	/* Connects to the database and removes a venue by its ID */
	public static void removeVenue(String vID) throws Exception {
		try {
			/* Open connection to the database */
			connect();

			/* Executes query */
			preparedStatement = connection.prepareStatement("call RemoveVenue(?)");
			preparedStatement.setString(1, vID);

			preparedStatement.executeUpdate();
		} catch (Exception e) {
			throw e;
		} finally {
			/* Close connection to the database */
			close();
		}
	}

	// Blacklisted functions
	/*
	 * Connects to the database and generates an array list of all the
	 * blacklists in the database
	 */
	public static ArrayList<ArrayList<String>> getBlacklisted() throws Exception {
		ArrayList<String> blacklist = new ArrayList<String>();
		ArrayList<ArrayList<String>> blacklists = new ArrayList<ArrayList<String>>();
		try {
			/* Open connection to the database */
			connect();

			/* Executes query and saves result into result set */
			preparedStatement = connection.prepareStatement("call GetTable(blacklisted)");
			resultSet = preparedStatement.executeQuery();

			/*
			 * While there is an row in result set, create a new blacklist array
			 * list and add it to the array list
			 */
			while (resultSet.next())
				;
			{
				String eID = resultSet.getString("employeeID");
				String vID = resultSet.getString("venueID");

				blacklist.add(eID);
				blacklist.add(vID);
				blacklists.add(blacklist);

				blacklist.clear();
			}
		} catch (Exception e) {
			throw e;
		} finally {
			/* Close connection to the database */
			close();
		}
		return blacklists;
	}

	/* Connects to the database and adds a new blacklist into the database */
	public static void addBlacklisted(String eID, String vID) throws Exception {
		try {
			/* Open connection to the database */
			connect();

			/* Executes query */
			preparedStatement = connection.prepareStatement("call AddBlacklisted(?, ?)");
			preparedStatement.setString(1, eID);
			preparedStatement.setString(2, vID);

			preparedStatement.executeUpdate();
		} catch (Exception e) {
			throw e;
		} finally {
			/* Close connection to the database */
			close();
		}
	}

	/* Connects to the database and searches for a blacklist by the employee */
	public static ArrayList<String> searchBlacklistedEmployee(String eID) throws Exception {
		ArrayList<String> blacklistedVenues = new ArrayList<String>();
		try {
			/* Open connection to the database */
			connect();

			/* Executes query and saves result into result set */
			preparedStatement = connection.prepareStatement("call SearchBlacklistedEmployee(?)");
			preparedStatement.setString(1, eID);
			resultSet = preparedStatement.executeQuery();

			/*
			 * While there is an row in result set, add the venueID to the array
			 * list
			 */
			while (resultSet.next()) {
				String vID = resultSet.getString("venueID");

				blacklistedVenues.add(vID);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			/* Close connection to the database */
			close();
		}
		return blacklistedVenues;
	}

	/* Connects to the database and searches for a blacklist by the venue */
	public static ArrayList<String> searchBlacklistedVenue(String vID) throws Exception {
		ArrayList<String> blacklistedEmployees = new ArrayList<String>();
		try {
			/* Open connection to the database */
			connect();

			/* Executes query and saves result into result set */
			preparedStatement = connection.prepareStatement("call SearchBlacklistedVenue(?)");
			preparedStatement.setString(1, vID);
			resultSet = preparedStatement.executeQuery();

			/*
			 * While there is an row in result set, add the employeeID to the
			 * array list
			 */
			while (resultSet.next()) {
				String eID = resultSet.getString("employeeID");

				blacklistedEmployees.add(eID);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			/* Close connection to the database */
			close();
		}
		return blacklistedEmployees;
	}

	/* Connects to the database and removes a blacklist by its ID */
	public static void removeBlacklisted(String eID, String vID) throws Exception {
		try {
			/* Open connection to the database */
			connect();

			/* Executes query */
			preparedStatement = connection.prepareStatement("call RemoveBlacklisted(?, ?)");
			preparedStatement.setString(1, eID);
			preparedStatement.setString(2, vID);

			preparedStatement.executeUpdate();
		} catch (Exception e) {
			throw e;
		} finally {
			/* Close connection to the database */
			close();
		}
	}

	// Connection functions
	/* Opens a connection to the database */
	private static void connect() throws Exception {
		try {
			// load MySql driver
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(
					"jdbc:mysql://" + domain + ":" + port + "/" + database_name + "?useSSL=false", sql_username,
					sql_passwd);
			System.out.println("Connected to database");
		} catch (Exception e) {
			throw e;
		}
	}

	/* Closes the connection to the database */
	private static void close() {
		try {
			if (resultSet != null)
				resultSet.close();
			if (statement != null)
				statement.close();
			if (connection != null)
				connection.close();
		} catch (Exception e) {
		}
	}
}
