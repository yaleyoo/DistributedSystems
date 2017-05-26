package serverControl;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Port {
	public static int port = 3780;
	public static int SecurtPort = 3781;//secure
	public static boolean isSecure = false;//secure

	public void bindtoPort(int port) {
		Port.port = port;
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss.SSS");
		System.out.println(sdf.format(new Date()) + " - [EZShare.serverControl] - [INFO] - bound to port " + port);
	}

	public void bindtoPort() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss.SSS");
		System.out.println(sdf.format(new Date()) + " - [EZShare.serverControl] - [INFO] - bound to port " + port);
	}

	public void bindtoSecurePort() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss.SSS");
		System.out
				.println(sdf.format(new Date()) + " - [EZShare.serverControl] - [INFO] - bound to secure port " + port);
	}

	public void bindtoSecurePort(int securtPort) {
		Port.SecurtPort = securtPort;
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss.SSS");
		System.out
				.println(sdf.format(new Date()) + " - [EZShare.serverControl] - [INFO] - bound to secure port " + securtPort);
	}

	public int getPortNumber() {
		return port;
	}

	public int getSecurePortNumber() {
		return SecurtPort;
	}

	public boolean getisSecure() {
		return isSecure;
	}

	public void setisSecure(boolean isSecure) {
		Port.isSecure = isSecure;
	}

}
