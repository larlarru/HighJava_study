package kr.or.ddit.dao;

import java.util.List;

import kr.or.ddit.vo.JdbcBoardVO;

public interface Jdbc_BoardDao {
	
	public int insertJdbc_Board(JdbcBoardVO jdbc_BoardVo);
		
	public int deleteJdbc_Board(int board_no);
	
	public int updateJdbc_Board(int board_no, JdbcBoardVO jdbc_BoardVo);
	
	public List<JdbcBoardVO> getAllJdbc_BoardList();
	
	public List<JdbcBoardVO> getJdbc_BoardList(String board_title);
	
	public int getJdbc_BoardCount(int board_no);
	
	public JdbcBoardVO getJdbc_BoardShowOne(int board_no);
	
}
