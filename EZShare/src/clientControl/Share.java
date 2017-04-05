package clientControl;

import bean.Resource;
import clientIO.Sender;
import net.sf.json.JSONObject;

public class Share {
	
	public void sendRequest(String secret, Resource resource){
		JSONObject jObject = new JSONObject();
		jObject.put("command", "SHARE");
		jObject.put("secret", secret);
		jObject.put("resource", resource);
		
		Sender sender = new Sender();
		sender.sendRequest(jObject);
	}
}
