package kr.or.ddit.basic;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import kr.or.ddit.util.DBUtil;

/*
 * LPROD테이블에 새로운 데이터 추가하기
 * 
 * 추가할 데이터 중 LPROD_GU와 LPROD_NM은 직접 입력 받아서 처리하고,
 * 입력받은 LPROD_GU가 이미 등록되어 있으면 다시 입력받아서 처리한다.
 * 그리고 LPROD_ID값은 현재의 LPROD_ID값 중 제일 큰 값보다 1 증가된 값으로 한다.
 */

public class JdbcTest05 {
	
	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		
		Connection conn = null;
		Statement stmt = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String lprodGu;
		String lprodNm;
		
		try {
			
			/*Class.forName("oracle.jdbc.driver.OracleDriver");
			
			conn = DriverManager.getConnection(
					"jdbc:oracle:thin:@localhost:1521:xe",
					"larlarru",
					"java");*/
			conn = DBUtil.getConnection();
			
			while(true) {
				System.out.print("LPROD_GU 입력(예 : P101) : ");
				lprodGu = sc.nextLine(); 
					
				String sql = "SELECT COUNT(*)" + 
						"FROM LPROD " + 
						"WHERE LPROD_GU = ?";
					
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, lprodGu);
				
				rs = pstmt.executeQuery();
				// if(rs.next()) // 이렇게 해도된다.
				rs.next();
				
				if(rs.getInt(1) > 0) {
					System.out.println("이미 있습니다. 다시 입력하세요.");
				} else {
					break;
				}
			}
						
			System.out.print("LPROD_NM 입력(예 : 화장품) : ");
			lprodNm = sc.nextLine();
						
			String sql ="INSERT INTO LPROD(LPROD_ID, LPROD_GU, LPROD_NM) "
					+ "VALUES( ( SELECT NVL(MAX(LPROD_ID),0) + 1 FROM LPROD ), ?, ? )";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, lprodGu);
			pstmt.setString(2, lprodNm);
			
			System.out.println("입력되었습니다.");
			
			int cnt = pstmt.executeUpdate();
			if(cnt>0) {
				System.out.println("DB 추가 되셨습니다.");
			} else {
				System.out.println("DB 추가 실패");
			}
			
			sql = "SELECT * FROM LPROD";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			
			System.out.println("LPROD테이블 내용물");
			System.out.println("=========================");
			
			while(rs.next()) {
				System.out.println("LPROD_ID : " + rs.getInt("LPROD_ID"));
				System.out.println("LPROD_GU" + rs.getString("LPROD_GU"));
				System.out.println("LPROD_NM" + rs.getString("LPROD_NM"));
				System.out.println("=========================");
			}
			
			
				
			
		} catch (SQLException e) {
			e.printStackTrace();
		} /*catch (ClassNotFoundException e) {
			e.printStackTrace();
		}*/ finally {
			if(stmt!=null) try { stmt.close(); } catch(SQLException e) {}
			if(pstmt!=null) try { pstmt.close(); } catch(SQLException e) {}
			if(conn!=null) try { conn.close(); } catch(SQLException e) {}
		}
		
		
		
	}

}
