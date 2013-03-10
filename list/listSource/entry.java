
public class entry<V> implements Trie<V> {
	V v;
	Dict<Trie<V>> dict;
	
	public boolean hasKey(String key){
		if (key.length() == 0) {
			return true;
		} else if(this.dict.hasKey(key.substring(0,1))){
			return this.dict.lookUp(key.substring(0,1)).hasKey(key.substring(1));
		} else return false;
	}
	public V lookUp(String key){
		if (key.length() == 0) {
			return this.v;
		} else {
			return this.dict.lookUp(key.substring(0,1)).lookUp(key.substring(1));
		}
	}
	
	public Trie<V> set(String key, V v) {
		Trie<V> empty = new noValue<V>(new emptyDict<Trie<V>>());
		if (key.length() == 0) {
			return new entry<V>(v, this.dict);
		} else if(this.dict.hasKey(key.substring(0,1))){
			return new entry<V>(this.v, this.dict.set(key.substring(0,1),this.dict.lookUp(key.substring(0,1)).set(key.substring(1), v)));
		} else {
			return new entry<V>(this.v, new consDict<Trie<V>>(key.substring(0,1), empty.set(key.substring(1), v), this.dict));
		}
	}
	
	public entry(V v, Dict<Trie<V>> dict) {
		this.v = v;
		this.dict = dict;
	}
}

