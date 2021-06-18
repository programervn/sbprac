package com.thaipd.sbprac.security.services;

import com.thaipd.sbprac.entity.User;
import com.thaipd.sbprac.security.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	private static final Logger logger = LoggerFactory.getLogger(UserDetailsServiceImpl.class);
	@Autowired
	UserRepository userRepository;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		logger.debug("Find username to login: {}", username);
//		User user = userRepository.findByUsername(username)
//				.orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));
		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new ResponseStatusException(
						HttpStatus.UNAUTHORIZED, "User Not Found with username: " + username));

		return UserDetailsImpl.build(user);
	}

}
