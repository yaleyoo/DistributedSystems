package clientControl;

import bean.ResourceTemplate;
import clientIO.Sender;
import net.sf.json.JSONObject;

public class Query {
	public void sendRequest(ResourceTemplate rt){
		JSONObject jObject = new JSONObject();
		jObject.put("command", "QUERY");
		jObject.put("relay", true);
		jObject.put("resourceTemplate", rt);
		
		Sender sender = new Sender();
		sender.sendRequest(jObject);
	}
}
