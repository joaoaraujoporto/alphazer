package com.alphazer;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.EncryptedDocumentException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.alphazer.util.Set;

public class XlsxHandlerTest {
	XlsxHandler xlsxHandler;
	XlsxDoc xlsxDoc;
	
	@Before
	public void init() throws EncryptedDocumentException, IOException {
		xlsxHandler = new XlsxHandler();
		xlsxDoc = xlsxHandler.readXlsx("C:/Users/JoaoGiet/Desktop/testSheet.xlsx");
	}
	
	@Test
	public void createXlsxCopytest() throws EncryptedDocumentException, FileNotFoundException, IOException {
		xlsxHandler.createXlsxCopy(xlsxDoc, "C:/Users/JoaoGiet/Desktop/testSheetCopy.xlsx").close();
	}
	
	@Test
	public void removeColumnsTest() throws EncryptedDocumentException, FileNotFoundException, IOException {
		Set<Integer> columns = new Set<Integer>();		
		XlsxDoc copy = xlsxHandler.createXlsxCopy(xlsxDoc, "C:/Users/JoaoGiet/Desktop/testSheetCopy.xlsx");
		
		columns.add(3);
		columns.add(7);
		columns.add(10);
		
		xlsxHandler.removeColumns(copy, columns);
		xlsxHandler.saveWorkbook(copy.getWorkbook(), "C:/Users/JoaoGiet/Desktop/testSheetCopy2.xlsx");
		copy.close();
	}
	
	@After
	public void finit() throws IOException {
		xlsxDoc.close();
	}
}
