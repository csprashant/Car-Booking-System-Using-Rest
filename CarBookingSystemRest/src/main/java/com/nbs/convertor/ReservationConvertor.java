package com.nbs.convertor;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;

import com.nbs.dto.ReservationnDto;
import com.nbs.dto.UserDto;
import com.nbs.model.Reservation;
import com.nbs.model.User;

public class ReservationConvertor {
	public ReservationnDto entityToDto(Reservation reservation) {
		return new ModelMapper().map(reservation,ReservationnDto.class);
	}
	public List<ReservationnDto> entityToDto(List<Reservation> listReservation){
		List<ReservationnDto> listDto=new ArrayList<>();
		int i;for ( i = 0; i < listReservation.size(); i++) {
			var reservationDto = new ReservationnDto();
			reservationDto.setId(listReservation.get(i).getId() + "");
			reservationDto.setUserId(listReservation.get(i).getUser().getId() + "");
			reservationDto.setUserName(listReservation.get(i).getUser().getName());
			reservationDto.setVehicleId(listReservation.get(i).getVehicle().getId()+ "");
			reservationDto.setVName(listReservation.get(i).getVehicle().getvName());
			reservationDto.setVNumber(listReservation.get(i).getVehicle().getvNumber());
			reservationDto.setFromDate((listReservation.get(i).getFromDate() + "").substring(0, 10));
			reservationDto.setToDate((listReservation.get(i).getToDate() + "").substring(0, 10));
			reservationDto.setStatus(listReservation.get(i).isStatus() + "");
			reservationDto.setUpdated(listReservation.get(i).getUpdated() + "");
			reservationDto.setCreated(listReservation.get(i).getCreated() + "");
			listDto.add(reservationDto);
		}
		return listDto;
	}

	public Reservation dtoToEntity(ReservationnDto reservationnDto) {
		var reservation=new ModelMapper().map(reservationnDto, Reservation.class);
		reservation.setUpdated(new Timestamp(new Date().getTime()));
		reservation.setStatus(true);
		return reservation; 
		}

	public List<Reservation> dtoToEntity(List<ReservationnDto> dto){
		return dto.stream().map( x -> dtoToEntity(x)).collect(Collectors.toList());
	}
	public boolean valid(String fromDate,String toDate) throws Exception{
		return new SimpleDateFormat("yyyy-MM-dd").parse(fromDate).compareTo(new SimpleDateFormat("yyyy-MM-dd").parse(toDate)) < 0;
	}
	

}
