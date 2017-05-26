package clientControl;

import bean.Resource;
import clientIO.Sender;
import net.sf.json.JSONObject;

public class Remove {
	public void sendRequest(Resource resource){
		JSONObject jObject = new JSONObject();
		jObject.put("command", "REMOVE");
		jObject.put("resource", resource);
		
		Sender sender = new Sender();
		sender.sendRequest(jObject);
	}
}
