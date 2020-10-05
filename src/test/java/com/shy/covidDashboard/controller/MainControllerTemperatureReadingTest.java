package com.shy.covidDashboard.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;

import java.security.Principal;
import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.validation.BindingResult;

import com.shy.covidDashboard.model.TemperatureDetail;
import com.shy.covidDashboard.model.User;
import com.shy.covidDashboard.service.UserService;


@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest
class MainControllerTemperatureReadingTest {
	
	private static final String SAVE_TEMPERATURE_URL = "/saveTemperatureReading";

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	UserService userService;

	@Test
	void successfulWhenSavingTemperatureDetailWithValidData() throws Exception {
		
		Principal mockPrincipal = Mockito.mock(Principal.class);
		
		TemperatureDetail temperatureDtl=new TemperatureDetail();
		temperatureDtl.setTemperature(10.0);
		temperatureDtl.setTimestamp(new Date());
		
		doNothing().when(userService).addTemperatureReading(any(Principal.class),any(TemperatureDetail.class));

		MvcResult mvcResult = mockMvc
				.perform(MockMvcRequestBuilders.post(SAVE_TEMPERATURE_URL)
						.principal(mockPrincipal)
				.flashAttr("tempDetail", temperatureDtl))
				.andReturn();
		
		assertEquals("redirect:/temperatureTracker?success", mvcResult.getModelAndView().getViewName());
	}
}

