package serverIO;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import serverControl.*;

public class Listener {
	
	@SuppressWarnings("resource")
	public void listening(){
		int port = Port.port;
		
		try {
			ServerSocket listenSocket = new ServerSocket(port);
			
			
			while(true){
				Socket clientSocket = listenSocket.accept();
				
				
				new Connection(clientSocket);
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
