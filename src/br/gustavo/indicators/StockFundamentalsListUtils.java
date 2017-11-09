package br.gustavo.indicators;

import java.util.List;

import br.gustavo.types.StockFundamentals;


public class StockFundamentalsListUtils {


	/**
	 * 
	 * @param listaFundamentos
	 */
	public static double averagePL(List<StockFundamentals> listaFundamentos){
		if (listaFundamentos == null || listaFundamentos.size() == 0){
			return -9999;
		}
		
		double totalPL = 0;
		int qtd = 0;
		
		for (StockFundamentals stockFundamentals: listaFundamentos){
			totalPL += stockFundamentals.getP_l_ratio();
			qtd++;
		}
		
		return totalPL / qtd;
	}
	
	/**
	 * 
	 * @param listaFundamentos
	 */
	public static double averageCrescReceita5Anos(List<StockFundamentals> listaFundamentos){
		if (listaFundamentos == null || listaFundamentos.size() == 0){
			return -9999;
		}
		
		double totalCresc = 0;
		int qtd = 0;
		
		for (StockFundamentals stockFundamentals: listaFundamentos){
			totalCresc += stockFundamentals.getCresc_receita_ult_5_anos();
			qtd++;
		}
		
		return totalCresc / qtd;
	}
	
	/**
	 * 
	 * @param listaFundamentos
	 */
	public static double averagePrecoSobreAtivoCirculanteLiquido(List<StockFundamentals> listaFundamentos){
		if (listaFundamentos == null || listaFundamentos.size() == 0){
			return -9999;
		}
		
		double totalPrecoSobreAtivoCirculanteLiquido = 0;
		int qtd = 0;
		
		for (StockFundamentals stockFundamentals: listaFundamentos){
			totalPrecoSobreAtivoCirculanteLiquido += stockFundamentals.getPrice_ativo_circ_liq_ratio();
			qtd++;
		}
		
		return totalPrecoSobreAtivoCirculanteLiquido / qtd;
	}
	
	/**
	 * 
	 * @param listaFundamentos
	 */
	public static double averageROIC(List<StockFundamentals> listaFundamentos){
		if (listaFundamentos == null || listaFundamentos.size() == 0){
			return -9999;
		}
		
		double totalROIC = 0;
		int qtd = 0;
		
		for (StockFundamentals stockFundamentals: listaFundamentos){
			totalROIC += stockFundamentals.getRoic();
			qtd++;
		}
		
		return totalROIC / qtd;
	}
}

