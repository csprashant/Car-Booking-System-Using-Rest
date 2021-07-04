package com.nbs.config;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.nbs.model.User;
import com.nbs.repository.UserRepository;
@Service
public class CustomUserDetailsServiceImpl implements UserDetailsService {
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> user= userRepository.getUserByEmail(username);
		return user.map(CustomUserDetails :: new).orElseThrow(()-> new UsernameNotFoundException (username + "Not found") );
	}

}
