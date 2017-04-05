package serverControl;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Debug {
	public static boolean isDebug=false;
	
	public  void defineDebug(boolean flag){
		isDebug = flag;
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss.SSS");
		System.out.println(sdf.format(new Date())+" - [EZShare.serverControl] - [INFO] - setDebug on");
	}

	
	
	
	
}
