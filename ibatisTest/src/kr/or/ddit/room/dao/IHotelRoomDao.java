package kr.or.ddit.room.dao;

import java.util.List;

import kr.or.ddit.room.vo.HotelRoomVO;


public interface IHotelRoomDao {

	public int updateRoomCheckIn(HotelRoomVO roomVo);
	
	public int updateRoomCheckOut(HotelRoomVO roomVo);
	
	public List<HotelRoomVO> getAllHotelRoom();
	
	//public int getRomUserCheckCount(String roomUser);
	public String getRomUserCheckCount(int roomNo);
	
}
