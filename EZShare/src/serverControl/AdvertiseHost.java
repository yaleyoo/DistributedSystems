package serverControl;

import java.net.InetAddress;
import java.net.UnknownHostException;

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
		this.advertiseHost = hostname;
		System.out.println("[EZShare.serverControl] - [INFO] - using advertised hostname "+advertiseHost);
	}
	
	public void setAdvertiseHost(){
		System.out.println("[EZShare.serverControl] - [INFO] - using advertised hostname "+advertiseHost);
	}
	


	
	
	

}
