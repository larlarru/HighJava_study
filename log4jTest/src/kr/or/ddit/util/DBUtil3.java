package kr.or.ddit.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

// JDBC드라이버를 로딩하고, Connection객체를 생성하여 반환하는 메서드로 구성된 class

//ResourceBundle객체 이용하기
public class DBUtil3 {
	
	static Logger logger = Logger.getLogger(DBUtil3.class);
	
	static ResourceBundle bundle;
	
	static {
		
		bundle = ResourceBundle.getBundle("dbinfo");
		logger.info("ResurceBundle 객체 생성 - dbinfo.properties 파일 읽기");
		
		try {
			
			//Class.forName("oracle.jdbc.driver.OracleDriver");
			Class.forName(bundle.getString("driver"));
			logger.info("DB드라이버 로딩 성공");
			
		} catch (ClassNotFoundException e) {
			//System.out.println("드라이버 로딩 실패");
			logger.error("드라이버 로딩 실패");
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
					bundle.getString("url"),
					bundle.getString("user"),
					bundle.getString("pass"));
		} catch (SQLException e) {
			//System.out.println("DB시스템 연결 실패!!!");
			//e.printStackTrace();
			logger.error("DB시스템 연결 실패 ");
			return null;
		}
	}
}
