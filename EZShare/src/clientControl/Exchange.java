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
	
	// ------------------------------secureExchange----------------------------------------------//
	public void setSecureServerList(String[] secureServerList) {
		this.clientJSON.setSecureServerList(secureServerList);
	}
	// ------------------------------secureExchange----------------------------------------------//
	
	public void sendRequest() {
		JSONObject exchangeItems = new JSONObject();

		String[] serverLists = clientJSON.getServerList();
		
		JSONArray value = new JSONArray();
		
		for (String serverList : serverLists) {
			String[] server = serverList.split(":");
			JSONObject temp = new JSONObject();
			temp.put("hostname", server[0]);
			temp.put("port", server[1]);
			value.add(temp);
		}
		
		exchangeItems.put("command",clientJSON.getCommand());
		exchangeItems.put("serverList", value);
		
		Sender sender = new Sender();
		sender.sendRequest(exchangeItems);
	}
	
	// ------------------------------secureExchange----------------------------------------------//

	public void sendSecureRequest() {
		JSONObject secureExchangeItems = new JSONObject();

		String[] secureServerLists = clientJSON.getSecureServerList();

		JSONArray value = new JSONArray();

		for (String secureServerList : secureServerLists) {
			String[] secureServer = secureServerList.split(":");
			JSONObject temp = new JSONObject();
			temp.put("hostname", secureServer[0]);
			temp.put("port", secureServer[1]);
			value.add(temp);
		}

		secureExchangeItems.put("command", clientJSON.getCommand());
		secureExchangeItems.put("secureServerList", value);

		Sender sender = new Sender();
		sender.setisSecure(true);
		sender.sendRequest(secureExchangeItems);
	}

	// ------------------------------secureExchange----------------------------------------------//
	
}
