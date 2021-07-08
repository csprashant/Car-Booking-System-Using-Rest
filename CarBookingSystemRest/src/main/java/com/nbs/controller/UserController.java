package com.nbs.controller;

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
import com.nbs.convertor.UserConvertor;
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
	 * 
	 * @param user    {@link UserDto} class object must provide value for Name,
	 *                Email id and password to create user account
	 * @param result  {@link BindingResult} used for validation purpose
	 * @param request {@link HttpServletRequest}
	 * @return returns success message if the user saved successfully
	 */

	@PostMapping("/add-user")
	public String saveUser(@Valid @RequestBody UserDto userdto, BindingResult result) {
		var user = new User();
		if (!result.hasErrors()) {
			mapper.map(userdto, user);
			user.setPassword(passwordEncoder.encode(user.getPassword()));
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

	@GetMapping("/users")
	@Secured("ROLE_ADMIN")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public List<UserDto> showAllUser() {
		return new UserConvertor().entityToDto(userService.getAllUserInfo());
	}

	/**
	 * Display a user details  by id
	 * 
	 * @param id user id
	 * @return {@link UserDto}
	 */

	@GetMapping("/{id}")
	@Secured("ROLE_ADMIN")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public UserDto getUser(@PathVariable Integer id) {
		return new UserConvertor().entityToDto(userService.getUserInfo(id));
	}

	/**
	 * Performs Update operation
	 * 
	 * @param user    {@link UserDto} class must provide Id and the information
	 *                which you want to change
	 * @param request {@link HttpServletRequest}
	 * @return return success message if the record updated .
	 */

	@PutMapping("/update-user/{id}")
	@Secured("ROLE_ADMIN")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public String updateUser(@Valid @RequestBody UserDto userdto, BindingResult result, @PathVariable Integer id) {
		if (!result.hasErrors()) {
			var user1 = new UserConvertor().dtoToEntity(userdto);
			user1.setPassword(passwordEncoder.encode(userdto.getPassword()));
			userService.updateUser(id, user1);
			return "Record updated";
		} else
			return result.getAllErrors().toString();
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
	public String deleteUser(@PathVariable Integer id) {
		return userService.deleteUser(id);
	}

}
