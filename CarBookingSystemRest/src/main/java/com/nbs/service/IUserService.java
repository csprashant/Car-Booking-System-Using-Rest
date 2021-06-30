package com.nbs.service;

import java.util.List;

import com.nbs.model.User;


public interface IUserService {
	public User saveUser(User user);
	public List<User> getAllUserInfo();
	public User getUserInfo(Integer userId);
	public void deleteUser(Integer userId);
	public User login(String emailId, String password);
	public String  updateUser(User user);
	

}
