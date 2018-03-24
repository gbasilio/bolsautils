package br.gustavo.general;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Random;


/**
 * Esta classe é usada para baixar as planilhas do Infomoney com cotações históricas. 
 * 
 * Ela deve ser executada somente uma vez, ao fazer a primeira carga de dados históricos para formar a base de dados que será usada para os backtests.
 * 
 * As configurações são feitas dentro do próprio método main.
 * 
 * As planilhas baixadas por esta classe são importadas para a base de dados pela classe "br.gustavo.importers.DadosHistoricosInfomoneyImporter".
 * 
 * O tratamento de erros desta classe é simplista mas suficiente para o problema. Os erros acontecem com pouca frequência e o processo é executado somente uma vez, então um tratamento manual dos casos de erro é totalmente viável.
 * 
 * @author Gustavo Zago Basilio
 *
 */
public class DownloadInformacoesInfomoney {

	// Ibrx50
	private static String[] ibrx50 = {"BISA3",
		"GOLL4",
		"RSID3",
		"ELPL4",
		"MMXM3",
		"GFSA3",
		"SUZB5",
		"MRFG3",
		"PDGR3",
		"MRVE3",
		"ALLL3",
		"BRKM5",
		"DASA3",
		"CSAN3",
		"FIBR3",
		"RENT3",
		"CYRE3",
		"CTIP3",
		"OIBR4",
		"OGXP3",
		"HYPE3",
		"USIM5",
		"HGTX3",
		"TIMP3",
		"GOAU4",
		"BRAP4",
		"LAME4",
		"CSNA3",
		"JBSS3",
		"NATU3",
		"LREN3",
		"CRUZ3",
		"CMIG4",
		"BRML3",
		"VIVT4",
		"SANB11",
		"CIEL3",
		"CCRO3",
		"GGBR4",
		"BBAS3",
		"BRFS3",
		"ITSA4",
		"BVMF3",
		"VALE3",
		"PETR3",
		"BBDC4",
		"AMBV4",
		"VALE5",
	"ITUB4"};


	// SMLL
	private static String[] smll = {"MNDL3",
		"POSI3",
		"CCXC3",
		"LUPA3",
		"BICB4",
		"BEEF3",
		"LLIS3",
		"ABCB4",
		"LLXL3",
		"TCSA3",
		"OSXB3",
		"MGLU3",
		"BBRK3",
		"TERI3",
		"VAGR3",
		"BPNM4",
		"TGMA3",
		"SLCE3",
		"BISA3",
		"EQTL3",
		"BTOW3",
		"GOLL4",
		"JHSF3",
		"COCE5",
		"HBOR3",
		"RSID3",
		"LEVE3",
		"BRIN3",
		"QGEP3",
		"GRND3",
		"FLRY3",
		"STBP11",
		"CGAS5",
		"PMAM3",
		"ELPL4",
		"MMXM3",
		"MAGG3",
		"EZTC3",
		"RAPT4",
		"HRTP3",
		"AMAR3",
		"EVEN3",
		"ARZZ3",
		"LPSB3",
		"MYPK3",
		"GFSA3",
		"ALSC3",
		"SULA11",
		"IGTA3",
		"ALPA4",
		"SUZB5",
		"VLID3",
		"MRFG3",
		"LIGT3",
		"MILS3",
		"ESTC3",
		"OHLB3",
		"CSMG3",
		"ODPV3",
		"BRSR6",
		"ENBR3",
		"QUAL3",
		"PDGR3",
		"MRVE3",
		"POMO4",
		"DASA3",
		"AEDU3",
		"TOTS3",
	"KROT11"};


	// ibrx100
	private static String[] ibrx100 = {
		"ABEV3",
		"ALSC3",
		"ALUP11",
		"BBAS3",
		"BBDC3",
		"BBDC4",
		"BBSE3",
		"BEEF3",
		"BPAC11",
		"BRAP4",
		"BRFS3",
		"BRKM5",
		"BRML3",
		"BRPR3",
		"BRSR6",
		"BTOW3",
		"BVMF3",
		"CCRO3",
		"CESP6",
		"CIEL3",
		"CMIG4",
		"CPFE3",
		"CPLE6",
		"CSAN3",
		"CSMG3",
		"CSNA3",
		"CVCB3",
		"CYRE3",
		"DTEX3",
		"ECOR3",
		"EGIE3",
		"ELET3",
		"ELET6",
		"ELPL4",
		"EMBR3",
		"ENBR3",
		"ENGI11",
		"EQTL3",
		"ESTC3",
		"EZTC3",
		"FIBR3",
		"FLRY3",
		"GFSA3",
		"GGBR4",
		"GOAU4",
		"GOLL4",
		"GRND3",
		"HGTX3",
		"HYPE3",
		"IGTA3",
		"ITSA4",
		"ITUB4",
		"JBSS3",
		"KLBN11",
		"KROT3",
		"LAME3",
		"LAME4",
		"LIGT3",
		"LINX3",
		"LREN3",
		"MDIA3",
		"MGLU3",
		"MPLU3",
		"MRFG3",
		"MRVE3",
		"MULT3",
		"MYPK3",
		"NATU3",
		"ODPV3",
		"PCAR4",
		"PETR3",
		"PETR4",
		"POMO4",
		"PSSA3",
		"QUAL3",
		"RADL3",
		"RAIL3",
		"RAPT4",
		"RENT3",
		"SANB11",
		"SAPR4",
		"SBSP3",
		"SEER3",
		"SMLS3",
		"SMTO3",
		"SULA11",
		"SUZB5",
		"TAEE11",
		"TIET11",
		"TIMP3",
		"TOTS3",
		"TRPL4",
		"UGPA3",
		"USIM5",
		"VALE3",
		"VIVT4",
		"VLID3",
		"VVAR11",
		"WEGE3",
		"WIZS3"
	};

	public static void main(String[] args) throws Exception{
		// ** config inicio **
		// lista de ativos que se quer baixar
		String[] cods = ibrx100;
		// pasta local onde se quer armazenar os arquivos baixados
		String localPath = "/tmp";
		// ano inicial da serie que se quer baixar
		int anoInicio = 2000;
		// ano final da serie que se quer baixar
		int anoFim = 2018;
		// ** config fim **


		for (int i = 0; i < cods.length; i++){
			for (int j = anoInicio; j <= anoFim; j = j+5){
				int anoInicioLote = j;
				int anoFimLote = (j + 4 <= anoFim) ? (j + 4) : anoFim;
				// URL de download
				String infoURL = "http://www.infomoney.com.br/Pages/Download/Download.aspx?dtIni=01/01/" + anoInicioLote + "&dtFinish=31/12/" + anoFimLote + "&Ativo=" + cods[i] + "&Semana=null&Per=null&type=2&Stock=" + cods[i] + "&StockType=1";
				try{
					// conexao
					URL url = new URL(infoURL);
					URLConnection con = url.openConnection();
					con.setConnectTimeout(30000);
					con.setReadTimeout(30000);
					InputStream in = con.getInputStream();

					// arquivo onde sera gravada a planilha
					File f = new File(localPath + "/infomoney_dl/" + anoInicioLote + "-" + anoFimLote + "/" + cods[i] + ".xls");
					f.getParentFile().mkdirs();
					java.io.FileOutputStream fos = new java.io.FileOutputStream(f);

					// download e gravacao no arquivo
					byte[] buffer = new byte[2048];
					int bytesRead = in.read(buffer);
					while (bytesRead > 0){
						fos.write(buffer, 0, bytesRead);
						bytesRead = in.read(buffer);
					}

					// tempinho para nao sobrecarregar servidor da infomoney - nao quero sacanear quem me ajuda!
					Thread.sleep(10000 + (new Random()).nextInt(15000));

					System.out.println("Histórico para " + cods[i] + " anos " + anoInicioLote + " a " + anoFimLote + " baixado!");
				} catch (Exception e){
					System.out.println("**ERRO ** Erro ao baixar " + cods[i] + " anos " + anoInicioLote + " a " + anoFimLote + "!");
					System.out.println("\n" + infoURL + "\n");
					e.printStackTrace();
				}
			}
		}
	}


}
