import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * Uses Apache POI to take in object array and exports it to an excel file.
 * Blank spots for employee schedules are handled
 * 
 * @author Bobby
 *
 */
public class ExcelExporter {

	public static final String[] scheduleHeaders = { "Employee", "Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat" };

	XSSFWorkbook workbook;
	FileOutputStream out;
	String fileName;

	public ExcelExporter(String fileName) {
		// Add an Excel extension to the file name if it is
		// .. not there already
		if (fileName.endsWith(".xls") || fileName.endsWith(".xlsx"))
			this.fileName = fileName;
		else
			this.fileName = fileName + ".xlsx";

		// Start a new workbook
		this.workbook = new XSSFWorkbook();
	}

	public void addSheet(String sheetName, String[] headers, Object[][] data) {
		// Create a new sheet in the workbook
		XSSFSheet sheet = workbook.createSheet(sheetName);

		// Write the headers
		XSSFRow header = sheet.createRow(0);
		for (int j = 0; j < headers.length; ++j) {
			Cell cell = header.createCell(j);
			cell.setCellValue(headers[j]);
		}

		// Write the data
		for (int i = 0; i < data.length; ++i) {
			XSSFRow row = sheet.createRow(i + 1); // offset by one because of
													// the header
			for (int j = 0; j < data[i].length; ++j) {
				Cell cell = row.createCell(j);
				cell.setCellValue(data[i][j].toString());
			}
		}
	}

	public void finish() {
		// Open a new Excel file
		try {
			this.out = new FileOutputStream(new File(fileName));
		} catch (IOException e) {
			System.out.println("Could not create FileOutputStream: e");
		}

		// Try to write the workbook to the file
		try {
			workbook.write(out);
		} catch (Exception e) {
			System.out.println("Unable to save file: " + e.getMessage());
		}

		// Try to close the file
		try {
			out.close();
		} catch (Exception e) {
		}
	}

}
