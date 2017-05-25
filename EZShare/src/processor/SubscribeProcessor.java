package processor;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import bean.ClientJSON;
import bean.Resource;
import bean.ResourceTemplate;
import net.sf.json.JSONObject;
import server.Main;
import serverIO.SubscribeSender;

public class SubscribeProcessor {
	int match_num = 0;
	public static List<Resource> match_List = new ArrayList<Resource>();
	public boolean is_subscribe = false;
	public String id = "";
	private ResourceTemplate rt; 
	boolean is_channel = false;
	boolean is_description = false;
	boolean is_uri = false;
	boolean is_tag = false;//any
	boolean is_owner = false;
	boolean is_name = false;
	DataOutputStream out = null;
	public ClientJSON cJSON;
	
	public void process(ClientJSON cJSON, DataOutputStream out) throws IOException{
		this.cJSON = cJSON;
		JSONObject jObject = new JSONObject();
		rt = cJSON.getResourceTemplate();
		this.out = out;
		id = cJSON.getId();
		/*
		 * 
		 * resource template pre-process
		 * 
		 * */
		if(cJSON.getId().equals("")||cJSON.getId()==null){
			jObject.put("response", "error");
			jObject.put("errorMessage", "missing id");

			return;
		}
		
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
		match_List.addAll(Main.resourceList);
		
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
		jObject.put("response", "success");
		out.writeUTF(jObject.toString());
		jObject = new JSONObject();//clear
		

		jObject.put("id", id);
		out.writeUTF(jObject.toString());
		jObject = new JSONObject();//clear
		
		for(Resource r:match_List){
			jObject = JSONObject.fromObject(r);
			out.writeUTF(jObject.toString());
		}
		
		if(cJSON.getRelay().equals("true")){
			for(String server:Main.serverList){
				String[] s = server.split(":");
				String address = s[0];
				int port = Integer.valueOf(s[1]);
				
				new SubscribeSender(address,port,cJSON);
			}
		}
		
		
		while(is_subscribe){
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		cJSON = new ClientJSON();
		cJSON.setCommand("UNSUBSCRIBE");
		cJSON.setId(id);
		for(String server:Main.serverList){
			String[] s = server.split(":");
			String address = s[0];
			int port = Integer.valueOf(s[1]);
			
			new SubscribeSender(address,port,cJSON);
		}
		
		match_num = match_List.size();
		jObject = new JSONObject();//clear
		jObject.put("resultSize", match_num);
		out.writeUTF(jObject.toString());
		Main.observerList.remove(this);
		
	}
	
	public void update(Resource e) throws IOException{
		boolean is_match = true;
		if(is_channel){
			if(!e.getChannel().equals(rt.getChannel())){
				is_match = false;
			}
		}
		if(is_description){
			if(!e.getDescription().contains(rt.getDescription())){
				is_match = false;
			}
		}
		if(is_name){
			if(!e.getName().equals(rt.getName())){
				is_match = false;
			}
		}
		if(is_owner){
			if(!e.getOwner().equals(rt.getOwner())){
				is_match = false;
			}
		}
		if(is_tag){
			boolean if_Contain_All_Tags = false;
			String[] listTags = e.getTags();
			String[] tempTags = rt.getTags();
			List<String> listTagsList = Arrays.asList(listTags);
			for(String s : tempTags){
				if(listTagsList.contains(s)){
					if_Contain_All_Tags = true;
				}
				else
					if_Contain_All_Tags = false;
			}
			
			if(!if_Contain_All_Tags){
				is_match = false;
			}
		}
		if(is_uri){
			if(!e.geturi().equals(rt.geturi())){
				is_match = false;
			}
		}
		
		if(is_match){
			match_List.add(e);
			JSONObject jObject = JSONObject.fromObject(e);
			out.writeUTF(jObject.toString());
		}
	}
}
