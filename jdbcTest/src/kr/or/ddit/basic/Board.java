package kr.or.ddit.basic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import kr.or.ddit.util.DBUtil3;

public class Board {

	int check = 0;
	Scanner sc = new Scanner(System.in);
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	
	public static void main(String[] args) {
		new Board().start();
	}

	private void start() {
		
		while(true) {
		if(check==0) {
			boardDisplayAll();
		}
		
		System.out.println("매뉴 : 1. 새글작성   2. 게시글보기   3. 검색   0. 작업끝");
		System.out.print("작업선택 >> ");
		
		int input = Integer.parseInt(sc.nextLine());
		
		switch (input) {
		case 1:
			boardInsert();
			break;
		case 2:
			boardDisplay();
			break;
		case 3:
			boardView();
			break;
		case 0:
			System.out.println("시스템을 종료합니다.");
			System.exit(0);
			break;

		default:
			System.out.println("해당 메뉴만 선택하세요");
			break;
		}
		
		}
		
		
	}

	
	
	
	private void boardView() {
		try {
			conn = DBUtil3.getConnection();
			
			System.out.print("검색할 제목을 입력하세요 : ");
			String title = sc.nextLine();
			
			if(title.equals("")) {
				check = 0;
				return;
			}else {
			
			String sql = "SELECT * FROM jdbc_board WHERE board_title LIKE '%"+title+"%'  ORDER BY board_no DESC";
			
			pstmt = conn.prepareStatement(sql);
		
			
			
			rs = pstmt.executeQuery();
			
			System.out.println("----------------------------------------");
			System.out.println("NO\t제 목\t\t작성자\t조회수");
			System.out.println("----------------------------------------");
			while (rs.next()) {
				System.out.print(rs.getInt("board_no")+"\t");
				System.out.print(rs.getString("board_title")+"\t\t");
				System.out.print(rs.getString("board_writer")+"\t");
				System.out.println(rs.getInt("board_cnt"));
			}
			System.out.println("----------------------------------------");
			check = 1;
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			if(rs!=null) try {rs.close();} catch(SQLException e) {}
			if(pstmt!=null) try {pstmt.close();} catch(SQLException e) {}
			if(conn!=null) try {conn.close();} catch(SQLException e) {}
		}
		
	}

	private void boardDisplay() {
		
		int num = 0;
		try {
			
			conn = DBUtil3.getConnection();
			
			System.out.print("열람을 원하는 게시물 번호를 입력하세요 : ");
			num = Integer.parseInt(sc.nextLine());
			
			String sql = "SELECT COUNT(*) bno FROM jdbc_board WHERE board_no = ?";
			
			pstmt = conn.prepareStatement(sql); // 컴파일과정

			pstmt.setInt(1, num);

			rs = pstmt.executeQuery();

			if(rs.next()) {
				if (rs.getInt("bno") == 0) {
					System.out.println("없는 게시글 번호입니다");
					return;
				} 
			}
			
			
			String sqlcount = "UPDATE jdbc_board SET board_cnt = (SELECT NVL(MAX(board_cnt),0)+1 FROM jdbc_board WHERE board_no = ?) WHERE board_no = ?";
			
			pstmt = conn.prepareStatement(sqlcount); 
			
			pstmt.setInt(1, num);
			pstmt.setInt(2, num);
			
			pstmt.executeUpdate();
		
			String sqldis = "SELECT * FROM jdbc_board WHERE board_no = ?";
			
			pstmt = conn.prepareStatement(sqldis); 

			pstmt.setInt(1, num);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
			System.out.println(rs.getInt("board_no")+"번글 내용");
			System.out.println("---------------------------------");
			System.out.println("- 제 목 : "+rs.getString("board_title"));
			System.out.println("- 작성자 : "+rs.getString("board_writer"));
			System.out.println("- 내 용 : "+rs.getString("board_content"));
			System.out.println("- 작성일 : "+rs.getString("board_date"));
			System.out.println("- 조회수 : "+rs.getString("board_cnt"));
			System.out.println("---------------------------------");
			}else {
				System.out.println("게시글 불러오기 실패");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(rs!=null) try {rs.close();} catch(SQLException e) {}
			if(pstmt!=null) try {pstmt.close();} catch(SQLException e) {}
			if(conn!=null) try {conn.close();} catch(SQLException e) {}
		}
		
		System.out.println("메뉴 : 1. 수정   2. 삭제   3. 리스트로 가기");
		System.out.print("작업 선택 >> ");
		
		int input = Integer.parseInt(sc.nextLine());
		
		switch (input) {
		case 1:
			boardUpdate(num);
			break;
		case 2:
			boardDelete(num);
			break;
		case 3:
			check = 0;
			break;

		default:
			break;
		}
		
	}

	
	private void boardDelete(int num) {
		try {
			conn = DBUtil3.getConnection();
			
			String sql = "DELETE jdbc_board WHERE board_no = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, num);
			
			int cnt = pstmt.executeUpdate();
			System.out.println();
			if(cnt>0) {
				
				System.out.println("게시글이 삭제 되었습니다.");
			}else {
				System.out.println("게시글 삭제에 실패했습니다.");
			}
			System.out.println();
			
			check = 0;
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(rs!=null) try {rs.close();} catch(SQLException e) {}
			if(pstmt!=null) try {pstmt.close();} catch(SQLException e) {}
			if(conn!=null) try {conn.close();} catch(SQLException e) {}
		}
		
	}

	private void boardUpdate(int num) {
		try {
			conn = DBUtil3.getConnection();
			
			System.out.print("- 제 목 : ");
			String title = sc.nextLine();
			
			System.out.print("- 내 용 : ");
			String coment = sc.nextLine();
			
			String sql = "UPDATE jdbc_board SET board_title = ?, board_content = ? WHERE board_no =? ";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, title);
			pstmt.setString(2, coment);
			pstmt.setInt(3, num);
			
			int cnt = pstmt.executeUpdate();
			System.out.println();
			if(cnt>0) {
				
				System.out.println("게시글이 수정되었습니다.");
			}else {
				System.out.println("게시글 수정에 실패했습니다.");
			}
			System.out.println();
			
			check = 0;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(rs!=null) try {rs.close();} catch(SQLException e) {}
			if(pstmt!=null) try {pstmt.close();} catch(SQLException e) {}
			if(conn!=null) try {conn.close();} catch(SQLException e) {}
		}
	}

	private void boardInsert() {
		System.out.print("- 제 목 : ");
		String title = sc.nextLine();
		
		System.out.print("- 작성자 : ");
		String name = sc.nextLine();
		
		System.out.print("- 내 용 : ");
		String coment = sc.nextLine();
		
		try {
			
			conn = DBUtil3.getConnection();
			
			String sqlSq = "SELECT board_seq.nextVal FROM DUAL"; 
			
			pstmt = conn.prepareStatement(sqlSq);
			
			rs = pstmt.executeQuery();
			int num = 0;
			if(rs.next()) {
				num = rs.getInt(1);
			}else {
				System.out.println("등록에 오류가 발생했습니다.");
				return;
			}
			
			
			
			String sql = "INSERT INTO jdbc_board VALUES(?,?,?,sysdate,0,?)";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, num);
			pstmt.setString(2, title);
			pstmt.setString(3, name);
			pstmt.setString(4, coment);
			
			int cnt = pstmt.executeUpdate();
			System.out.println();
			if(cnt>0) {
				System.out.println("게시글이 작성되었습니다.");
			}else {
				System.out.println("게시글 작성 실패!");
			}
			System.out.println();
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(rs!=null) try {rs.close();} catch(SQLException e) {}
			if(pstmt!=null) try {pstmt.close();} catch(SQLException e) {}
			if(conn!=null) try {conn.close();} catch(SQLException e) {}
		}
		
		
	}

	private void boardDisplayAll() {

		try {

			conn = DBUtil3.getConnection();

			String sql = "SELECT * FROM jdbc_board ORDER BY board_no DESC";

			pstmt = conn.prepareStatement(sql);

			rs = pstmt.executeQuery();

			System.out.println("----------------------------------------");
			System.out.println("NO\t제 목\t\t작성자\t조회수");
			System.out.println("----------------------------------------");
			while (rs.next()) {
				System.out.print(rs.getInt("board_no") + "\t");
				System.out.print(rs.getString("board_title") + "\t\t");
				System.out.print(rs.getString("board_writer") + "\t");
				System.out.println(rs.getInt("board_cnt"));
			}
			System.out.println("----------------------------------------");

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(rs!=null) try {rs.close();} catch(SQLException e) {}
			if(pstmt!=null) try {pstmt.close();} catch(SQLException e) {}
			if(conn!=null) try {conn.close();} catch(SQLException e) {}
		}
	}
	
}
