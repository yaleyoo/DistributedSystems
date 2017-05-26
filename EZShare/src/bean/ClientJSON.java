package bean;

public class ClientJSON {
	private String command;
	private Resource resource;
	private String secret;
	private String relay;
	private String id;
	private ResourceTemplate resourceTemplate;
	private String[] serverList;
	private String[] secureServerList;
	
	
	////getter&setter
	public String getCommand() {
		return command;
	}
	public void setCommand(String command) {
		this.command = command;
	}
	public Resource getResource() {
		return resource;
	}
	public void setResource(Resource resource) {
		this.resource = resource;
	}
	public String getSecret() {
		return secret;
	}
	public void setSecret(String secret) {
		this.secret = secret;
	}
	public String getRelay() {
		return relay;
	}
	public void setRelay(String relay) {
		this.relay = relay;
	}
	public ResourceTemplate getResourceTemplate() {
		return resourceTemplate;
	}
	public void setResourceTemplate(ResourceTemplate resourceTemplate) {
		this.resourceTemplate = resourceTemplate;
	}
	public String[] getServerList() {
		return serverList;
	}
	public void setServerList(String[] serverList) {
		this.serverList = serverList;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	// ------------------------------secureExchange----------------------------------------------//
	public String[] getSecureServerList() {
		return secureServerList;
	}

	public void setSecureServerList(String[] secureServerList) {
		this.secureServerList = secureServerList;
	}
	// ------------------------------secureExchange----------------------------------------------//
	
}
