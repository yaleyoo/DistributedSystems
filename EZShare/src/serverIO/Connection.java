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
import net.sf.json.JSONObject;
import processor.Processor;
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
			ClientJSON clientJSON = processor.getClientJSON(JSONObject.fromObject(data));
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
			 * fetch reply
			 * */
			else if(clientJSON.getCommand().equals("FETCH")){
				
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
		} finally{
			try {
				clientSocket.close();
			}catch (IOException e){/*close failed*/}
			}
	}
}
