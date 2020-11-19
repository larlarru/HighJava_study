package kr.or.ddit.basic;

import java.io.IOException;
import java.io.Reader;
import java.nio.charset.Charset;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;

public class LprodibatisTest {
	
	// iBatix를 이용하여 DB자료를 처리하는 순서 및 방법
	
	public static void main(String[] args) {
		
		Scanner scan = new Scanner(System.in);
		
		// 1. iBatis의 환경 설정 파일(sqlMapConfig.xml)을 읽어와서 실행한다.
		try {
			
			// 1-1. 문자 인코딩 캐릭터셋 설정하기
			Charset charset = Charset.forName("UTF-8");
			Resources.setCharset(charset);
			
			// 1-2. 환경 설정 파일을 읽어온다.
			Reader rd = Resources.getResourceAsReader("sqlMapConfig.xml");
			
			// 1-3. 위에서 읽어온 Reader객체를 이용하여 실제 환경설정을 완성한 후
			//		SQL문을 호출해서 실행할 수 있는 객체를 생성한다.
			SqlMapClient smc = SqlMapClientBuilder.buildSqlMapClient(rd);
			
			rd.close();	// Reader객체 닫기
			
			// 환경 설정 끝...
			
			//------------------------------------------------------------------
			
			/*
			// 2. 실행할 SQL문에 맞는 쿼리문을 호출해서 원하는 작업을 수행한다.
			
			// 2-1. insert 연습
			System.out.println("insert 작업 시작....");
			System.out.println("lprod_id 입력 : ");
			int lprodId = scan.nextInt();
			
			System.out.print("lprod_gu 입력 : ");
			String lprodGu = scan.next();
			
			System.out.print("lprod_nm 입력 : ");
			String lprodNm = scan.next();
			
			// 1) insert할 데이터들을 VO객체에 담는다.
			LprodVO lprodVo = new LprodVO();
			lprodVo.setLprod_id(lprodId);
			lprodVo.setLprod_gu(lprodGu);
			lprodVo.setLprod_nm(lprodNm);;
			
			// 2) sqlMapClient객체변수를 이용해서 처리할 쿼리문을 호출하여 실행한다.
			//		형식) sqlMapClient객체변수.insert("namespace값.id속성값", 파라미터클래스);
			//				반환값 : insert성공 : null, insert실패 : 오류 객체
			
			Object obj = smc.insert("lprod.insertLprod", lprodVo);
			if(obj==null) {
				System.out.println("insert 작업 성공!!!");
			} else {
				System.out.println("insert 작업 실패~~");
			}
			
			*/
			/*
			// 2-2. update 연습
			System.out.println("update 작업 시작....");
			
			System.out.print("수정할 lprod_gu 입력 : ");
			String lprodGu = scan.next();
			
			System.out.print("새로운 lprod_id 입력 : ");
			int lprodId = scan.nextInt();
			
			System.out.print("새로운 lprod_nm 입력 : ");
			String lprodNm = scan.next();
			
			// 1) 수정할 데이터를 VO객체에 담는다.
			LprodVO lrodVo2 = new LprodVO();
			lrodVo2.setLprod_gu(lprodGu);
			lrodVo2.setLprod_id(lprodId);
			lrodVo2.setLprod_nm(lprodNm);
			
			// 2) sqlMapClient객체변수.update("namespace값.id속성값", 파라미터클래스);
			//		==> 반환값 : 작업에 성공한 레코드 수
			int cnt = smc.update("lprod.updateLprod", lrodVo2);
			
			if(cnt > 0) {
				System.out.println("update 작업 성공");
			} else {
				System.out.println("update 작업 실패.");
			}
			System.out.println("---------------------------------------------");
			*/
			/*
			// 2-3. delete 연습
			System.out.println("delete 작업 시작.");
			System.out.print("삭제할 Lprod_gu 입력 : ");
			String lprodGu = scan.next();
			
			// 1) sqlMapClient객체 변수, .delete("banesoace값.id속성값", 파라미터클래스)
			//		반환값 : 작업에 성공한 레코드 수
			
			int cnt2 = smc.delete("lprod.deleteLprod", lprodGu);
			
			if(cnt2 > 0) {
				System.out.println("delete 작업 성공");
			} else {
				System.out.println("delete 작업 실패.");
			}
			System.out.println("---------------------------------------------");
			*/
			
			// 2-4. select 연습
			
			// 1) 응답 결과가 여러개의 레코드인 경우
			//		==> 응답 결과가 여러개일 경우에는 queryForList() 메서드를 사용하는데
			//			이 메서드는 여러개의 레코드 각각을 VO객체에 담은 후 이 VO객체를 List에
			//			추가하는 작업을 자동으로 수행한다.
			// 형식) sqlMapClient객체변수.queryForList("namespace값.id속성값", 파라미터클래스)
			//		==> SQL문에 전달할 데이터가 없으면 '파라미터클래스'값을 생략할 수 있다.
			/*
			System.out.println("1) select 연습(결과가 여러개일 경우...)");
			List<LprodVO> lprodList = smc.queryForList("lprod.getAllLprod");
			
			System.out.println("---------------------------------------------");
			for(LprodVO lpVo : lprodList) {
				System.out.println("ID : " + lpVo.getLprod_id());
				System.out.println("GU : " + lpVo.getLprod_gu());
				System.out.println("NM : " + lpVo.getLprod_nm());
				System.out.println("---------------------------------------------");
			}
			System.out.println("출력 끝......");
			*/
			// 2) 응답 결과가 1개일 경우
			//		==> 응답 결과가 1개일 경우에는 queryForObject()메서드를 사용한다.
			//		형식) sqlMapClient객체변수.queryForObject("namespace값.id속성값", 파라미터클래스)
			
			System.out.println("2) select 연습시작(결과가 1개일 경우)...");
			System.out.print("검색할 lprod_gu 입력  : ");
			String lprodGu = scan.next();
			
			LprodVO lprodVo = (LprodVO)smc.queryForObject("lprod.getLprod", lprodGu);
			
			if(lprodVo==null) {	// 검색할 결과가 하나도 없으면 null을 반환한다.
				System.out.println("검색한 데이터가 하나도 없습니다.");				
			} else {
				System.out.println("ID : " + lprodVo.getLprod_id());
				System.out.println("GU : " + lprodVo.getLprod_gu());
				System.out.println("NM : " + lprodVo.getLprod_nm());
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		
		
	}

}





