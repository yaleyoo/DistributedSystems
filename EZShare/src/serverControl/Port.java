package serverControl;

public class Port {
	public static int port = 3780;
	
	public  void bindtoPort(int port){
		this.port = port;
		System.out.println("[EZShare.serverControl] - [INFO] - bound to port "+port);
	}
	
	public  void bindtoPort(){
		System.out.println("[EZShare.serverControl] - [INFO] - bound to port "+port);
	}

	
	
	
}
