package serverControl;

public class Debug {
	public static boolean isDebug=false;
	
	public  void defineDebug(boolean flag){
		isDebug = flag;
		System.out.println("[EZShare.serverControl] - [INFO] - setDebug on");
	}

	
	
	
	
}
