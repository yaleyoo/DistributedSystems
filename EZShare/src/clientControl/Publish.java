package clientControl;

import bean.ClientJSON;
import clientIO.Sender;
import bean.*;
import net.sf.json.JSONObject;


public class Publish {
	
	private ClientJSON clientJSON;
	
	public Publish() {
		clientJSON = new ClientJSON();
	}

	public void setCommand(String command) {
		this.clientJSON.setCommand(command); 
	}
	
	public void setResource(Resource resource) {
		this.clientJSON.setResource(resource);
	}
	
	public void sendRequest() {
		JSONObject publishItems = new JSONObject();
		
		publishItems.put("command", clientJSON.getCommand());
		publishItems.put("resource", clientJSON.getResource());
		

		Sender sender = new Sender();
		sender.sendRequest(publishItems);
	}
}

 
