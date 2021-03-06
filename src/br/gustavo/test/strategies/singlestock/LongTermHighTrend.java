package br.gustavo.test.strategies.singlestock;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.gustavo.db.LoadFromDB;
import br.gustavo.indicators.StockQuoteListUtils;
import br.gustavo.indicators.Utils;
import br.gustavo.types.StockQuote;

public class LongTermHighTrend {

//		public static void main(String[] args) throws Exception{
//			Calendar start = new GregorianCalendar(2010, Calendar.DECEMBER, 07);
//			Date startDate = start.getTime();
//			Calendar end = new GregorianCalendar(2013, Calendar.FEBRUARY, 28);
//			Date endDate = end.getTime();
//	
//			List<StockQuote> listaAcao = LoadFromDB.load("RADL3", startDate, endDate);
//			StockQuoteListUtils.populateStochasticOscillator14Days(listaAcao);
//			List<StockQuote> listaIdx = LoadFromDB.load("IBOVESPA", startDate, endDate);
//	
//			int size = 20;
//			double[] ibovespaRatioList = new double[size];
//			for (int i=0; i < size; i++){
//				ibovespaRatioList[i] = 1;
//			}
//			
//			double c = 1;
//			int count = 0;
//			for (int i = 0; i < listaAcao.size(); i++){
//				if (i == 0){
//					continue;
//				}
//	
//				StockQuote stock = listaAcao.get(i);
//				StockQuote stockDayB4 = listaAcao.get(i-1);
//				StockQuote ibovespa = listaIdx.get(i);
//				StockQuote ibovespaDayB4 = listaIdx.get(i-1);
//	
//				double ibovespaRatio = ((stock.getDayClosing()/stockDayB4.getDayClosing()) / (ibovespa.getDayClosing()/ibovespaDayB4.getDayClosing()));
//				
//				for (int j=size-1; j>0; j--){
//					ibovespaRatioList[j] = ibovespaRatioList[j-1];
//				}
//				ibovespaRatioList[0] = ibovespaRatio;
//				
//				double ibovespaRatioMedioXDias = 1;
//				for (int k=0; k < size; k++){
//					ibovespaRatioMedioXDias = ibovespaRatioMedioXDias * ibovespaRatioList[k];
//				}
//				
//	//			System.out.println(stock.getDate() + "\t" + stock.getDayClosing() + "\t" + stock.getStochasticOscillator14Days() + "\t" + ibovespaRatioMedioXDias);
//				System.out.println(stock.getDate() + "\t" + stock.getDayClosing() + "\t" + ibovespaRatioMedioXDias);
//				
//				
//				c = c * ibovespaRatio;
//			}
//			
//			System.out.println(c);
//	
//		} 


	private static String[] cods = {
		"AEDU3",
//		"ALLL3",
//		"AMBV3",
//		"AMBV4",
//		"AMIL3",
//		"BBAS3",
//		"BBDC3",
//		"BBDC4",
//		"BBRK3",
//		"BISA3",
//		"BRAP4",
//		"BRFS3",
		"BRKM5",
//		"BRML3",
//		"BRPR3",
//		"BRSR6",
//		"BTOW3",
//		"BVMF3",
//		"CCRO3",
//		"CCXC3",
//		"CESP6",
//		"CIEL3",
//		"CMIG4",
//		"CPFE3",
//		"CPLE6",
//		"CRUZ3",
//		"CSAN3",
//		"CSMG3",
//		"CSNA3",
//		"CTIP3",
//		"CYRE3",
//		"DASA3",
//		"DTEX3",
//		"ECOR3",
//		"ELET3",
//		"ELET6",
//		"ELPL4",
//		"EMBR3",
//		"ENBR3",
//		"EZTC3",
//		"FIBR3",
//		"GETI4",
//		"GFSA3",
//		"GGBR4",
//		"GOAU4",
//		"GOLL4",
//		"HGTX3",
//		"HRTP3",
//		"HYPE3",
//		"IGTA3",
//		"ITSA4",
//		"ITUB4",
		"JBSS3",
//		"KLBN4",
//		"LAME4",
//		"LIGT3",
//		"LLXL3",
//		"LREN3",
//		"MGLU3",
//		"MMXM3",
//		"MPLU3",
//		"MPXE3",
//		"MRFG3",
//		"MRVE3",
//		"MULT3",
//		"MYPK3",
//		"NATU3",
//		"ODPV3",
//		"OGXP3",
//		"OHLB3",
//		"OIBR3",
//		"OIBR4",
//		"OSXB3",
//		"PCAR4",
//		"PDGR3",
		"PETR3",
		"PETR4",
//		"POMO4",
//		"PSSA3",
//		"QGEP3",
//		"RADL3",
		"RAPT4",
//		"RENT3",
//		"RSID3",
//		"SANB11",
//		"SBSP3",
//		"SULA11",
//		"SUZB5",
//		"TBLE3",
//		"TCSA3",
//		"TIMP3",
//		"TOTS3",
//		"TRPL4",
//		"UGPA3",
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



//	public static void main(String[] args) throws Exception{
//		Calendar start = new GregorianCalendar(2003, Calendar.JULY, 07);
//		Date startDate = start.getTime();
//		Calendar end = new GregorianCalendar(2013, Calendar.FEBRUARY, 21);
//		Date endDate = end.getTime();
//
//		List<StockQuote> listaIdx = LoadFromDB.load("IBOVESPA", startDate, endDate);
//		HashMap<Date, Double> ibovespaMap = Utils.carregaMapDataValores(listaIdx);
//
//		double total = 1;
//		for (int i = 0; i < cods.length; i++){
//			List<StockQuote> listaAcao = LoadFromDB.load(cods[i], startDate, endDate);
//			StockQuoteListUtils.populateStochasticOscillator14Days(listaAcao);
//
//			double precoCompra = 0;
//			double ultimoPreco = 0;
//			int countDias = 0;
//			for (int k = 0; k < listaAcao.size(); k++){
//				if (k <= 30){
//					continue;
//				}
//
//				StockQuote stockHoje = listaAcao.get(k);
//				StockQuote stockOntem = listaAcao.get(k-30);
//
//				double stockIbovespaRatio = ((stockHoje.getAdjustedClosing() / stockOntem.getAdjustedClosing()) / (ibovespaMap.get(stockHoje.getDate()) / ibovespaMap.get(stockOntem.getDate())));
//
//				if (precoCompra > 0){
//					countDias++;
//				}
//				
//				// compra
//				if (stockIbovespaRatio > 1.1 && precoCompra == 0){
//					System.out.println("[" + stockHoje.getSymbol() + "] " + stockHoje.getDate() + " / " + stockIbovespaRatio);
//					System.out.println("[COMPRA] " + stockHoje.getDate() + " / precoCompra=" + stockHoje.getAdjustedClosing());
//					precoCompra = stockHoje.getAdjustedClosing();
//				}
//				
//				// vende < 60 dias
//				if (stockHoje.getAdjustedClosing() > (precoCompra * 1.15) && precoCompra > 0 && countDias < 60){
//					System.out.println("[VENDA 60 DIAS] " + stockHoje.getDate() + " / precoVenda=" + stockHoje.getAdjustedClosing() + " / rentabilidade=" + (stockHoje.getAdjustedClosing() / precoCompra));
//					total = total * (stockHoje.getAdjustedClosing() / precoCompra);
//					precoCompra = 0;
//					countDias = 0;
//				}
//				
//				// vende < 120 dias
//				if (stockHoje.getAdjustedClosing() > (precoCompra * 1.10) && precoCompra > 0 && countDias > 60 && countDias < 120){
//					System.out.println("[VENDA 120 DIAS] " + stockHoje.getDate() + " / precoVenda=" + stockHoje.getAdjustedClosing() + " / rentabilidade=" + (stockHoje.getAdjustedClosing() / precoCompra));
//					total = total * (stockHoje.getAdjustedClosing() / precoCompra);
//					precoCompra = 0;
//					countDias = 0;
//				}
//				
//				// vende > 120 dias
//				if (stockHoje.getAdjustedClosing() > (precoCompra * 1.05) && precoCompra > 0 && countDias > 120){
//					System.out.println("[VENDA > 120 dias] " + stockHoje.getDate() + " / precoVenda=" + stockHoje.getAdjustedClosing() + " / rentabilidade=" + (stockHoje.getAdjustedClosing() / precoCompra));
//					total = total * (stockHoje.getAdjustedClosing() / precoCompra);
//					precoCompra = 0;
//					countDias = 0;
//				}
//				
//				// stop loss
//				if (stockHoje.getAdjustedClosing() < (precoCompra * 0.96) && precoCompra > 0){
//					System.out.println("[VENDA STOP LOSS] " + stockHoje.getDate() + " / precoVenda=" + stockHoje.getAdjustedClosing() + " / rentabilidade=" + (stockHoje.getAdjustedClosing() / precoCompra) + "#### stop loss ###");
//					if ((stockHoje.getAdjustedClosing() / precoCompra) > 0)
//						total = total * (stockHoje.getAdjustedClosing() / precoCompra);
//					precoCompra = 0;
//				}
//				
//				ultimoPreco = stockHoje.getAdjustedClosing();
//			}
//			
//			if (precoCompra > 0){
//				System.out.println("[VENDA FINAL PERIODO] " + " / precoVenda=" + ultimoPreco + " / rentabilidade=" + (ultimoPreco / precoCompra));
//				if ( (ultimoPreco / precoCompra) > 0)
//					total = total * (ultimoPreco / precoCompra);
//			}
//			System.out.println("\n");
//		}
//		
//		System.out.println("TOTAL = " + total);
//	}
	
	
	public static void main(String[] args) throws Exception {
		Calendar start = new GregorianCalendar(2010, Calendar.DECEMBER, 07);
		Date startDate = start.getTime();
		Calendar end = new GregorianCalendar(2013, Calendar.FEBRUARY, 21);
		Date endDate = end.getTime();

		List<StockQuote> listaAcao = LoadFromDB.load("AEDU3", startDate, endDate);
//		StockQuoteListUtils.populateStochasticOscillator14Days(listaAcao);
		List<StockQuote> listaIdx = LoadFromDB.load("IBOVESPA", startDate, endDate);
		HashMap<Date, Double> ibovespaMap = Utils.carregaMapDataValores(listaIdx);

		for (int i = 30; i < listaAcao.size(); i++){
			StockQuote stockHoje = listaAcao.get(i);
			StockQuote stockOntem = listaAcao.get(i-30);
			
			System.out.println(stockHoje.getDate() + "\t" + stockHoje.getAdjustedClosing() + "\t" +  ( (stockHoje.getAdjustedClosing()/stockOntem.getAdjustedClosing()) / (ibovespaMap.get(stockHoje.getDate()) / ibovespaMap.get(stockOntem.getDate())) ));
//			System.out.println(ibovespaMap.get(stockHoje.getDate()));
		}
	}
}
