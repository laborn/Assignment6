package abstractfactory;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

import singleton.Logger;
import singleton.Reader;

/**
 * @author LuAnn Born
 *
 */
public abstract class PortfolioAbstractFactory {
	
	static Logger logfile = Logger.getInstance();
	static Scanner input = Reader.getInstance();
	
	/**
	 * @param styleChoice
	 * @return portfolio
	 */
	public static Portfolio createPortfolio(int styleChoice){
		
		Portfolio portfolio = null;
		
		if (styleChoice == 1) {
			portfolio = AggressiveFactory.createPortfolio();
		}
		else if (styleChoice == 2){
			portfolio = DefensiveFactory.createPortfolio();
		}
		else if (styleChoice == 3){
			portfolio = IncomeFactory.createPortfolio();
		}
		else if (styleChoice == 4){
			portfolio = HybridFactory.createPortfolio();
		}
		else if (styleChoice == 5){
			portfolio = SpeculativeFactory.createPortfolio();
		}
		return portfolio;
	}

	/**
	 * Choices is the control mechanism using a switch statement that is
	 * controlled by the user's input from the printMenu method.
	 * @param styleChoice 
	 * 
	 * @throws IOException
	 */
	public static void getStyleChoice(int styleChoice) throws IOException {
		// Define the choices:
		int choice = styleChoice;
		logfile.append("Chose " + choice + "\n");
		createPortfolio(styleChoice);
	}

	/**
	 * @return styleChoice
	 * @throws InputMismatchException
	 */
	public static int setStyleChoice() throws InputMismatchException {
	
		System.out.println("Choose an investment style:");
		System.out.println("1. Aggressive");
		System.out.println("2. Defensive");
		System.out.println("3. Income");
		System.out.println("4. Hybrid");
		System.out.println("5. Speculative");
		System.out.println("6. Exit");
	
		// Get user's choice:
		try {
			while (!input.hasNextInt()){
				System.out.println("You must enter an integer from 1-6!\n");
				input.next();
			}
			int choice = input.nextInt();
			if (choice > 0 && choice < 7){
				return choice;
			}
			else {
				System.out.println("Integer must be 1-6. Try again.\n");
				return 0;
			}
		} catch (InputMismatchException e) {
			System.out.println("Not a number. Try again.\n");
			return 10;
		}
	}
}
