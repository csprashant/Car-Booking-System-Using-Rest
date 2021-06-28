package com.nbs.service;

import java.util.List;

import com.nbs.dto.ReservationnDto;

public interface IReservationService {
	public boolean bookReservation(ReservationnDto rvo) throws Exception;
	public List<ReservationnDto> fetchAllReservationDetails();
}
