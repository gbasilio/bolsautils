package br.gustavo.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.gustavo.commons.ConverterUtils;
import br.gustavo.types.StockQuote;

/**
 * 
 * @author gbasilio
 *
 */
public class LoadFromDB {

	/**
	 * 
	 * @param symbol
	 * @return
	 */
	public static List<StockQuote> loadStock(String symbol) throws Exception{
		return load(symbol, null, null);
	}

	/**
	 * 
	 * @param symbol
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static List<StockQuote> load(String symbol, Date startDate, Date endDate) throws Exception{

		Date inicio = new Date();

		List<StockQuote> listaRetorno = new ArrayList<StockQuote>();

		Connection conn = StockDbUtils.getConnection();
		PreparedStatement ps = conn.prepareStatement("SELECT * FROM stock_quote " +
				"WHERE UPPER(TRIM(codneg))=UPPER(TRIM(?)) AND data >= ? AND data <= ? ORDER BY data ASC");

		ps.setString(1, symbol);
		if (startDate != null){
			ps.setDate(2, new java.sql.Date(startDate.getTime()));
		}
		else{
			ps.setDate(2, new java.sql.Date(0));
		}
		if (endDate != null){
			ps.setDate(3, new java.sql.Date(endDate.getTime()));
		}
		else{
			ps.setDate(3, new java.sql.Date(new Date().getTime()));
		}

		ResultSet rs = ps.executeQuery();

		int c=0;
		while(rs.next()){
			c++;
			StockQuote stock = new StockQuote();
			stock.setSymbol(rs.getString("codneg"));
			stock.setVolume(rs.getDouble("voltot"));
			stock.setDayClosing(rs.getDouble("preult"));
			stock.setAdjustedClosing(rs.getDouble("adjclose"));
			stock.setDayOpening(rs.getDouble("preabe"));
			stock.setDayHighest(rs.getDouble("premax"));
			stock.setDayLowest(rs.getDouble("premin"));
			stock.setDate(rs.getDate("data"));
			listaRetorno.add(stock);
		}
//		System.out.println(c);

//		System.out.println((new Date().getTime() - inicio.getTime()));


		return listaRetorno;
	}

	/**
	 * 
	 * @param data
	 */
	public static double loadDollarQuote(Date data) throws Exception{
		Connection conn = StockDbUtils.getConnection();
		PreparedStatement ps = conn.prepareStatement("SELECT precomedio FROM dolar_quote " +
				"WHERE data = ?");

		ps.setDate(1, new java.sql.Date(data.getTime()));
		
		ResultSet rs = ps.executeQuery();
		
		if (rs.next()){
			return rs.getDouble(1);
		}
		else{
			throw new IllegalArgumentException();
		}
	}
	

//	public static void main(String[] args) throws Exception{
//		List<StockQuote> lista = loadStock("geti4");
//		System.out.println("lista.size() = " + lista.size());
//		
//		StockQuote stock = lista.get(2207);
//		System.out.println("Symbol= " + stock.getSymbol() + " / Data = " + stock.getDate() + " / dayClosing = " + stock.getDayClosing());
//	}
	
	
	public static void main(String[] args) throws Exception{
		
		Calendar start = new GregorianCalendar(2003, Calendar.JANUARY, 01);
		Date startDate = start.getTime();
		Calendar end = new GregorianCalendar(2013, Calendar.FEBRUARY, 20);
		Date endDate = end.getTime();
		
		List<StockQuote> listaAcoes = load("VALE3", startDate, endDate);
		
		for (StockQuote stock: listaAcoes){
			System.out.println("    {");
			System.out.println("     \"Date\": \"" + ConverterUtils.dateToStringyyyy_mm_dd(stock.getDate()) + "\",");
			System.out.println("     \"Open\": \"" + stock.getDayOpening() + "\",");
			System.out.println("     \"High\": \"" + stock.getDayHighest() + "\",");
			System.out.println("     \"Low\": \"" + stock.getDayLowest() + "\",");
			System.out.println("     \"Close\": \"" + stock.getDayClosing() + "\",");
			System.out.println("     \"Volume\": \"" + stock.getVolume().intValue() + "\",");
			System.out.println("     \"Adj_Close\": \"" + stock.getAdjustedClosing() + "\",");
			System.out.println("    },");
		}
	}
	
}
