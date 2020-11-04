package kr.or.ddit.basic;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;

/*
 * 문제) 이름, 주소, 전화번호를 맴버로 갖는 Phone클래스를 만들고
 * 		Map을 이용해서 전화번호 정보를 관리하는 프로그램을 작성하시오.
 * 		이 프로그램에는 전화번호를 등록, 수정, 삭제, 검색, 전체 출력하는 기능이 있다.
 * 
 * 		(Map의 구조는 key값으로 '이름'을 사용하고, value값으로 'Phone클래스의 인스턴스'로 한다.)
 * 
 * 		- 삭제, 검색기능은 '이름'을 입력받아 처리한다.
 * 
 * 		- 추가 조건)
 * 			1) 메뉴에 '6. 전화번호 저장' 메뉴를 추가하고 전화번호를 저장하는 기능을 구현한다.
 * 			2) 프로그램이 시작될 때 저장된 파일이 일ㅆ으면 그 데이터를 읽어와 Map에 저장한다.
 * 			3) 프로그램을 종료할 때 전화번호 데이터가 변경되거나 추가 또는 삭제되었으면
 * 				변경된 데이터를 저장한 후에 종료되도록 한다.
 * 				(즉, 데이터가 변경되었는데 저장이 되지 않은 상태이면 저장한다.)
 * 
 * -----------------------------------------------------
 * 
 * 실행예시)
 * ----------
 * 다음 메뉴를 선택하세요.
 * 1. 전화 번호 등록
 * 2. 전화번호 수정
 * 3. 전화번호 삭제
 * 4. 전화번호 검색
 * 5. 전화번호 전체 출력
 * 0. 프로그램 종료
 * ---------------------
 * 번호입력>> 1 한다면
 * 
 * 세롭게 등록할 전화번호 정보를 입력하세요.
 * 이름 >> 홍길동
 * 전화번호 >> 010-1234-5678
 * 주소 >> 대전시 중구 대흥동
 * 
 * '홍길동' 전화번호 등록 완료!!
 * 
 * 실행예시)
 * ----------
 * 다음 메뉴를 선택하세요.
 * 1. 전화 번호 등록
 * 2. 전화번호 수정
 * 3. 전화번호 삭제
 * 4. 전화번호 검색
 * 5. 전화번호 전체 출력
 * 0. 프로그램 종료
 * ---------------------
 * 번호입력>> 1
 * 
 * 세롭게 등록할 전화번호 정보를 입력하세요.
 * 이름 >> 홍길동
 * 
 * '홍길동'은 이미 등록된 사람입니다.
 * 
 * 실행예시)
 * ----------
 * 다음 메뉴를 선택하세요.
 * 1. 전화 번호 등록
 * 2. 전화번호 수정
 * 3. 전화번호 삭제
 * 4. 전화번호 검색
 * 5. 전화번호 전체 출력
 * 0. 프로그램 종료
 * ---------------------
 * 번호입력>> 5
 * 
 * -------------------------------------------------
 * 번호	이름			전화번호			주소
 * ---------------------------------------------
 * 1	홍길동	010-1234-5678	대전시 중구 대홍동
 * ~~~~~~~~~~~~~~~~~~~~~~~
 * -----------------------------------------------
 * 출력 완료...
 * 
 * 다음 메뉴를 선택하세요.
 * 1. 전화 번호 등록
 * 2. 전화번호 수정
 * 3. 전화번호 삭제
 * 4. 전화번호 검색
 * 5. 전화번호 전체 출력
 * 0. 프로그램 종료
 * ---------------------
 * 번호입력>> 0
 * 
 * 프로그램을 종료합니다.
 * 
 */

public class PhoneBook {
	
	private Map<String, Phone> phoneBookMap;
	private Scanner scan;
	boolean check=false;
	
	public PhoneBook() {
		phoneBookMap = new HashMap<>();
		scan = new Scanner(System.in);
	}

	public static void main(String[] args) {
		new PhoneBook().phoneBookStart();
	}
	
	// 프로그램을 시작하는 메서드
	private void phoneBookStart() {
		System.out.println("=====================================");
		System.out.println("		전화번호 관리 프로그램");
		System.out.println("=====================================");
		System.out.println();
		
		while(true) {
			int choice = displayMenu();
			
			switch(choice) {
				case 1 :	// 등록	
					insert();
					break;
				case 2 :	// 수정
					update();
					break;
				case 3 :	// 삭제
					delete();
					break;
				case 4 :	// 검색
					search();
					break;
				case 5 :	// 전체 출력
					displayAll();
					break;
				case 6 :	// 전하번호 데이터 파일로 저장
					save();
					break;
				case 0 :	// 종료
					if(check==false) {
					System.out.println("프로그램 종료");
						System.out.println("종료전 저장");
						save();
						check=true;
						System.out.println(check);
						return;
					} else {
						System.out.println(check);
						return;
					}
				default :
					System.out.println("번호 잘못 입력하셨습니다. 다시 입력하세요.");
					break;
			}
		}
		
	}
	

	// 메뉴를 출력하고 작업 번호를 입력받아 반환하는 메서드
	private int displayMenu() {
		System.out.println();
		System.out.println("=====================================");
		System.out.println("	다음 메뉴를 선택하세요. ");
		System.out.println();
		System.out.println("	1.전화번호 등록");
		System.out.println("	2.전화번호 수정");
		System.out.println("	3.전화번호 삭제");
		System.out.println("	4.전화번호 검색");
		System.out.println("	5.전화번호 전체 출력");
		System.out.println("	6. 전화번호 저장");
		System.out.println("	0.종료");
		System.out.println("=====================================");
		System.out.print("번호 입력>> ");
		int num = scan.nextInt();
		return num;
		
	}
	
	// 새로운 전화번호 정보를 등록하는 메서드
	private void insert() {
		System.out.println();
		System.out.println("새로 등록할 전화번호 정보를 입력하세요.");
		
		System.out.print("이름 입력>>");
		String name = scan.next();
		
		// 이미 등록된 사람인지 검사하기
		if(phoneBookMap.containsKey(name)) {
			System.out.println(name + "씨는 이미 등록된 사람입니다.");
			return; // 메서드를 끝낸다.
		}
		
		System.out.print("전화번호 입력>>");
		String tel = scan.next();
		
		System.out.print("주소 입력>>");
//		String addr = scan.next();
		// 입력 버퍼 비우기
		scan.nextLine();	// 입력 버퍼 비어준다.
		String addr = scan.nextLine(); // 주소 입력 받기도 전에 그냥 넘어가버림 그 이유가 버퍼에 엔터값등이 남아 있기때문에 그걸 입력했다고 판단하고 넘어감
//		Phone p = new Phone(name, tel, addr);
//		phoneBookMap.put(name, p);
		phoneBookMap.put(name, new Phone(name, tel, addr));
		
		
		
		
		System.out.println(name + "씨 등록 완료");
		save();
		
		
		
	}
	
	// Map에 저장된 전체 전화번호 정보를 수정하는 메서드
	private void update() {
		readDate();
		System.out.println("수정할 전화번호 입력");
		
		System.out.print("이름 >>");
		String name = scan.next();
		
		// 수정할 데이터가 있느지 검사
		if(!phoneBookMap.containsKey(name)) {
			System.out.println(name + "씨의 전화번호 정보가 없습니다.");
			System.out.println("수정불가!!");
			return;
		}
		
		System.out.print("새로운 전화번호>>");
		String newTel = scan.next();
		
		scan.nextLine();	// 입력 버퍼 비우기
		System.out.print("새로운 주소>>");
		String newAddr = scan.nextLine();
		
		// 방법1
		//	같은 키값에 새로운 전화번호 정보를 저장한다.	==>	수정작업
		phoneBookMap.put(name, new Phone(name, newTel, newAddr));
		
		/*
		// 방법2
		Phone p = phoneBookMap.get(name); // 키값을 이용해서 value값을 구한다.
		p.setTel(newTel);	// 구해온 Phone객체의 각각의 데이터를 변경한다.
		p.setAddr(newAddr);
		
		System.out.println(name + "씨의 전화번호 정보를 변경하였습니다.");
		*/
		if(check==false) {
			System.out.println("수정확인 ");
			save();
		}
	}
	
	// 전화번호 정보를 삭제하는 메서드
	private void delete() {
		readDate();
		System.out.println();
		System.out.println("삭제할 전화번호 정보를 입력하세요.");
		System.out.print("삭제할 사람 이름>>");
		String name = scan.next();
		
		// 등록된 전화번호 정보가 있는지 검사
		if(!phoneBookMap.containsKey(name)) {
			System.out.println(name + "씨의 전화번호 정보는 없습니다.");
			System.out.println("삭제 작업 불가!!");
			return;
		}
		
		phoneBookMap.remove(name);
		
		System.out.println(name + "씨의 전화번호 정보를 삭제했습니다.");
		if(check==false) {
			System.out.println("삭제확인");
			save();
		}
	}
	
	// 전화번호 정보를 검색하는 메서드
	private void search() {
		readDate();
		System.out.println();
		System.out.println("검색할 전화번호 정보를 입력하세요.");
		System.out.print("이름>>");
		String name = scan.next();
		
		// 등록된 전화번호 정보가 있는지 검사
		if (!phoneBookMap.containsKey(name)) {
			System.out.println(name + "씨의 전화번호 정보는 없습니다.");
			return;
		}
		
		// 검색한 데이터가 있으면 해당 key값에 맞는 value값을 구한다.
		Phone p = phoneBookMap.get(name);
		
		System.out.println();
		System.out.println(name + "씨의 전화번호 정보");
		System.out.println("=====================================");
		System.out.println("	이름 : " + p.getName());
		System.out.println("	전화번호 : " + p.getTel());
		System.out.println("	주소 : " + p.getAddr());
		System.out.println("=====================================");
		
	}
	
	
	// Map에 저장된 전체 전화번호 정보를 출력하는 메서드
	private void displayAll() {
		readDate();
		System.out.println();
		System.out.println("===========================================================");
		System.out.println("	번호	이름	전화번호	주소");
		System.out.println("===========================================================");
		
		if(phoneBookMap.size()==0) {
			System.out.println("등록된 데이터가 하나도 없습니다.");
		} else {
			int cnt = 0;	// 번호를 나타내는 변수
			Iterator<String> phoneIt = phoneBookMap.keySet().iterator();
			
			while(phoneIt.hasNext()) {
				cnt++;
				String name = phoneIt.next();	// 키값(등록된 사람이름) 찾기
				Phone p = phoneBookMap.get(name);	// value값(Phone객체) 구하기
				System.out.println("	" + cnt + "	" + p.getName() + "	"
						+ p.getTel() + "	" + p.getAddr());
			} // while문
		} // else문
		
		System.out.println("===========================");
		System.out.println("	출력 작업 끝");
		
		
		
		
		
		
		
		
	}
	
	// 저장된 전화번호 객체 읽어오는 메서드
	private void readDate() {
		// 입력용 스트림 객체 생성
		try {
			ObjectInputStream ois = new ObjectInputStream(
					new BufferedInputStream(new FileInputStream("d:/D_Other/phoneData.dat")));

			Object obj; // 읽어온 객체를 저장할 변수
			try {

				System.out.println("저장된 전화번호 데이터 읽기 작업 시작");

				// System.out.println("==================================");
				// readObject() 메서드가 데이터의 끝까지 다 읽어오면 EOFException을 발생한다.
				while ((obj = ois.readObject()) != null) {
					// 읽어온 데이터를 원래의 객체형으로 형변환 후 사용한다.
					// Member mem = (Member)obj;
					Phone ph = (Phone) obj;
					
					
					phoneBookMap.put(ph.getName(), new Phone(ph.getName(), ph.getTel(), ph.getAddr()));
					
				}

			} catch (EOFException e) {
				System.out.println("저장된 전화번호 데이터 읽기 작업 끝");
			} catch (ClassNotFoundException e) {
				System.out.println("오류1");
				//e.printStackTrace();
			} finally {
				ois.close();// 스트림 닫기
			}
		} catch (IOException e) {
			System.out.println("읽어오기 위한 저장된 전화번호부가 없습니다.");
			//e.printStackTrace();
		}
	}
	
	// 전화번호 저장 메서드
	private void save() {
		
		try {
			
			// 객체 저장하기
			FileOutputStream fout = new FileOutputStream("d:/D_Other/phoneData.dat");

			BufferedOutputStream bout = new BufferedOutputStream(fout);

			ObjectOutputStream oos = new ObjectOutputStream(bout);

			// 데이터 가져오기
			if(phoneBookMap.size()==0) {
				System.out.println("등록된 데이터가 하나도 없습니다.");
				return;
			} else {
				
				//Iterator<String> phoneIt = phoneBookMap.keySet().iterator();
				
				
				System.out.println("전화번호 객체 저장하기");
				
				check = false;
				
				for(String key : phoneBookMap.keySet()) {
					Phone value = phoneBookMap.get(key);
					oos.writeObject(phoneBookMap.get(key));
					System.out.println(key);
				}
				
				/*while(phoneIt.hasNext()) {
					System.out.println(phoneIt.next());
					String name = phoneIt.next();	// 키값(등록된 사람이름) 찾기
					Phone p = phoneBookMap.get(name);	// value값(Phone객체) 구하기
					System.out.println(p);
					oos.writeObject(phoneIt.hasNext());
					
				} // while문
*/			} // else문
			
			// 쓰기 작업 (출력작업)
			System.out.println("전화 번호 객체 저장 작업 끝");

			oos.close(); // 스트림 닫기
			
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	

}


//이름, 주소, 전화번호를 맴버로 갖는 Phone 클래스
class Phone implements Serializable {
	
	private String name; 
	private String tel;
	private String addr;
	
	public Phone(String name, String tel, String addr) {
		super();
		this.name = name;
		this.tel = tel;
		this.addr = addr;
	}
	
	public String getName() {
		return name;
	}
	public String getTel() {
		return tel;
	}
	public String getAddr() {
		return addr;
	}
	/*public void setName(String name) {
		this.name = name;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}*/
}