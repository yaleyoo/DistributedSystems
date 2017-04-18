package processor;

import java.util.List;

import bean.ClientJSON;
import net.sf.json.JSONObject;

public class Processor {
	ClientJSON clientJSON;
	
	public ClientJSON getClientJSON(JSONObject jObject){
		clientJSON = (ClientJSON) JSONObject.toBean(jObject, ClientJSON.class);
		return clientJSON;
	}
	
	public List<JSONObject> assignQueryRequest(){
		QueryProcessor qp = new QueryProcessor();
		
		return qp.process(clientJSON);
	}
	
	public JSONObject assignRequest(){
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
