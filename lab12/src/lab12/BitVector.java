package lab12;
import java.util.*;
import java.lang.Math;
public class BitVector{
	BitSet bs = new BitSet();
	
	/**
	 * 
	 * @param: Integer
	 * @return: Boolean
	 */
	public Boolean get(Integer i) {
		return this.bs.get(i);
	}
	
	/**
	 * 
	 * @param: Integer
	 * @return: Void
	 */
	public void set(Integer i) {
		this.bs.set(i);
	}
	
	
	/**
	 * 
	 * @param: Integer
	 * @return: Void
	 */
	public void flip(Integer i) {
		this.bs.set(i);
	}
	
	/**
	 * 
	 * @param: List<Integer>
	 * @return: SortedList<Integer>
	 */
	public List<Integer> intSort(List<Integer> ints, Integer max) {
		bs = new BitSet(max);
		for(Integer i : ints) {
			bs.set(i);
		}
		List<Integer> sl = new ArrayList<Integer>();
		for(Integer i = 0; i <= max; i++) {
			if(bs.get(i)) sl.add(i);
		} 
		
		return sl;
	}
}
