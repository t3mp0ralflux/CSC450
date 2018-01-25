
/**
 * Object containing an Employee's contact information.
 */
public class Employee {
	private String id;
	private String firstName;
	private String lastName;
	private String password;
	private String phone;
	private String email;
	private boolean manager;

	/**
	 * 
	 * @param i
	 *            Use ID
	 * @param f
	 *            First name
	 * @param l
	 *            Last name
	 * @param p
	 *            Phone Number
	 * @param ip
	 *            Password
	 * @param e
	 *            Email
	 */
	public Employee(String i, String f, String l, String ip, String p, String e) {
		/**
		 * constructor
		 */
		id = i;
		firstName = f;
		lastName = l;
		password = ip;
		phone = p;
		manager = false;
		email = e;
	}

	public String getEmail() {
		/**
		 * Returns employee's email.
		 */
		return email;
	}

	public void setEmail(String email) {

		/**
		 * Sets employee's email.
		 */
		this.email = email;
	}

	public String getFirstName() {

		/**
		 * Returns employee's first name.
		 */
		return firstName;
	}

	public void setFirstName(String firstName) {

		/**
		 * Sets employee's email.
		 */
		this.firstName = firstName;
	}

	public String getId() {

		/**
		 * Returns employee's ID number.
		 */
		return id;
	}

	public void setId(String id) {
		/**
		 * Sets employee's ID number.
		 */
		this.id = id;
	}

	public String getLastName() {
		/**
		 * Returns employee's last name.
		 */
		return lastName;
	}

	public void setLastName(String lastName) {

		/**
		 * Sets employee's last name.
		 */
		this.lastName = lastName;
	}

	public String getFullName() {

		/**
		 * Returns employee's first name concatenated with their last name.
		 */
		return this.firstName + " " + this.lastName;
	}

	public String getPhone() {

		/**
		 * Returns employee's phone number.
		 */
		return phone;
	}

	public void setPhone(String phone) {

		/**
		 * Sets employee's phone number.
		 */
		this.phone = phone;
	}

	public boolean isManager() {

		/**
		 * Returns boolean value telling if this employee is a manager.
		 */
		return manager;
	}

	public void setManager(boolean manager) {

		/**
		 * Sets boolean value telling if this employee is a manager.
		 */
		this.manager = manager;
	}

	public String getPassword() {

		/**
		 * Returns employee's password.
		 */
		return password;
	}

	public void setPassword(String pw) {
		/**
		 * Sets employee's password.
		 */
		this.password = pw;
	}

	public String toString() {

		/**
		 * Returns employee's first name concatenated with their last name.
		 */
		return getFullName();
	}

	public boolean equals(Employee employee) {
		/**
		 * Determines if two employee objects are equal.
		 */
		return this.getId().equals(employee.getId());

	}
}
