package api.utilities;

import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.*;

import java.io.*;

public class ExcelUtility {
    public FileInputStream fileInputStream;
    public FileOutputStream fileOutputStream;
    public XSSFWorkbook xssfWorkbook;
    public XSSFSheet xssfSheet;
    public XSSFRow xssfRow;
    public XSSFCell xssfCell;
    String path;

    public ExcelUtility(String path) {
        this.path = path;
    }

    public int getRowCount(String sheetName) throws IOException {
        fileInputStream = new FileInputStream(path);
        xssfWorkbook = new XSSFWorkbook(fileInputStream);
        xssfSheet = xssfWorkbook.getSheet(sheetName);
        int rowCount = xssfSheet.getLastRowNum();
        xssfWorkbook.close();
        fileInputStream.close();
        return rowCount;
    }

    public int getCellCount(String sheetName, int rowNumber) throws IOException {
        fileInputStream = new FileInputStream(path);
        xssfWorkbook = new XSSFWorkbook(fileInputStream);
        xssfSheet = xssfWorkbook.getSheet(sheetName);
        xssfRow = xssfSheet.getRow(rowNumber);
        int cellCount = xssfRow.getLastCellNum();
        xssfWorkbook.close();
        fileInputStream.close();
        return cellCount;
    }

    public String getCellData(String sheetName, int rowNumber, int column) throws IOException {
        fileInputStream = new FileInputStream(path);
        xssfWorkbook = new XSSFWorkbook(fileInputStream);
        xssfSheet = xssfWorkbook.getSheet(sheetName);
        xssfRow = xssfSheet.getRow(rowNumber);
        xssfCell = xssfRow.getCell(column);

        DataFormatter dataFormatter = new DataFormatter();
        String data;

        try {
            data = dataFormatter.formatCellValue(xssfCell);
        } catch (Exception e) {
            data = "";
        }
        xssfWorkbook.close();
        fileInputStream.close();
        return data;
    }

    public void setCellData(String sheetName, int rowNumber, int column, String data) throws IOException {
        File file = new File(path);
        if(!file.exists()) {
            xssfWorkbook = new XSSFWorkbook();
            fileOutputStream = new FileOutputStream(path);
            xssfWorkbook.write(fileOutputStream);
        }

        fileInputStream = new FileInputStream(path);
        xssfWorkbook = new XSSFWorkbook(fileInputStream);

        if(xssfWorkbook.getSheetIndex(sheetName) == -1) {
            xssfWorkbook.createSheet(sheetName);
        }
        xssfSheet = xssfWorkbook.getSheet(sheetName);

        xssfCell = xssfRow.createCell(column);
        xssfCell.setCellValue(data);
        fileOutputStream = new FileOutputStream(path);
        xssfWorkbook.write(fileOutputStream);
        xssfWorkbook.close();
        fileInputStream.close();
        fileOutputStream.close();
    }

    public void fillGreenColor(String sheetName, int rowNumber, int column) throws IOException {
        fileInputStream = new FileInputStream(path);
        xssfWorkbook = new XSSFWorkbook(fileInputStream);
        xssfSheet = xssfWorkbook.getSheet(sheetName);

        xssfRow = xssfSheet.getRow(rowNumber);
        xssfCell = xssfRow.getCell(column);

        XSSFCellStyle style = xssfWorkbook.createCellStyle();
        style.setFillForegroundColor(IndexedColors.GREEN.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        xssfCell.setCellStyle(style);
        xssfWorkbook.write(fileOutputStream);
        xssfWorkbook.close();
        fileInputStream.close();
        fileOutputStream.close();
    }
}
