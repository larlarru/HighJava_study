package kr.or.ddit.basic;

import javax.swing.JOptionPane;

public class ThreadTest07_teacher {
	
	// 가위바위보 게임
	
	public static boolean inputCheck;

	public static void main(String[] args) {
		GameTimer gt = new GameTimer();
		
		// 난수를 이용해서 컴퓨터의 가위 바위 보를 정한다.
		String[] data = {"가위", "바위", "보"};
		int index = (int)(Math.random() * 3); // 0~2사이의 난수
		String com = data[index];
		
		// 사용자로부터 가위 바위 보 입력 받기
		gt.start();
		String man;
		do {
			man = JOptionPane.showInputDialog("가위 바위 보를 입력하세요.");
//		} while(!(man.equals("가위") || man.equals("바위") || man.equals("보")));
		} while(!man.equals("가위") && !man.equals("바위") && !man.equals("보"));
		inputCheck = true;
		
		// 결과를 판정하기
		String result = "";	// 결과가 저장될 변수 선언
		if(man.equals(com)) {
			result ="비겼습니다.";
		} else if(man.equals("가위") && com.equals("보") 
				|| man.equals("바위") && com.equals("가위")
				|| man.equals("보") && com.equals("바위")) {
			result = "당신이 이겼습니다.";
		} else {
			result = "당신이 졌습니다.";
		}
		
		// 결과 출력
		System.out.println();
		System.out.println("---	결과	---");
		System.out.println("컴퓨터 : " + com);
		System.out.println("당신 : " + man);
		System.out.println("결과 : " + result);
	}

}

class GameTimer extends Thread {
	@Override
	public void run() {
		System.out.println("카운트 다운 시작...");
		for (int i = 5; i >= 1; i--) {
			
			if (ThreadTest07_teacher.inputCheck==true) {	// 입력 여부 검사
				return;
			}
			
			System.out.println();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}
		}
		System.out.println("시간이 초괴되어 당신이 졌습니다.");
		System.exit(0);
	}
	
}




