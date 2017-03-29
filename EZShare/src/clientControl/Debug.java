package clientControl;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Debug {
	public static boolean isDebug;
	
	public  void defineDebug(boolean flag){
		isDebug = flag;
		SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/yy hh:mm:ss");
		System.out.println(sdf.format(new Date())+" - [EZShare.clientControl] - [INFO] - setDebug on");
	}
	
}
