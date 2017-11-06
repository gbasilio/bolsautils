package br.gustavo.test.strategies.singlestock;

import java.util.Date;
import java.util.List;

import br.gustavo.db.LoadFromDB;
import br.gustavo.types.StockQuote;

public class CurrentAgainstHistoricalValueStrategy {

	// este valores foram obtidos a partir da regressao exponencial do grafico do fechamento anual do ibovespa em dolar desde 1967
	// equacao no formato:
	// startingPointDollarIndex * annualIncreaseDollarIndex ^ (ano - startingYearDollarIndex) 
	private static int startingYearDollarIndex = 1967;
	private static double startingPointDollarIndex = 855d;
	private static double annualIncreaseDollarIndex = 1.07562625d;
	
	// este valores foram obtidos a partir da regressao exponencial do grafico do fechamento anual do ibovespa em moeda nacional corrigido pelo IPC-SP
	// equacao no formato:
	// startingPointBRLIndex * annualIncreaseBRLIndex ^ (ano - startingYearBRLIndex)
	private static int startingYearBRLIndex = 1968;
	private static double startingPointBRLIndex = 777.4967d;
	private static double annualIncreaseBRLIndex = 1.108045239d;
	

	/**
	 * 
	 * @param listaAcoes
	 * @throws Exception
	 */
	public static void checkCurrentAgainstHistoricalValueDollarIndex(List<StockQuote> listaAcoes) throws Exception{
		if (listaAcoes == null || listaAcoes.size() == 0){
			return;
		}

		// carrega cotacoes do ibovespa para medir contra o valor esperado para o indice de acordo com a valorizacao historica
		Date startDate = listaAcoes.get(0).getDate();
		Date endDate = listaAcoes.get(listaAcoes.size()-1).getDate();
		List<StockQuote> listaIbovespa = LoadFromDB.load("IBOV11", startDate, endDate);

		for (StockQuote ibovespa : listaIbovespa) {
			int year  = ibovespa.getDate().getYear() + 1900;
			double historicalValue = startingPointDollarIndex * Math.pow(annualIncreaseDollarIndex, (year - startingYearDollarIndex));
			// obtem o valor do dolar do dia para achar a cotacao do ibovespa em dolar
			double dollarValue = LoadFromDB.loadDollarQuote(ibovespa.getDate());
			double dollarIbovespa = ibovespa.getDayClosing() / dollarValue;
			double ratio = (( dollarIbovespa / historicalValue) - 1) * 100;
			System.out.println(ibovespa.getDate() +  " [value=" + dollarIbovespa + "] [historicalValue=" + historicalValue + "] [ratio=" + ratio  + "]");
		}
			
	}
	
	
	/**
	 * 
	 * @param listaAcoes
	 * @throws Exception
	 */
	public static void checkCurrentAgainstHistoricalValueBRLIndex(List<StockQuote> listaAcoes) throws Exception{
		if (listaAcoes == null || listaAcoes.size() == 0){
			return;
		}

		// carrega cotacoes do ibovespa para medir contra o valor esperado para o indice de acordo com a valorizacao historica
		Date startDate = listaAcoes.get(0).getDate();
		Date endDate = listaAcoes.get(listaAcoes.size()-1).getDate();
		List<StockQuote> listaIbovespa = LoadFromDB.load("IBOV11", startDate, endDate);

		for (StockQuote ibovespa : listaIbovespa) {
			int year  = ibovespa.getDate().getYear() + 1900;
			double historicalValue = startingPointBRLIndex * Math.pow(annualIncreaseBRLIndex, (year - startingYearBRLIndex));
			double ratio = ((ibovespa.getDayClosing() / historicalValue) - 1) * 100;
			System.out.println(ibovespa.getDate() +  " [value=" + ibovespa.getDayClosing() + "] [historicalValue=" + historicalValue + "] [ratio=" + ratio  + "]");
		}
			
	}
}
