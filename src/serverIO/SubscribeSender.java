package serverIO;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

import bean.ClientJSON;
import bean.KeyUtil;
import bean.ResourceTemplate;
import net.sf.json.JSONObject;
import server.Main;
import serverControl.Port;

public class SubscribeSender extends Thread {
	boolean is_End = false;
	DataInputStream input;
	DataOutputStream output;
	String add;
	int port;
	JSONObject jObject;
	boolean isSecure = Port.isSecure;

	public SubscribeSender(String add, int port, ClientJSON cJSON) {
		this.add = add;
		this.port = port;

		cJSON.setRelay("false");
		jObject = JSONObject.fromObject(cJSON);

		this.start();
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub

		Socket socket = null;
		if (isSecure == true) {
			try {
//				System.setProperty("javax.net.ssl.trustStore", "././serverkey/SecureQuerySendertrust.jks");
//				System.setProperty("javax.net.ssl.keyStorePassword", "123456");
				InputStream keystoreInput = Thread.currentThread().getContextClassLoader()
					    .getResourceAsStream("serverkey/SecureQuerySenderkeys.jks");
				InputStream truststoreInput = Thread.currentThread().getContextClassLoader()
					    .getResourceAsStream("serverkey/SecureQuerySendertrust.jks");
				try {
					KeyUtil.setSSLFactories(keystoreInput, "123456", truststoreInput);

					keystoreInput.close();
					truststoreInput.close();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				SSLSocket sslsocket = null;
				SSLSocketFactory sslsocketfactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
				sslsocket = (SSLSocket) sslsocketfactory.createSocket(add, port);

				DataInputStream input = new DataInputStream(sslsocket.getInputStream());
				DataOutputStream output = new DataOutputStream(sslsocket.getOutputStream());


				output.writeUTF(jObject.toString());

				boolean isEndFlag = false;

				while (!isEndFlag) {
					try {
						String reply = input.readUTF();
						SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss.SSS");
						System.out.println(
								sdf.format(new Date()) + " - [EZShare.SubscribeSender] - [INFO] - RECEIVED:" + reply);

						JSONObject json = JSONObject.fromObject(reply);
						if (json.has("owner") && json.has("channel") && json.has("uri")) {
							ResourceTemplate r = (ResourceTemplate) JSONObject.toBean(json, ResourceTemplate.class);
							Main.notifyObservers(r);
						}
						//
					} 
					catch(SSLException e){
						
					}
					catch (EOFException e) {
						isEndFlag = true;
					} catch (Exception e) {
						e.printStackTrace();
					}
				}

			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				if (socket != null) {
					try {
						socket.close();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		} else {// unsecure
			try {
				socket = new Socket(add, port);

				input = new DataInputStream(socket.getInputStream());
				output = new DataOutputStream(socket.getOutputStream());

				output.writeUTF(jObject.toString());

				boolean isEndFlag = false;

				while (!isEndFlag) {
					try {
						String reply = input.readUTF();
						SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss.SSS");
						System.out.println(
								sdf.format(new Date()) + " - [EZShare.SubscribeSender] - [INFO] - RECEIVED:" + reply);

						JSONObject json = JSONObject.fromObject(reply);
						if (json.has("owner") && json.has("channel") && json.has("uri")) {
							ResourceTemplate r = (ResourceTemplate) JSONObject.toBean(json, ResourceTemplate.class);
							Main.notifyObservers(r);
						}
						//
					} catch(SSLException e){
						
					} catch (EOFException e) {
						isEndFlag = true;
					} catch (Exception e) {
						e.printStackTrace();
					}
				}

			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				if (socket != null) {
					try {
						socket.close();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}

	}
}
