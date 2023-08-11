package com.tim1.cook.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class Encryption {

	public static String getEncodedPassword(String password) {
		BCryptPasswordEncoder bce = new BCryptPasswordEncoder();
		return bce.encode(password);
	}

	public static boolean validatePassword(String password, String encodedPassword) {
        BCryptPasswordEncoder bce = new BCryptPasswordEncoder();
        String replacedEncodedPassword = encodedPassword.replace("{bcrypt}", "");
        return bce.matches(password, replacedEncodedPassword);
    }

	public static void main(String[] args) {

		System.out.println(getEncodedPassword("testkuvar"));

		/*
		 * System.out.println( validatePassword("T4nj4!",
		 * "{bcrypt}$2a$10$i5j5cOqxVQZTcNK0FlQ0VObTELUhrDlD3mhjKNAzjyrwWdvScpQ0u"));
		 */

	}

}
