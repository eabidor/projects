package lab12;
import java.util.List;

import cs2510h.*;
import org.apache.commons.*;
import winterwell.*;
import winterwell.jtwitter.Status;

import com.winterwell.*;
import oauth.*;
import com.google.gdata.*;
public class Driver {
	public static void main(String[] args) {
		ITwitter twitterClient = new TwitterFactory().makeTwitter();
		String status = "";
		if (args[0].equals("status") && args.length == 1) {
			status = twitterClient.getStatus().getText();
		} else if (args[0].equals("status")) {
			String stat = "";
			for(int i = 1; i < args.length; i++) {
				stat = stat + " " + args[i];
			}
			try {
				twitterClient.setStatus(stat);
				status = "status set successfully";
			} catch (Exception e) {
				status = e.getMessage();
			}
		} else if (args[0].equals("timeline")) {
			//twitterClient.setStatus(stat);
			List<Status> timeline = twitterClient.getHomeTimeline();
			for(Status i : timeline) {
				System.out.println(i.getText());
			}	
		} else {
			status = "invalid command";
		}
		System.out.println(status);
		
		new MyWorld("").bigBang(500,500,1);
	}
	
	public String update() {
		ITwitter twitterClient = new TwitterFactory().makeTwitter();
		return twitterClient.getStatus().getText();
	}

	
	
	
}
