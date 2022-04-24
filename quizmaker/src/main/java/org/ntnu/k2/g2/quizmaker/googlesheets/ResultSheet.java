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

public class ResultSheet {
    private final String APPLICATION_NAME = "Quiz Maker";

    public ResultSheet() {
    }

    public Sheets createSheetsService() throws IOException, GeneralSecurityException {
        NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
        GoogleAuthenticator googleAuthenticator = new GoogleAuthenticator();

        return new Sheets.Builder(HTTP_TRANSPORT, jsonFactory, googleAuthenticator.getCredentials(HTTP_TRANSPORT))
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

    public Drive createDriveService() throws IOException, GeneralSecurityException {
        NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
        GoogleAuthenticator googleAuthenticator = new GoogleAuthenticator();

        return new Drive.Builder(HTTP_TRANSPORT, jsonFactory, googleAuthenticator.getCredentials(HTTP_TRANSPORT))
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

    /**
     * Updates the spreadsheet to be public for everyone with a URL to the spreadsheet
     * @param driveService drive service instance
     * @param sheetId id of the spreadsheet
     * @return created permission
     */
    public Permission makeSpreadsheetPublic(Drive driveService, String sheetId){
        Permission permission = new Permission();

        permission.setRole("writer");
        permission.setType("anyone");

        try{
            return driveService.permissions().create(sheetId, permission).execute();
        }
        catch (IOException e){
            System.out.println("Could not update spreadsheet permission");
            System.out.println("Stacktrace:" + e);
        }
        return null;
    }


    /**
     * Deletes a spreadsheet
     * @param driveService drive service
     * @param sheetId id of the spreadsheet
     */
    static void deleteSheet(Drive driveService, String sheetId) {

        try {
            driveService.files().delete(sheetId).execute();
        } catch (IOException e) {
            System.out.println("Error when trying to delete spreadsheet " + sheetId);
            System.out.println("Stacktrace: " + e);
        }
    }


    /**
     * Creates an empty spreadsheet. Should be called using QuizResultManager and
     * createResultSheet()  method.
     * @param sheetTitle Title of the spreadsheet
     * @return Spreadsheet-id (used in sheet URL:https://docs.google.com/spreadsheets/d/SHEET_ID)
     * @throws IOException
     * @throws GeneralSecurityException
     */
    public String createSheet(String sheetTitle) throws IOException, GeneralSecurityException {
        Spreadsheet spreadsheet = new Spreadsheet().setProperties(new SpreadsheetProperties().setTitle(sheetTitle));
        Sheets sheetsService = createSheetsService();
        Sheets.Spreadsheets.Create request = sheetsService.spreadsheets().create(spreadsheet);

        Spreadsheet response = request.execute();

        return response.getSpreadsheetId();
    }

    /**
     * Gets the title of the spreadsheet
     * @param sheetId id of the spreadsheet
     * @return title of the spreadsheet
     * @throws GeneralSecurityException
     * @throws IOException
     */
    public String getSheetTitle(String sheetId) throws GeneralSecurityException, IOException {
        Sheets sheetService = createSheetsService();

        Sheets.Spreadsheets.Get getRequest = sheetService.spreadsheets().get(sheetId).setIncludeGridData(false);
        Spreadsheet response = getRequest.execute();

        String title = response.getProperties().getTitle();

        return title;
    }

    /**
     * Changes the title of the spreadsheet
     * @param sheetTitle tile of the spreadsheet
     * @param sheetId id of the spreadsheet
     * @throws GeneralSecurityException
     * @throws IOException
     * @return
     */

    public boolean setSheetTitle(String sheetTitle, String sheetId) throws GeneralSecurityException, IOException {
        Sheets sheetsService = createSheetsService();

        List<Request> request = new ArrayList<>();

        request.add(new Request().setUpdateSpreadsheetProperties(new UpdateSpreadsheetPropertiesRequest()
                .setProperties(new SpreadsheetProperties().setTitle(sheetTitle)).setFields("title")));

        BatchUpdateSpreadsheetRequest body = new BatchUpdateSpreadsheetRequest().setRequests(request);

        Sheets.Spreadsheets.BatchUpdate batchUpdate = sheetsService.spreadsheets().batchUpdate(sheetId,body);

        BatchUpdateSpreadsheetResponse response = batchUpdate.execute();

        return true;
    }

    /**
     * Appends values to spreadsheet in column A and B
     * @param sheetId id of the spreadsheet
     * @param valueA cell value for column A
     * @param valueB cell value for column B
     * @throws GeneralSecurityException
     * @throws IOException
     */
    public void appendRowValues(String sheetId, String valueA, String valueB) throws GeneralSecurityException, IOException {
        Sheets sheetsService = createSheetsService();

        ValueRange body = new ValueRange().setValues(List.of(Arrays.asList(valueA, valueB)));

        Sheets.Spreadsheets.Values.Append request =sheetsService.spreadsheets().values()
                .append(sheetId, "A1", body)
                .setValueInputOption("USER_ENTERED")
                .setInsertDataOption("INSERT_ROWS")
                .setIncludeValuesInResponse(true);

        AppendValuesResponse response = request.execute();
    }

    /**
     * Sets values row of cells in the A and B column of the spreadsheet. Will overwrite existing values.
     * Use method appendRowValues() instead to put values at the end of the spreadsheet.
     * @param sheetId id of the spreadsheet
     * @param valueA value of cell in column A
     * @param valueB value of cell in column B
     */
    public void addRowValues(String sheetId, String valueA, String valueB, String rowNumber) throws GeneralSecurityException, IOException {
        Sheets sheetsService = createSheetsService();
        String range = "A" + rowNumber;

        ValueRange body = new ValueRange().setValues(List.of(Arrays.asList(valueA, valueB)));
        Sheets.Spreadsheets.Values.Update request = sheetsService.spreadsheets().values()
                .update(sheetId,range,body).setValueInputOption("RAW");

        UpdateValuesResponse response = request.execute();
    }

    /**
     * Counts number of filled rows in spreadsheet. Includes first row.
     * @param sheetId spreadsheet id
     * @return number of filled rows
     * @throws IOException
     * @throws GeneralSecurityException
     */
    public int countRows(String sheetId) throws IOException, GeneralSecurityException {
        Sheets SheetService = createSheetsService();
        final String range = "A1:B";

        Sheets.Spreadsheets.Values.Get request = SheetService.spreadsheets().values().get(sheetId,range);
        ValueRange response = request.execute();

        List<List<Object>> results = response.getValues();

        if(results == null){
            return 0;
        }
        else{
            return results.size();
        }
    }

    /**
     * Fetches the values of column A and B, but does not include row 1.
     * @param sheetId id of the spreadsheet
     * @return list containing spreadsheet values
     * @throws IOException
     * @throws GeneralSecurityException
     */
    public List<List<Object>> fetchResultSheetValues(String sheetId) throws IOException, GeneralSecurityException {
        Sheets SheetService = createSheetsService();
        final String range = "A2:B";

        Sheets.Spreadsheets.Values.Get request = SheetService.spreadsheets().values()
                .get(sheetId, range);

        ValueRange response = request.execute();

        List<List<Object>> results = response.getValues();
        return results;
    }
    /**
     * Removes all values from the spreadsheet from column A to F
     * @param sheetId id of the spreadsheet
     * @throws GeneralSecurityException
     * @throws IOException
     */
    public void clearSheetValues(String sheetId) throws GeneralSecurityException, IOException {
        Sheets sheetsService = createSheetsService();

        // Alternative range: <Worksheet name>
        String range = "A:F";
        ClearValuesRequest requestBody = new ClearValuesRequest();
        Sheets.Spreadsheets.Values.Clear request = sheetsService.spreadsheets().values().clear(sheetId, range, requestBody);

        ClearValuesResponse response = request.execute();
    }


}

