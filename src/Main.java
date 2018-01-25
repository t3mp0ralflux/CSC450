/**
 * The main logic of the scheduling program. Calls Viewer and sets it visible.
 * Other functions are attached to Menu Items in Viewer.
 *
 */
public class Main {

	public Main() {
	}

	public static void main(String[] args) {
		Viewer viewer = new Viewer();

		viewer.setVisible(true);

	}
}
