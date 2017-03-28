package clientControl;

import bean.ClientJSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


public class Exchange {
	
	private ClientJSON clientJson;
	
	public Exchange() {
		clientJson = new ClientJSON();
	}

	public void setCommand(String command) {
		this.clientJson.setCommand(command); 
	}
	
	public void setServerList(String[] serverList) {
		this.clientJson.setServerList(serverList);
	}
	public void printExchange() {
		JSONObject exchangeItems = new JSONObject();

		String[] serverLists = clientJson.getServerList();
		
		JSONArray value = new JSONArray();
		
		for (String serverList : serverLists) {
			String[] server = serverList.split(":");
			exchangeItems.put("hostname", server[0]);
			exchangeItems.put("port", server[1]);
			value.add(exchangeItems);
		}
		
		JSONObject exchangeObj = new JSONObject();
		exchangeObj.put("command", clientJson.getCommand());
		exchangeObj.put("serverList", value);
		
		System.out.println(exchangeObj);
	}
}
