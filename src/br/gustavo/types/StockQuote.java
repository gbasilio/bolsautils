package br.gustavo.types;

import java.util.Date;

public class StockQuote {

	private String symbol;
	private Date date;
	private Double dayClosing;
	private Double adjustedClosing;
	private Double dayOpening;
	private Double dayHighest;
	private Double dayLowest;
	private Double minLast200Days;
	private Double maxLast200Days;
	private Double volume;
	private Double _12DayEMA;
	private Double _26DayEMA;
	private Double _4DayAMA;
	private Double _9DayAMA;
	private Double _18DayAMA;
	private Double _25DayAMA;
	private Double _50DayAMA;
	private Double _100DayAMA;
	private Double _200DayAMA;
	private Double roc20Days;
	private Double _200DaysEMAOfRoc20Days;
	private Double stochasticOscillator14Days;
	private Double stochasticOscillator200Days;
	private Double bollingerUpperBand;
	private Double bollingerMiddleBand;
	private Double bollingerLowerBand;
	private Double RSI14Days;
	private Double RSI5Days;
	private boolean moreThan10DaysBelowIbovespa = false;

	/**
	 * Getters e setters.
	 * 
	 * @return
	 */
	public String getSymbol() {
		return symbol;
	}
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Double getDayClosing() {
		return dayClosing;
	}
	public void setDayClosing(Double dayClosing) {
		this.dayClosing = dayClosing;
	}
	public Double getVolume() {
		return volume;
	}
	public void setVolume(Double volume) {
		this.volume = volume;
	}
	public Double get_12DayEMA() {
		return _12DayEMA;
	}
	public void set_12DayEMA(Double _12DayEMA) {
		this._12DayEMA = _12DayEMA;
	}
	public Double get_26DayEMA() {
		return _26DayEMA;
	}
	public void set_26DayEMA(Double _26DayEMA) {
		this._26DayEMA = _26DayEMA;
	}
	public Double get_4DayAMA() {
		return _4DayAMA;
	}
	public void set_4DayAMA(Double _4DayAMA) {
		this._4DayAMA = _4DayAMA;
	}
	public Double get_9DayAMA() {
		return _9DayAMA;
	}
	public void set_9DayAMA(Double _9DayAMA) {
		this._9DayAMA = _9DayAMA;
	}
	public Double get_18DayAMA() {
		return _18DayAMA;
	}
	public void set_18DayAMA(Double _18DayAMA) {
		this._18DayAMA = _18DayAMA;
	}
	public Double get_25DayAMA() {
		return _25DayAMA;
	}
	public void set_25DayAMA(Double _25DayAMA) {
		this._25DayAMA = _25DayAMA;
	}
	public Double get_50DayAMA() {
		return _50DayAMA;
	}
	public void set_50DayAMA(Double _50DayAMA) {
		this._50DayAMA = _50DayAMA;
	}
	public Double get_100DayAMA() {
		return _100DayAMA;
	}
	public void set_100DayAMA(Double _100DayAMA) {
		this._100DayAMA = _100DayAMA;
	}
	public Double get_200DayAMA() {
		return _200DayAMA;
	}
	public void set_200DayAMA(Double _200DayAMA) {
		this._200DayAMA = _200DayAMA;
	}
	public Double getStochasticOscillator14Days() {
		return stochasticOscillator14Days;
	}
	public void setStochasticOscillator14Days(Double stochasticOscillator) {
		this.stochasticOscillator14Days = stochasticOscillator;
	}
	public Double getBollingerUpperBand() {
		return bollingerUpperBand;
	}
	public void setBollingerUpperBand(Double bollingerUpperBand) {
		this.bollingerUpperBand = bollingerUpperBand;
	}
	public Double getBollingerMiddleBand() {
		return bollingerMiddleBand;
	}
	public void setBollingerMiddleBand(Double bollingerMiddleBand) {
		this.bollingerMiddleBand = bollingerMiddleBand;
	}
	public Double getBollingerLowerBand() {
		return bollingerLowerBand;
	}
	public void setBollingerLowerBand(Double bollingerLowerBand) {
		this.bollingerLowerBand = bollingerLowerBand;
	}
	public Double getStochasticOscillator200Days() {
		return stochasticOscillator200Days;
	}
	public void setStochasticOscillator200Days(Double stochasticOscillator200Days) {
		this.stochasticOscillator200Days = stochasticOscillator200Days;
	}
	public Double getDayHighest() {
		return dayHighest;
	}
	public void setDayHighest(Double dayHighest) {
		this.dayHighest = dayHighest;
	}
	public Double getDayLowest() {
		return dayLowest;
	}
	public void setDayLowest(Double dayLowest) {
		this.dayLowest = dayLowest;
	}
	public Double getMinLast200Days() {
		return minLast200Days;
	}
	public void setMinLast200Days(Double minLast200Days) {
		this.minLast200Days = minLast200Days;
	}
	public Double getMaxLast200Days() {
		return maxLast200Days;
	}
	public void setMaxLast200Days(Double maxLast200Days) {
		this.maxLast200Days = maxLast200Days;
	}
	public Double getRoc20Days() {
		return roc20Days;
	}
	public void setRoc20Days(Double roc20Days) {
		this.roc20Days = roc20Days;
	}
	public Double get_200DaysEMAOfRoc20Days() {
		return _200DaysEMAOfRoc20Days;
	}
	public void set_200DaysEMAOfRoc20Days(Double _200DaysEMAOfRoc20Days) {
		this._200DaysEMAOfRoc20Days = _200DaysEMAOfRoc20Days;
	}
	public Double getDayOpening() {
		return dayOpening;
	}
	public void setDayOpening(Double dayOpening) {
		this.dayOpening = dayOpening;
	}
	public Double getRSI14Days() {
		return RSI14Days;
	}
	public void setRSI14Days(Double rSI14Days) {
		RSI14Days = rSI14Days;
	}
	public Double getRSI5Days() {
		return RSI5Days;
	}
	public void setRSI5Days(Double rSI5Days) {
		RSI5Days = rSI5Days;
	}
	public Double getAdjustedClosing() {
		return adjustedClosing;
	}
	public void setAdjustedClosing(Double adjustedClosing) {
		this.adjustedClosing = adjustedClosing;
	}
	public boolean isMoreThan10DaysBelowIbovespa() {
		return moreThan10DaysBelowIbovespa;
	}
	public void setMoreThan10DaysBelowIbovespa(boolean moreThan10DaysBelowIbovespa) {
		this.moreThan10DaysBelowIbovespa = moreThan10DaysBelowIbovespa;
	}


}
