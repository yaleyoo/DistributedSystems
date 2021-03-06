package server;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

import org.apache.commons.cli.*;

import bean.ClientJSON;
import bean.Resource;
import processor.SubscribeProcessor;
import serverControl.*;
import serverIO.ExchangeSender;
import serverIO.Listener;
import serverIO.SubscribeSender;

public class Main {
	//public  static List<Resource> resourceList;
	/*Using Vector to store resource*/
	public static Vector<Resource> resourceList;
	public static Vector<String> serverList;
	public static List<SubscribeProcessor> observerList;
	public static Vector<String> secureServerList;
	
	
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		resourceList = new Vector<Resource>();
		serverList = new Vector<String>();
		secureServerList = new Vector<String>();//secure
		observerList = new ArrayList<SubscribeProcessor>();
		
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss.SSS");
		System.out.println(sdf.format(new Date())+" - [EZShare.server] - [INFO] - Starting the EZShare Server");
		Options options = new Options();
		options(args,options);
		
		CommandLine commandLine = null;
	    CommandLineParser parser = new DefaultParser();
	    Port port = new Port();// initialize object port
	    
	    try {
	    	commandLine = parser.parse(options, args);
	    	
			if(commandLine.hasOption("h")){
				HelpFormatter hf = new HelpFormatter();
			    hf.setWidth(110);
			    hf.printHelp("Command Help", options, true);
			}
			
			if(commandLine.hasOption("a")){
				String value = commandLine.getOptionValue("a");
				if(value==null){// command without arg   ERR
					System.out.println(sdf.format(new Date())+" - [EZShare.server] - [Error] - Command 'advertisedhost' should have arg");
					System.out.println(sdf.format(new Date()) + " - [EZShare.server] - [INFO] - server shutdown");
					return;
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
					System.out.println(sdf.format(new Date()) + " - [EZShare.server] - [INFO] - server shutdown");
					return;
				}
				else{// command with arg 
					if(Kits.isNumeric(value)){// arg is number
						long intervalLimit = Long.parseLong(value);
						ConnectionIntervalLimit cIL = new ConnectionIntervalLimit();
						cIL.connectionIntervalLimit(intervalLimit);
					}
					else{//arg is not number
						System.out.println(sdf.format(new Date())+" - [EZShare.server] - [Error] - Command 'connectionintervallimit' should have numberic arg");
						System.out.println(sdf.format(new Date()) + " - [EZShare.server] - [INFO] - server shutdown");
						return;
					}
				}
			
			}
			
			if(commandLine.hasOption("e")){
				String value = commandLine.getOptionValue("e");
				if(value==null){// command without arg   ERR
					System.out.println(sdf.format(new Date())+" - [EZShare.server] - [Error] - Command 'exchangeinterval' should have arg");
					System.out.println(sdf.format(new Date()) + " - [EZShare.server] - [INFO] - server shutdown");
					return;
				}
				else{// command with arg 
					if(Kits.isNumeric(value)){// arg is number
						long intervalLimit = Long.parseLong(value);
						ExchangeInterval.exChangeIntervalLimit(intervalLimit);
					}
					else{//arg is not number
						System.out.println(sdf.format(new Date())+" - [EZShare.server] - [Error] - Command 'connectionintervallimit' should have numberic arg");
						System.out.println(sdf.format(new Date()) + " - [EZShare.server] - [INFO] - server shutdown");
						return;
					}
				}
			}
			
			if(commandLine.hasOption("d")){
				Debug debug = new Debug();
				debug.defineDebug(true);
			}
			
			if (commandLine.hasOption("p")) {
				String value = commandLine.getOptionValue("p");
				if (value == null) {// command without arg ERR
					System.out.println(
							sdf.format(new Date()) + " - [EZShare.server] - [Error] - Command 'port' should have arg");
					System.out.println(sdf.format(new Date()) + " - [EZShare.server] - [INFO] - server shutdown");
					return;
				} else {// command with arg
					if (Kits.isNumeric(value)) {// arg is number
						// Port p = new Port();
						int portInt = Integer.parseInt(value);
						port.bindtoPort(portInt);
					} else {// arg is not number
						System.out.println(sdf.format(new Date())
								+ " - [EZShare.server] - [Error] - Command 'port' should have integer arg");
						System.out.println(sdf.format(new Date()) + " - [EZShare.server] - [INFO] - server shutdown");
						return;
					}
				}
			}
			
			
			
			if(commandLine.hasOption("s")){

				String value = commandLine.getOptionValue("s");
				if(value==null){// command without arg   ERR
					System.out.println(sdf.format(new Date())+" - [EZShare.server] - [Error] - Command 'secret' should have arg");
					System.out.println(sdf.format(new Date()) + " - [EZShare.server] - [INFO] - server shutdown");
					return;
				}
				else{// command with arg 
					Secret secret = new Secret();
					secret.setSecret(value);
				}
			
			}
			
			// ------------------------------securePort----------------------------------------------//

			if (commandLine.hasOption("sport") && (commandLine.hasOption("p") || commandLine.hasOption("port"))) {
				System.out.println(sdf.format(new Date())
						+ " - [EZShare.server] - [Error] - securet port cannot start with other port together");
				System.out.println(sdf.format(new Date()) + " - [EZShare.server] - [INFO] - server shutdown");
				return;
			}
			if (commandLine.hasOption("sport")) {
				String value = commandLine.getOptionValue("sport");
				if (value == null) {// sport cannot with arg ERR
					System.out.println(
							sdf.format(new Date()) + " - [EZShare.server] - [Error] - Command 'sport' should have arg");
					System.out.println(sdf.format(new Date()) + " - [EZShare.server] - [INFO] - server shutdown");
					return;
				} else {
					if (Kits.isNumeric(value)) {// arg is number
						// Port p = new Port();
						int securePortInt = Integer.parseInt(value);
						port.bindtoSecurePort(securePortInt);
						port.setisSecure(true);
					} else {// arg is not number
						System.out.println(sdf.format(new Date())
								+ " - [EZShare.server] - [Error] - Command 'sport' should have integer arg");
						System.out.println(sdf.format(new Date()) + " - [EZShare.server] - [INFO] - server shutdown");
						return;
					}
				}
			}

			// ------------------------------securePort----------------------------------------------//
			
			///////starting without arg
			if(!commandLine.hasOption("s")){
				Secret secret = new Secret();
				secret.setSecret();
			}
			
			if(!commandLine.hasOption("a")){
				AdvertiseHost aHost = new AdvertiseHost();
				aHost.setAdvertiseHost();
			}
			
			if(!commandLine.hasOption("p")&&!commandLine.hasOption("sport")){
				//Port port = new Port();
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
		// ------------------------------securePort----------------------------------------------//
		if (port.getisSecure() == true) {
			System.out.println(sdf.format(new Date())
					+ " - [EZShare.server] - [INFO] - ******************Securce Connection Started******************");
		}
		// ------------------------------securePort----------------------------------------------//
		
		
		Timer timer = new Timer();
		/*
		 * exchange per 10 mins
		 * */
		timer.schedule(new TimerTask() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				if(Main.serverList.size()!=0){
					ExchangeSender eSender = new ExchangeSender();
					eSender.send();
				}
			}
		}, 0,600000);//10mins
		
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
	     
			// ------------------------------securePort----------------------------------------------//
			Option opt7 = new Option("sport", true, "start secure connection");
			opt.setRequired(false);
			options.addOption(opt7);
			// ------------------------------securePort----------------------------------------------//
	     
	}
	
	
	public static void addResource(Resource e) throws IOException {
		resourceList.add(e);
		notifyObservers(e);
	}
	
	
	
	public static void register(SubscribeProcessor sub){
		observerList.add(sub);
	}
	
	public static void unregister(SubscribeProcessor sub){
		observerList.remove(sub);
	}
	
	public static void notifyObservers(Resource e) throws IOException{
		for(SubscribeProcessor observer : observerList){
	           observer.update((Resource) e);
	       }
	}
	
	public static void addServerList(String server) {
		serverList.add(server);
		if(!observerList.isEmpty()) {
			for(SubscribeProcessor sub : observerList) {
				ClientJSON cJSON = sub.cJSON;
				if(cJSON.getRelay().equals("true")) {
					
					cJSON.setRelay("false");
					
					String[] s = server.split(":");
					String address = s[0];
					int port = Integer.valueOf(s[1]);
					new SubscribeSender(address,port,cJSON);
				}
			}
		}
	}
	
	public static void addSecureServerList(String server) {
		secureServerList.add(server);
		if(!observerList.isEmpty()) {
			for(SubscribeProcessor sub : observerList) {
				ClientJSON cJSON = sub.cJSON;
				if(cJSON.getRelay().equals("true")) {
					cJSON.setRelay("false");
					
					String[] s = server.split(":");
					String address = s[0];
					int port = Integer.valueOf(s[1]);
					new SubscribeSender(address,port,cJSON);
				}
			}
		}
	}

}
