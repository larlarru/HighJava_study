package kr.or.ddit.dao;

import java.util.List;

import kr.or.ddit.vo.Jdbc_BoardVO;

public interface Jdbc_BoardDao {
	
	public int insertJdbc_Board(Jdbc_BoardVO jdbc_BoardVo);
		
	public int deleteJdbc_Board(int board_no);
	
	public int updateJdbc_Board(int board_no, Jdbc_BoardVO jdbc_BoardVo);
	
	public List<Jdbc_BoardVO> getAllJdbc_BoardList();
	
	public List<Jdbc_BoardVO> getJdbc_BoardList(String board_title);
	
	public int getJdbc_BoardCount(int board_no);
	
	public Jdbc_BoardVO getJdbc_BoardShowOne(int board_no);
	
}
