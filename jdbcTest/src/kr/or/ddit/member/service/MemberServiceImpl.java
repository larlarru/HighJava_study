package kr.or.ddit.member.service;

import java.util.List;

import kr.or.ddit.member.dao.IMemberDao;
import kr.or.ddit.member.dao.MemberDaoImpl;
import kr.or.ddit.member.vo.MemberVO;

public class MemberServiceImpl implements IMemberService {
	
	private IMemberDao dao;	// DAO객체가 저장될 변수 선언
	
	private static MemberServiceImpl service;	// 1번
		
	// 생성자 - 2번
	//prublic MemberServiceImpl() {
	private MemberServiceImpl() {
		
		//dao = new MemberDaoImpl();
		dao = MemberDaoImpl.getInstance();
	}
	
	// 3번
	public static MemberServiceImpl getInstance() {
		if(service==null) service = new MemberServiceImpl();
		return service;
	}
	
	@Override
	public int insertMember(MemberVO memVo) {
		
		return dao.insertMember(memVo);
	}

	@Override
	public int deleteMember(String memId) {
		
		return dao.deleteMember(memId);
	}

	@Override
	public int updateMember(MemberVO memVo) {
		
		return dao.updateMember(memVo);
	}

	@Override
	public List<MemberVO> getAllMemberList() {
		
		return dao.getAllMemberList();
	}

	@Override
	public int getMemberCount(String memId) {
		
		return dao.getMemberCount(memId);
	}

	@Override
	public int updateMemName(String memId, String memName) {
		return dao.updateMemName(memId, memName);
	}

	@Override
	public int updateMemTel(String memId, String memTel) {
		return dao.updateMemTel(memId, memTel);
	}

	@Override
	public int updateMemAddr(String memId, String memAddr) {
		return dao.updateMemAddr(memId, memAddr);
	}

}
