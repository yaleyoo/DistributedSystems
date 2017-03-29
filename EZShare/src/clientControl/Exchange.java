package clientControl;

import bean.*;
import clientIO.Sender;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


public class Exchange {
	
	private ExchangeJSON exchangeJson;
	
	public Exchange() {
		exchangeJson = new ExchangeJSON();
	}

	public void setCommand(String command) {
		this.exchangeJson.setCommand(command); 
	}
	
	public void setServerList(String[] serverList) {
		this.exchangeJson.setServerList(serverList);
	}
	public void sendRequest() {
		JSONObject exchangeItems = new JSONObject();

		String[] serverLists = exchangeJson.getServerList();
		
		JSONArray value = new JSONArray();
		
		for (String serverList : serverLists) {
			String[] server = serverList.split(":");
			exchangeItems.put("hostname", server[0]);
			exchangeItems.put("port", server[1]);
			value.add(exchangeItems);
		}
		
		Sender sender = new Sender();
		sender.sendRequest(exchangeJson);
	}
}
