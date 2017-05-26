package test;



public class Test {
	
	public static int i=0;
	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		aaa a = new aaa();
		a.start();
		
		System.out.println(i);
		a.join();
		System.out.println(i);
	}

}
