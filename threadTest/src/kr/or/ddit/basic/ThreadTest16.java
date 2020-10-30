package kr.or.ddit.basic;

public class ThreadTest16 {
	
	public static void main(String[] args) {
		ShareObject sobj = new ShareObject();
		
		TestThread th1 = new TestThread("1번 쓰레드", sobj);
		TestThread th2 = new TestThread("2번 쓰레드", sobj);
		
		th1.start();
		th2.start();
	}

}

class TestThread extends Thread {
	private ShareObject sObj;
	
	// 생성자
	public TestThread(String name, ShareObject sObj) {
		super(name);	// 쓰레드의 name 설정한다.
		this.sObj = sObj;
	}
	
	@Override
	public void run() {
		for (int i = 0; i < 10; i++) {
			sObj.add();
		}
	}
	
	
}

// 공통으로 사용할 클래스
class ShareObject {
	private int sum = 0;
	
//	public synchronized void add() {	// 방법 1 ==> 메서드에 동기화 설정을 한다.(메서드자체에 락을 건다.)
	public void add() {
		
		synchronized (this) {	// 방법2 ==> 동기화 블럭으로 설정한다.
			
		int n = sum;
		
		n += 10;
		
		sum = n;
		
		System.out.println(Thread.currentThread().getName() + " 합계 : " + sum);
		}
	}
	
}






















