package singleton;

import java.util.Scanner;

/**
 * @author LuAnn Born
 * Reader uses the Singleton design pattern to ensure one instance
 * of an a Scanner for console input.
 */
public class Reader {
	
	private static final Scanner input = new Scanner(System.in);

	//private constructor to avoid client applications to use constructor
	private Reader(){
    	super();
    }
	
    /**
     * Gets the one and only instance of the Reader
     * @return input
     */
    public static Scanner getInstance(){
     	return input;
	}
}