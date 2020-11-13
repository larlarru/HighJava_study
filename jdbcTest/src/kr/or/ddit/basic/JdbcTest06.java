package kr.or.ddit.basic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import kr.or.ddit.util.DBUtil;
import kr.or.ddit.util.DBUtil2;
import kr.or.ddit.util.DBUtil3;

/*
 * 회원을 관리하는 프로그램 작성하기
 * (DB시스템의 MYMEMBER테이블 이용)
 * 
 * 1. 아래 메뉴의 기능을 모두 구현한다.(CRUD 구현하기)
 * 2. '자료 추가'에서는 입력한 회원 ID가 중복되는지 여부를 검사해서 중복되면 다시 입력 받로록 한다.
 * 3. '자료 수정'이나 '자료 삭제'는 회원 ID를 입력 받아 삭제한다.
 * 4. '자료 수정'은 회원 ID를 제외한 전체 자료를 수정한다.
 * 
 * 메뉴예시)
 * --작업 선택--
 * 1. 자료 추가	--> insert (C)
 * 2. 자료 삭제	--> delete (D)
 * 3. 자료 수정	--> update (U)
 * 4. 전체 자료 출력	--> select (R)
 * 0. 작업 끝.
 * -------------
 * 작업 번호 >>
 * 
 */

public class JdbcTest06 {
	
	Scanner scan = new Scanner(System.in);
	
	Connection conn = null;
	Statement stmt = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	
	public static void main(String[] args) {
		
		new JdbcTest06().start();
	}
	
	public void start() {
		
		while(true) {
			menu();
			System.out.print("작업 번호 >>");
			int input = scan.nextInt();
			scan.nextLine();
			
			switch (input) {
			case 1:			// 추가
				insert();
				break;
			case 2:			// 삭제
				delete();
				break;
			case 3:			// 수정
				update();
				break;
			case 4:			// 전체 출력
				selectAll();
				break;

			case 0:
				System.out.println("프로그램을 종료합니다.");
				System.exit(0);
			default:
				System.out.println("다시 입력하세요.");
				break;
			}
		}
		
	}
	
	public void menu() {
		
		System.out.println("=========================================");
		System.out.println("	--	작업 선택	--");
		System.out.println("	1. 자료추가");
		System.out.println("	2. 자료 삭제");
		System.out.println("	3. 자료 수정");
		System.out.println("	4. 전체 자료 출력");
		System.out.println("	0. 작업 끝");
		System.out.println("=========================================");
		
	}
	
	private void insert() {
		
		try {
			
			conn = DBUtil.getConnection();
		
			System.out.print("ID 입력>>");
			String memId = scan.nextLine();
		
			String sql = "SELECT * FROM MYMEMBER WHERE MEM_ID = ?";
		
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, memId);
			
			rs = pstmt.executeQuery();	//행의 갯수만큼 인트반환 즉 1개면 1 2개면 2이렇게다.
			
			if(rs.next()) {
				if(rs.getString(1).equals(memId)) {
					System.out.println("이미 있는 ID입니다. 다시 입력해 주세요.");
					insert();
				}
			}
			
			System.out.print("이름 입력>>");
			String memName = scan.nextLine();
			
			System.out.print("전화번호 입력>>");
			String memTel = scan.nextLine();
			
			System.out.print("주소 입력>>");
			String memAddr = scan.nextLine();
			
			sql = "INSERT INTO MYMEMBER(MEM_ID, MEM_NAME,"
					+ "MEM_TEL, MEM_ADDR) "
					+ "VALUES( '" + memId + "', '" + memName  + "', '"
					+ memTel + "', '" + memAddr + "')";
			pstmt = conn.prepareStatement(sql);
			
			int cnt = pstmt.executeUpdate();
			
			if(cnt>0) {
				System.out.println("DB 추가 되셨습니다.");
			} else {
				System.out.println("DB 추가 실패");
			}
			
		} catch (SQLException e) {
			System.out.println("오류 : ");
			e.printStackTrace();
		}  finally {
			if(rs!=null) try { rs.close(); }catch(SQLException e) {}
			if(stmt!=null) try { stmt.close(); }catch(SQLException e) {}
			if(pstmt!=null) try { pstmt.close(); }catch(SQLException e) {}
			if(conn!=null) try { conn.close(); }catch(SQLException e) {}
		}
		start();	
	}
	
	public void delete() {
		
		try {
			
			conn = DBUtil.getConnection();
			
			String sql = "SELECT * FROM MYMEMBER";
			
			stmt = conn.createStatement();
			int cnt = stmt.executeUpdate(sql);
			if(cnt == 0) {
				System.out.println("삭제할 자료가 없습니다.");
				start();
			}
			rs = stmt.executeQuery(sql);
			
			
			System.out.println("============================");
			while(rs.next()) {
				System.out.println("ID : " + rs.getString("MEM_ID"));
				System.out.println("============================");
			}
			
			System.out.print("삭제할 ID입력 >>");
			String delId = scan.nextLine();
			
			sql = "DELETE MYMEMBER WHERE MEM_ID = ?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, delId);
			
			cnt = pstmt.executeUpdate();
			
			if(cnt > 0) {
				System.out.println("삭제되었습니다.");
			} else {
				System.out.println("삭제 실패하였습니다.");
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			if(rs!=null) try { rs.close(); }catch(SQLException e) {}
			if(stmt!=null) try { stmt.close(); }catch(SQLException e) {}
			if(pstmt!=null) try { pstmt.close(); }catch(SQLException e) {}
			if(conn!=null) try { conn.close(); }catch(SQLException e) {}
		}
		
		start();
		
	}
	
	public void update() {
		
		try {
			
			conn = DBUtil.getConnection();
			
			String sql = "SELECT * FROM MYMEMBER";
			
			stmt = conn.createStatement();
			int cnt = stmt.executeUpdate(sql);
			if(cnt == 0) {
				System.out.println("저장된 자료가 없습니다.");
				start();
			}
			rs = stmt.executeQuery(sql);
			
			
			System.out.println("============================");
			while(rs.next()) {
				System.out.println("Id : " + rs.getString("MEM_ID"));
				System.out.println("Name : " + rs.getString("MEM_NAME"));
				System.out.println("Tel : " + rs.getString("MEM_TEL"));
				System.out.println("Addr : " + rs.getString("MEM_ADDR"));
				System.out.println("============================");
			}
			
			System.out.print("수정할 Id 입력>>");
			String memId = scan.nextLine();
			
			System.out.print("수정할 이름 입력>>");
			String memName = scan.nextLine();
			
			System.out.print("수정할 전화번호 입력>>");
			String memTel = scan.nextLine();
			
			System.out.print("수정할 주소 입력>>");
			String memAddr = scan.nextLine();
			
			sql ="UPDATE MYMEMBER "
					+ "SET MEM_NAME = ?, MEM_TEL = ?, "
					+ "MEM_ADDR = ? WHERE MEM_ID = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, memName);
			pstmt.setString(2, memTel);
			pstmt.setString(3, memAddr);
			pstmt.setString(4, memId);
			
			cnt = pstmt.executeUpdate();
			
			if(cnt > 0) {
				System.out.println("자료 수정되었습니다.");
			} else {
				System.out.println("자료 수정 실패하셨습니다.");
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			if(rs!=null) try { rs.close(); }catch(SQLException e) {}
			if(stmt!=null) try { stmt.close(); }catch(SQLException e) {}
			if(pstmt!=null) try { pstmt.close(); }catch(SQLException e) {}
			if(conn!=null) try { conn.close(); }catch(SQLException e) {}
		}
		
		start();
	}
	
	public void selectAll() {
		
		try {
			
			//conn = DBUtil.getConnection();
			//conn = DBUtil2.getConnection();
			conn = DBUtil3.getConnection();
			
			String sql = "SELECT * FROM MYMEMBER";
			
			stmt = conn.createStatement();
			int cnt = stmt.executeUpdate(sql);
			if(cnt == 0) {
				System.out.println("저장된 자료가 없습니다.");
				start();
			}
			rs = stmt.executeQuery(sql);
			
			
			System.out.println("============================");
			while(rs.next()) {
				System.out.println("Id : " + rs.getString("MEM_ID"));
				System.out.println("Name : " + rs.getString("MEM_NAME"));
				System.out.println("Tel : " + rs.getString("MEM_TEL"));
				System.out.println("Addr : " + rs.getString("MEM_ADDR"));
				System.out.println("============================");
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			if(rs!=null) try { rs.close(); }catch(SQLException e) {}
			if(stmt!=null) try { stmt.close(); }catch(SQLException e) {}
			if(pstmt!=null) try { pstmt.close(); }catch(SQLException e) {}
			if(conn!=null) try { conn.close(); }catch(SQLException e) {}
		}
		
		start();
	}
	
	// 쓰지는 않지만 중복 검사를 위한 메서드 만든어둠
	// 매개변수로 회원ID를 받아서 해당 회원ID의 개수를 반환하는 메서드
	private int getMemberCount(String memId) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		int count = 0;
		
		try {
			
			conn = DBUtil.getConnection();
			
			String sql = "SELECT COUNT(*) cnt "
					+ "FROM MYMEMBER WHERE mem_id = ?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, memId);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				count = rs.getInt("cnt");
			}
			
		} catch (SQLException e) {
			count = 0;
			e.printStackTrace();
		} finally {
			if(rs!=null) try { rs.close(); }catch(SQLException e) {}
			if(stmt!=null) try { stmt.close(); }catch(SQLException e) {}
			if(pstmt!=null) try { pstmt.close(); }catch(SQLException e) {}
			if(conn!=null) try { conn.close(); }catch(SQLException e) {}
		}
		
		return count;
	}
	

}









