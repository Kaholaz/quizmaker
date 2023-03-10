package org.ntnu.k2.g2.quizmaker.googlesheets;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.Permission;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.*;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Contains methods used to create and modify Google Sheets.
 */
public class ResultSheet {
    private final String APPLICATION_NAME = "Quiz Maker";

    public ResultSheet() {
    }

    /**
     * Creates an authorized Drive API service.
     *
     * @return Sheets API service.
     *
     * @throws IOException
     *             if NetHttpTransport could not be created
     */
    public Sheets createSheetsService() throws IOException {
        NetHttpTransport HTTP_TRANSPORT = null;

        try {
            HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        } catch (GeneralSecurityException e) { // Something is wrong with the tokes
            System.out.println("Error when creating the Google Sheet service");
            e.printStackTrace();
        }
        JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();

        return new Sheets.Builder(HTTP_TRANSPORT, jsonFactory, GoogleAuthenticator.getCredentials(HTTP_TRANSPORT))
                .setApplicationName(APPLICATION_NAME).build();
    }

    /**
     * Creates an authorized Drive API service.
     *
     * @return Drive API service.
     *
     * @throws IOException
     *             if NetHttpTransport could not be created
     */
    public Drive createDriveService() throws IOException {
        NetHttpTransport HTTP_TRANSPORT = null;
        try {
            HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        } catch (GeneralSecurityException e) {// Something is wrong with the tokes
            System.out.println("Error when creating the Google Drive service");
            e.printStackTrace();
        }
        JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();

        return new Drive.Builder(HTTP_TRANSPORT, jsonFactory, GoogleAuthenticator.getCredentials(HTTP_TRANSPORT))
                .setApplicationName(APPLICATION_NAME).build();
    }

    /**
     * Updates the spreadsheet to be public for everyone with a URL to the spreadsheet.
     *
     * @param driveService
     *            Drive API service.
     * @param sheetId
     *            id of the spreadsheet.
     *
     * @return created permission.
     *
     * @throws IOException
     *             Throws an exception if the sheet could not be made public.
     */
    public Permission makeSpreadsheetPublic(Drive driveService, String sheetId) throws IOException {
        Permission permission = new Permission();

        permission.setRole("writer");
        permission.setType("anyone");

        try {
            return driveService.permissions().create(sheetId, permission).execute();
        } catch (IOException e) {
            throw new IOException("Kunne ikke gj??re regnearket offentlig: " + e);
        }
    }

    /**
     * Deletes a spreadsheet.
     *
     * @param driveService
     *            Drive API service.
     * @param sheetId
     *            id of the spreadsheet.
     */
    public void deleteSheet(Drive driveService, String sheetId) {

        try {
            driveService.files().delete(sheetId).execute();
        } catch (IOException e) {
            System.out.println("Error when trying to delete spreadsheet " + sheetId);
            System.out.println("Stacktrace: " + e);
        }
    }

    /**
     * Creates an empty spreadsheet. Should be called using QuizResultManager and createResultSheet() method.
     *
     * @param sheetTitle
     *            Title of the spreadsheet
     *
     * @return Spreadsheet-id (used in sheet URL:https://docs.google.com/spreadsheets/d/SHEET_ID)
     *
     * @throws IOException
     *             if spreadsheet could not be created.
     */
    public String createSheet(String sheetTitle) throws IOException {
        Spreadsheet spreadsheet = new Spreadsheet().setProperties(new SpreadsheetProperties().setTitle(sheetTitle));
        Sheets sheetsService = createSheetsService();
        Sheets.Spreadsheets.Create request = sheetsService.spreadsheets().create(spreadsheet);

        Spreadsheet response = request.execute();

        return response.getSpreadsheetId();
    }

    /**
     * Gets the title of the spreadsheet.
     *
     * @param sheetId
     *            id of the spreadsheet.
     *
     * @return title of the spreadsheet.
     *
     * @throws IOException
     *             if request could not be executed.
     */
    public String getSheetTitle(String sheetId) throws IOException {
        Sheets sheetService = createSheetsService();

        Sheets.Spreadsheets.Get getRequest = sheetService.spreadsheets().get(sheetId).setIncludeGridData(false);
        Spreadsheet response = getRequest.execute();

        return response.getProperties().getTitle();
    }

    /**
     * Changes the title of the spreadsheet.
     *
     * @param sheetTitle
     *            tile of the spreadsheet.
     * @param sheetId
     *            id of the spreadsheet.
     *
     * @throws IOException
     *             if operation was unsuccessful.
     */

    public boolean setSheetTitle(String sheetTitle, String sheetId) throws IOException {
        Sheets sheetsService = createSheetsService();

        List<Request> request = new ArrayList<>();

        request.add(new Request().setUpdateSpreadsheetProperties(new UpdateSpreadsheetPropertiesRequest()
                .setProperties(new SpreadsheetProperties().setTitle(sheetTitle)).setFields("title")));

        BatchUpdateSpreadsheetRequest body = new BatchUpdateSpreadsheetRequest().setRequests(request);

        Sheets.Spreadsheets.BatchUpdate batchUpdate = sheetsService.spreadsheets().batchUpdate(sheetId, body);
        batchUpdate.execute();

        return true;
    }

    /**
     * Appends values to spreadsheet in column A and B
     *
     * @param sheetId
     *            id of the spreadsheet.
     * @param valueA
     *            cell value for column A.
     * @param valueB
     *            cell value for column B.
     *
     * @throws IOException
     *             if request could not be executed.
     */
    public void appendRowValues(String sheetId, String valueA, String valueB) throws IOException {
        Sheets sheetsService = createSheetsService();

        ValueRange body = new ValueRange().setValues(List.of(Arrays.asList(valueA, valueB)));

        Sheets.Spreadsheets.Values.Append request = sheetsService.spreadsheets().values().append(sheetId, "A1", body)
                .setValueInputOption("USER_ENTERED").setInsertDataOption("INSERT_ROWS")
                .setIncludeValuesInResponse(true);
        request.execute();
    }

    /**
     * Sets values row of cells in the A and B column of the spreadsheet. Will overwrite existing values. Use method
     * appendRowValues() instead to put values at the end of the spreadsheet.
     *
     * @param sheetId
     *            id of the spreadsheet.
     * @param valueA
     *            value of cell in column A.
     * @param valueB
     *            value of cell in column B.
     *
     * @throws IOException
     *             if request could not be executed.
     */
    public void addRowValues(String sheetId, String valueA, String valueB, String rowNumber) throws IOException {
        Sheets sheetsService = createSheetsService();
        String range = "A" + rowNumber;

        ValueRange body = new ValueRange().setValues(List.of(Arrays.asList(valueA, valueB)));
        Sheets.Spreadsheets.Values.Update request = sheetsService.spreadsheets().values().update(sheetId, range, body)
                .setValueInputOption("RAW");
        request.execute();
    }

    /**
     * Counts number of filled rows in spreadsheet. Includes first row.
     *
     * @param sheetId
     *            spreadsheet id
     *
     * @return number of filled rows
     *
     * @throws IOException
     *             if request could not be executed.
     */
    public int countRows(String sheetId) throws IOException {
        Sheets SheetService = createSheetsService();
        final String range = "A1:B";

        Sheets.Spreadsheets.Values.Get request = SheetService.spreadsheets().values().get(sheetId, range);
        ValueRange response = request.execute();

        List<List<Object>> results = response.getValues();

        if (results == null) {
            return 0;
        } else {
            return results.size();
        }
    }

    /**
     * Fetches the values of column A and B, but does not include row 1.
     *
     * @param sheetId
     *            id of the spreadsheet
     *
     * @return list containing spreadsheet values
     *
     * @throws IOException
     *             if request could not be executed.
     */
    public List<List<Object>> fetchResultSheetValues(String sheetId) throws IOException {
        Sheets SheetService = createSheetsService();
        final String range = "A2:B";

        Sheets.Spreadsheets.Values.Get request = SheetService.spreadsheets().values().get(sheetId, range);

        ValueRange response = request.execute();

        return response.getValues();
    }

    /**
     * Removes all values from the spreadsheet from column A to F
     *
     * @param sheetId
     *            id of the spreadsheet
     *
     * @throws IOException
     *             if request could not be executed.
     */
    public void clearSheetValues(String sheetId) throws IOException {
        Sheets sheetsService = createSheetsService();

        // Alternative range: <Worksheet name>
        String range = "A:F";
        ClearValuesRequest requestBody = new ClearValuesRequest();
        Sheets.Spreadsheets.Values.Clear request = sheetsService.spreadsheets().values().clear(sheetId, range,
                requestBody);

        request.execute();
    }

}
