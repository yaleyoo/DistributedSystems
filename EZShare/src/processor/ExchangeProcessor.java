package processor;


import bean.ClientJSON;
import net.sf.json.JSONObject;
import server.Main;

public class ExchangeProcessor {
	
	public JSONObject process(ClientJSON cJSON){
		String[] serverList = cJSON.getServerList();
		for(String s:serverList){
			boolean is_exist=false;
			for(String k:Main.serverList){
				if(k.equals(s))
					is_exist = true;
			}
			if(!is_exist)
			Main.serverList.add(s);
		}
		
		if(serverList.length==0){
			JSONObject jObject = new JSONObject();
			jObject.put("response", "error");
			jObject.put("errorMessage", "missing or invalid server list");
			return jObject;
		}
		
		
		JSONObject jObject = new JSONObject();
		jObject.put("response", "success");
		return jObject;
	}
}
