package kr.or.ddit.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

// JDBC드라이버를 뢷ㅇ하고, Connection객체를 생성하여 반환하는 메서드로 구성된 class

public class DBUtil2 {
	
	static Properties prop;
	
	static {
		
		prop = new Properties();
		
		// 읽어올 파일명을 이용한 File객체 생성
		File f = new File("res/dbinfo.properties");
		FileInputStream fin = null;
		
		try {
			
			fin = new FileInputStream(f);
			
			prop.load(fin);
			
			Class.forName(prop.getProperty("driver"));
			
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 로딩 실패");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("드라이버 로딩 실패");
			System.out.println("파일 입출력 오류");
			e.printStackTrace();
		}
	}
	
	public static Connection getConnection() {
		
		try {
			
			/*return DriverManager.getConnection(
					"jdbc:oracle:thin:@localhost:1521:xe",
					"larlarru",
					"java");*/
			
			return DriverManager.getConnection(
					prop.getProperty("url"),
					prop.getProperty("user"),
					prop.getProperty("pass"));
			
		} catch (SQLException e) {
			System.out.println("DB시스템 연결 실패!!!");
			e.printStackTrace();
			return null;
		}
	}
}
