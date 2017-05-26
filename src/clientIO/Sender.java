package clientIO;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

import bean.KeyUtil;
import clientControl.Debug;
import clientControl.Subscribe;
import net.sf.json.JSONObject;

public class Sender {
	public static InetAddress address = null;
	public static int port = 0;
	public static int SecurtPort = 0;
	public static boolean isSecure = false;
	int num_of_fetching_resource = 0;
	boolean redundant = false;

	public void sendRequest(JSONObject jObject) {
		Socket s = null;
		// ------------------------------secure----------------------------------------------//
		SSLSocket sslsocket = null;
		if (isSecure == true) {

			// System.setProperty("javax.net.debug", "ssl,handshake");
//			System.setProperty("javax.net.ssl.keyStore", "././Key/clientkey/clientkeys.jks");
//			System.setProperty("javax.net.ssl.keyStorePassword", "123456");
//
//			System.setProperty("javax.net.ssl.trustStore", "././Key/clientkey/clienttrust.jks");
//			System.setProperty("javax.net.ssl.keyStorePassword", "123456");
			InputStream keystoreInput = Thread.currentThread().getContextClassLoader()
				    .getResourceAsStream("clientkey/clientkeys.jks");
			InputStream truststoreInput = Thread.currentThread().getContextClassLoader()
				    .getResourceAsStream("clientkey/clienttrust.jks");
			try {
				KeyUtil.setSSLFactories(keystoreInput, "123456", truststoreInput);
				keystoreInput.close();
				truststoreInput.close();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				if (address == null) {
					address = InetAddress.getByName("127.0.0.1");
				}

				SSLSocketFactory sslsocketfactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
				sslsocket = (SSLSocket) sslsocketfactory.createSocket(address, SecurtPort);

				if (Debug.isDebug&&!redundant) {
					SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss.SSS");
					System.out.println(
							sdf.format(new Date()) + " - [EZShare.clientIO] - [INFO] - secure connection established");
					System.out.println(
							sdf.format(new Date()) + " - [EZShare.clientIO] - [INFO] - *****************************");
				}

				if (Debug.isDebug) {
					SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss.SSS");
					System.out.println(sdf.format(new Date()) + " - [EZShare.clientIO] - [INFO] - sending request:"
							+ jObject.toString());
				}

				DataInputStream input = new DataInputStream(sslsocket.getInputStream());
				DataOutputStream output = new DataOutputStream(sslsocket.getOutputStream());

				output.writeUTF(jObject.toString());

				// receive replys
				boolean isEndFlag = false;

				if (!Subscribe.is_subscribe) {
					while (!isEndFlag) {
						try {
							String reply = input.readUTF();

							if (Debug.isDebug) {
								SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss.SSS");
								System.out.println(
										sdf.format(new Date()) + " - [EZShare.clientIO] - [INFO] - RECEIVED:" + reply);
							}
							JSONObject fileJSON = JSONObject.fromObject(reply);
							if (fileJSON.has("resourceSize")) {
								fetchResource(fileJSON, input);
							}

						} catch (EOFException e) {
							isEndFlag = true;
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				} else {// Subscribing
					Thread t = new Thread() {
						@SuppressWarnings("resource")
						public void run() {
							Scanner scanner = new Scanner(System.in);
							boolean flag = true;
							while (flag) {
								String s = scanner.nextLine();
								if (s.equals("")) {
									Subscribe.is_subscribe = false;
									JSONObject jObject = new JSONObject();
									jObject.put("command", "UNSUBSCRIBE");
									jObject.put("id", Subscribe.id);
									redundant = true;
									sendRequest(jObject);

									flag = false;
								}
							}
						}
					};
					t.start();

					while (Subscribe.is_subscribe) {
						try {
							String reply = input.readUTF();

							if (Debug.isDebug) {
								SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss.SSS");
								System.out.println(
										sdf.format(new Date()) + " - [EZShare.clientIO] - [INFO] - RECEIVED:" + reply);
							}

						} catch(SSLException e){
							
						} catch (EOFException e) {

						} catch (Exception e) {
							e.printStackTrace();
						}

					}
				}
			} 
			catch (SocketException e){
				System.out.println("sss");
			}catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (s != null) {
					try {
						s.close();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}

		}

		// ------------------------------secure----------------------------------------------//
		else {
			try {
				// InetAddress address = InetAddress.getByName("127.0.0.1");
				// int port = 3780;
				// s = new Socket("127.0.0.1", 8888,
				// InetAddress.getByName("localhost"), 8873);
				if (address == null) {
					address = InetAddress.getByName("127.0.0.1");
				}
				if (port == 0) {
					port = 3780;
				}
				s = new Socket(address, port);

				if (Debug.isDebug&&!redundant) {
					SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss.SSS");
					System.out.println(
							sdf.format(new Date()) + " - [EZShare.clientIO] - [INFO] - connection established");
				}
				DataInputStream input = new DataInputStream(s.getInputStream());
				DataOutputStream output = new DataOutputStream(s.getOutputStream());

				if (Debug.isDebug) {
					SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss.SSS");
					System.out.println(sdf.format(new Date()) + " - [EZShare.clientIO] - [INFO] - sending request:"
							+ jObject.toString());
				}

				output.writeUTF(jObject.toString());

				// receive replys
				boolean isEndFlag = false;

				if (!Subscribe.is_subscribe) {
					while (!isEndFlag) {
						try {
							String reply = input.readUTF();

							if (Debug.isDebug) {
								SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss.SSS");
								System.out.println(
										sdf.format(new Date()) + " - [EZShare.clientIO] - [INFO] - RECEIVED:" + reply);
							}
							JSONObject fileJSON = JSONObject.fromObject(reply);
							if (fileJSON.has("resourceSize")) {
								fetchResource(fileJSON, input);
							}

						} catch (EOFException e) {
							isEndFlag = true;
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				} else {// Subscribing
					Thread t = new Thread() {
						@SuppressWarnings("resource")
						public void run() {
							Scanner scanner = new Scanner(System.in);
							boolean flag = true;
							while (flag) {
								String s = scanner.nextLine();
								if (s.equals("")) {
									Subscribe.is_subscribe = false;
									JSONObject jObject = new JSONObject();
									jObject.put("command", "UNSUBSCRIBE");
									jObject.put("id", Subscribe.id);
									redundant = true;
									sendRequest(jObject);

									flag = false;
								}
							}
						}
					};
					t.start();

					while (Subscribe.is_subscribe) {
						try {
							String reply = input.readUTF();

							if (Debug.isDebug) {
								SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss.SSS");
								System.out.println(
										sdf.format(new Date()) + " - [EZShare.clientIO] - [INFO] - RECEIVED:" + reply);
							}

						} catch(SSLException e){
							
						} catch (EOFException e) {

						} catch (Exception e) {
							e.printStackTrace();
						}

					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (s != null) {
					try {
						s.close();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	public void fetchResource(JSONObject fileJSON, DataInputStream input) throws IOException {
		num_of_fetching_resource++;
		if (Debug.isDebug) {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss.SSS");
			System.out.println(sdf.format(new Date()) + " - [EZShare.clientIO] - [INFO] - Downloading resource");
		}
		String dir = fileJSON.getString("uri");
		String[] list = dir.split("/");
		// create file named as resource name&&&& save in current folder
		File file = new File("./" + list[list.length - 1]);

		@SuppressWarnings("resource")
		FileOutputStream fileOut = new FileOutputStream(file);

		int len = fileJSON.getInt("resourceSize");
		byte[] buffer = new byte[len];
		int intent = 0;
		while (true) {
			intent = input.read(buffer); // intent saves resource chunk
			if (intent != -1) {
				fileOut.write(buffer, 0, intent);
				fileOut.flush();
			} else {
				if (Debug.isDebug) {
					SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss.SSS");
					System.out.println(
							sdf.format(new Date()) + " - [EZShare.clientIO] - [INFO] - RECEIVED:{\"resultSize\":"
									+ num_of_fetching_resource + "}");
				}
				break;
			}
		}

	}

	// ------------------------------secure----------------------------------------------//

	public void setisSecure(boolean isSecure) {
		Sender.isSecure = isSecure;
	}

	public boolean getisSecure() {
		return isSecure;
	}

	// ------------------------------secure----------------------------------------------//
}
