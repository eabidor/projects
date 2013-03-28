package lab12;
import java.util.*;
public class SetList<T> implements Set<T> {
	public ArrayList<T> set;
	
	
	public SetList<Integer> randIntSetList(Integer i) {
		new Set<Integer> rand = new ArrayList<Integer>();
		for (idx = 0; idx < i; i++) {
			rand.add(new Random( 19580427).nextInt())
		}
	}
	public SetList(ArrayList<T> set) {
		this.set = set;
	}
	
	/**
	 * Adds an element to the set
	 * Effect: updates the set
	 * @param: T
	 * @return: Void
	 */
	public void add(T t) {
		if (this.contains(t))
			return;
		else this.set.add(t);
	}
	
	/**
	 * 
	 * @param: T 
	 * @return: Boolean
	 */
	public Boolean contains(T t) {
		return this.set.contains(t);
	}
}
