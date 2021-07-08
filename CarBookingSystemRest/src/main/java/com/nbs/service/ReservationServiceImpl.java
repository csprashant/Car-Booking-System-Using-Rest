package com.nbs.service;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import com.nbs.convertor.ReservationConvertor;
import com.nbs.dto.ReservationnDto;
import com.nbs.exception.UserNotFoundException;
import com.nbs.exception.VehicleNotFoundException;
import com.nbs.model.Reservation;
import com.nbs.model.User;
import com.nbs.model.Vehicle;
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
	public String bookReservation(ReservationnDto reservationDto) {
		var reservation = new ReservationConvertor().dtoToEntity(reservationDto);
		// loading existing user
		Optional<User> user = userRepository.findById(Integer.valueOf(reservationDto.getUserId()));
		// loading existing VehicleDto
		Optional<Vehicle> vehicle = vehicleRepository.findById(Integer.valueOf(reservationDto.getVehicleId()));
		// adding user data to reservation
		if(user.isPresent())
			reservation.setUser(user.get());
		else 
			throw new UserNotFoundException("");
		// adding vehicle data to reservation
		if(vehicle.isPresent())
			reservation.setVehicle(vehicle.get());
		else
			throw new VehicleNotFoundException("");
		var res = "";
		try {
			repository.save(reservation);
			return "Reservation created";
		}catch (Exception ee) {
			ee.printStackTrace();
			res = "Reservation faild";
		}
		return res;
}
public List<ReservationnDto> fetchAllReservationDetails() {
	List<Reservation> listReservation = (List<Reservation>)repository.findAll();
	return  new ReservationConvertor().entityToDto(listReservation);
}
}