package kr.or.ddit.basic;

// 멀티 쓰레드
public class ThreadTest02 {
	
	public static void main(String[] args) {
		// Thread를 작성해서 사용하는 방법
		
		// 방법1
		// Thread클래스를 상속한 class의 인스턴스를 생성한 후
		// 이 인스턴스의 start()메서드를 호출해서 실행한다.
		MyThread1 th1 = new MyThread1(); // 호출
		th1.start();
		
		// 방법2
		// Runnable인터페이스를 구현한 class의 인스턴스를 생성한 후
		// 이 인스턴스를 Thread의 인스턴스를 생성할 때 생성자의 인수값으로 넘겨준다.
		// 이 때 생성된 Thread의 인스턴스의 start()메서드를 호출해서 실행한다.
		MyThread2 r2 = new MyThread2();
		Thread th2 = new Thread(r2);
		th2.start();
		
		// 방법3 ==> 익명 구현체를 이용하는 방법
		// 이렇게 직접 구현하는 방법이 있다.
		Runnable r3 = new Runnable() {
			@Override
			public void run() {
				for (int i = 1; i <= 200; i++) {
					System.out.print("%");
					try {
						// Thread.sleep(시간); ==> 주어진 '시간'동안 작업을 잠시 멈춘다.
						//		'시간'은 밀리세컨드 단위를 사용한다.
						//		즉, 1000은 1초를 의미한다.
						Thread.sleep(100);
					} catch (InterruptedException e) {
					}
				}
			}
		};
		Thread th3 = new Thread(r3);
		th3.start();
		
		System.out.println("메인 메서드 끝...");
		
	}

}

// 방법1
class MyThread1 extends Thread {

	@Override
	public void run() {
		// 이 run() 메서드안에 Thread가 처리할 내용을 기술한다.
		for (int i = 1; i <= 200; i++) {
			System.out.print("*");
			try {
				// Thread.sleep(시간); ==> 주어진 '시간'동안 작업을 잠시 멈춘다.
				//		'시간'은 밀리세컨드 단위를 사용한다.
				//		즉, 1000은 1초를 의미한다.
				Thread.sleep(100);
			} catch (InterruptedException e) {
			}
		}
	}
	
}


// 방법2

class MyThread2 implements Runnable {

	@Override
	public void run() {
		for (int j = 1; j <= 200; j++) {
			System.out.print("$");
			try {
				// Thread.sleep(시간); ==> 주어진 '시간'동안 작업을 잠시 멈춘다.
				//		'시간'은 밀리세컨드 단위를 사용한다.
				//		즉, 1000은 1초를 의미한다.
				Thread.sleep(100);
			} catch (InterruptedException e) {
			}
		}
		
	}
	
}









