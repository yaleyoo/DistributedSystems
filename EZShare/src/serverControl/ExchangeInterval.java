package serverControl;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ExchangeInterval {
	
	public static void exChangeIntervalLimit(long intervalLimit){
		SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/yy hh:mm:ss");
		System.out.println(sdf.format(new Date())+" - [EZShare.serverControl] - [INFO] - set Exchange on");
		ConnectionIntervalLimit cIL = new ConnectionIntervalLimit();
		cIL.connectionIntervalLimit(intervalLimit);
	}
}
