package processor;
import bean.Resource;
import bean.ClientJSON;
import net.sf.json.JSONObject;
import server.Main;

public class RemoveProcessor {

	public JSONObject process(ClientJSON cJSON)
	{
		
		JSONObject removeStatus = new JSONObject();
		
		Resource resource = cJSON.getResource();
		
		for(int i = 0; i < Main.resourceList.size(); i++)
		{
			boolean found = false;
			
			if(resource == null)
			{
				removeStatus.put("response", "error");
				removeStatus.put("errorMessage",  "missing resource");
			}
			else if(resource.getOwner() == null || resource.getChannel() == null || resource.geturi() == null || resource.getDescription() == null || resource.getName() == null || resource.getezserver() == null || resource.getTags() == null)
			{
				removeStatus.put("response", "error");
				removeStatus.put("errorMessage",  "invalid resource");
			}
			
			else{
				if(Main.resourceList.get(i).getOwner().equals(resource.getOwner()) && Main.resourceList.get(i).getChannel().equals(resource.getChannel()) && Main.resourceList.get(i).geturi().equals(resource.geturi()))
				{
					found = true;
//					Resource resourceStored = Main.resourceList.get(i);
					Main.resourceList.remove(i);
					removeStatus.put("response", "success");
					System.out.println(removeStatus);
				}
				if(found == false)
				{
					removeStatus.put("response", "error");
					removeStatus.put("errorMessage", "cannot remove resource");
				}
				
			}
		}
		return removeStatus;
	}
}