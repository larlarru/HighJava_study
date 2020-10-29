package kr.or.ddit.basic;

// 쓰레드의 상태를 출력하는 예제
public class ThreadTest10 {
	
	public static void main(String[] args) {
		TargetThread t = new TargetThread();
		StatePrintThread th = new StatePrintThread(t);
		
		th.start();
	}

}

// 쓰레드 상태의 검사 대상이 되는 쓰레드
class TargetThread extends Thread {
	@Override
	public void run() {
		for (long i = 1L; i <= 20_000_000_000L; i++) { // 시간 지연용
		}
		
		try {
			Thread.sleep(1500);
		} catch (InterruptedException e) {
		}

		for (long i = 1L; i <= 20_000_000_000L; i++) { // 시간 지연용
		}
	}
}

// TargetThread의 상태를 출력하는 쓰레드
class StatePrintThread extends Thread {
	private TargetThread target;	// TargetThread가 저장될 변수 선언
	
	// 생성자
	public StatePrintThread(TargetThread target) {
		this.target = target;
	}
	
	@Override
	public void run() {
		while(true) {
			// TargetThread의 상태 구하기
			Thread.State state = target.getState();
			System.out.println("TargetThread의 현재 상태값 : " + state);
			
			if (state==Thread.State.NEW) {	// 쓰레드의 현재 상태가 NEW 상태이면...
				target.start();
			}
			
			if (state == Thread.State.TERMINATED) {	// 쓰레드가 종료 상태이면...
				break;
			}
			
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
			}
		}
	}
}












