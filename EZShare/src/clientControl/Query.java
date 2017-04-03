package clientControl;

import bean.ClientJSON;
import clientIO.Sender;
import bean.*;
import net.sf.json.JSONObject;


public class Query {
	
	private ClientJSON queryJson;
	
	public Query() {
		queryJson = new ClientJSON();
	}

	public void setCommand(String command) {
		this.queryJson.setCommand(command); 
	}
	
	public void setResource(Resource resource) {
		this.queryJson.setResource(resource);
	}
	
	public void sendRequest() {
		JSONObject queryServer = new JSONObject();
		
		queryServer.put("command", queryJson.getCommand());
		queryServer.put("relay", true);
		queryServer.put("resource", queryJson.getResource());
		

		Sender sender = new Sender();
		sender.sendRequest(queryServer);
	}
}