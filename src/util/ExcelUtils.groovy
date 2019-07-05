package util

import org.apache.poi.ss.usermodel.CellStyle
import org.apache.poi.ss.usermodel.Row
import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.ss.usermodel.Workbook
import org.apache.poi.xssf.usermodel.XSSFWorkbook

class ExcelUtils{

    static def createExcel(def fileName, def diffMap){
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("0");
        Row row = sheet.createRow(0);
        CellStyle cellStyle = workbook.createCellStyle();
//        // set style
//        cellStyle.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
//        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
//        cellStyle.setBorderBottom(CellStyle.BORDER_THIN);
//        cellStyle.setBorderLeft(CellStyle.BORDER_THIN);
//        cellStyle.setBorderRight(CellStyle.BORDER_THIN);
//        cellStyle.setBorderTop(CellStyle.BORDER_THIN);
//        cellStyle.setAlignment(CellStyle.ALIGN_CENTER);

        // line1 default
        row.createCell(0).setCellStyle(cellStyle);
        row.createCell(0).setCellValue("fileName");

        row.createCell(1).setCellStyle(cellStyle);
        row.createCell(1).setCellValue("Expect");

        row.createCell(2).setCellStyle(cellStyle);
        row.createCell(2).setCellValue("Actual");


        // different line
        diffMap.eachWithIndex { Map.Entry<Object, Object> entry, int i ->

            Row newRow = sheet.createRow(i+1);
            newRow.createCell(0).setCellValue(fileName)
            newRow.createCell(1).setCellValue(entry.key)
            newRow.createCell(2).setCellValue(entry.value)
        }


        workbook.setSheetName(0, "default");
        try {
            // result Excel path
            File file = new File("d://Common/result.xlsx");
            FileOutputStream fileoutputStream = new FileOutputStream(file);
            workbook.write(fileoutputStream);
            fileoutputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}

