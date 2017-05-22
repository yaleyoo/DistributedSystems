package processor;

import java.io.IOException;
import java.util.List;

import bean.ClientJSON;
import bean.Resource;
import net.sf.json.JSONObject;
import server.Main;
import serverControl.Secret;

public class ShareProcessor {

	public  JSONObject process(ClientJSON cJSON) throws IOException{
		JSONObject response = new JSONObject();
		
		Resource resource = cJSON.getResource();
		
		List<Resource> resourceList = Main.resourceList;
		if (resource == null) {
			response.put("response", "error");
			response.put("errorMessage", "missing resource and/or secret");
			return response;
		} 
		else {
			if(resource.getOwner().equals("*")) {
				response.put("response", "error");
				response.put("errorMessage", "invalid resource");
				return response;
			}
			else if(resource.geturi().equals((""))) {
				response.put("response", "error");
				response.put("errorMessage", "cannot share resource");
				return response;
			}
			
			else if(cJSON.getSecret()==null){
				response.put("response", "error");
				response.put("errorMessage", "missing secret");
				return response;
			}
			
			else if (!cJSON.getSecret().equals(Secret.secret)) {
				response.put("response", "error");
				response.put("errorMessage", "incorrect secret");
				return response;
			}
			
			if(resource.geturi().startsWith("file:")){
				boolean is_exist_flag = false;
				for (Resource aResource : resourceList) {
					if(resource.getChannel().equals(aResource.getChannel()) && resource.geturi().equals(aResource.geturi())) {
						if(!resource.getOwner().equals(aResource.getOwner())) {
							is_exist_flag = true;
							response.put("response", "error");
							response.put("response", "invalid resource");
							return response;
						}
						else {
							is_exist_flag = true;
							aResource = resource;
							response.put("response", "success");
							return response;
						}
					}
				}
				
				if(!is_exist_flag){
					Main.addResource(resource);
					response.put("response", "success");
					return response;
				}
				else{
					return response;
				}
			}//end of file:
			else{
				response.put("response", "error");
				response.put("response", "invalid resource");
				return response;
			}
		}
		
		
		
	}
}