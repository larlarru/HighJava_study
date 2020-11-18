package kr.or.ddit.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import kr.or.ddit.util.DBUtil3;
import kr.or.ddit.vo.JdbcBoardVO;

public class JdbcBoardDaoImpl implements IJdbcBoardDao{
	
	private static JdbcBoardDaoImpl dao;
	
	private JdbcBoardDaoImpl() {}
	
	public static JdbcBoardDaoImpl getInstance() {
		if(dao==null) dao = new JdbcBoardDaoImpl();
		return dao;
	}
	
	// DB작업에 필요한 객체 변수 선언
	private Connection conn;
	private Statement stmt;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	// 사용한 자원을 반납하는 메서드
	private void disconnect() {
		if(rs!=null) try { rs.close(); } catch(SQLException e) {}
		if(stmt!=null) try { stmt.close(); } catch(SQLException e) {}
		if(pstmt!=null) try { pstmt.close(); } catch(SQLException e) {}
		if(conn!=null) try { conn.close(); } catch(SQLException e) {}
	}
	
	
	@Override
	public int insertBoard(JdbcBoardVO boardVo) {
		int cnt = 0;
		
		try {
			
			conn = DBUtil3.getConnection();
			
			String sql = "INSERT INTO JDBC_BOARD "
					+ "(BOARD_NO, BOARD_TITLE, BOARD_WRITER, "
					+ "BOARD_DATE, BOARD_CNT, BOARD_CONTENT) "
					+ "VALUES(board_seq.nextval, "
					+ "?, ?, SYSDATE, 0, ?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, boardVo.getBoard_title());
			pstmt.setString(2, boardVo.getBoard_writer());
			pstmt.setString(3, boardVo.getBoard_content());
			
			cnt = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			cnt = 0;
			e.printStackTrace();
		} finally {
			disconnect();
		}
		
		return cnt;
	}

	@Override
	public int deleteBoard(int boardNo) {
		
		int cnt = 0;
		
		try {
			conn = DBUtil3.getConnection();
			
			String sql = "DELETE FROM JDBC_BOARD WHERE board_no = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, boardNo);
			
			cnt = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			cnt = 0;
			e.printStackTrace();
		} 
		
		return cnt;
	}

	@Override
	public int updateBoard(JdbcBoardVO boardVo) {
		int cnt = 0;
		
		try {
			
			conn = DBUtil3.getConnection();
			
			String sql = "UPDATE JDBC_BOARD SET "
					+ "board_title = ?, board_date = SYSDATE, "
					+ "board_content = ? "
					+ "WHERE board_no = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, boardVo.getBoard_title());
			pstmt.setString(2, boardVo.getBoard_content());
			pstmt.setInt(3, boardVo.getBoard_no());
			
			cnt = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			cnt = 0;
			e.printStackTrace();
		}
		
		return cnt;
	}

	@Override
	public List<JdbcBoardVO> getAllBoardList() {
		
		List<JdbcBoardVO> boardList = null;
		
		try {
			
			conn = DBUtil3.getConnection();
			
			String sql = "SELECT board_no, board_title, "
					+ "board_writer, to_char(board_date, 'YYYY-MM-DD') "
					+ "board_date, board_cnt, board_content FROM JDBC_BOARD "
					+ "ORDER BY board_no desc";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			
			boardList = new ArrayList<>();
			while(rs.next()) {
				JdbcBoardVO boardVo = new JdbcBoardVO();
				
				boardVo.setBoard_no(rs.getInt("board_no"));
				boardVo.setBoard_title(rs.getString("board_title"));
				boardVo.setBoard_writer(rs.getString("board_writer"));
				boardVo.setBoard_date(rs.getString("board_date"));
				boardVo.setBoard_cnt(rs.getInt("board_cnt"));
				boardVo.setBoard_content(rs.getString("board_content"));
				
				boardList.add(boardVo);
			}
			
		} catch (SQLException e) {
			boardList = null;
			e.printStackTrace();
		}
		
		return boardList;
	}

	@Override
	public JdbcBoardVO getBoard(int boardNo) {
		
		JdbcBoardVO boardVo = null;
		
		try {
			
			conn = DBUtil3.getConnection();
			
			String sql = "SELECT BOARD_NO, board_title, "
					+ "board_writer, to_char(board_date, 'YYYY-MM-DD') "
					+ "board_date, board_cnt, board_content FROM JDBC_BOARD "
					+ "WHERE board_no = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, boardNo);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				boardVo = new JdbcBoardVO();
				
				boardVo.setBoard_no(rs.getInt("board_no"));
				boardVo.setBoard_title(rs.getString("board_title"));
				boardVo.setBoard_writer(rs.getString("board_writer"));
				boardVo.setBoard_date(rs.getString("board_date"));
				boardVo.setBoard_cnt(rs.getInt("board_cnt"));
				boardVo.setBoard_content(rs.getString("board_content"));
				
			}
			
		} catch (SQLException e) {
			boardVo = null;
			e.printStackTrace();
		}
		
		return boardVo;
	}

	@Override
	public List<JdbcBoardVO> getSearchBoardList(String title) {
		List<JdbcBoardVO> boardList = null;
		
		try {
			
			conn = DBUtil3.getConnection();
			
			String sql = "SELECT BOARD_NO, board_title, "
					+ "board_writer, to_char(board_date, 'YYYY-MM-DD') "
					+ "board_date, board_cnt, board_content FROM JDBC_BOARD "
					+ "WHERE board_title like '%' || ? || '%' "
					+ "ORDER BY board_no desc";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, title);
			
			rs = pstmt.executeQuery();
			
			boardList = new ArrayList<>();
			while(rs.next()) {
				JdbcBoardVO boardVo = new JdbcBoardVO();
				
				boardVo.setBoard_no(rs.getInt("board_no"));
				boardVo.setBoard_title(rs.getString("board_title"));
				boardVo.setBoard_writer(rs.getString("board_writer"));
				boardVo.setBoard_date(rs.getString("board_date"));
				boardVo.setBoard_cnt(rs.getInt("board_cnt"));
				boardVo.setBoard_content(rs.getString("board_content"));
				
				boardList.add(boardVo);
			}
						
		} catch (SQLException e) {
			boardList = null;
			e.printStackTrace();
		}
		return boardList;
	}

	@Override
	public int setCountIncrement(int boardNo) {
		
		int cnt = 0;
		
		try {
			
			conn = DBUtil3.getConnection();
			
			String sql = "UPDATE JDBC_BOARD SET "
					+ "board_cnt = board_cnt + 1 "
					+ "WHERE board_no = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, boardNo);
			
			cnt = pstmt.executeUpdate();
			
			
		} catch (Exception e) {
			cnt = 0;
			e.printStackTrace();
		}
		
		return cnt;
	}

}
