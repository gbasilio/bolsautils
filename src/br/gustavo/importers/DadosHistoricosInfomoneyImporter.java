package br.gustavo.importers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.util.Date;

import br.gustavo.db.StockDbUtils;

public class DadosHistoricosInfomoneyImporter {


	public static void main(String[] args) throws Exception {
		Date inicio = new Date();
		int numRegistrosAjustados = 0;
		
		Connection conn = StockDbUtils.getConnection();
		PreparedStatement update = conn.prepareStatement("UPDATE stock_quote " +
				"SET adjclose = ? WHERE codneg=? AND data=?");
		
		// ARQUIVOS COM DADOS INFOMONEY
//		File fileDir = new File("/home/gbasilio/Desktop/historicos_infomoney/ibrx50");
//		File fileDir = new File("/home/gbasilio/Desktop/historicos_infomoney/smll");
		File fileDir = new File("/home/gbasilio/Desktop/historicos_infomoney/infomoney_20130222");
		
//		File fileDir = new File("C:\\Users\\gbasilio\\Desktop\\historicos_infomoney\\ibrx50");
//		File fileDir = new File("C:\\Users\\gbasilio\\Desktop\\historicos_infomoney\\smll");
		
		File[] files = fileDir.listFiles();

		for (int i = 0; i < files.length; i++){
			if (files[i].isDirectory()){
				continue;
			}

			BufferedReader br = new BufferedReader(new FileReader(files[i]));
			String codneg = (files[i].getName().replace(".xls", "")).trim();
			Date data = null;
			Double fechamentoAjustado = null;

			String linha = br.readLine();
			String[] linhasAnteriores = new String[9];
			while (linha != null){
				if (linhasAnteriores[8] != null && linhasAnteriores[8].indexOf("<td>") > 0 &&
						linhasAnteriores[7] != null && linhasAnteriores[7].indexOf("<tr>") > 0){
					SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
					data = sdf.parse(linha.trim());
				}

				if (linhasAnteriores[8] != null && linhasAnteriores[8].indexOf("<td>") > 0 &&
						linhasAnteriores[7] != null && linhasAnteriores[7].indexOf("</td>") > 0 &&
						linhasAnteriores[5] != null && linhasAnteriores[5].indexOf("<td>") > 0 &&
						linhasAnteriores[4] != null && linhasAnteriores[4].indexOf("</td>") > 0 &&
						linhasAnteriores[2] != null && linhasAnteriores[2].indexOf("<td>") > 0 &&
						linhasAnteriores[1] != null && linhasAnteriores[1].indexOf("<tr>") > 0){
					if (linha.indexOf("n/d") < 0){
						fechamentoAjustado = new Double(linha.trim().replace(',', '.'));
					}
					else{
						fechamentoAjustado = -1d;	
					}
				}

				linhasAnteriores[0] = linhasAnteriores[1];
				linhasAnteriores[1] = linhasAnteriores[2];
				linhasAnteriores[2] = linhasAnteriores[3];
				linhasAnteriores[3] = linhasAnteriores[4];
				linhasAnteriores[4] = linhasAnteriores[5];
				linhasAnteriores[5] = linhasAnteriores[6];
				linhasAnteriores[6] = linhasAnteriores[7];
				linhasAnteriores[7] = linhasAnteriores[8];
				linhasAnteriores[8] = linha;

				linha = br.readLine();

				if (data != null && fechamentoAjustado != null){
					System.out.println(codneg + " / Data = " + data + " / fechamentoAjustado = " + fechamentoAjustado);

					update.setDouble(1, fechamentoAjustado);
					update.setString(2, codneg);
					update.setDate(3, new java.sql.Date(data.getTime()));
					update.execute();
					numRegistrosAjustados++;
					
					data = null;
					fechamentoAjustado = null;
				}
			}
			
			System.out.println(numRegistrosAjustados + " registros ajustados em " + ((new Date().getTime() - inicio.getTime()) / 1000) + " segundos.");
		}

	}

}
