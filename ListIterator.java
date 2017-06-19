package iterator;

/**
 * @author LuAnn Born
 *
 */
public interface ListIterator {

	/**
	 * @return boolean
	 */
	public boolean isDone();
	/**
	 * 
	 */
	public void next();;
	/**
	 * @return String
	 */
	public String currentItem();;
}
