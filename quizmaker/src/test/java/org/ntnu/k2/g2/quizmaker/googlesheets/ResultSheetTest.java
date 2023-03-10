package org.ntnu.k2.g2.quizmaker.googlesheets;

import com.google.api.services.drive.Drive;
import junit.framework.TestCase;

import java.io.IOException;
import java.util.List;

public class ResultSheetTest extends TestCase {

    // Public sheets to be used in tests
    /**
     * Spreadsheet with read/write rights
     * https://docs.google.com/spreadsheets/d/1KeuOymxKNfPnre6eEobFb1GEwoCdPIKUHP0K_YUexpg
     */
    String publicSpreadsheet1 = "1KeuOymxKNfPnre6eEobFb1GEwoCdPIKUHP0K_YUexpg";

    /**
     * Spreadsheet containing 4 Teams with points. READ ONLY
     * https://docs.google.com/spreadsheets/d/121tMrONqwBucH8vJ2ERIeKly8TJyUjBaIqJB8q2FoH0
     */
    String publicSpreadsheet2 = "121tMrONqwBucH8vJ2ERIeKly8TJyUjBaIqJB8q2FoH0";

    public void testCreateSheet() throws IOException {
        ResultSheet resultSheet = new ResultSheet();
        String sheetId = resultSheet.createSheet("Test-sheet");
        assertNotNull(sheetId);

        Drive driveService = resultSheet.createDriveService();
        resultSheet.deleteSheet(driveService, sheetId);
    }

    public void testAppendRowValues() throws IOException {
        ResultSheet resultsheet = new ResultSheet();
        String sheetID = publicSpreadsheet1;

        resultsheet.appendRowValues(sheetID, "Team XX", "9");
    }

    public void testAddRowValuesOverwritesOldValue() throws IOException {
        ResultSheet resultsheet = new ResultSheet();
        String sheetID = publicSpreadsheet1;

        resultsheet.addRowValues(sheetID, "Team", "Points", "1");
        resultsheet.addRowValues(sheetID, "Team DD", "2", "2");
        resultsheet.addRowValues(sheetID, "Team LL", "3", "3");
        resultsheet.addRowValues(sheetID, "Team YD", "4", "2");

        List<List<Object>> results = resultsheet.fetchResultSheetValues(sheetID);

        // Checks row 2
        List<Object> row = results.get(0); // row 2
        String teamName = row.get(0).toString(); // column 1
        int points = Integer.parseInt((String) row.get(1)); // column 2

        assertEquals(4, points);
        assertEquals("Team YD", teamName);

    }

    public void testCountRows() throws IOException {
        ResultSheet resultsheet = new ResultSheet();
        String sheetID = publicSpreadsheet2;
        assertEquals(5, resultsheet.countRows(sheetID));
    }

    public void testFetchResultSheetValuesRow() throws IOException {
        ResultSheet resultsheet = new ResultSheet();
        String sheetID = publicSpreadsheet2;

        List<List<Object>> results = resultsheet.fetchResultSheetValues(sheetID);
        List<Object> row = results.get(0); // row 2 on the spreadsheet

        String teamName = row.get(0).toString();
        int points = Integer.parseInt((String) row.get(1));

        assertEquals(1, points);
        assertEquals("Team1", teamName);
    }

    public void testClearSheet() throws IOException {
        ResultSheet resultSheet = new ResultSheet();
        String sheetId = publicSpreadsheet1;

        resultSheet.clearSheetValues(sheetId);

        resultSheet.appendRowValues(sheetId, "Hello", "3");
        resultSheet.appendRowValues(sheetId, "Hello", "7");
        resultSheet.appendRowValues(sheetId, "Hello", "2");
        resultSheet.appendRowValues(sheetId, "Hello", "3");

        assertEquals(4, resultSheet.countRows(sheetId));

        resultSheet.clearSheetValues(sheetId);

        assertEquals(0, resultSheet.countRows(sheetId));
    }

    public void testGetName() throws IOException {
        ResultSheet resultSheet = new ResultSheet();
        String sheetId = resultSheet.createSheet("My Name");

        assertEquals("My Name", resultSheet.getSheetTitle(sheetId));
        Drive driveService = resultSheet.createDriveService();
        resultSheet.deleteSheet(driveService, sheetId);
    }

    public void testSetName() throws IOException {
        ResultSheet resultSheet = new ResultSheet();
        String sheetId = resultSheet.createSheet("Old Name");
        resultSheet.setSheetTitle("New name", sheetId);

        assertEquals("New name", resultSheet.getSheetTitle(sheetId));

        Drive driveService = resultSheet.createDriveService();
        resultSheet.deleteSheet(driveService, sheetId);
    }

}