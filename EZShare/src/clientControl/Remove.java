package clientControl;

import bean.ClientJSON;
import clientIO.Sender;
import bean.*;
import net.sf.json.JSONObject;


public class Remove {
	
	private ClientJSON removeJson;
	
	public Remove() {
		removeJson = new ClientJSON();
	}

	public void setCommand(String command) {
		this.removeJson.setCommand(command); 
	}
	
	public void setResource(Resource resource) {
		this.removeJson.setResource(resource);
	}
	
	public void sendRequest() {
		JSONObject removeItem = new JSONObject();
		
		removeItem.put("command", removeJson.getCommand());
		removeItem.put("resource", removeJson.getResource());
		

		Sender sender = new Sender();
		sender.sendRequest(removeItem);
	}
}