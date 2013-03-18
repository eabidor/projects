package lab11;
import java.util.*;
public class Examples {
	public <X, Y> Iterable<Y> map(Iterable<X> i, Fun<X,Y> f) {
		return new consDict<Y>(f.apply(xVals.next())) 
	}
}
