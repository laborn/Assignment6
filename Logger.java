package singleton;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @author LuAnn Born
 * Logger uses the Singleton design pattern that ensures one instance
 * of an application log file that all methods can use.
 */
public class Logger {
	
	String outFile = ("logfile.txt");
	private static final Logger logfile = new Logger();
	String content = null;
    
    //private constructor to avoid client applications to use constructor
    private Logger(){
    	super();
    }
    /**
     * Gets the one and only instance of the Logger
     * @return logfile
     */
    public static Logger getInstance(){
     	return logfile;
    }
    
	/**
	 * Initializes the logfile each time the application is launched
	 * with the current system date and time
	 */
	public void initialize(){
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(outFile, true))) {
			content = "\nInitializing Investment Tracker logfile on " + java.time.LocalDate.now() + " at " + java.time.LocalTime.now() + "\n";
			bw.write(content);
		}
		catch (IOException ex) {
			System.err.format("Error writing filename " + outFile);
		}
	}
	
	/**
	 * Method to append text to the logfile and advance a carriage return
	 * @param content
	 */
	public void append(String content){
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(outFile, true))) {
			logfile.content = content + "\n";
			bw.write(content);
		}
		catch (IOException ex) {
			System.err.format("Error writing filename " + outFile);
		}
	}
}