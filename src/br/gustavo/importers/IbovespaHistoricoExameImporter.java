package br.gustavo.importers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.util.Date;

import br.gustavo.db.StockDbUtils;

public class IbovespaHistoricoExameImporter {

//	private final static String FILE  = "/home/gbasilio/Desktop/historicos_infomoney/ibovespa_historico_exame-UTF-8.xls";
//	private final static String FILE  = "C:\\Users\\gbasilio\\Desktop\\historicos_infomoney\\ibovespa_historico_exame-UTF-8.xls";
	private final static String FILE  = "/home/gbasilio/Desktop/interday.xls";
	
	
	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new FileReader(new File(FILE)));
		
		Connection conn = StockDbUtils.getConnection();
		PreparedStatement insert = conn.prepareStatement("INSERT INTO stock_quote " +
				"(data, codneg, preult) VALUES (?, ?, ?)");

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		String linha = br.readLine();
		while (linha != null){
			String[] elementos = linha.split("\t");
			Date data = null;
			Double valor = null;
			try{
				data = sdf.parse(elementos[0]);
				valor = new Double(elementos[1].replace(',', '.'));
				
				insert.setDate(1, new java.sql.Date(data.getTime()));
				insert.setString(2, "IBOVESPA");
				insert.setDouble(3, valor);
				insert.execute();
			}
			catch (Exception e) {
				System.out.println(e.toString() + " / " + elementos[0] + " / " + elementos[1].replace(',', '.'));
			}
					
			linha = br.readLine();
		}
	}
}
