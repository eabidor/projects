package lab12;
import tester.*;
import java.util.*;
public class Examples {
	ArrayList<Integer> numAL = new ArrayList<Integer>();
	SetList<Integer> nums = new SetList<Integer>(numAL);
	
	public void testAdd(Tester t) {
		nums.add(1);
		t.checkExpect(nums.contains(1), true);
		t.checkExpect(nums.contains(2), false);
	}
	
	public void testIntSort(Tester t) {
		
	}
}
