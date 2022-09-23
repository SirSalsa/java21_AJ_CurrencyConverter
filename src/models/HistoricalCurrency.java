package models;

import java.time.LocalDate;
import java.util.ArrayList;

public class HistoricalCurrency {

	private LocalDate date;
	private String baseCurrency;
	private String targetCurrency;
	private ArrayList<HistoricalRate> ratesList = new ArrayList<>();
	
	public LocalDate getDate() {
		return date;
	}
	public String getBaseCurrency() {
		return baseCurrency;
	}
	public String getTargetCurrency() {
		return targetCurrency;
	}
	public ArrayList<HistoricalRate> getRatesList() {
		return ratesList;
	}
}
