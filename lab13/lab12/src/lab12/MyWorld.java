package lab12;
import colors.*;
import funworld.*;
import tester.*;
import geometry.*;

public class MyWorld extends World {
	String status;
	public MyWorld(String status) {
		this.status = status;
	}
	public WorldImage makeImage() {
		// TODO Auto-generated method stub
		return new TextImage(new Posn(250, 250), status, java.awt.Color.BLACK);
	}

	public void setStatus() {
		this.status = new Driver().update();
	}
	
	public World onTick() {
		return this;
	}

}

