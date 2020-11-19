package kr.or.ddit.board.dao;

import java.io.Reader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.ibatis.sqlmap.client.SqlMapClient;

import kr.or.ddit.board.vo.JdbcBoardVO;
import kr.or.ddit.util.BuildSqlMapClient;

public class JdbcBoardDaoImpl implements IJdbcBoardDao{
	
	private SqlMapClient smc;	// iBatis용 sqlMapClient객체 변수 선언
	Reader rd = null;
	
	private static JdbcBoardDaoImpl dao;
	
	private JdbcBoardDaoImpl() {
		smc = BuildSqlMapClient.getSqlMapClient();
	}
	
	public static JdbcBoardDaoImpl getInstance() {
		if(dao==null) dao = new JdbcBoardDaoImpl();
		return dao;
	}
	
	

	@Override
	public int insertBoard(JdbcBoardVO boardVo) {
		int cnt = 0;
		try {
						
			Object obj= smc.insert("jdbcboard.insertBoard", boardVo);
			if(obj==null) cnt = 1;
			
		} catch (SQLException e) {
			cnt = 0;
			e.printStackTrace();
		} 
		return cnt;
	}

	@Override
	public int deleteBoard(int boardNo) {
		int cnt = 0;
		try {
			
			Object obj= smc.delete("jdbcboard.deleteBoard", boardNo);
			if(obj!=null) cnt = 1;
			
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
			
			Object obj= smc.update("jdbcboard.updateBoard", boardVo);
			if(obj!=null) cnt = 1;
			
		} catch (SQLException e) {
			cnt = 0;
			e.printStackTrace();
		} 
		
		return cnt;
	}

	@Override
	public List<JdbcBoardVO> getAllBoardList() {
		List<JdbcBoardVO> boardList = new ArrayList<>();
		
		try {
			
			boardList = smc.queryForList("jdbcboard.selectAllBoard");
			
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
			//boardVo = (JdbcBoardVO) smc.queryForList("jdbcboard.getBoard");
			boardVo = (JdbcBoardVO) smc.queryForObject("jdbcboard.getBoard", boardNo);
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
			
			boardList = smc.queryForList("jdbcboard.getSearchBoardList");
			
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
			
			Object obj= smc.update("jdbcboard.setCountIncrement", boardNo);
			if(obj!=null) cnt = 1;
			
		} catch (SQLException e) {
			cnt = 0;
			e.printStackTrace();
		} 
		
		return cnt;
	}

}
