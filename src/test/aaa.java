package test;

public class aaa extends Thread{

	public void run(){
		for(int i=0;i<5;i++){
			try {
				sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Test.i++;
		}
	}
}
