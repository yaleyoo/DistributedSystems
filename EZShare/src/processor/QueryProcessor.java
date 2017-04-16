package processor;

import bean.ClientJSON;
import bean.Resource;
import bean.ResourceTemplate;
import net.sf.json.JSONObject;
import server.Main;

public class QueryProcessor {

	public JSONObject process(ClientJSON cJSON){
		JSONObject jObject = new JSONObject();
		System.out.println(cJSON);
		ResourceTemplate rt = cJSON.getResourceTemplate();
		
		for(Resource r:Main.resourceList){
			
		}
		
		return jObject;

	}
}
