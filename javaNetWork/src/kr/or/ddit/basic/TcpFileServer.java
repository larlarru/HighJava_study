package kr.or.ddit.basic;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class TcpFileServer {
	
	/*
	 * 문제) 서버와 클라이언트가 접속이 되면 클라이언트에서 'd:/D_Other/' 폴더에 있는 '극한직업.jpg'파일을
	 * 		서버쪽에 전송한다.
	 * 		서버는 클라이언트가 보내온 이미지 데이터를 'd:/D_Other/연습옹/'폴더에 '극한직업_전송파일.jpg'로 저장한다.
	 */
	
	public static void main(String[] args) throws IOException {
		
		ServerSocket server = new ServerSocket(1234);
		
		System.out.println("접속을 기다립니다.");
		
		Socket socket = server.accept();
		
		System.out.println("클라이언트와 연결되었습니다.");
		System.out.println();
		
		// 여기서 파일 읽어들이기하기
		String fileName = "극한직업.jpg";
		File file = new File("d:/D_Other/" + fileName);
		
		if(!file.exists()) {
			System.out.println(file.getPath() + " 파일이 없습니다.");
			System.out.println("복사 작업을 중지합니다.");
			return;
		}
		
		OutputStream out = socket.getOutputStream();
		DataOutputStream dout = new DataOutputStream(out);
		
		try {
			// 복사할 파일 스트림 객체 생성 (원본 파일용)
			FileInputStream fin = new FileInputStream(file);
			
			BufferedInputStream bin = new BufferedInputStream(fin);
			
			int data;
			
			while((data = bin.read())!= -1) {
				dout.write(data);
			}
			
			
			/*dout.writeUTF("파일을 보냈습니다.");
			dout.writeByte(data);*/
			System.out.println("파일 전송했습니다.");
			
			bin.close();
			
			dout.close();
			socket.close();
			server.close();
			
			
			
		} catch (IOException e) {
			// TODO: handle exception
			System.out.println("error");
			e.printStackTrace();
		}
		
		
		
	}

}






















