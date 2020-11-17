package kr.or.ddit.service;

import java.util.List;

import kr.or.ddit.dao.Jdbc_BoardDao;
import kr.or.ddit.dao.Jdbc_BoardDaoImpl;
import kr.or.ddit.vo.JdbcBoardVO;

public class Jdbc_BoardServiceImpl implements Jdbc_BoardService{
	
	private Jdbc_BoardDao dao;
	
	private static Jdbc_BoardServiceImpl service;
	
	private Jdbc_BoardServiceImpl() {
		dao = Jdbc_BoardDaoImpl.getInstance();
	}
	
	public static Jdbc_BoardServiceImpl getInstance() {
		if(service==null) service = new Jdbc_BoardServiceImpl();
		return service;
	}
	
	@Override
	public int insertJdbc_Board(JdbcBoardVO jdbc_BoardVo) {
		return dao.insertJdbc_Board(jdbc_BoardVo);
	}

	@Override
	public int deleteJdbc_Board(int board_no) {
		return dao.deleteJdbc_Board(board_no);
	}

	@Override
	public int updateJdbc_Board(int board_no, JdbcBoardVO jdbc_BoardVo) {
		return dao.updateJdbc_Board(board_no, jdbc_BoardVo);
	}

	@Override
	public List<JdbcBoardVO> getAllJdbc_BoardList() {
		return dao.getAllJdbc_BoardList();
	}

	@Override
	public List<JdbcBoardVO> getJdbc_BoardList(String board_title) {
		return dao.getJdbc_BoardList(board_title);
	}

	@Override
	public int getJdbc_BoardCount(int board_no) {
		return dao.getJdbc_BoardCount(board_no);
	}

	@Override
	public JdbcBoardVO getJdbc_BoardShowOne(int board_no) {
		return dao.getJdbc_BoardShowOne(board_no);
	}

}
