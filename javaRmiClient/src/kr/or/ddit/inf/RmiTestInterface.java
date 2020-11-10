package kr.or.ddit.inf;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import kr.or.ddit.vo.FileInfoVO;
import kr.or.ddit.vo.TestVO;

/*
 * 원격지에서 호출할 수 있는 메서드를 선언하는 interface
 * 즉, RMI용 interface는 Remote를 상속해서 작성해야 한다.
 */

public interface RmiTestInterface extends Remote {
	// 이 인터페이스에서 선언되는 모든 메서드들은 RemoteException을 throws해서 선언해야 한다.
	
	// 그리고, 이 곳에서 선언된 메서드의 파라미터 변수는 클라이언트쪽에서 서버쪽으로 보내는 데이터가 되고,
	// 선언된 메서드의 반환값은 서버에서 처리한 결과가 클라이언트에게 전달되는 데이터이다.
	
	public int doRemotePrint(String str) throws RemoteException;
	
	public void doPrintList(List<String> list) throws RemoteException;
	
	public void doPrintVo(TestVO vo) throws RemoteException;
	
	//파일 전송용 메서드 추가
	public void transFile(FileInfoVO fileVo) throws RemoteException; 
}











