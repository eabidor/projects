
public interface Trie<V> {
	public boolean hasKey(String key);
	public V lookUp(String key);
	public Trie<V> set(String key, V v);
}
