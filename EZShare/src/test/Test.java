package test;

import bean.Resource;
import net.sf.json.JSONObject;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Resource re = new Resource();
		
		re.seturi("http://www.jkjkl.com.au");
		
		String s = re.geturi();
		System.out.println(s);
		s= s.replace("/", "\\/");
		System.out.println(s);
	}

}
