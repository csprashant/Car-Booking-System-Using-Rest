package com.nbs.controller;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.nbs.convertor.ReservationConvertor;
import com.nbs.dto.ReservationnDto;
import com.nbs.service.IReservationService;

@RestController
@RequestMapping("/reservation")
public class ReservationController {
	@Autowired
	private IReservationService reservationService;
	
	/**
	 * Method to create reservation 
	 * @param reservationnDto Must provide value for userID,vehiclId,fromDate,todate
	 * @return String
	 * @throws Exception
	 */
	@PostMapping("/create-reservation")
	@PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_USER')")
	public String handlerCreateReservation(@RequestBody ReservationnDto reservationnDto) throws Exception {
		if (new ReservationConvertor().valid(reservationnDto.getFromDate(), reservationnDto.getToDate())) 
			return reservationService.bookReservation(reservationnDto);
		else
			return "from date must be before to date";
	}
	/**
	 * Display Reservation information 
	 * @param request {@link HttpServletRequest}
	 * @return information about all reservations
	 */
	
	@GetMapping("/reservations")
	@Secured("ROLE_ADMIN")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public List<ReservationnDto> mapperListReservatinHistory() {
	return reservationService.fetchAllReservationDetails();
	}
}
