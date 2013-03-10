

/* Lists */
import tester.*;

// [X -> Y]
interface Fun<X,Y> {
    Y apply(X x);
}

class Add1 implements Fun<Integer,Integer> {
    public Integer apply(Integer x) {
        return x+1;
    }
}

class IntToStr implements Fun<Integer,String> {
    IntToStr() {}
    public String apply(Integer x) {
        return x.toString();
    }
}

interface List<X> {
    List<X> rev();
    List<X> revAcc(List<X> acc);
    <Y> List<Y> map(Fun<X,Y> f);
    <Y> Y accept(ListVisitor<X,Y> v);
}

interface ListVisitor<X,Y> {
    Y visitEmpty();
    Y visitCons(X first, List<X> rest);
}

class GetFirst implements ListVisitor<Integer,Integer> {
    public Integer visitEmpty() { throw new RuntimeException("You suck"); }
    public Integer visitCons(Integer first, List<Integer> rest) {
        return first;
    }
}


class Empty<X> implements List<X> {
    Empty() {}

    public List<X> rev() {
        return new Empty<X>();
    }

    public List<X> revAcc(List<X> acc) {
        return acc;
    }

    public <Y> List<Y> map(Fun<X,Y> f) {
        return new Empty<Y>();
    }

    public <Y> Y accept(ListVisitor<X,Y> v) {
        return v.visitEmpty();
    }
}

// new Cons<Integer>().first
class Cons<X> implements List<X> {
    X first;
    List<X> rest;

    Cons(X first, List<X> rest) {
        this.first = first;
        this.rest = rest;
    }

    Cons(X first) {
        this.first = first;
        this.rest = new Empty<X>();
    }

    public List<X> rev() {
        return this.revAcc(new Empty<X>());
    }

    public List<X> revAcc(List<X> acc) {
        return this.rest.revAcc(new Cons<X>(this.first, acc));
    }

    public <Y> List<Y> map(Fun<X,Y> f) {
        return new Cons<Y>(f.apply(this.first),
                           this.rest.map(f));
    }

    public <Y> Y accept(ListVisitor<X,Y> v) {
        return v.visitCons(this.first, this.rest);
    }

}
