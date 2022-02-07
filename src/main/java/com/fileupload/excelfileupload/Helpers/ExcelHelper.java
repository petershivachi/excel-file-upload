package com.fileupload.excelfileupload.Helpers;

import com.fileupload.excelfileupload.Models.Tutorial.Tutorial;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ExcelHelper {
    public static String TYPE =  "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    public String[] HEADERS = {"id", "title", "description", "published"};
    static  String SHEET = "Tutorials";

    public static boolean hasExcelFormat(MultipartFile file){
        return TYPE.equals((file.getContentType()));
    };

    public static List<Tutorial> excelToTutorial(InputStream is){
        try {
            Workbook workbook = new XSSFWorkbook(is);
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rows = sheet.iterator();
            List<Tutorial> tutorials = new ArrayList<Tutorial>();
            int rowNumber = 0;
            while(rows.hasNext()){
                Row currentRow = rows.next();
                //Skip header
                if(rowNumber == 0){
                    rowNumber++;
                    continue;
                }
                Iterator<Cell> cellsInRow = currentRow.iterator();
                Tutorial tutorial = new Tutorial();

                int cellIdx = 0;

                while(cellsInRow.hasNext()){
                    Cell currentCell = cellsInRow.next();
                    switch (cellIdx){
                        case 0:
                            tutorial.setId((long) currentCell.getNumericCellValue());
                            break;
                        case 1:
                            tutorial.setTitle(currentCell.getStringCellValue());
                            break;
                        case 2:
                            tutorial.setDescription(currentCell.getStringCellValue());
                            break;
                        case 3:
                            tutorial.setPublished(currentCell.getBooleanCellValue());
                            break;
                        default:
                            break;
                    }
                    cellIdx++;
                }
                tutorials.add(tutorial);
            }

            workbook.close();

            return  tutorials;

        }catch (IOException e){
            throw new RuntimeException("Fail to parse Excel file: "+e.getMessage());
        }
    }
}
