package kr.or.ddit.room.service;

import java.util.List;

import kr.or.ddit.room.dao.HotelRoomDaoImpl;
import kr.or.ddit.room.dao.IHotelRoomDao;
import kr.or.ddit.room.vo.HotelRoomVO;

public class HotelRoomServiceImpl implements IHotelRoomService{
	
private IHotelRoomDao dao;
	
	private static HotelRoomServiceImpl service;
	
	private HotelRoomServiceImpl() {
		dao = HotelRoomDaoImpl.getInstance();
	}
	
	public static HotelRoomServiceImpl getInstance() {
		if(service==null) service = new HotelRoomServiceImpl();
		return service;
	}

	@Override
	public int updateRoomCheckIn(HotelRoomVO roomVo) {
		// TODO Auto-generated method stub
		return dao.updateRoomCheckIn(roomVo);
	}

	@Override
	public int updateRoomCheckOut(HotelRoomVO roomVo) {
		// TODO Auto-generated method stub
		return dao.updateRoomCheckOut(roomVo);
	}

	@Override
	public List<HotelRoomVO> getAllHotelRoom() {
		// TODO Auto-generated method stub
		return dao.getAllHotelRoom();
	}

	@Override
	public String getRomUserCheckCount(int roomNo) {
		// TODO Auto-generated method stub
		return dao.getRomUserCheckCount(roomNo);
	}

	/*@Override
	public int getRomUserCheckCount(String roomUser) {
		// TODO Auto-generated method stub
		return dao.getRomUserCheckCount(roomUser);
	}*/

}
