package clientControl;

import bean.ResourceTemplate;
import clientIO.Sender;
import net.sf.json.JSONObject;

public class Fetch {
	
	public void sendRequest(ResourceTemplate rt){
		
		
		JSONObject jObject = new JSONObject();
		jObject.put("command", "FETCH");
		jObject.put("resourceTemplate", rt);
		
		Sender sender = new Sender();
		sender.sendRequest(jObject);
		
	}
}
