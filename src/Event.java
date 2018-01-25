public class Event {
	/**Each Event contains a venue, the number of tables at the venue, 
	* and a single employee.
	*/
	private Venue venue;
	private Employee employee; // assuming a small number (<10)
	private int tables;
	private boolean found;

	public Event(Venue v, Employee e) {
		
		/** constructor
		*/ 
		venue = v;
		employee = e;
	}


	public int getTables() {
		/** Gets the number of tables.
		*/ 
		return tables;
	}

	public void setTables(int tables) {
		/** Sets the number of tables.
		*/ 
		this.tables = tables;
	}

	public Venue getVenue() {
		/** Gets the venue.
		*/ 
		return venue;
	}




	public Employee getEmployee() {
		/** Gets the current employee.
		*/
		return employee;
	}


	
}
