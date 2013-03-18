package lab11;
import java.util.*;
public class consDict<V> implements Dict<V> {
	String key;
	V v;
	Dict<V> rest;
	// has-key : Key -> Boolean
	// Does the given key exist?
	public boolean hasKey(String key) {
		if (key.compareTo(this.key) == 0) {
			return true;
		} else {
			return this.rest.hasKey(key);
		}
	}
	// lookup : Key -> V
	// The value mapped to by the given key
	public V lookUp(String key) {
		if (key.compareTo(this.key) == 0) {
			return this.v;
		} else {
			return this.rest.lookUp(key);
		}
	}
	// set : Key V -> Dict<V>
	// Set the given key-value mapping
	public Dict<V> set(String key, V v) {
		if (key.compareTo(this.key) ==  0) {
			return new consDict<V>(key, v, this.rest);
		} else {
			return new consDict<V>(this.key, this.v, this.rest.set(key, v));
		}
	}
	// keys : -> List<String>
	// Return a list of all the keys in the dictionary
	public List<String> keys() {
		return new Cons<String>(this.key, this.rest.keys());
	}
	// values : -> List<V>
	// Return a list of all the values in the dictionary
	public List<V> values() {
		return new Cons<V>(this.v, this.rest.values());
	}
	// update: String Func<V,V> -> Dict<V>
	// Update the value associated with the given key by applying it the given function
	/*
	public Dict<V> update(String key, Func<V,V> f){
		if (key.compareTo(this.key) ==  0) {
			return new consDict<V>(key, f.call(this.v), this.rest);
		} else {
			return new consDict<V>(this.key, this.v, this.rest.update(key, f));
		}
	}
	*/
	public consDict(String key, V v, Dict<V> rest) {
		this.key = key;
		this.v = v;
		this.rest = rest;
	}
	
	
	public Iterator<V> iterator() {
		List<V> vals = this.values();
		return new DictIter<V>(vals);
	}
}
