package br.gustavo.indicators;

import java.io.Console;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;

import br.gustavo.db.LoadFromDB;
import br.gustavo.types.StockQuote;


public class StockQuoteListUtils {


	/**
	 * 
	 * @param listaAcoes
	 */
	public static void populate12DayEMA(List<StockQuote> listaAcoes){
		if (listaAcoes == null || listaAcoes.size() == 0){
			return;
		}
		
		// primeiro valor é sempre o valor do dia
		listaAcoes.get(0).set_12DayEMA(listaAcoes.get(0).getDayClosing());

		for (int i = 1; i < listaAcoes.size(); i++){
			double mediaMovelExponencial12DiasAnterior = listaAcoes.get(i-1).get_12DayEMA().doubleValue();
			double valorDeFechamentoCorrente = listaAcoes.get(i).getDayClosing().doubleValue();
			listaAcoes.get(i).set_12DayEMA(Utils.mediaMovelExponencial(mediaMovelExponencial12DiasAnterior, valorDeFechamentoCorrente, 12));
		}
	}

	/**
	 * 
	 * @param listaAcoes
	 */
	public static void populate26DayEMA(List<StockQuote> listaAcoes){
		if (listaAcoes == null || listaAcoes.size() == 0){
			return;
		}
		
		// primeiro valor é sempre o valor do dia
		listaAcoes.get(0).set_26DayEMA(listaAcoes.get(0).getDayClosing());

		for (int i = 1; i < listaAcoes.size(); i++){
			double mediaMovelExponencial26DiasAnterior = listaAcoes.get(i-1).get_26DayEMA().doubleValue();
			double valorDeFechamentoCorrente = listaAcoes.get(i).getDayClosing().doubleValue();
			listaAcoes.get(i).set_26DayEMA(Utils.mediaMovelExponencial(mediaMovelExponencial26DiasAnterior, valorDeFechamentoCorrente, 26));
		}
	}


	/**
	 * 
	 * @param listaAcoes
	 */
	public static void populate4DayAMA(List<StockQuote> listaAcoes){

		for (int i = 3; i < listaAcoes.size(); i++){
			List<Double> listaValores = new ArrayList<Double>();
			for (int j = i; j > (i - 4); j--){
				listaValores.add(listaAcoes.get(j).getDayClosing().doubleValue());
			}
			listaAcoes.get(i).set_4DayAMA(Utils.mediaAritmetica(listaValores));
		}
	}


	/**
	 * 
	 * @param listaAcoes
	 */
	public static void populate9DayAMA(List<StockQuote> listaAcoes){

		for (int i = 8; i < listaAcoes.size(); i++){
			List<Double> listaValores = new ArrayList<Double>();
			for (int j = i; j > (i - 9); j--){
				listaValores.add(listaAcoes.get(j).getDayClosing().doubleValue());
			}
			listaAcoes.get(i).set_9DayAMA(Utils.mediaAritmetica(listaValores));
		}
	}

	/**
	 * 
	 * @param listaAcoes
	 */
	public static void populate18DayAMA(List<StockQuote> listaAcoes){

		for (int i = 17; i < listaAcoes.size(); i++){
			List<Double> listaValores = new ArrayList<Double>();
			for (int j = i; j > (i - 18); j--){
				listaValores.add(listaAcoes.get(j).getDayClosing().doubleValue());
			}
			listaAcoes.get(i).set_18DayAMA(Utils.mediaAritmetica(listaValores));
		}
	}

	/**
	 * 
	 * @param listaAcoes
	 */
	public static void populate25DayAMA(List<StockQuote> listaAcoes){

		for (int i = 24; i < listaAcoes.size(); i++){
			List<Double> listaValores = new ArrayList<Double>();
			for (int j = i; j > (i - 25); j--){
				listaValores.add(listaAcoes.get(j).getDayClosing().doubleValue());
			}
			listaAcoes.get(i).set_25DayAMA(Utils.mediaAritmetica(listaValores));
		}
	}

	/**
	 * 
	 * @param listaAcoes
	 */
	public static void populate50DayAMA(List<StockQuote> listaAcoes){

		for (int i = 49; i < listaAcoes.size(); i++){
			List<Double> listaValores = new ArrayList<Double>();
			for (int j = i; j > (i - 50); j--){
				listaValores.add(listaAcoes.get(j).getDayClosing().doubleValue());
			}
			listaAcoes.get(i).set_50DayAMA(Utils.mediaAritmetica(listaValores));
		}
	}

	/**
	 * 
	 * @param listaAcoes
	 */
	public static void populate100DayAMA(List<StockQuote> listaAcoes){

		for (int i = 99; i < listaAcoes.size(); i++){
			List<Double> listaValores = new ArrayList<Double>();
			for (int j = i; j > (i - 100); j--){
				listaValores.add(listaAcoes.get(j).getDayClosing().doubleValue());
			}
			listaAcoes.get(i).set_100DayAMA(Utils.mediaAritmetica(listaValores));
		}
	}

	/**
	 * 
	 * @param listaAcoes
	 */
	public static void populate200DayAMA(List<StockQuote> listaAcoes){

		for (int i = 199; i < listaAcoes.size(); i++){
			List<Double> listaValores = new ArrayList<Double>();
			for (int j = i; j > (i - 200); j--){
				listaValores.add(listaAcoes.get(j).getDayClosing().doubleValue());
			}
			listaAcoes.get(i).set_200DayAMA(Utils.mediaAritmetica(listaValores));
		}
	}

	/**
	 * 
	 * @param listaAcoes
	 */
	public static void populateStochasticOscillator14Days(List<StockQuote> listaAcoes){
		for (int i = 13; i < listaAcoes.size(); i++){
			List<Double> listaValores = new ArrayList<Double>();
			for (int j = i; j > (i - 14); j--){
				listaValores.add(listaAcoes.get(j).getDayClosing().doubleValue());
			}
			Double max = Collections.max(listaValores);
			Double min = Collections.min(listaValores);

			listaAcoes.get(i).setStochasticOscillator14Days(100 * ((listaAcoes.get(i).getDayClosing().doubleValue() - min)/(max - min)));
		}
	}

	/**
	 * 
	 * @param listaAcoes
	 */
	public static void populateStochasticOscillator200Days(List<StockQuote> listaAcoes){
		for (int i = 199; i < listaAcoes.size(); i++){
			List<Double> listaValores = new ArrayList<Double>();
			for (int j = i; j > (i - 200); j--){
				listaValores.add(listaAcoes.get(j).getDayClosing().doubleValue());
			}
			Double max = Collections.max(listaValores);
			Double min = Collections.min(listaValores);

			listaAcoes.get(i).setStochasticOscillator200Days(100 * ((listaAcoes.get(i).getDayClosing().doubleValue() - min)/(max - min)));
		}
	}

	/**
	 * 
	 * @param listaAcoes
	 */
	public static void populateBollingerBands(List<StockQuote> listaAcoes){
		for (int i = 19; i < listaAcoes.size(); i++){
			List<Double> listaValores = new ArrayList<Double>();
			for (int j = i; j > (i - 20); j--){
				listaValores.add(listaAcoes.get(j).getDayClosing().doubleValue());
			}

			// Bollinger middle band
			double mMA20Dias = Utils.mediaAritmetica(listaValores);
			listaAcoes.get(i).setBollingerMiddleBand(mMA20Dias);

			// Bollinger upper band
			double desvioPadrao = Utils.desvioPadrao(listaValores);
			listaAcoes.get(i).setBollingerUpperBand(mMA20Dias + (2 * desvioPadrao));

			// Bollinger lower band
			listaAcoes.get(i).setBollingerLowerBand(mMA20Dias - (2 * desvioPadrao));
		}
	}


	/**
	 * 
	 * @param listaAcoes
	 */
	public static void populateMinLast200Days(List<StockQuote> listaAcoes){

		for (int i = 199; i < listaAcoes.size(); i++){
			List<Double> listaValores = new ArrayList<Double>();
			for (int j = i; j > (i - 200); j--){
				listaValores.add(listaAcoes.get(j).getDayClosing().doubleValue());
			}
			listaAcoes.get(i).setMinLast200Days(Collections.min(listaValores));
		}
	}

	/**
	 * 
	 * @param listaAcoes
	 */
	public static void populateMaxLast200Days(List<StockQuote> listaAcoes){

		for (int i = 199; i < listaAcoes.size(); i++){
			List<Double> listaValores = new ArrayList<Double>();
			for (int j = i; j > (i - 200); j--){
				listaValores.add(listaAcoes.get(j).getDayClosing().doubleValue());
			}
			listaAcoes.get(i).setMaxLast200Days(Collections.max(listaValores));
		}
	}

	/**
	 * 
	 * @param listaAcoes
	 */
	public static void populateRoc20Days(List<StockQuote> listaAcoes){
		for (int i = 19; i < listaAcoes.size(); i++){
			listaAcoes.get(i).setRoc20Days(listaAcoes.get(i).getDayClosing() - listaAcoes.get(i - 19).getDayClosing());
		}
	}


	public static void populate200DaysEMAOfOC20DaysR(List<StockQuote> listaAcoes){

		for (int i = 0; i < listaAcoes.size(); i++){
			if (listaAcoes.get(i).getRoc20Days() == null){
				continue;
			}
			else{
				listaAcoes.get(i).set_200DaysEMAOfRoc20Days(listaAcoes.get(i).getRoc20Days());
			}

			if (listaAcoes.get(i-1).get_200DaysEMAOfRoc20Days() != null){
				double mediaMovelExponencialAnterior = listaAcoes.get(i-1).get_200DaysEMAOfRoc20Days().doubleValue();
				double roc20DiasCorrente = listaAcoes.get(i).get_200DaysEMAOfRoc20Days().doubleValue();
				listaAcoes.get(i).set_200DaysEMAOfRoc20Days(Utils.mediaMovelExponencial(mediaMovelExponencialAnterior, roc20DiasCorrente, 200));
			}
		}
	}


//	public static void testRSI(double[] stockQuotes){
//		Core c = new Core();
//
//		double[] output = new double[stockQuotes.length];
//		MInteger begin = new MInteger();
//		MInteger length = new MInteger();
//
//
//		RetCode retCode = c.rsi(0, stockQuotes.length-1, stockQuotes, 14, begin, length, output);
//
//		if (retCode == RetCode.Success)
//		{
//			System.out.println("Output Begin:" + begin.value);
//			System.out.println("Output Length:" + length.value);
//
//			for (int i = 0; i < begin.value + length.value; i++)
//			{
//				StringBuilder line = new StringBuilder();
//				line.append("Period #");
//				line.append(i + 1);
//				line.append(" close= ");
//				line.append(stockQuotes[i]);
//				line.append(" RSI=");
//				line.append(output[i]);
//				System.out.println(line);
//			}
//		}
//		else{
//			System.out.println("Error! " + retCode);
//		}
//	}


	private static List<Double> RSI(int n, List<StockQuote> listaAcoes) {

		List<Double> listaRSI = new ArrayList<Double>();
		
		for (int j = 0; j < listaAcoes.size(); j++){
			double rsi;
			if (j < n){
				listaRSI.add(null);
				continue;
			}
			
			double U = 0.0;
			double D = 0.0;
			for (int i = j; i > j-n; i--) {
				StockQuote stockDiaAnterior = listaAcoes.get(i-1);
				StockQuote stockDiaAtual = listaAcoes.get(i);

				double change = stockDiaAtual.getDayClosing() - stockDiaAnterior.getDayClosing();

				if (change > 0) {
					U += change;
				} else {
					D += Math.abs(change);
				}
			}
			
			// catch division by zero
			if(D == 0 || (1 + (U / D)) == 0) {
				rsi = 0.0;
			}
			
			rsi = 100 - (100 / (1 + (U / D)));
			
//			rsi = 100 - (100 * D / (U + D));
			
			listaRSI.add(rsi);	
		}

		return listaRSI;
	}

	
	public static void populateRSI14Days(List<StockQuote> listaAcoes){

		List<Double> listaRSI = RSI(14, listaAcoes);
		
		for (int i = 0; i < listaRSI.size(); i++){
			listaAcoes.get(i).setRSI14Days(listaRSI.get(i));
		}
	}
	
	
	public static void populateRSI5Days(List<StockQuote> listaAcoes){

		List<Double> listaRSI = RSI(5, listaAcoes);
		
		for (int i = 0; i < listaRSI.size(); i++){
			listaAcoes.get(i).setRSI5Days(listaRSI.get(i));
		}
	}

	
	public static void populateMoreThan10DaysBelowIbovespa(List<StockQuote> listaAcao) throws Exception {
		if (listaAcao.size() == 0){
			return;
		}
		
		List<StockQuote> listaIdx = LoadFromDB.load("IBOVESPA", listaAcao.get(0).getDate(), listaAcao.get(listaAcao.size()-1).getDate());
		HashMap<Date, Double> ibovespaMap = Utils.carregaMapDataValores(listaIdx);

		for (int i = 25; i < listaAcao.size(); i++){
			
			boolean last10DaysBelowIbovespa = true;
			for (int j = 0; j < 10; j++){
				StockQuote stockHoje = listaAcao.get(i-j);
				StockQuote stockOntem = listaAcao.get(i-15-j);

				if ( ( (stockHoje.getDayClosing()/stockOntem.getDayClosing()) / (ibovespaMap.get(stockHoje.getDate()) / ibovespaMap.get(stockOntem.getDate())) ) >= 1){
					last10DaysBelowIbovespa = false;
					break;
				}
			}
			listaAcao.get(i).setMoreThan10DaysBelowIbovespa(last10DaysBelowIbovespa);
		}
	}
	

	public static void main(String[] args) throws Exception {

//		double[] stockQuotes = new double[25];
		//		stockQuotes[24] = 18.62;
		//		stockQuotes[23] = 19.13;
		//		stockQuotes[22] = 19.03;
		//		stockQuotes[21] = 19.8;
		//		stockQuotes[20] = 20.47;
		//		stockQuotes[19] = 20.44;
		//		stockQuotes[18] = 20.78;
		//		stockQuotes[17] = 20.61;
		//		stockQuotes[16] = 21.18;
		//		stockQuotes[15] = 21.68;
		//		stockQuotes[14] = 21.24;
		//		stockQuotes[13] = 20.85;
		//		stockQuotes[12] = 20.8;
		//		stockQuotes[11] = 21.53;
		//		stockQuotes[10] = 21.35;
		//		stockQuotes[9] = 22.1;
		//		stockQuotes[8] = 21.75;
		//		stockQuotes[7] = 21.54;
		//		stockQuotes[6] = 21.67;
		//		stockQuotes[5] = 22.05;
		//		stockQuotes[4] = 22.25;
		//		stockQuotes[3] = 22.5;
		//		stockQuotes[2] = 22.62;
		//		stockQuotes[1] = 22.8;
		//		stockQuotes[0] = 22.8;

//		stockQuotes[0] = 18.62;
//		stockQuotes[1] = 19.13;
//		stockQuotes[2] = 19.03;
//		stockQuotes[3] = 19.8;
//		stockQuotes[4] = 20.47;
//		stockQuotes[5] = 20.44;
//		stockQuotes[6] = 20.78;
//		stockQuotes[7] = 20.61;
//		stockQuotes[8] = 21.18;
//		stockQuotes[9] = 21.68;
//		stockQuotes[10] = 21.24;
//		stockQuotes[11] = 20.85;
//		stockQuotes[12] = 20.8;
//		stockQuotes[13] = 21.53;
//		stockQuotes[14] = 21.35;
//		stockQuotes[15] = 22.1;
//		stockQuotes[16] = 21.75;
//		stockQuotes[17] = 21.54;
//		stockQuotes[18] = 21.67;
//		stockQuotes[19] = 22.05;
//		stockQuotes[20] = 22.25;
//		stockQuotes[21] = 22.5;
//		stockQuotes[22] = 22.62;
//		stockQuotes[23] = 22.8;
//		stockQuotes[24] = 22.8;

		
		Calendar start = new GregorianCalendar(2011, Calendar.OCTOBER, 03);
		Date startDate = start.getTime();
		Calendar end = new GregorianCalendar(2020, Calendar.MARCH, 30);
		Date endDate = end.getTime();
		
		
		List<StockQuote> listaAcoes = LoadFromDB.load("PETR4", startDate, endDate);
		
		double[] stockQuotes = new double[18];
				stockQuotes[17] = 18.62;
				stockQuotes[16] = 19.13;
				stockQuotes[15] = 19.03;
				stockQuotes[14] = 19.8;
				stockQuotes[13] = 20.47;
				stockQuotes[12] = 20.44;
				stockQuotes[11] = 20.78;
				stockQuotes[10] = 20.61;
				stockQuotes[9] = 21.18;
				stockQuotes[8] = 21.68;
				stockQuotes[7] = 21.24;
				stockQuotes[6] = 20.85;
				stockQuotes[5] = 20.8;
				stockQuotes[4] = 21.53;
				stockQuotes[3] = 21.35;
				stockQuotes[2] = 22.1;
				stockQuotes[1] = 21.75;
				stockQuotes[0] = 21.54;
		
				
		for (int i =0; i < stockQuotes.length; i++){
			StockQuote s = new StockQuote();
			s.setDayClosing(stockQuotes[i]);
			listaAcoes.add(s);
		}

		//		testRSI(stockQuotes);
		RSI(14, listaAcoes);
	}
}

