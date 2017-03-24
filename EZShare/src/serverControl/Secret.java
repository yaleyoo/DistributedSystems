package serverControl;

import java.util.UUID;

public class Secret {
	public static String secret;
	
	public Secret(){
		UUID uuid = UUID.randomUUID();
		this.secret = uuid.toString();
	}
	
	public void setSecret(String secret){
		this.secret = secret;
		System.out.println("[EZShare.serverControl] - [INFO] - using secret: "+secret);
	}
	
	public void setSecret(){
		System.out.println("[EZShare.serverControl] - [INFO] - using secret: "+secret);
	}

	
	
}
