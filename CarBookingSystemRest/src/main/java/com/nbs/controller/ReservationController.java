package com.nbs.controller;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.nbs.dto.ReservationnDto;
import com.nbs.model.User;
import com.nbs.service.IReservationService;

@RestController
public class ReservationController {
	@Autowired
	private IReservationService reservationService;
	HttpSession session = null;
	User user1 = null;
	private static final String msg = "you dont have  privilege for this page ";

	/**
	 * Display Reservation information 
	 * @param request {@link HttpServletRequest}
	 * @return information about all reservations
	 */
	
	@GetMapping("/reservations")
	public List<ReservationnDto> mapperListReservatinHistory(HttpServletRequest request) {
		if (valid(request)) {
			return reservationService.fetchAllReservationDetails();
		} else
			throw new RuntimeException(msg);
	}
	/**
	 * Method to create reservation 
	 * @param reservationnDto Must provide value for userID,vehiclId,fromDate,todate
	 * @return String
	 * @throws Exception
	 */
	@PostMapping("/reservation")
	public String handlerCreateReservation(@RequestBody ReservationnDto reservationnDto) throws Exception {
		boolean res;
		if (new SimpleDateFormat("yyyy-MM-dd").parse(reservationnDto.getFromDate())
				.compareTo(new SimpleDateFormat("yyyy-MM-dd").parse(reservationnDto.getToDate())) < 0) {
			res = false;
			try {
				res = reservationService.bookReservation(reservationnDto);
				}
			catch (Exception e) {
				e.printStackTrace();
				res = false;
				}
			if (res) 
				return "Reservation created successfully";
			else
				return "Reservation Faild";
			}
		else
			return "from date must be before to date";
	}
	
	
	public boolean valid(HttpServletRequest request) {
		return ( (User)  request.getSession(false).getAttribute("user")).getType() ==1;
	}
}
