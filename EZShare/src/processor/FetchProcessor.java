package processor;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import bean.ClientJSON;
import bean.Resource;
import bean.ResourceTemplate;
import net.sf.json.JSONObject;
import server.Main;

public class FetchProcessor {

	public List<JSONObject> process(ClientJSON cJSON){
		List<JSONObject> response_list = new ArrayList<JSONObject>();
		ResourceTemplate rTemplate = cJSON.getResourceTemplate();
		Resource fetching_Resource = new Resource();
		JSONObject jObject = new JSONObject();
		boolean is_channel = false;
		boolean is_uri = false;
		if(!rTemplate.getChannel().equals("")){
			is_channel = true;
		}
		if(!rTemplate.geturi().equals("")){
			is_uri = true;
		}
		
		/*
		 * missing uri or channel
		 * */
		if(!is_channel||!is_uri){
			jObject.put("response", "error");
			jObject.put("errorMessage", "invalid resourceTemplate");
			response_list.add(jObject);
			jObject = new JSONObject();//clear json
			
			return response_list;
		}
		else{
			boolean has_resource = false;
			for(Resource s:Main.resourceList){
				if(s.geturi().equals(rTemplate.geturi())&&s.getChannel().equals(rTemplate.getChannel())){
					fetching_Resource = s;
					has_resource = true;
				}
			}
			if(has_resource){/*if got the fetching resource*/
				jObject.put("type", "sending");
				jObject.put("resource", JSONObject.fromObject(fetching_Resource));
				response_list.add(jObject);

				return response_list;
			}
			else{//resource not found
				jObject.put("response", "success");
				response_list.add(jObject);
				jObject = new JSONObject();
				
				jObject.put("resultSize", 0);
				response_list.add(jObject);
				jObject = new JSONObject();
				
				return response_list;
			}
		}
	}
	

	public static void sendingResource( Resource resource, DataOutputStream out){
		InputStream input = null;
		JSONObject jObject = new JSONObject();
		String uri = resource.geturi();		
		int intent = 0;
		
		try {
			File file = new File(uri);
			
			jObject.put("response", "success");
			out.writeUTF(jObject.toString());
			
			jObject = JSONObject.fromObject(resource);
			jObject.put("resourceSize", file.length());
			out.writeUTF(jObject.toString());


			
			input = new FileInputStream(file);
			
			
			/*
			 *exact bytes 
			 * */
			while(true){
		
				intent = input.read();
				if(intent!=-1){
					out.write(intent);
				}
				else{
					break;
				}
			}
			
			
			
			jObject.put("resultSize", 1);
			out.writeUTF(jObject.toString());
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			try {
				input.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		
	}
}
