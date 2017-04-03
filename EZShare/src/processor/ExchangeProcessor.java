package processor;

import bean.ClientJSON;
import bean.Resource;
import server.Main;

public class ExchangeProcessor {
	
	public void process(ClientJSON cJSON){
		System.out.println("exchangeprocessor");
		this.add();
	}
	
	public synchronized void add(){
		for(int i=0;i<1000000;i++){
			Main.resourceList.add(new Resource());
			System.out.println("aaaaaa");
		}
	}
}
