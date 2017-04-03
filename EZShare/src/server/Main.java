package server;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.cli.*;

import bean.Resource;
import serverControl.*;
import serverIO.Listener;

public class Main {
	public static List<Resource> resourceList;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		resourceList = new ArrayList<Resource>();
		String[] arg1 ={"-a","steve"};
		SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/yy hh:mm:ss");
		
		System.out.println(sdf.format(new Date())+" - [EZShare.server] - [INFO] - Starting the EZShare Server");
		Options options = new Options();
		//options(args,options);
		options(arg1,options);
		
		CommandLine commandLine = null;
	    CommandLineParser parser = new DefaultParser();
	    
	    try {
	    	//test
	    	commandLine = parser.parse(options, arg1);
	    	//commandLine = parser.parse(options, args);
	    	
			if(commandLine.hasOption("h")){
				HelpFormatter hf = new HelpFormatter();
			    hf.setWidth(110);
			    hf.printHelp("Command Help", options, true);
			}
			
			if(commandLine.hasOption("a")){
				String value = commandLine.getOptionValue("a");
				if(value==null){// command without arg   ERR
					System.out.println(sdf.format(new Date())+" - [EZShare.server] - [Error] - Command 'advertisedhost' should have arg");
				}
				else{// command with arg 
					AdvertiseHost aHost = new AdvertiseHost();
					aHost.setAdvertiseHost(value);
				}
			}
			
			if(commandLine.hasOption("c")){
				String value = commandLine.getOptionValue("c");
				if(value==null){// command without arg   ERR
					System.out.println(sdf.format(new Date())+" - [EZShare.server] - [Error] - Command 'connectionintervallimit' should have arg");
				}
				else{// command with arg 
					if(Kits.isNumeric(value)){// arg is number
						long intervalLimit = Long.parseLong(value);
						ConnectionIntervalLimit cIL = new ConnectionIntervalLimit();
						cIL.connectionIntervalLimit(intervalLimit);
					}
					else{//arg is not number
						System.out.println(sdf.format(new Date())+" - [EZShare.server] - [Error] - Command 'connectionintervallimit' should have numberic arg");
					}
				}
			
			}
			
			if(commandLine.hasOption("e")){
				String value = commandLine.getOptionValue("e");
				if(value==null){// command without arg   ERR
					System.out.println(sdf.format(new Date())+" - [EZShare.server] - [Error] - Command 'exchangeinterval' should have arg");
				}
				else{// command with arg 
					if(Kits.isNumeric(value)){// arg is number
						long intervalLimit = Long.parseLong(value);
						ExchangeInterval.exChangeIntervalLimit(intervalLimit);
					}
					else{//arg is not number
						System.out.println(sdf.format(new Date())+" - [EZShare.server] - [Error] - Command 'connectionintervallimit' should have numberic arg");
					}
				}
			}
			
			if(commandLine.hasOption("d")){
				Debug debug = new Debug();
				debug.defineDebug(true);
			}
			
			if(commandLine.hasOption("p")){
				String value = commandLine.getOptionValue("p");
				if(value==null){// command without arg   ERR
					System.out.println(sdf.format(new Date())+" - [EZShare.server] - [Error] - Command 'port' should have arg");
				}
				else{// command with arg 
					if(Kits.isNumeric(value)){// arg is number
						Port p = new Port();
						int port = Integer.parseInt(value);
						p.bindtoPort(port);
					}
					else{//arg is not number
						System.out.println(sdf.format(new Date())+" - [EZShare.server] - [Error] - Command 'port' should have integer arg");
					}
				}
			}
			
			if(commandLine.hasOption("s")){

				String value = commandLine.getOptionValue("s");
				if(value==null){// command without arg   ERR
					System.out.println(sdf.format(new Date())+" - [EZShare.server] - [Error] - Command 'secret' should have arg");
				}
				else{// command with arg 
					Secret secret = new Secret();
					secret.setSecret(value);
				}
			
			}
			
			///////starting without arg
			if(!commandLine.hasOption("s")){
				Secret secret = new Secret();
				secret.setSecret();
			}
			
			if(!commandLine.hasOption("a")){
				AdvertiseHost aHost = new AdvertiseHost();
				aHost.setAdvertiseHost();
			}
			
			if(!commandLine.hasOption("p")){
				Port port = new Port();
				port.bindtoPort();
			}
			
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	    
//	    //starting without arg
//	    Secret secret = new Secret();
//		secret.defineSecret();
//		
//		AdvertiseHost aHost = new AdvertiseHost();
//		aHost.defineAdvertiseHost();
//		
//		Port port = new Port();
//		port.bindtoPort();

		System.out.println(sdf.format(new Date())+" - [EZShare.server] - [INFO] - started");
		Listener listener = new Listener();
		listener.listening();
		
		
		
	}
	
	////generate options list
	public  static void options(String[]  args,Options options){
		 //options = new Options();
		 
	     Option opt = new Option("h", "help", false, "Print help");
	     opt.setRequired(false);
	     options.addOption(opt);
	     
	     Option opt1 = new Option("a","advertisedhost",true,"advertised hostname");
	     opt.setRequired(false);
	     options.addOption(opt1);
	     
	     Option opt2 = new Option("c","connectionintervallimit",true,"connection interval limits in seconds");
	     opt.setRequired(false);
	     options.addOption(opt2);
	     
	     Option opt3 = new Option("e","exchangeinterval",true,"exchange interval in seconds");
	     opt.setRequired(false);
	     options.addOption(opt3);
	     
	     Option opt4 = new Option("p","port",true,"server port, an integer");
	     opt.setRequired(false);
	     options.addOption(opt4);
	     
	     Option opt5 = new Option("s","secret",true,"secret");
	     opt.setRequired(false);
	     options.addOption(opt5);
	     
	     Option opt6 = new Option("d","debug",false,"print debug information");
	     opt.setRequired(false);
	     options.addOption(opt6);
	     
	     
	}

}
