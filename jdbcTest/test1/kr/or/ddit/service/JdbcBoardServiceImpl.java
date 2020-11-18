package kr.or.ddit.service;

import java.util.List;

import kr.or.ddit.dao.IJdbcBoardDao;
import kr.or.ddit.dao.JdbcBoardDaoImpl;
import kr.or.ddit.vo.JdbcBoardVO;

public class JdbcBoardServiceImpl implements IJdbcBoardService{
	
	private IJdbcBoardDao dao;
	private static JdbcBoardServiceImpl service;
	
	private JdbcBoardServiceImpl() {
		dao = JdbcBoardDaoImpl.getInstance();
	}
	
	public static JdbcBoardServiceImpl getInstance() {
		if(service==null) service = new JdbcBoardServiceImpl();
		return service;
	}
	
	@Override
	public int insertBoard(JdbcBoardVO boardVo) {
		return dao.insertBoard(boardVo);
	}

	@Override
	public int deleteBoard(int boardNo) {
		return dao.deleteBoard(boardNo);
	}

	@Override
	public int updateBoard(JdbcBoardVO boardVo) {
		return dao.updateBoard(boardVo);
	}

	@Override
	public List<JdbcBoardVO> getAllBoardList() {
		return dao.getAllBoardList();
	}

	@Override
	public JdbcBoardVO getBoard(int boardNo) {
		int cnt = setCountIncrement(boardNo);	// 조회수 증가
		if(cnt==0) {	// 조회수 증가가 실패했을 때
			return null;
		}
		return dao.getBoard(boardNo);
	}

	@Override
	public List<JdbcBoardVO> getSearchBoardList(String title) {
		return dao.getSearchBoardList(title);
	}

	@Override
	public int setCountIncrement(int boardNo) {
		return dao.setCountIncrement(boardNo);
	}

}
