package processor;

import bean.ClientJSON;
import net.sf.json.JSONObject;

public class FetchProcessor {

	public JSONObject process(ClientJSON cJSON){
		System.out.println("fetchprocessor");
		
		return new JSONObject();
	}
}
