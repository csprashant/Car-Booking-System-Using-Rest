package com.nbs.convertor;

import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import com.nbs.dto.UserDto;
import com.nbs.model.User;

public class UserConvertor {
	public UserDto entityToDto(User user) {
		return new ModelMapper().map(user,UserDto.class);
	}
	public List<UserDto> entityToDto(List<User> user){
		return user.stream().map( x -> entityToDto(x)).collect(Collectors.toList());
	}

	public User dtoToEntity(UserDto userDto) {
		return new ModelMapper().map(userDto, User.class);
		}
	public List<User> dtoToEntity(List<UserDto> dto){
		return dto.stream().map( x -> dtoToEntity(x)).collect(Collectors.toList());
	}
	

}
