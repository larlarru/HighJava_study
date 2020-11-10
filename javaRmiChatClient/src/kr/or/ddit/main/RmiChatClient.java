package kr.or.ddit.main;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

import kr.or.ddit.inf.ClientChatInf;
import kr.or.ddit.inf.ServerChatInf;

// 클라이언트용 인터페이스를 구현한 클래스
public class RmiChatClient extends UnicastRemoteObject implements ClientChatInf{
	
	// 생성자
	public RmiChatClient() throws RemoteException{ }
	
	// 서버가 보내온 메시지를 화면에 출력하는 메서드
	@Override
	public void printMessage(String msg) throws RemoteException {
		
		System.out.println(msg);
	}
	
		
	public static void main(String[] args) {
		
		Scanner scan = new Scanner(System.in);
		
		try {
			
			// 클라이언트 객체 생성
			ClientChatInf client = new RmiChatClient();
			
			Registry reg = LocateRegistry.getRegistry("localhost", 1099);
			
			// 서버에 접속하기
			ServerChatInf server = (ServerChatInf) reg.lookup("rmiChat");
			
			// 서버에 접속되면 현재 클라이언트 정보를 서버에 등록한다.
			server.setClient(client);
			
			while(true) {
				
				// 메시지를 입력 받아서 채팅 작업을 수행한다.
				String msg = scan.nextLine();
				
				if("/end".equals(msg)) {	// 채팅방 빠져나오기
					break;	// 반복문 탈출
				}
				server.sendToAll(msg);	// 모든 클라이언트에게 메시지를 보내는 서버의 메서드 호출
			}
			server.disConnect(client);	// 서버에서 현재 클라이언트 정보를 삭제한다.

			// RMI로 연결된 클라이언트와 서버의 접속을 끝내기
			UnicastRemoteObject.unexportObject(client, true);
			
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
	}

}
