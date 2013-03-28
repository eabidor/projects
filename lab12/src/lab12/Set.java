package lab12;
import java.util.*;

public interface Set<T> {
	/**
	 * Adds an element to the set
	 * Effect: updates the set
	 * @param: T
	 * @return: Void
	 */
	public void add(T t);
	
	/**
	 * 
	 * @param: 
	 * @return:
	 */
	public Boolean contains(T t);
}