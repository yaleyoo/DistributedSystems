package processor;
import java.util.List;
import bean.ClientJSON;
import bean.Resource;
import net.sf.json.JSONObject;
import server.Main;

public class RemoveProcessor {
	
	public JSONObject process(ClientJSON removeMatch){
		JSONObject jObject = new JSONObject();
		Resource resource = removeMatch.getResource();
		
		if(resource == null){
			jObject.put("responce", "error");
			jObject.put("errorMessage","missing resource");
			
			return jObject;
		}
		else{
		
			for(int i = 0; i < Main.resourceList.size(); i++){
				if(Main.resourceList.get(i).getOwner().equals(resource.getOwner()) 
						&& Main.resourceList.get(i).getChannel().equals(resource.getChannel())
							&& Main.resourceList.get(i).geturi().equals(resource.geturi())){
					Resource resourceStored = Main.resourceList.get(i);
					Main.resourceList.remove(i);
					jObject.put("responce", "success");
					return jObject;
				}
				else{
				}
			}
		}
		return jObject;
	}
}