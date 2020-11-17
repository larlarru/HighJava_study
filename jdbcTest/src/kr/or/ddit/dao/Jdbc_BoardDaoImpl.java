package kr.or.ddit.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import kr.or.ddit.util.DBUtil3;
import kr.or.ddit.vo.Jdbc_BoardVO;

public class Jdbc_BoardDaoImpl implements Jdbc_BoardDao{
	
	private static Jdbc_BoardDaoImpl dao;
	
	private Jdbc_BoardDaoImpl() {}
	
	public static Jdbc_BoardDaoImpl getInstance() {
		
		if(dao==null) dao = new Jdbc_BoardDaoImpl();
		return dao;
	}

	@Override
	public int insertJdbc_Board(Jdbc_BoardVO jdbc_BoardVo) {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int cnt = 0;
		int sqNum=0;
		
		try {
			
			conn = DBUtil3.getConnection();
			
			String sqlSq = "SELECT board_seq.nextVal FROM DUAL"; 
			
			pstmt = conn.prepareStatement(sqlSq);
			
			rs = pstmt.executeQuery();
			if(rs.next()) {
				sqNum = rs.getInt(1);
				pstmt.close();
			}else {
				System.out.println("등록에 오류가 발생했습니다.");
				return cnt;
			}
			
			String sql = "INSERT INTO JDBC_BOARD(BOARD_NO, BOARD_TITLE, " 
					+ "BOARD_WRITER, BOARD_DATE, BOARD_CNT, BOARD_CONTENT) " 
					+ "VALUES(?, ?, ?, SYSDATE, 0, ?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, sqNum);
			pstmt.setString(2, jdbc_BoardVo.getBoard_title());
			pstmt.setString(3, jdbc_BoardVo.getBoard_writer());
			pstmt.setString(4, jdbc_BoardVo.getBoard_content());
			
			cnt = pstmt.executeUpdate();
			
			
		} catch (Exception e) {
			cnt = 0;
			e.printStackTrace();
		} finally {
			if(rs!=null) try { rs.close(); }catch(SQLException e) {}
			if(pstmt!=null) try { pstmt.close(); }catch(SQLException e) {}
			if(conn!=null) try { conn.close(); }catch(SQLException e) {}
		}
		
		return cnt;
	}

	@Override
	public int deleteJdbc_Board(int board_no) {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int cnt = 0;
		
		try {
			//boardNumber
			conn = DBUtil3.getConnection();
			
			String sql ="DELETE JDBC_BOARD WHERE BOARD_NO = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, board_no);
			
			cnt = pstmt.executeUpdate();
			rs = pstmt.executeQuery(sql);
			
			if(cnt >= 0) {
				System.out.println(board_no + "번글이 삭제되었습니다.");
			} else {
				System.out.println(board_no + "번글 삭제 실패하였습니다.");
			}
			
		} catch (Exception e) {
			cnt = 0;
			e.printStackTrace();
		} finally {
			if(rs!=null) try { rs.close(); }catch(SQLException e) {}
			if(pstmt!=null) try { pstmt.close(); }catch(SQLException e) {}
			if(conn!=null) try { conn.close(); }catch(SQLException e) {}
		}
		
		return cnt;
	}

	@Override
	public int updateJdbc_Board(int board_no, Jdbc_BoardVO jdbc_BoardVo) {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int cnt = 0;
		
		try {
			//boardNumber
			conn = DBUtil3.getConnection();
			
			String sql ="UPDATE JDBC_BOARD SET "
					+ "BOARD_TITLE = ?, "
					+ "BOARD_CONTENT = ? " + 
					"WHERE BOARD_NO = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, jdbc_BoardVo.getBoard_title());
			pstmt.setString(2, jdbc_BoardVo.getBoard_content());
			pstmt.setInt(3, board_no);
			
			cnt = pstmt.executeUpdate();
			rs = pstmt.executeQuery(sql);
			
			if(cnt >= 0) {
				System.out.println(board_no + "번 글이 수정되었습니다.");
			} else {
				System.out.println(board_no + "번 글 수정 실패하였습니다.");
			}
			
		} catch (Exception e) {
			cnt=0;
			e.printStackTrace();
		} finally {
			if(rs!=null) try { rs.close(); }catch(SQLException e) {}
			if(pstmt!=null) try { pstmt.close(); }catch(SQLException e) {}
			if(conn!=null) try { conn.close(); }catch(SQLException e) {}
		}
		
		return cnt;
	}

	@Override
	public List<Jdbc_BoardVO> getAllJdbc_BoardList() {
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		int cnt = 0;
		
		List<Jdbc_BoardVO> jdbc_BoardList = null;
		
		try {

			conn = DBUtil3.getConnection();

			String sql = "SELECT * FROM JDBC_BOARD ORDER BY BOARD_NO DESC";
			stmt = conn.createStatement();
			cnt = stmt.executeUpdate(sql);
			if(cnt == 0) {
				System.out.println("저장된 자료가 없습니다.");
				return jdbc_BoardList;
			}
			rs = stmt.executeQuery(sql);
			
			jdbc_BoardList = new ArrayList<>();
			
			while (rs.next()) {
				Jdbc_BoardVO jdbc_BoardVo = new Jdbc_BoardVO();
				
				jdbc_BoardVo.setBoard_no(rs.getInt("BOARD_NO"));
				jdbc_BoardVo.setBoard_title(rs.getString("BOARD_TITLE"));
				jdbc_BoardVo.setBoard_writer(rs.getString("BOARD_WRITER"));
				jdbc_BoardVo.setBoard_cnt(rs.getInt("BOARD_CNT"));
				
				jdbc_BoardList.add(jdbc_BoardVo);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(rs!=null) try { rs.close(); }catch(SQLException e) {}
			if(stmt!=null) try { stmt.close(); }catch(SQLException e) {}
			if(conn!=null) try { conn.close(); }catch(SQLException e) {}
		}
		
		return jdbc_BoardList;
	}

	@Override
	public List<Jdbc_BoardVO> getJdbc_BoardList(String board_title) {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int cnt = 0;
		
		List<Jdbc_BoardVO> jdbc_BoardList = null;
		
		try {

			conn = DBUtil3.getConnection();

			String sql ="SELECT * FROM JDBC_BOARD "
					+ "WHERE BOARD_TITLE LIKE '%" + board_title + "%'";
			pstmt = conn.prepareStatement(sql);
			cnt = pstmt.executeUpdate(sql);
			if(cnt == 0) {
				System.out.println("저장된 자료가 없습니다.");
				jdbc_BoardList = null;
				return jdbc_BoardList;
			}
			rs = pstmt.executeQuery();
			
			jdbc_BoardList = new ArrayList<>();
			
			System.out.println("검색된 자료들 : ");
			System.out.println();
			
			while (rs.next()) {
				Jdbc_BoardVO jdbc_BoardVo = new Jdbc_BoardVO();
				jdbc_BoardVo.setBoard_no(rs.getInt("BOARD_NO"));
				jdbc_BoardVo.setBoard_title(rs.getString("BOARD_TITLE"));
				jdbc_BoardVo.setBoard_writer(rs.getString("BOARD_WRITER"));
				jdbc_BoardVo.setBoard_cnt(rs.getInt("BOARD_CNT"));
				jdbc_BoardList.add(jdbc_BoardVo);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(rs!=null) try { rs.close(); }catch(SQLException e) {}
			if(pstmt!=null) try { pstmt.close(); }catch(SQLException e) {}
			if(conn!=null) try { conn.close(); }catch(SQLException e) {}
		}
		
		return jdbc_BoardList;
	}

	@Override
	public int getJdbc_BoardCount(int board_no) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int cnt = 0;
		
		try {
			
			conn = DBUtil3.getConnection();
			
			String sqlSq = "SELECT COUNT(*) cnt FROM JDBC_BOARD "
					+ "WHERE BOARD_NO = ?"; 
			
			pstmt = conn.prepareStatement(sqlSq);
			pstmt.setInt(1, board_no);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				cnt = rs.getInt(1);
			}else {
				cnt=0;
				System.out.println("번호 부여에 오류가 발생했습니다.");
				return cnt;
			}
			
		}  catch (Exception e) {
			cnt = 0;
			e.printStackTrace();
		} finally {
			if(rs!=null) try { rs.close(); }catch(SQLException e) {}
			if(pstmt!=null) try { pstmt.close(); }catch(SQLException e) {}
			if(conn!=null) try { conn.close(); }catch(SQLException e) {}
		}
		
		return cnt;
	}

	@Override
	public Jdbc_BoardVO getJdbc_BoardShowOne(int board_no) {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int cnt = 0;
		int boardNumber = board_no;
		Jdbc_BoardVO jdbc_BoardVo = null;
		
		try {
			conn = DBUtil3.getConnection();
			
			String sql = "UPDATE JDBC_BOARD SET "
					+ "BOARD_CNT = (SELECT NVL(MAX(BOARD_CNT),0)+1 "
					+ "FROM JDBC_BOARD WHERE BOARD_NO = ?) "
					+ "WHERE BOARD_NO = ?";
						
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, boardNumber);
			pstmt.setInt(2, boardNumber);
			rs = pstmt.executeQuery();
			pstmt.close();
			
			sql ="SELECT * FROM JDBC_BOARD WHERE BOARD_NO = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, boardNumber);
			
			
			cnt = pstmt.executeUpdate();
			rs = pstmt.executeQuery();
			
			jdbc_BoardVo = new Jdbc_BoardVO();
			
			if(rs.next()) {
				jdbc_BoardVo.setBoard_no(rs.getInt("BOARD_NO"));
				jdbc_BoardVo.setBoard_title(rs.getString("BOARD_TITLE"));
				jdbc_BoardVo.setBoard_writer(rs.getString("BOARD_WRITER"));
				jdbc_BoardVo.setBoard_date(rs.getString("BOARD_DATE"));
				jdbc_BoardVo.setBoard_cnt(rs.getInt("BOARD_CNT"));
				jdbc_BoardVo.setBoard_content(rs.getString("BOARD_CONTENT"));
			}
			
		} catch (Exception e) {
			cnt=0;
			e.printStackTrace();
		} finally {
			if(rs!=null) try { rs.close(); }catch(SQLException e) {}
			if(pstmt!=null) try { pstmt.close(); }catch(SQLException e) {}
			if(conn!=null) try { conn.close(); }catch(SQLException e) {}
		}
		
		return jdbc_BoardVo;
	}

	
}
