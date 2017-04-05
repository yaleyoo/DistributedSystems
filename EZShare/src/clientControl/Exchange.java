package clientControl;

import bean.*;
import clientIO.Sender;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


public class Exchange {
	
	private ClientJSON clientJSON;
	
	public Exchange() {
		clientJSON = new ClientJSON();
	}

	public void setCommand(String command) {
		this.clientJSON.setCommand(command); 
	}
	
	public void setServerList(String[] serverList) {
		this.clientJSON.setServerList(serverList);
	}
	public void sendRequest() {
		JSONObject exchangeItems = new JSONObject();

		String[] serverLists = clientJSON.getServerList();
		
		JSONArray value = new JSONArray();
		
		for (String serverList : serverLists) {
			String[] server = serverList.split(":");
			exchangeItems.put("hostname", server[0]);
			exchangeItems.put("port", server[1]);
			value.add(exchangeItems);
		}
		
		Sender sender = new Sender();
		sender.sendRequest(exchangeItems);
	}
}
