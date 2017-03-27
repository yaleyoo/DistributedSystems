package serverControl;

public class ExchangeInterval {
	
	public static void exChangeIntervalLimit(long intervalLimit){
		System.out.println("[EZShare.serverControl] - [INFO] - set Exchange on");
		ConnectionIntervalLimit cIL = new ConnectionIntervalLimit();
		cIL.connectionIntervalLimit(intervalLimit);
	}
}
