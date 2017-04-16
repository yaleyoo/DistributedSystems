package processor;

import java.util.List;

import bean.ClientJSON;
import bean.Resource;
import net.sf.json.JSONObject;
import server.Main;
import serverControl.Secret;

public class ShareProcessor {

	public static JSONObject process(ClientJSON cJSON){
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
			else if(resource.getURI().equals((""))) {
				response.put("response", "error");
				response.put("errorMessage", "cannot share resource");
				return response;
			}
			else if (cJSON.getSecrete().equals(Secret.secret)) {
				response.put("response", "error");
				response.put("errorMessage", "incorrect secret");
				return response;
			}
			
			for (Resource aResource : resourceList) {
				if(resource.getChannel().equals(aResource.getChannel()) && resource.getURI().equals(aResource.getURI())) {
					if(!resource.getOwner().equals(aResource.getOwner())) {
						response.put("response", "error");
						response.put("response", "invalid resource");
						return response;
					}
					else {
						aResource = resource;
						response.put("response", "success");
						return response;
					}
				}
			}
		}
		
		Main.resourceList.add(resource);
		response.put("response", "success");
		return response;
		
	}
}
