package clientControl;

import bean.ClientJSON;
import bean.Resource;
import net.sf.json.JSONObject;


public class Publish {
	
	private ClientJSON clientJson;
	
	public Publish() {
		clientJson = new ClientJSON();
	}

	public void setCommand(String command) {
		this.clientJson.setCommand(command); 
	}
	
	public void setResource(Resource resource) {
		this.clientJson.setResource(resource);
	}
	
	public void printPublish() {
		JSONObject publishItems = new JSONObject();
		
		publishItems.put("command", clientJson.getCommand());
		publishItems.put("resource", clientJson.getResource());
		
		System.out.println(publishItems);
	}
}

 
