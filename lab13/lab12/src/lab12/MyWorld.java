package lab12;
//import colors.*;
import funworld.*;
//import tester.*;
import geometry.*;
import cs2510h.*;
import org.apache.commons.*;
import winterwell.*;
import winterwell.jtwitter.Status;
import com.winterwell.*;
//import oauth.*;
//import com.google.gdata.*;
public class MyWorld extends World {
	ITwitter i;
	String s;
	String entry;
	boolean typing;
	public MyWorld() {
		this.i = new TwitterFactory().makeTwitter();
		this.s = "";
		this.entry = "";
	}
	public WorldImage makeImage() {
		// TODO Auto-generated method stub
		TextImage stat = new TextImage(new Posn(250, 250),
										this.s, java.awt.Color.BLACK);
		TextImage ent = new TextImage(new Posn(250, 300),
				this.entry, java.awt.Color.BLACK);
		return stat.overlayImages(ent);
	}
	
	public World onKeyEvent(String ke) {
		if(!ke.equals("up")) {
			this.entry = this.entry + ke;
		} else {
			this.i.setStatus(entry);
		}
		return this;
	}
	
	public World onTick() {
		if (!this.typing) {
		this.s = this.i.getStatus().getText();
		return this;
		} else {
			return this;
		}
	}
}

