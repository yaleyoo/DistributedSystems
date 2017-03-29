package bean;

public class PublishJSON extends ClientJSON{
	private String command = "publish";
	private Resource resource;
	
	
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
	
	
}
