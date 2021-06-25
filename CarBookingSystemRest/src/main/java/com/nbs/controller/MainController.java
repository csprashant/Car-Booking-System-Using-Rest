package com.nbs.controller;

import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.nbs.model.User;
import com.nbs.model.Vehicle;
import com.nbs.service.IReservationService;
import com.nbs.service.IUserService;
import com.nbs.service.IVehicleService;
import com.nbs.vo.ReservationnVo;

@RestController
public class MainController {

	@Autowired
	private IUserService  userService;
	@Autowired
	private IVehicleService vehicleService;
	@Autowired
	private IReservationService reservationService;
	HttpSession session = null;
	User user1 = null;
	private final String msg = "you dont have  privilege for this page ";

	/**
	 * performs login operation
	 * @param user    {@link User} class provide only emailid and password for login purpose
	 * @param request {@link HttpServletRequest}
	 * @return user object if login operation is successful
	 * @throws Exception
	 */
	@PostMapping("/login")
	public User handleLogin(@RequestBody User user, HttpServletRequest request) throws Exception {
		if (user.getEmail().length() == 0 || user.getPassword().length() == 0) {
			return null;
		} else {
			User user1 = userService.login(user.getEmail(), user.getPassword());
			if (user1 != null) {
				session = request.getSession();
				session.setAttribute("user", user1);
				if (user1.getType() == 1) {
					return user1;}
				else
					return user1;
				} else
				throw new RuntimeException("Invalid emailid and password");
			}
	}

	/**
	 * Performs persist operation on User Entity
	 * 
	 * @param user    {@link User} class object must provide value for Name, Email id  and password to create user account
	 * @param result  {@link BindingResult} used for validation purpose
	 * @param request {@link HttpServletRequest}
	 * @return returns success message if the user saved successfully
	 */
	@PostMapping("/user")
	public String saveUser(@Valid @RequestBody User user, BindingResult result, HttpServletRequest request) {
		/*
		 * if (!result.hasErrors()) { if (valid(request)) { userService.saveUser(user);
		 * return "Data saved "; } else return msg; } else return
		 * result.getAllErrors().toString();
		 */
		if(valid(request)){
			if(!result.hasErrors()){
				userService.saveUser(user);
				return "Data saved";
				}
			else
				return result.getAllErrors().toString();
		}
		else
			throw new RuntimeException(msg);
		}

	/**
	 * Display all users information
	 * 
	 * @param request {@link HttpServletRequest}
	 * @return List<User>
	 */
	@GetMapping("/users")
	public List<User> showAllUser(HttpServletRequest request) {
		if (valid(request))
			return userService.getAllUserInfo();
		else
			throw new RuntimeException(msg);
	}

	/**
	 * Performs Update operation
	 * @param user    {@link User} class must provide Id and the information which you want to change
	 * @param request {@link HttpServletRequest}
	 * @return return success message if the record updated .
	 */
	@PutMapping("/update-user")
	public String updateUser(@RequestBody User user, HttpServletRequest request) {
		if (valid(request)) {
			return userService.updateUser(user);
		}else {
			throw new RuntimeException(msg);
		}
	}

	/**
	 * Performs deletion operation
	 * @param id      represents userId
	 * @param request {@link HttpServletRequest}
	 * @return return success message if the record Deleted
	 */
	@DeleteMapping("/delete-user/{id}")
	public String deleteUser(@PathVariable Integer id, HttpServletRequest request) {
		if (valid(request))
			return userService.deleteUser(id);
		else
			throw new RuntimeException(msg);
		}

	/**
	 * Performs insertion operation on Vehicle class
	 * @param vehicle {@link Vehicle} class object must provide value for vehicle name, color and for vehicle number
	 * @param result  {@link BindingResult}
	 * @param request {@link HttpServletRequest}
	 * @return returns success message if the user saved
	 */
	@PostMapping("/vehicle")
	public String addVehicle(@Valid @RequestBody Vehicle vehicle, BindingResult result, HttpServletRequest request) {
		if (valid(request)) {
			if (!result.hasErrors()) {
				vehicleService.saveVehicle(vehicle);
				return "Data saved ";
			} else
				return result.getAllErrors().toString();
		} else {
			throw new RuntimeException(msg);
		}
	}

	/**
	 * Display all Vehicle information
	 * @return List<Vehicle> contains vehicles information
	 */
	@GetMapping("/vehicles")
	public List<Vehicle> getVehicles() {
		return vehicleService.getAllVehicleInfo();
	}
	/**
	 * Performs update operation on Vehicle class
	 * @param vehicle {@link Vehicle} class must provide value for id ,and the information which you want to change
	 * @param request {@link HttpServletRequest}
	 * @return returns success message if the vehicle information updated.
	 */
	@PutMapping("/update-vehicle")
	public String updateVehicle(@RequestBody Vehicle vehicle, HttpServletRequest request) {
		if (valid(request)) {
			return vehicleService.updateVehicle(vehicle);
		} else {
			throw new RuntimeException(msg);
		}
	}
	/**
	 * Performs Deletion on vehicles 
	 * @param id      vehicle id which you want to delete
	 * @param request {@link HttpServletRequest}
	 * @return return success message if the record deleted
	 */
	@DeleteMapping("/delete-vehicle/{id}")
	public String deleteVehicle(@PathVariable Integer id, HttpServletRequest request) {
		if (valid(request)) {
			return vehicleService.deleteVehicle(id);
		} else
			throw new RuntimeException(msg);
	}
	/**
	 * Display Reservation information 
	 * @param request {@link HttpServletRequest}
	 * @return information about all reservations
	 */
	@GetMapping("/reservations")
	public List<ReservationnVo> mapperListReservatinHistory(HttpServletRequest request) {
		if (valid(request)) {
			return reservationService.fetchAllReservationDetails();
		} else
			throw new RuntimeException(msg);
	}
	/**
	 * Method to create reservation 
	 * @param reservationnVo Must provide value for userID,vehiclId,fromDate,todate
	 * @return String
	 * @throws Exception
	 */
	@PostMapping("/reservation")
	public String handlerCreateReservation(@RequestBody ReservationnVo reservationnVo) throws Exception {
		boolean res;
		if (new SimpleDateFormat("yyyy-MM-dd").parse(reservationnVo.getFromDate())
				.compareTo(new SimpleDateFormat("yyyy-MM-dd").parse(reservationnVo.getToDate())) < 0) {
			res = false;
			try {
				res = reservationService.bookReservation(reservationnVo);
				res = true;}
			catch (Exception e) {
				e.printStackTrace();
				res = false;}
			if (res = true) {
				return "Reservation created successfully";}
			else
				return "Reservation Faild";
			}
		else
			return "from date must be before to date";
	}
	@GetMapping("/logout")
	public String logout(HttpServletRequest request) throws Exception {
		session = request.getSession(false);
		session.invalidate();
		return "logout Successflly";
	}
	/**
	 * method for providing validation based on type of user 
	 * @param request
	 * @return
	 */
	public boolean valid(HttpServletRequest request) {
		session = request.getSession(false);
		User user1 = (User) session.getAttribute("user");
		if (user1.getType() == 1)
			return true;
		else
			return false;
	}
	/**
	 * method for handling custom exceptions
	 * @return String
	 */
	@ExceptionHandler(value =  RuntimeException.class)
	public String customException(Exception e) {
		return e.getMessage();
	}
	@ExceptionHandler(value = NullPointerException.class)
	public String customException1() {
		return msg;
	}
}
