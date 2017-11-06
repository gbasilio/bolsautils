package br.gustavo.test.strategies.singlestock;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.commons.math3.stat.correlation.PearsonsCorrelation;

import br.gustavo.db.LoadFromDB;
import br.gustavo.types.StockQuote;

public class GeneralStrategies {

	
	public static void buyAfterFallsAndSellAfterGain(List<StockQuote> listaAcoes){
		if (listaAcoes == null || listaAcoes.size() == 0){
			System.out.println("Nothing to do ...");
			return;
		}

		double total = 0;
		double precoCompra = 0;
		double precoPrimeiraCompra = 0;
		int totalCustosTx = 0;
		boolean compraProximaSessao = false;
		double precoSessaoAnterior = 0;
		double precoInicioQueda = 0;
		double drawDown = 0;
		int numQuedas = 0;
		for (StockQuote stock: listaAcoes){
			if (precoSessaoAnterior == 0){
				precoSessaoAnterior = stock.getDayClosing();
				continue;
			}
			
			if (stock.getStochasticOscillator14Days() == null){
				continue;
			}
			
			if (compraProximaSessao){
				precoCompra = stock.getDayClosing();
				if (precoPrimeiraCompra == 0){
					precoPrimeiraCompra = precoCompra;
				}
				System.out.println("data = "+ stock.getDate() + " / precoCompra = " + stock.getDayClosing());
				compraProximaSessao = false;
				numQuedas = 0;
				precoInicioQueda = 0;
				drawDown = 0;
				continue;
			}
			
			// queda
			if (stock.getDayClosing() <= precoSessaoAnterior){
				if (numQuedas == 0){
					precoInicioQueda = stock.getDayClosing();
				}
				numQuedas++;
				drawDown = ((stock.getDayClosing() / precoInicioQueda) - 1) * 100;
			}
			
			// se caiu por n pregoes seguidos, draw down é grande e estou vendido, entao compra
			if (numQuedas >= 6 && drawDown <= -10 && precoCompra == 0){
				compraProximaSessao = true;
				numQuedas = 0;
				precoInicioQueda = 0;
				drawDown = 0;
			}
			// vende
			else if (stock.getDayClosing() >= precoCompra && stock.getDayClosing() < precoSessaoAnterior && precoCompra > 0){
//			else if (stock.getDayHighest() >= (precoCompra * 1.06) && precoCompra > 0){
			// se estou comprado, então vendo
				total += stock.getDayHighest() - precoCompra;
				precoCompra = 0;
				System.out.println("data = "+ stock.getDate() + " / precoVenda = " + stock.getDayClosing());
				totalCustosTx += 50;
				numQuedas = 0;
				precoInicioQueda = 0;
				drawDown = 0;
			}
			// stop loss
			else if (stock.getDayClosing() <= (precoCompra * 0.90) && precoCompra > 0){
				total += stock.getDayClosing() - precoCompra;
				precoCompra = 0;
				System.out.println("data = "+ stock.getDate() + " / precoVenda = " + stock.getDayClosing() + "#stop loss#");
				totalCustosTx += 50;
				numQuedas = 0;
				precoInicioQueda = 0;
				drawDown = 0;
			}
			
			// zera numQuedas se preco voltou a subir
			if (stock.getDayClosing() > precoSessaoAnterior){
				numQuedas = 0;
				precoInicioQueda = 0;
				drawDown = 0;
			}
			precoSessaoAnterior = stock.getDayClosing();
			
		}
		
		System.out.println("[" + listaAcoes.get(0).getSymbol() + "] Total custos de transação = " + totalCustosTx);
		System.out.println("[" + listaAcoes.get(0).getSymbol() + "] Total = " + total);
		System.out.println("[" + listaAcoes.get(0).getSymbol() + "] Fator de multiplicação = " + ((total / precoPrimeiraCompra) + 1));
	}
	
	
	
	/**
	 * 
	 * @param listaAcoes
	 */
	public static void buyAfterFallsAndSellNextSession(List<StockQuote> listaAcoes){
		if (listaAcoes == null || listaAcoes.size() == 0){
			System.out.println("Nothing to do ...");
			return;
		}

		double total = 0;
		double precoCompra = 0;
		double precoPrimeiraCompra = 0;
		int totalCustosTx = 0;
		boolean compraProximaSessao = false;
		double precoSessaoAnterior = 0;
		int numQuedas = 0;
		int qtdDiasComprado = 0;
		for (StockQuote stock: listaAcoes){
			if (precoCompra != 0){
				qtdDiasComprado++;
			}
			
			if (precoSessaoAnterior == 0){
				precoSessaoAnterior = stock.getDayClosing();
				continue;
			}
			
			if (compraProximaSessao){
				precoCompra = stock.getDayClosing();
				if (precoPrimeiraCompra == 0){
					precoPrimeiraCompra = precoCompra;
				}
				System.out.println("data = "+ stock.getDate() + " / precoCompra = " + stock.getDayClosing());
				compraProximaSessao = false;
				numQuedas = 0;
			}
			
			if (qtdDiasComprado == 1){
				total += stock.getDayClosing() - precoCompra;
				precoCompra = 0;
				qtdDiasComprado = 0;
				System.out.println("data = "+ stock.getDate() + " / precoVenda = " + stock.getDayClosing());
				totalCustosTx += 50;
				numQuedas = 0;
			}
			
			
			// queda
			if (stock.getDayClosing() <= precoSessaoAnterior){
				numQuedas++;
			}
			
			// se caiu por n pregoes seguidos, voltar a subir e estou vendido, entao compra
			if (numQuedas >= 6 && stock.getDayClosing() > precoSessaoAnterior && precoCompra == 0){
				compraProximaSessao = true;
				numQuedas = 0;
			}
					
			// zera numQuedas se preco voltou a subir
			if (stock.getDayClosing() > precoSessaoAnterior){
				numQuedas = 0;
			}
			precoSessaoAnterior = stock.getDayClosing();
			
		}
		
		System.out.println("[" + listaAcoes.get(0).getSymbol() + "] Total custos de transação = " + totalCustosTx);
		System.out.println("[" + listaAcoes.get(0).getSymbol() + "] Total = " + total);
		System.out.println("[" + listaAcoes.get(0).getSymbol() + "] Fator de multiplicação = " + ((total / precoPrimeiraCompra) + 1));
	}
	
	/**
	 * 
	 * @param listaAcoes
	 */
	public static void buyBelowMinSellAboveMax(List<StockQuote> listaAcoes){
		if (listaAcoes == null || listaAcoes.size() == 0){
			System.out.println("Nothing to do ...");
			return;
		}

		double total = 0;
		double precoCompra = 0;
		double precoPrimeiraCompra = 0;
		double precoMaxJaAlcancado = 0;
		double ultimoPreco = 0;
		int totalCustosTx = 0;
		boolean compraProximaSessao = false;
		boolean vendeProximaSessao = false;
		for (StockQuote stock: listaAcoes){
			
			if (stock.getMinLast200Days() == null || stock.getMaxLast200Days() == null){
				continue;
			}
			
			if (compraProximaSessao){
				precoCompra = stock.getDayClosing();
				precoMaxJaAlcancado = stock.getDayClosing();
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
			
			// se caiu abaixo do minimo de 200 dias, entao compra
			if (stock.getDayClosing() <= (stock.getMinLast200Days() * 1.05) && ((stock.getMaxLast200Days() / stock.getMinLast200Days()) > 1.40) && precoCompra == 0){
				System.out.println("min = " + stock.getMinLast200Days());
				compraProximaSessao = true;
			}
					
			// vende
			if (stock.getDayClosing() >= (stock.getMaxLast200Days() * 0.95) && precoCompra > 0){
				System.out.println("max = " + stock.getMaxLast200Days());
				vendeProximaSessao = true;
			}
			
			// stop loss
			if (stock.getDayLowest() <= (precoMaxJaAlcancado * 0.60) && precoCompra > 0){
				total += stock.getDayLowest() - precoCompra;
				totalCustosTx +=50;
				precoCompra = 0;
				System.out.println("data = "+ stock.getDate() + " / precoVenda = " + stock.getDayLowest() + " #stop loss#");		
			}
			
			if (stock.getDayHighest() > precoMaxJaAlcancado){
				precoMaxJaAlcancado = stock.getDayHighest();
			}
			
			ultimoPreco = stock.getDayClosing();
		}
		
		// liquida posicoes
		if (precoCompra > 0){
			total += ultimoPreco - precoCompra;
			totalCustosTx +=50;
			precoCompra = 0;
			System.out.println("data = ---- / precoVenda = " + ultimoPreco);		
		}
		
		System.out.println("[" + listaAcoes.get(0).getSymbol() + "] Total custos de transação = " + totalCustosTx);
		System.out.println("[" + listaAcoes.get(0).getSymbol() + "] Total = " + total);
		System.out.println("[" + listaAcoes.get(0).getSymbol() + "] Fator de multiplicação = " + ((total / precoPrimeiraCompra) + 1));
	}
	
	
	
//	public static void printMin(List<StockQuote> listaAcoes){
//		for (StockQuote stock: listaAcoes){
//			System.out.println(stock.getDate() + ";" + stock.getMinLast200Days());
//		}
//		System.out.println("[" + listaAcoes.get(0).getSymbol() + "]");
//	}
//	
//	
//	public static void printROC(List<StockQuote> listaAcoes){
//		for (int i = 0; i < listaAcoes.size(); i++){
//			if (i < 15){
//				continue;
//			}
//			
//			StockQuote stock = listaAcoes.get(i);
//			
//			System.out.println(stock.getDate() + " - " + stock.getDayClosing() + " - ROC20Days = " + stock.getRoc20Days() + " mme200DiasRoc20Dias = " + stock.get_200DaysEMAOfRoc20Days());
//		}
//	}
	
	
	public static void tradeWithEMAOfROC(List<StockQuote> listaAcoes){
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
		double EMAROCAnterior = 0;
		double EMAROCAntesDoAnterior = 0;
		for (StockQuote stock: listaAcoes){
			
			if (stock.get_200DaysEMAOfRoc20Days() == null){
				continue;
			}
			
			if (EMAROCAnterior == 0){
				EMAROCAnterior = stock.get_200DaysEMAOfRoc20Days();
				continue;
			}
			
			if (EMAROCAntesDoAnterior == 0){
				EMAROCAntesDoAnterior = stock.get_200DaysEMAOfRoc20Days();
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
			
			// se formou um vale de EMA ROC, então compra
			if (EMAROCAntesDoAnterior > EMAROCAnterior && EMAROCAnterior < stock.get_200DaysEMAOfRoc20Days() && precoCompra == 0){
				compraProximaSessao = true;
				System.out.println("VALE = " + stock.getDate());
			}
					
			// se formou um pico de EMA ROC, então vende
			if (EMAROCAntesDoAnterior < EMAROCAnterior && EMAROCAnterior > stock.get_200DaysEMAOfRoc20Days() && precoCompra > 0){
				vendeProximaSessao = true;
				System.out.println("PICO = " + stock.getDate());
			}
			
			EMAROCAntesDoAnterior = EMAROCAnterior;
			EMAROCAnterior = stock.get_200DaysEMAOfRoc20Days();
		}
		
		System.out.println("[" + listaAcoes.get(0).getSymbol() + "] Total custos de transação = " + totalCustosTx);
		System.out.println("[" + listaAcoes.get(0).getSymbol() + "] Total = " + total);
		System.out.println("[" + listaAcoes.get(0).getSymbol() + "] Fator de multiplicação = " + ((total / precoPrimeiraCompra) + 1));
	}
	

	public static void tradeSpreadPETR3PETR4(List<StockQuote> listaAcoes) throws Exception{
		if (listaAcoes == null || listaAcoes.size() == 0){
			System.out.println("Nothing to do ...");
			return;
		}

		Date startDate = listaAcoes.get(0).getDate();
		Date endDate = listaAcoes.get(listaAcoes.size()-1).getDate();
		
		List<StockQuote> petr3List = LoadFromDB.load("PETR3", startDate, endDate);
		List<StockQuote> petr4List = LoadFromDB.load("PETR4", startDate, endDate);
		
		double total = 0;
		double precoCompra = 0;
		double precoPrimeiraCompra = 0;
		int totalCustosTx = 0;
//		int count = 0;
//		double soma = 0;
//		for (StockQuote petr4: itubList){
//			for (StockQuote petr3: bbasList){
//				if (petr4.getDate().equals(petr3.getDate())){
//					double razao = petr3.getDayClosing() / petr4.getDayClosing();
//					System.out.println(petr3.getDate() + " - PETR3/PETR4 = " + razao);
//					count++;
//					soma += razao;
//				}
//			}
//		}
		
		System.out.println("[" + listaAcoes.get(0).getSymbol() + "] Total custos de transação = " + totalCustosTx);
		System.out.println("[" + listaAcoes.get(0).getSymbol() + "] Total = " + total);
		System.out.println("[" + listaAcoes.get(0).getSymbol() + "] Fator de multiplicação = " + ((total / precoPrimeiraCompra) + 1));
	}
	
	
	public static void main(String[] args) throws Exception{

		Calendar start = new GregorianCalendar(2003, Calendar.OCTOBER, 03);
		Date startDate = start.getTime();
		Calendar end = new GregorianCalendar(2020, Calendar.MARCH, 30);
		Date endDate = end.getTime();
		
//		List<StockQuote> itubList = LoadFromDB.load("ITUB4", startDate, endDate);
//		List<StockQuote> bbdcList = LoadFromDB.load("BBDC4", startDate, endDate);
//		List<StockQuote> bbasList = LoadFromDB.load("BBAS3", startDate, endDate);
//		
//		List<StockQuote> petr3List = LoadFromDB.load("PETR3", startDate, endDate);
//		List<StockQuote> petr4List = LoadFromDB.load("PETR4", startDate, endDate);
		
//		for (StockQuote itub4: itubList){
//			for (StockQuote bbdc4: bbdcList){
//				if (itub4.getDate().equals(bbdc4.getDate())){
//					System.out.println(bbdc4.getDate() + " - BBDC4/ITUB4 = " + bbdc4.getDayClosing() / itub4.getDayClosing());
//				}
//			}
//		}
//		
//		
//		for (StockQuote itub4: itubList){
//			for (StockQuote bbas3: bbasList){
//				if (itub4.getDate().equals(bbas3.getDate())){
//					System.out.println(bbas3.getDate() + " - BBAS3/ITUB4 = " + bbas3.getDayClosing() / itub4.getDayClosing());
//				}
//			}
//		}
		
//		int count = 0;
//		double soma = 0;
//		for (StockQuote petr4: petr4List){
//			for (StockQuote petr3: petr3List){
//				if (petr4.getDate().equals(petr3.getDate())){
//					double razao = petr3.getAdjustedClosing() / petr4.getAdjustedClosing();
////					System.out.println(petr3.getDate() + " - PETR3/PETR4 = " + razao);
//					System.out.println(petr3.getDate() + " - PETR3 = " + petr3.getAdjustedClosing() + " / PETR4 = " + petr4.getAdjustedClosing());
//					count++;
//					soma += razao;
//				}
//			}
//		}
//		System.out.println("Média = " + soma / count);
	}
}
