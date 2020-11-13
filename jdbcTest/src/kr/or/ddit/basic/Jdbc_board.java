package kr.or.ddit.basic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import kr.or.ddit.util.DBUtil3;

public class Jdbc_board {
	
Scanner scan = new Scanner(System.in);
	
	Connection conn = null;
	Statement stmt = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	int boardNumber=0;
	
	public static void main(String[] args) {
		new Jdbc_board().start();		
	}
	
	public void start() {
		
		while(true) {
			//System.out.println("-------------------------------------------------------------");
			//System.out.println("No	        제 목            작성자 	조회수");
			selectAllBoard();
			System.out.println("메뉴 : 1. 새글작성     2. 게시글보기    3. 검색    0. 작업끝");
			System.out.print("작업선택 >>");
			int input = scan.nextInt();
			scan.nextLine();
			
			switch(input) {
				case 1:	// 새글작성
					createBoard();
					break;
				case 2:	// 게시글보기
					showBoard();
					break;
				case 3:	// 검색
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
		
		try {
			
			conn = DBUtil3.getConnection();
			
			System.out.print("게시글 제목 입력>>");
			String boardTitle = scan.nextLine();
			
			System.out.print("작성자 입력>>");
			String boardWriter = scan.nextLine();
			
			System.out.print("내용 입력>>");
			String boardContent = scan.nextLine();
			
			String sqlSq = "SELECT board_seq.nextVal FROM DUAL"; 
			
			pstmt = conn.prepareStatement(sqlSq);
			
			rs = pstmt.executeQuery();
			int sqNum = 0;
			if(rs.next()) {
				sqNum = rs.getInt(1);
			}else {
				System.out.println("등록에 오류가 발생했습니다.");
				return;
			}
			/*			String sql = "INSERT INTO JDBC_BOARD(BOARD_NO, BOARD_TITLE, " 
					+ "BOARD_WRITER, BOARD_DATE, BOARD_CNT, BOARD_CONTENT) " 
					+ "VALUES((SELECT NVL(MAX(BOARD_NO),0)+1 FROM JDBC_BOARD), " 
					+ " ?, ?, SYSDATE, 0, ?)";
			 */			
			
			String sql = "INSERT INTO JDBC_BOARD(BOARD_NO, BOARD_TITLE, " 
					+ "BOARD_WRITER, BOARD_DATE, BOARD_CNT, BOARD_CONTENT) " 
					+ "VALUES(?, ?, ?, SYSDATE, 0, ?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, sqNum);
			pstmt.setString(2, boardTitle);
			pstmt.setString(3, boardWriter);
			pstmt.setString(4, boardContent);
			
			int cnt = pstmt.executeUpdate();
			
			if(cnt>0) {
				System.out.println("새글이 추가되었습니다.");
			} else {
				System.out.println("새글이 추가 실패하였습니다.");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(rs!=null) try { rs.close(); }catch(SQLException e) {}
			if(stmt!=null) try { stmt.close(); }catch(SQLException e) {}
			if(pstmt!=null) try { pstmt.close(); }catch(SQLException e) {}
			if(conn!=null) try { conn.close(); }catch(SQLException e) {}
		}
		
		start();
	}
	
	// 게시글 보기
	public void showBoard() {
		boardNumber=0;
		
		try {
			conn = DBUtil3.getConnection();
			
			System.out.print("보기를 원하는 게시물 번호 입력 >>");
			boardNumber = scan.nextInt();
			
			String sql = "UPDATE JDBC_BOARD SET "
					+ "BOARD_CNT = (SELECT NVL(MAX(BOARD_CNT),0)+1 "
					+ "FROM JDBC_BOARD WHERE BOARD_NO = ?) "
					+ "WHERE BOARD_NO = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, boardNumber);
			pstmt.setInt(2, boardNumber);
			rs = pstmt.executeQuery();
			
			sql ="SELECT * FROM JDBC_BOARD WHERE BOARD_NO = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, boardNumber);
			
			int cnt = pstmt.executeUpdate();
			rs = pstmt.executeQuery();
			
			if(cnt >= 0) {
				System.out.println("============================");
				while(rs.next()) {
					System.out.println("제  목 : " + rs.getString("BOARD_TITLE"));
					System.out.println("작성자 : " + rs.getString("BOARD_WRITER"));
					System.out.println("내  용 : " + rs.getString("BOARD_CONTENT"));
					System.out.println("작성일 : " + rs.getDate("BOARD_DATE"));
					System.out.println("조회수 : " + rs.getInt("BOARD_CNT"));
					System.out.println("============================");
				}
				
			} else {
				System.out.println("해당 게시글이 없습니다.");
			}
			
			while(true) {
				
				System.out.println("============================");
				System.out.println("메뉴 : 1. 수정    2. 삭제    3. 리스트로 가기");
				System.out.println("============================");
				
				System.out.print("작업선택 >>");
				int input = scan.nextInt();
				scan.nextLine();
				
				switch(input) {
					case 1:	// 수정
						updateBoard();
						break;
					case 2:	// 삭제
						deleteBoard();
						break;
					case 3:	// 리스트로 가기
						start();
						break;
					default:
						System.out.println("다시 입력하세요.");
						break;
				}
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			if(rs!=null) try { rs.close(); }catch(SQLException e) {}
			if(stmt!=null) try { stmt.close(); }catch(SQLException e) {}
			if(pstmt!=null) try { pstmt.close(); }catch(SQLException e) {}
			if(conn!=null) try { conn.close(); }catch(SQLException e) {}
		}
		
		start();
		
		
	}
	
	// 수정
	public void updateBoard() {
		
		try {
			//boardNumber
			conn = DBUtil3.getConnection();
			
			System.out.print("수정할 제목 입력 >>");
			String boardTitle = scan.nextLine();
			
			System.out.print("수정할 내용 입력>>");
			String boardContent = scan.nextLine();
			
			String sql ="UPDATE JDBC_BOARD SET "
					+ "BOARD_TITLE = ?, "
					+ "BOARD_CONTENT = ? " + 
					"WHERE BOARD_NO = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, boardTitle);
			pstmt.setString(2, boardContent);
			pstmt.setInt(3, boardNumber);
			
			int cnt = pstmt.executeUpdate();
			rs = pstmt.executeQuery(sql);
			
			if(cnt >= 0) {
				System.out.println(boardNumber + "번 글이 수정되었습니다.");
			} else {
				System.out.println(boardNumber + "번 글 수정 실패하였습니다.");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(rs!=null) try { rs.close(); }catch(SQLException e) {}
			if(stmt!=null) try { stmt.close(); }catch(SQLException e) {}
			if(pstmt!=null) try { pstmt.close(); }catch(SQLException e) {}
			if(conn!=null) try { conn.close(); }catch(SQLException e) {}
		}
		
		start();
	}
	
	// 삭제
	public void deleteBoard() {
		
		selectAllBoard();
		
		try {
			//boardNumber
			conn = DBUtil3.getConnection();
			
			String sql ="DELETE JDBC_BOARD WHERE BOARD_NO = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, boardNumber);
			
			int cnt = pstmt.executeUpdate();
			rs = pstmt.executeQuery(sql);
			
			if(cnt >= 0) {
				System.out.println(boardNumber + "번글이 삭제되었습니다.");
			} else {
				System.out.println(boardNumber + "번글 삭제 실패하였습니다.");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(rs!=null) try { rs.close(); }catch(SQLException e) {}
			if(stmt!=null) try { stmt.close(); }catch(SQLException e) {}
			if(pstmt!=null) try { pstmt.close(); }catch(SQLException e) {}
			if(conn!=null) try { conn.close(); }catch(SQLException e) {}
		}
		
		start();
	}
	
	// 검색
	public void selectBoard() {
		
		System.out.print("검색할 제목 입력 >>");
		String searchName = scan.nextLine();
		try {
			
			conn = DBUtil3.getConnection();
			
			
			String sql ="SELECT * FROM JDBC_BOARD "
					+ "WHERE BOARD_TITLE LIKE '%" + searchName + "%'";
/*			String sql ="SELECT * FROM JDBC_BOARD "
					+ "WHERE BOARD_TITLE LIKE ?%";
*/			pstmt = conn.prepareStatement(sql);
			//pstmt.setString(1, searchName);
			
			int cnt = pstmt.executeUpdate();
			rs = pstmt.executeQuery(sql);
			System.out.println(cnt);
			
			if(cnt > 0) {
				System.out.println("============================");
				while(rs.next()) {

					System.out.print(rs.getInt("BOARD_NO") + "\t");
					System.out.print(rs.getString("BOARD_TITLE") + "\t");
					System.out.print(rs.getString("BOARD_WRITER") + "\t");
					System.out.print(rs.getInt("BOARD_CNT") + "\t");
					System.out.println();
				}
				
				while(true) {
					System.out.println("-------------------------------------------------------------");
					System.out.println("메뉴 : 1. 새글작성     2. 게시글보기    3. 검색    0. 작업끝");
					System.out.print("작업선택 >>");
					int input = scan.nextInt();
					scan.nextLine();
					
					switch(input) {
						case 1:	// 새글작성
							createBoard();
							break;
						case 2:	// 게시글보기
							showBoard();
							break;
						case 3:	// 검색
							selectBoard();
							break;
						case 0:
							System.out.println("프로그램을 종료합니다.");
							System.exit(0);
						default:
							break;
					}
				}
				
			} else {
				System.out.println("해당 게시글이 없습니다.");
				selectAllBoard();
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			if(rs!=null) try { rs.close(); }catch(SQLException e) {}
			if(stmt!=null) try { stmt.close(); }catch(SQLException e) {}
			if(pstmt!=null) try { pstmt.close(); }catch(SQLException e) {}
			if(conn!=null) try { conn.close(); }catch(SQLException e) {}
		}
		
		//start();
	}
	
	// 게시글 전체 검색
	public void selectAllBoard() {

		try {

			conn = DBUtil3.getConnection();

			String sql = "SELECT * FROM JDBC_BOARD ORDER BY BOARD_NO DESC";
			stmt = conn.createStatement();
			int cnt = stmt.executeUpdate(sql);
			if(cnt == 0) {
				System.out.println("저장된 자료가 없습니다.");
				start();
			}
			rs = stmt.executeQuery(sql);
			System.out.println("-------------------------------------------------------------");
			System.out.println("No	        제 목            작성자 	조회수");
			System.out.println("-------------------------------------------------------------");
			while (rs.next()) {
				System.out.print(rs.getInt("BOARD_NO") + "\t");
				System.out.print(rs.getString("BOARD_TITLE") + "\t");
				System.out.print(rs.getString("BOARD_WRITER") + "\t");
				System.out.print(rs.getInt("BOARD_CNT") + "\t");
				System.out.println();
			}
			System.out.println("-------------------------------------------------------------");
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(rs!=null) try { rs.close(); }catch(SQLException e) {}
			if(stmt!=null) try { stmt.close(); }catch(SQLException e) {}
			if(pstmt!=null) try { pstmt.close(); }catch(SQLException e) {}
			if(conn!=null) try { conn.close(); }catch(SQLException e) {}
		}
	}
}





















