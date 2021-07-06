package com.nbs.convertor;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;

import com.nbs.dto.UserDto;
import com.nbs.dto.VehicleDto;
import com.nbs.model.User;
import com.nbs.model.Vehicle;

public class VehicleConvertor {
	public VehicleDto entityToDto(Vehicle vehicle) {
		return new ModelMapper().map(vehicle,VehicleDto.class);
	}
	public List<VehicleDto> entityToDto(List<Vehicle> vehicle){
		return vehicle.stream().map( x -> entityToDto(x)).collect(Collectors.toList());
	}

	public Vehicle dtoToEntity(VehicleDto dto) {
		return new ModelMapper().map(dto, Vehicle.class);
		}
	public List<Vehicle> dtoToEntity(List<VehicleDto> dto){
		return dto.stream().map( x -> dtoToEntity(x)).collect(Collectors.toList());
	}
	

}
