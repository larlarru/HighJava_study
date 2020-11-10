package kr.or.ddit.vo;

import java.io.Serializable;

// 파일 전송용 VO클래스
public class FileInfoVO implements Serializable{
	
	private String fileName;	// 파일명이 저장될 변수
	private byte[] fileData;	// 파일의 내용이 저장될 변수
	
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public byte[] getFileData() {
		return fileData;
	}
	public void setFileData(byte[] fileData) {
		this.fileData = fileData;
	}
	
}
