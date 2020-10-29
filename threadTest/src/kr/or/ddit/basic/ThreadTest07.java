package kr.or.ddit.basic;

import javax.swing.JOptionPane;

public class ThreadTest07 {
	
	/*
	 * 문제) 컴퓨터와 가위 바위 보를 진행하는 프로그램을 작성하시오.
	 * - 컴퓨터의 가위 바위 보는 난수를 이용해서 구하고
	 * - 사용자는 showInputDialog()메서드를 이용해서 입력 받는다.
	 * - 입력시간은 5초로 제한하고 카운트 다운을 진행한다.
	 * - 5초안에 입력이 없으면 게임에 진것으로 처리하고 끝낸다.
	 * - 5초안에 입력이 완료되면 승패를 구해서 출력한다.
	 * 
	 * 결과 예시)
	 * -- 결과 --
	 * 컴퓨터 : 가위
	 * 사용자 : 바위
	 * 결과 : 당신이 이겼습니다.
	 * 
	 * 5초안에 입력이 없었을때 결과 예시
	 * 		시간이 초과되어 당신이 졌습니다.
	 */
	
	public static void main(String[] args) {
		
		Thread th1 = new GameDataInput();
		Thread th2 = new GameCountDown();
		
		th1.start();
		th2.start();
		
	}

}

//데이터를 입력하는 쓰레드
class GameDataInput extends Thread {
	
	// 입력 여부를 확인하기 위한 벼수 선언 ==> 쓰레드에서 공통으로 사용할 변수
	public static boolean inputCheck;
	
	@Override
	public void run() {
		
		int ai = (int)(Math.random() * 3)+1;
		
//		System.out.println("랜덤 값 잘 나오는지 확인 : " + ai);
		String str = JOptionPane.showInputDialog("가위바위보를 입력하세요.");
		inputCheck = true;
		
		// 가위바위보 조건 비교들
		if (str == null) {	//만약에 입력하지 않고 취소 눌러서 null이 나올경우 에러 방지하는데 만약에 이게 여기 맨앞이 아니라 밑에 있으면 에러난다.
			System.out.println("아무것도 입력하지 않았길래 기권으로 판단해서 당신은 졌습니다.");
			System.exit(0);
		} else if (str.equals("가위")) {
			if (ai == RPSGame.SCISSORS) { // 가위-가위였을경우
				System.out.println("입력한 값 : " + str);
				System.out.println("비겼습니다.");
				System.exit(0);
			} else if (ai == RPSGame.ROCK) { // 가위-바위였을경우
				System.out.println("입력한 값 : " + str);
				System.out.println("당신이 졌습니다.");
				System.exit(0);
			} else if (ai == RPSGame.PAPER) { // 가위-보였을경우
				System.out.println("입력한 값 : " + str);
				System.out.println("당신이 이겼습니다.");
				System.exit(0);
			}

		} else if (str.equals("바위")) {
			if (ai == RPSGame.SCISSORS) { // 바위-가위였을경우
				System.out.println("입력한 값 : " + str);
				System.out.println("당신이 이겼습니다.");
				System.exit(0);
			} else if (ai == RPSGame.ROCK) { // 바위-바위였을경우
				System.out.println("입력한 값 : " + str);
				System.out.println("비겼습니다.");
				System.exit(0);
			} else if (ai == RPSGame.PAPER) { // 바위-보였을경우
				System.out.println("입력한 값 : " + str);
				System.out.println("당신이 졌습니다.");
				System.exit(0);
			}

		} else if (str.equals("보")) {
			if (ai == RPSGame.SCISSORS) { // 보-가위였을경우
				System.out.println("입력한 값 : " + str);
				System.out.println("당신이 졌습니다.");
				System.exit(0);
			} else if (ai == RPSGame.ROCK) { // 보-바위였을경우
				System.out.println("입력한 값 : " + str);
				System.out.println("당신이 이겼습니다.");
				System.exit(0);
			} else if (ai == RPSGame.PAPER) { // 보-보였을경우
				System.out.println("입력한 값 : " + str);
				System.out.println("비겼습니다.");
				System.exit(0);
			}

		} else {
			System.out.println("아무것도 입력하지 않았길래 기권으로 판단해서 당신은 졌습니다.");
			System.exit(0);
		}
		
		
	}
	
}

//카운트 다운을 진행하는 쓰레드
class GameCountDown extends Thread {

	@Override
	public void run() {
		for(int i=5; i>=1; i--) {
			
			// 입력이 완료되었는지 여부를 검사해서 입력이 완료되면 쓰레드를 종료시킨다.
			if (GameDataInput.inputCheck==true) {
				return; // run()메서드가 종료되면 해당 쓰레드로 종료된다.
			}
			System.out.println(i);
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}
		}
		System.out.println();
		System.out.println("시간이 초과되어 당신이 졌습니다.");
		System.exit(0);
	}
	
}

class RPSGame {
	
	public final static int ROCK = 1;
	public final static int PAPER = 2;
	public final static int SCISSORS = 3;
	
}