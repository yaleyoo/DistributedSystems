package serverControl;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ConnectionIntervalLimit {
	public static int intervalLimit = 600;
	
	public  void connectionIntervalLimit(long intervalLimit){
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss.SSS");
		System.out.println(sdf.format(new Date())+" - [EZShare.serverControl] - [INFO] - set internal limits: "+intervalLimit);
		/*
		 * stub
		 * 
		 * */
	}
}
