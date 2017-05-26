package serverIO;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;

import bean.KeyUtil;
import serverControl.*;

public class Listener {

	public static boolean isSecure = Port.isSecure;

	@SuppressWarnings("resource")
	public void listening() throws Exception {
		int port = Port.port;
		int securePort = Port.SecurtPort;
		boolean isSecure = Port.isSecure;

		if (isSecure == true) {
//			// System.setProperty("javax.net.debug", "ssl,handshake");
//								
//			System.setProperty("javax.net.ssl.keyStore", this.getClass().getResource("/serverkeys.jks").getPath());
//			System.setProperty("javax.net.ssl.keyStorePassword", "123456");
//
//			System.setProperty("javax.net.ssl.trustStore", this.getClass().getResource("/SecureQuerySendertrust.jks").getPath());
//			System.setProperty("javax.net.ssl.keyStorePassword", "123456");
//			// System.setProperty("javax.net.ssl.trustStore",
//			// "././clientkey/clientkey.jks");
//			// System.setProperty("javax.net.ssl.keyStorePassword", "123456");
			InputStream keystoreInput = Thread.currentThread().getContextClassLoader()
				    .getResourceAsStream("serverkey/serverkeys.jks");
			InputStream truststoreInput = Thread.currentThread().getContextClassLoader()
				    .getResourceAsStream("serverkey/SecureQuerySendertrust.jks");
			KeyUtil.setSSLFactories(keystoreInput, "123456", truststoreInput);
			keystoreInput.close();
			truststoreInput.close();

			try {
				SSLServerSocketFactory sslserversocketfactory = (SSLServerSocketFactory) SSLServerSocketFactory
						.getDefault();
				SSLServerSocket sslserversocket = (SSLServerSocket) sslserversocketfactory
						.createServerSocket(securePort);

				while (true) {

					SSLSocket sslsocket = (SSLSocket) sslserversocket.accept();

					new Connection(sslsocket);
				}
			} catch (Exception exception) {
				exception.printStackTrace();
			}
		} 

		else {
			try {
				ServerSocket listenSocket = new ServerSocket(port);

				while (true) {

					Socket clientSocket = listenSocket.accept();

					new Connection(clientSocket);
				}

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
