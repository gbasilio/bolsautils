package br.gustavo.monitor.strategies;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import br.gustavo.db.LoadFromDB;
import br.gustavo.db.StockDbUtils;
import br.gustavo.indicators.StockQuoteListUtils;
import br.gustavo.types.StockQuote;

public class CopyOfTrendLinesForBuyingAndBollingerForSellingStrategy {

	private static double lucratividadeEsperada;
	private static double stopLossThreshold;

	private static GregorianCalendar dataDaAnalise;

	public static void main(String[] args) throws Exception{
		// define data da analise
		dataDaAnalise = new GregorianCalendar();
		dataDaAnalise.set(Calendar.HOUR, 0);
		dataDaAnalise.set(Calendar.MINUTE, 0);
		dataDaAnalise.set(Calendar.SECOND, 0);
		dataDaAnalise.set(Calendar.MILLISECOND, 0);
		if (dataDaAnalise.get(Calendar.DAY_OF_WEEK) == Calendar.TUESDAY ||
				dataDaAnalise.get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY ||
						dataDaAnalise.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY ||
								dataDaAnalise.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY){
			dataDaAnalise.add(Calendar.DATE, -1);
		}
		else if (dataDaAnalise.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY){
			dataDaAnalise.add(Calendar.DATE, -3);
		}
		else{
			throw new Exception("Nao existe mercado aos sabados e domingos!");
		}

		// carrega acoes a monitorar
		List<String> listaStocksToMonitor = findStocksToMonitor();

		// define limite de datas 
		// endDate = hoje
		Date endDate = dataDaAnalise.getTime();
		// startDate = hoje - 300 dias
		dataDaAnalise.add(Calendar.DATE, -300);
		Date startDate = dataDaAnalise.getTime();

		System.out.println("Relat�rio de " + startDate + " a " + endDate + "\n\n");

		// bull market
		System.out.println("<<<----------------------->>");
		System.out.println("-- BULL MARKET STRATEGY --");
		System.out.println("<<<----------------------->>\n");
		double total = 0;
		for (String simbolo: listaStocksToMonitor){
			List<StockQuote> listaAcoes = LoadFromDB.load(simbolo, startDate, endDate);

			carregaIndicadores(listaAcoes);
			total += bullMarketStrategy(listaAcoes);
		}
		System.out.println("Rendimento total = " + total / listaStocksToMonitor.size() + "\n");

		// sideways market
		System.out.println("<<<------------------------->>");
		System.out.println("-- SIDEWAYS MARKET STRATEGY --");
		System.out.println("<<<------------------------->>\n");
		total = 0;
		for (String simbolo: listaStocksToMonitor){
			List<StockQuote> listaAcoes = LoadFromDB.load(simbolo, startDate, endDate);

			carregaIndicadores(listaAcoes);
			total += sidewaysMarketStrategy(listaAcoes);
		}
		System.out.println("Rendimento total = " + total / listaStocksToMonitor.size());
	}


	private static List<String> findStocksToMonitor() throws Exception{
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		String query = "select symbol from stock_fundamentals where " +
				"p_l_ratio > 0 and " +
				"liq_2_meses > 1000000 and " +
				"liquidez_corr >= 0.9 and " +
				"cresc_receita_ult_5_anos > 0.15 and " +
				"data = to_timestamp('" + sdf.format(dataDaAnalise.getTime()) + "', 'DD/MM/YYYY') and " +
				"divbruta_patrimonio_ratio < 0.6 " +
				"order by (roe/p_l_ratio) DESC";

		Connection conn = StockDbUtils.getConnection();
		PreparedStatement ps = conn.prepareStatement(query);
		ResultSet rs = ps.executeQuery();

		List<String> retorno = new ArrayList<String>();
		while(rs.next()){
			retorno.add(rs.getString("symbol"));
		}

		return retorno;
	}


	/**
	 * 
	 * @param listaAcoes
	 * @return
	 */
	private static List<Integer> findShortTermDipIndices(List<StockQuote> listaAcoes){
		Double[] valoresAnteriores = new Double[3];
		List<Integer> listaIndicesVale = new ArrayList<Integer>();
		for (int i = 0; i < listaAcoes.size(); i++){
			StockQuote stock = listaAcoes.get(i);

			valoresAnteriores[0] = valoresAnteriores[1];
			valoresAnteriores[1] = valoresAnteriores[2];
			valoresAnteriores[2] = stock.getDayClosing();

			if (valoresAnteriores[0] == null || 
					valoresAnteriores[1] == null || 
					valoresAnteriores[2] == null){
				continue;
			}


			if (valoresAnteriores[1] < valoresAnteriores[0] && 
					valoresAnteriores[1] < valoresAnteriores[2]){
				listaIndicesVale.add(i-1);
			}
		}

		return listaIndicesVale;
	}


	/**
	 * 
	 * @param listaAcoes
	 * @param dataAvaliada
	 * @return
	 */
	private static boolean shortestTermHighTrendLineFromDips(List<StockQuote> listaAcoes, Date dataAvaliada){
		if (listaAcoes == null || listaAcoes.size() == 0){
			System.out.println("Nothing to do ...");
			return false;
		}

		List<Integer> listaIndicesVale = findShortTermDipIndices(listaAcoes);

		StockQuote valeAnterior = null;
		StockQuote valeAtual = null;
		for (Integer indiceVale: listaIndicesVale){
			valeAnterior = valeAtual;
			valeAtual = listaAcoes.get(indiceVale);
			if (valeAnterior == null || valeAtual == null){
				continue;
			}

			if (valeAnterior.getDayClosing() < valeAtual.getDayClosing()){
				//				System.out.println("[Tendencia de alta] de " + stockAntesDoAnterior.getDate() + " a " + stock.getDate());
				if (dataAvaliada.equals(valeAtual.getDate())){
					return true;
				}
			}
		}		
		return false;
	}


	/**
	 * 
	 * @param listaAcoes
	 */
	private static void carregaIndicadores(List<StockQuote> listaAcoes){
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
	}


	/**
	 * 
	 * @param listaAcoes
	 * @return
	 */
	private static double bullMarketStrategy(List<StockQuote> listaAcoes){
		lucratividadeEsperada = 1.20;
		stopLossThreshold = 0.75;

		return strategy(listaAcoes);
	}

	private static double sidewaysMarketStrategy(List<StockQuote> listaAcoes){
		lucratividadeEsperada = 1.06;
		stopLossThreshold = 0.75;

		return strategy(listaAcoes);
	}


	/**
	 * 
	 * @param listaAcoes
	 * @return
	 */
	private static double strategy(List<StockQuote> listaAcoes){
		if (listaAcoes == null || listaAcoes.size() == 0){
			System.out.println("Nothing to do ...");
			return 1;
		}

		double total = 0;
		double totalCustosTx = 0;
		double precoCompra = 0;
		double precoPrimeiraCompra = 0;
		double precoMaxJaAlcancado = 0;
		boolean compraProximaSessao = false;
		System.out.println("*** " + listaAcoes.get(0).getSymbol() + " ***");
		for (int i = 0; i < listaAcoes.size(); i++){
			StockQuote stock = listaAcoes.get(i);

			String marcadorDeDataAtual = "";
			if (i == listaAcoes.size()-1){
				marcadorDeDataAtual = "  <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<,";
			}

			if (stock.getBollingerUpperBand() == null){
				continue;
			}

			if (compraProximaSessao){
				precoCompra = stock.getDayClosing();
				precoMaxJaAlcancado = precoCompra;
				if (precoPrimeiraCompra == 0){
					precoPrimeiraCompra = precoCompra;
				}
				compraProximaSessao = false;
				System.out.println("[COMPRA]  data = "+ stock.getDate() + " / precoCompra = " + stock.getDayClosing() + marcadorDeDataAtual);
			}

			if (shortestTermHighTrendLineFromDips(listaAcoes, stock.getDate()) && precoCompra == 0){
				// se estou vendido, ent�o compro
				compraProximaSessao = true;
			}
			// alcan�ou lucratividade esperada e est� sobrevendida
			else if (stock.getDayClosing() >= (precoCompra * lucratividadeEsperada ) && precoCompra > 0
					&& stock.getDayClosing() >= stock.getBollingerUpperBand() && stock.getRSI14Days() >= 70){
				//				System.out.println(stock.getDayClosing() + " / " + stock.getBollingerUpperBand() + " / " + stock.getRSI14Days());
				total += stock.getDayClosing() - precoCompra;
				totalCustosTx +=50;
				precoCompra = 0;
				precoMaxJaAlcancado = 0;
				System.out.println("[VENDA]  data = "+ stock.getDate() + " / precoVenda = " + stock.getDayClosing() + marcadorDeDataAtual);
			}
			// stop loss
			else if (stock.getDayClosing() <= (precoMaxJaAlcancado * stopLossThreshold) && precoCompra > 0){
				total += stock.getDayClosing() - precoCompra;
				totalCustosTx +=50;
				precoCompra = 0;
				System.out.println("[VENDA] data = "+ stock.getDate() + " / precoVenda = " + stock.getDayClosing() + " #stop loss#" + marcadorDeDataAtual);		
			}

			// ajusta valor maximo ja alcan�ado para ter um stop loss movel
			if (stock.getDayClosing() > precoMaxJaAlcancado && precoCompra > 0){
				precoMaxJaAlcancado = stock.getDayClosing();
			}
		}

		//		System.out.println("\nTotal custos de transa��o = " + totalCustosTx);
		//		System.out.println("Total $ = " + total);
		System.out.println("Rentabilidade = " + ((total / precoPrimeiraCompra) + 1));
		System.out.println("");

		if (precoPrimeiraCompra > 0){
			return ((total / precoPrimeiraCompra) + 1);
		}
		else{
			return 1;
		}
	}

}
