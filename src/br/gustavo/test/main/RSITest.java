package br.gustavo.test.main;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import br.gustavo.db.LoadFromDB;
import br.gustavo.indicators.StockQuoteListUtils;
import br.gustavo.test.strategies.singlestock.BollingerStrategies;
import br.gustavo.test.strategies.singlestock.GeneralStrategies;
import br.gustavo.test.strategies.singlestock.TrendLineStrategies;
import br.gustavo.types.StockQuote;

public class RSITest {


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
		listaSimbolos.add("TRPL4");
		listaSimbolos.add("MPLU3");
		listaSimbolos.add("HBOR3");
		listaSimbolos.add("EZTC3");
		listaSimbolos.add("MRVE3");
		listaSimbolos.add("OHLB3");
		listaSimbolos.add("RDNI3");
		listaSimbolos.add("HGTX3");
//		listaSimbolos.add("LPSB3");
//		listaSimbolos.add("DIRR3");
//		listaSimbolos.add("STBP11");
//		listaSimbolos.add("EVEN3");
//		listaSimbolos.add("BRIN3");
//		listaSimbolos.add("BRML3");
//		listaSimbolos.add("IGTA3");
//		listaSimbolos.add("CYRE3");
//		listaSimbolos.add("VIVT4");
//		listaSimbolos.add("MILS3");
//		listaSimbolos.add("BBRK3");
//		listaSimbolos.add("TOTS3");
//		listaSimbolos.add("RSID3");
//		listaSimbolos.add("ARZZ3");
//		listaSimbolos.add("ODPV3");



		int RSITesteInicio = 50;

		for (int ano = 2010; ano < 2013; ano++){
			for (int i = RSITesteInicio; i <= 150; i++){
				double totalIteracao = 0;
				for (String simbolo: listaSimbolos){
					Calendar start = new GregorianCalendar(ano, Calendar.JANUARY, 01);
					Date startDate = start.getTime();
					Calendar end = new GregorianCalendar((ano+1), Calendar.JANUARY, 01);
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

					//				System.out.println("Carregamento em " + (new Date().getTime() - inicio) + " ms.");

					// testa estrategia
					//		BollingerStrategies.simpleBollinger(listaAcoes);
					//		AMAStrategies.simpleAMARibbonLongTerm(listaAcoes);
					//		BollingerStrategies.simpleBollingerWithTrendMediumTerm(listaAcoes);
					//		CurrentAgainstHistoricalValueStrategy.checkCurrentAgainstHistoricalValueDollarIndex(listaAcoes);
					//		CurrentAgainstHistoricalValueStrategy.checkCurrentAgainstHistoricalValueBRLIndex(listaAcoes);
					//		StochasticStrategies.simpleStochastic200Days8040(listaAcoes);
					//		BollingerStrategies.indiceVolatilidade(listaAcoes);
					//		TrendLineStrategies.simpleTrendLine100DaysAMA(listaAcoes);
					//		AMAStrategies.simpleTrendLine200DaysAMA(listaAcoes);

					//		TrendLineStrategies.findPeaks(listaAcoes);
					//		TrendLineStrategies.shortTermLowTrendLineFromPeaks(listaAcoes);
					//		TrendLineStrategies.shortTermHighTrendLineFromDips(listaAcoes);
					//		TrendLineStrategies.tradeWithTrendLinesFromPeaksAndDipsShortTermStrategy(listaAcoes);
					//		TrendLineStrategies.tradeWithTrendLinesFromPeaksAndDipsShortestTermStrategy(listaAcoes);
					//		TrendLineStrategies.tradeWithHighTrendLinesFromDipsForBuyingShortTermStrategy(listaAcoes);
					//		BollingerStrategies.buyWhenCheapSellWhenReaching10PerCentStrategy(listaAcoes);
					//		GeneralStrategies.buyAfterFallsAndSellAfterGain(listaAcoes);
					//		GeneralStrategies.buyAfterFallsAndSellNextSession(listaAcoes);
					//		TrendLineStrategies.tradeWithTrendLinesJustForBuyingStrategy(listaAcoes);
					//		TrendLineStrategies.tradeWithTrendLinesSellNextSessionStrategy(listaAcoes);
					//		TrendLineStrategies.findLongTermDipIndices(listaAcoes);
					//		GeneralStrategies.buyBelowMinSellAboveMax(listaAcoes);
					//		GeneralStrategies.printMin(listaAcoes);
					//		GeneralStrategies.printROC(listaAcoes);
					//		GeneralStrategies.tradeWithEMAOfROC(listaAcoes);
					//		TrendLineStrategies.tradeWithTrendLinesFromPeaksAndDipsShortestTermStrategy(listaAcoes);

					double retorno = TrendLineStrategies.tradeWithTrendLinesFromPeaksAndDipsPlusRSIShortTermTestVariousRSIValues(listaAcoes, i);
					if (retorno != 0){
						totalIteracao += retorno;
					}
				}

				System.out.println("--------------------->>>> Periodo = janeiro-" + ano + " a janeiro-" + (ano+1) + " / RSITeste = " + i + " / total = " + (totalIteracao / listaSimbolos.size()));
			}
		}
	}


}
