package br.gustavo.importers;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Date;

import org.htmlcleaner.CleanerProperties;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;

import br.gustavo.db.StockDbUtils;

public class BastterImporter {


	public static void main(String[] args) throws Exception {
		Date inicio = new Date();
		System.out.println("Importação iniciada em " + inicio);

		String[] codneg = {"ABEV",
				"BBAS",
				"BBDC",
				"BBDC",
				"BBSE",
				"BRAP",
				"BRFS",
				"BRML",
				"BRPR",
				"BVMF",
				"CCRO",
				"CIEL",
				"CMIG",
				"CRUZ",
				"CSAN",
				"CSNA",
				"CTIP",
				"CYRE",
				"EMBR",
				"ESTC",
				"FIBR",
				"GFSA",
				"GGBR",
				"HGTX",
				"HYPE",
				"ITSA",
				"ITUB",
				"JBSS",
				"KLBN",
				"KROT",
				"LAME",
				"LREN",
				"MRVE",
				"NATU",
				"OIBR",
				"PCAR",
				"PDGR",
				"PETR",
				"QUAL",
				"RENT",
				"RLOG",
				"SANB",
				"SBSP",
				"SUZB",
				"TIMP",
				"UGPA",
				"USIM",
				"VALE",
				"VIVT"
				};


		for (int j  = 0; j < codneg.length; j++){

			try{
				// REAL
				URL bastter = new URL("http://www.bastter.com/mercado/acao/" + codneg[j] + ".aspx");
				InputStream in = (InputStream)bastter.getContent();

				// TESTE
				//		FileInputStream in = new FileInputStream(new File("/home/gbasilio/Desktop/rcsl.html"));


				Connection conn = StockDbUtils.getConnection();

				PreparedStatement insert = conn.prepareStatement("INSERT INTO yearly_fundamentals " +
						"(ano,	" +
						"codneg, " +
						"patrimonio_liquido, " +
						"receita_liquida, " +
						"lucro_liquido, " +
						"margem_liquida, " +
						"roe, " +
						"caixa, " +
						"caixa_liquido, " +
						"divida, " +
						"divida_pl_ratio, " +
						"divida_lucro_liquido_ratio) " +
						"VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				byte[] buffer = new byte[1024];
				int bytesRead = in.read(buffer);
				while (bytesRead >= 0){
					baos.write(buffer, 0, bytesRead);
					buffer = new byte[1024];
					bytesRead = in.read(buffer);
				}

				String conteudo = new String(baos.toByteArray());
				conteudo = conteudo.substring(conteudo.indexOf("<table class=\"evanual\">"));
				conteudo = conteudo.substring(conteudo.indexOf("<tbody>"), conteudo.indexOf("</tbody>")+8);

				HtmlCleaner cleaner = new HtmlCleaner();
				CleanerProperties props = cleaner.getProperties();
				props.setTranslateSpecialEntities(true);
				props.setTransResCharsToNCR(true);
				props.setOmitComments(true);
				//TagNode node = cleaner.clean(conteudo);
				TagNode node = cleaner.clean("<html><body><table>" + conteudo + "</table></body></html>");

				int ano = -1;
				double patrimonio_liquido = -1d;
				double receita_liquida = -1d;
				double lucro_liquido = -1d;
				double margem_liquida = -1d;
				double roe= -1d;
				double caixa = -1d;
				double caixa_liquido = -1d;
				double divida = -1d;
				double divida_pl_ratio = -1d;
				double divida_lucro_liquido_ratio = -1d;

				TagNode[] allNodes = node.getAllElements(true);
				int index = 1;
				for (int i = 0; i < allNodes.length; i++){
					if (allNodes[i].getName().equalsIgnoreCase("td")){
						String s = allNodes[i].getText()+"";
						s=s.substring(s.lastIndexOf(">")+1);
						if (index == 1){
							System.out.println("\n\nAno = " + s);
							ano = Integer.parseInt(s);
						}
						if (index == 2){
							System.out.println("Patrimonio = " + s);
							patrimonio_liquido = convertStringToDouble(s);
						}
						if (index == 3){
							System.out.println("Receita liquida = " + s);
							receita_liquida = convertStringToDouble(s);
						}
						if (index == 4){
							System.out.println("Lucro liquido = " + s);
							lucro_liquido = convertStringToDouble(s);
						}
						if (index == 5){
							System.out.println("Margem liquida = " + s);
							margem_liquida = convertStringToDouble(s);
						}
						if (index == 6){
							System.out.println("ROE = " + s);
							roe = convertStringToDouble(s);
						}
						if (index == 7){
							System.out.println("Caixa = " + s);
							caixa = convertStringToDouble(s);
						}
						if (index == 8){
							System.out.println("Caixa liquido = " + s);
							caixa_liquido = convertStringToDouble(s);
						}
						if (index == 9){
							System.out.println("Divida bruta = " + s);
							divida = convertStringToDouble(s);
						}
						if (index == 10){
							System.out.println("Divida/PL = " + s);
							divida_pl_ratio = convertStringToDouble(s);
						}
						if (index == 11){
							System.out.println("Divida/LL = " + s);
							divida_lucro_liquido_ratio = convertStringToDouble(s);
						}
						index++;
					}
					if (allNodes[i].getName().equalsIgnoreCase("tr")){

						try{
							if (ano != -1){
								insert.setInt(1, ano);
								insert.setString(2, codneg[j]);
								insert.setDouble(3, patrimonio_liquido);
								insert.setDouble(4, receita_liquida);
								insert.setDouble(5, lucro_liquido);
								insert.setDouble(6, margem_liquida);
								insert.setDouble(7, roe);
								insert.setDouble(8, caixa);
								insert.setDouble(9, caixa_liquido);
								insert.setDouble(10, divida);
								insert.setDouble(11, divida_pl_ratio);
								insert.setDouble(12, divida_lucro_liquido_ratio);
								insert.execute();

								System.out.println(codneg[j] + " / " + ano + " importado!");
							}
						}catch (Exception e) {
							System.out.println("Erro ao importar " + codneg[j] + "! " + e.toString());
						}

						index = 1;
					}
				}

				// insere ultimo registro
				if (ano != -1){
					insert.setInt(1, ano);
					insert.setString(2, codneg[j]);
					insert.setDouble(3, patrimonio_liquido);
					insert.setDouble(4, receita_liquida);
					insert.setDouble(5, lucro_liquido);
					insert.setDouble(6, margem_liquida);
					insert.setDouble(7, roe);
					insert.setDouble(8, caixa);
					insert.setDouble(9, caixa_liquido);
					insert.setDouble(10, divida);
					insert.setDouble(11, divida_pl_ratio);
					insert.setDouble(12, divida_lucro_liquido_ratio);
					insert.execute();

					System.out.println(codneg[j] + " / " + ano + " importado!");
				}
			}
			catch (Exception e) {
				System.out.println("Erro importando " + codneg[j]);
				e.printStackTrace();
			}

			//System.out.println(numRegistrosImportados + " registros importados em " + ((new Date().getTime() - inicio.getTime()) / 1000d) + " segundos.");
		}
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
				try{
					return Double.parseDouble(s);
				}
				catch (Exception e) {
					return -9999999999d;
				}
			}
		}
		catch (Exception e) {
			System.out.println(e.getCause() + " / entrada = " + s);
			throw e;
		}
	}
}
