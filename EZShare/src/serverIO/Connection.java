package serverIO;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import bean.ClientJSON;
import bean.Resource;
import net.sf.json.JSONObject;
import processor.FetchProcessor;
import processor.Processor;
import processor.SubscribeProcessor;
import server.Main;
import serverControl.Debug;

public class Connection extends Thread {
	DataInputStream in;
	DataOutputStream out;
	Socket clientSocket;
	Processor processor;
	
	public Connection(Socket aClientSocket){
		try {
			clientSocket = aClientSocket;
			processor = new Processor();
			in = new DataInputStream( clientSocket.getInputStream());
			out =new DataOutputStream( clientSocket.getOutputStream());
			this.start();
		} catch(IOException e) {
			System.out.println("Connection:"+e.getMessage());
		}
	}
	public void run(){
		try { // an echo server
			String data = in.readUTF(); // read a line of data from the stream
			if(Debug.isDebug){
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss.SSS");
				System.out.println(sdf.format(new Date())+" - [EZShare.serverIO] - [FINE] - RECEIVED:"+data);
				}
			JSONObject jsonObject = JSONObject.fromObject(data);
			/*
			 * exchange response
			 * */
			if(jsonObject.has("response")){
				throw new Exception();// receive response from other servers;
			}
			
			ClientJSON clientJSON = processor.getClientJSON(jsonObject);
			/*
			 * query reply
			 * */
			if(clientJSON.getCommand().equals("QUERY")){
				List<JSONObject> list = processor.assignQueryRequest();
				for(int i=0;i<list.size();i++){
					out.writeUTF(list.get(i).toString());
				}
				
			}
			/*
			 * Subscribe reply
			 * */
			else if(clientJSON.getCommand().equals("SUBSCRIBE")){
				SubscribeProcessor sub = new SubscribeProcessor();
				sub.is_subscribe = true;
				Main.register(sub);
				processor.assignSubscribeRequest(sub, out);
			}
			else if(clientJSON.getCommand().equals("UNSUBSCRIBE")){
				String id = clientJSON.getId();
				SubscribeProcessor sub = null;
				for(SubscribeProcessor tempSub:Main.observerList){
					if(tempSub.id.equals(id))
						sub = tempSub;
				}
				sub.is_subscribe = false;
				
			}
			/*
			 * fetch reply
			 * */
			else if(clientJSON.getCommand().equals("FETCH")){
				List<JSONObject> list = processor.assignFetchRequest();
				/*
				 * FETCH command with sending resource
				 * */
				if(list.get(0).has("type")){
					Resource resource = (Resource) JSONObject.toBean(list.get(0).getJSONObject("resource"), Resource.class);
					FetchProcessor.sendingResource(resource, out);
				}
				else{
					/*
					 * 
					 * FETCH command without sending resource
					 * */
					for(int i=0;i<list.size();i++){
						out.writeUTF(list.get(i).toString());
					}
				}
			}
			
			else{
				JSONObject jObject = processor.assignRequest();
				out.writeUTF(jObject.toString());
			}
			//out.writeUTF(data);
			
		}catch (EOFException e){
			System.out.println("EOF:"+e.getMessage());
		} catch(IOException e) {
			System.out.println("readline:"+e.getMessage());
		}catch(Exception e){
			
		}
		finally{
			try {
				clientSocket.close();
			}catch (IOException e){/*close failed*/}
			}
	}
	
}
