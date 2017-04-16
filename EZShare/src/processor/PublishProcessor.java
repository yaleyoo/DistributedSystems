package processor;

import bean.ClientJSON;
import bean.Resource;
import net.sf.json.JSONObject;
import server.Main;

public class PublishProcessor {

	public JSONObject process(ClientJSON cJSON){
		JSONObject jObject = new JSONObject();
		Resource resource = cJSON.getResource();
		
		if(resource==null){
			jObject.put("response", "error");
			jObject.put("erroMessage", "missing resource");
			return jObject;
		}
		
		else{
			if(resource.geturi().equals("")){
				jObject.put("response", "error");
				jObject.put("erroMessage", "cannot publish resource");
				return jObject;
			}
			if(resource.getOwner().equals("*")){
				jObject.put("response", "error");
				jObject.put("erroMessage", "invalid resource");
				return jObject;
			}
			
			int publish_flag = 0;//0---nothing has same uri&channel 1---has same uri&channel&owner 2---has same uri&channel,different owner
			for(Resource r:Main.resourceList){
				if(r.geturi().equals(resource.geturi())&&r.getChannel().equals(resource.getChannel())){
					if(r.getOwner().equals(resource.getOwner())){
						r = resource;
						publish_flag = 1;
					}
					else{
						publish_flag = 2;
					}
				}
			}
			if(publish_flag==1){
				jObject.put("response", "success");
				return jObject;
			}
			if(publish_flag==2){
				jObject.put("response", "error");
				jObject.put("erroMessage", "invalid resource");
				return jObject;
			}
			if(publish_flag==0){
				Main.resourceList.add(resource);
				jObject.put("response", "success");
				return jObject;
			}
			
		}
		return jObject;

	}
}
