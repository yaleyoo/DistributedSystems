package serverIO;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import server.Main;

public class ExchangeSender {
	Socket s = null;
	
	public  void send(){
		try {
			/*
			 * select a random server to exchange
			 * */
			int index = (int)(Math.random()*10)%Main.serverList.size();
			String destination = Main.serverList.get(index);
			String[] list = destination.split(":");
			InetAddress address = InetAddress.getByName(list[0]);
			int port = Integer.valueOf(list[1]);
			s = new Socket(address,port);
			DataOutputStream output = new DataOutputStream(s.getOutputStream());
			
			
			JSONArray value = new JSONArray();
			
			for (String serverList : Main.serverList) {
				String[] server = serverList.split(":");
				JSONObject temp = new JSONObject();
				temp.put("hostname", server[0]);
				temp.put("port", server[1]);
				value.add(temp);
			}
			
			JSONObject jObject = new JSONObject();
			jObject.put("command", "EXCHANGE");
			jObject.put("serverList", value);
			
			output.writeUTF(jObject.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
