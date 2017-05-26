package processor;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import bean.ClientJSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import serverIO.Listener;

public class Processor {
	ClientJSON clientJSON;
	
	public ClientJSON getClientJSON(JSONObject jObject){
		if(jObject.get("command").equals("EXCHANGE")){
			
			// ------------------------------secure----------------------------------------------//
			if (Listener.isSecure == true) {
				clientJSON = new ClientJSON();
				List<String> temp_secureServerList = new ArrayList<String>();
				clientJSON.setCommand("EXCHANGE");
				JSONArray jArray = jObject.getJSONArray("secureServerList");
				@SuppressWarnings("unchecked")
				List<JSONObject> JSON_List = JSONArray.toList(jArray, new JSONObject(), new JsonConfig());
				
				for(int i=0;i<JSON_List.size();i++){
					JSONObject json = (JSONObject) JSON_List.get(i);
					temp_secureServerList.add(json.getString("hostname")+":"+json.getString("port"));
				}
				clientJSON.setSecureServerList(temp_secureServerList.toArray(new String[temp_secureServerList.size()]));

			}
			// ------------------------------secure----------------------------------------------//
			
			else{
			clientJSON = new ClientJSON();
			List<String> temp_serverList = new ArrayList<String>();
			clientJSON.setCommand("EXCHANGE");
			JSONArray jArray = jObject.getJSONArray("serverList");
			@SuppressWarnings("unchecked")
			List<JSONObject> JSON_List = JSONArray.toList(jArray, new JSONObject(), new JsonConfig());
		
			for(int i=0;i<JSON_List.size();i++){
				JSONObject json = (JSONObject) JSON_List.get(i);
				temp_serverList.add(json.getString("hostname")+":"+json.getString("port"));
			}
			clientJSON.setServerList(temp_serverList.toArray(new String[temp_serverList.size()]));
			}
			
		}
		else{
			clientJSON = (ClientJSON) JSONObject.toBean(jObject, ClientJSON.class);
		}
		return clientJSON;
	}
	
	public List<JSONObject> assignQueryRequest(){
		QueryProcessor qp = new QueryProcessor();
		
		return qp.process(clientJSON);
	}
	
	public void assignSubscribeRequest(SubscribeProcessor subp, DataOutputStream out) throws IOException{	
		subp.process(clientJSON, out);
	}
	
	public List<JSONObject> assignFetchRequest(){
		FetchProcessor fp = new FetchProcessor();
		return fp.process(clientJSON);
	}
	
	public JSONObject assignRequest() throws IOException{
		String command = clientJSON.getCommand();
		
		if(command.equals("EXCHANGE")){
			ExchangeProcessor ep = new ExchangeProcessor();
			return ep.process(clientJSON);
		}
		
		if(command.equals("PUBLISH")){
			PublishProcessor pp = new PublishProcessor();
			return pp.process(clientJSON);
		}
		
		if(command.equals("REMOVE")){
			RemoveProcessor rp = new RemoveProcessor();
			return rp.process(clientJSON);
		}
		
		if(command.equals("SHARE")){
			ShareProcessor sp = new ShareProcessor();
			return sp.process(clientJSON);
		}
		
		
		else{
			JSONObject jObject = new JSONObject();
			jObject.put("response", "error");
			jObject.put("erroMessage", "invalid command type");
			return jObject;
		}
		
	}
}
