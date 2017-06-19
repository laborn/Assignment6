package adapter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URLConnection;
import java.util.InputMismatchException;
import java.util.Scanner;

import interpreter.YahooInterpreter;
import singleton.Logger;
import singleton.Reader;

/**
 * The DataAdapter class contains the methods for
 * access to Yahoo Finance investment statistics
 * 
 * @author LuAnn Born
 */
public class DataAdapter {

	static Logger logfile = Logger.getInstance();
	static Scanner input = Reader.getInstance();
	
	/**
	 * 
	 */
	public DataAdapter() {
		super();
	}
	
	/**
	 * The setStatisticsChoice method is used to form the URL
	 * and retrieve an investment's statistics by prompting
	 * for the investment symbol and using the getStatisticsChoice
	 * method to get the user's choice of statistics.
	 * 
	 * @throws IOException
	 */
	public static void setStatisticsChoice() throws IOException {

		System.out.println("Enter one or more stock or mutual fund symbols separated by commas: ");
		// Get the investment symbols and statistics request from the user:
		try {
			String ticker = input.next();
			String symbol = "\"" + ticker + "\"";
			String statistics = "";
			System.out.println("\n" + "Requested the following: " + symbol + "\n");
			String menuChoice = chooseStatistics();
			System.out.println(menuChoice);
			statistics = getStatisticsChoice(menuChoice);
			YahooFinance yf = new YahooFinance(symbol, statistics);
			URLConnection urlConn = yf.getURL(symbol, statistics).openConnection();
			BufferedReader buffer = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
			String line = buffer.readLine();
			while (line != null){
				logfile.append(line);
				String[] splitLine = line.split(",");
				for (int i = 0; i < splitLine.length; i++){
					System.out.print(splitLine[i] + "\t");
				}
				line = buffer.readLine();
				System.out.println("");
			}
			System.out.println("\n");
		} catch (InputMismatchException e) {
			System.out.println("Not investment symbols. Try again.");
		}
	}

	/**
	 * The getStatisticsChoice method takes the user's choice of statistics and
	 * appends it to the codes for investment symbol and name to form the
	 * statistics string used in the Yahoo Finance URL.
	 * 
	 * @param menuChoice
	 * @return statistics
	 * @throws IOException
	 */
	public static String getStatisticsChoice(String menuChoice) throws IOException {

		String statistics = "sn";

		for (int i = 0; i < menuChoice.length(); i++) {
			if (menuChoice.charAt(i) == '1') {
				statistics = statistics + "l1t1";
				logfile.append("Chose last trade price\n");
			} else if (menuChoice.charAt(i) == '2') {
				statistics = statistics + "r";
				logfile.append("Chose P/E ratio\n");
			} else if (menuChoice.charAt(i) == '3') {
				statistics = statistics + "r5";
				logfile.append("Chose PEG ratio\n");
			} else if (menuChoice.charAt(i) == ',') {
				logfile.append("Comma found\n");
			} else if (menuChoice.charAt(i) == '4') {
				logfile.append("Chose to exit\n");
				break;
			}
		}
		return statistics;
	}

	/**
	 * The chooseStatistics method gives the user a choice of statistics
	 * 
	 * @return statsChoice
	 * @throws InputMismatchException
	 */
	public static String chooseStatistics() throws InputMismatchException {

		System.out.println("Choose statistics by number separated by commas: ");
		System.out.println("1. Last Trade Price");
		System.out.println("2. P/E Ratio");
		System.out.println("3. PEG");
		System.out.println("4. Exit");

		// Get user's choice:
		try {
			String statsChoice = input.next();
			return statsChoice;
		} catch (InputMismatchException e) {
			System.out.println("Not numbers from 1 to 4 separated by commas. Try again.");
			return null;
		}
	}
	
	/**
	 * The getLastPrice uses the investment's symbol to get the last price
	 * of the investment in trading.
	 * 
	 * @param symbol
	 * @return price
	 * @throws IOException
	 */
	public static double getLastPrice(String symbol) throws IOException{
		// Get the purchase price (last trade price) for an investment
		String lastPrice;
		double price = 0;
		try {
			String statistics = "snl1";
			YahooFinance yf = new YahooFinance(symbol, statistics);
			URLConnection urlConn = yf.getURL(symbol, statistics).openConnection();
			InputStreamReader inStream = new InputStreamReader(urlConn.getInputStream());
			BufferedReader buffer = new BufferedReader(inStream);
			String line = buffer.readLine();
			System.out.println("\n" + YahooInterpreter.getEnglish().get("s") + " " + YahooInterpreter.getEnglish().get("n") + " " + YahooInterpreter.getEnglish().get("l1"));
			System.out.println(line);
			while (line != null) {
				logfile.append(line);
				logfile.append("Used YahooInterpreter here.\n");
				System.out.println();
				String[] splitLine = line.split(",");
				lastPrice = splitLine[2];
				price = Double.parseDouble(lastPrice);
				line = buffer.readLine();
			}
		} catch (InputMismatchException e) {
			System.out.println("Not investment symbols. Try again.");
		}
		return price;
	}
	
	/**
	 * The getStockListLastPrice uses the investment's symbol to get the symbol and last price
	 * of the investment in trading. 
	 * @param symbol
	 * @return price
	 * @throws IOException
	 */
	public static double getStockListLastPrice(String symbol) throws IOException{
		// Get the purchase price (last trade price) for an investment
		String lastPrice;
		double price = 0;
		try {
			String statistics = "sl1";
			YahooFinance yf = new YahooFinance(symbol, statistics);
			URLConnection urlConn = yf.getURL(symbol, statistics).openConnection();
			InputStreamReader inStream = new InputStreamReader(urlConn.getInputStream());
			BufferedReader buffer = new BufferedReader(inStream);
			String line = buffer.readLine();
			while (line != null) {
				logfile.append(line);
				System.out.println();
				String[] splitLine = line.split(",");
				lastPrice = splitLine[1];
				price = Double.parseDouble(lastPrice);
				line = buffer.readLine();
			}
		} catch (InputMismatchException e) {
			System.out.println("Not investment symbols. Try again.");
		}
		return price;
	}
	
	/**
	 * The getStockListName uses the investment's symbol to get the name
	 * of the investment in trading.
	 * 
	 * @param symbol
	 * @return name
	 * @throws IOException
	 */
	public static String getStockListName(String symbol) throws IOException{
		// Get the name for an investment
		String name = null;
		try {
			String statistics = "n";
			YahooFinance yf = new YahooFinance(symbol, statistics);
			URLConnection urlConn = yf.getURL(symbol, statistics).openConnection();
			InputStreamReader inStream = new InputStreamReader(urlConn.getInputStream());
			BufferedReader buffer = new BufferedReader(inStream);
			String line = buffer.readLine();
			while (line != null) {
				logfile.append(line);
				logfile.append("Used YahooInterpreter here.\n");
				name = line;
				System.out.println();
				line = buffer.readLine();
			}
		} catch (InputMismatchException e) {
			System.out.println("Not investment symbols. Try again.");
		}
		return name;
	}

}
