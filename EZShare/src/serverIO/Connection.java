package serverIO;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

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
			if(Debug.isDebug){
				SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/yy hh:mm:ss");
				System.out.println(sdf.format(new Date())+" - [EZShare.serverIO] - [FINE] - receive data:"+data);
				}
			Processor processor = new Processor();
			processor.getClientJSON(JSONObject.fromObject(data));
			JSONObject jObject = processor.assignRequest();
			out.writeUTF(jObject.toString());
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
