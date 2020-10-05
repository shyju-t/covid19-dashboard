package com.shy.covidDashboard.service;

import java.security.Principal;
import java.util.List;

import com.shy.covidDashboard.model.TemperatureDetail;
import com.shy.covidDashboard.model.User;

public interface UserService {
	public void registerUser(User user);
	public void addTemperatureReading(Principal principal, TemperatureDetail tempDtls);
	public List<TemperatureDetail> getAllTemperatureReadings(Principal principal);
}
