package br.gustavo.test.strategies.singlestock;

import java.util.List;

import br.gustavo.types.StockQuote;

public class BollingerStrategies {

	/**
	 * 
	 * @param listaAcoes
	 */
	public static void simpleBollinger(List<StockQuote> listaAcoes){
		if (listaAcoes == null || listaAcoes.size() == 0){
			System.out.println("Nothing to do ...");
			return;
		}
		String symbol = "";
		double total = 0;
		double precoCompra = 0;
		for (StockQuote stock: listaAcoes){
			if (stock.getBollingerUpperBand() == null || stock.getBollingerLowerBand() == null){
				continue;
			}

			symbol = stock.getSymbol();
			// caro
			if (stock.getDayClosing() >= stock.getBollingerUpperBand()){
				// se estou comprado, ent�o vendo
				if (precoCompra > 0){
					total += stock.getDayClosing() - precoCompra;
					precoCompra = 0;
					System.out.println("data = "+ stock.getDate() + " / precoVenda = " + stock.getDayClosing());
				}
			}
			// barato
			if (stock.getDayClosing() <= stock.getBollingerLowerBand()){
				// se estou vendido, ent�o compro
				if (precoCompra == 0){
					precoCompra = stock.getDayClosing();
					System.out.println("data = "+ stock.getDate() + " / precoCompra = " + stock.getDayClosing());
				}
			}
			// stop loss
			//			// se estiver comprado e preco atual for menor que 90% do preco de compra, então vendo para minimizar prejuizo
			if (precoCompra > 0){
				if (stock.getDayClosing() < (precoCompra*0.90)){
					total += stock.getDayClosing() - precoCompra;
					precoCompra = 0;
					System.out.println("data = "+ stock.getDate() + " / precoVenda = " + stock.getDayClosing() + " - #stop loss#");		
				}
			}
		}

		System.out.println("[" + symbol + "] Total = " + total);
	}



	/**
	 * 
	 * @param listaAcoes
	 */
	public static void simpleBollingerWithTrendMediumTerm(List<StockQuote> listaAcoes){
		if (listaAcoes == null || listaAcoes.size() == 0){
			System.out.println("Nothing to do ...");
			return;
		}
		double total = 0;
		double precoCompra = 0;
		for (StockQuote stock: listaAcoes){
			if (stock.getBollingerUpperBand() == null || stock.getBollingerLowerBand() == null){
				continue;
			}

			// caro
			if (stock.getDayClosing() >= stock.getBollingerUpperBand()){
				// se estou comprado, ent�o vendo
				if (precoCompra > 0){
					total += stock.getDayClosing() - precoCompra;
					precoCompra = 0;
					System.out.println("data = "+ stock.getDate() + " / precoVenda = " + stock.getDayClosing());
				}
			}
			// barato
			if (stock.getDayClosing() <= stock.getBollingerLowerBand()){
				// se estou vendido e for tendencia de alta, ent�o compro
				if (precoCompra == 0 && AMAStrategies.simpleAMARibbonMediumTerm(stock) == 1){
					precoCompra = stock.getDayClosing();
					System.out.println("data = "+ stock.getDate() + " / precoCompra = " + stock.getDayClosing());
				}
			}
			// stop loss
			//			// se estiver comprado e preco atual for menor que 90% do preco de compra, então vendo para minimizar prejuizo
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
	 * 
	 * @param listaAcoes
	 */
	public static void indiceVolatilidade(List<StockQuote> listaAcoes){
		for (StockQuote stock: listaAcoes){
			if (stock.getBollingerUpperBand() == null || stock.getBollingerLowerBand() == null){
				continue;
			}
			
			System.out.println("[data = " + stock.getDate() + "] [indiceVolatilidade = " + (stock.getBollingerUpperBand() - stock.getBollingerLowerBand()) + "]");
		}
	}

	
	
	public static void buyWhenCheapSellWhenReaching10PerCentStrategy(List<StockQuote> listaAcoes){
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
			if (stock.getBollingerUpperBand() == null || stock.getBollingerLowerBand() == null){
				continue;
			}
			
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
			
			// barato
			if (stock.getDayClosing() <= stock.getBollingerLowerBand()){
				// se estou vendido, então compro
				if (precoCompra == 0){
					compraProximaSessao = true;
				}
			}
			
			// alcançou lucratividade esperada
			if (stock.getDayClosing() >= (precoCompra * 1.08)){
				// se estou comprado, então vendo
				if (precoCompra > 0){
					vendeProximaSessao = true;
				}
			}

			// stop loss
			// se estiver comprado e preco atual for menor que 90% do preco de compra, então vendo para minimizar prejuizo
			if (precoCompra > 0){
				if (stock.getDayClosing() <= (precoCompra * 0.96)){
					vendeProximaSessao = true;
					System.out.println("#stop loss#");		
				}
			}
		}
		
		System.out.println("[" + listaAcoes.get(0).getSymbol() + "] Total custos de transação = " + totalCustosTx);
		System.out.println("[" + listaAcoes.get(0).getSymbol() + "] Total = " + total);
		System.out.println("[" + listaAcoes.get(0).getSymbol() + "] Fator de multiplicação = " + ((total / precoPrimeiraCompra) + 1));
	}
}
