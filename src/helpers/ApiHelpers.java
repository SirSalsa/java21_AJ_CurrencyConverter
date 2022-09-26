package helpers;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.time.LocalDate;
import java.util.ArrayList;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import models.HistoricalCurrency;

public class ApiHelpers {

	// Makes the convert API call with given variables
	public static String callConvertApi(String currency1, String currency2, double amount) {

		String returnString = ""; // Used in labels
		JSONParser parser = new JSONParser();

		try {
			// Creating URL with variables
			URL urlAdress = new URL("https://api.currencyscoop.com/v1/convert?api_key=64e1bf3a3d7ee7d878c70bec407ec1a1"
					+ "&from=" + currency1 + "&to=" + currency2 + "&amount=" + amount);

			URLConnection yc = urlAdress.openConnection();
			BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));

			// Fetching response and storing it as JSON object
			Object obj = parser.parse(in.readLine());
			JSONObject jsObject = (JSONObject) obj;

			String jsonString = jsObject.entrySet().toArray()[1].toString();

			// Removing "response" from JSON by finding first {
			int startIndex = jsonString.indexOf("{");
			String finalString = jsonString.substring(startIndex, jsonString.length());

			// Returning string to JSONObject so specific components can be called
			JSONObject ja = (JSONObject) parser.parse(finalString);
			String value = ja.get("value").toString();
			returnString = (value.substring(0, value.indexOf(".") + 3)) + " " + ja.get("to");

			return returnString;

		} catch (StringIndexOutOfBoundsException e) {
			e.printStackTrace();
			returnString = "ERROR: Incorrect currency!";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnString;
	}

	// Calls api with historical date and returns one HistoricalCurrency object
	private static HistoricalCurrency callHistoricalApi(String currency1, String currency2, LocalDate date) {
		double rate = 0.0;

		JSONParser parser = new JSONParser();

		try {
			// Creating URL with variables
			URL urlAdress = new URL(
					"https://api.currencyscoop.com/v1/historical?api_key=64e1bf3a3d7ee7d878c70bec407ec1a1" + "&base="
							+ currency1 + "&date=" + date);

			URLConnection yc = urlAdress.openConnection();
			BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));

			// Fetching response and storing it as JSON object
			Object obj = parser.parse(in.readLine());
			JSONObject jsObject = (JSONObject) obj;

			String jsonString = jsObject.entrySet().toArray()[1].toString();

			// Removing "response" from JSON by finding first {
			int startIndex = jsonString.indexOf("{");
			String finalString = jsonString.substring(startIndex, jsonString.length());

			// Returning string to JSONObject so specific components can be called
			JSONObject ja = (JSONObject) parser.parse(finalString);

			// Makes a string array of list of currency exchange rates
			String ratesToArray = ja.get("rates").toString().replace("{", "").replace("}", "");
			String[] rates = ratesToArray.split(",");

			// Looks for the corresponding currency and saves it
			for (Object object : rates) {
				if (object.toString().contains(currency2)) {
					String rateString = object.toString().substring(object.toString().indexOf(":") + 1);
					rate = Double.parseDouble(rateString.substring(0, rateString.indexOf(".") + 4));
				}
			}

		} catch (StringIndexOutOfBoundsException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		HistoricalCurrency newCurrency = new HistoricalCurrency(date, rate);
		return newCurrency;
	}

	// Manages all the calls and changes date based on previously selected format
	public static ArrayList<HistoricalCurrency> callAllHistoricalApis(String currency1, String currency2,
			String dateFormat) {
		ArrayList<HistoricalCurrency> currencyList = new ArrayList<>();

		LocalDate baseDate = LocalDate.now();

		if (dateFormat.equals("Years")) {
			currencyList.add(callHistoricalApi(currency1, currency2, baseDate.minusYears(3)));
			currencyList.add(callHistoricalApi(currency1, currency2, baseDate.minusYears(2)));
			currencyList.add(callHistoricalApi(currency1, currency2, baseDate.minusYears(1)));
			currencyList.add(callHistoricalApi(currency1, currency2, baseDate));
		} else if (dateFormat.equals("Months")) {
			currencyList.add(callHistoricalApi(currency1, currency2, baseDate.minusMonths(3)));
			currencyList.add(callHistoricalApi(currency1, currency2, baseDate.minusMonths(2)));
			currencyList.add(callHistoricalApi(currency1, currency2, baseDate.minusMonths(1)));
			currencyList.add(callHistoricalApi(currency1, currency2, baseDate));
		} else if (dateFormat.equals("Weeks")) {
			currencyList.add(callHistoricalApi(currency1, currency2, baseDate.minusWeeks(3)));
			currencyList.add(callHistoricalApi(currency1, currency2, baseDate.minusWeeks(2)));
			currencyList.add(callHistoricalApi(currency1, currency2, baseDate.minusWeeks(1)));
			currencyList.add(callHistoricalApi(currency1, currency2, baseDate));

		}

		return currencyList;
	}

}
