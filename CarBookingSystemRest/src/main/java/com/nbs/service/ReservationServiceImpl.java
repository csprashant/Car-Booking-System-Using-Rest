package com.nbs.service;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.stereotype.Service;

import com.nbs.convertor.ReservationConvertor;
import com.nbs.dto.ReservationnDto;
import com.nbs.model.Reservation;
import com.nbs.repository.ReservationRepository;
import com.nbs.repository.UserRepository;
import com.nbs.repository.VehicleRepository;

@Service
public class ReservationServiceImpl implements  IReservationService {
	private ReservationRepository repository;
	private UserRepository userRepository;
	private VehicleRepository vehicleRepository;
	
	public ReservationServiceImpl(ReservationRepository repository, UserRepository userRepository,
			VehicleRepository vehicleRepository) {
		this.repository = repository;
		this.userRepository = userRepository;
		this.vehicleRepository = vehicleRepository;
	}
	public boolean bookReservation(ReservationnDto reservationDto) {
		var reservation = new ReservationConvertor().dtoToEntity(reservationDto);
		// loading existing user
		var user = userRepository.findById(Integer.valueOf(reservationDto.getUserId())).get();
		// loading existing VehicleDto
		var vehicle = vehicleRepository.findById(Integer.valueOf(reservationDto.getVehicleId())).get();
		// adding user data to reservation
		reservation.setUser(user);
		// adding vehicle data to reservation
		reservation.setVehicle(vehicle);
		var res = false;
		try {
			repository.save(reservation);
			res = true;
		}catch (Exception ee) {
			ee.printStackTrace();
			res = false;
		}
		return res;
}
public List<ReservationnDto> fetchAllReservationDetails() {
	List<Reservation> listReservation = (List<Reservation>)repository.findAll();
	return  new ReservationConvertor().entityToDto(listReservation);
}
}