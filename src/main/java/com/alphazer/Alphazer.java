package com.alphazer;

import java.io.IOException;

import org.apache.poi.EncryptedDocumentException;
import org.rosuda.JRI.*;

import com.alphazer.util.Set;

public class Alphazer {
	private XlsxHandler xlsxHandler;
	XlsxDoc dataset;
	Set<Result> alphas;
	Rengine re;
	
	public static void main(String args[]) throws EncryptedDocumentException, IOException {
		if (args[0] == null)
			System.exit(0);
		
		Alphazer alphazer = new Alphazer();
		System.out.println(alphazer.alphazer(args[0]));
	}
	
	public Alphazer() {
		xlsxHandler = new XlsxHandler();
		alphas = new Set<Result>();
		re = new Rengine(new String [] {"--vanilla"}, false, null);
	}
	
	public String alphazer(String datasetLocation) throws EncryptedDocumentException, IOException {		
		dataset = xlsxHandler.readXlsx(datasetLocation);
		Set<Set<Integer>> sub = dataset.getColumnsNumbers().getSubsets();
			
		re.eval("library(\"readxl\")");
		re.eval("library(\"psych\")");
		
		for (Set<Integer> z : sub) {
			Set<Integer> columnsToExclude = dataset.getColumnsNumbers();		
			columnsToExclude.removeAll(z);
			
			XlsxDoc datasubset = xlsxHandler.createXlsxCopy(dataset, "temp/datasubset.xlsx");
			xlsxHandler.removeColumns(datasubset, columnsToExclude);
			xlsxHandler.saveWorkbook(datasubset.getWorkbook(), "temp/datasubset_2.xlsx");
			
			datasubset.close();
			alphas.add(new Result(z, alpha(datasubset)));
		}
		
		dataset.close();
		
		Result result = alphas.getFirst();
		
		for (Result r : alphas)
			if (result.getAlpha() < r.getAlpha())
				result = r;
		
		return result.toString();
	}

	private double alpha(XlsxDoc datasubset) {
		REXP result;
		
		re.assign("dados", "read_excel(\"" + datasubset.getFileLocation() + "\", na = \"NA\")");
		re.assign("a", "alpha(dados)");
		result =  re.eval("a$total$raw_alpha");
		
		return result.asDouble();
	}
}
