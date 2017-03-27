package test;

import bean.Resource;
import net.sf.json.JSONObject;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Resource re = new Resource();
		re.setName("ssss");
		re.setDescription("eeeee");
		re.setURI("www.jkjkl.com.au");
		
		JSONObject o = JSONObject.fromObject(re);
		System.out.println(o);
	}

}
