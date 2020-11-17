package kr.or.ddit.controller;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import kr.or.ddit.service.Jdbc_BoardService;
import kr.or.ddit.service.Jdbc_BoardServiceImpl;
import kr.or.ddit.util.DBUtil3;
import kr.or.ddit.vo.Jdbc_BoardVO;

public class Jdbc_BoardController {

	public Scanner scan = new Scanner(System.in);

	private Jdbc_BoardService service;
	int boardNumber = 0;

	public Jdbc_BoardController() {

		service = Jdbc_BoardServiceImpl.getInstance();
	}

	public static void main(String[] args) {
		new Jdbc_BoardController().start();
	}

	public void start() {

		while (true) {
			// System.out.println("-------------------------------------------------------------");
			// System.out.println("No 제 목 작성자 조회수");
			selectAllBoard();
			System.out.println("메뉴 : 1. 새글작성     2. 게시글보기    3. 검색    0. 작업끝");
			System.out.print("작업선택 >>");
			int input = scan.nextInt();
			scan.nextLine();

			switch (input) {
			case 1: // 새글작성
				createBoard();
				break;
			case 2: // 게시글보기
				showBoard();
				break;
			case 3: // 검색
				selectBoard();
				break;
			case 0:
				System.out.println("프로그램을 종료합니다.");
				System.exit(0);
			default:
				break;
			}
		}

	}
	
	// 새글작성
	private void createBoard() {

		System.out.print("게시글 제목 입력>>");
		String boardTitle = scan.nextLine();

		System.out.print("작성자 입력>>");
		String boardWriter = scan.nextLine();

		System.out.print("내용 입력>>");
		String boardContent = scan.nextLine();

		
		
		Jdbc_BoardVO jdbc_BoardVo = new Jdbc_BoardVO();
		
		jdbc_BoardVo.setBoard_title(boardTitle);
		jdbc_BoardVo.setBoard_writer(boardWriter);
		jdbc_BoardVo.setBoard_content(boardContent);
		
		int cnt = service.insertJdbc_Board(jdbc_BoardVo);

		if (cnt > 0) {
			System.out.println("새글이 추가되었습니다.");
		} else {
			System.out.println("새글이 추가 실패하였습니다.");
		}

		start();
	}
	
	// 검색하는 메서드
	public void selectBoard() {

		System.out.print("검색할 제목 입력 >>");
		String searchName = scan.nextLine();
		
		List<Jdbc_BoardVO> jdbc_BoardList = service.getJdbc_BoardList(searchName);
		
		if (jdbc_BoardList != null) {

			System.out.println("----------------------------------------");
			System.out.println("NO\t제 목\t\t작성자\t조회수");
			System.out.println("----------------------------------------");
			
			for(Jdbc_BoardVO jdbc_Board : jdbc_BoardList) {

				System.out.print(jdbc_Board.getBoard_no() + "\t");
				System.out.print(jdbc_Board.getBoard_title() + "\t");
				System.out.print(jdbc_Board.getBoard_writer() + "\t");
				System.out.print(jdbc_Board.getBoard_cnt() + "\t");
				System.out.println();
			}
			System.out.println("검색이 끝났습니다.");
			
			/*while (true) {
				System.out.println("-------------------------------------------------------------");
				System.out.println("메뉴 : 1. 새글작성     2. 게시글보기    3. 검색    0. 작업끝");
				System.out.print("작업선택 >>");
				int input = scan.nextInt();
				scan.nextLine();

				switch (input) {
				case 1: // 새글작성
					createBoard();
					break;
				case 2: // 게시글보기
					showBoard();
					break;
				case 3: // 검색
					selectBoard();
					break;
				case 0:
					System.out.println("프로그램을 종료합니다.");
					System.exit(0);
				default:
					break;
				}
			}*/

		} else {
			System.out.println("해당 게시글이 없습니다.");
			start();
		}

		start();
	}
	
	// 게시글 보기
	public void showBoard() {
		boardNumber = 0;
		System.out.print("보기를 원하는 게시물 번호 입력 >>");
		boardNumber = scan.nextInt();
		
		int count = service.getJdbc_BoardCount(boardNumber);
		
		if (count==0) {
			System.out.println("조회 실패하였습니다.");
			start();
		}
		
		Jdbc_BoardVO jdbc_BoardVo = service.getJdbc_BoardShowOne(boardNumber);

		if (jdbc_BoardVo != null) {
			System.out.println("============================");
			System.out.println("제  목 : " + jdbc_BoardVo.getBoard_title());
			System.out.println("작성자 : " + jdbc_BoardVo.getBoard_writer());
			System.out.println("내  용 : " + jdbc_BoardVo.getBoard_content());
			System.out.println("작성일 : " + jdbc_BoardVo.getBoard_date());
			System.out.println("조회수 : " + jdbc_BoardVo.getBoard_cnt());
			System.out.println("============================");
		} else {
			System.out.println("해당 게시글이 없습니다.");
		}

		while (true) {

			System.out.println("============================");
			System.out.println("메뉴 : 1. 수정    2. 삭제    3. 리스트로 가기");
			System.out.println("============================");

			System.out.print("작업선택 >>");
			int input = scan.nextInt();
			scan.nextLine();

			switch (input) {
			case 1: // 수정
				updateBoard();
				break;
			case 2: // 삭제
				deleteBoard();
				break;
			case 3: // 리스트로 가기
				start();
				break;
			default:
				System.out.println("다시 입력하세요.");
				break;
			}
		}


	}
	
	// 수정
	public void updateBoard() {
		
		
		System.out.print("수정할 제목 입력 >>");
		String boardTitle = scan.nextLine();

		System.out.print("수정할 내용 입력>>");
		String boardContent = scan.nextLine();
		
		Jdbc_BoardVO jdbc_BoardVo = new Jdbc_BoardVO();
		
		jdbc_BoardVo.setBoard_title(boardTitle);
		jdbc_BoardVo.setBoard_content(boardContent);
		
		int cnt = service.updateJdbc_Board(boardNumber, jdbc_BoardVo);
		System.out.println();

		start();
	}
	
	// 삭제
	public void deleteBoard() {

		selectAllBoard();
		int cnt =service.deleteJdbc_Board(boardNumber);

		start();
	}
	
	// 전체 검색
	public void selectAllBoard() {
		
		List<Jdbc_BoardVO> jdbc_BoardList = service.getAllJdbc_BoardList();
		
		System.out.println("-------------------------------------------------------------");
		System.out.println("No	        제 목            작성자 	조회수");
		System.out.println("-------------------------------------------------------------");
		for(Jdbc_BoardVO jdbc_BoardVo : jdbc_BoardList) {
			System.out.print(jdbc_BoardVo.getBoard_no() + "\t");
			System.out.print(jdbc_BoardVo.getBoard_title() + "\t");
			System.out.print(jdbc_BoardVo.getBoard_writer() + "\t");
			System.out.print(jdbc_BoardVo.getBoard_cnt() + "\t");
			System.out.println();
		}
		System.out.println("-------------------------------------------------------------");
		
		
	}

}
