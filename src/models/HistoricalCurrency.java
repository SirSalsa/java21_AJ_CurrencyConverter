package models;

import java.time.LocalDate;
import java.util.ArrayList;

public class HistoricalCurrency {

	private LocalDate date;
	private String baseCurrency;
	private String targetCurrency;
	private ArrayList<HistoricalRate> ratesList = new ArrayList<>();
	
}
