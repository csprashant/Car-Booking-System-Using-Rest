package com.nbs.service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.nbs.model.User;
import com.nbs.repository.UserRepository;

@Service
public class UserServiceImpl implements IUserService {
	private final UserRepository repository;
	
	
	public UserServiceImpl(UserRepository repository) {
		this.repository = repository;
	}
	/**
	 * save a new UserDto class object
	 * @param user UserDto class object
	 */
	
	public User saveUser(User user) {
		user.setType("ROLE_USER");
		user.setUpdated(new Timestamp(new Date().getTime()));
		return repository.save(user);
	}

	/**
	 *  Returns all UserDto details
	 *@return returns List<UserDto> List contains Users 
	 */
	
	public List<User> getAllUserInfo() {
		return (List<User>) repository.findAll();
	}
	
	/**
 	*Returns single UserDto class object
 	*@param userId  A Integer value represents userId 
    */
	
	public User getUserInfo(Integer userId) {
		return  repository.findById(userId).get();
	}

	/**
	*	Deletes single UserDto
	*@param vehicleId  a Integer value represents userId
	*/
	
	public void deleteUser(Integer userId) {
		System.out.println(2);
		repository.deleteById(userId);
		
	}
	
	/**
	 * checks the correct user for user name and password
	 * @param emailId	 email id of the user
	 * @param password password of the user
	 * @return	UserDto object if user name and password matched
	 */
	
	public User login(String emailId, String password) {
		return repository.findByEmailAndPassword(emailId, password);
	}
	
	public String  updateUser(User user) {
		var existingUser=repository.findById(user.getId()).get();
		if(existingUser!=null) {
			existingUser.setName(user.getName());
			existingUser.setEmail(user.getEmail());
			existingUser.setPassword(user.getPassword());
			repository.save(existingUser);
			return "Record updated";
		}
		else
			return "record not found";
		}
}
