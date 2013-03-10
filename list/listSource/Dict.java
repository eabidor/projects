
public interface Dict<V> {
	
	public boolean hasKey(String key);
	
	public V lookUp(String key);
	
    public Dict<V> set(String key, V v);
	
    public List<String> keys();
    
    public List<V> values();
    
    public Dict<V> update(String key, Func<V,V> f);
}
