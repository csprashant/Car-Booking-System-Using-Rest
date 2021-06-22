package com.nbs.controller;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.nbs.model.User;
import com.nbs.model.Vehicle;
import com.nbs.service.ReservationService;
import com.nbs.service.UserService;
import com.nbs.service.VehicleService;
import com.nbs.vo.ReservationnVo;

@RestController
public class MainController {
	@Autowired
	private UserService userService;
	@Autowired
	private VehicleService vehicleService;
	@Autowired
	private ReservationService reservationService;
	HttpSession session = null;
	User user1 = null;

	@GetMapping("/")
	public String home(@ModelAttribute("User") User user) {

		return "login";
	}

	@GetMapping("/welcome")
	public String showWelcome(HttpServletRequest request, Map<String, Object> m) {

		if (valid(request))
			return "welcome";
		else {
			session.invalidate();
			m.put("msg", "you dont have  privilege for this page");
			return "landing";
		}
	}

	@GetMapping("/welcomeuser")
	public String mapperWelcomeUserPage() {
		return "welcomeuser";
	}

	@PostMapping("/login")
	public String handleLogin(@RequestBody User user, BindingResult result, HttpServletRequest request)
			throws Exception {

		if (user.getEmail().length() == 0 || user.getPassword().length() == 0) {
			return "login";
		} else {
			User user1 = userService.login(user.getEmail(), user.getPassword());
			if (user1 != null) {
				session = request.getSession();
				session.setAttribute("user", user1);
				if (user1.getType() == 1) {
					return "Admin login";
				} else {
					return "Normal user logined";
				}
			} else {
		
				return  "you have enter wrong credentials ";
			}
		}

	}

	@PostMapping("/user")
	public String  saveUser(@Valid @RequestBody User user,BindingResult result) {
		if(!result.hasErrors()) {
				userService.saveUser(user);
		return "Data saved ";}
		else
		return result.getAllErrors().toString();
	}

	@GetMapping("/user")
	public List<User> showAllUser() {
		return userService.getAllUserInfo();
	}

	@PutMapping("/updateuser")
	public String updateUser(@RequestBody User user) {
		User user1 = userService.updateUser(user);
		if (user1 != null)
			return "record updated";
		else
			return "Record not found";
	}

	@DeleteMapping("/delete/{id}")
	public String deleteUser(@PathVariable Integer id) {
		return userService.deleteUser(id);
	}

// Vehicle Related operation 
	@PostMapping("/vehicle")
	public String addVehicle(@Valid @RequestBody Vehicle vehicle,BindingResult result) {
		if(!result.hasErrors()) {
			 vehicleService.saveVehicle(vehicle);;
	return "Data saved ";}
	else
	return result.getAllErrors().toString();
	}

	@GetMapping("/vehicle")
	public List<Vehicle> showAllVehicle() {
		return vehicleService.getAllVehicleInfo();

	}

	@PutMapping("/update-vehicle")
	public String handlerUpdateVehicle(@RequestBody Vehicle vehicle) {
		Vehicle v = vehicleService.updateVehicle(vehicle);
		if (v != null)
			return "record updated";
		else
			return "Record not found";
	}

	@DeleteMapping("/delete-vehicle/{id}")
	public String deleteVehicle(@PathVariable Integer id) {
		return vehicleService.deleteVehicle(id);
	}

	@GetMapping("/reservation")
	public List<ReservationnVo> mapperListReservatinHistory() {
		return reservationService.fetchAllReservationDetails();

	}
/**
 * 
 * @param reservationnVo Must  provide value for userID,vehiclId,fromDate,todate
 * @return	
 * @throws Exception
 */
	@PostMapping("/reservation")
	public String handlerCreateReservation(@RequestBody ReservationnVo reservationnVo)
			throws Exception {

		if (new SimpleDateFormat("yyyy-MM-dd").parse(reservationnVo.getFromDate())
				.compareTo(new SimpleDateFormat("yyyy-MM-dd").parse(reservationnVo.getToDate())) < 0) {
			boolean res = false;
			try {
				res = reservationService.bookReservation(reservationnVo);
				res = true;
			} catch (Exception e) {
				e.printStackTrace();
				res = false;
			}
			if (res = true) {
				return "Reservation created successfully";
			} else
				return "Reservation Faild";

		} else
			return "from date must be before to date";

	}


	@GetMapping("/logout")
	public String logout(@ModelAttribute("User") User user, HttpServletRequest request) throws Exception {
		session = request.getSession(false);
		session.invalidate();
		return "login";
	}

	public boolean valid(HttpServletRequest request) {
		session = request.getSession(false);
		User user1 = (User) session.getAttribute("user");
		if (user1.getType() == 1)
			return true;
		else
			return false;
	}

	@ExceptionHandler(value = NullPointerException.class)
	public String customException(Model m) {
		m.addAttribute("msg", "you dont have  privilege for this page");
		return "landing";
	}

}
