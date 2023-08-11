package com.tim1.cook.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.tim1.cook.entities.dto.LoginDTO;
import com.tim1.cook.service.LoginService;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class LoginController {

	@Autowired
	private LoginService loginService;

	private final Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());

	@RequestMapping(path = "api/v1/login", method = RequestMethod.POST)
	public ResponseEntity<?> login(@RequestParam String username, @RequestParam String password) throws Exception {
		logger.info("Login request received for user: " + username);

		try {
			LoginDTO loginDTO = loginService.login(username, password);
			logger.info("Login successful for user: " + username);
			return ResponseEntity.ok(loginDTO);
		} catch (AuthenticationException e) {
			logger.error("Authentication failed for user: " + username);
			return new ResponseEntity<>("Wrong username or password", HttpStatus.UNAUTHORIZED);
		}
	}
}
