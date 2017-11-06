package br.gustavo.importers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import br.gustavo.db.StockDbUtils;

public class DollarImporter {


//	private final static String FILE = "/home/gbasilio/Desktop/dolar/soma_de_todos.csv";

	private final static String FILE  = "C:\\Users\\gbasilio\\Desktop\\bovespa\\soma_de_todos.csv";

	private static Date data;
	private static BigDecimal precoMedio;

	private static PreparedStatement ps = null;

	private static int numTotalRegistros = 0;
	private static int numRegistrosImportados = 0;


	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new FileReader(new File(FILE)));

		String linha = br.readLine();
		while (linha != null){
			numTotalRegistros++;
			data = stringToDateddmmyyyy(linha.substring(0, 8));
			BigDecimal precoCompra = stringToBigDecimal(linha.substring(19, 33));
			BigDecimal precoVenda = stringToBigDecimal(linha.substring(34, 48));
			precoMedio = (precoCompra.add(precoVenda)).divide(new BigDecimal(2));

			insereLinhaNoBD(data, precoMedio);

			linha = br.readLine();
		}

		System.out.println("numTotalRegistros = " + numTotalRegistros);
		System.out.println("numRegistrosImportados = " + numRegistrosImportados);
	}

	/**
	 * 
	 * @param data
	 * @param codbdi
	 * @param codneg
	 * @param tpmerc
	 * @param nomres
	 * @param especi
	 * @param prazot
	 * @param modref
	 * @param preabe
	 * @param premax
	 * @param premin
	 * @param premed
	 * @param preult
	 * @param preofc
	 * @param preofv
	 * @param totneg
	 * @param quatot
	 * @param voltot
	 * @param preexe
	 * @param indopc
	 * @param datven
	 * @param fatcot
	 * @param ptoexe
	 * @param codisi
	 * @param dismes
	 */
	private static void insereLinhaNoBD(Date data, BigDecimal precoMedio) throws Exception{

		prepareDB();

		ps.setDate(1, (new java.sql.Date(data.getTime())));
		ps.setBigDecimal(2, precoMedio);

		try{
			ps.executeUpdate();
			numRegistrosImportados++;
		}
		catch(org.postgresql.util.PSQLException e){
			System.out.println("[data = " + data + "] [precoMedio = " + precoMedio + "] " + e.getMessage());
			if (e.getMessage().toLowerCase().indexOf("duplicate key value violates unique constraint") < 0 &&
					e.getMessage().toLowerCase().indexOf("duplicar valor da chave viola a restri") < 0){
				throw e;
			}
		}

	}


	/**
	 * 
	 * @throws Exception
	 */
	private static void prepareDB() throws Exception{

		Connection conn = StockDbUtils.getConnection();

		if (ps == null){
			ps = conn.prepareStatement("INSERT INTO dolar_quote " +
					"(data, precomedio)" +
					"VALUES (?,?)");
		}
	}


	/**
	 * 
	 * @param dataString
	 * @return
	 * @throws Exception
	 */
	private static Date stringToDateddmmyyyy(String dataString) throws Exception{
		SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");
		return sdf.parse(dataString);
	}


	/**
	 * 
	 * @param valorString
	 * @return
	 */
	private static BigDecimal stringToBigDecimal(String valorString){
		if (valorString == null || valorString.trim().length() == 0){
			return null;
		}

		String[] valoresSeparados = valorString.split(",");
		
		String parteInteira = valoresSeparados[0];
		String parteDecimal = valoresSeparados[1];

		System.out.println(valorString + " / " + parteInteira + " / " + parteDecimal);

		return new BigDecimal(parteInteira + "." + parteDecimal);
	}
}
