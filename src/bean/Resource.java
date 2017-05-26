package bean;



public class Resource {
	private String name;
	private String description;
	private String[] tags;
	private String uri;
	private String channel;
	private String owner;
	private String ezserver;
	
	////////////getter&setter
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String[] getTags() {
		return tags;
	}
	public void setTags(String[] tags) {
		this.tags = tags;
	}
	public String geturi() {
		return uri;
	}
	public void seturi(String uRI) {
		this.uri = uRI;
	}
	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	public String getezserver() {
		return ezserver;
	}
	public void setezserver(String ezserver) {
		this.ezserver = ezserver;
	}
	
	
}
