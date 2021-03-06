package br.gustavo.importers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Date;

import br.gustavo.commons.ConverterUtils;
import br.gustavo.db.StockDbUtils;

public class HistoricalQuotesImporter {

//	private final static String FILE = "/home/gbasilio/Desktop/bovespa/COTAHIST_A2003.TXT";
//	private final static String FILE = "/home/gbasilio/Desktop/bovespa/COTAHIST_A2004.TXT";
//	private final static String FILE = "/home/gbasilio/Desktop/bovespa/COTAHIST_A2005.TXT";
//	private final static String FILE = "/home/gbasilio/Desktop/bovespa/COTAHIST_A2006.TXT";
//	private final static String FILE = "/home/gbasilio/Desktop/bovespa/COTAHIST_A2007.TXT";
//	private final static String FILE = "/home/gbasilio/Desktop/bovespa/COTAHIST_A2008.TXT";
//	private final static String FILE = "/home/gbasilio/Desktop/bovespa/COTAHIST_A2009.TXT";
//	private final static String FILE = "/home/gbasilio/Desktop/bovespa/COTAHIST_A2010.TXT";
//	private final static String FILE = "/home/gbasilio/Desktop/bovespa/COTAHIST_A2011.TXT";
//	private final static String FILE = "/home/gbasilio/Desktop/bovespa/COTAHIST_A2012.TXT";
//	private final static String FILE = "/home/gbasilio/Desktop/bovespa/COTAHIST_A2013.TXT";
	private final static String FILE = "/home/rodrigo/Downloads/hist_ibovespa/COTAHIST_A2018.TXT";
	
//		private final static String FILE  = "C:\\Users\\gbasilio\\Desktop\\bovespa\\COTAHIST_A2003.TXT";
//		private final static String FILE  = "C:\\Users\\gbasilio\\Desktop\\bovespa\\COTAHIST_A2004.TXT";
//		private final static String FILE  = "C:\\Users\\gbasilio\\Desktop\\bovespa\\COTAHIST_A2005.TXT";
//		private final static String FILE  = "C:\\Users\\gbasilio\\Desktop\\bovespa\\COTAHIST_A2006.TXT";
//		private final static String FILE  = "C:\\Users\\gbasilio\\Desktop\\bovespa\\COTAHIST_A2007.TXT";
//		private final static String FILE  = "C:\\Users\\gbasilio\\Desktop\\bovespa\\COTAHIST_A2008.TXT";
//		private final static String FILE  = "C:\\Users\\gbasilio\\Desktop\\bovespa\\COTAHIST_A2009.TXT";
//		private final static String FILE  = "C:\\Users\\gbasilio\\Desktop\\bovespa\\COTAHIST_A2010.TXT";
//		private final static String FILE  = "C:\\Users\\gbasilio\\Desktop\\bovespa\\COTAHIST_A2011.TXT";
//		private final static String FILE  = "C:\\Users\\gbasilio\\Desktop\\bovespa\\COTAHIST_A2012.TXT";
//		private final static String FILE  = "C:\\Users\\gbasilio\\Desktop\\bovespa\\COTAHIST_A2013.TXT";


	private static Date data;
	private static String codbdi;
	private static String codneg;
	private static String tpmerc;
	private static String nomres;
	private static String especi;
	private static BigDecimal prazot;
	private static String modref;
	private static BigDecimal preabe;
	private static BigDecimal premax;
	private static BigDecimal premin;
	private static BigDecimal premed;
	private static BigDecimal preult;
	private static BigDecimal preofc;
	private static BigDecimal preofv;
	private static Long totneg; 
	private static Long quatot;
	private static BigDecimal voltot;
	private static BigDecimal preexe;
	private static Long indopc;
	private static Date datven;
	private static Long fatcot;
	private static BigDecimal ptoexe;
	private static String codisi;
	private static Long  dismes;

	private static PreparedStatement ps = null;
	private static Connection conn = null;

	private static int numTotalRegistros = 0;
	private static int numRegistrosImportados = 0;
	private static Date initialTime;


	public static void main(String[] args) throws Exception {
		initialTime = new Date();
		BufferedReader br = new BufferedReader(new FileReader(new File(FILE)));

		String linha = br.readLine();
		while (linha != null){
			if (linha.startsWith("01")){
				numTotalRegistros++;
				data = ConverterUtils.stringToDateyyyymmdd(linha.substring(2, 10));
				codbdi = linha.substring(10, 12).trim();
				codneg = linha.substring(12, 24).trim();
				tpmerc = linha.substring(24, 27).trim();
				nomres = linha.substring(27, 39).trim();
				especi = linha.substring(39, 49).trim();
				prazot = ConverterUtils.stringToBigDecimal(linha.substring(49, 52));
				modref = linha.substring(52, 56).trim();
				preabe = ConverterUtils.stringToBigDecimal(linha.substring(56, 69));
				premax = ConverterUtils.stringToBigDecimal(linha.substring(69, 82));
				premin = ConverterUtils.stringToBigDecimal(linha.substring(82, 95));
				premed = ConverterUtils.stringToBigDecimal(linha.substring(95, 108));
				preult = ConverterUtils.stringToBigDecimal(linha.substring(108, 121));
				preofc = ConverterUtils.stringToBigDecimal(linha.substring(121, 134));
				preofv = ConverterUtils.stringToBigDecimal(linha.substring(134, 147));
				totneg = Long.parseLong(linha.substring(147, 152));
				quatot = Long.parseLong(linha.substring(152, 170));
				voltot = ConverterUtils.stringToBigDecimal(linha.substring(170, 188));
				preexe = ConverterUtils.stringToBigDecimal(linha.substring(188, 201));
				indopc = Long.parseLong(linha.substring(201, 202));
				datven = ConverterUtils.stringToDateyyyymmdd(linha.substring(202, 210));
				fatcot = Long.parseLong(linha.substring(210, 217));
				ptoexe = ConverterUtils.stringToBigDecimal(linha.substring(217, 230));
				codisi = linha.substring(230, 242).trim();
				dismes = Long.parseLong(linha.substring(242, 245));

				if (tpmerc.equals("010")) { // somente mercado a vista
//					System.out.println("[data = " + data + "] " +
//							"[codbdi = " + codbdi + "] " +
//							"[codneg = " + codneg + "] " +
//							"[tpmerc = " + tpmerc + "] " +
//							"[nomres = " + nomres + "] " +
//							"[especi = " + especi + "] " +
//							"[prazot = " + prazot + "] " +
//							"[modref = " + modref + "] " +
//							"[preabe = " + preabe + "] " +
//							"[premax = " + premax + "] " +
//							"[premin = " + premin + "] " +
//							"[premed = " + premed + "] " +
//							"[preult = " + preult + "] " +
//							"[preofc = " + preofc + "] " +
//							"[preofv = " + preofv + "] " +
//							"[totneg = " + totneg + "] " +
//							"[quatot = " + quatot + "] " +
//							"[voltot = " + voltot + "] " +
//							"[preexe = " + preexe + "] " +
//							"[indopc = " + indopc + "] " +
//							"[datven = " + datven + "] " +
//							"[fatcot = " + fatcot + "] " +
//							"[ptoexe = " + ptoexe + "] " +
//							"[codisi = " + codisi + "] " +
//							"[dismes = " + dismes + "] "
//							);

					insereLinhaNoBD(data, codbdi, codneg, tpmerc, nomres, especi, prazot, modref, preabe, premax,
							premin, premed, preult, preofc, preofv, totneg, quatot, voltot, preexe, indopc, 
							datven, fatcot, ptoexe, codisi, dismes);
					
					if (numRegistrosImportados % 5000 == 0){
						conn.commit();
						System.out.println("Committing... Total até o momento="+numRegistrosImportados);
					}
				}

			}
			linha = br.readLine();
		}
		
		conn.commit();
		
		printInfo();
	}

	/**
	 * 
	 */
	public static void printInfo(){
		System.out.println("\n");
		System.out.println("numTotalRegistros = " + numTotalRegistros);
		System.out.println("numRegistrosImportados = " + numRegistrosImportados);
		System.out.println("Em " + (new Date().getTime()-initialTime.getTime())/1000d + " segundos.");
		
		numTotalRegistros = 0;
		numRegistrosImportados = 0;
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
	public static void insereLinhaNoBD(Date data, String codbdi, String codneg, String tpmerc, String nomres, 
			String especi, BigDecimal prazot, String modref, BigDecimal preabe, BigDecimal premax,
			BigDecimal premin, BigDecimal premed, BigDecimal preult, BigDecimal preofc, BigDecimal preofv, 
			Long totneg, Long quatot, BigDecimal voltot, BigDecimal preexe, Long indopc, 
			Date datven, Long fatcot, BigDecimal ptoexe, String codisi, Long dismes) throws Exception{

		prepareDB();


		ps.setDate(1, (new java.sql.Date(data.getTime())));
		ps.setString(2, codbdi);
		ps.setString(3, codneg);
		ps.setString(4, tpmerc);
		ps.setString(5, nomres);
		ps.setString(6, especi);
		ps.setBigDecimal(7, prazot);
		ps.setString(8, modref);
		ps.setBigDecimal(9, preabe);
		ps.setBigDecimal(10, premax);
		ps.setBigDecimal(11, premin);
		ps.setBigDecimal(12, premed);
		ps.setBigDecimal(13, preult);
		ps.setBigDecimal(14, preofc);
		ps.setBigDecimal(15, preofv);
		ps.setLong(16, totneg);
		ps.setLong(17, quatot);
		ps.setBigDecimal(18, voltot);
		ps.setBigDecimal(19, preexe);
		ps.setLong(20, indopc);
		ps.setDate(21, (new java.sql.Date(datven.getTime())));
		ps.setLong(22, fatcot);
		ps.setBigDecimal(23, ptoexe);
		ps.setString(24, codisi);
		ps.setLong(25,  dismes);

		try{
			ps.executeUpdate();
			numRegistrosImportados++;
		}
		catch(org.postgresql.util.PSQLException e){
			System.out.println("[data = " + data + "] [codneg = " + codneg + "] " + e.getMessage());
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

		if (ps == null){
			conn = StockDbUtils.getConnection();
			conn.setAutoCommit(false);
			ps = conn.prepareStatement("INSERT INTO stock_quote " +
					"(data,codbdi,codneg,tpmerc,nomres,especi,prazot,modref,preabe,premax,premin,premed,preult,preofc,preofv," +
					"totneg,quatot,voltot,preexe,indopc,datven,fatcot,ptoexe,codisi,dismes) " +
					"VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
		}
	}
}
