package com.tim1.cook.utils;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.tim1.cook.entities.dto.UserDTO;

@Component
public class UserCustomValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return UserDTO.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		UserDTO user = (UserDTO) target;

		if (user.getPassword() != null && !user.getPassword().equals(user.getRepeatedPassword())) {
			errors.reject("400", "Password must match");
		}

	}

}
