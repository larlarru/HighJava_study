package kr.or.ddit.basic;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/*
	'd:/D_Other/' 폴더에 있는 '극한직업.jpg'파일을
	'd:/D_Other/연습용' 폴더에 '극한직업_복사본.jpg'이름으로 복사하는 프로그램을 작성하시오.
*/
public class FileCopyTest_test {

	public static void main(String[] args) {
		String fileName = "극한직업.jpg";
		File file = new File("d:/D_Other/" + fileName );
		
		if(!file.exists()) {
			System.out.println(file.getPath() + " 파일이 없습니다.");
			System.out.println("복사 작업을 중지합니다.");
			return;
		}
		
		try {
			// 복사할 파일 스트림 객체 생성 (원본 파일용)
			FileInputStream fin = new FileInputStream(file);
			
			BufferedInputStream bin = new BufferedInputStream(fin);
			
			
			// 복사될 파일 스트림 객체 생성 (저장될 파일용)
			FileOutputStream fout = 
				new FileOutputStream("d:/D_Other/연습용/극한직업_복사본.jpg");
			
			BufferedOutputStream bout = new BufferedOutputStream(fout);
			
			System.out.println("복사 시작...");
			
			int data;	// 읽어온 데이터를 저장할 변수
			/*
			while((data = fin.read()) != -1) {
				fout.write(data);
			}
			fout.flush();
			// 사용했던 스트림 닫기
			fout.close();
			fin.close();
			*/
			
			while((data = bin.read()) != -1) {
				bout.write(data);
			}
			bout.flush();
			
			// 사용했던 스트림 닫기
			bout.close();
			bin.close();
			System.out.println("복사 완료...");
			
			
		} catch (IOException e) {
			// TODO: handle exception
		}

	}

}












