package adapter;

import java.net.URL;

/**
 * Interface for getting a URL from Yahoo or Google Financials
 * @author LuAnn Born
 */
public interface WebDataSource {

	/**
	 * @param symbol
	 * @param statistics
	 * @return URL
	 */
	public URL getURL(String symbol, String statistics);
}
