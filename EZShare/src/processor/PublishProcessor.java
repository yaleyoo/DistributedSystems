package processor;

import java.util.List;

import bean.ClientJSON;
import bean.Resource;
import net.sf.json.JSONObject;
import server.Main;
import serverControl.AdvertiseHost;

public class PublishProcessor {

	public JSONObject process(ClientJSON cJSON){
		JSONObject response = new JSONObject();
		
		Resource resource = cJSON.getResource();
		
		List<Resource> resourceList = Main.resourceList;
		if (resource == null) {
			response.put("response", "error");
			response.put("errorMessage", "missing resource");
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
				response.put("errorMessage", "cannot publish resource");
				return response;
			}
			
			int index = 0;
			for (Resource aResource : resourceList) {
				if(resource.getChannel().equals(aResource.getChannel()) && resource.geturi().equals(aResource.geturi())) {
					if(!resource.getOwner().equals(aResource.getOwner())) {
						response.put("response", "error");
						response.put("response", "invalid resource");
						return response;
					}
					else {
						resource.setezserver(AdvertiseHost.advertiseHost);
						resourceList.set(index, resource);
//						aResource = resource;
						response.put("response", "success");
						
						return response;
					}
				}
				index++;
			}
		}
		
		resource.setezserver(AdvertiseHost.advertiseHost);
		Main.resourceList.add(resource);
		response.put("response", "success");
	
		return response;
		
	}
}