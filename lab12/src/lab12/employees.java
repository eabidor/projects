
public class employees {

}


public interface Emp {
	// returns the name of this employee
	public String name();
	// returns the number of tasks this employee has
	public Integer tasks();
	// returns the total number of subordinates of this employee
	public Integer countSubs();
	// returns a list of employees corresponding to all of the subordinates
	// of this employee
	public ILOE fullUnit();
	// returns a boolean indicating if this employee has a peon of the 
	// given name
	public boolean hasPeon(String n);
}

public interface ILOE {
	// appends this list of employees to the given list of employees
	public ILOE append(ILOE i);
	// accepts the LOE visitor and returns the type of that visitor
	public <Y> Y accept(iVisit<Y> v);
}

public interface iVisit<Y> {

	// visits an empty list of employees, returning a value of type Y
	public Y visitMT();
	// visits a cons list of employees, returning a value of type y
	public Y visitCons(Emp first, ILOE rest);
}

public class Worker implements Emp {
	public String name;
	public Integer tasks;
	
	public Worker(String name, Integer tasks) {
		this.name = name;
		this.tasks = tasks;
	}

	public String name() {
		return this.name;
	}
	
	public Integer tasks() {
		return this.tasks;
	}

	public Integer countSubs() {
		return 0;
	}

	public ILOE fullUnit() {
		return new MtLOE();
	}
	
	public boolean hasPeon(String n) {
		return false;
	}
}

public class Boss implements Emp {
	public String name;
	public Integer tasks;
	public String unit;
	public ILOE peons;
	
	public Boss(String name, Integer tasks, String unit, ILOE peons) {
		this.name = name;
		this.tasks = tasks;
		this.unit = unit;
		this.peons = peons;
	}

	public String name() {
		return this.name;
	}
	
	public Integer tasks() {
		return this.tasks;
	}

	public Integer countSubs() {
		return this.peons.accept(new CountEmp());
	}

	public ILOE fullUnit() {
		return this.peons.accept(new MakeFullUnit());
	}
	
	public boolean hasPeon(String n) {
		return this.peons.accept(new HasPeon(n));
	}
}

public class MtLOE implements ILOE {
	// returns the given list of employees, since this LOE is empty
	public ILOE append(ILOE i) {
		return i;
	}
	// accepts the LOE visitor and returns the type of that visitor
	public <Y> Y accept(iVisit<Y> v) {
		return v.visitMT();
	}
}

public class ConsLOE implements ILOE {
	public Emp first;
	public ILOE rest;
	
	public ConsLOE(Emp first, ILOE rest) {
		this.first = first;
		this.rest = rest;
	}

	// appends this list of employees to the given list of employees,
	// by calling append recursively
	public ILOE append(ILOE i) {
		return i;
	}
	// accepts the LOE visitor and returns the type of that visitor
	public <Y> Y accept(iVisit<Y> v) {
		return v.visitCons(this.first, this.rest);
	}
}

public CountEmp implements iVisit<Integer> {
	
	public Integer visitMT() {
		return 0;
	}
	// visits a cons list of employees, returning a value of type y
	public Y visitCons(Emp first, ILOE rest) {
		return 1 + first.countSubs() + rest.accept(new CountEmp());
	}

}
public MakeFullUnit implements iVisit<ILOE> {
	
	public ILOE visitMT() { 
		return new visitMT();
	}
	public ILOE visitCons(Emp first, ILOE rest) {
		return new ConsLOE(first, first.fullUnit().append(rest.accept(this)));
	}

}

public HasPeon implements iVisit<boolean> {
	public String n;
	
	public HasPeon(String n) {
		this.n = n;
	}
	
	public boolean visitMT() {
		return false;
	}
	// visits a cons list of employees, returning a value of type y
	public boolean visitCons(Emp first, ILOE rest) {
		return (first.name().equals(n) || first.hasPeon(n) || rest.accept(this));
}
	
public class Examples 
	