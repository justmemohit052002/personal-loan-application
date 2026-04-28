package com.loanapp.security;

import com.loanapp.entity.User;
import com.loanapp.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

		String normalizedEmail = email.toLowerCase().trim();

		User user = userRepository.findByEmailAndIsDeletedFalse(normalizedEmail)
				.orElseThrow(() -> new UsernameNotFoundException("Invalid email or password"));

		return new CustomUserDetails(user);
	}
}