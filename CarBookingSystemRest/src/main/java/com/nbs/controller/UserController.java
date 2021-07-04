package com.nbs.controller;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nbs.dto.UserDto;
import com.nbs.model.User;
import com.nbs.service.IUserService;

@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private IUserService userService;
	
	 @Autowired
	    private BCryptPasswordEncoder passwordEncoder;
	 
		ModelMapper mapper = new ModelMapper();

	/**
	 * Performs persist operation on UserDto Entity
	 * @param user    {@link UserDto} class object must provide value for Name, Email id and password to create user account
	 * @param result  {@link BindingResult} used for validation purpose
	 * @param request {@link HttpServletRequest}
	 * @return returns success message if the user saved successfully
	 */

	@PostMapping("/add-user")
	public String saveUser(@Valid @RequestBody UserDto userdto, BindingResult result, HttpServletRequest request) {
		var user = new User();
		if (!result.hasErrors()) {
			mapper.map(userdto, user);
	        user.setPassword( passwordEncoder.encode(user.getPassword()));
			userService.saveUser(user);
			return "Data saved";
		} else
			return result.getAllErrors().toString();
	}

	/**
	 * Display all users information
	 * 
	 * @param request {@link HttpServletRequest}
	 * @return List<UserDto>
	 */

	@GetMapping("/display-all-users")
	@Secured("ROLE_ADMIN")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public List<UserDto> showAllUser(HttpServletRequest request) {
		var user = userService.getAllUserInfo();
		List<UserDto> listdto = new ArrayList<>();
		UserDto dto;
		while (user.iterator().hasNext()) {
			dto = new UserDto();
			mapper.map(dto, user);
			listdto.add(dto);
		}
		return listdto;
	}

	/**
	 * Performs Update operation
	 * 
	 * @param user    {@link UserDto} class must provide Id and the information
	 *                which you want to change
	 * @param request {@link HttpServletRequest}
	 * @return return success message if the record updated .
	 */
	
	@PutMapping("/update-user")
	@Secured("ROLE_ADMIN")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public String updateUser(@RequestBody UserDto userdto, HttpServletRequest request) {
		var user1 = new User();
		mapper.map(userdto, user1);
		user1.setPassword( passwordEncoder.encode(user1.getPassword()));
		return userService.updateUser(user1);
	}

	/**
	 * Performs deletion operation
	 * 
	 * @param id      represents userId
	 * @param request {@link HttpServletRequest}
	 * @return return success message if the record Deleted
	 */

	@DeleteMapping("/delete-user/{id}")
	@Secured("ROLE_ADMIN")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public void deleteUser(@PathVariable Integer id, HttpServletRequest request) {
		userService.deleteUser(id);
	}
	
}
