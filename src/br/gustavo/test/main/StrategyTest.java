package br.gustavo.test.main;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import br.gustavo.db.LoadFromDB;
import br.gustavo.indicators.StockQuoteListUtils;
import br.gustavo.test.strategies.singlestock.TrendLineStrategies;
import br.gustavo.types.StockQuote;

public class StrategyTest {

	
	
	private static String[] ibrx100 = {
		"AEDU3",
		"ALLL3",
		"AMBV3",
		"AMBV4",
		"AMIL3",
		"BBAS3",
		"BBDC3",
		"BBDC4",
		"BBRK3",
		"BISA3",
		"BRAP4",
		"BRFS3",
		"BRKM5",
		"BRML3",
		"BRPR3",
		"BRSR6",
		"BTOW3",
		"BVMF3",
		"CCRO3",
		"CCXC3",
		"CESP6",
		"CIEL3",
		"CMIG4",
		"CPFE3",
		"CPLE6",
		"CRUZ3",
		"CSAN3",
		"CSMG3",
		"CSNA3",
		"CTIP3",
		"CYRE3",
		"DASA3",
		"DTEX3",
		"ECOR3",
		"ELET3",
		"ELET6",
		"ELPL4",
		"EMBR3",
		"ENBR3",
		"EZTC3",
		"FIBR3",
		"GETI4",
		"GFSA3",
		"GGBR4",
		"GOAU4",
		"GOLL4",
		"HGTX3",
		"HRTP3",
		"HYPE3",
		"IGTA3",
		"ITSA4",
		"ITUB4",
		"JBSS3",
		"KLBN4",
		"LAME4",
		"LIGT3",
		"LLXL3",
		"LREN3",
		"MGLU3",
		"MMXM3",
		"MPLU3",
		"MPXE3",
		"MRFG3",
		"MRVE3",
		"MULT3",
		"MYPK3",
		"NATU3",
		"ODPV3",
		"OGXP3",
		"OHLB3",
		"OIBR3",
		"OIBR4",
		"OSXB3",
		"PCAR4",
		"PDGR3",
		"PETR3",
		"PETR4",
		"POMO4",
		"PSSA3",
		"QGEP3",
		"RADL3",
		"RAPT4",
		"RENT3",
		"RSID3",
		"SANB11",
		"SBSP3",
		"SULA11",
		"SUZB5",
		"TBLE3",
		"TCSA3",
		"TIMP3",
		"TOTS3",
		"TRPL4",
		"UGPA3",
		"USIM3",
		"USIM5",
		"VAGR3",
		"VALE3",
		"VALE5",
		"VIVT4",
		"HBOR3",
		"DIRR3",
		"EVEN3",
		"BRIN3"
			};
	
	
	
	
	

	public static void main(String[] args) throws Exception{
		long inicio = new Date().getTime();

		List<String> listaSimbolos = new ArrayList<String>();
		//		listaSimbolos.add("BBAS3");
		//		listaSimbolos.add("BRKM5");
		//		listaSimbolos.add("ELET6");
		//		listaSimbolos.add("GETI4");
		//		listaSimbolos.add("ITUB4");
		//		listaSimbolos.add("JBSS3");
		//		listaSimbolos.add("VALE3");
		//		listaSimbolos.add("PETR4");

		// piores 2011
		//		listaSimbolos.add("BTOW3");
		//		listaSimbolos.add("GOLL4");
		//		listaSimbolos.add("HYPE3");
		//		listaSimbolos.add("USIM5");
		//		listaSimbolos.add("GGBR4");
		//		listaSimbolos.add("GFSA3");
		//		listaSimbolos.add("CSNA3");
		//		listaSimbolos.add("OGXP3");
		//		listaSimbolos.add("JBSS3");
		//		listaSimbolos.add("SANB11");

		// ibovespa
		//		listaSimbolos.add("BISA3");
		//		listaSimbolos.add("GOLL4");
		//		listaSimbolos.add("RSID3");
		//		listaSimbolos.add("ELPL4");
		//		listaSimbolos.add("MMXM3");
		//		listaSimbolos.add("GFSA3");
		//		listaSimbolos.add("SUZB5");
		//		listaSimbolos.add("MRFG3");
		//		listaSimbolos.add("PDGR3");
		//		listaSimbolos.add("MRVE3");
		//		listaSimbolos.add("ALLL3");
		//		listaSimbolos.add("BRKM5");
		//		listaSimbolos.add("DASA3");
		//		listaSimbolos.add("CSAN3");
		//		listaSimbolos.add("FIBR3");
		//		listaSimbolos.add("RENT3");
		//		listaSimbolos.add("CYRE3");
		//		listaSimbolos.add("CTIP3");
		//		listaSimbolos.add("OIBR4");
		//		listaSimbolos.add("OGXP3");
		//		listaSimbolos.add("HYPE3");
		//		listaSimbolos.add("USIM5");
		//		listaSimbolos.add("HGTX3");
		//		listaSimbolos.add("TIMP3");
		//		listaSimbolos.add("GOAU4");
		//		listaSimbolos.add("BRAP4");
		//		listaSimbolos.add("LAME4");
		//		listaSimbolos.add("CSNA3");
		//		listaSimbolos.add("JBSS3");
		//		listaSimbolos.add("NATU3");
		//		listaSimbolos.add("LREN3");
		//		listaSimbolos.add("CRUZ3");
		//		listaSimbolos.add("CMIG4");
		//		listaSimbolos.add("BRML3");
		//		listaSimbolos.add("VIVT4");
		//		listaSimbolos.add("SANB11");
		//		listaSimbolos.add("CIEL3");
		//		listaSimbolos.add("CCRO3");
		//		listaSimbolos.add("GGBR4");
		//		listaSimbolos.add("BBAS3");
		//		listaSimbolos.add("BRFS3");
		//		listaSimbolos.add("ITSA4");
		//		listaSimbolos.add("BVMF3");
		//		listaSimbolos.add("VALE3");
		//		listaSimbolos.add("PETR3");
		//		listaSimbolos.add("BBDC4");
		//		listaSimbolos.add("AMBV4");
		//		listaSimbolos.add("VALE5");
		//		listaSimbolos.add("ITUB4");

		// filtradas fundamentus
//		listaSimbolos.add("MPLU3");
//		listaSimbolos.add("HBOR3");
//		listaSimbolos.add("OHLB3");
//		listaSimbolos.add("MRVE3");
//		listaSimbolos.add("KROT11");
//		listaSimbolos.add("HGTX3");
//		listaSimbolos.add("DIRR3");
//		listaSimbolos.add("EVEN3");
//		listaSimbolos.add("BRIN3");
//		listaSimbolos.add("BRML3");
//		listaSimbolos.add("RSID3");
//		listaSimbolos.add("ODPV3");
//		listaSimbolos.add("DTEX3");
//		listaSimbolos.add("PCAR4");
//		listaSimbolos.add("ALSC3");
//		listaSimbolos.add("MYPK3");
//		listaSimbolos.add("KROT3");
//		listaSimbolos.add("JSLG3");
//		listaSimbolos.add("FLRY3");
//		listaSimbolos.add("CSAN3");
//		listaSimbolos.add("LLIS3");
//		listaSimbolos.add("JBSS3");
//		listaSimbolos.add("RADL3");
//		listaSimbolos.add("QGEP3");


//		listaSimbolos.add("VALE3");
//		listaSimbolos.add("RSID3");
		
		
		// backtest desde 2003
//		listaSimbolos.add("BBAS3");
//		listaSimbolos.add("BRKM5");
//		listaSimbolos.add("PETR4");
//		listaSimbolos.add("USIM5");
//		listaSimbolos.add("VALE5");
//		listaSimbolos.add("HGTX3");
//		listaSimbolos.add("LAME4");
//		listaSimbolos.add("AMBV4");
//		listaSimbolos.add("ELET6");
//		listaSimbolos.add("GGBR4");

//		for (int i = 0; i < ibrx100.length; i++){
//			listaSimbolos.add(ibrx100[i]);
//		}
		
		
		listaSimbolos.add("MPLU3");
		listaSimbolos.add("HGTX3");
		listaSimbolos.add("EZTC3");
//		listaSimbolos.add("LPSB3");
		listaSimbolos.add("BRML3");
		listaSimbolos.add("BRIN3");
		listaSimbolos.add("VIVT4");
		listaSimbolos.add("BBRK3");
		listaSimbolos.add("ODPV3");
//		listaSimbolos.add("ARZZ3");
		listaSimbolos.add("DTEX3");
		listaSimbolos.add("MULT3");
//		listaSimbolos.add("GGBR3");
		listaSimbolos.add("GGBR4");
//		listaSimbolos.add("FLRY3");
//		listaSimbolos.add("KROT3");
		listaSimbolos.add("DASA3");
		listaSimbolos.add("AEDU3");
		listaSimbolos.add("RADL3");
		listaSimbolos.add("QGEP3");
		listaSimbolos.add("PETR4");
		listaSimbolos.add("VALE3");
		listaSimbolos.add("MILK11");
		
		
		double total = 0;
		int qtdAcoesCompradas = 0;
		for (String simbolo: listaSimbolos){
			Calendar start = new GregorianCalendar(2003, Calendar.FEBRUARY, 02);
			Date startDate = start.getTime();
			Calendar end = new GregorianCalendar(2012, Calendar.FEBRUARY, 20);
			Date endDate = end.getTime();

			List<StockQuote> listaAcoes = LoadFromDB.load(simbolo, startDate, endDate);

			// carrega indicadores
			StockQuoteListUtils.populate12DayEMA(listaAcoes);
			StockQuoteListUtils.populate26DayEMA(listaAcoes);
			StockQuoteListUtils.populate4DayAMA(listaAcoes);
			StockQuoteListUtils.populate9DayAMA(listaAcoes);
			StockQuoteListUtils.populate18DayAMA(listaAcoes);
			StockQuoteListUtils.populate25DayAMA(listaAcoes);
			StockQuoteListUtils.populate50DayAMA(listaAcoes);
			StockQuoteListUtils.populate100DayAMA(listaAcoes);
			StockQuoteListUtils.populate200DayAMA(listaAcoes);
			StockQuoteListUtils.populateStochasticOscillator14Days(listaAcoes);
			StockQuoteListUtils.populateStochasticOscillator200Days(listaAcoes);
			StockQuoteListUtils.populateBollingerBands(listaAcoes);
			StockQuoteListUtils.populateMinLast200Days(listaAcoes);
			StockQuoteListUtils.populateMaxLast200Days(listaAcoes);
			StockQuoteListUtils.populateRoc20Days(listaAcoes);
			StockQuoteListUtils.populate200DaysEMAOfOC20DaysR(listaAcoes);
			StockQuoteListUtils.populateRSI14Days(listaAcoes);
			StockQuoteListUtils.populateRSI5Days(listaAcoes);
			StockQuoteListUtils.populateMoreThan10DaysBelowIbovespa(listaAcoes);

			//				System.out.println("Carregamento em " + (new Date().getTime() - inicio) + " ms.");

			// testa estrategia
			//		BollingerStrategies.simpleBollinger(listaAcoes);
			//					AMAStrategies.simpleAMARibbonLongTerm(listaAcoes);
			//					BollingerStrategies.simpleBollingerWithTrendMediumTerm(listaAcoes);
			//		CurrentAgainstHistoricalValueStrategy.checkCurrentAgainstHistoricalValueDollarIndex(listaAcoes);
			//		CurrentAgainstHistoricalValueStrategy.checkCurrentAgainstHistoricalValueBRLIndex(listaAcoes);
			//					StochasticStrategies.simpleStochastic200Days8040(listaAcoes);
			//					BollingerStrategies.indiceVolatilidade(listaAcoes);
			//					TrendLineStrategies.simpleTrendLine100DaysAMA(listaAcoes);
			//					AMAStrategies.simpleTrendLine200DaysAMA(listaAcoes);

			//		TrendLineStrategies.findPeaks(listaAcoes);
//					TrendLineStrategies.shortTermLowTrendLineFromPeaks(listaAcoes);
			//		TrendLineStrategies.shortTermHighTrendLineFromDips(listaAcoes);
//					TrendLineStrategies.tradeWithTrendLinesFromPeaksAndDipsShortTermStrategy(listaAcoes);	
			
			
//			total += TrendLineStrategies.tradeWithTrendLinesFromPeaksAndDipsShortestTermStrategy(listaAcoes);
			//		TrendLineStrategies.tradeWithHighTrendLinesFromDipsForBuyingShortTermStrategy(listaAcoes);
//								BollingerStrategies.buyWhenCheapSellWhenReaching10PerCentStrategy(listaAcoes);
			//					GeneralStrategies.buyAfterFallsAndSellAfterGain(listaAcoes);
			//					GeneralStrategies.buyAfterFallsAndSellNextSession(listaAcoes);
//			total += TrendLineStrategies.tradeWithTrendLinesJustForBuyingStrategy(listaAcoes);
			//		TrendLineStrategies.tradeWithTrendLinesSellNextSessionStrategy(listaAcoes);
			//		TrendLineStrategies.findLongTermDipIndices(listaAcoes);
			//					GeneralStrategies.buyBelowMinSellAboveMax(listaAcoes);
			//		GeneralStrategies.printMin(listaAcoes);
			//		GeneralStrategies.printROC(listaAcoes);
//								GeneralStrategies.tradeWithEMAOfROC(listaAcoes);
			//					TrendLineStrategies.tradeWithTrendLinesFromPeaksAndDipsShortestTermStrategy(listaAcoes);
//			total += TrendLineStrategies.tradeWithTrendLinesForBuyingAndBollingerForSellingStrategy(listaAcoes);
			double fatorMultiplicacao = TrendLineStrategies.newTradeWithTrendLinesForBuyingAndBollingerForSellingStrategy(listaAcoes);
			if (fatorMultiplicacao > 0 && fatorMultiplicacao != 1){
				total += fatorMultiplicacao;
				qtdAcoesCompradas++;
			}
		}
		
		System.out.println("Rendimento total = " + total / qtdAcoesCompradas);
	}


}
