package com.yinhe.susproject.controller;

import java.io.Serializable;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.security.auth.spi.Util;

import com.yinhe.susproject.service.UserManager;

@Named
@SessionScoped
public class LoginBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8875844441535583633L;

	@Inject
	private Logger log;

	@Inject
	private UserManager um;

	private String username;
	private String password;

	@PostConstruct
	public void initNewMember() {
		String hashedPasswd = Util.createPasswordHash("SHA-256",
				Util.BASE64_ENCODING, null, null, "admin2015");
		log.info("==============>admin2015:" + hashedPasswd);

		/*
		 * if (um.isCallerInRole("admin")) { return "admin_console"; } if
		 * (ctx.isCallerInRole("operator")){ return "operator_console"; }
		 * 
		 * return "guest_console";
		 */
	}

	public String login() {
		String loginForward = um.where2Go();
		if (!"void".equals(loginForward)){
			log.info("welcome back to " + loginForward);
			return loginForward;
		}

		log.info("We are new here, loging!");
		loginForward = um.login(this.username, this.password);
		log.info("we are going " + loginForward);

		return loginForward;
	}

	public String logout() {
		log.info("here we are logout()!");
		String logoutForward = um.logout();

		return logoutForward;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
