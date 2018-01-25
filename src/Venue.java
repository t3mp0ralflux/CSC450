/**
 * Object containing a Venue's contact information.
 */
public class Venue {
	private String ID;
	private String name;
	private int tables;
	private String address;

	public Venue(String id, String n, int t, String a) {
		/**
		 * constructor
		 */
		ID = id;
		name = n;
		tables = t;
		address = a;
	}

	public String getID() {
		/**
		 * Returns venue ID
		 */
		return ID;
	}

	public void setID(String iD) {
		/**
		 * Sets new venue ID in the database.
		 */
		try {
			Database.updateVenue("venueID", iD, this.ID);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.ID = iD;
	}

	public String getName() {

		/**
		 * Returns venue name
		 */
		return name;
	}

	public void setName(String name) {
		/**
		 * Sets new venue name in the database.
		 */
		try {
			Database.updateVenue("name", name, this.name);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.name = name;
	}

	public int getTables() {

		/**
		 * Returns venue table number
		 */
		return tables;
	}

	public void setTables(int tables) {
		/**
		 * Sets new venue table amount in the database.
		 */
		try {
			Database.updateVenue("tableNum", String.valueOf(tables), String.valueOf(this.tables));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.tables = tables;
	}

	public String getAddress() {

		/**
		 * Returns venue address
		 */
		return address;
	}

	public void setAddress(String address) {
		/**
		 * Sets new venue Iaddress in the database.
		 */
		try {
			Database.updateVenue("address", address, this.address);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.address = address;
	}
}
