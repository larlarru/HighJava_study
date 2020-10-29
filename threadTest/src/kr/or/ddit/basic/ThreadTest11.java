package kr.or.ddit.basic;

// yield() 메서드 연습
public class ThreadTest11 {
	
	public static void main(String[] args) {
		
		YieldThread th1 = new YieldThread("1번 쓰레드");
		YieldThread th2 = new YieldThread("2번 쓰레드");
		
		th1.start();
		th2.start();
		
		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
		}
		
		System.out.println("===============================================================");
		th1.work = false;
		
		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
		}
		
		System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
		
		th1.work = true;
		
		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
		}
		
		System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
		
		th1.stop = true;
		th2.stop = true;
		
		
	}

}


// yield()메서드 연습용 쓰레드
class YieldThread extends Thread {	//여기 Thread에 super(name)이거 저장하는게 들어 있다.
	public boolean stop = false;	// 쓰레드의 종료 여부를 나타내는 값을 저장하는 변수 선언
	public boolean work = true;		// 작업을 처리하는 여부를 나타내는 값을 저장하는 변수 선언
	
	// 생성자
	public YieldThread(String name) {
		super(name);	// 쓰레드의 이름을 설정한다.
	}
	
	@Override
	public void run() {
		while(!stop) {	// stop이 true가 되면 반복문이 종료된다.
			if(work) {
				// getName()메서드 ==> 현재 쓰레드의 name값을 반환한다.
				System.out.println(getName() + " 작업 중...");	// 여기 getName()메서드는 Thread에 있는 것을 가져온다.
			} else {
				System.out.println(getName() + " 양보...");
				Thread.yield();
			}
		}
	}
}


















