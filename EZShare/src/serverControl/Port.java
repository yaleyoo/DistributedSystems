package serverControl;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Port {
	public static int port = 3780;
	
	public  void bindtoPort(int port){
		Port.port = port;
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss.SSS");
		System.out.println(sdf.format(new Date())+" - [EZShare.serverControl] - [INFO] - bound to port "+port);
	}
	
	public  void bindtoPort(){
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss.SSS");
		System.out.println(sdf.format(new Date())+" - [EZShare.serverControl] - [INFO] - bound to port "+port);
	}

	
	
	
}
