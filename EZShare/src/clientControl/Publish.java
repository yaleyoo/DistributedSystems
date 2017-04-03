package clientControl;

import bean.ClientJSON;
import clientIO.Sender;
import bean.*;
import net.sf.json.JSONObject;


public class Publish {
	
	private ClientJSON publishJson;
	
	public Publish() {
		publishJson = new ClientJSON();
	}

	public void setCommand(String command) {
		this.publishJson.setCommand(command); 
	}
	
	public void setResource(Resource resource) {
		this.publishJson.setResource(resource);
	}
	
	public void sendRequest() {
		
		JSONObject publishItems = new JSONObject();
		
		publishItems.put("command", publishJson.getCommand());
		publishItems.put("resource", publishJson.getResource());
		
		
		
		System.out.println(publishItems);
		

		Sender sender = new Sender();
		sender.sendRequest(publishItems);
	}
}

 
