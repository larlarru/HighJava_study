package kr.or.ddit.room.dao;

import java.io.Reader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ibatis.sqlmap.client.SqlMapClient;

import kr.or.ddit.room.vo.HotelRoomVO;
import kr.or.ddit.util.BuildSqlMapClient;

public class HotelRoomDaoImpl implements IHotelRoomDao{
	
	private SqlMapClient smc;
	Reader rd = null;
	
	private static HotelRoomDaoImpl dao;
	
	private HotelRoomDaoImpl() {
		smc = BuildSqlMapClient.getSqlMapClient();
	}
	
	public static HotelRoomDaoImpl getInstance() {
		if(dao==null) dao = new HotelRoomDaoImpl();
		return dao;
	}
	
	@Override
	public int updateRoomCheckIn(HotelRoomVO roomVo) {
		
		int cnt = 0;
		
		try {
			
			Object obj= smc.update("hotelroom.updateRoomCheckIn", roomVo);
			if(obj!=null) cnt = 1;
			
		} catch (SQLException e) {
			cnt = 0;
			e.printStackTrace();
		} 
		
		return cnt;
	}

	@Override
	public int updateRoomCheckOut(HotelRoomVO roomVo) {
		int cnt = 0;
		
		try {
			
			Object obj= smc.update("hotelroom.updateRoomCheckOut", roomVo);
			if(obj!=null) cnt = 1;
			
		} catch (SQLException e) {
			cnt = 0;
			e.printStackTrace();
		} 
		
		return cnt;
	}
	
	@Override
	public List<HotelRoomVO> getAllHotelRoom() {
		
		List<HotelRoomVO> roomList = new ArrayList<>();
		
		try {
			
			roomList = smc.queryForList("hotelroom.selectAllHotelRoom");
			
		} catch (SQLException e) {
			roomList = null;
			e.printStackTrace();
		}
		
		return roomList;
	}

	@Override
	public String getRomUserCheckCount(int roomNo) {
		String obj="";
		
		try {
			
			obj = (String) smc.queryForObject("hotelroom.selectRoomUserCheck", roomNo);
			
		} catch (SQLException e) {
			obj = null;
			e.printStackTrace();
		}
		
		return obj;
	}

	/*@Override
	public int getRomUserCheckCount(String roomUser) {

		int count = 0;
		
		try {
			
			count = (int) smc.queryForObject(
					"hotelroom.selectRoomUserCheck", roomUser);
			
		} catch (SQLException e) {
			count = 0;
			e.printStackTrace();
		}
		
		return count;
	}*/


}
