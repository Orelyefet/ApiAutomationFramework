package api.utilities;

import org.testng.annotations.DataProvider;

import java.io.IOException;

public class DataProviders {

    @DataProvider(name = "AllData")
    public String[][] getAllData() throws IOException {
        String path = System.getProperty("user.dir") + "//testData//UserData.xlsx";
        ExcelUtility excelUtility = new ExcelUtility(path);

        int rowNumber = excelUtility.getRowCount("Sheet1");
        int columnCount = excelUtility.getCellCount("Sheet1", 1);

        String[][] apiData = new String[rowNumber][columnCount];

        for (int i = 1; i <= rowNumber; i++) {
            for (int j = 0; j < columnCount; j++) {
                apiData[i - 1][j] = excelUtility.getCellData("Sheet1", i, j);
            }
        }
        return apiData;
    }

    @DataProvider(name = "UserNames")
    public String[] getUserNames() throws IOException {
        String path = System.getProperty("user.dir") + "//testData//UserData.xlsx";
        ExcelUtility excelUtility = new ExcelUtility(path);

        int rowNumber = excelUtility.getRowCount("Sheet1");

        String[] apiData = new String[rowNumber];

        for (int i = 1; i <= rowNumber; i++) {
            apiData[i - 1] = excelUtility.getCellData("Sheet1", i, 1);

        }
        return apiData;
    }
}
