package kr.or.ddit.room.controller;

import java.util.List;
import java.util.Scanner;

import kr.or.ddit.member.vo.MemberVO;
import kr.or.ddit.room.service.HotelRoomServiceImpl;
import kr.or.ddit.room.service.IHotelRoomService;
import kr.or.ddit.room.vo.HotelRoomVO;

public class HotelRoomController {
	
	public Scanner scan;
	private IHotelRoomService service;
	
	public HotelRoomController() {
		scan = new Scanner(System.in);
		service = HotelRoomServiceImpl.getInstance();
	}

	public static void main(String[] args) {

		System.out.println("*********************************************");
		System.out.println("       호텔문을 열었습니다. 어서오십시요."); 
		System.out.println("*********************************************");
		
		new HotelRoomController().hotelRoomStart();
	}

	public void hotelRoomStart() {
		
		while (true) {
			int choice = displayMenu();
			switch (choice) {
			case 1:
				roomCheckIn();	// 체크인
				break;
			case 2:
				roomCheckOut();	// 체크아웃
				break;
			case 3:
				displayRoom();	// 전체 출력
				break;
			case 4:
				System.out.println();
				System.out.println("프로그램을 종료합니다.");
				return;
			default:
				System.out.println("잘못 선택했습니다. 다시 입력하세요.");
				System.out.println();
			}
		}
		
	}
	
	private void roomCheckOut() {
		
		System.out.print("방번호 입력>>");
		int roomNo = scan.nextInt();
		
		//int count = service.getRomUserCheckCount(roomNo);
		String obj="";
		obj = service.getRomUserCheckCount(roomNo);
		if(obj.equals("-")) {
			System.out.println("아무도 없는 방입니다.");
			return;
		}
		/*System.out.print("투수객 이름 입력>>");
		String roomUser = scan.next();*/
		String roomUser ="-";
		HotelRoomVO roomVo = new HotelRoomVO();
		roomVo.setRoom_no(roomNo);
		roomVo.setRoom_user(roomUser);
		
		int cnt = service.updateRoomCheckIn(roomVo);
		
		if (cnt > 0) {
			System.out.println("체크아웃 되셨습니다.");
		} else {
			System.out.println("체크아웃 실패~~~~");
		}
		
		/*System.out.print("방번호 입력>>");
		int roomNo = scan.nextInt();
		
		int cnt = service.updateRoomCheckOut(roomNo);
		
		if (cnt > 0) {
			System.out.println("체크아웃 되셨습니다.");
		} else {
			System.out.println("체크아웃 실패~~~~");
		}*/
		
	}

	private void roomCheckIn() {
		
		System.out.println("-----------------------------------------------------------");
		System.out.println("201~209 : 싱글룸");
		System.out.println("301~309 : 더블룸");
		System.out.println("401~409 : 스위트룸");
		System.out.println("-----------------------------------------------------------");
		
		System.out.print("방번호 입력>>");
		int roomNo = scan.nextInt();
		
		//int count = service.getRomUserCheckCount(roomNo);
		String obj="";
		obj = service.getRomUserCheckCount(roomNo);
		if(!obj.equals("-")) {
			System.out.println("이미 투수객이 있는 방입니다.");
			return;
		}
		System.out.print("투수객 이름 입력>>");
		String roomUser = scan.next();
		
		HotelRoomVO roomVo = new HotelRoomVO();
		roomVo.setRoom_no(roomNo);
		roomVo.setRoom_user(roomUser);
		
		int cnt = service.updateRoomCheckIn(roomVo);
		
		if (cnt > 0) {
			System.out.println("체크인 되셨습니다.");
		} else {
			System.out.println("체크인 실패~~~~");
		}
		
		
	}


	
	private int displayMenu() {
		
		System.out.println();
		System.out.println("-----------------------------------------------------------");
		System.out.println("어떤 업무를 하시겠습니까?");
		System.out.println("1. 체크인    2. 체크아웃    3. 객실상태    4. 업무종료");
		System.out.println("-----------------------------------------------------------");
		System.out.print("번호 입력>> ");
		
		return scan.nextInt();
	}
	
	// 전체 정보를 출력하는 메서드
	private void displayRoom() {

		List<HotelRoomVO> roomList = service.getAllHotelRoom();

		System.out.println();
		System.out.println("--------------------------------------");
		System.out.println("방 번호	 방 종류	 투숙객 이름");
		System.out.println("--------------------------------------");

		if (roomList == null || roomList.size() == 0) {
			System.out.println("회원 정보가 하나도 없습니다.");
		} else {

			// List에 저장된 데이터 갯수만큼 반복해서 자료를 출력
			for (HotelRoomVO roomVo : roomList) {
				int roomNo = roomVo.getRoom_no();
				String roomType = roomVo.getRoom_type();
				String roomUser = roomVo.getRoom_user();

				System.out.println(roomNo + "\t" + roomType + "\t" + roomUser);
			}
			System.out.println("--------------------------------------");
		}

	}
	
	
	

}
