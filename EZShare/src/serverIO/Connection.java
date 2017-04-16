package serverIO;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;

import bean.ClientJSON;
import net.sf.json.JSONObject;
import processor.Processor;
import serverControl.Debug;

public class Connection extends Thread {
	DataInputStream in;
	DataOutputStream out;
	Socket clientSocket;
	
	public Connection(Socket aClientSocket){
		try {
			clientSocket = aClientSocket;
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
			
			JSONObject dataJson = JSONObject.fromObject(data);
			if(Debug.isDebug)
				System.out.println("[EZShare.serverIO] - [FINE] - receive data:"+data);
			Processor processor = new Processor();
			processor.getClientJSON(dataJson);
			JSONObject jsonObject = processor.assignRequest();
			out.writeUTF(jsonObject.toString());
			
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
