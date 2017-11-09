package br.gustavo.importers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import br.gustavo.db.StockDbUtils;

public class IbovespaHistoricoBMFImporter {


	public static void main(String[] args) throws Exception {
		Date inicio = new Date();
		int numRegistrosProcessados = 0;
		
		Connection conn = StockDbUtils.getConnection();
		PreparedStatement insert = conn.prepareStatement("INSERT INTO stock_quote " +
				"(data, codneg, preult) VALUES (?, ?, ?)");
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		
		// DIRETORIO DOS ARQUIVOS COM DADOS DE HISTORICO DO IBOVESPA (xls com tabela do site da BMF)
		File fileDir = new File("/home/rodrigo/Downloads/ibovespa/");
		File[] files = fileDir.listFiles();

		for (int i = 0; i < files.length; i++){
			if (files[i].isDirectory()){
				continue;
			}

		    POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(files[i]));
		    HSSFWorkbook wb = new HSSFWorkbook(fs);
		    HSSFSheet sheet = wb.getSheetAt(0);
		    HSSFRow row;
		    HSSFCell cell;

		    String dataAno = (files[i].getName().replace(".xls", "")).trim();
			Date data = null;

		    int rows; // No of rows
		    rows = sheet.getPhysicalNumberOfRows();
		    int cols = 13;
		    
		    for(int r = 0; r < rows; r++) {
		        row = sheet.getRow(r);
		        if(row != null) {
		            for(int c = 1; c < cols; c++) {
		                cell = row.getCell((short)c);
		                if(cell != null && !cell.toString().isEmpty()) {
		                	data = sdf.parse((r+1) + "/" + c + "/" + dataAno);
		    				insert.setDate(1, new java.sql.Date(data.getTime()));
		    				insert.setString(2, "IBOVESPA");
		    				insert.setDouble(3, cell.getNumericCellValue());
		    				insert.execute();
		    				numRegistrosProcessados++;
		                }
		            }
		        }
		    }
			
			System.out.println(numRegistrosProcessados + " registros processados em " + ((new Date().getTime() - inicio.getTime()) / 1000) + " segundos.");
		}

	}

}
