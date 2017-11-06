package br.gustavo.commons;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author gbasilio
 *
 */
public class ConverterUtils {
	
	/**
	 * 
	 * @param dataString
	 * @return
	 * @throws Exception
	 */
	public static Date stringToDateyyyymmdd(String dataString) throws Exception{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		return sdf.parse(dataString);
	}

	
	/**
	 * 
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public static String dateToStringyyyy_mm_dd(Date data) throws Exception{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(data);
	}
	

	/**
	 * 
	 * @param valorString
	 * @return
	 */
	public static BigDecimal stringToBigDecimal(String valorString){
		if (valorString == null || valorString.trim().length() == 0){
			return null;
		}

		String parteInteira = valorString.substring(0, valorString.length()-2);
		String parteDecimal = valorString.substring(valorString.length()-2, valorString.length());

		//		System.out.println(valorString + " / " + parteInteira + " / " + parteDecimal);

		return new BigDecimal(parteInteira + "." + parteDecimal);
	}
}
