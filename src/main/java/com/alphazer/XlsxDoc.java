package com.alphazer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.alphazer.util.Set;

public class XlsxDoc {
	private Workbook workbook;
	private String fileLocation;
	
	
	public XlsxDoc(Workbook workbook, String fileLocation) {
		this.workbook = workbook;
		this.fileLocation = fileLocation;
	}
	
	public int getNumberColumns() {
		return workbook.getSheetAt(0).
				getRow(0).getLastCellNum();
	}
	
	public Set<Integer> getColumnsNumbers() {
		Set<Integer> columnsNumbers = new Set<Integer>();
		
		for (int i = 0; i < getNumberColumns(); i++)
			columnsNumbers.add(i + 1);
		
		return columnsNumbers;
	}

	public void close() throws IOException {
		workbook.close();
	}

	public Workbook getWorkbook() {
		return workbook;
	}

	public String getFileLocation() {
		return fileLocation;
	}
}
