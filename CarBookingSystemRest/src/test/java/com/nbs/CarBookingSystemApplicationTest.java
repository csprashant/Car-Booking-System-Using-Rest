package com.nbs;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.nbs.model.Reservation;
import com.nbs.model.User;
import com.nbs.model.Vehicle;
import com.nbs.repository.ReservationRepository;
import com.nbs.repository.UserRepository;
import com.nbs.repository.VehicleRepository;
import com.nbs.service.IReservationService;
import com.nbs.service.IUserService;
import com.nbs.service.IVehicleService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CarBookingSystemApplicationTest {
	@Autowired
	private IUserService iUserService;

	@Autowired
	private IVehicleService iVehicleService;

	@Autowired
	private IReservationService iReservationService;

	@MockBean
	private UserRepository userRepository;

	@MockBean
	private VehicleRepository vehicleRepository;

	@MockBean
	private ReservationRepository reservationRepository;

	@Test
	public void getUsersTest() {
		when(userRepository.findAll()).thenReturn(Stream.of(new User("Prashant", "Prashant@gmail.com", "9211"),
				new User("Prashant", "Prashant@gmail.com", "9211")).collect(Collectors.toList()));
		assertEquals(2, iUserService.getAllUserInfo().size());
	}

	@Test
	public void getVehiclesTest() {
		when(vehicleRepository.findAll()).thenReturn(Stream.of(new Vehicle("Tata Magic", "Black", "CG KL 07 8956"),
				new Vehicle("Tata Magic", "Black", "CG KL 07 8956")).collect(Collectors.toList()));
		assertEquals(2, iVehicleService.getAllVehicleInfo().size());
	}

	@Test
	public void getReservationsTest() {
		Reservation re = new Reservation();
		Reservation re1 = new Reservation();
		User user = new User("Prashant", "Prashant@gmail.com", "9211");
		user.setId(102);
		Vehicle vehicle = new Vehicle("Tata Magic", "Black", "CG KL 07 8956");
		vehicle.setId(1);
		re.setUser(user);
		re.setVehicle(vehicle);
		re1.setUser(user);
		re1.setVehicle(vehicle);
		when(reservationRepository.findAll()).thenReturn(Stream.of(re, re1).collect(Collectors.toList()));
		assertEquals(2, iReservationService.fetchAllReservationDetails().size());
	}

	@Test
	public void saveUserTest() {
		User user = new User("Prashant", "Prashant@gmail.com", "9211");
		when(userRepository.save(user)).thenReturn(user);
		assertEquals(user, iUserService.saveUser(user));
	}

	@Test
	public void saveVehicleTest() {
		Vehicle vehicle = new Vehicle("Tata Magic", "Black", "CG KL 07 8956");
		when(vehicleRepository.save(vehicle)).thenReturn(vehicle);
		assertEquals(vehicle, iVehicleService.saveVehicle(vehicle));
	}

	@Test
	public void updateUserTest() {
		User user = new User("Prashant", "Prashant@gmail.com", "9211");
		user.setId(105);
		assertEquals("Prashant", iUserService.updateUser(105, user).getName());

	}

	@Test
	public void deleteUserTest() {
		iUserService.deleteUser(1589);
		verify(userRepository, times(1)).deleteById(1589);
	}

	@Test
	public void deleteVehicleTest() {
		iVehicleService.deleteVehicle(12);
		verify(vehicleRepository, times(1)).deleteById(12);
	}

}
