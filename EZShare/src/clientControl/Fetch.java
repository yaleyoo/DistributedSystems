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
		JSONObject fetchItems = new JSONObject();
		
		fetchItems.put("command", fetchJson.getCommand());
		fetchItems.put("resource", fetchJson.getResource());
		

		Sender sender = new Sender();
		sender.sendRequest(fetchItems);
	}
}

 
