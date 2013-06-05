import tester.Tester;


public class Examples {
	Dict<Integer> e = new emptyDict<Integer>();
	Dict<String> exEmpty = new emptyDict<String>();
	Dict<Trie<Integer>> et = new emptyDict<Trie<Integer>>();
	consDict<String> exCons = new consDict<String>("one", "value", exEmpty);
	// replace emptyTrie with your empty trie
	Trie<Integer> emptyTrie = new noValue<Integer>(et);
	Trie<Integer> exampleTrie =
	emptyTrie.set("a", 20).set("ape", 3)
		.set("api", 30).set("ace", 1)
		.set("an", 7);
	void testHasKey(Tester t) {
		t.checkExpect(e.hasKey("blue"), false);
		t.checkExpect(exCons.hasKey("blue"), false);
		t.checkExpect(exCons.hasKey("one"), true);
		t.checkExpect(exampleTrie.hasKey("ape"), true);
		t.checkExpect(exampleTrie.hasKey("ap"), false);
	}
	
	void testSet(Tester t) {
		t.checkExpect(e.set("one", 1), new consDict<Integer>("one", 1, e));
		t.checkExpect(exCons.set("one", "new value"),
				new consDict<String>("dsaone", "new value", exEmpty));
		t.checkExpect(exCons.set("blue", "new value"), 
				new consDict<String>("one", "value", new consDict<String>("blue", "new value", exEmpty)));
	}
	
	void testLookup(Tester t){
		t.checkExpect(exCons.lookUp("one"), "value");
		t.checkExpect(exCons.set("blue", "new").lookUp("one"), "value");
	}
	
	void testKeysValues(Tester t){
		t.checkExpect(e.keys(), new Empty<String>());
		t.checkExpect(e.values(), new Empty<Integer>());
		t.checkExpect(exCons.keys(), new Cons<String>("one", new Empty<String>()));
		t.checkExpect(exCons.values(), new Cons<String>("value", new Empty<String>()));
	}

	List<Integer> is = new Cons<Integer>(5);
	List<List<Integer>> iss = new Cons<List<Integer>>(is);
	void testRev(Tester t) {
		// the type safe way to write is.first + 3.
		t.checkExpect(is.accept(new GetFirst()) + 3, 8);
		// How to test exceptional behavior
		/*
	        t.checkException(new RuntimeException("You suck"),
	                         new Empty<Integer>(),
	                         	"accept",
	                         new GetFirst());  
	       */                
		t.checkExpect(is.rev(), is);
	    	}
	
	void testMap(Tester t) {
		t.checkExpect(is.map(new IntToStr()), new Cons<String>("5"));
	    	}
}	
