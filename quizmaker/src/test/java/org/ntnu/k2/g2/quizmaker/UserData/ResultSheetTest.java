package org.ntnu.k2.g2.quizmaker.UserData;

import junit.framework.TestCase;
import org.ntnu.k2.g2.quizmaker.data.QuizModel;
import org.ntnu.k2.g2.quizmaker.data.QuizRegister;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

public class ResultSheetTest extends TestCase {

    //Public sheets to be used in tests
    /**
     * Spreadsheet with read/write rights
     * https://docs.google.com/spreadsheets/d/11MG2HsYNGL5VFpdaRkGlvE8EqtqlYAI13IBF9fw2K8o
     */
    String publicSpreadsheet1 = "11MG2HsYNGL5VFpdaRkGlvE8EqtqlYAI13IBF9fw2K8o";

    /**
     * Spreadsheet containing 4 Teams with points.
     * READ ONLY
     * https://docs.google.com/spreadsheets/d/1WNHT9u2QELw9Z8CE8YO7CctHUt2XpaTl2DeHBxk3a7k
     */
    String publicSpreadsheet2 = "1WNHT9u2QELw9Z8CE8YO7CctHUt2XpaTl2DeHBxk3a7k";

    public void testCreateSheet() throws IOException, GeneralSecurityException {
        ResultSheet resultsheet = new ResultSheet();
        String sheetID = resultsheet.createSheet("Test-sheet");
        assertNotNull(sheetID);
    }

    public void testAppendRowValues() throws GeneralSecurityException, IOException {
        ResultSheet resultsheet = new ResultSheet();
        String sheetID = publicSpreadsheet1;

        resultsheet.appendRowValues(sheetID,"Team XX", "9");
    }

    public void testAddRowValuesOverwritesOldValue() throws GeneralSecurityException, IOException {
        ResultSheet resultsheet = new ResultSheet();
        String sheetID = publicSpreadsheet1;

        resultsheet.addRowValues(sheetID,"Team", "Points","1");
        resultsheet.addRowValues(sheetID,"Team DD", "2","2");
        resultsheet.addRowValues(sheetID,"Team LL", "3","3");
        resultsheet.addRowValues(sheetID,"Team YD", "4","2");

        List<List<Object>> results = resultsheet.fetchResultSheetValues(sheetID);

        //Checks row 2
        List<Object> row = results.get(0); // row 2
        String teamName = row.get(0).toString(); //column 1
        int points = Integer.parseInt((String) row.get(1)); //column 2

        assertEquals(4, points);
        assertEquals("Team YD", teamName);

    }

    public void testCountRows() throws GeneralSecurityException, IOException {
        ResultSheet resultsheet = new ResultSheet();
        String sheetID = publicSpreadsheet2;
        assertEquals(5,resultsheet.countRows(sheetID));
    }

    public void testFetchResultSheetValuesRow() throws GeneralSecurityException, IOException {
        ResultSheet resultsheet = new ResultSheet();
        String sheetID = publicSpreadsheet2;

        List<List<Object>> results = resultsheet.fetchResultSheetValues(sheetID);
        List<Object> row = results.get(0); //row 2 on the spreadsheet

        String teamName = row.get(0).toString();
        int points = Integer.parseInt((String) row.get(1));

        assertEquals(1, points);
        assertEquals("Team1",teamName);
}
    public void testClearSheet() throws GeneralSecurityException, IOException {
        ResultSheet resultSheet = new ResultSheet();
        String sheetId = publicSpreadsheet1;

        resultSheet.clearSheetValues(sheetId);

        resultSheet.appendRowValues(sheetId,"Hello","3");
        resultSheet.appendRowValues(sheetId,"Hello","7");
        resultSheet.appendRowValues(sheetId,"Hello","2");
        resultSheet.appendRowValues(sheetId,"Hello","3");

        assertEquals(4,resultSheet.countRows(sheetId));

        resultSheet.clearSheetValues(sheetId);

        assertEquals(0,resultSheet.countRows(sheetId));
    }

}