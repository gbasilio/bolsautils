package br.gustavo.test.main;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import br.gustavo.db.LoadFromDB;
import br.gustavo.test.strategies.singlestock.TrendLineStrategies;
import br.gustavo.types.StockQuote;

/**
 * 
 * @author gbasilio
 *
 */
public class PortfolioMonitor {
	
	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) throws Exception{
//		String[] acoes = {"BBAS3",
//				"BRKM5",
//				"ELET6",
//				"GETI4",
//				"ITUB4",
//				"JBSS3",
//				"MILK11",
//				"PETR4",
//				"PIBB11",
//				"RCSL4",
//				"VAGR3",
//				"VALE3"
//				};
		
		String[] acoes = {"BBAS3",
				"PETR4",
				"ELET6"
			};
		
		for (int i = 0; i < acoes.length; i++){
			// carrega informações diarias da acao
			Calendar start = new GregorianCalendar(2012, Calendar.SEPTEMBER, 01);
			Date startDate = start.getTime();
			Calendar end = new GregorianCalendar(2020, Calendar.MARCH, 30);
			Date endDate = end.getTime();
			List<StockQuote> listaAcoes = LoadFromDB.load(acoes[i], startDate, endDate);
			
			
			TrendLineStrategies.verifyActionToTakeShortestTerm(listaAcoes);
			System.out.println("\n");
		}
	}
}
