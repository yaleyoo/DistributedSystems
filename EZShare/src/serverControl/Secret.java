package serverControl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class Secret {
	public static String secret;
	
	public Secret(){
		UUID uuid = UUID.randomUUID();
		Secret.secret = uuid.toString();
	}
	
	public void setSecret(String secret){
		Secret.secret = secret;
		SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/yy hh:mm:ss");
		System.out.println(sdf.format(new Date())+" - [EZShare.serverControl] - [INFO] - using secret: "+secret);
	}
	
	public void setSecret(){
		SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/yy hh:mm:ss");
		System.out.println(sdf.format(new Date())+" - [EZShare.serverControl] - [INFO] - using secret: "+secret);
	}

	
	
}
