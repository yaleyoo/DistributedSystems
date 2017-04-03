package clientIO;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

import clientControl.Debug;
import net.sf.json.JSONObject;

public class Sender {
	public static InetAddress address=null;
	public static int port=0;
	
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
				SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/yy hh:mm:ss");
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
				SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/yy hh:mm:ss");
				System.out.println(sdf.format(new Date())+" - [EZShare.clientControl] - [INFO] - sending request:"+jObject.toString());
			}
			output.writeUTF(jObject.toString());
			String reply = input.readUTF();
			if(Debug.isDebug)
				System.out.println("received:"+reply);
			
			
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
}
