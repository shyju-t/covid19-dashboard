package com.shy.covidDashboard.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shy.covidDashboard.model.TemperatureDetail;
import com.shy.covidDashboard.model.User;

public interface TemperatureDetailRepository  extends JpaRepository<TemperatureDetail, Long>{
	public List<TemperatureDetail> findAllByUserOrderByTimestamp(User user);
}
