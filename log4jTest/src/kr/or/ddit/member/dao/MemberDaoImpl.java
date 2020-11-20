package kr.or.ddit.member.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import kr.or.ddit.member.vo.MemberVO;
import kr.or.ddit.util.DBUtil3;

public class MemberDaoImpl implements IMemberDao{
	
	private static final Logger logger = Logger.getLogger(MemberDaoImpl.class);
	
	// 1번
	private static MemberDaoImpl dao;
	
	// 2번
	private MemberDaoImpl() { }
	
	// 3번
	public static MemberDaoImpl getInstance() {
		
		if(dao==null) dao = new MemberDaoImpl();
		return dao;
	}

	@Override
	public int insertMember(MemberVO memVo) {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		int cnt = 0;
		
		try {
			conn = DBUtil3.getConnection();
			logger.info("Connection객체 생성...");
			
			String sql = "INSERT INTO MYMEMBER (mem_id, mem_name, mem_tel, mem_addr) "
					+ " VALUES (?, ?, ?, ?)";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, memVo.getMem_id());
			pstmt.setString(2, memVo.getMem_name());
			pstmt.setString(3, memVo.getMem_tel());
			pstmt.setString(4, memVo.getMem_addr());
			
			logger.info("PreparedStatement객체 생성");
			logger.info("실행 SQL문 ==> " + sql);
			logger.info("사용 데이터 : [" + memVo.getMem_id()
						+ ", " + memVo.getMem_name() + ", " 
						+ memVo.getMem_tel() + ", " + memVo.getMem_addr() + "]");
			
			cnt = pstmt.executeUpdate();			
			logger.info("SQL문 실행 성공~");
			
		} catch (SQLException e) {
			logger.error("SQL문 실행 실패~~" + e);
			cnt = 0;
			e.printStackTrace();
		} finally {
			if(pstmt!=null) try { pstmt.close(); logger.info("PreparedStatement객체 반납"); }catch(SQLException e) {}
			if(conn!=null) try { conn.close(); logger.info("Connection객체 반납"); }catch(SQLException e) {}
		}
		
		return cnt;
	}

	@Override
	public int deleteMember(String memId) {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		int cnt = 0;
		
		try {
			
			conn = DBUtil3.getConnection();
			logger.info("Connection객체 생성...");
			
			String sql = "DELETE MYMEMBER WHERE mem_id = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, memId);
			
			logger.info("PreparedStatement객체 생성");
			logger.info("실행 SQL문 ==> " + sql);
			logger.info("사용 데이터 : [" + memId + "]");
			
			cnt = pstmt.executeUpdate();
			logger.info("SQL문 실행 성공~");
			
		} catch (SQLException e) {
			cnt = 0;
			//e.printStackTrace();
			logger.info("SQL문 실행 실패" + e);
		} finally {
			if(pstmt!=null) try { pstmt.close(); logger.info("PreparedStatement객체 반납"); }catch(SQLException e) {}
			if(conn!=null) try { conn.close(); logger.info("Connection객체 반납"); }catch(SQLException e) {}
		}
		
		return cnt;
	}

	@Override
	public int updateMember(MemberVO memVo) {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		int cnt = 0;
		
		try {
			
			conn = DBUtil3.getConnection();
			logger.info("Connection객체 생성...");
			
			String sql ="UPDATE MYMEMBER SET mem_name = ?, "
					+ "mem_tel = ?, mem_addr = ? "
					+ "WHERE mem_id = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, memVo.getMem_name());
			pstmt.setString(2, memVo.getMem_tel());
			pstmt.setString(3, memVo.getMem_addr());
			pstmt.setString(4, memVo.getMem_id());

			logger.info("PreparedStatement객체 생성");
			logger.info("실행 SQL문 ==> " + sql);
			logger.info("사용 데이터 : [" + memVo.getMem_name() + ", "
						+ memVo.getMem_tel() + ", "
						+ memVo.getMem_addr() + ", " 
						+ memVo.getMem_id() + "]");
			
			cnt = pstmt.executeUpdate();
			logger.info("SQL문 실행 성공~");
		
		} catch (SQLException e) {
			cnt = 0;
			//e.printStackTrace();
			logger.info("SQL문 실행 실패" + e);
		} finally {
			if(pstmt!=null) try { pstmt.close(); logger.info("PreparedStatement객체 반납"); }catch(SQLException e) {}
			if(conn!=null) try { conn.close(); logger.info("Connection객체 반납"); }catch(SQLException e) {}
		}
		return cnt;
	}

	@Override
	public List<MemberVO> getAllMemberList() {
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		List<MemberVO> memList = null;	// MemberVO객체가 저장될 List객체 변수 선언
		
		try {
			
			conn = DBUtil3.getConnection();
			logger.info("Connection객체 생성...");
			
			String sql = "SELECT * FROM MYMEMBER";
			
			stmt = conn.createStatement();
			
			rs = stmt.executeQuery(sql);
			logger.info("ResultSet객체 생성");
			logger.info("실행 SQL문 ==> " + sql);
			
			memList = new ArrayList<>();
			logger.info("memList 생성");
			
			while(rs.next()) {
				
				MemberVO memVo = new MemberVO();	// MemberVO객체 생성
				logger.info("memVo 생성");
				
				// ResultSet객체의 데이터를 가져와 MemberVO객체에 넣는다.
				memVo.setMem_id(rs.getString("mem_id"));
				memVo.setMem_name(rs.getString("mem_name"));
				memVo.setMem_tel(rs.getString("mem_tel"));
				memVo.setMem_addr(rs.getString("mem_addr"));
				
				logger.info("받아온 데이터 : [" + rs.getString("mem_id") + ", "
							+ rs.getString("mem_name") + ", " 
							+ rs.getString("mem_tel") + ", " 
							+ rs.getString("mem_addr") + ", " +"]");
				
				memList.add(memVo);	// List에 MemberVO객체 추가
				logger.info("List에 MemberVO객체 추가");
			}
			logger.info("SQL문 실행 성공~");
		} catch (SQLException e) {
			memList = null;
			//e.printStackTrace();
			logger.info("SQL문 실행 실패" + e);
		} finally {
			if(rs!=null) try { rs.close(); logger.info("PreparedStatement객체 반납"); }catch(SQLException e) {}
			if(stmt!=null) try { stmt.close(); logger.info("Connection객체 반납"); }catch(SQLException e) {}
			if(conn!=null) try { conn.close(); logger.info("ResultSet객체 반납"); }catch(SQLException e) {}
		}
		
		return memList;
	}

	@Override
	public int getMemberCount(String memId) {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		int count = 0;   // 회원ID의 개수가 저장될 변수
		try {
			conn = DBUtil3.getConnection();
			logger.info("Connection객체 생성...");
			
			String sql = "SELECT COUNT(*) cnt FROM MYMEMBER WHERE mem_id = ?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, memId);
			logger.info("실행 SQL문 ==> " + sql);
			logger.info("사용 데이터 : [" + memId + "]");
			
			rs = pstmt.executeQuery();
			logger.info("SQL문 실행 성공~");
			
			if(rs.next()) {
				count = rs.getInt("cnt");
			}
			
		} catch (SQLException e) {
			count = 0;
			//e.printStackTrace();
			logger.info("SQL문 실행 실패" + e);
		} finally {
			if(rs!=null) try { rs.close();  logger.info("ResultSet객체 반납"); }catch(SQLException e) {}
			if(pstmt!=null) try { pstmt.close();  logger.info("PreparedStatement객체 반납"); }catch(SQLException e) {}
			if(conn!=null) try { conn.close(); logger.info("Connection객체 반납"); }catch(SQLException e) {}
		}
		
		return count;
	}

	@Override
	public int updateMemName(String memId, String memName) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int cnt = 0;
		
		try {
			
			conn = DBUtil3.getConnection();
			logger.info("Connection객체 생성...");
			
			String sql ="UPDATE MYMEMBER SET mem_name = ? "
					+ "WHERE mem_id = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, memName);
			pstmt.setString(2, memId);
			logger.info("PreparedStatement객체 생성");
			logger.info("실행 SQL문 ==> " + sql);
			logger.info("사용 데이터 : [" + memName + ", " + memId + "]");
			
			cnt = pstmt.executeUpdate();
			logger.info("SQL문 실행 성공~");
		
		} catch (SQLException e) {
			cnt = 0;
			//e.printStackTrace();
			logger.info("SQL문 실행 실패" + e);
		} finally {
			if(pstmt!=null) try { pstmt.close(); logger.info("PreparedStatement객체 반납"); }catch(SQLException e) {}
			if(conn!=null) try { conn.close(); logger.info("Connection객체 반납"); }catch(SQLException e) {}
		}
		return cnt;
	}

	@Override
	public int updateMemTel(String memId, String memTel) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int cnt = 0;
		
		try {
			
			conn = DBUtil3.getConnection();
			logger.info("Connection객체 생성...");
			
			String sql ="UPDATE MYMEMBER SET mem_tel = ? "
					+ "WHERE mem_id = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, memTel);
			pstmt.setString(2, memId);
			logger.info("PreparedStatement객체 생성");
			logger.info("실행 SQL문 ==> " + sql);
			logger.info("사용 데이터 : [" + memTel + ", " + memId + "]");
			
			cnt = pstmt.executeUpdate();
			logger.info("SQL문 실행 성공~");
			
		} catch (SQLException e) {
			cnt = 0;
			//e.printStackTrace();
			logger.info("SQL문 실행 실패" + e);
		} finally {
			if(pstmt!=null) try { pstmt.close(); logger.info("PreparedStatement객체 반납"); }catch(SQLException e) {}
			if(conn!=null) try { conn.close(); logger.info("Connection객체 반납"); }catch(SQLException e) {}
		}
		return cnt;
	}

	@Override
	public int updateMemAddr(String memId, String memAddr) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int cnt = 0;
		
		try {
			
			conn = DBUtil3.getConnection();
			logger.info("Connection객체 생성...");
			
			String sql ="UPDATE MYMEMBER SET mem_addr = ? "
					+ "WHERE mem_id = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, memAddr);
			pstmt.setString(2, memId);
			
			logger.info("PreparedStatement객체 생성");
			logger.info("실행 SQL문 ==> " + sql);
			logger.info("사용 데이터 : [" + memAddr + ", " + memId + "]");
			
			cnt = pstmt.executeUpdate();
			logger.info("SQL문 실행 성공~");
			
		} catch (SQLException e) {
			cnt = 0;
			//e.printStackTrace();
			logger.info("SQL문 실행 실패" + e);
		} finally {
			if(pstmt!=null) try { pstmt.close(); logger.info("PreparedStatement객체 반납"); }catch(SQLException e) {}
			if(conn!=null) try { conn.close(); logger.info("Connection객체 반납"); }catch(SQLException e) {}
		}
		return cnt;
	}

	@Override
	public int updateMeber2(Map<String, String> paramMap) {
		// key값 ==> 회원ID(memId), 변경할 컬럼명(field), 변경할데이터(data)
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		int cnt = 0;
		
		try {

			conn = DBUtil3.getConnection();
			logger.info("Connection객체 생성...");

			String sql= "UPDATE MYMEMBER SET "  
					+ paramMap.get("field")
					+ " = ? WHERE mem_id = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, paramMap.get("data"));
			pstmt.setString(2, paramMap.get("memId"));
			
			logger.info("PreparedStatement객체 생성");
			logger.info("실행 SQL문 ==> " + sql);
			logger.info("사용 데이터 : [" + paramMap.get("data") + ", " 
						+ paramMap.get("memId") + "]");
			
			cnt = pstmt.executeUpdate();
			logger.info("SQL문 실행 성공~");
			
		} catch (SQLException e) {
			cnt = 0;
			//e.printStackTrace();
			logger.info("SQL문 실행 실패" + e);
		} finally {
			if(pstmt!=null) try { pstmt.close(); logger.info("PreparedStatement객체 반납"); }catch(SQLException e) {}
			if(conn!=null) try { conn.close(); logger.info("Connection객체 반납"); }catch(SQLException e) {}
		}
		
		return cnt;
	}

}
