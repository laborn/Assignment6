package factory;

import java.io.IOException;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import adapter.DataAdapter;
import command.AddInvestmentCommand;
import command.Command;
import iterator.StockListIterator;
import singleton.Logger;
import singleton.Reader;

/**
 * @author LuAnn Born
 *
 */
public class InvestmentFactory {

	static Logger logfile = Logger.getInstance();
	static Scanner input = Reader.getInstance();
	static List<String> stocks = null;
	
	/**
	 * @param symbol
	 * @return Stock
	 */
	public static Investment addInvestment(String symbol) {

		if (symbol.length() > 4) {

			return new MutualFund(symbol, null, 0, 0, 0, null);
		} else {

			return new Stock(symbol, null, 0, 0, 0);
		}
	}

	/**
	 * @param symbol
	 * @param lastPrice
	 * @param shares
	 */
	public static void setPurchasePrice(String symbol, double lastPrice, double shares){
		
		if (symbol.length() > 4) {
			 
			MutualFund.setPurchasePrice(symbol, lastPrice, shares);
		}
		else {
			
			Stock.setPurchasePrice(symbol, lastPrice, shares);
		}
	}

	/**
	 * @param symbol
	 * @param name
	 * @param shares
	 * @param purchasePrice
	 * @param lastPrice
	 * @param stocks
	 * @return MutualFund
	 */
	public static Investment addInvestment(String symbol, String name, double shares, double purchasePrice, double lastPrice, List<String> stocks) {

		return new MutualFund(symbol, name, shares, purchasePrice, lastPrice, stocks);
	}

	/**
	 * Choices is the control mechanism using a switch statement that is
	 * controlled by the user's input from the printMenu method.
	 * @param portfolioChoice 
	 * 
	 * @throws IOException
	 */
	public static void getPortfolioChoice(int portfolioChoice) throws IOException {
		
		// Define the choices:
		switch (portfolioChoice) {
		case 1:
			logfile.append("Chose Create a Portfolio: \n");
			System.out.println("Enter the investment symbol: \n");
			String symbol = input.next();
			Command mycommand = new AddInvestmentCommand<Object>(symbol);
			mycommand.execute();
			System.out.println("Enter the number of shares: \n");
			int shares = input.nextInt();
			setPurchasePrice(symbol,DataAdapter.getLastPrice(symbol),shares);
			break;
	
		case 2:
			logfile.append("Chose Add to a Portfolio \n");
			System.out.println("Enter the investment symbol: \n");
			symbol = input.next();
			logfile.append("Input: " + symbol + "\n");
			Command mycommand1 = new AddInvestmentCommand<Object>(symbol);
			mycommand1.execute();
			break;
	
		case 3:
			logfile.append("Chose get the portfolio's value\n");
			// get the value of the portfolio
			break;
			
		case 4:
			logfile.append("Chose new mutual fund\n");
			// create a mutual fund
			System.out.println("Enter the investment symbol: \n");
			symbol = input.next();
			System.out.println("Enter the name of the fund: \n");
			String name = input.next();
			List<String> stocks = Arrays.asList("MMM","AXP","AAPL","BA","CAT","CVX","CSCO","KO","DIS",
					"DD","XOM","GE","GS","HD","IBM","INTC","JNJ","JPM","MCD",
					"MRK","MSFT","NKE","PFE","PG","TRV","UTX","UNH","VZ","V","WMT");
			Investment newfund = addInvestment(symbol, name, 0, 0.00, 0.00, stocks);
			StockListIterator iterate = new StockListIterator(stocks);
			String format = "Stock: " + "%-6s" + "%-35s" + "Last Trade Price: " + "%-10s" + "Buy " + "%-10s" + " Shares";
			while(!iterate.isDone()){
//				System.out.print("Stock: " + iterate.currentItem() +
//						"     Name: " + DataAdapter.getStockListName(iterate.currentItem()) +
//						"     Last Trade Price: " + DataAdapter.getStockListLastPrice(iterate.currentItem()) +
//						"     Buy " + 1000/DataAdapter.getStockListLastPrice(iterate.currentItem()) + " Shares");
				System.out.printf(format, iterate.currentItem(),
						DataAdapter.getStockListName(iterate.currentItem()),
						DataAdapter.getStockListLastPrice(iterate.currentItem()),
						1000/DataAdapter.getStockListLastPrice(iterate.currentItem()));
				iterate.next();
			}
			
			System.out.println("\n\nMutual Fund Symbol: " + newfund.getSymbol());
			System.out.println("Mutual Fund Name: " + newfund.getName());
			System.out.println("Total of $30,000 investment with $1,000 per stock in the fund\n");
			System.out.println("Mutual Fund Share Price: " + newfund.getLastPrice());
			break;
	
		case 5:
			logfile.append("Return to main menu\n");
			break;
	
		default:
			System.out.println("Enter a choice from 1 to 5!\n"); // If something
			// other than 1, 2, 3, 4, 5 is entered
			portfolioChoice = InvestmentFactory.setPortfolioChoice();
			getPortfolioChoice(portfolioChoice);
		}
	}

	/**
	 * @return portfolioChoice
	 * @throws InputMismatchException
	 */
	public static int setPortfolioChoice() throws InputMismatchException {
	
		System.out.println("Choose an option:");
		System.out.println("1. Create a Portfolio");
		System.out.println("2. Add to a Portfolio");
		System.out.println("3. Get a Portfolio's Current Value");
		System.out.println("4. New Mutual Fund");
		System.out.println("5. Exit");
	
		// Get user's choice:
		try {
			while (!input.hasNextInt()){
				System.out.println("You must enter an integer from 1-5!\n");
				input.next();
			}
			int choice = input.nextInt();
			return choice;
		} catch (InputMismatchException e) {
			System.out.println("Not a number. Try again.");
			return 10;
		}
	}

}
