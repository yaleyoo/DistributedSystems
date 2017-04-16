package clientIO;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

import clientControl.Debug;
import net.sf.json.JSONObject;

public class Sender {
	public static InetAddress address=null;
	public static int port=0;
	int num_of_fetching_resource=0;
	
	public void sendRequest(JSONObject jObject){
		Socket s = null;
		try{
//			InetAddress address = InetAddress.getByName("127.0.0.1");
//			int port = 3780;
			//s = new Socket("127.0.0.1", 8888, InetAddress.getByName("localhost"), 8873);
			if(address==null){
				address = InetAddress.getByName("127.0.0.1");
			}
			if(port == 0){
				port = 3780;
			}
			s = new Socket(address,port);
			if(Debug.isDebug){
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss.SSS");
				System.out.println(sdf.format(new Date())+" - [EZShare.clientIO] - [INFO] - connection established");
			}
			DataInputStream input = new DataInputStream(s.getInputStream());
			DataOutputStream output = new DataOutputStream(s.getOutputStream());
			
			///transfer ClientJSON to JSONObject
//			JsonConfig config = new JsonConfig();  
//			config.setJsonPropertyFilter(new PropertyFilter()  
//			{  
//			    @Override  
//			    public boolean apply(Object source, String name, Object value)  
//			    {  
//			        return value == null;  
//			    }  
//			}); 
//			JSONObject jObject = JSONObject.fromObject(clientJSON,config);
			
			if(Debug.isDebug){
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss.SSS");
				System.out.println(sdf.format(new Date())+" - [EZShare.clientIO] - [INFO] - sending request:"+jObject.toString());
			}
			
			output.writeUTF(jObject.toString());
			
			
			//receive replys
			boolean isEndFlag = false;
			
			while(!isEndFlag){
					try{
						String reply = input.readUTF();
						if(Debug.isDebug){
							SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss.SSS");
							System.out.println(sdf.format(new Date())+" - [EZShare.clientIO] - [INFO] - RECEIVED:"+reply);
						}
						JSONObject fileJSON = JSONObject.fromObject(reply);
						if(fileJSON.has("resourceSize")){
							fetchResource(fileJSON, input);
						}
						
					}catch(EOFException e){
						isEndFlag = true;
						
						
					}
					catch(Exception e){
						e.printStackTrace();
					}
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(s!=null){
				try{
					s.close();
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		}
	}
	
	public void fetchResource(JSONObject fileJSON,DataInputStream input) throws IOException{
		num_of_fetching_resource++;
		if(Debug.isDebug){
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss.SSS");
			System.out.println(sdf.format(new Date())+" - [EZShare.clientIO] - [INFO] - Downloading resource");
		}
		String dir = fileJSON.getString("uri");
		String[] list = dir.split("/");
		//create file named as resource name&&&& save in current folder
		File file = new File("./"+list[list.length-1]);
		
		@SuppressWarnings("resource")
		FileOutputStream fileOut = new FileOutputStream(file);
		
		int len = fileJSON.getInt("resourceSize");
		byte[] buffer = new byte[len];
		int intent = 0;
		while(true){
			intent = input.read(buffer);  //intent saves resource chunk 
				if(intent!=-1){
					fileOut.write(buffer, 0, intent);
				}
				else {
					if(Debug.isDebug){
						SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss.SSS");
						System.out.println(sdf.format(new Date())+" - [EZShare.clientIO] - [INFO] - RECEIVED:{\"resultSize\":"+num_of_fetching_resource+"}");
					}
					break;
				}
		}
		
	}
}
