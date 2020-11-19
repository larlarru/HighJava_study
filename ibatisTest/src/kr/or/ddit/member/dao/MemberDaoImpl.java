package kr.or.ddit.member.dao;

import java.io.IOException;
import java.io.Reader;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;

import kr.or.ddit.member.vo.MemberVO;
import kr.or.ddit.util.BuildSqlMapClient;

public class MemberDaoImpl implements IMemberDao{
	
	private SqlMapClient smc;	// iBatis용 sqlMapClient객체 변수 선언
	Reader rd = null;
	
	// 1번
	private static MemberDaoImpl dao;
	
	// 2번
	// DAO의 생성자에서 iBatis환경 설정과 sqlMapClient객체를 생성한다.
	private MemberDaoImpl() {
		/*
		try {
			
			// 1-1. 문자 인코딩 캐릭터셋 설정하기
			Charset charset = Charset.forName("UTF-8");
			Resources.setCharset(charset);

			// 1-2. 환경 설정 파일을 읽어온다.
			rd = Resources.getResourceAsReader("sqlMapConfig.xml");

			// 1-3. 위에서 읽어온 Reader객체를 이용하여 실제 환경설정을 완성한 후
			// SQL문을 호출해서 실행할 수 있는 객체를 생성한다.
			smc = SqlMapClientBuilder.buildSqlMapClient(rd);
			

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// 자원 반납
			if(rd!=null) try { rd.close(); } catch (IOException e2) {}
		}
		*/
		smc = BuildSqlMapClient.getSqlMapClient();
	}
	
	// 3번
	public static MemberDaoImpl getInstance() {
		
		if(dao==null) dao = new MemberDaoImpl();
		return dao;
	}

	@Override
	public int insertMember(MemberVO memVo) {
		
		int cnt = 0;
		
		try {
			
			Object obj = smc.insert("mymember.insertMember", memVo);
			if(obj==null) cnt = 1;
			
		} catch (SQLException e) {
			cnt = 0;
			e.printStackTrace();
		}
		
		return cnt;
	}

	@Override
	public int deleteMember(String memId) {
		
		int cnt = 0;
		
		try {
			
			Object obj = smc.delete("mymember.deleteMember", memId);
			if(obj!=null) cnt = 1;
			
		} catch (SQLException e) {
			cnt = 0;
			e.printStackTrace();
		}
		
		return cnt;
	}

	@Override
	public int updateMember(MemberVO memVo) {
		
		int cnt = 0;
		
		try {
			
			Object obj = smc.update("mymember.updateMember", memVo);
			if(obj!=null) cnt = 1;
			
		} catch (SQLException e) {
			cnt = 0;
			e.printStackTrace();
		}
		
		return cnt;
	}

	@Override
	public List<MemberVO> getAllMemberList() {
		List<MemberVO> memVoList = new ArrayList<>();

		try {

			memVoList = smc.queryForList("mymember.seletcAllMember");

		} catch (SQLException e) {
			memVoList = null;
			e.printStackTrace();
		}

		return memVoList;
	}

	@Override
	public int getMemberCount(String memId) {
		
		int count = 0;   // 회원ID의 개수가 저장될 변수
		
		try {
			
			count = (int) smc.queryForObject("mymember.getMemberCount", memId);
			
		} catch (SQLException e) {
			count = 0;
			e.printStackTrace();
		}
		
		return count;
	}

	
	@Override
	public int updateMeber2(Map<String, String> paramMap) {
		// key값 ==> 회원ID(memId), 변경할 컬럼명(field), 변경할데이터(data)
		
		int cnt = 0;
		
		try {
			
			cnt = smc.update("mymember.updateMember2", paramMap);

		} catch (SQLException e) {
			cnt = 0;
			e.printStackTrace();
		} 
		
		return cnt;
	}
	

}
