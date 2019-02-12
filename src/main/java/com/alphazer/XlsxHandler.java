package com.alphazer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.*;

import com.alphazer.util.Set;

public class XlsxHandler {
	
	
	public XlsxDoc readXlsx(String fileLocation) throws EncryptedDocumentException, IOException {
		File file = new File(fileLocation);
		return new XlsxDoc(WorkbookFactory.create(file), file.getAbsolutePath());
	}

	public XlsxDoc createXlsxCopy(XlsxDoc original, String copyFileLocation) throws FileNotFoundException, 
									EncryptedDocumentException, IOException {
		File file = new File(copyFileLocation);
		FileOutputStream fileOut = new FileOutputStream(file);
		
		original.getWorkbook().write(fileOut);
		return readXlsx(copyFileLocation);
	}
	
	public void saveWorkbook(Workbook workbook, String fileLocation) throws IOException {
		File file = new File(fileLocation);
		FileOutputStream fileOut = new FileOutputStream(file);
		
		workbook.write(fileOut);
	}

	public void removeColumns(Workbook workbook, Set<Integer> columns) {		
		for (int i = 0; i < columns.size(); i++)
			deleteColumn(workbook.getSheetAt(0), columns.get(i) - i);
	}
	
	public void removeColumns(XlsxDoc xlsxDoc, Set<Integer> columns) {
		for (int i = 0; i < columns.size(); i++)
			deleteColumn(xlsxDoc.getWorkbook().getSheetAt(0), columns.get(i) - i - 1);
	}
	
	public void deleteColumn(Sheet sheet, int columnToDelete) {
        int maxColumn = 0;
        for (int r = 0; r < sheet.getLastRowNum() + 1; r++){
            Row row = sheet.getRow(r);

            // if no row exists here; then nothing to do; next!
            if (row == null)
                continue;

            // if the row doesn't have this many columns then we are good; next!
            int lastColumn = row.getLastCellNum();
            
            if (lastColumn > maxColumn)
                maxColumn = lastColumn;

            if (lastColumn < columnToDelete)
                continue;

            for (int x = columnToDelete + 1; x < lastColumn + 1; x++){
                Cell oldCell = row.getCell(x-1);
                
                if (oldCell != null)
                    row.removeCell(oldCell);

                Cell nextCell = row.getCell(x);
                
                if (nextCell != null) {
                    Cell newCell = row.createCell(x-1, nextCell.getCellType());
                    cloneCell(newCell, nextCell);
                }
            }
        }

        // Adjust the column widths
        for ( int c=0; c < maxColumn; c++ ){
            sheet.setColumnWidth( c, sheet.getColumnWidth(c+1) );
        }
    }
	
	private void cloneCell(Cell cNew, Cell cOld){
	    cNew.setCellComment(cOld.getCellComment());
	    cNew.setCellStyle(cOld.getCellStyle());

	    switch (cNew.getCellType()) {
	        case BOOLEAN:	{
	            cNew.setCellValue( cOld.getBooleanCellValue() );
	            break;
	        }
	        
	        case NUMERIC:	{
	            cNew.setCellValue( cOld.getNumericCellValue() );
	            break;
	        }
	        
	        case STRING:	{
	            cNew.setCellValue( cOld.getStringCellValue() );
	            break;
	        }
	        
	        case ERROR:	{
	            cNew.setCellValue( cOld.getErrorCellValue() );
	            break;
	        }
	        
	        case FORMULA:	{
	            cNew.setCellFormula( cOld.getCellFormula() );
	            break;
	        }
	        
			default:
				cNew.setCellValue(cOld.getNumericCellValue());
				break;
	    }
	}
}
