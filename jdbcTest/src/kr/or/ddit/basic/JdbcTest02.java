package kr.or.ddit.basic;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

// 문제) 사용자로부터 LPROD_ID값을 입력받아 입력한 값보다 LPROD_ID값이 큰 자료들을 출력하시오.

public class JdbcTest02 {
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		int input=0;
		while(true) {
			System.out.print("숫자 입력>>");
			input = sc.nextInt();
			if(input >= 0) break;
		}
		
		// DB작업에 필요한 객체변수 선언
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		try {				
			
			// 1. 드라이버 로딩
			Class.forName("oracle.jdbc.driver.OracleDriver");
				
			// 2. DB연결 ==> Connection객체 생성
			conn = DriverManager.getConnection(
					"jdbc:oracle:thin:@localhost:1521:xe",
					"larlarru",
					"java");
			
			
			// 3-1. 실행할 SQL문 작성
			String sql = "SELECT * FROM LPROD WHERE LPROD_ID > " + input;
				
			// 3-2. Statement객체 생성 ==> Connection객체를 이용한다.
			stmt = conn.createStatement();
				
			// 4. SQL문을 DB서버로 전송해서 실행하고 결과를 얻어온다.
			//		(지금은 실행할 SQL문이 select문이기 때문에 결과가 ResultSet객체에 저장되어 반환된다.)
			rs = stmt.executeQuery(sql);
				
			// 5. 결과 처리하기 ==> 한 레코드씩 화면에 출력하기
			//		==> ResultSet에 저장된 데이터를 차례로 꺼내오려면 반복문과 next()메서드를 이용한다.
			System.out.println("== 처리 결과 출력 ==");
			System.out.println("--------------------------------------");
			/*
			 * rs.next() ==> ResultSet객체의 데이터를 가리키는 포인터를 다음 레코드 자리로 이동시키고
			 * 				 그 곳에 데이터가 있으면 true, 없으면 false를 반환한다.
			 */
			while(rs.next()) {
				// 포인터가 가리키는 곳의 데이터를 가져오는 방법
				// 형식1) rs.get자료형 이름("컬럼명")
				// 형식2) rs.get자료형 이름(컬럼번호) ==> 컬럼번호는 1부터 시작
				// 형식3) rs.get자료형 이름("컬럼의 alias명")
					
				System.out.println("LPROD_ID" + rs.getInt("LPROD_ID"));
				System.out.println("LPROD_GU" + rs.getString(2));
				System.out.println("LPROD_NM" + rs.getString("LPROD_NM"));
				System.out.println("--------------------------------------");
			}
				
			System.out.println("전체 자료 출력 끝...");
				
		} catch (SQLException e) {
				e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
				// 6. 사용했던 자원 반납하기
				if(rs!=null) try { rs.close();} catch (SQLException e) {}
				if(stmt!=null) try { stmt.close();} catch (SQLException e) {}
				if(conn!=null) try { conn.close();} catch (SQLException e) {}
		}
	}
}










