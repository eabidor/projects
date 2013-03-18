package lab11;
import java.util.*;
public class DictIter<V> implements Iterator<V>{
	List<V> values;
	
	public DictIter(List<V> values) {
		this.values = values;
	}
	
	public boolean hasNext() {
		return !this.values.empty();
	}

	public V next() {
		if (this.values.empty()) {
			throw new RuntimeException("Iteration has no more elements");
		} else {
			V first = this.values.first();
			this.values = this.values.rest();
			return first;
		}
	}

	public void remove() {
		this.values = this.values.rest();
	}

}
