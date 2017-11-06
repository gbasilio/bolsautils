package br.gustavo.test.strategies.singlestock;

import java.util.List;

import br.gustavo.types.StockQuote;

public class StochasticStrategies {

	
	public static void simpleStochastic14Days8040(List<StockQuote> listaAcoes){
		if (listaAcoes == null || listaAcoes.size() == 0){
			System.out.println("Nothing to do ...");
			return;
		}
		
		String symbol = "";
		double total = 0;
		double precoCompra = 0;
		double estocasticoAnterior = -1;
		for (StockQuote stock: listaAcoes){
			if (stock.getStochasticOscillator14Days() == null){
				continue;
			}
			if (estocasticoAnterior < 0){
				estocasticoAnterior = stock.getStochasticOscillator14Days();
				continue;
			}
	
			symbol = stock.getSymbol();
			// se estocastico estava acima de 40 e caiu, quebrando a barreira dos 40, então vendo porque a acao está perdendo momento
			if (estocasticoAnterior > 80 && stock.getStochasticOscillator14Days() < 80){
				// se estou comprado, ent�o vendo
				if (precoCompra > 0){
					total += stock.getDayClosing() - precoCompra;
					precoCompra = 0;
					System.out.println("data = "+ stock.getDate() + " / precoVenda = " + stock.getDayClosing());
				}
			}
			// se estocastico estava abaixo de 80 e subiu, passando a barreira dos 80, então compro porque a acao está ganhando momento
			if (estocasticoAnterior < 40 && stock.getStochasticOscillator14Days() > 40){
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
	public static void simpleStochastic200Days8040(List<StockQuote> listaAcoes){
		if (listaAcoes == null || listaAcoes.size() == 0){
			System.out.println("Nothing to do ...");
			return;
		}
		
		String symbol = "";
		double total = 0;
		double precoCompra = 0;
		double estocasticoAnterior = -1;
		for (StockQuote stock: listaAcoes){
			if (stock.getStochasticOscillator200Days() == null){
				continue;
			}
			if (estocasticoAnterior < 0){
				estocasticoAnterior = stock.getStochasticOscillator200Days();
				continue;
			}
	
			symbol = stock.getSymbol();
			// se estocastico estava acima de 80 e caiu, quebrando a barreira dos 80, então vendo porque a acao está perdendo momento
			if (estocasticoAnterior > 80 && stock.getStochasticOscillator200Days() < 80){
				// se estou comprado, ent�o vendo
				if (precoCompra > 0){
					total += stock.getDayClosing() - precoCompra;
					precoCompra = 0;
					System.out.println("data = "+ stock.getDate() + " / precoVenda = " + stock.getDayClosing());
				}
			}
			// se estocastico estava abaixo de 40 e subiu, passando a barreira dos 40, então compro porque a acao está ganhando momento
			if (estocasticoAnterior < 40 && stock.getStochasticOscillator200Days() > 40){
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
	
}
