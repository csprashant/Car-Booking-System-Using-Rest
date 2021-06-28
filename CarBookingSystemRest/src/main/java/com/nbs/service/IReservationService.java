package com.nbs.service;

import java.util.List;

import com.nbs.vo.ReservationnVo;

public interface IReservationService {
	public boolean bookReservation(ReservationnVo rvo) throws Exception;
	public List<ReservationnVo> fetchAllReservationDetails();
}
