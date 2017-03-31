package clientControl;

import bean.ClientJSON;
import clientIO.Sender;
import bean.*;
import net.sf.json.JSONObject;


public class Fetch {
	
	private ClientJSON fetchJson;
	
	public Fetch() {
		fetchJson = new ClientJSON();
	}

	public void setCommand(String command) {
		this.fetchJson.setCommand(command); 
	}
	
	public void setResource(Resource resource) {
		this.fetchJson.setResource(resource);
	}
	
	public void sendRequest() {
		JSONObject publishItems = new JSONObject();
		
		publishItems.put("command", fetchJson.getCommand());
		publishItems.put("resource", fetchJson.getResource());
		

		Sender sender = new Sender();
		sender.sendRequest(fetchJson);
	}
}

 
