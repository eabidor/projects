
import java.util.*;

/**

 * 
 * @author Eli Abidor
 * @email abidor.e@husky.neu.edu
 * @param <K>
 * @param <V>
 * 
 * Notes : 
 * Implementation of the FMap<K,V> immutable abstract data type
 * whose values represent finite functions from key of type K to 
 * values of type V
 * Signature:
  Static methods:
    empty        :                                  ->  FMap<K,V>
    empty        : java.util.Comparator<? super K>  ->  FMap<K,V>
  Dynamic methods (for which the receiver is an FMap<K,V>):
    include      :  K x V                           ->  FMap<K,V>
    isEmpty      :                                  ->  boolean
    size         :                                  ->  int
    containsKey  :  K                               ->  boolean
    get          :  K                               ->  V
    toString     :                                  ->  String
    equals       :  Object                          ->  boolean
    hashCode     :                                  ->  int
Restrictions:
    Null arguments may not be passed to any of the above methods except for 
    equals(Object).
 */

public abstract class FMap<K,V> implements Iterable<K>{
	
	// singleton instance of Empty, ensures that no unneccesary duplicates
	// of Empty are made
    // final FMap<K,V> EMPTY_MAP = new Empty<K,V>();
	
    /**
     * basic creator for Empty subclass
     * @return new empty
     */
	public static <K,V> FMap<K,V> empty() {
		return new Empty<K,V>();
	}
	
	/**
	 * Drops the given comparator on the floor and just calls 
	 * the basic creator for an empty map
	 * @param c
	 * @return new empty
	 */
	public static <K,V> FMap<K,V> empty(java.util.Comparator<? super K> c) {
		return new EmptyBST<K,V>(c);
	}
	
	/**
	 * Basic creator for Include subclass
	 * @param k new key of type K
	 * @param v new value of type V
	 * @return a new FMap<K,V>
	 */
	public abstract FMap<K,V> include(K k, V v);
	
	/**
	 * Indicates whether this is empty
	 * @return boolean 
	 */
	public abstract boolean isEmpty();											
	
	/**
	 * Returns the number of non-duplicate keys in this FMap
	 * @return int number of non-duplicate keys
	 */
	public abstract int size();
		
	/**
	 * Returns true if this FMap contains the given key                
	 * @param k
	 * @return boolean
	 */
	public abstract boolean containsKey(K k);
	
	/**
	 * Gets the first value of type V mapped to the given key of type k
	 * Returns an exception if method has reached the empty FMap
	 * @param k of type K
	 * @return value of type V
	 */
	public abstract V get(K k);
	
	/**
	 * Overrides the equals method
	 * @param o Object
	 * @return boolean
	 */
	public abstract boolean equals(Object o);
	
	/**
	 * Overrides the toString method. Returns a string 
	 * representing the number of distinct keys mapped to values
	 * @return String
	 */
	public String toString() {
		return "{...(" + this.size() + " keys mapped to values)...}";
	}
	
	/**
	 * Overrides the hashCode method
	 * @return int
	 */
	public abstract int hashCode();
	
	/**
	 * Getter for this FMap's key field
	 * @return K key
	 */
	public abstract K getThisK();
	
	/**
	 * Getter for this FMap's rest field
	 * @return FMap<K,V>
	 */
	public abstract FMap<K,V> getRest();
	
	/**
	 * Removes first instance of given key. 
	 * @param k
	 * @return FMap<K,V>
	 */
	public abstract FMap<K,V> remove(K k);
	
	
	/**
	 * Accepts a Visitor<K,V> and returns an FMap
	 * in which all values of type V have been replaced 
	 * by the output of the visit operation, also of type
	 * V
	 * @param v
	 * @return FMap<K,V>
	 */
	public abstract FMap<K,V> accept (Visitor<K,V> vi);
	
	/**
	 * Returns an iterator that generates all non-duplicate keys
	 * of type K in this FMap in no particular order.
	 * @return Iterator<K>
	 */
	public Iterator<K> iterator() {
		ArrayList<K> keys = this.toArray();
		return new KeyIterator(keys.iterator());
	}
	
	/**
	 * Returns an iterator that generates all non-duplicate keys
	 * of type K in this FMap in increasing order as defined by the
	 * given comparator
	 * @param c comparator of type K that determines order of keys
	 * @return Iterator<K>	
	 */
	public Iterator<K> iterator(java.util.Comparator<? super K> c) {
		ArrayList<K> sortedKeys = this.toArray();
		Collections.sort(sortedKeys, c);
		return new KeyIterator(sortedKeys.iterator());
	}
	
	/**
	 * Inserts all of the keys of this FMap into an array list
	 * @return ArrayList<K>
	 */
	public ArrayList<K> toArray() {
		FMap<K,V> f = this;
		ArrayList<K> k = new ArrayList<K>(); 
		while(!f.isEmpty()) {
			k.add(f.getThisK());
			f = f.getRest();
		}
		return k;
	}
	
	/**
	 * Private iterator class for FMap. Implements the iterator of type
	 * k that generates all of the non-duplicate keys in this FMap
	 * @param <K> 
	 */
	private class KeyIterator implements Iterator<K> {
		// State variable in the form of an iterator. This iterator obtained from calling
		// iterator() on the ArrayList returned from toArray() 
		Iterator<K> it;
		
		/**
		 * Package-private constructor for KeyIterator<K>
		 * @param it Iterator<K>	
		 */
		KeyIterator(Iterator<K> it) {
			this.it = it;
		}
		
		/**
		 * Returns true if there is another key value. Otherwise returns 
		 * false;
		 * @return boolean
		 */
		public boolean hasNext() {
			return it.hasNext();
		}
		
		/**
		 * Returns the next key value in the iterator, if there is one. If not, throws 
		 * an exception
		 * @return K
		 */
		public K next() {
			return it.next();
		}
		
		/**
		 * Throws an unsupported operation exception, since it is not necessary
		 * to implement the remove method for KeyIterator<K>
		 */
		public void remove() {
			throw new UnsupportedOperationException();
		}
		
	}
}


/*
 * Represents an empty tree. Takes a comparator. 
 *
 * @param <K> type of keys
 * @param <V> type of values
 */
class EmptyBST<K,V> extends FMap<K,V> {
	private java.util.Comparator<? super K> c; // comparator of type K
	
	/**
	 * Constructor for Empty. Takes a comparator of type K
	 * @param java.util.Comparator<? super K> c
	 */
	EmptyBST(java.util.Comparator<? super K> c){
		this.c = c;
	}
	
	/**
	 * Basic creator for Include subclass
	 * @param k new key of type K
	 * @param v new value of type V
	 * @return a new FMap<K,V>
	 */
	public FMap<K,V> include(K k, V v) {
		return new Node<K,V>(k, v, this, this, c);
	}

	/**
	 * Inserts all of the keys of this FMap into an array list
	 * @return ArrayList<K>
	 */
	public ArrayList<K> toArray() {
		return new ArrayList<K>();
	}
	
	
	/**
	 * Indicates whether this is empty
	 * @return boolean 
	 */
	public boolean isEmpty() {
		return true;
	}
	
	/**
	 * Returns the number of non-duplicate keys in this FMap BST
	 * @return int number of non-duplicate keys
	 */
	public int size() {
		return 0;
	}

	/**
	 * Returns true if this FMap BST contains the given key
	 * @param k
	 * @return boolean
	 */
	public boolean containsKey(K k) {
		return false;
	}

	/**
	 * Gets the first value of type V mapped to the given key of type k
	 * Returns an exception if method has reached the empty FMap
	 * @param k of type K
	 * @return value of type V
	 */
	public V get(K k) {
		throw new RuntimeException("The given key is not in the FMap BST");
	}
	
	/**
	 * Overrides the equals method
	 * @param o Object
	 * @return boolean
	 */
	public boolean equals(Object o) {
		if (o instanceof FMap<?,?>) {
			@SuppressWarnings("unchecked")
			FMap<K,V> f = (FMap<K,V>) o;
			return f.isEmpty();
		} else return false;
	}
	
	/**
	 * Overrides the hashCode method
	 * @return int
	 */
	public int hashCode() {
		return 0;
	}

	public K getThisK() {
		throw new UnsupportedOperationException();
	}

	public FMap<K, V> getRest() {
		throw new UnsupportedOperationException();
	}

	public FMap<K, V> remove(K k) {
		throw new UnsupportedOperationException();
	}
	
	/**
	 * Accepts a Visitor<K,V> and returns an FMap
	 * in which all values of type V have been replaced 
	 * by the output of the visit operation, also of type
	 * V
	 * @param v
	 * @return FMap<K,V>
	 */
	public FMap<K,V> accept (Visitor<K,V> vi) {
		return this;
	}
	
}

class Node<K,V> extends FMap<K,V> {
	private K k; // Key of type K mapped to value below
	private V v; // Value of type V 
	private FMap<K,V> left; // Left subtree
	private FMap<K,V> right; // Right subtree
	private java.util.Comparator<? super K> c; // comparator of type K
	private int size;
	/**
	 * Constructor for Node
	 * @param k key of type K
	 * @param v value of type V
	 * @param left The left subtree
	 * @param right The right subtree
	 */
	Node(K k, V v, FMap<K,V> left,
			FMap<K,V> right,
			java.util.Comparator<? super K> c) {
		this.k = k;
		this.v = v;
		this.left = left;
		this.right = right;
		this.c = c;
		this.size = 1 + left.size() + right.size();
	}
	
	/**
	 * Basic creator for Include subclass
	 * @param k new key of type K
	 * @param v new value of type V
	 * @return a new FMap<K,V>
	 */
	public FMap<K,V> include(K k, V v) {
		if (c.compare(k, this.k) == 0) return new Node<K,V>(k, v, left, right, c);
		else if (c.compare(k, this.k) < 0) 
			return new Node<K,V>(this.k, this.v, left.include(k, v), right, c);
		else return new Node<K,V>(this.k, this.v, left, right.include(k, v), c);
	}
	/**
	 * Indicates whether this is empty
	 * @return boolean 
	 */
	public boolean isEmpty() {
		return false;
	}
	
	/**
	 * Returns the number of keys in this FMap
	 * @return int number of non-duplicate keys
	 */
	public int size() {
		return this.size;
	}
	
	/**
	 * Inserts all of the keys of this FMap into an array list
	 * @return ArrayList<K>
	 */
	public ArrayList<K> toArray() {
		ArrayList<K> a = this.left.toArray();
		a.add(k);
		a.addAll(this.right.toArray());
		return a;
	}
	
	/**
	 * Returns true if this FMap contains the given key
	 * @param k
	 * @return boolean
	 */
	public boolean containsKey(K k) {
		if(c.compare(k, this.k) == 0) return true;
		else if(c.compare(k, this.k) < 0) return left.containsKey(k);
		else return right.containsKey(k);
	}
	
	
	/**
	 * Gets the first value of type V mapped to the given key of type k
	 * Returns an exception if method has reached the empty FMap
	 * @param k of type K
	 * @return value of type V
	 */
	public V get(K k) {
		if (c.compare(k, this.k)==0) return this.v;
		else if(c.compare(k, this.k) < 0) return left.get(k);
		else return right.get(k);
	}
	
	/**
	 * Overrides the equals method
	 * @param o Object
	 * @return boolean
	 */
	public boolean equals(Object o) {
		if (o instanceof FMap<?,?>) {
			@SuppressWarnings("unchecked")
			FMap<K,V> f = (FMap<K,V>) o;
			if (!(f.size() == this.size())) return false;
			return this.sameKeys(f);
		} else return false;
	}
	
	/**
	 * checks if the FMap given has the same keys and the
	 * same values mapped to those keys. Only checks for first
	 * key/value pair returned
	 * @param f
	 * @return
	 */
	private boolean sameKeys(FMap<K,V> f) {
		boolean b = true;
		for(K k : this) {
			if(f.containsKey(k)) b = b && this.get(k).equals(f.get(k));
			else return false;
		}
		return b;
	}

	/**
	 * Overrides the hashCode method
	 * @return int
	 */
	public int hashCode() {
		return k.hashCode() + v.hashCode() + left.hashCode() + right.hashCode();
	}
	
	public K getThisK() {
		throw new UnsupportedOperationException();
	}

	public FMap<K, V> getRest() {
		throw new UnsupportedOperationException();
	}

	public FMap<K, V> remove(K k) {
		throw new UnsupportedOperationException();
	}
	
	/**
	 * Accepts a Visitor<K,V> and returns an FMap
	 * in which all values of type V have been replaced 
	 * by the output of the visit operation, also of type
	 * V
	 * @param v
	 * @return FMap<K,V>
	 */
	public FMap<K,V> accept (Visitor<K,V> vi) {
		FMap<K,V> newLeft = left.accept(vi);
		FMap<K,V> newRight = right.accept(vi);
		V newV = vi.visit(k, v);
		return new Node<K,V>(k, newV, newLeft, newRight, c);
	}
}
/**
 * Subtype of the abstract immutable FMap class. Represents an empty Map
 * of keys of type K to values of type V. 
 *
 * @param <K> type of keys
 * @param <V> type of values
 */
class Empty<K,V> extends FMap<K,V> {
	
	/**
	 * Constructor for Empty. Equivalent to default
	 */
	Empty(){}
	
	/**
	 * Basic creator for Include subclass
	 * @param k new key of type K
	 * @param v new value of type V
	 * @return a new FMap<K,V>
	 */
	public FMap<K,V> include(K k, V v) {
		return new Include<K,V>(k, v, this);
	}
	/**
	 * Indicates whether this is empty
	 * @return boolean 
	 */
	public boolean isEmpty() {
		return true;
	}
	
	/**
	 * Returns the number of non-duplicate keys in this FMap
	 * @return int number of non-duplicate keys
	 */
	public int size() {
		return 0;
	}

	/**
	 * Returns true if this FMap contains the given key
	 * @param k
	 * @return boolean
	 */
	public boolean containsKey(K k) {
		return false;
	}
	
	/**
	 * Gets the first value of type V mapped to the given key of type k
	 * Returns an exception if method has reached the empty FMap
	 * @param k of type K
	 * @return value of type V
	 */
	public V get(K k) {
		throw new RuntimeException("Attempted to call get on an empty FMap");
	}
	
	/**
	 * Overrides the equals method
	 * @param o Object
	 * @return boolean
	 */
	public boolean equals(Object o) {
		if (o == null) {
			return false;
		} else if (o instanceof FMap<?,?>) {
			@SuppressWarnings("unchecked")
			FMap<K,V> f = (FMap<K,V>) o;
			return f.isEmpty();
		} else return false;
	}
	
	/**
	 * Overrides the hashCode method
	 * @return int
	 */
	public int hashCode() {
		return 0;
	}
	
	/**
	 * Getter for key of type K. Returns an exception.
	 * @return K
	 */
	public K getThisK() {
		throw new RuntimeException("can't getThisK on an empty map");
	}
	
	/**
	 * Getter for rest of FMap. Returns an exception.
	 */
	public FMap<K, V> getRest() {
		throw new RuntimeException("can't getRest on an empty map");
	}
	
	/**
	 * Removes first instance of given key. 
	 * @param k
	 * @return
	 */
	public FMap<K,V> remove(K k) {
		throw new RuntimeException("can't remove from an empty map");
	}
	
	
	/**
	 * Accepts a Visitor<K,V> and returns an FMap
	 * in which all values of type V have been replaced 
	 * by the output of the visit operation, also of type
	 * V
	 * @param v
	 * @return FMap<K,V>
	 */
	public FMap<K,V> accept (Visitor<K,V> v) {
		return this;
	}
	
}

/**
 * Subtype of the abstract immutable FMap class. Represents a non-empty map
 * of keys of type K to values of type V. Contains one key/value pair and 
 * the rest of the FMap
 *
 * @param <K> type of keys
 * @param <V> type of values
 */
class Include<K,V> extends FMap<K,V> {
	private K k; // Key of type K mapped to value below
	private V v; // Value of type V 
	private FMap<K,V> m; // rest of this FMap
	
	/**
	 * Constructor for Include
	 * @param k key of type K
	 * @param v value of type V
	 * @param m rest of FMap
	 */
	Include(K k, V v, FMap<K,V> m) {
		this.k = k;
		this.v = v;
		this.m = m;
	}
	
	/**
	 * Basic creator for Include subclass
	 * @param k new key of type K
	 * @param v new value of type V
	 * @return a new FMap<K,V>
	 */
	public FMap<K,V> include(K k, V v) {
		if (this.containsKey(k)) {
			return new Include<K,V>(k, v, this.remove(k));
		}
		else return new Include<K,V>(k, v, this);
	}
	
	/**
	 * Indicates whether this is empty
	 * @return boolean 
	 */
	public boolean isEmpty() {
		return false;
	}
	
	/**
	 * Returns the number of non-duplicate keys in this FMap
	 * @return int number of non-duplicate keys
	 */
	public int size() {
		if (this.m.containsKey(this.k)) {
			return m.size();
		} else return 1 + m.size();
	}
	
	/**
	 * Returns true if this FMap contains the given key
	 * @param k
	 * @return boolean
	 */
	public boolean containsKey(K k) {
		return this.k.equals(k) || this.m.containsKey(k);
	}
	
	/**
	 * Gets the first value of type V mapped to the given key of type k
	 * Returns an exception if method has reached the empty FMap
	 * @param k of type K
	 * @return value of type V
	 */
	public V get(K k) {
		if (this.k.equals(k)) {
			return this.v;
		} else return this.m.get(k);
	}
	
	/**
	 * Overrides the equals method
	 * @param o Object
	 * @return boolean
	 */
	public boolean equals(Object o) {
		if (o == null) {
			return false;
		} else if (o instanceof FMap<?,?>) {
			@SuppressWarnings("unchecked")
			FMap<K,V> f = (FMap<K,V>) o;
			if (!(f.size() == this.size())) return false;
			return this.sameKeys(f);
		} else return false;
	}
	
	/**
	 * checks if the FMap given has the same keys and the
	 * same values mapped to those keys. Only checks for first
	 * key/value pair returned
	 * @param f
	 * @return
	 */
	private boolean sameKeys(FMap<K,V> f) {
		boolean b = true;
		FMap<K,V> check = this;
		for(int i = 0; i < this.size(); i++ ) {
			K k = check.getThisK();
			if(f.containsKey(k)) {
				b = b && check.get(k).equals(f.get(k));
			} else return false;
			check = check.getRest();
		}
		return b;
	}

	/**
	 * Overrides the hashCode method
	 * @return int
	 */
	public int hashCode() {
		return this.k.hashCode() + this.v.hashCode() + this.m.hashCode();
	}
	
	/**
	 * Getter for this FMap's key field
	 * @return K key
	 */
	public K getThisK() {
		return this.k;
	}
	
	/**
	 * Getter for this FMap's rest field
	 * @return FMap<K,V>
	 */
	public FMap<K, V> getRest() {
		return this.m;
	}
	
	/**
	 * Removes first instance of given key. 
	 * @param k
	 * @return
	 */
	public FMap<K,V> remove(K k) {
		if (this.k.equals(k)) {
			return this.m;
		}
		else return this.m.remove(k).include(this.k, this.v);
	}
	
	
	/**
	 * Accepts a Visitor<K,V> and returns an FMap
	 * in which all values of type V have been replaced 
	 * by the output of the visit operation, also of type
	 * V
	 * @param v
	 * @return FMap<K,V>
	 */
	public FMap<K,V> accept (Visitor<K,V> vi) {
		V newV = vi.visit(k, v);
		FMap<K,V> newM = m.accept(vi);
		return new Include<K,V>(k, newV, newM);
	}
	
}


