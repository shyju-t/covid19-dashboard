package com.shy.covidDashboard.security;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.shy.covidDashboard.model.User;

public class CustomUserDetails implements UserDetails {

	private static final long serialVersionUID = 454418640491717231L;
	
	private String userName;
	private String password;
	private boolean active;
	private List<GrantedAuthority> authorities;
	
	public CustomUserDetails(User user) {
		this.userName=user.getUserName();
		this.password=user.getPassword();
		//All users are considered as active
		this.active=true;
		//currently there is only one authority
		this.authorities= Arrays.asList(new SimpleGrantedAuthority("USER"));
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.authorities;
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	@Override
	public String getUsername() {
		return this.userName;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return this.active;
	}

}
