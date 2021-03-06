package br.gustavo.test.strategies.singlestock;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import br.gustavo.db.LoadFromDB;
import br.gustavo.indicators.Utils;
import br.gustavo.types.StockQuote;

public class TrendLineStrategies {

	/**
	 * 
	 * @param listaAcoes
	 */
	private static List<Integer> findShortTermPeakIndices(List<StockQuote> listaAcoes){
		Double[] valoresAnteriores = new Double[3];
		List<Integer> listaIndicesPico = new ArrayList<Integer>();
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


			if (valoresAnteriores[1] > valoresAnteriores[0] && 
					valoresAnteriores[1] > valoresAnteriores[2]){
//				System.out.println("Pico encontrado = " + valoresAnteriores[1] + " [" + listaAcoes.get(i-1).getDate() + "]");
				listaIndicesPico.add(i-1);
			}
		}

		return listaIndicesPico;
	}
	
	
	/**
	 * 
	 * @param listaAcoes
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
//				System.out.println("Vale encontrado = " + valoresAnteriores[1] + " [" + listaAcoes.get(i-1).getDate() + "]");
				listaIndicesVale.add(i-1);
			}
		}

		return listaIndicesVale;
	}

	/**
	 * 
	 * @param listaAcoes
	 * @return
	 */
	public static List<Integer> findLongTermDipIndices(List<StockQuote> listaAcoes){
		Double[] valoresAnteriores = new Double[9];
		List<Integer> listaIndicesVale = new ArrayList<Integer>();
		for (int i = 0; i < listaAcoes.size(); i++){
			StockQuote stock = listaAcoes.get(i);

			valoresAnteriores[0] = valoresAnteriores[1];
			valoresAnteriores[1] = valoresAnteriores[2];
			valoresAnteriores[2] = valoresAnteriores[3];
			valoresAnteriores[3] = valoresAnteriores[4];
			valoresAnteriores[4] = valoresAnteriores[5];
			valoresAnteriores[5] = valoresAnteriores[6];
			valoresAnteriores[6] = valoresAnteriores[7];
			valoresAnteriores[7] = valoresAnteriores[8];
			valoresAnteriores[8] = stock.getDayClosing();

			if (valoresAnteriores[0] == null || 
					valoresAnteriores[1] == null || 
					valoresAnteriores[2] == null ||
					valoresAnteriores[3] == null || 
					valoresAnteriores[4] == null ||
					valoresAnteriores[5] == null || 
					valoresAnteriores[6] == null ||
					valoresAnteriores[7] == null ||
					valoresAnteriores[8] == null){
				continue;
			}


			if (valoresAnteriores[1] <= valoresAnteriores[0] && 
				valoresAnteriores[2] <= valoresAnteriores[1] &&
				valoresAnteriores[3] <= valoresAnteriores[2] &&
				valoresAnteriores[4] <= valoresAnteriores[3] &&
				valoresAnteriores[4] <= valoresAnteriores[5] &&
				valoresAnteriores[5] <= valoresAnteriores[6] &&
				valoresAnteriores[6] <= valoresAnteriores[7] &&
				valoresAnteriores[7] <= valoresAnteriores[8]){
				System.out.println("Vale encontrado = " + valoresAnteriores[4] + " [" + listaAcoes.get(i-4).getDate() + "] -> draw down = " + ((valoresAnteriores[4] / valoresAnteriores[0]) - 1) * 100 );
				listaIndicesVale.add(i-4);
			}
		}

		return listaIndicesVale;
	}


	
	/**
	 * 
	 * @param listaAcoes
	 */
	public static boolean shortTermLowTrendLineFromPeaks(List<StockQuote> listaAcoes, Date dataAvaliada){
		if (listaAcoes == null || listaAcoes.size() == 0){
			System.out.println("Nothing to do ...");
			return false;
		}

		List<Integer> listaIndicesPico = findShortTermPeakIndices(listaAcoes);

		StockQuote picoAnterior = null;
		StockQuote picoAntesDoAnterior = null;
		StockQuote picoAtual = null;
		for (Integer indicePico: listaIndicesPico){
			picoAntesDoAnterior = picoAnterior;
			picoAnterior = picoAtual;
			picoAtual = listaAcoes.get(indicePico);
			if (picoAntesDoAnterior == null || picoAnterior == null || picoAtual == null){
				continue;
			}

			if (picoAntesDoAnterior.getDayClosing() >= picoAnterior.getDayClosing() && 
					picoAnterior.getDayClosing() >= picoAtual.getDayClosing()){
				// System.out.println("[Tendencia de baixa] de " + stockAntesDoAnterior.getDate() + " a " + stock.getDate());
				if (dataAvaliada.equals(picoAtual.getDate())){
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
	public static boolean shortTermHighTrendLineFromDips(List<StockQuote> listaAcoes, Date dataAvaliada){
		if (listaAcoes == null || listaAcoes.size() == 0){
			System.out.println("Nothing to do ...");
			return false;
		}

		List<Integer> listaIndicesVale = findShortTermDipIndices(listaAcoes);

		StockQuote picoAnterior = null;
		StockQuote picoAntesDoAnterior = null;
		StockQuote picoAtual = null;
		for (Integer indiceVale: listaIndicesVale){
			picoAntesDoAnterior = picoAnterior;
			picoAnterior = picoAtual;
			picoAtual = listaAcoes.get(indiceVale);
			if (picoAntesDoAnterior == null || picoAnterior == null || picoAtual == null){
				continue;
			}

			if (picoAntesDoAnterior.getDayClosing() <= picoAnterior.getDayClosing() && 
					picoAnterior.getDayClosing() <= picoAtual.getDayClosing()){
				//				System.out.println("[Tendencia de alta] de " + stockAntesDoAnterior.getDate() + " a " + stock.getDate());
				if (dataAvaliada.equals(picoAtual.getDate())){
					return true;
				}
			}
		}		
		return false;
	}

	/**
	 * Esta versão é boa mas está errada! Não consigo comprar no mesmo dia em que descubro a tendência! 
	 * Produz bons resultados, comparáveis à estratégia de longo prazo de comprar na baixa e vender na alta.
	 * @param listaAcoes
	 */
	public static void tradeWithTrendLinesFromPeaksAndDipsShortTermStrategyWrong(List<StockQuote> listaAcoes){
		if (listaAcoes == null || listaAcoes.size() == 0){
			System.out.println("Nothing to do ...");
			return;
		}

		double total = 0;
		double precoCompra = 0;
		for (StockQuote stock: listaAcoes){
			if (shortTermHighTrendLineFromDips(listaAcoes, stock.getDate())){
//				System.out.println(stock.getDate() + " = Tendência de alta");
				// se estou vendido, então compro
				if (precoCompra == 0){
					precoCompra = stock.getDayClosing();
					System.out.println("data = "+ stock.getDate() + " / precoCompra = " + stock.getDayClosing());
				}
			}
			else if (shortTermLowTrendLineFromPeaks(listaAcoes, stock.getDate())){
//				System.out.println(stock.getDate() + " = Tendência de baixa");
				// se estou comprado, então vendo
				if (precoCompra > 0){
					total += stock.getDayClosing() - precoCompra;
					precoCompra = 0;
					System.out.println("data = "+ stock.getDate() + " / precoVenda = " + stock.getDayClosing());
				}
			}

			// stop loss
			// se estiver comprado e preco atual for menor que 90% do preco de compra, então vendo para minimizar prejuizo
			if (precoCompra > 0){
				if (stock.getDayClosing() < (precoCompra*0.90)){
					total += stock.getDayClosing() - precoCompra;
					precoCompra = 0;
					System.out.println("data = "+ stock.getDate() + " / precoVenda = " + stock.getDayClosing() + " - #stop loss#");		
				}
			}
		}
		
		System.out.println("Total = " + total);
	}
	
	
	/**
	 * Este está certo!
	 * 
	 * @param listaAcoes
	 */
	public static void tradeWithTrendLinesFromPeaksAndDipsShortTermStrategy(List<StockQuote> listaAcoes){
		if (listaAcoes == null || listaAcoes.size() == 0){
			System.out.println("Nothing to do ...");
			return;
		}

		double total = 0;
		double precoCompra = 0;
		double precoPrimeiraCompra = 0;
		int totalCustosTx = 0;
		boolean compraProximaSessao = false;
		boolean vendeProximaSessao = false;
		for (StockQuote stock: listaAcoes){
			if (compraProximaSessao){
				precoCompra = stock.getDayClosing();
				if (precoPrimeiraCompra == 0){
					precoPrimeiraCompra = precoCompra;
				}
				System.out.println("data = "+ stock.getDate() + " / precoCompra = " + stock.getDayClosing());
				compraProximaSessao = false;
			}
			else if (vendeProximaSessao){
				total += stock.getDayClosing() - precoCompra;
				precoCompra = 0;
				System.out.println("data = "+ stock.getDate() + " / precoVenda = " + stock.getDayClosing());
				totalCustosTx += 50;
				vendeProximaSessao = false;
			}
			
			if (shortTermHighTrendLineFromDips(listaAcoes, stock.getDate())){
//				System.out.println(stock.getDate() + " = Tendência de alta");
				// se estou vendido, então compro
				if (precoCompra == 0){
					compraProximaSessao = true;
				}
			}
			else if (shortTermLowTrendLineFromPeaks(listaAcoes, stock.getDate())){
//				System.out.println(stock.getDate() + " = Tendência de baixa");
				// se estou comprado, então vendo
				if (precoCompra > 0){
					vendeProximaSessao = true;
				}
			}

			// stop loss
			// se estiver comprado e preco atual for menor que 90% do preco de compra, então vendo para minimizar prejuizo
			if (precoCompra > 0){
				if (stock.getDayClosing() < (precoCompra*0.90)){
					total += stock.getDayClosing() - precoCompra;
					precoCompra = 0;
					System.out.println("data = "+ stock.getDate() + " / precoVenda = " + stock.getDayClosing() + " - #stop loss#");		
				}
			}
		}
		
		System.out.println("[" + listaAcoes.get(0).getSymbol() + "] Total custos de transação = " + totalCustosTx);
		System.out.println("[" + listaAcoes.get(0).getSymbol() + "] Total = " + total);
		System.out.println("[" + listaAcoes.get(0).getSymbol() + "] Fator de multiplicação = " + ((total / precoPrimeiraCompra) + 1));
	}
	
	/**
	 * 
	 * @param listaAcoes
	 */
	public static void tradeWithHighTrendLinesFromDipsForBuyingShortTermStrategy(List<StockQuote> listaAcoes){
		if (listaAcoes == null || listaAcoes.size() == 0){
			System.out.println("Nothing to do ...");
			return;
		}

		double total = 0;
		double precoCompra = 0;
		double precoPrimeiraCompra = 0;
		int totalCustosTx = 0;
		boolean compraProximaSessao = false;
		boolean vendeProximaSessao = false;
		for (StockQuote stock: listaAcoes){
			if (compraProximaSessao){
				precoCompra = stock.getDayClosing();
				if (precoPrimeiraCompra == 0){
					precoPrimeiraCompra = precoCompra;
				}
				System.out.println("data = "+ stock.getDate() + " / precoCompra = " + stock.getDayClosing());
				compraProximaSessao = false;
			}
			
			if (vendeProximaSessao){
				total += stock.getDayClosing() - precoCompra;
				precoCompra = 0;
				System.out.println("data = "+ stock.getDate() + " / precoVenda = " + stock.getDayClosing());
				totalCustosTx += 50;
				vendeProximaSessao = false;
			}
			
			if (shortestTermHighTrendLineFromDips(listaAcoes, stock.getDate())){
//				System.out.println(stock.getDate() + " = Tendência de alta");
				// se estou vendido, então compro
				if (precoCompra == 0){
					compraProximaSessao = true;
				}
			}
			
			if (stock.getDayClosing() >= (precoCompra * 1.06)){
//				System.out.println(stock.getDate() + " = Tendência de baixa");
				// se estou comprado, então vendo
				if (precoCompra > 0){
					vendeProximaSessao = true;
				}
			}

			// stop loss
			// se estiver comprado e preco atual for menor que 90% do preco de compra, então vendo para minimizar prejuizo
			if (precoCompra > 0){
				if (stock.getDayClosing() <= (precoCompra * 0.90)){
					vendeProximaSessao = true;
					System.out.println("#stop loss#");		
				}
			}
		}
		
		System.out.println("[" + listaAcoes.get(0).getSymbol() + "] Total custos de transação = " + totalCustosTx);
		System.out.println("[" + listaAcoes.get(0).getSymbol() + "] Total = " + total);
		System.out.println("[" + listaAcoes.get(0).getSymbol() + "] Fator de multiplicação = " + ((total / precoPrimeiraCompra) + 1));
	}
	
	
	
	
	
	
	
	
	
	
	/*
	 * 
	 * Shortest term
	 * 
	 * 
	 */
	
	/**
	 * 
	 * @param listaAcoes
	 */
	public static boolean shortestTermLowTrendLineFromPeaks(List<StockQuote> listaAcoes, Date dataAvaliada){
		if (listaAcoes == null || listaAcoes.size() == 0){
			System.out.println("Nothing to do ...");
			return false;
		}

		List<Integer> listaIndicesPico = findShortTermPeakIndices(listaAcoes);

		StockQuote picoAnterior = null;
		StockQuote picoAtual = null;
		for (Integer indicePico: listaIndicesPico){
			picoAnterior = picoAtual;
			picoAtual = listaAcoes.get(indicePico);
			if (picoAnterior == null || picoAtual == null){
				continue;
			}

			if (picoAnterior.getDayClosing() > picoAtual.getDayClosing()){
				// System.out.println("[Tendencia de baixa] de " + stockAntesDoAnterior.getDate() + " a " + stock.getDate());
				if (dataAvaliada.equals(picoAtual.getDate())){
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
	public static boolean shortestTermHighTrendLineFromDips(List<StockQuote> listaAcoes, Date dataAvaliada){
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
	 * Esta versão é super boa mas está errada! Não consigo comprar no mesmo dia em que descubro a tendência!
	 * Produz ótimos resultados. Eu escolheria ela para testar uma estratégia de curto prazo.
	 * @param listaAcoes
	 */
	public static void tradeWithTrendLinesFromPeaksAndDipsShortestTermStrategyWrong(List<StockQuote> listaAcoes){
		if (listaAcoes == null || listaAcoes.size() == 0){
			System.out.println("Nothing to do ...");
			return;
		}

		double total = 0;
		double totalCustosTx = 0;
		double precoCompra = 0;
		double precoPrimeiraCompra = 0;
		for (StockQuote stock: listaAcoes){
			if (shortestTermHighTrendLineFromDips(listaAcoes, stock.getDate())){
//				System.out.println(stock.getDate() + " = Tendência de alta");
				// se estou vendido, então compro
				if (precoCompra == 0){
					precoCompra = stock.getDayClosing();
					if (precoPrimeiraCompra == 0){
						precoPrimeiraCompra = precoCompra;
					}
					System.out.println("data = "+ stock.getDate() + " / precoCompra = " + stock.getDayClosing());
				}
			}
			else if (shortestTermLowTrendLineFromPeaks(listaAcoes, stock.getDate())){
//				System.out.println(stock.getDate() + " = Tendência de baixa");
				// se estou comprado, então vendo
				if (precoCompra > 0){
					total += stock.getDayClosing() - precoCompra;
					totalCustosTx +=50;
					precoCompra = 0;
					System.out.println("data = "+ stock.getDate() + " / precoVenda = " + stock.getDayClosing());
				}
			}

			// stop loss
			// se estiver comprado e preco atual for menor que 90% do preco de compra (perda máxima = 10%), então vendo para minimizar prejuizo
//			if (precoCompra > 0){
//				if (stock.getDayClosing() < (precoCompra*0.90)){
//					total += stock.getDayClosing() - precoCompra;
//					totalCustosTx +=50;
//					precoCompra = 0;
//					System.out.println("data = "+ stock.getDate() + " / precoVenda = " + stock.getDayClosing() + " - #stop loss#");		
//				}
//			}
		}
		
		System.out.println("[" + listaAcoes.get(0).getSymbol() + "] Total custos de transação = " + totalCustosTx);
		System.out.println("[" + listaAcoes.get(0).getSymbol() + "] Total = " + total);
		System.out.println("[" + listaAcoes.get(0).getSymbol() + "] Fator de multiplicação = " + ((total / precoPrimeiraCompra) + 1));
	}
	
	
	/**
	 * Este está certo!
	 * @param listaAcoes
	 */
	public static double tradeWithTrendLinesFromPeaksAndDipsShortestTermStrategy(List<StockQuote> listaAcoes){
		if (listaAcoes == null || listaAcoes.size() == 0){
			System.out.println("Nothing to do ...");
			return 1;
		}

		double total = 0;
		double totalCustosTx = 0;
		double precoCompra = 0;
		double precoPrimeiraCompra = 0;
		boolean compraProximaSessao = false;
		boolean vendeProximaSessao = false;
		for (StockQuote stock: listaAcoes){
			if (compraProximaSessao){
				precoCompra = stock.getDayClosing();
				if (precoPrimeiraCompra == 0){
					precoPrimeiraCompra = precoCompra;
				}
				compraProximaSessao = false;
				System.out.println("data = "+ stock.getDate() + " / precoCompra = " + stock.getDayClosing());
			}
			if (vendeProximaSessao){
				total += stock.getDayClosing() - precoCompra;
				totalCustosTx +=50;
				precoCompra = 0;
				vendeProximaSessao = false;
				System.out.println("data = "+ stock.getDate() + " / precoVenda = " + stock.getDayClosing());
			}
			
			
			if (shortestTermHighTrendLineFromDips(listaAcoes, stock.getDate())){
//				System.out.println(stock.getDate() + " = Tendência de alta");
				// se estou vendido, então compro
				if (precoCompra == 0){
					compraProximaSessao = true;
				}
			}
			else if (shortestTermLowTrendLineFromPeaks(listaAcoes, stock.getDate())){
//				System.out.println(stock.getDate() + " = Tendência de baixa");
				// se estou comprado, então vendo
				if (precoCompra > 0){
					vendeProximaSessao = true;
				}
			}

			// stop loss
			// se estiver comprado e preco atual for menor que 94% do preco de compra (perda máxima = 6%), então vendo para minimizar prejuizo
//			if (precoCompra > 0){
//				if (stock.getDayClosing() < (precoCompra*0.94)){
//					total += stock.getDayClosing() - precoCompra;
//					totalCustosTx +=50;
//					precoCompra = 0;
//					System.out.println("data = "+ stock.getDate() + " / precoVenda = " + stock.getDayClosing() + " - #stop loss#");		
//				}
//			}
		}
		
		System.out.println("[" + listaAcoes.get(0).getSymbol() + "] Total custos de transação = " + totalCustosTx);
		System.out.println("[" + listaAcoes.get(0).getSymbol() + "] Total = " + total);
		System.out.println("[" + listaAcoes.get(0).getSymbol() + "] Fator de multiplicação = " + ((total / precoPrimeiraCompra) + 1));
		
		if (precoPrimeiraCompra > 0){
			return ((total / precoPrimeiraCompra) + 1);
		}
		else{
			return 1;
		}
	}
	
	
	/**
	 * 
	 * @param listaAcoes
	 */
	public static void verifyActionToTakeShortestTerm(List<StockQuote> listaAcoes){
		if (listaAcoes == null || listaAcoes.size() == 0){
			System.out.println("Nothing to do ...");
			return;
		}

		double precoCompra = 0;
		for (StockQuote stock: listaAcoes){
			if (shortestTermHighTrendLineFromDips(listaAcoes, stock.getDate())){
				// se estou vendido, então compro
				if (precoCompra == 0){
					precoCompra = stock.getDayClosing();
					System.out.println("[" + stock.getSymbol() + "] [COMPRAR] data = "+ stock.getDate() + " / precoCompra = " + stock.getDayClosing());
				}
			}
			else if (shortestTermLowTrendLineFromPeaks(listaAcoes, stock.getDate())){
				// se estou comprado, então vendo
				if (precoCompra > 0){
					precoCompra = 0;
					System.out.println("[" + stock.getSymbol() + "] [VENDER] data = "+ stock.getDate() + " / precoVenda = " + stock.getDayClosing());
				}
			}

			// stop loss (DESATIVADO)
			// se estiver comprado e preco atual for menor que 90% do preco de compra, então vendo para minimizar prejuizo
//			if (precoCompra > 0){
//				if (stock.getDayClosing() < (precoCompra*0.90)){
//					precoCompra = 0;
//					System.out.println("[" + stock.getSymbol() + "] [VENDER] data = "+ stock.getDate() + " / precoVenda = " + stock.getDayClosing() + " - #stop loss#");		
//				}
//			}
		}
	}
	
	
	
	/**
	 * 
	 * @param listaAcoes
	 */
//	public static void tradeWithTrendLinesFromHalfPeaksAndHalfDipsShortestTermStrategy(List<StockQuote> listaAcoes){
//		if (listaAcoes == null || listaAcoes.size() == 0){
//			System.out.println("Nothing to do ...");
//			return;
//		}
//
//		double total = 0;
//		double totalCustosTx = 0;
//		double precoCompra = 0;
//		double precoPrimeiraCompra = 0;
//		for (StockQuote stock: listaAcoes){
//			if (shortestTermHighTrendLineFromHalfDips(listaAcoes, stock.getDate())){
////				System.out.println(stock.getDate() + " = Tendência de alta");
//				// se estou vendido, então compro
//				if (precoCompra == 0){
//					precoCompra = stock.getDayClosing();
//					if (precoPrimeiraCompra == 0){
//						precoPrimeiraCompra = precoCompra;
//					}
//					System.out.println("data = "+ stock.getDate() + " / precoCompra = " + stock.getDayClosing());
//				}
//			}
//			else if (shortestTermLowTrendLineFromHalfPeaks(listaAcoes, stock.getDate())){
////				System.out.println(stock.getDate() + " = Tendência de baixa");
//				// se estou comprado, então vendo
//				if (precoCompra > 0){
//					total += stock.getDayClosing() - precoCompra;
//					totalCustosTx +=50;
//					precoCompra = 0;
//					System.out.println("data = "+ stock.getDate() + " / precoVenda = " + stock.getDayClosing());
//				}
//			}
//
//			// stop loss
//			// se estiver comprado e preco atual for menor que 94% do preco de compra (perda máxima = 6%), então vendo para minimizar prejuizo
//			if (precoCompra > 0){
//				if (stock.getDayClosing() < (precoCompra*0.94)){
//					total += stock.getDayClosing() - precoCompra;
//					totalCustosTx +=50;
//					precoCompra = 0;
//					System.out.println("data = "+ stock.getDate() + " / precoVenda = " + stock.getDayClosing() + " - #stop loss#");		
//				}
//			}
//		}
//		
//		System.out.println("[" + listaAcoes.get(0).getSymbol() + "] Total custos de transação = " + totalCustosTx);
//		System.out.println("[" + listaAcoes.get(0).getSymbol() + "] Total = " + total);
//		System.out.println("[" + listaAcoes.get(0).getSymbol() + "] Fator de multiplicação = " + ((total / precoPrimeiraCompra) + 1));
//	}


	/**
	 * 
	 * @param listaAcoes
	 */
	public static void tradeWithTrendLinesJustForBuyingStrategy(List<StockQuote> listaAcoes){
		if (listaAcoes == null || listaAcoes.size() == 0){
			System.out.println("Nothing to do ...");
			return;
		}

		double total = 0;
		double totalCustosTx = 0;
		double precoCompra = 0;
		double precoPrimeiraCompra = 0;
		double precoMaxJaAlcancado = 0;
		boolean compraProximaSessao = false;
		for (int i = 0; i < listaAcoes.size(); i++){
			StockQuote stock = listaAcoes.get(i);
			
			if (compraProximaSessao){
				precoCompra = stock.getDayClosing();
				precoMaxJaAlcancado = precoCompra;
				if (precoPrimeiraCompra == 0){
					precoPrimeiraCompra = precoCompra;
				}
				compraProximaSessao = false;
				System.out.println("data = "+ stock.getDate() + " / precoCompra = " + stock.getDayClosing());
			}
			
			if (shortestTermHighTrendLineFromDips(listaAcoes, stock.getDate()) && precoCompra == 0){
				// se estou vendido, então compro
				compraProximaSessao = true;
			}
			// alcançou lucratividade esperada
			else if (stock.getDayHighest() >= (precoCompra * 1.05) && precoCompra > 0){
				// se estou comprado, então vendo
				total += stock.getDayHighest() - precoCompra;
				totalCustosTx +=50;
				precoCompra = 0;
				System.out.println("data = "+ stock.getDate() + " / precoVenda = " + stock.getDayHighest());
			}
			// stop loss
			else if (stock.getDayLowest() <= (precoMaxJaAlcancado * 0.90) && precoCompra > 0){
				total += stock.getDayLowest() - precoCompra;
				totalCustosTx +=50;
				precoCompra = 0;
				System.out.println("data = "+ stock.getDate() + " / precoVenda = " + stock.getDayLowest() + " #stop loss#");		
			}
			
			if (stock.getDayHighest() > precoMaxJaAlcancado){
				precoMaxJaAlcancado = stock.getDayHighest();
			}
		}
		
		System.out.println("[" + listaAcoes.get(0).getSymbol() + "] Total custos de transação = " + totalCustosTx);
		System.out.println("[" + listaAcoes.get(0).getSymbol() + "] Total = " + total);
		System.out.println("[" + listaAcoes.get(0).getSymbol() + "] Fator de multiplicação = " + ((total / precoPrimeiraCompra) + 1));
	}
	
	/**
	 * 
	 * @param listaAcoes
	 */
	public static void tradeWithTrendLinesSellNextSessionStrategy(List<StockQuote> listaAcoes){
		if (listaAcoes == null || listaAcoes.size() == 0){
			System.out.println("Nothing to do ...");
			return;
		}

		double total = 0;
		double totalCustosTx = 0;
		double precoCompra = 0;
		double precoPrimeiraCompra = 0;
		boolean compraProximaSessao = false;
		boolean vendeProximaSessao = false;
		for (int i = 0; i < listaAcoes.size(); i++){
			StockQuote stock = listaAcoes.get(i);

			if (compraProximaSessao){
				precoCompra = stock.getDayClosing();
				if (precoPrimeiraCompra == 0){
					precoPrimeiraCompra = precoCompra;
				}
				compraProximaSessao = false;
				System.out.println("data = "+ stock.getDate() + " / precoCompra = " + stock.getDayClosing());
			}
			
			if (vendeProximaSessao){
				total += stock.getDayClosing() - precoCompra;
				totalCustosTx +=50;
				precoCompra = 0;
				vendeProximaSessao = false;
				System.out.println("data = "+ stock.getDate() + " / precoVenda = " + stock.getDayClosing());
			}

			
			if (shortestTermHighTrendLineFromDips(listaAcoes, stock.getDate())){
				// se estou vendido, então compro
				if (precoCompra == 0){
					compraProximaSessao = true;
				}
			}
			
			// se estou comprado, então vendo
			if (precoCompra > 0 && stock.getDayClosing() > precoCompra){
				vendeProximaSessao = true;
			}
			
			// stop loss
			if (precoCompra > 0){
				if (stock.getDayClosing() <= (precoCompra * 0.97)){
					vendeProximaSessao = true;
					System.out.println("#stop loss#");		
				}
			}
		}
		
		System.out.println("[" + listaAcoes.get(0).getSymbol() + "] Total custos de transação = " + totalCustosTx);
		System.out.println("[" + listaAcoes.get(0).getSymbol() + "] Total = " + total);
		System.out.println("[" + listaAcoes.get(0).getSymbol() + "] Fator de multiplicação = " + ((total / precoPrimeiraCompra) + 1));
	}
	
	
	/**
	 * 	 * 
	 * @param listaAcoes
	 */
	public static void tradeWithTrendLinesFromPeaksAndDipsPlusRSIShortTermStrategy(List<StockQuote> listaAcoes){
		if (listaAcoes == null || listaAcoes.size() == 0){
			System.out.println("Nothing to do ...");
			return;
		}

		double total = 0;
		double precoCompra = 0;
		double precoPrimeiraCompra = 0;
		int totalCustosTx = 0;
		boolean compraProximaSessao = false;
		boolean vendeProximaSessao = false;
		for (int i = 1; i < listaAcoes.size(); i++){
			StockQuote stock = listaAcoes.get(i);
			StockQuote stockAnterior = listaAcoes.get(i-1);
			
			if (stock.getRSI14Days() == null || stockAnterior.getRSI14Days() == null){
				continue;
			}
			
			if (compraProximaSessao && (stock.getRSI14Days() + stock.getRSI5Days() < 105)){
				precoCompra = stock.getDayClosing();
				if (precoPrimeiraCompra == 0){
					precoPrimeiraCompra = precoCompra;
				}
				System.out.println("data = "+ stock.getDate() + " / precoCompra = " + stock.getDayClosing());
				compraProximaSessao = false;
			}
			else{
				compraProximaSessao = false;
			}
			
			if (vendeProximaSessao){
				total += stock.getDayClosing() - precoCompra;
				precoCompra = 0;
				System.out.println("data = "+ stock.getDate() + " / precoVenda = " + stock.getDayClosing());
				totalCustosTx += 50;
				vendeProximaSessao = false;
			}
			
			if (shortTermHighTrendLineFromDips(listaAcoes, stock.getDate())){
//				System.out.println(stock.getDate() + " = Tendência de alta");
				// se estou vendido, então compro
//				if (precoCompra == 0 && stock.getRSI14Days() < 30 && stockAnterior.getRSI14Days() < stock.getRSI14Days()){
				if (precoCompra == 0){
					compraProximaSessao = true;
				}
			}
			else if (shortTermLowTrendLineFromPeaks(listaAcoes, stock.getDate())){
//				System.out.println(stock.getDate() + " = Tendência de baixa");
				// se estou comprado, então vendo
				if (precoCompra > 0){
					vendeProximaSessao = true;
				}
			}

			// stop loss
			// se estiver comprado e preco atual for menor que 90% do preco de compra, então vendo para minimizar prejuizo
//			if (precoCompra > 0){
//				if (stock.getDayClosing() < (precoCompra*0.90)){
//					total += stock.getDayClosing() - precoCompra;
//					precoCompra = 0;
//					System.out.println("data = "+ stock.getDate() + " / precoVenda = " + stock.getDayClosing() + " - #stop loss#");		
//				}
//			}
		}
		
		System.out.println("[" + listaAcoes.get(0).getSymbol() + "] Total custos de transação = " + totalCustosTx);
		System.out.println("[" + listaAcoes.get(0).getSymbol() + "] Total = " + total);
		System.out.println("[" + listaAcoes.get(0).getSymbol() + "] Fator de multiplicação = " + ((total / precoPrimeiraCompra) + 1));
	}
	
	
	/**
	 * 
	 * @param listaAcoes
	 * @param maxRSI
	 */
	public static double tradeWithTrendLinesFromPeaksAndDipsPlusRSIShortTermTestVariousRSIValues(List<StockQuote> listaAcoes, int maxRSI){
		if (listaAcoes == null || listaAcoes.size() == 0){
//			System.out.println("Nothing to do ...");
			return 0;
		}

		double total = 0;
		double precoCompra = 0;
		double precoPrimeiraCompra = 0;
		int totalCustosTx = 0;
		boolean compraProximaSessao = false;
		boolean vendeProximaSessao = false;
		for (int i = 1; i < listaAcoes.size(); i++){
			StockQuote stock = listaAcoes.get(i);
			StockQuote stockAnterior = listaAcoes.get(i-1);
			
			if (stock.getRSI14Days() == null || stockAnterior.getRSI14Days() == null){
				continue;
			}
			
			if (compraProximaSessao && (stock.getRSI14Days() + stock.getRSI5Days() < maxRSI)){
				precoCompra = stock.getAdjustedClosing();
				if (precoPrimeiraCompra == 0){
					precoPrimeiraCompra = precoCompra;
				}
//				System.out.println("data = "+ stock.getDate() + " / precoCompra = " + stock.getAdjustedClosing());
				compraProximaSessao = false;
			}
			else{
				compraProximaSessao = false;
			}
			
			if (vendeProximaSessao){
				total += stock.getAdjustedClosing() - precoCompra;
				precoCompra = 0;
//				System.out.println("data = "+ stock.getDate() + " / precoVenda = " + stock.getAdjustedClosing());
				totalCustosTx += 50;
				vendeProximaSessao = false;
			}
			
			if (shortTermHighTrendLineFromDips(listaAcoes, stock.getDate())){
//				System.out.println(stock.getDate() + " = Tendência de alta");
				// se estou vendido, então compro
//				if (precoCompra == 0 && stock.getRSI14Days() < 30 && stockAnterior.getRSI14Days() < stock.getRSI14Days()){
				if (precoCompra == 0){
					compraProximaSessao = true;
				}
			}
			else if (shortTermLowTrendLineFromPeaks(listaAcoes, stock.getDate())){
//				System.out.println(stock.getDate() + " = Tendência de baixa");
				// se estou comprado, então vendo
				if (precoCompra > 0){
					vendeProximaSessao = true;
				}
			}

			// stop loss
			// se estiver comprado e preco atual for menor que 90% do preco de compra, então vendo para minimizar prejuizo
//			if (precoCompra > 0){
//				if (stock.getDayClosing() < (precoCompra*0.90)){
//					total += stock.getDayClosing() - precoCompra;
//					precoCompra = 0;
//					System.out.println("data = "+ stock.getDate() + " / precoVenda = " + stock.getDayClosing() + " - #stop loss#");		
//				}
//			}
		}
		
//		System.out.println("[" + listaAcoes.get(0).getSymbol() + "] Total custos de transação = " + totalCustosTx);
//		System.out.println("[" + listaAcoes.get(0).getSymbol() + "] Total = " + total);
//		System.out.println("[" + listaAcoes.get(0).getSymbol() + "] Fator de multiplicação = " + ((precoPrimeiraCompra + total) / precoPrimeiraCompra));
		
		if (precoPrimeiraCompra != 0){
			double retorno = ((precoPrimeiraCompra + total) / precoPrimeiraCompra);
			
			if (retorno >=0)
				return retorno;
			else
				return 0;
		}
		else{
			return 1;
		}
	}
	
	
	/**
	 * Estratégia campeã
	 * @param listaAcoes
	 * @return
	 */
	public static double tradeWithTrendLinesForBuyingAndBollingerForSellingStrategy(List<StockQuote> listaAcoes){
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
		for (int i = 0; i < listaAcoes.size(); i++){
			StockQuote stock = listaAcoes.get(i);
			
			if (stock.getBollingerUpperBand() == null){
				continue;
			}
			
			if (stock.getAdjustedClosing() == null || stock.getAdjustedClosing() == 0){
				System.out.println("[" + stock.getDate() + "] Ação " + stock.getSymbol() + " não teve valor ajustado!!!!!!!!!!!!!!!!!!!!!!!! ERRO.");
				break;
			}
			
			if (compraProximaSessao){
				precoCompra = stock.getAdjustedClosing();
				precoMaxJaAlcancado = precoCompra;
				if (precoPrimeiraCompra == 0){
					precoPrimeiraCompra = precoCompra;
				}
				compraProximaSessao = false;
				System.out.println("data = "+ stock.getDate() + " / precoCompra = " + stock.getAdjustedClosing());
			}
			
			if (shortestTermHighTrendLineFromDips(listaAcoes, stock.getDate()) && precoCompra == 0){
				// se estou vendido, então compro
				compraProximaSessao = true;
			}
			// alcançou lucratividade esperada e está sobrevendida
			else if (stock.getAdjustedClosing() >= (precoCompra * 1.20) && precoCompra > 0
					&& stock.getDayClosing() >= stock.getBollingerUpperBand() && stock.getRSI14Days() >= 70){
				System.out.println(stock.getDayClosing() + " / " + stock.getBollingerUpperBand() + " / " + stock.getRSI14Days());
				total += stock.getAdjustedClosing() - precoCompra;
				totalCustosTx +=50;
				precoCompra = 0;
				precoMaxJaAlcancado = 0;
				System.out.println("data = "+ stock.getDate() + " / precoVenda = " + stock.getAdjustedClosing());
			}
			// stop loss
			else if (stock.getAdjustedClosing() <= (precoMaxJaAlcancado * 0.75) && precoCompra > 0){
				total += stock.getAdjustedClosing() - precoCompra;
				totalCustosTx +=50;
				precoCompra = 0;
				System.out.println("data = "+ stock.getDate() + " / precoVenda = " + stock.getAdjustedClosing() + " #stop loss#");		
			}
			
			// ajusta valor maximo ja alcançado para ter um stop loss movel
			if (stock.getAdjustedClosing() > precoMaxJaAlcancado && precoCompra > 0){
				precoMaxJaAlcancado = stock.getAdjustedClosing();
			}
		}

		System.out.println("[" + listaAcoes.get(0).getSymbol() + "] Total custos de transação = " + totalCustosTx);
		System.out.println("[" + listaAcoes.get(0).getSymbol() + "] Total = " + total);
		System.out.println("[" + listaAcoes.get(0).getSymbol() + "] Fator de multiplicação = " + ((total / precoPrimeiraCompra) + 1));
		
		if (precoPrimeiraCompra > 0){
			return ((total / precoPrimeiraCompra) + 1);
		}
		else{
			return 1;
		}
	}
	
	
	
	public static double newTradeWithTrendLinesForBuyingAndBollingerForSellingStrategy(List<StockQuote> listaAcoes) throws Exception{
		if (listaAcoes == null || listaAcoes.size() == 0){
			System.out.println("Nothing to do ...");
			return -1;
		}
		
		List<StockQuote> listaIdx = LoadFromDB.load("IBOVESPA", listaAcoes.get(0).getDate(), listaAcoes.get(listaAcoes.size()-1).getDate());
		HashMap<Date, Double> ibovespaMap = Utils.carregaMapDataValores(listaIdx);

		double total = 0;
		double totalCustosTx = 0;
		double precoCompra = 0;
		double precoPrimeiraCompra = 0;
		double precoMaxJaAlcancado = 0;
		double ibovespaDataCompra = 0;
		boolean compraProximaSessao = false;
		int countDays = 0;
		for (int i = 0; i < listaAcoes.size(); i++){
			StockQuote stock = listaAcoes.get(i);
			
			if (stock.getBollingerUpperBand() == null){
				continue;
			}
			
			if (stock.getAdjustedClosing() == null || stock.getAdjustedClosing() == 0){
				System.out.println("[" + stock.getDate() + "] Ação " + stock.getSymbol() + " não teve valor ajustado!!!!!!!!!!!!!!!!!!!!!!!! ERRO.");
				break;
			}
			
			if (precoCompra > 0){
				countDays++;
			}
			
			if (compraProximaSessao){
				precoCompra = stock.getAdjustedClosing();
				ibovespaDataCompra = ibovespaMap.get(stock.getDate());
				precoMaxJaAlcancado = precoCompra;
				if (precoPrimeiraCompra == 0){
					precoPrimeiraCompra = precoCompra;
				}
				compraProximaSessao = false;
				countDays = 0;
				System.out.println("data = "+ stock.getDate() + " / precoCompra = " + stock.getAdjustedClosing());
			}
			
			// compra
			if (shortestTermHighTrendLineFromDips(listaAcoes, stock.getDate()) && precoCompra == 0 && !stock.isMoreThan10DaysBelowIbovespa()){
				// se estou vendido, então compro
				compraProximaSessao = true;
			}
			// alcançou lucratividade esperada de forma rápida
			else if (stock.getAdjustedClosing() >= (precoCompra * 1.15) && precoCompra > 0
					&& countDays <= 10){
				System.out.println(stock.getDayClosing() + " / " + stock.getBollingerUpperBand() + " / " + stock.getRSI14Days());
				total += stock.getAdjustedClosing() - precoCompra;
				totalCustosTx +=50;
				precoCompra = 0;
				precoMaxJaAlcancado = 0;
				System.out.println("data = "+ stock.getDate() + " / precoVenda = " + stock.getAdjustedClosing());
			}
			// < 45 dias
			else if (stock.getAdjustedClosing() >= (precoCompra * 1.20) && precoCompra > 0
					&& stock.getDayClosing() >= stock.getBollingerUpperBand() && stock.getRSI14Days() >= 75
					&& countDays > 10 && countDays <= 45){
				System.out.println(stock.getDayClosing() + " / " + stock.getBollingerUpperBand() + " / " + stock.getRSI14Days());
				total += stock.getAdjustedClosing() - precoCompra;
				totalCustosTx +=50;
				precoCompra = 0;
				precoMaxJaAlcancado = 0;
				System.out.println("data = "+ stock.getDate() + " / precoVenda = " + stock.getAdjustedClosing());
			}
			// 45 < dias < 90
			else if (stock.getAdjustedClosing() >= (precoCompra * 1.10) && precoCompra > 0
					&& stock.getDayClosing() >= stock.getBollingerUpperBand() && stock.getRSI14Days() >= 75
					&& countDays > 45 && countDays <= 90){
				System.out.println(stock.getDayClosing() + " / " + stock.getBollingerUpperBand() + " / " + stock.getRSI14Days());
				total += stock.getAdjustedClosing() - precoCompra;
				totalCustosTx +=50;
				precoCompra = 0;
				precoMaxJaAlcancado = 0;
				System.out.println("data = "+ stock.getDate() + " / precoVenda = " + stock.getAdjustedClosing());
			}
			// > 90 dias
			else if (stock.getAdjustedClosing() >= (precoCompra * 1.05) && precoCompra > 0
					&& stock.getDayClosing() >= stock.getBollingerUpperBand() && stock.getRSI14Days() >= 75
					&& countDays > 90){
				System.out.println(stock.getDayClosing() + " / " + stock.getBollingerUpperBand() + " / " + stock.getRSI14Days());
				total += stock.getAdjustedClosing() - precoCompra;
				totalCustosTx +=50;
				precoCompra = 0;
				precoMaxJaAlcancado = 0;
				System.out.println("data = "+ stock.getDate() + " / precoVenda = " + stock.getAdjustedClosing());
			}
			// stop loss
			else if (precoCompra > 0 &&
					((stock.getAdjustedClosing() / precoCompra) / (ibovespaMap.get(stock.getDate()) / ibovespaDataCompra)) < 0.86 ){
				total += stock.getAdjustedClosing() - precoCompra;
				totalCustosTx +=50;
				precoCompra = 0;
				System.out.println("data = "+ stock.getDate() + " / precoVenda = " + stock.getAdjustedClosing() + " #stop loss#");		
			}
			
			// ajusta valor maximo ja alcançado para ter um stop loss movel
			if (stock.getAdjustedClosing() > precoMaxJaAlcancado && precoCompra > 0){
				precoMaxJaAlcancado = stock.getAdjustedClosing();
			}
		}

		System.out.println("[" + listaAcoes.get(0).getSymbol() + "] Total custos de transação = " + totalCustosTx);
		System.out.println("[" + listaAcoes.get(0).getSymbol() + "] Total = " + total);
		System.out.println("[" + listaAcoes.get(0).getSymbol() + "] Fator de multiplicação = " + ((total / precoPrimeiraCompra) + 1));
		
		if (precoPrimeiraCompra > 0 && total != 0){
			return ((total / precoPrimeiraCompra) + 1);
		}
		else{
			return -1;
		}
	}
	
	
	
	
	
//	BKP
//	public static double tradeWithTrendLinesForBuyingAndBollingerForSellingStrategy(List<StockQuote> listaAcoes){
//		if (listaAcoes == null || listaAcoes.size() == 0){
//			System.out.println("Nothing to do ...");
//			return 1;
//		}
//
//		double total = 0;
//		double totalCustosTx = 0;
//		double precoCompra = 0;
//		double precoPrimeiraCompra = 0;
//		double precoMaxJaAlcancado = 0;
//		boolean compraProximaSessao = false;
//		for (int i = 0; i < listaAcoes.size(); i++){
//			StockQuote stock = listaAcoes.get(i);
//			
//			if (stock.getAdjustedClosing() == null || stock.getAdjustedClosing() == 0){
//				System.out.println("[" + stock.getDate() + "] Ação " + stock.getSymbol() + " não teve valor corrigido!!!!!!!!!!!!!!!!!!!!!!!! ERRO.");
//				break;
//			}
//			
//			if (compraProximaSessao){
//				precoCompra = stock.getAdjustedClosing();
//				precoMaxJaAlcancado = precoCompra;
//				if (precoPrimeiraCompra == 0){
//					precoPrimeiraCompra = precoCompra;
//				}
//				compraProximaSessao = false;
//				System.out.println("data = "+ stock.getDate() + " / precoCompra = " + stock.getAdjustedClosing());
//			}
//			
//			if (shortestTermHighTrendLineFromDips(listaAcoes, stock.getDate()) && precoCompra == 0){
//				// se estou vendido, então compro
//				compraProximaSessao = true;
//			}
//			// alcançou lucratividade esperada
//			else if (stock.getAdjustedClosing() >= (precoCompra * 1.15) && precoCompra > 0){
//				// se estou comprado, então vendo
//				total += stock.getAdjustedClosing() - precoCompra;
//				totalCustosTx +=50;
//				precoCompra = 0;
//				precoMaxJaAlcancado = 0;
//				System.out.println("data = "+ stock.getDate() + " / precoVenda = " + stock.getAdjustedClosing());
//			}
//			// stop loss
//			else if (stock.getAdjustedClosing() <= (precoMaxJaAlcancado * 0.70) && precoCompra > 0){
//				total += stock.getAdjustedClosing() - precoCompra;
//				totalCustosTx +=50;
//				precoCompra = 0;
//				System.out.println("data = "+ stock.getDate() + " / precoVenda = " + stock.getAdjustedClosing() + " #stop loss#");		
//			}
//			
//			if (stock.getAdjustedClosing() > precoMaxJaAlcancado && precoCompra > 0){
//				precoMaxJaAlcancado = stock.getAdjustedClosing();
//			}
//		}
//
//		System.out.println("[" + listaAcoes.get(0).getSymbol() + "] Total custos de transação = " + totalCustosTx);
//		System.out.println("[" + listaAcoes.get(0).getSymbol() + "] Total = " + total);
//		System.out.println("[" + listaAcoes.get(0).getSymbol() + "] Fator de multiplicação = " + ((total / precoPrimeiraCompra) + 1));
//		
//		if (precoPrimeiraCompra > 0){
//			return ((total / precoPrimeiraCompra) + 1);
//		}
//		else{
//			return 1;
//		}
//	}
}

