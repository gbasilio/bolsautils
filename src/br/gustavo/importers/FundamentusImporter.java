package br.gustavo.importers;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Date;

import br.gustavo.db.StockDbUtils;

public class FundamentusImporter {


	public static void main(String[] args) throws Exception {
		Date inicio = new Date();
		System.out.println("Importação iniciada em " + inicio);
		
		URL fundamentus = new URL("http://fundamentus.com.br/resultado.php?negociada=ON");
		InputStream in = (InputStream)fundamentus.getContent();

		Connection conn = StockDbUtils.getConnection();
		PreparedStatement insert = conn.prepareStatement("INSERT INTO stock_fundamentals " +
				"(data,	" +
				"symbol, " +
				"name, " +
				"p_l_ratio, " +
				"p_vp_ratio, " +
				"price_sales_ratio, " +
				"divyield, " +
				"price_ativos_ratio, " +
				"price_capgiro_ratio, " +
				"price_ebit_ratio, " +
				"price_ativo_circ_liq_ratio, " +
				"ev_ebit_ratio, " +
				"mrg_ebit, " +
				"mgr_liq, " +
				"liquidez_corr, " +
				"roic, " +
				"roe, " +
				"liq_2_meses, " +
				"patr_liq, " +
				"divbruta_patrimonio_ratio, " +
				"cresc_receita_ult_5_anos) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int bytesRead = in.read(buffer);
		while (bytesRead >= 0){
			baos.write(buffer, 0, bytesRead);
			buffer = new byte[1024];
			bytesRead = in.read(buffer);
		}

		String[] tokenizedContent = new String(baos.toByteArray()).split("<td ckass=\"res_papel\">");
		int numRegistrosImportados = 0;
		for (int i = 0; i < tokenizedContent.length; i++){
			String[] linha = tokenizedContent[i].split("\n");
			if (linha[0].indexOf("href=\"detalhes.php?papel=") > 0){
				String name = linha[0].substring(linha[0].indexOf("title=")+7);
				name = name.substring(0, name.indexOf("\""));
				String symbol = linha[0].substring(linha[0].indexOf("?papel=")+7);
				symbol = symbol.substring(0, symbol.indexOf("\""));
				double p_l_ratio = convertStringToDouble(linha[2].replace("<td>", "").replace("</td>", ""));
				double p_vp_ratio = convertStringToDouble(linha[3].replace("<td>", "").replace("</td>", ""));
				double price_sales_ratio = convertStringToDouble(linha[4].replace("<td>", "").replace("</td>", ""));
				double divyield = convertStringToDouble(linha[5].replace("<td>", "").replace("</td>", ""));
				double price_ativos_ratio = convertStringToDouble(linha[6].replace("<td>", "").replace("</td>", ""));
				double price_capgiro_ratio = convertStringToDouble(linha[7].replace("<td>", "").replace("</td>", ""));
				double price_ebit_ratio = convertStringToDouble(linha[8].replace("<td>", "").replace("</td>", ""));
				double price_ativo_circ_liq_ratio = convertStringToDouble(linha[9].replace("<td>", "").replace("</td>", ""));
				double ev_ebit_ratio = convertStringToDouble(linha[10].replace("<td>", "").replace("</td>", ""));
				double mrg_ebit = convertStringToDouble(linha[11].replace("<td>", "").replace("</td>", ""));
				double mrg_liq = convertStringToDouble(linha[12].replace("<td>", "").replace("</td>", ""));
				double liquidez_corr = convertStringToDouble(linha[13].replace("<td>", "").replace("</td>", ""));
				double roic = convertStringToDouble(linha[14].replace("<td>", "").replace("</td>", ""));
				double roe = convertStringToDouble(linha[15].replace("<td>", "").replace("</td>", ""));
				double liq_2_meses = convertStringToDouble(linha[16].replace("<td>", "").replace("</td>", ""));
				double patr_liq = convertStringToDouble(linha[17].replace("<td>", "").replace("</td>", ""));
				double divbruta_patrimonio_ratio = convertStringToDouble(linha[18].replace("<td>", "").replace("</td>", ""));
				double cresc_receita_ult_5_anos = convertStringToDouble(linha[19].replace("<td>", "").replace("</td>", ""));

				//				System.out.println(symbol + "\n" + name + "\n" + p_l_ratio + "\n" + p_vp_ratio + "\n" + price_sales_ratio + "\n" + divyield + "\n" + price_ativos_ratio + "\n" + 
				//						price_capgiro_ratio + "\n" + price_ebit_ratio + "\n" + price_ativo_circ_liq_ratio + "\n" + ev_ebit_ratio + "\n" + mrg_ebit + "\n" + 
				//						mrg_liq + "\n" + liquidez_corr + "\n" + roic + "\n" + roe + "\n" + liq_2_meses + "\n" + patr_liq + "\n" + divbruta_patrimonio_ratio + "\n" + cresc_receita_ult_5_anos + "\n\n\n\n");

				try{
					Date hoje = new Date();
					hoje.setHours(0);
					hoje.setMinutes(0);
					hoje.setSeconds(0);
					insert.setDate(1, new java.sql.Date(hoje.getTime()));
					insert.setString(2, symbol);
					insert.setString(3, name);
					insert.setDouble(4, p_l_ratio);
					insert.setDouble(5, p_vp_ratio);
					insert.setDouble(6, price_sales_ratio);
					insert.setDouble(7, divyield);
					insert.setDouble(8, price_ativos_ratio);
					insert.setDouble(9, price_capgiro_ratio);
					insert.setDouble(10, price_ebit_ratio);
					insert.setDouble(11, price_ativo_circ_liq_ratio);
					insert.setDouble(12, ev_ebit_ratio);
					insert.setDouble(13, mrg_ebit);
					insert.setDouble(14, mrg_liq);
					insert.setDouble(15, liquidez_corr);
					insert.setDouble(16, roic);
					insert.setDouble(17, roe);
					insert.setDouble(18, liq_2_meses);
					insert.setDouble(19, patr_liq);
					insert.setDouble(20, divbruta_patrimonio_ratio);
					insert.setDouble(21, cresc_receita_ult_5_anos);
					insert.execute();

					System.out.println(symbol + " importado!");
					numRegistrosImportados++;
				}catch (Exception e) {
					System.out.println("Erro ao importar " + symbol + "! " + e.toString());
				}
			}
		}
		
		System.out.println(numRegistrosImportados + " registros importados em " + ((new Date().getTime() - inicio.getTime()) / 1000d) + " segundos.");
	}


	private static Double convertStringToDouble(String s) throws Exception{
		if (s == null || s.length() == 0 || s.trim().length() == 0){
			return null;
		}

		s = s.trim();
		s = s.replace(".", "");
		s = s.replace(",", ".");

		try{
			if (s.endsWith("%")){
				s = s.replace("%", "");
				Double retorno = Double.parseDouble(s);
				return retorno / 100;
			}
			else{
				return Double.parseDouble(s);
			}
		}
		catch (Exception e) {
			System.out.println(e.getCause() + " / entrada = " + s);
			throw e;
		}
	}
}
