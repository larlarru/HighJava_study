package kr.or.ddit.basic;

import java.util.ArrayList;
import java.util.HashSet;

public class ThreadTest13 {
	
	/*
	 * 문제) 10마리의 말들이 경주하는 경마 프로그램 작성하기
	 * 
	 * 경주마는 Horse라는 이름의 클래스로 구성하고
	 * 이 클래스는 말이름(String), 등수(int), 현재위치(int)를 맴버변수로 갖는다.
	 * 그리고, 이 클래스에는 등수를 오름차순으로 처리하는 내부 정렬 기준이 있다.
	 * 		(Comparable인터페이스 구현하기)
	 * 
	 * - 이 Horse클래스는 쓰레드로 작성한다.
	 * 
	 * - 경기 구간은 1 ~ 50구간으로 되어 있다.
	 * 
	 * - 경기 중간 중간에 각 말들의 위치를 나타내시오.
	 * 예)
	 * 01번 말 : --->---------------------
	 * 02번 말 : -->----------------------
	 * 03번 말 : ------------------------
	 * .... 
	 * 10번 말 : ----->-------------------
	 * 
	 * - 경기가 끝나면 등수 순으로 경기 결과를 출력한다.
	 * 
	 */
	
	
	public static void main(String[] args) {
		Horse[] players = new Horse[] {
				new Horse("말01"),
				new Horse("말02"),
				new Horse("말03"),
				new Horse("말04"),
				new Horse("말05"),
				new Horse("말06"),
				new Horse("말07"),
				new Horse("말08"),
				new Horse("말09"),
				new Horse("말10")
		};
		
		for (Horse player : players) {
			player.start();
		}
//		String here="";
		for (Horse player : players) {
			try {
				player.join(); // 다른 쓰레드가 끝날때까지 기달려주는거(?)
				/*for(int i = 1; i <= 50; i++) {
					System.out.println(players[0] + "이 현재 돈 바퀴 수 : " + i);
					System.out.println(players[1] + "이 현재 돈 바퀴 수 : " + i);
					
					if (i == 5 || i == 10 || i == 15 || i == 20 
							|| i == 25 || i == 30 || i == 35 
							|| i == 40 || i == 45 || i == 50) {
						here +=">";
						System.out.println();
						System.out.println("비교하기 위해 넣은것");
						System.out.println("main 있는거");
						System.out.println("현재 말들의 위치");
						System.out.println(players[0] + " : " + here);
						System.out.println(players[1] + " : " + here);
						System.out.println();
						if(here.equals(">")) {
							char temp = here.charAt(i);
							temp='-';
							here. = temp;
						}
					} else {
						here += "-";
					}
					
				}*/
			} catch (InterruptedException e) {
			}
		}
		
		System.out.println();
		HashSet<String> horseList = new HashSet<>();
		horseList.add(Horse.result);
		ArrayList<String> horseListList = new ArrayList<>(horseList);
		for (int i = 0; i < horseListList.size(); i++) {
			System.out.println("경주 결과 :\n" + horseListList.get(i));
		}
//		System.out.println();
//		System.out.println("경기 결과 : " + Horse.rank);
		System.out.println("경기 결과 : ");
		System.out.println(Horse.rank);
//		horseList.add(Horse.name + "\t: " + Horse.here + "> " + Horse.name + " 도착");
	}

}


class Horse extends Thread {
	
	
	public static String rank = "";	// 빨리 출력한 순서대로 저장할 변수 선언
	private String name;
	private String here="";
//	public static String name;
//	public static String here="";
	private static int rrank=1;
	public static String result="";
	
	// 생성자
	public Horse(String name) {
		this.name = name;
	}
	
	@Override
	public void run() {
		for(int i = 1; i <= 50; i++) {
//			HashSet<String> horseList = new HashSet<>();
			if (i == 5 || i == 10 || i == 15 || i == 20 
					|| i == 25 || i == 30 || i == 35 
					|| i == 40 || i == 45 || i == 49) {
////				here += ">";
//				System.out.println(name + " : " + here);
//				here = here.replace("->", "-");
				
				here += "->";
				System.out.println(name + "\t: " + here);
//				result += name + "\t: " + here;
//				horseList.add(result);				
//				ArrayList<String> horseListList = new ArrayList<>(horseList);
//				System.out.println("TEST!!! :\n" + horseListList.get(0)+"\n");
//				// 초기화
//				horseList = new HashSet<>();
//				horseListList = new ArrayList<>(horseList);
//				result = "";
				here = here.replace("->", "-");
			}
			here += "-";
			if (i == 50){
//				HashSet<String> horseList = new HashSet<>();
//				ArrayList<String> horseListList = new ArrayList<>(horseList);
				System.out.println(name + "\t: " + here + "> " + name + " 도착");
				result += name + "\t: " + here + "> " + name + " 도착" + "\n";				
			}
//			System.out.println(name + "\t: " + here);
//			here = here.replace("->", "-");
//			System.out.println(name + "이 현재 돈 바퀴 수 : " + i);
//				System.out.println();
//				System.out.println("TEST!!!!!!");
//				System.out.println("말들 현재 위치");
				/*if (i == 5 || i == 10 || i == 15 || i == 20 
						|| i == 25 || i == 30 || i == 35 
						|| i == 40 || i == 45 || i == 50) {
//					here += ">";
					System.out.println(name + " : " + here);
					here = here.replace("->", "-");
					
				} else {
					here += "->";
//					System.out.println("TEST!! : " + name + " : " + ">");
//					System.out.println(name + "이 현재 돈 바퀴 수 : " + i);
					System.out.println(name + " : " + here);
					
				}*/
//				System.out.println(name + " 현재 바퀴 : " + i);
				/*if (i == 5 || i == 10 || i == 15 || i == 20 
						|| i == 25 || i == 30 || i == 35 
						|| i == 40 || i == 45 || i == 50) {
					here += ">";
					System.out.println();
					System.out.println("비교하기 위해 넣은것");
					System.out.println("본문에 있는거");
					System.out.println("현재 말들의 위치");
					System.out.println(name + " : " + here);
					System.out.println();
					
					 * if(here.equals(">")) { char temp = here.charAt(i); temp='-'; here. = temp; }
					 
				} else {
					here += "-";
				}*/			
			
			try {
				// 101 ~ 500 사이의 난수 설정하기
//				Thread.sleep((int)(Math.random() * 400 + 101));
				Thread.sleep((int)(Math.random() * 101));
				/*if (i == 5 || i == 10 || i == 15 || i == 20 
					|| i == 25 || i == 30 || i == 35 
					|| i == 40 || i == 45 || i == 50) {
					here +=">";
					System.out.println();
					System.out.println("비교하기 위해 넣은것");
					System.out.println("본문에 있는거");
					System.out.println("현재 말들의 위치");
					System.out.println(name + " : " + here);
					System.out.println();
					if(here.equals(">")) {
						char temp = here.charAt(i);
						temp='-';
						here. = temp;
					}
				} else {
					here += "-";
				}*/
			} catch (InterruptedException e) {
			}
			
		} // for문
		
//		System.out.println(name + "이 경기장 다돔.");
//		System.out.println();
//		Horse.rank += name + "\t";
		Horse.rank += name + " 순위 : " + rrank + "\n";
		Horse.rrank++;

		
		
//		System.out.println(name + " 랭크 : " + rrank);
	}
	
	
	
}
