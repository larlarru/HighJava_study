package kr.or.ddit.basic;

public class ArgsTest {
	
	/*
	 * 가변형인수 ==> 메서드의 매개변수에 주어지는 인수의 개수가 실행될 때마다 다를 때 사용한다.
	 * 		- 가변형 인수는 메서드 내부에서는 배열로 처리한다.
	 * 		- 가변형 인수는 한가지 자료형만 사용할 수 있다.
	 */
	
	public static void main(String[] args) {
		ArgsTest test = new ArgsTest();
		
		int[] nums = {100, 200, 300};
		int[] nums2 = new int[] {100, 200, 300};
		
		System.out.println(test.sumData(nums));
		System.out.println(test.sumData(new int[] {1,2,3,4,5}));
		System.out.println("-----------------------------");
		
		System.out.println(test.sumArgs(100,200,300));
		System.out.println(test.sumArgs(1,2,3,4,5));
		System.out.println(test.sumArgs2("이순신", 80, 70, 90, 100));
		
		/*int k = 100;
		test.test1(k);
		
		test.test1(300);*/
	}
	
	/*public void test1(int a) {
	}*/
	
	// 매개변수들의 합을 계산해서 반환하는 메서드
	public int sumData(int[] data) {
		int sum =0;
		for (int i = 0; i < data.length; i++) {
			sum += data[i];
		}
		
		return sum;
	}
	
	// 매개변수를 합을 계산해서 반환하는 메서드 ==> 가변형 인수를 이용한 메서드
	public int sumArgs(int...data) {
		// 이 메서드 안에서 변수 'data'는 'int[] data'와 같다. 즉 배열과 같다.
		int sum =0;
		for (int i = 0; i < data.length; i++) {
			sum += data[i];
		}
		
		return sum;
		
	}
	
	// 가변형 인수와 일반 인수를 같이 사용할 경우
	public String sumArgs2(String name, int...data) {
		// 이 메서드 안에서 변수 'data'는 'int[] data'와 같다. 즉 배열과 같다.
		int sum =0;
		for (int i = 0; i < data.length; i++) {
			sum += data[i];
//			sum = sum + date[i];
		}
		
		return name + "씨의 합계 : " + sum;
		
	}
	
}













