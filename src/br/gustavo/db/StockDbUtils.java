package br.gustavo.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

/**
 * 
 * @author gbasilio
 *
 */
public class StockDbUtils {
	
	
	private static Connection conn = null;
	
	
	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public static Connection getConnection() throws Exception{
		if (conn == null){
			Class.forName("org.postgresql.Driver");
			String url = "jdbc:postgresql://localhost/bovespa";		
			Properties props = new Properties();
			props.setProperty("user","postgres");
			props.setProperty("password","password");
			//		props.setProperty("ssl","true");
			conn = DriverManager.getConnection(url, props);
		}
		
		return conn;
	}

}
