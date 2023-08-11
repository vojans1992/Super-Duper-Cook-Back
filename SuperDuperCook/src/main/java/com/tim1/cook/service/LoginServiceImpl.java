package com.tim1.cook.service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Service;
import com.tim1.cook.entities.UserEntity;
import com.tim1.cook.entities.dto.LoginDTO;
import com.tim1.cook.repositories.UserRepository;
import com.tim1.cook.utils.Encryption;

import io.jsonwebtoken.Jwts;

@Service
public class LoginServiceImpl implements LoginService {

	@Autowired
	public UserRepository userRepository;

	@Value("${spring.security.token-duration}")
	private Integer tokenDuration;

	@Autowired
	private SecretKey secretKey;

	@Override
    public LoginDTO login(String username, String password) throws Exception {
        UserEntity user = userRepository.findByUsername(username);

        if (user != null && Encryption.validatePassword(password, user.getPassword())) {
            String token = generateJWTToken(user);
            return new LoginDTO(username, token, user.getRole().getName());
        } else {
            throw new Exception("Invalid username or password");
        }
    }

	private String generateJWTToken(UserEntity userEntity) {
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
