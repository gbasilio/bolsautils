package br.gustavo.indicators;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import br.gustavo.types.StockQuote;

/**
 * 
 * @author gbasilio
 *
 */
public class Utils {

	/**
	 * 
	 * @param valores
	 * @return
	 */
	private static double variancia(List<Double> valores){
		double media = mediaAritmetica(valores);
		
		double somatorio = 0;
		for (int i = 0; i < valores.size(); i++){
			somatorio += Math.pow((valores.get(i) - media), 2);
		}
		
		return (somatorio / (valores.size() - 1));
	}
	
	
	/**
	 * 
	 * @param valores
	 * @return
	 */
	public static double desvioPadrao(List<Double> valores){
		
		return Math.sqrt(variancia(valores));
	}
	
	
	/**
	 * 
	 * @param stockquote
	 */
	public static double mediaAritmetica(List<Double> valores){
		if (valores == null || valores.size() == 0){
			return 0;
		}
		
		double total = 0;
		for (int i = 0; i < valores.size(); i++){
			total += valores.get(i);	
		}
		
		return (total / valores.size());
		
	}
	
	
	/**
	 * 
	 * @param mediaMovelExponencialAnterior
	 * @param valor
	 * @param numDias
	 * @return
	 */
	public static double mediaMovelExponencial(double mediaMovelExponencialAnterior, double valor, int numDias){
		double alfa = (2 / ((double)numDias + 1));
		
		return (alfa * valor) + ((1 - alfa) * mediaMovelExponencialAnterior);
	}

	
	/**
	 * 
	 * @param listaIdx
	 * @return
	 */
	public static HashMap<Date, Double> carregaMapDataValores(List<StockQuote> lista){

		HashMap<Date, Double> retorno = new HashMap<Date, Double>();

		for (int i = 0; i < lista.size(); i++){
			StockQuote ibovespa = lista.get(i);
			retorno.put(ibovespa.getDate(), ibovespa.getDayClosing());
		}

		return retorno;
	}
	
	
	
	
	public static void main(String[] args) {
		System.out.println(mediaMovelExponencial(36.92095238095238, 37.62, 20));
		
		
		List lista = new ArrayList<Double>();
		
		
		lista.add(168158148d);
		lista.add(218206481d);
		lista.add(156513618d);
		lista.add(232979239d);
		lista.add(180018888d);
		lista.add(222379906d);
		lista.add(280664302d);
		lista.add(467629456d);
		lista.add(337591848d);
		lista.add(264806034d);
		lista.add(301732043d);
		lista.add(463878202d);
		lista.add(165419220d);
		lista.add(117949884d);
		lista.add(141141474d);
		lista.add(66779133d);
		lista.add(239800169d);
		lista.add(111149806d);
		lista.add(197561534d);
		lista.add(104757379d);
		lista.add(89427143d);
		lista.add(164254049d);
		lista.add(122960762d);
		lista.add(104918461d);
		lista.add(201761796d);
		lista.add(148854382d);
		lista.add(184544273d);
		lista.add(175379941d);
		lista.add(230065757d);
		lista.add(195754185d);
		lista.add(115440865d);
		lista.add(202841153d);
		lista.add(222553831d);
		lista.add(181385354d);
		lista.add(122824383d);
		lista.add(103755154d);
		lista.add(130972152d);
		lista.add(117244905d);
		lista.add(148176097d);
		lista.add(152624124d);
		lista.add(118727349d);
		lista.add(462730873d);
		lista.add(384437518d);
		lista.add(152065420d);
		lista.add(294931506d);
		lista.add(159300255d);
		lista.add(186039073d);
		lista.add(119568393d);
		lista.add(181843784d);
		lista.add(78087171d);
		lista.add(77395309d);
		lista.add(136035891d);
		lista.add(185599436d);
		lista.add(147916126d);
		lista.add(235512932d);
		lista.add(242522102d);
		lista.add(185564834d);
		lista.add(38791887d);
		lista.add(568317756d);
		lista.add(95063929d);
		lista.add(211022258d);
		lista.add(247782408d);
		lista.add(150285453d);
		lista.add(240266733d);
		lista.add(129933643d);
		lista.add(139800488d);
		lista.add(126239268d);
		lista.add(184771556d);
		lista.add(276219526d);
		lista.add(169919148d);
		lista.add(540010418d);
		lista.add(98875311d);
		lista.add(156892741d);
		lista.add(126567314d);
		lista.add(152439371d);
		lista.add(157150676d);
		lista.add(161527856d);
		lista.add(140477855d);
		lista.add(150096405d);
		lista.add(128281937d);
		lista.add(323411981d);
		lista.add(151720453d);
		lista.add(239358084d);
		lista.add(48631910d);
		lista.add(175021061d);
		lista.add(218325843d);
		lista.add(268167923d);
		lista.add(184450396d);
		lista.add(161158390d);
		lista.add(242423365d);
		lista.add(261387847d);
		lista.add(262460423d);
		lista.add(206052310d);
		lista.add(154122122d);
		lista.add(178018300d);
		lista.add(152379350d);
		lista.add(203362363d);
		lista.add(252575910d);
		lista.add(116176961d);
		lista.add(334459527d);
		lista.add(247733360d);
		lista.add(283204114d);
		lista.add(143474106d);
		lista.add(226550328d);
		lista.add(302665926d);
		lista.add(196426459d);
		lista.add(121588665d);
		lista.add(146993959d);
		lista.add(125062585d);
		lista.add(202464040d);
		lista.add(289745099d);
		lista.add(234577481d);
		lista.add(182890465d);
		lista.add(248763664d);
		lista.add(416036552d);
		lista.add(194370265d);
		lista.add(143997592d);
		lista.add(156709673d);
		lista.add(155656441d);
		lista.add(112224769d);
		lista.add(190845682d);
		lista.add(228100597d);
		lista.add(217912246d);
		lista.add(216505663d);
		lista.add(113027910d);
		lista.add(160224136d);
		lista.add(86702675d);
		lista.add(98579573d);
		lista.add(162824137d);
		lista.add(93857674d);
		lista.add(145341094d);
		lista.add(76234817d);
		lista.add(116134739d);
		lista.add(80974154d);
		lista.add(129143342d);
		lista.add(221296769d);
		lista.add(195842484d);
		lista.add(204587743d);
		lista.add(173125518d);
		lista.add(288734692d);
		lista.add(323200515d);
		lista.add(180136443d);
		lista.add(133561621d);
		lista.add(113455186d);
		lista.add(299566306d);
		lista.add(135212280d);
		lista.add(125997966d);
		lista.add(155461069d);
		lista.add(176327291d);
		lista.add(89045310d);
		lista.add(239226228d);
		lista.add(194202535d);
		lista.add(209309354d);
		lista.add(222376754d);
		lista.add(119939841d);
		lista.add(202121294d);
		lista.add(151103653d);
		lista.add(139323173d);
		lista.add(138331417d);
		lista.add(81620678d);
		lista.add(173667266d);
		lista.add(149346851d);
		lista.add(279404425d);
		lista.add(290583306d);
		lista.add(175385807d);
		lista.add(113170176d);
		lista.add(229635291d);
		lista.add(248748463d);
		lista.add(190059602d);
		lista.add(121417625d);
		lista.add(195489940d);
		lista.add(244257850d);
		lista.add(262979589d);
		lista.add(141711372d);
		lista.add(343619814d);
		lista.add(227188019d);
		lista.add(123066112d);
		lista.add(128465298d);
		lista.add(66755507d);
		lista.add(94466362d);
		lista.add(120906765d);
		lista.add(104359380d);
		lista.add(238621196d);
		lista.add(83187360d);
		lista.add(135328622d);
		lista.add(151369508d);
		lista.add(169161265d);
		lista.add(20388080d);
		lista.add(85148984d);
		lista.add(99803535d);
		lista.add(134170156d);
		lista.add(156984982d);
		lista.add(109180684d);
		lista.add(168150938d);
		lista.add(171294106d);
		lista.add(171060374d);
		lista.add(127336364d);
		lista.add(123207856d);
		lista.add(115644831d);
		lista.add(160424821d);
		lista.add(272002315d);
		lista.add(131302277d);
		lista.add(121494487d);
		lista.add(140941835d);
		lista.add(213808032d);
		lista.add(313944800d);
		lista.add(152516662d);
		lista.add(122120614d);
		lista.add(155961669d);
		lista.add(44122916d);

		
		System.out.println(mediaAritmetica(lista));  // OK
		
		
		List<Double> lista2 = new ArrayList<Double>();
		lista2.add(1000d);
		lista2.add(900d);
		lista2.add(845d);
		lista2.add(1245d);
		lista2.add(253d);
		lista2.add(800d);
		lista2.add(1500d);
		lista2.add(780d);
		
		System.out.println(desvioPadrao(lista2));  // OK
		
	}
}
