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
	
	public void setSecret(String secret){
		this.shareJson.setSecret(secret);
	}
	
	public void setResource(Resource resource) {
		this.shareJson.setResource(resource);
	}
	
	public void sendRequest() {
		JSONObject shareItems = new JSONObject();
		
		shareItems.put("command", shareJson.getCommand());
		shareItems.put("secret", shareJson.getSecret());
		shareItems.put("resource", shareJson.getResource());
		

		Sender sender = new Sender();
		sender.sendRequest(shareItems);
	}
}

 
