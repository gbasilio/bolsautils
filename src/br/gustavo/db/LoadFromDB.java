package br.gustavo.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import br.gustavo.commons.ConverterUtils;
import br.gustavo.types.StockFundamentals;
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

//		Date inicio = new Date();

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
	
	public static List<StockFundamentals> loadFundamentals(String symbol, Date startDate, Date endDate) throws Exception{
//		Date inicio = new Date();

		List<StockFundamentals> listaRetorno = new ArrayList<StockFundamentals>();

		Connection conn = StockDbUtils.getConnection();
		PreparedStatement ps = conn.prepareStatement("SELECT * FROM stock_fundamentals " +
				"WHERE UPPER(TRIM(symbol))=UPPER(TRIM(?)) AND data >= ? AND data <= ? ORDER BY data ASC");

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
			StockFundamentals stockFundamentals = new StockFundamentals();
			stockFundamentals.setSymbol(rs.getString("symbol"));
			stockFundamentals.setP_l_ratio(rs.getDouble("p_l_ratio"));
			stockFundamentals.setP_vp_ratio(rs.getDouble("p_vp_ratio"));
			stockFundamentals.setPrice_sales_ratio(rs.getDouble("price_sales_ratio"));
			stockFundamentals.setDivyield(rs.getDouble("divyield"));
			stockFundamentals.setPrice_ativos_ratio(rs.getDouble("price_ativos_ratio"));
			stockFundamentals.setPrice_capgiro_ratio(rs.getDouble("price_capgiro_ratio"));
			stockFundamentals.setPrice_ebit_ratio(rs.getDouble("price_ebit_ratio"));
			stockFundamentals.setPrice_ativo_circ_liq_ratio(rs.getDouble("price_ativo_circ_liq_ratio"));
			stockFundamentals.setEv_ebit_ratio(rs.getDouble("ev_ebit_ratio"));
			stockFundamentals.setMrg_ebit(rs.getDouble("mrg_ebit"));
			stockFundamentals.setMgr_liq(rs.getDouble("mgr_liq"));
			stockFundamentals.setLiquidez_corr(rs.getDouble("liquidez_corr"));
			stockFundamentals.setRoic(rs.getDouble("roic"));
			stockFundamentals.setRoe(rs.getDouble("roe"));
			stockFundamentals.setLiq_2_meses(rs.getDouble("liq_2_meses"));
			stockFundamentals.setPatr_liq(rs.getDouble("patr_liq"));
			stockFundamentals.setDivbruta_patrimonio_ratio(rs.getDouble("divbruta_patrimonio_ratio"));
			stockFundamentals.setCresc_receita_ult_5_anos(rs.getDouble("cresc_receita_ult_5_anos"));
			stockFundamentals.setDate(rs.getDate("data"));
			listaRetorno.add(stockFundamentals);
		}
//		System.out.println(c);

//		System.out.println((new Date().getTime() - inicio.getTime()));


		return listaRetorno;
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
