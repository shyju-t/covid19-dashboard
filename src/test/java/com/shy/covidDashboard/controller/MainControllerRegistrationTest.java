package com.shy.covidDashboard.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.validation.BindingResult;

import com.shy.covidDashboard.model.User;
import com.shy.covidDashboard.service.UserService;


@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest
class MainControllerRegistrationTest {
	
	private static final String USER_REGISTERATION_URL = "/saveRegistration";

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	UserService userService;

	private User user;
	
	@BeforeEach
	void init() {
		user = new User();
		user.setFirstName("firstName");
		user.setLastName("lastName");
		user.setUserName("test@test.com");
		user.setPassword("passWord");
	}

	@Test
	void exceptionWhenRegisteringUserWithBlankLastName() throws Exception {

		doNothing().when(userService).registerUser(any(User.class));
		final String emptyLastName = "";
		user.setLastName(emptyLastName);

		MvcResult mvcResult = mockMvc
				.perform(MockMvcRequestBuilders.post(USER_REGISTERATION_URL)
				.flashAttr("user", user))
				.andReturn();
		
		BindingResult bindingResult=(BindingResult) mvcResult.getModelAndView().getModel().get("org.springframework.validation.BindingResult.user");
		assertEquals(2, bindingResult.getErrorCount());
		assertEquals("lastName", bindingResult.getFieldError().getField());
		assertEquals("register", mvcResult.getModelAndView().getViewName());
	}
	
	@Test
	void exceptionWhenRegisteringUserWithBlankFirstName() throws Exception {

		doNothing().when(userService).registerUser(any(User.class));
		final String emptyFirstName = "";
		user.setFirstName(emptyFirstName);

		MvcResult mvcResult = mockMvc
				.perform(MockMvcRequestBuilders.post(USER_REGISTERATION_URL)
				.flashAttr("user", user))
				.andReturn();
		
		BindingResult bindingResult=(BindingResult) mvcResult.getModelAndView().getModel().get("org.springframework.validation.BindingResult.user");
		assertEquals(2, bindingResult.getErrorCount());
		assertEquals("firstName", bindingResult.getFieldError().getField());
		assertEquals("register", mvcResult.getModelAndView().getViewName());
	}
	
	@Test
	void successfulWhenRegisteringUserWithValidData() throws Exception {

		doNothing().when(userService).registerUser(any(User.class));

		MvcResult mvcResult = mockMvc
				.perform(MockMvcRequestBuilders.post(USER_REGISTERATION_URL)
				.flashAttr("user", user))
				.andReturn();
		
		assertEquals("redirect:/register?success", mvcResult.getModelAndView().getViewName());
	}

}

