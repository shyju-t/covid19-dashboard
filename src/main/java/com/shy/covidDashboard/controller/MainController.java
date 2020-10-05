package com.shy.covidDashboard.controller;

import java.security.Principal;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.shy.covidDashboard.exception.UsernameAlreadyExistException;
import com.shy.covidDashboard.model.TemperatureDetail;
import com.shy.covidDashboard.model.User;
import com.shy.covidDashboard.service.UserService;

@Controller
public class MainController {
	@Autowired
	UserService userService;

	@RequestMapping("/login")
	public String login() {
		return "login";
	}

	@RequestMapping("/")
	public String defaultPage() {
		return "login";
	}

	@RequestMapping("/register")
	public String register(Model model) {
		User user = new User();
		model.addAttribute("user", user);
		return "register";
	}

	@RequestMapping(path = "/saveRegisteration", method = RequestMethod.POST)
	public String saveRegisteration(@Valid @ModelAttribute User user, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "register";
		}
		try {
			userService.registerUser(user);
		}catch(UsernameAlreadyExistException e) {
			bindingResult.rejectValue("userName", null, "There is already an account registered with that email");
			return "register";
		}
		return "redirect:/register?success";
	}

	@RequestMapping("/home")
	public String home() {
		return "home.html";
	}
	
	@RequestMapping(path="/temperatureTracker")
	public String getTemperatureTracker(Principal principal,Model model) {
		TemperatureDetail tempDetail=new TemperatureDetail();
		model.addAttribute("tempDetail",tempDetail);
		
		List<TemperatureDetail> tempReadings=userService.getAllTemperatureReadings(principal);
		model.addAttribute("tempReadings",tempReadings);
		return "temperatureTracker";
	}
	
	@RequestMapping(path="/saveTemperatureReading", method = RequestMethod.POST)
	public String saveTemperatureReading(@ModelAttribute TemperatureDetail tempDetail,Principal principal) {
		System.out.println(tempDetail);
		userService.addTemperatureReading(principal, tempDetail);
		return "redirect:/temperatureTracker?success";
	}

}
