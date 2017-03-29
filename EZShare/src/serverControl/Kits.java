package serverControl;


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
