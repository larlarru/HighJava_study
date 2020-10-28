package kr.or.ddit.basic;

public class ThreadTest01 {
	
	//싱글 쓰레드
	//위에서부터 순서대로 실행
	public static void main(String[] args) {
		for (int i = 0; i <= 200; i++) {
			System.out.print("*");
		}
			
			System.out.println();
			System.out.println();
			
			for (int j = 0; j <= 200; j++) {
				System.out.print("$");
			}
		}
		

}





















