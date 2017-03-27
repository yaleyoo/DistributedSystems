package serverControl;

import java.util.regex.Pattern;

public class Kits {
	
	
	 public static  boolean isNumeric(String str) {
		 try{
			 Long.parseLong(str);
		 }catch(Exception e){
			 return false;
		 }
		 return true;
	 }  
	 
	 public static boolean isInteger(String str){
		 try{
			 Integer.parseInt(str);
		 }catch(Exception e){
			 return false;
		 }
		 return true;
	 }
}
