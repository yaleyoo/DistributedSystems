package processor;

import bean.ClientJSON;
import net.sf.json.JSONObject;

public class Processor {
	ClientJSON clientJSON;
	
	public void getClientJSON(JSONObject jObject){
		clientJSON = (ClientJSON) JSONObject.toBean(jObject, ClientJSON.class);
	}
	
	public JSONObject assignRequest(){
		String command = clientJSON.getCommand();
		
		if(command.equals("EXCHANGE")){
			//ExchangeProcessor ep = new ExchangeProcessor();
			//return ep.process(clientJSON);
		}
		
		if(command.equals("FETCH")){
			//FetchProcessor fp = new FetchProcessor();
			//fp.process(clientJSON);
		}
		
		if(command.equals("PUBLISH")){
			PublishProcessor publicProcessor = new PublishProcessor();
			
			return publicProcessor.process(clientJSON);
		}
		
		if(command.equals("QUERY")){
			//QueryProcessor qp = new QueryProcessor();
			//qp.process(clientJSON);
		}
		
		if(command.equals("REMOVE")){
			//RemoveProcessor rp = new RemoveProcessor();
			//rp.process(clientJSON);
		}
		
		if(command.equals("SHARE")){
			ShareProcessor shareProcessor = new ShareProcessor();
			
			return shareProcessor.process(clientJSON);
		}
		 return new JSONObject();
		
	}
}
