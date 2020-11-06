package kr.or.ddit.basic;

import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.Socket;

public class TcpFileClient {
	
	public static void main(String[] args) {
		
		try {

			Socket socket = new Socket("localhost", 1234);
			System.out.println("서버에 연결되었습니다.");

			InputStream is = socket.getInputStream();
			DataInputStream dis = new DataInputStream(is);
			
			// 복사될 파일 스트림 객체 생성 (저장될 파일용)
			FileOutputStream fout = 
					new FileOutputStream(
							"d:/D_Other/연습용/극한직업_복사본.jpg");
			
			BufferedOutputStream bout = new BufferedOutputStream(fout);
			
			System.out.println("다운시작");
			
			int data;
			
			while((data = dis.read()) != -1) {
				bout.write(data);
			}
			bout.flush();
			
			bout.close();
			System.out.println("복사 완료...");
			
			System.out.println("연결을 종료합니다.");
			
			// 스트림과 소켓닫기
			dis.close();
			socket.close();

		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("오류");
			e.printStackTrace();
		}
		
	}

}
















