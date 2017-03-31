package clientControl;

import bean.ClientJSON;
import clientIO.Sender;
import bean.*;
import net.sf.json.JSONObject;


public class Share {
	
	private ClientJSON shareJson;
	
	public Share() {
		shareJson = new ClientJSON();
	}

	public void setCommand(String command) {
		this.shareJson.setCommand(command); 
	}
	
	public void setResource(Resource resource) {
		this.shareJson.setResource(resource);
	}
	
	public void sendRequest() {
		JSONObject publishItems = new JSONObject();
		
		publishItems.put("command", shareJson.getCommand());
		publishItems.put("resource", shareJson.getResource());
		

		Sender sender = new Sender();
		sender.sendRequest(shareJson);
	}
}

 
