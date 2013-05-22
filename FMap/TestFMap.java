import java.util.Random;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Basic test program for assignment 5 Summer 2013.
 * @author Clinger
 * @author Schmidt
 */
public class TestFMap {

    /**
     * Runs the tests
     * @param args command line arguments
     */
    public static void main(String args[]) {
        TestFMap test = new TestFMap();  //TestFMap instance

        // Test with 0-argument FMap.empty().

        System.out.println("Testing 0-argument empty()");
        test.creation(0);
        test.accessors();
        test.usual();
        test.accessors();            // repeated to test for side effects
        test.usual();                // repeated to test for side effects
        test.iterators(0);
        test.iterators(1);

        // Test with 1-argument FMap.empty().

        System.out.println("Testing 1-argument empty()");
        test.creation(1);
        test.accessors();
        test.usual();
        test.accessors();            // repeated to test for side effects
        test.usual();                // repeated to test for side effects
        test.iterators(0);
        test.iterators(1);

        System.out.println("Testing cross-representation equality");

        test.equality();

        test.summarize();
    }

    /**
     * Prints a summary of the tests.
     */
    private void summarize () {
        System.out.println();
        System.out.println (totalErrors + " errors found in " +
                            totalTests + " tests.");
    }

    /**
     * Constructor for TestFMap
     */
    public TestFMap () { }

    // String objects to serve as values.

    private final String alice = "Alice";
    private final String bob = "Bob";
    private final String carol = "Carol";
    private final String dave = "Dave";

    // Integer objects to serve as keys.

    private final Integer one = 1;
    private final Integer two = 2;
    private final Integer three = 3;
    private final Integer four = 4;
    private final Integer five = 5;
    private final Integer six = 6;

    // FMap<Integer,String> objects to be created and then tested.

    private FMap<Integer,String> f0;// [ ]
    private FMap<Integer,String> f1;// [ (1 Alice) ]
    private FMap<Integer,String> f2;// [ (2 Bob) (1 Alice) ]
    private FMap<Integer,String> f3;// [ (3 Carol) (2 Bob) (1 Alice) ]
    private FMap<Integer,String> f4;// [ (4 Dave) (3 Carol) (2 Bob) (1 Alice) ]
    private FMap<Integer,String> f5;// [ (1 Carol) (2 Bob) (1 Alice) ]
    private FMap<Integer,String> f6;// [ (3 Carol) (4 Dave) (2 Bob) (1 Alice) ]
    private FMap<Integer,String> f7;// [ (1 Alice) (2 Bob) (2 dave) (1 dave) ]
    private FMap<Integer,String> f8;// [ (2 dave) (1 dave) ]
    private FMap<Integer,String> f9;// [ (1 Alice) (1 dave) ]
    private FMap<Integer,String> f10;// [ (1 dave) (1 Alice) ]

    /**
     * A comparator for Integer values.
     */
    private static class UsualIntegerComparator
        implements Comparator<Integer> {

	/**
	 * Compares its two arguments for order.
	 * @param m first Integer to compare
	 * @param n second Integer to compare
	 * @return Returns a negative integer, zero, or a positive integer 
	 *    as m is less than, equal to, or greater than n
	 */
        public int compare (Integer m, Integer n) {
            return m.compareTo(n);
        }

    }

    Comparator<Integer> usualIntegerComparator
        = new UsualIntegerComparator(); //Instance of Comparator

    /**
     * Another comparator for Integer values.
     */
    private static class ReverseIntegerComparator
        implements Comparator<Integer> {

	/**
	 * Compares its two arguments for order.
	 * @param m first Integer to compare
	 * @param n second Integer to compare
	 * @return Returns a negative integer, zero, or a positive integer 
	 *    as n is less than, equal to, or greater than m
	 */
        public int compare (Integer m, Integer n) {
            return n.compareTo(m);
        }

    }

    Comparator<Integer> reverseIntegerComparator
        = new ReverseIntegerComparator(); //Instance of Comparator



    /**
     * Creates some FMap<Integer,String> objects.
     *
     * @param nargs argument
     *
     * If nargs is 0, then 0-argument FMap.empty() is used.
     * Otherwise the more efficient 1-argument version is used.
     */
    private void creation (int nargs) {
        creation (nargs, reverseIntegerComparator);
    }

    /**
     * Creates some FMap<Integer,String> objects.
     *
     * @param nargs argument
     * @param c Comparator<Integer>
     */
    private void creation (int nargs, Comparator<Integer> c) {
        try {
            if (nargs == 0)
                f0 = FMap.empty();
            else
                f0 = FMap.empty(c);
            f1 = f0.include(one, alice);
            f2 = f1.include(two, bob);
            f3 = f2.include(three, carol);
            f4 = f3.include(four, dave);
	    f5 = f2.include(one, carol);
	    f6 = f2.include(four, dave).include(three, carol);

            f7 = f0.include(one, dave);
            f7 = f7.include(two, dave);
            f7 = f7.include(two, bob);
            f7 = f7.include(one, alice);

            f8 = f0.include(two, bob);
            f8 = f8.include(one, alice);

            f9 = f0.include(one, dave);
            f9 = f9.include(one, alice);

	    f10 = f0.include(one, alice);
	    f10 = f10.include(one, dave);
	}
        catch (Exception e) {
            System.out.println("Exception thrown during creation tests:");
            System.out.println(e);
            assertTrue ("creation", false);
        }
    }

    /**
     * Tests the accessors.
     */
    private void accessors () {
        try {
	    //Testing isEmpty()
            assertTrue ("empty", f0.isEmpty());
            assertFalse ("nonempty", f1.isEmpty());
            assertFalse ("nonempty", f3.isEmpty());

	    //Testing size()
            assertTrue ("f0.size()", f0.size() == 0);
            assertTrue ("f1.size()", f1.size() == 1);
            assertTrue ("f2.size()", f2.size() == 2);
            assertTrue ("f3.size()", f3.size() == 3);
            assertTrue ("f4.size()", f4.size() == 4);
            assertTrue ("f5.size()", f5.size() == 2);
            assertTrue ("f7.size()", f7.size() == 2);

	    //Testing containsKey
            assertFalse ("containsKey01", f0.containsKey(one));
            assertFalse ("containsKey04", f0.containsKey(four));
            assertTrue  ("containsKey11", f1.containsKey(one));
            assertTrue  ("containsKey11new", f1.containsKey(new Integer(1)));
            assertFalse ("containsKey14", f1.containsKey(four));
            assertTrue  ("containsKey21", f2.containsKey(one));
            assertFalse ("containsKey24", f2.containsKey(four));
            assertTrue  ("containsKey31", f3.containsKey(one));
            assertFalse ("containsKey34", f3.containsKey(four));
            assertTrue  ("containsKey41", f4.containsKey(one));
            assertTrue  ("containsKey44", f4.containsKey(four));
            assertTrue  ("containsKey51", f5.containsKey(one));
            assertFalse ("containsKey54", f5.containsKey(four));

	    //Testing get
            assertTrue ("get11", f1.get(one).equals(alice));
            assertTrue ("get11new", f1.get(new Integer(1)).equals(alice));
            assertTrue ("get21", f2.get(one).equals(alice));
            assertTrue ("get22", f2.get(two).equals(bob));
            assertTrue ("get31", f3.get(one).equals(alice));
            assertTrue ("get32", f3.get(two).equals(bob));
            assertTrue ("get33", f3.get(three).equals(carol));
            assertTrue ("get41", f4.get(one).equals(alice));
            assertTrue ("get42", f4.get(two).equals(bob));
            assertTrue ("get43", f4.get(three).equals(carol));
            assertTrue ("get44", f4.get(four).equals(dave));
            assertTrue ("get51c", f5.get(one).equals(carol));
            assertFalse ("get51a", f5.get(one).equals(alice));
            assertTrue ("get52", f5.get(two).equals(bob));
            assertTrue ("get71t", f7.get(one).equals("Alice"));
            assertTrue ("get72t", f7.get(two).equals(bob));
            assertFalse ("get71f", f7.get(one).equals(dave));
            assertFalse ("get72f", f7.get(two).equals(dave));

        }
        catch (Exception e) {
            System.out.println("Exception thrown during accessors tests:");
            System.out.println(e);
            assertTrue ("accessors", false);
        }
    }

    /**
     * Tests the usual overloaded methods. (toString, equals, hashCode)
     */
    private void usual () {
        try {
	    //Testing toString
            assertTrue ("toString0",
                        f0.toString().equals("{...(0 keys mapped to values)...}"));
            assertTrue ("toString1",
                        f1.toString().equals("{...(1 keys mapped to values)...}"));
            assertTrue ("toString7",
                        f7.toString().equals("{...(2 keys mapped to values)...}"));

	    //Testing equals
            assertTrue ("equals00", f0.equals(f0));
            assertTrue ("equals33", f3.equals(f3));
            assertTrue ("equals55", f5.equals(f5));
            assertTrue ("equals55a", f5.equals(f0.include(one, carol).include(two,bob)));
            assertTrue ("equals46", f4.equals(f6));
            assertTrue ("equals64", f6.equals(f4));
            assertTrue ("equals27", f2.equals(f7));
            assertTrue ("equals72", f7.equals(f2));
            assertFalse ("equals78", f7.equals(f0.include(one, dave).include(two,dave)));
            assertFalse ("equals1_10", f1.equals(f10));
	    assertFalse ("equals25", f2.equals(f5));

            assertFalse ("equals01", f0.equals(f1));
            assertFalse ("equals02", f0.equals(f2));
            assertFalse ("equals10", f1.equals(f0));
            assertFalse ("equals12", f1.equals(f2));
            assertFalse ("equals21", f2.equals(f1));
            assertFalse ("equals23", f2.equals(f3));
            assertFalse ("equals35", f3.equals(f5));

            assertFalse ("equals0string", f0.equals("just a string"));
            assertFalse ("equals4string", f4.equals("just a string"));

            assertFalse ("equals0null", f0.equals(null));
            assertFalse ("equals1null", f1.equals(null));

	    //Testing hashCode
            assertTrue ("hashCode00", f0.hashCode() == f0.hashCode());
            assertTrue ("hashCode44", f4.hashCode() == f4.hashCode());
            assertTrue ("hashCode46", f4.hashCode() == f6.hashCode());
            assertTrue ("hashCode27", f2.hashCode() == f7.hashCode());

	    //Probabilistic Testing
            probabilisticTests();
        }
        catch (Exception e) {
            System.out.println("Exception thrown during usual tests:");
            System.out.println(e);
            assertTrue ("usual", false);
        }
    }


    /**
     * Tests the 0-argument or 1-argument iterator method,
     * as determined by nargs.
     */
    private void iterators (int nargs) {
        if (nargs == 0)
            iterators();
        else
            iteratorsSorted();
    }

    /**
     * Tests iterators
     */
    private void iterators () {
        try {
            FMap<Integer,String> f;
            FMap<Integer,String> m;
            Iterator<Integer> it;
            int count;
	    
            f = f0;
            m = f0;
            it = f.iterator();
            count = 0;
            while (it.hasNext()) {
                Integer k = it.next();
                m = m.include(k, f.get(k));
                count = count + 1;
            }
            assertTrue ("iterator001", f0.equals(m));
            assertFalse ("iteratorSanity", it.hasNext());
            assertTrue ("count001", f.size() == count);
	    
            f = f0;
            m = f0;
            count = 0;
            for (Integer k : f) {
                m = m.include(k, f.get(k));
                count = count + 1;
            }
            assertTrue ("iterator001", f0.equals(m));
            assertFalse ("iteratorSanity", it.hasNext());
            assertTrue ("count001", f.size() == count);
	    
            f = f5;
            m = f0;
            it = f.iterator();
            count = 0;
            while (it.hasNext()) {
                Integer k = it.next();
                m = m.include(k, f.get(k));
                count = count + 1;
            }
            assertTrue ("iterator551", f5.equals(m));
            assertFalse ("iteratorSanity", it.hasNext());
            assertTrue ("count551", f.size() == count);
	    
            f = f5;
            m = f0;
            count = 0;
            for (Integer k : f) {
                m = m.include(k, f.get(k));
                count = count + 1;
            }
            assertTrue ("iterator551", f5.equals(m));
            assertFalse ("iteratorSanity", it.hasNext());
            assertTrue ("count551", f.size() == count);
	    
            f = f6;
            m = f0;
            it = f.iterator();
            count = 0;
            while (it.hasNext()) {
                Integer k = it.next();
                m = m.include(k, f.get(k));
                count = count + 1;
            }
            assertTrue ("iterator461", f4.equals(m));
            assertFalse ("iteratorSanity", it.hasNext());
            assertTrue ("count461", f.size() == count);
	    
            f = f6;
            m = f0;
            count = 0;
            for (Integer k : f) {
                m = m.include(k, f.get(k));
                count = count + 1;
            }
            assertTrue ("iterator461", f4.equals(m));
            assertFalse ("iteratorSanity", it.hasNext());
            assertTrue ("count461", f.size() == count);
	    
            f = f4;
            m = f0;
            it = f.iterator();
            count = 0;
            while (it.hasNext()) {
                Integer k = it.next();
                m = m.include(k, f.get(k));
                count = count + 1;
            }
            assertTrue ("iterator641", f6.equals(m));
            assertFalse ("iteratorSanity", it.hasNext());
            assertTrue ("count641", f.size() == count);
	    
            f = f4;
            m = f0;
            count = 0;
            for (Integer k : f) {
                m = m.include(k, f.get(k));
                count = count + 1;
            }
            assertTrue ("iterator641", f6.equals(m));
            assertFalse ("iteratorSanity", it.hasNext());
            assertTrue ("count641", f.size() == count);
	    
            f = f7;
            m = f0;
            it = f.iterator();
            count = 0;
            while (it.hasNext()) {
                Integer k = it.next();
                m = m.include(k, f.get(k));
                count = count + 1;
            }
            assertTrue ("iterator271", f2.equals(m));
            assertFalse ("iteratorSanity", it.hasNext());
            assertTrue ("count271", f.size() == count);
	    
            f = f7;
            m = f0;
            count = 0;
            for (Integer k : f) {
                m = m.include(k, f.get(k));
                count = count + 1;
            }
            assertTrue ("iterator271", f2.equals(m));
            assertFalse ("iteratorSanity", it.hasNext());
            assertTrue ("count271", f.size() == count);
	    
            f = f8;
            m = f0;
            it = f.iterator();
            count = 0;
            while (it.hasNext()) {
                Integer k = it.next();
                m = m.include(k, f.get(k));
                count = count + 1;
            }
            assertTrue ("iterator881", f8.equals(m));
            assertFalse ("iteratorSanity", it.hasNext());
            assertTrue ("count881a", f.size() == count);
            assertTrue ("count881b", f.size() == f8.size());
	    
            f = f8;
            m = f0;
            count = 0;
            for (Integer k : f) {
                m = m.include(k, f.get(k));
                count = count + 1;
            }
            assertTrue ("iterator881", f8.equals(m));
            assertFalse ("iteratorSanity", it.hasNext());
            assertTrue ("count881a", f.size() == count);
            assertTrue ("count881b", f.size() == f8.size());
	    
            f = f2;
            m = f0;
            it = f.iterator();
            count = 0;
            while (it.hasNext()) {
                Integer k = it.next();
                m = m.include(k, f.get(k));
                count = count + 1;
            }
            assertTrue ("iterator721", f7.equals(m));
            assertFalse ("iteratorSanity", it.hasNext());
            assertTrue ("count721", f.size() == count);
	    
            f = f2;
            m = f0;
            count = 0;
            for (Integer k : f) {
                m = m.include(k, f.get(k));
                count = count + 1;
            }
            assertTrue ("iterator721", f7.equals(m));
            assertFalse ("iteratorSanity", it.hasNext());
            assertTrue ("count721", f.size() == count);
	    
            // Make sure the next() method throws the right exception.

            try {
                it.next();
                assertTrue ("next (exception)", false);
            }
            catch (NoSuchElementException e) {
                assertTrue ("next (exception)", true);
            }
            catch (Exception e) {
                assertTrue ("next (exception)", false);
            }

            // Make sure the remove() method throws the right exception.

            try {
                it = f.iterator();
                it.remove();
                assertTrue ("remove", false);
            }
            catch (UnsupportedOperationException e) {
                assertTrue ("remove", true);
            }
            catch (Exception e) {
                assertTrue ("remove", false);
            }
        }
        catch (Exception e) {
            System.out.println("Exception thrown during iterator tests:");
            System.out.println(e);
            assertTrue ("iterators", false);
        }
    }

    /**
     * Tests sorted iterators
     */
    private void iteratorsSorted () {
        try {
            FMap<Integer,String> f;
            FMap<Integer,String> m;
            Iterator<Integer> it;
            int count;

            f = f0;
            m = f0;
            it = f.iterator(usualIntegerComparator);
            count = 0;
            while (it.hasNext()) {
                Integer k = it.next();
                m = m.include(k, f.get(k));
                count = count + 1;
            }
            assertTrue ("iterator001", f0.equals(m));
            assertFalse ("iteratorSanity", it.hasNext());
            assertTrue ("count001", f.size() == count);

            f = f5;
            m = f0;
            it = f.iterator(reverseIntegerComparator);
            count = 0;
            while (it.hasNext()) {
                Integer k = it.next();
                m = m.include(k, f.get(k));
                count = count + 1;
            }
            assertTrue ("iterator551", f5.equals(m));
            assertFalse ("iteratorSanity", it.hasNext());
            assertTrue ("count551", f.size() == count);

            f = f6;
            m = f0;
            it = f.iterator(usualIntegerComparator);
            count = 0;
            while (it.hasNext()) {
                Integer k = it.next();
                m = m.include(k, f.get(k));
                count = count + 1;
            }
            assertTrue ("iterator461", f4.equals(m));
            assertFalse ("iteratorSanity", it.hasNext());
            assertTrue ("count461", f.size() == count);

            f = f4;
            m = f0;
            it = f.iterator(reverseIntegerComparator);
            count = 0;
            while (it.hasNext()) {
                Integer k = it.next();
                m = m.include(k, f.get(k));
                count = count + 1;
            }
            assertTrue ("iterator641", f6.equals(m));
            assertFalse ("iteratorSanity", it.hasNext());
            assertTrue ("count641", f.size() == count);

            f = f7;
            m = f0;
            it = f.iterator(usualIntegerComparator);
            count = 0;
            while (it.hasNext()) {
                Integer k = it.next();
                m = m.include(k, f.get(k));
                count = count + 1;
            }
            assertTrue ("iterator271", f2.equals(m));
            assertFalse ("iteratorSanity", it.hasNext());
            assertTrue ("count271", f.size() == count);

            f = f2;
            m = f0;
            it = f.iterator(reverseIntegerComparator);
            count = 0;
            while (it.hasNext()) {
                Integer k = it.next();
                m = m.include(k, f.get(k));
                count = count + 1;
            }
            assertTrue ("iterator721", f7.equals(m));
            assertFalse ("iteratorSanity", it.hasNext());
            assertTrue ("count721", f.size() == count);

            // The keys must be generated in ascending order,
            // as determined by the comparator.

            it = f6.iterator(reverseIntegerComparator);
            Integer previous = it.next();
            while (it.hasNext()) {
                Integer k = it.next();
                int comparison
                    = reverseIntegerComparator.compare(previous, k);
                assertTrue("ascending", comparison < 0);
                previous = k;
            }

            it = f6.iterator(usualIntegerComparator);
            previous = it.next();
            while (it.hasNext()) {
                Integer k = it.next();
                int comparison
                    = usualIntegerComparator.compare(previous, k);
                assertTrue("ascending2", comparison < 0);
                previous = k;
            }

            // Make sure the next() method throws the right exception.

            try {
                it.next();
                assertTrue ("next (exception)", false);
            }
            catch (NoSuchElementException e) {
                assertTrue ("next (exception)", true);
            }
            catch (Exception e) {
                assertTrue ("next (exception)", false);
            }

            // Make sure the remove() method throws the right exception.

            try {
                it = f.iterator(reverseIntegerComparator);
                it.remove();
                assertTrue ("remove", false);
            }
            catch (UnsupportedOperationException e) {
                assertTrue ("remove", true);
            }
            catch (Exception e) {
                assertTrue ("remove", false);
            }
        }
        catch (Exception e) {
            System.out.println("Exception thrown during iterator tests:");
            System.out.println(e);
            assertTrue ("iterators", false);
        }
    }


    /**
     * Tests equality of FMap values that were created using
     * 0-argument and 1-argument empty, as well as different
     * comparators.
     *
     * Precondition:
     *     this.f0 through this.f7 have already been initialized
     *     using 1-argument empty(_).
     */
    private void equality () {

        // According to the precondition, these should be red-black trees.

        FMap<Integer,String> f0 = this.f0;
        FMap<Integer,String> f1 = this.f1;
        FMap<Integer,String> f2 = this.f2;
        FMap<Integer,String> f3 = this.f3;
        FMap<Integer,String> f4 = this.f4;
        FMap<Integer,String> f5 = this.f5;
        FMap<Integer,String> f6 = this.f6;
        FMap<Integer,String> f7 = this.f7;

        creation(0);

        try {
 	    //Testing equals
            assertTrue ("equals00", f0.equals(f0));
            assertTrue ("equals33", f3.equals(f3));
            assertTrue ("equals55", f5.equals(f5));
            assertTrue ("equals55a", f5.equals(f0.include(one, carol).include(two,bob)));
            assertTrue ("equals46", f4.equals(f6));
            assertTrue ("equals64", f6.equals(f4));
            assertTrue ("equals27", f2.equals(f7));
            assertTrue ("equals72", f7.equals(f2));
            assertFalse ("equals78", f7.equals(f0.include(one, dave).include(two,dave)));
            assertFalse ("equals1_10", f1.equals(f10));

            assertFalse ("equals01", f0.equals(f1));
            assertFalse ("equals02", f0.equals(f2));
            assertFalse ("equals10", f1.equals(f0));
            assertFalse ("equals12", f1.equals(f2));
            assertFalse ("equals21", f2.equals(f1));
            assertFalse ("equals23", f2.equals(f3));
            assertFalse ("equals35", f3.equals(f5));

            assertFalse ("equals0string", f0.equals("just a string"));
            assertFalse ("equals4string", f4.equals("just a string"));

            assertFalse ("equals0null", f0.equals(null));
            assertFalse ("equals1null", f1.equals(null));

	    //Testing hashCode
            assertTrue ("hashCode00", f0.hashCode() == f0.hashCode());
            assertTrue ("hashCode44", f4.hashCode() == f4.hashCode());
            assertTrue ("hashCode46", f4.hashCode() == f6.hashCode());
            assertTrue ("hashCode27", f2.hashCode() == f7.hashCode());

            // Initialize this.f0 through this.f7 
            // that use a different comparator.

            creation (1, usualIntegerComparator);

 	    //Testing equals
            assertTrue ("equals00", f0.equals(f0));
            assertTrue ("equals33", f3.equals(f3));
            assertTrue ("equals55", f5.equals(f5));
            assertTrue ("equals55a", f5.equals(f0.include(one, carol).include(two,bob)));
            assertTrue ("equals46", f4.equals(f6));
            assertTrue ("equals64", f6.equals(f4));
            assertTrue ("equals27", f2.equals(f7));
            assertTrue ("equals72", f7.equals(f2));
            assertFalse ("equals78", f7.equals(f0.include(one, dave).include(two,dave)));
            assertFalse ("equals1_10", f1.equals(f10));
	    assertFalse ("equals25", f2.equals(f5));

            assertFalse ("equals01", f0.equals(f1));
            assertFalse ("equals02", f0.equals(f2));
            assertFalse ("equals10", f1.equals(f0));
            assertFalse ("equals12", f1.equals(f2));
            assertFalse ("equals21", f2.equals(f1));
            assertFalse ("equals23", f2.equals(f3));
            assertFalse ("equals35", f3.equals(f5));

            assertFalse ("equals0string", f0.equals("just a string"));
            assertFalse ("equals4string", f4.equals("just a string"));

            assertFalse ("equals0null", f0.equals(null));
            assertFalse ("equals1null", f1.equals(null));

	    //Testing hashCode
            assertTrue ("hashCode00", f0.hashCode() == f0.hashCode());
            assertTrue ("hashCode44", f4.hashCode() == f4.hashCode());
            assertTrue ("hashCode46", f4.hashCode() == f6.hashCode());
            assertTrue ("hashCode27", f2.hashCode() == f7.hashCode());
	}
        catch (Exception e) {
            System.out.println("Exception thrown during "
                               + "cross-representation equality tests:");
            System.out.println(e);
            assertTrue ("equality", false);
        }
    }

    /**
     * Probabilistic test for distribution of hash codes.
     */
    private void probabilisticTests () {
        probabilisticTests (200, 100);
        base = -2;
        probabilisticTests (200, 100);
        base = 412686306;
        probabilisticTests (200, 100);
    }

    // random number generator, initialed by probabilisticTests()
    Random rng;

    int base = 0;   // base for Frob hash codes

    private void initializeRNG () {
        rng = new Random(1059786856);
    }

    private void initializeRNGrandomly () {
        rng = new Random(System.nanoTime());
    }

    /**
     * Generate n random pairs of unequal FMap<K,V> values.
     * If k or more have the same hash code, then report failure.
     *
     * @param n number of random pairs
     * @param k number to report failue
     */
    private void probabilisticTests (int n, int k) {
        initializeRNGrandomly();
        int sameHash = 0;
        int i = 0;
        while (i < n) {
            FMap<Frob,Frob> f1 = randomFMap();
            FMap<Frob,Frob> f2 = randomFMap();
            if (! (f1.equals(f2))) {
                i = i + 1;
                if (f1.hashCode() == f2.hashCode())
                    sameHash = sameHash + 1;
            }
        }
	//System.out.println ("Same Hash: "+sameHash + " / " + n);
        if (sameHash >= k)
            assertTrue ("hashCode quality", 0 == sameHash);
    }

    /**
     * Returns a randomly selected FMap<Frob,Frob>.
     */
    private FMap<Frob,Frob> randomFMap () {
        // First pick the size.
        double x = rng.nextDouble();
        double y = 0.5;
        int size = 0;
        while (y > x) {
            size = size + 1;
            y = y / 2.0;
        }
        FMap<Frob,Frob> f = FMap.empty();
        while (f.size() < size)
            f = f.include (randomFrob(), randomFrob());
        return f;
    }

    /**
     * Returns a randomly selected Frob.
     */
    private Frob randomFrob () {
        int h = base + rng.nextInt(5);
        return new Frob(h);
    }

    /**
     * Frob
     */
    private static class Frob {
        int theHash;
        Frob (int h) { theHash = h; }

	/**
	 * hashCode for Frob
	 * @return hashCode
	 */
        public int hashCode () {
            return theHash;
        }
    }

    ////////////////////////////////////////////////////////////////

    private int totalTests = 0;       // tests run so far
    private int totalErrors = 0;      // errors so far

    /**
     * For anonymous tests.  Deprecated.
     * @param result to test
     */
    private void assertTrue (boolean result) {
	assertTrue ("anonymous", result);
    }

    /**
     * Prints failure report if the result is not true.
     * @param name name of test
     * @param result result to test
     */
    private void assertTrue (String name, boolean result) {
        if (! result) {
            System.out.println ();
            System.out.println ("***** Test failed ***** "
                                + name + ": " +totalTests);
            totalErrors = totalErrors + 1;
        }/*else{
	   System.out.println ("^^^^ Test passed ^^^^ "
	   + name + ": " +totalTests);
	   }*/
        totalTests = totalTests + 1;
    }

    /**
     * For anonymous tests.  Deprecated.
     * @return result to test
     */
    private void assertFalse (boolean result) {
        assertTrue (! result);
    }

    /**
     * Prints failure report if the result is not false.
     * @param name name of test
     * @param result result to test
     */
    private void assertFalse (String name, boolean result) {
        assertTrue (name, ! result);
    }

}
