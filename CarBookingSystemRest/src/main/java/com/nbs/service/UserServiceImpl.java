package com.nbs.service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.nbs.exception.UserNotFoundException;
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
		return  repository.findAll();
	}
	
	/**
 	*Returns single UserDto class object
 	*@param userId  A Integer value represents userId 
    */
	
	public User getUserInfo(Integer userId) {
		  Optional<User> user = repository.findById(userId);
		  if(user.isPresent())
			  return user.get();
		  else
			  throw new UserNotFoundException("");
	}

	/**
	*	Deletes single UserDto
	*@param vehicleId  a Integer value represents userId
	*/
	
	public String deleteUser(Integer userId) {
  Optional<User> user = repository.findById(userId);
		if(user.isPresent()) {
			repository.deleteById(userId);
			return "Deleted";}
		else
			throw new UserNotFoundException("User not found");
		
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
	
	public User  updateUser(Integer id,User user) {
			Optional<User> existingUser = repository.findById(id);
			if(existingUser.isPresent()) {
			existingUser.get().setName(user.getName());
			existingUser.get().setEmail(user.getEmail());
			existingUser.get().setPassword(user.getPassword());
			return repository.save(existingUser.get());
			}
			else
				throw new UserNotFoundException("User not found");
	}
}
