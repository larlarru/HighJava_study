package kr.or.ddit.basic;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;

/*
 * 'd:/D_Other/' 폴더에 있는 '극한직업.jpg'파일을
 * 'd:/D_Other/연습용' 폴더에 '극한직업_복사본.jpg' 이름으로 복사하는 프로그램을 작성하시오.
 */

public class FileCopyTest {
	
	public static void main(String[] args) {
		
		try {
			FileInputStream fin = new FileInputStream("d:/D_Other/극한직업.jpg");
			File file = new File("d:/D_Other/연습용/극한직업_복사본.jpg");	
			FileOutputStream fout = new FileOutputStream(file);
			
			int c;	// 읽어온 데이터가 저장될 변수
			
			while( ( c=fin.read() ) != -1 ) {
				fout.write(c);
			}
			System.out.println("작업 완료");
			
			fin.close();
			fout.close();
			
			
		} catch (IOException e) {
			// TODO: handle exception
			System.out.println("오류");
			e.printStackTrace();
		}
		
		
	}

}











