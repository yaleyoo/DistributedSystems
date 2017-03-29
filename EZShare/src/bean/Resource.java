package bean;



public class Resource{
	private String Name;
	private String Description;
	private String[] Tags;
	private String URI;
	private String Channel;
	private String Owner;
	private String EZserver;
	
	////////////getter&setter
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public String getDescription() {
		return Description;
	}
	public void setDescription(String description) {
		Description = description;
	}
	public String[] getTags() {
		return Tags;
	}
	public void setTags(String[] tags) {
		Tags = tags;
	}
	public String getURI() {
		return URI;
	}
	public void setURI(String uRI) {
		URI = uRI;
	}
	public String getChannel() {
		return Channel;
	}
	public void setChannel(String channel) {
		Channel = channel;
	}
	public String getOwner() {
		return Owner;
	}
	public void setOwner(String owner) {
		Owner = owner;
	}
	public String getEZserver() {
		return EZserver;
	}
	public void setEZserver(String eZserver) {
		EZserver = eZserver;
	}
	
	
}
