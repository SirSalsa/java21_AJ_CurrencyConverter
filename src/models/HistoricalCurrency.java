package models;

import java.time.LocalDate;

public class HistoricalCurrency {

	private LocalDate date;
	private double rate;

	public HistoricalCurrency(LocalDate date, double rate) {
		this.date = date;
		this.rate = rate;
	}

	public LocalDate getDate() {
		return date;
	}

	public double getRate() {
		return rate;
	}

}
