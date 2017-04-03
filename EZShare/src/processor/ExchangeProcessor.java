package processor;

import bean.ClientJSON;
import bean.Resource;
import net.sf.json.JSONObject;
import server.Main;

public class ExchangeProcessor {
	
	public JSONObject process(ClientJSON cJSON){
		System.out.println("exchangeprocessor");
		
		return new JSONObject();
		
	}
	/*
	public synchronized void add(){
		for(int i=0;i<1000000;i++){
			Main.resourceList.add(new Resource());
			System.out.println("aaaaaa");
		}
	}*/
}
