package com.shy.covidDashboard.service.impl;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.shy.covidDashboard.exception.UsernameAlreadyExistException;
import com.shy.covidDashboard.model.TemperatureDetail;
import com.shy.covidDashboard.model.User;
import com.shy.covidDashboard.repository.TemperatureDetailRepository;
import com.shy.covidDashboard.repository.UserRepository;
import com.shy.covidDashboard.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	Logger log = LogManager.getLogger(UserServiceImpl.class);

	@Autowired
	UserRepository userRepository;

	@Autowired
	TemperatureDetailRepository temperatureDetailRepository;

	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;

	@Override
	public void registerUser(User user) {

		log.debug("new user registration:" + user);
		Optional<User> retrievedUser = userRepository.findByUserName(user.getUserName());

		retrievedUser.ifPresent(existingUser -> {
			throw new UsernameAlreadyExistException();
		});

		log.debug("username not registered before:" + user.getUserName());
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		User savedUser = userRepository.save(user);
		log.debug("new user saved:" + savedUser);

	}

	@Override
	public void addTemperatureReading(Principal principal, TemperatureDetail tempDtls) {
		Optional<User> optionalUser = userRepository.findByUserName(principal.getName());
		optionalUser.ifPresent(user -> {
			tempDtls.setUser(user);
			temperatureDetailRepository.save(tempDtls);
		});

	}

	@Override
	public List<TemperatureDetail> getAllTemperatureReadings(Principal principal) {
		Optional<User> optionalUser = userRepository.findByUserName(principal.getName());
		optionalUser.orElseThrow(RuntimeException::new);
		return optionalUser.map(user -> temperatureDetailRepository.findAllByUserOrderByTimestamp(user)).get();

	}

}
