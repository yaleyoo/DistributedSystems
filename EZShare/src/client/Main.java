package client;
import java.net.InetAddress;
import java.net.UnknownHostException;

import org.apache.commons.cli.*;

import bean.*;
import clientControl.*;
import clientIO.Sender;

public class Main {

	static ResourceTemplate resource = null;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
//		String[] arg1 = {"-remove","-name","aaa","-debug"};
		resource = new ResourceTemplate();
		Options options = new Options();
		options(args,options);
//		options(arg1,options);
		CommandLine commandLine = null;
	    CommandLineParser parser = new DefaultParser();
	    
	    try {
	    	commandLine = parser.parse(options, args);
//			commandLine = parser.parse(options, arg1);
			
			if(commandLine.hasOption("help")){
				HelpFormatter hf = new HelpFormatter();
			    hf.setWidth(110);
			    hf.printHelp("Command Help", options, true);
			}
			
			if(commandLine.hasOption("debug")){
				//stub
				Debug.isDebug = true;
			}
			
			if(commandLine.hasOption("channel")){
				//stub
				String value = commandLine.getOptionValue("channel");
				resource.setChannel(value);
			}
			
			if(commandLine.hasOption("description")){
				//stub
				String value = commandLine.getOptionValue("description");
				resource.setDescription(value);
			}
			
			if(commandLine.hasOption("host")){
				//stub
				String value = commandLine.getOptionValue("host");
				try {
					InetAddress address = InetAddress.getByName(value);
					Sender.address = address;
				} catch (UnknownHostException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			
			if(commandLine.hasOption("name")){
				//stub
				String value = commandLine.getOptionValue("name");
				resource.setName(value);
			}
			
			if(commandLine.hasOption("tags")){
				//stub
				String value = commandLine.getOptionValue("tags");
				resource.setChannel(value);
			}
			
			if(commandLine.hasOption("uri")){
				//stub
				String value = commandLine.getOptionValue("uri");
				resource.seturi(value);
			}
			
			if(commandLine.hasOption("ezServer")){
				//stub
				String value = commandLine.getOptionValue("ezServer");
				resource.setezserver(value);
			}
			
			if(commandLine.hasOption("owner")){
				//stub
				String value = commandLine.getOptionValue("owner");
				resource.setOwner(value);
			}
			
			if(commandLine.hasOption("port")){
				//stub
				String value = commandLine.getOptionValue("port");
				Sender.port = Integer.valueOf(value);
			}

			if(commandLine.hasOption("secret")){
				//stub
				String value = commandLine.getOptionValue("port");
				Secret.secret = value;
			}

			if(commandLine.hasOption("servers")){
				//stub
			}
			////////functional commands

			if(commandLine.hasOption("exchange")){
				//stub
				String value = commandLine.getOptionValue("servers");
				String[] serverLists = value.split(",");
				
				Exchange exchange = new Exchange();
				exchange.setCommand("EXCHANGE");
				exchange.setServerList(serverLists);
				
				exchange.sendRequest();
			}
			
			if(commandLine.hasOption("fetch")){
				//stub
				ResourceTemplate rt = resource;
				
				Fetch fetch = new Fetch();
				fetch.sendRequest(rt);
			}
			
			if(commandLine.hasOption("publish")){
				//stub
				Publish publish = new Publish();
				
				publish.setCommand("PUBLISH");
				publish.setResource(resource);
				publish.sendRequest();
			}
			
			if(commandLine.hasOption("query")){
				//stub
				Query query = new Query();
				query.sendRequest(resource);
				
			}
			
			if(commandLine.hasOption("remove")){
				//stub
				Remove remove = new Remove();			
				remove.sendRequest(resource);
			}
			
			
			if(commandLine.hasOption("share")){
				//stub
				Share share = new Share();
				share.sendRequest(Secret.secret, resource);
				
			}
			
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
////generate options list
	public  static void options(String[]  args,Options options){
		 //options = new Options();
		 
	     Option opt = new Option("help", false, "Print help");
	     opt.setRequired(false);
	     options.addOption(opt);
	     
	     Option opt1 = new Option("channel",true,"channel");
	     opt.setRequired(false);
	     options.addOption(opt1);
	     
	     Option opt2 = new Option("description",true,"resource description");
	     opt.setRequired(false);
	     options.addOption(opt2);
	     
	     Option opt3 = new Option("exchange",false,"exchange server list with server");
	     opt.setRequired(false);
	     options.addOption(opt3);
	     
	     Option opt4 = new Option("port",true,"server port, an integer");
	     opt.setRequired(false);
	     options.addOption(opt4);
	     
	     Option opt5 = new Option("secret",true,"secret");
	     opt.setRequired(false);
	     options.addOption(opt5);
	     
	     Option opt6 = new Option("debug",false,"print debug information");
	     opt.setRequired(false);
	     options.addOption(opt6);
	     
	     Option opt7 = new Option("fetch",false,"fetch resource from server");
	     opt.setRequired(false);
	     options.addOption(opt7);
	     
	     Option opt8 = new Option("host",true,"server host, a domain name or IP address");
	     opt.setRequired(false);
	     options.addOption(opt8);
	     
	     Option opt9 = new Option("name",true,"resource name");
	     opt.setRequired(false);
	     options.addOption(opt9);
	     
	     Option opt10 = new Option("owner",true,"owner");
	     opt.setRequired(false);
	     options.addOption(opt10);
	     
	     Option opt11 = new Option("publish",false,"publish resource on server");
	     opt.setRequired(false);
	     options.addOption(opt11);
	     
	     Option opt12 = new Option("query",false,"query for resources from server");
	     opt.setRequired(false);
	     options.addOption(opt12);
	     
	     Option opt13 = new Option("remove",false,"remove resource from server");
	     opt.setRequired(false);
	     options.addOption(opt13);
	     
	     Option opt14 = new Option("servers",true,"server list, host1:port1, host2:post2, ...");
	     opt.setRequired(false);
	     options.addOption(opt14);
	     
	     Option opt15 = new Option("share",false,"share resource on server");
	     opt.setRequired(false);
	     options.addOption(opt15);
	     
	     Option opt16 = new Option("tags",true,"resource tag1, tag2, tag3, ..");
	     opt.setRequired(false);
	     options.addOption(opt16);
	     
	     Option opt17 = new Option("uri",true,"resource URI");
	     opt.setRequired(false);
	     options.addOption(opt17);
	     
	     Option opt18 = new Option("ezServer",true,"resource server");
	     opt.setRequired(false);
	     options.addOption(opt18);
	}

}
