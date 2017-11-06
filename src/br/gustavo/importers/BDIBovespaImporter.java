package br.gustavo.importers;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.apache.commons.lang3.StringUtils;

import br.gustavo.commons.ConverterUtils;

public class BDIBovespaImporter {


	public static void main(String[] args) throws Exception {
		// baixa o BDI de ontem (se ter�a, quarta, quinta ou sexta)
		// ou o BDI de sexta se segunda.
		GregorianCalendar cal = new GregorianCalendar();
		cal.set(Calendar.HOUR, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.TUESDAY ||
				cal.get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY ||
				cal.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY ||
				cal.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY){
			cal.add(Calendar.DATE, -1);
		}
		else if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY){
			cal.add(Calendar.DATE, -3);
		}
		else{
			throw new Exception("Nao existe BDI aos sabados e domingos!");
		}
		int dia = cal.get(Calendar.DAY_OF_MONTH);
		int mes = cal.get(Calendar.MONTH) + 1;

		String arquivo = "bdi" + StringUtils.leftPad(""+mes, 2, "0") + StringUtils.leftPad(""+dia, 2, "0") + ".zip";

		String urlArq = "http://www.bmfbovespa.com.br/fechamento-pregao/bdi/" + arquivo;
		URL url = new URL(urlArq);
		System.out.println("Baixando arquivo BDI = " + urlArq);
		InputStream in  = (InputStream)url.getContent();

		byte[] buffer = new byte[256];
		int bytesRead = in.read(buffer);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		while (bytesRead >= 0){
			baos.write(buffer, 0, bytesRead);
			bytesRead = in.read(buffer);
		}

		ZipInputStream zipIn = new ZipInputStream(new ByteArrayInputStream(baos.toByteArray()));


		ZipEntry entry;
		// busco as entradas no arquivo zip
		while ((entry = zipIn.getNextEntry()) != null)
		{
			System.out.println("entry: " + entry.getName() + ", " + entry.getSize());
			// se for o arquivo certo só deve ter uma entrada (BDIN), mas vale a pena verificar, just in case!
			if (entry.getName().toUpperCase().indexOf("BDIN") >= 0){
				// lê do arquivo zip
				byte[] BDINBuffer = new byte[256];
				baos = new ByteArrayOutputStream();
				while (zipIn.available() > 0){
					bytesRead = zipIn.read(BDINBuffer);
					if (bytesRead >= 0){
						baos.write(BDINBuffer, 0, bytesRead);
					}
				}
				// BufferedReader do arquivo BDIN descompactado
				BufferedReader BDINBufferedReader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(baos.toByteArray())));

				// Processa arquivo BDIN (importa dados para BD)
				String linha = BDINBufferedReader.readLine();
				while (linha != null){
//					System.out.println(linha);

					// índices
					if (linha != null && linha.startsWith("01")){

					}
					// cotacoes do lote padrao no mercado a vista
					else if (linha != null && linha.startsWith("0202")){						
						String codbdi = "02";
						String codneg = linha.substring(57, 69).trim();
						String tpmerc = linha.substring(69, 72).trim();
						String nomres = linha.substring(34, 46).trim();
						String especi = linha.substring(46, 56).trim();
						BigDecimal prazot = ConverterUtils.stringToBigDecimal(linha.substring(87, 90));
						BigDecimal preabe = ConverterUtils.stringToBigDecimal(linha.substring(90, 101));
						BigDecimal premax = ConverterUtils.stringToBigDecimal(linha.substring(101, 112));
						BigDecimal premin = ConverterUtils.stringToBigDecimal(linha.substring(112, 123));
						BigDecimal premed = ConverterUtils.stringToBigDecimal(linha.substring(123, 134));
						BigDecimal preult = ConverterUtils.stringToBigDecimal(linha.substring(134, 145));
						BigDecimal preofc = ConverterUtils.stringToBigDecimal(linha.substring(151, 162));
						BigDecimal preofv = ConverterUtils.stringToBigDecimal(linha.substring(162, 173));
						Long totneg = Long.parseLong(linha.substring(173, 178));
						Long quatot = Long.parseLong(linha.substring(178, 193));
						BigDecimal voltot = ConverterUtils.stringToBigDecimal(linha.substring(193, 210));
						BigDecimal preexe = ConverterUtils.stringToBigDecimal(linha.substring(210, 221));
						Long indopc = Long.parseLong(linha.substring(229, 230));
						Date datven = ConverterUtils.stringToDateyyyymmdd(linha.substring(221, 229));
						Long fatcot = Long.parseLong(linha.substring(245, 252));
						BigDecimal ptoexe = ConverterUtils.stringToBigDecimal(linha.substring(252, 265));
						String codisi = linha.substring(265, 277).trim();
						Long dismes = Long.parseLong(linha.substring(277, 280));
						
						System.out.println("Inserindo linha = [" + linha + "]");
						HistoricalQuotesImporter.insereLinhaNoBD(cal.getTime(),
								codbdi, 
								codneg, 
								tpmerc, 
								nomres, 
								especi, 
								prazot, 
								"--", 
								preabe, 
								premax, 
								premin, 
								premed, 
								preult, 
								preofc, 
								preofv, 
								totneg,
								quatot, 
								voltot, 
								preexe, 
								indopc, 
								datven, 
								fatcot, 
								ptoexe, 
								codisi, 
								dismes);
					}
					
					linha = BDINBufferedReader.readLine();
				}
				
				HistoricalQuotesImporter.printInfo();
			}

			zipIn.closeEntry();
		}

		zipIn.close();
	}




}
