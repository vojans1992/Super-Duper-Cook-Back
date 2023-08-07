package com.tim1.cook.controllers;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tim1.cook.entities.UserEntity;
import com.tim1.cook.entities.dto.LoginDTO;
import com.tim1.cook.repositories.UserRepository;
import com.tim1.cook.utils.Encryption;

import io.jsonwebtoken.Jwts;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class LoginController {

	@Autowired
	private UserRepository userRepository;

	@Value("${spring.security.token-duration}")
	private Integer tokenDuration;

	@Autowired
	private SecretKey secretKey;

	@RequestMapping(path = "api/v1/login", method = RequestMethod.POST)
	public ResponseEntity<?> login(@RequestParam String username, @RequestParam String password) {
		UserEntity user = userRepository.findByUsername(username);
		if (user != null && Encryption.validatePassword(password, user.getPassword())) {
			// 1. create token
			String token = getJWTToken(user);
			// 2. create response with LoginDTO
			LoginDTO retVal = new LoginDTO(username, token);
			return new ResponseEntity<LoginDTO>(retVal, HttpStatus.OK);
		}
		return new ResponseEntity<>("Wrong usernam or password", HttpStatus.UNAUTHORIZED);
	}

	private String getJWTToken(UserEntity userEntity) {
		List<GrantedAuthority> grantedAuthorities = AuthorityUtils
				.commaSeparatedStringToAuthorityList(userEntity.getRole().getName());

		String token = Jwts.builder().setId("softtekJWT").setSubject(userEntity.getUsername())
				.claim("authorities",
						grantedAuthorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + this.tokenDuration)).signWith(this.secretKey)
				.compact();

		return "Bearer " + token;
	}

}
