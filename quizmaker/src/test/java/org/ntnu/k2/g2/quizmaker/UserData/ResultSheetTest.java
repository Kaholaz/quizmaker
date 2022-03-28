package org.ntnu.k2.g2.quizmaker.UserData;

import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.testing.http.HttpTesting;
import com.google.api.client.testing.http.MockHttpTransport;
import junit.framework.TestCase;
import org.ntnu.k2.g2.quizmaker.Data.QuizRegister;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

public class ResultSheetTest extends TestCase {

    //Not in use atm
    public void createMockSheetService() throws IOException {
        HttpTransport transport = new MockHttpTransport();
        HttpRequest request = transport.createRequestFactory().buildGetRequest(HttpTesting.SIMPLE_GENERIC_URL);
        HttpResponse response = ((HttpRequest) request).execute();
    }

    public void testCreateSheet() throws IOException, GeneralSecurityException {
        ResultSheet resultsheet = new ResultSheet();
        String sheetID = resultsheet.createSheet("Test-sheet");
        assertNotNull(sheetID);
    }

    public void testCreateSheetWithDatabase() throws IOException, GeneralSecurityException {
        QuizRegister quizRegister = new QuizRegister();
        QuizResultManager quizResultManager = new QuizResultManager();
        quizResultManager.createResultSheet(quizRegister.newQuiz().getId(),"TestCreateSheetWithDatabase");
    }

    public void testAppendRowValues() throws GeneralSecurityException, IOException {
        ResultSheet resultsheet = new ResultSheet();
        String sheetID ="11MG2HsYNGL5VFpdaRkGlvE8EqtqlYAI13IBF9fw2K8o";

        resultsheet.appendRowValues(sheetID,"Team XX", "9");

    }

    public void testAddRowValues() throws GeneralSecurityException, IOException {
        ResultSheet resultsheet = new ResultSheet();
        String sheetID ="11MG2HsYNGL5VFpdaRkGlvE8EqtqlYAI13IBF9fw2K8o";

        resultsheet.addRowValues(sheetID,"Team YY", "98","1");
        resultsheet.addRowValues(sheetID,"Team DD", "8","2");
        resultsheet.addRowValues(sheetID,"Team LL", "8","3");
        resultsheet.addRowValues(sheetID,"Team YD", "9","2");
    }

    public void testCountRows() throws GeneralSecurityException, IOException {
        ResultSheet resultsheet = new ResultSheet();
        String sheetID ="1MHQmjOIkBtKALzZ7rVJEZtn7pPIo7nvGevARwIcX4aI";
        assertEquals(5,resultsheet.countRows(sheetID));
    }

    public void testFetchResultSheetValuesRow2() throws GeneralSecurityException, IOException {
        ResultSheet resultsheet = new ResultSheet();
        String sheetID ="11MG2HsYNGL5VFpdaRkGlvE8EqtqlYAI13IBF9fw2K8o";

        List<List<Object>> results = resultsheet.fetchResultSheetValues(sheetID);
        List<Object> row = results.get(0);

        String teamName = row.get(0).toString();
        int points = Integer.parseInt((String) row.get(1));
        assertTrue(points>0);
        assertNotNull(teamName);
}}