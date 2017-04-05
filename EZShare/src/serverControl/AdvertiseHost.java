package serverControl;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AdvertiseHost {
	public static String advertiseHost;
	
	public AdvertiseHost(){
		try {
			advertiseHost = InetAddress.getLocalHost().toString();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void setAdvertiseHost(String hostname){
		AdvertiseHost.advertiseHost = hostname;
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss.SSS");
		System.out.println(sdf.format(new Date())+" - [EZShare.serverControl] - [INFO] - using advertised hostname "+advertiseHost);
	}
	
	public void setAdvertiseHost(){
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss.SSS");
		System.out.println(sdf.format(new Date())+" - [EZShare.serverControl] - [INFO] - using advertised hostname "+advertiseHost);
	}
	


	
	
	

}
