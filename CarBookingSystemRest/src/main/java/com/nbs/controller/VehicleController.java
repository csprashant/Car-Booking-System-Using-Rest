package com.nbs.controller;

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
import com.nbs.service.IVehicleService;

@RestController
public class VehicleController {
	@Autowired
	private IVehicleService vehicleService;
	HttpSession session = null;
	User user1 = null;
	private final String msg = "you dont have  privilege for this page ";
	
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
