package facade;

import java.io.IOException;
import java.util.Scanner;

import singleton.Logger;
import singleton.Reader;

/**
 * @author LuAnn Born
 *
 */
public class UserInterface {
	
	/**
	 * Standard constructor for class
	 */
	public UserInterface(){
		super();
	}

	/**
	 * Main program for launching the application
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {

		// Start the logger
		Logger logfile = Logger.getInstance();
		logfile.initialize();
		
		//Get a reader
		Scanner input = Reader.getInstance();

		// Call the menu and get the user's choice:
		int choice = Menu.setFunctionChoice();
		Menu.getFunctionChoice(choice);
		
		input.close();

	}
}
