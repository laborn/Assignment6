package facade;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

import abstractfactory.PortfolioAbstractFactory;
import factory.InvestmentFactory;
import singleton.Logger;
import singleton.Reader;
import adapter.DataAdapter;


/**
 * This class contains the main menu that appears to
 * the user and the logic that controls the direction
 * of the application's functionality 
 * @author LuAnn Born
 *
 */
public class Menu {

	static Logger logfile = Logger.getInstance();
	static Scanner input = Reader.getInstance();
	/**
	 * 
	 */
	public Menu() {
		super();
	}


	/**
	 * This method gets input from the user after providing
	 * a menu of choices
	 * @return choice
	 * @throws InputMismatchException
	 * @throws IOException 
	 */
	public static int setFunctionChoice() throws InputMismatchException, IOException {

		System.out.println("Choose an option:");	//Menu choices
		System.out.println("1. Get current trading information");
		System.out.println("2. Find my investment style");
		System.out.println("3. Portfolio Management");
		System.out.println("4. Exit");

		// Get user's choice:
		try {
			while (!input.hasNextInt()){	//Deal with non-integer input
				System.out.println("You must enter an integer from 1-4!");
				input.next();
			}
			int choice = input.nextInt();
			return choice;
		} catch (InputMismatchException e) {
			System.out.println("Not a number. Try again.");
			return 10;
		}
	}

	/**
	 * This method uses a switch statement that is controlled by
	 * the user's input from the setFunctionChoice method.
	 * 
	 * 
	 * @param choice
	 * @throws InputMismatchException 
	 * @throws IOException
	 */
	public static void getFunctionChoice(int choice) throws InputMismatchException, IOException {

		// Define the choices:
		switch (choice) {
		case 1:
			logfile.append("Chose Trading\n");
			DataAdapter.setStatisticsChoice();
			repeatChoices();
			break;

		case 2:
			logfile.append("Chose Styles\n");
			choice = PortfolioAbstractFactory.setStyleChoice();
			PortfolioAbstractFactory.getStyleChoice(choice);
			repeatChoices();
			break;

		case 3:
			logfile.append("Chose Portfolio Management\n");
			choice = InvestmentFactory.setPortfolioChoice();
			InvestmentFactory.getPortfolioChoice(choice);
			repeatChoices();
			break;

		case 4:
			logfile.append("Chose to exit\n");
			System.out.println("Have a nice day!"); // End the program
			break;

		default:
			System.out.println("Enter a choice from 1 to 4!\n"); // If something
			// other than 1, 2, 3, 4 is entered
			repeatChoices();
		}
	}
	
	/**
	 * Method to return to main menu choices
	 * @throws InputMismatchException
	 * @throws IOException
	 */
	public static void repeatChoices() throws InputMismatchException, IOException{
		int choice = setFunctionChoice();
		getFunctionChoice(choice);
	}
}
