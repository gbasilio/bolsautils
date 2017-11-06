package br.gustavo.test.strategies.singlestock;

import java.util.List;

import br.gustavo.types.StockQuote;

/**
 * 
 * @author gbasilio
 *
 */
public class AMAStrategies {


	/**
	 * 
	 * @param listaAcoes
	 */
	public static void simpleAMARibbonLongTerm(List<StockQuote> listaAcoes){
		if (listaAcoes == null || listaAcoes.size() == 0){
			System.out.println("Nothing to do ...");
			return;
		}

		double total = 0;
		double precoCompra = 0;
		for (StockQuote stock: listaAcoes){
			if (stock.get_200DayAMA() == null){
				continue;
			}

			if (stock.get_9DayAMA() > stock.get_25DayAMA() && 
				stock.get_25DayAMA() > stock.get_50DayAMA() && 
				stock.get_50DayAMA() > stock.get_100DayAMA() && 
				stock.get_100DayAMA() > stock.get_200DayAMA()){
//				System.out.println(stock.getDate() + " = Tendência de alta");
				// se estou vendido, então compro
				if (precoCompra == 0){
					precoCompra = stock.getDayClosing();
					System.out.println("data = "+ stock.getDate() + " / precoCompra = " + stock.getDayClosing());
				}
			}
			else if (stock.get_9DayAMA() < stock.get_25DayAMA() && stock.get_25DayAMA() < stock.get_50DayAMA() && stock.get_50DayAMA() < stock.get_100DayAMA() && stock.get_100DayAMA() < stock.get_200DayAMA()){
//				System.out.println(stock.getDate() + " = Tendência de baixa");
				// se estou comprado, então vendo
				if (precoCompra > 0){
					total += stock.getDayClosing() - precoCompra;
					precoCompra = 0;
					System.out.println("data = "+ stock.getDate() + " / precoVenda = " + stock.getDayClosing());
				}
			}
			else{
//				System.out.println(stock.getDate() + " = Tendência indefinida");
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

		System.out.println("[" + listaAcoes.get(0).getSymbol() + "] Total = " + total);
	}



	/**
	 * 
	 * @param stock
	 * @return
	 */
	public static int simpleAMARibbonMediumTerm(StockQuote stock){

		if (stock.get_100DayAMA() == null){
			return 0;
		}

		if (stock.get_9DayAMA() > stock.get_25DayAMA() && stock.get_25DayAMA() > stock.get_50DayAMA() && stock.get_50DayAMA() > stock.get_100DayAMA()){
			return 1;
		}
		else if (stock.get_9DayAMA() < stock.get_25DayAMA() && stock.get_25DayAMA() < stock.get_50DayAMA() && stock.get_50DayAMA() < stock.get_100DayAMA()){
			return -1;
		}
		else{
			return 0;
		}
	}
	
	
	/**
	 * 
	 * @param listaAcoes
	 */
	public static void simpleTrendLine200DaysAMA(List<StockQuote> listaAcoes){

		double total = 0;
		double precoCompra = 0;
		double precoPrimeiraCompra = 0;
		int totalCustosTx = 0;
		double _200DayAMAAnterior = -1;
		for (StockQuote stock: listaAcoes){
			if (stock.get_200DayAMA() == null){
				continue;
			}

			if (_200DayAMAAnterior < 0){
				_200DayAMAAnterior = stock.get_200DayAMA();
				continue;
			}
			
			if (precoPrimeiraCompra == 0){
				precoPrimeiraCompra = precoCompra;
			}

			if (stock.get_200DayAMA() >= _200DayAMAAnterior){
				// se estou vendido, então compro
				if (precoCompra == 0){
					precoCompra = stock.getDayClosing();
					System.out.println("data = "+ stock.getDate() + " / precoCompra = " + stock.getDayClosing());
				}
			}
			else{
				// se estou comprado, então vendo
				if (precoCompra > 0){
					total += stock.getDayClosing() - precoCompra;
					totalCustosTx += 50;
					precoCompra = 0;
					System.out.println("data = "+ stock.getDate() + " / precoVenda = " + stock.getDayClosing());
				}
			}

			_200DayAMAAnterior = stock.get_200DayAMA();
		}
		
		System.out.println("[" + listaAcoes.get(0).getSymbol() + "] Total custos de transação = " + totalCustosTx);
		System.out.println("[" + listaAcoes.get(0).getSymbol() + "] Total = " + total);
		System.out.println("[" + listaAcoes.get(0).getSymbol() + "] Fator de multiplicação = " + ((total / precoPrimeiraCompra) + 1));
	}


	/**
	 * 
	 * @param listaAcoes
	 */
	public static void simpleTrendLine100DaysAMA(List<StockQuote> listaAcoes){

		double total = 0;
		double precoCompra = 0;
		double _100DayAMAAnterior = -1;
		for (StockQuote stock: listaAcoes){
			if (stock.get_100DayAMA() == null){
				continue;
			}

			if (_100DayAMAAnterior < 0){
				_100DayAMAAnterior = stock.get_100DayAMA();
				continue;
			}

			if (stock.get_100DayAMA() >= _100DayAMAAnterior){
				// se estou vendido, então compro
				if (precoCompra == 0){
					precoCompra = stock.getDayClosing();
					System.out.println("data = "+ stock.getDate() + " / precoCompra = " + stock.getDayClosing());
				}
			}
			else{
				// se estou comprado, então vendo
				if (precoCompra > 0){
					total += stock.getDayClosing() - precoCompra;
					precoCompra = 0;
					System.out.println("data = "+ stock.getDate() + " / precoVenda = " + stock.getDayClosing());
				}
			}

			_100DayAMAAnterior = stock.get_100DayAMA();
		}
		System.out.println("Total = " + total);
	}
}
