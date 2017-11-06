package br.gustavo.general;

import java.util.List;

import org.apache.commons.math3.stat.correlation.PearsonsCorrelation;

import br.gustavo.db.LoadFromDB;
import br.gustavo.types.StockQuote;

public class CalculaCorrelacaoIbovespa {

	private static String[] codsIbov = {"BISA3",
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
	"PETR4",
	"BBDC4",
	"AMBV4",
	"VALE5",
	"ITUB4"};
	
	public static void main(String[] args) throws Exception{
		PearsonsCorrelation correl = new PearsonsCorrelation();
		for (int i = 0; i < codsIbov.length; i++){
			List<StockQuote> stockSerieA = LoadFromDB.loadStock(codsIbov[i]);
			for (int k = 0; k < codsIbov.length; k++){
				List<StockQuote> stockSerieB = LoadFromDB.loadStock(codsIbov[k]);
				
				int numElementos = Math.min(stockSerieA.size(), stockSerieB.size());

				int arrayIndex = 0;
				double[] serieA = new double[numElementos];
				for (int c = stockSerieA.size()-1; c >= (stockSerieA.size() - numElementos); c--){
					serieA[arrayIndex] = stockSerieA.get(c).getDayClosing();
//					System.out.println(stockSerieA.get(c).getDate() + " / " + stockSerieA.get(c).getDayClosing());
					arrayIndex++;
				}
				
				arrayIndex = 0;
				double[] serieB = new double[numElementos];;
				for (int c = stockSerieB.size()-1; c >= (stockSerieB.size() - numElementos); c--){
					serieB[arrayIndex] = stockSerieB.get(c).getDayClosing();
//					System.out.println(stockSerieB.get(c).getDate() + " / " + stockSerieB.get(c).getDayClosing());
					arrayIndex++;
				}
				
				System.out.println("Correlação " + codsIbov[i] + " vs " + codsIbov[k] + " = " + correl.correlation(serieA, serieB));
			}
		}
	}
	
}
