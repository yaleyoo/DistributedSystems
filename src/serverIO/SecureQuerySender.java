package serverIO;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

import bean.ClientJSON;
import bean.KeyUtil;
import bean.Resource;
import bean.ResourceTemplate;
import net.sf.json.JSONObject;

public class SecureQuerySender implements Callable<Object> {
	boolean is_End = false;
	DataInputStream input;
	DataOutputStream output;
	String add;
	int port;
	JSONObject jObject;

	public SecureQuerySender(String add, int port, ClientJSON cJSON) {
		this.add = add;
		this.port = port;

		cJSON.setRelay("false");
		jObject = JSONObject.fromObject(cJSON);

	}

	@Override
	public List<Resource> call() throws Exception {
		// TODO Auto-generated method stub
		// System.setProperty("javax.net.debug", "ssl,handshake");
//		System.setProperty("javax.net.ssl.trustStore", "././Key/serverkey/SecureQuerySendertrust.jks");
//		System.setProperty("javax.net.ssl.keyStorePassword", "123456");
		InputStream keystoreInput = Thread.currentThread().getContextClassLoader()
			    .getResourceAsStream("serverkey/SecureQuerySenderkeys.jks");
		InputStream truststoreInput = Thread.currentThread().getContextClassLoader()
			    .getResourceAsStream("serverkey/SecureQuerySendertrust.jks");
		KeyUtil.setSSLFactories(keystoreInput, "123456", truststoreInput);
		keystoreInput.close();
		truststoreInput.close();
 
		// Socket socket = null;
		SSLSocket sslsocket = null;
		List<Resource> list = new ArrayList<Resource>();
		try {

			SSLSocketFactory sslsocketfactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
			sslsocket = (SSLSocket) sslsocketfactory.createSocket(add, port);

			// socket = new Socket(add, port);

			DataInputStream input = new DataInputStream(sslsocket.getInputStream());
			DataOutputStream output = new DataOutputStream(sslsocket.getOutputStream());

			output.writeUTF(jObject.toString());

			boolean isEndFlag = false;
			while (!isEndFlag) {
				try {
					String reply = input.readUTF();
					SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss.SSS");
					System.out
							.println(sdf.format(new Date()) + " - [EZShare.QuerySender] - [INFO] - RECEIVED:" + reply);

					JSONObject json = JSONObject.fromObject(reply);
					if (json.has("owner") && json.has("channel") && json.has("uri")) {
						ResourceTemplate r = (ResourceTemplate) JSONObject.toBean(json, ResourceTemplate.class);
						list.add(r);
					}
					//
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
			if (sslsocket != null) {
				try {
					sslsocket.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return list;

	}
}
