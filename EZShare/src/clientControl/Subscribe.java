package clientControl;

import bean.ResourceTemplate;
import clientIO.Sender;
import net.sf.json.JSONObject;

public class Subscribe {
	public static String id = "";
	public static boolean is_subscribe = false;

	public void sendRequest(ResourceTemplate rt) {
		JSONObject jObject = new JSONObject();
		jObject.put("command", "SUBSCRIBE");
		jObject.put("relay", Relay.is_relay);
		jObject.put("id", Subscribe.id);
		jObject.put("resourceTemplate", rt);

		Sender sender = new Sender();
		sender.sendRequest(jObject);
	}
}
