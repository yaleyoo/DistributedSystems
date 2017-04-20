package processor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import bean.ClientJSON;
import bean.Resource;
import bean.ResourceTemplate;
import net.sf.json.JSONObject;
import server.Main;

public class QueryProcessor {
	int match_num = 0;
	List<Resource> match_List = new ArrayList<Resource>();
	List<JSONObject> response_List = new ArrayList<JSONObject>();

	@SuppressWarnings("unchecked")
	public List<JSONObject> process(ClientJSON cJSON){
		JSONObject jObject = new JSONObject();
		ResourceTemplate rt = cJSON.getResourceTemplate();
		
		
		
		boolean is_channel = false;
		boolean is_description = false;
		boolean is_uri = false;
		boolean is_tag = false;//any
		boolean is_owner = false;
		boolean is_name = false;
		/*
		 * 
		 * resource template pre-process
		 * 
		 * */
		if(!rt.getChannel().equals(""))
			is_channel = true;
		if(!rt.getDescription().equals(""))
			is_description = true;
		if(!rt.geturi().equals(""))
			is_uri = true;
		if(rt.getTags().length!=0)
			is_tag = true;
		if(!rt.getOwner().equals(""))
			is_owner = true;
		if(!rt.getName().equals(""))
			is_name = true;
		/*
		 * 
		 * resource template pre-process done
		 * 
		 * */
		match_List = (List<Resource>) Main.resourceList.clone();
		
		if(is_channel){
			Iterator<Resource> iterator = match_List.iterator();
			while(iterator.hasNext()){
				Resource r = iterator.next();
				
				if(!rt.getChannel().equals(r.getChannel())){
					iterator.remove();
				}
			}
		}
		if(is_description){
			Iterator<Resource> iterator = match_List.iterator();
			while(iterator.hasNext()){
				Resource r = iterator.next();
				
				if(!r.getDescription().contains(rt.getDescription())){
					iterator.remove();
				}
			}
		}
		if(is_uri){
			Iterator<Resource> iterator = match_List.iterator();
			while(iterator.hasNext()){
				Resource r = iterator.next();
				
				if(!r.geturi().equals(rt.geturi())){
					iterator.remove();
				}
			}
		}
		if(is_owner){
			Iterator<Resource> iterator = match_List.iterator();
			while(iterator.hasNext()){
				Resource r = iterator.next();
				
				if(!r.getOwner().equals(rt.getOwner())){
					iterator.remove();
				}
			}
		}
		if(is_name){
			Iterator<Resource> iterator = match_List.iterator();
			while(iterator.hasNext()){
				Resource r = iterator.next();
				
				if(!r.getName().contains(rt.getName())){
					iterator.remove();
				}
			}
		}
		if(is_tag){
			Iterator<Resource> iterator = match_List.iterator();
			while(iterator.hasNext()){
				boolean if_Contain_All_Tags = false;
				Resource r = iterator.next();
				String[] listTags = r.getTags();
				String[] tempTags = rt.getTags();
				/*
				 * contain all tags?
				 * */
				List<String> listTagsList = Arrays.asList(listTags);
				for(String s : tempTags){
					if(listTagsList.contains(s)){
						if_Contain_All_Tags = true;
					}
					else
						if_Contain_All_Tags = false;
				}
				/*
				 * contain all tags done
				 * */
				
				if(!if_Contain_All_Tags){
					iterator.remove();
				}
			}
		}
		
		/*
		 * 
		 * genarate the List of Response
		 * 
		 * */
		
		match_num = match_List.size();
		jObject.put("response", "success");
		response_List.add(jObject);
		jObject = new JSONObject();//clear
		
		for(Resource r:match_List){
			jObject = JSONObject.fromObject(r);
			
			response_List.add(jObject);
		}
		
		jObject = new JSONObject();//clear
		jObject.put("resultSize", match_num);
		response_List.add(jObject);
	
		
		
		return response_List;

	}
}
