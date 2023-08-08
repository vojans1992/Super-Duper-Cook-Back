package com.tim1.cook.service;

import com.tim1.cook.entities.dto.LoginDTO;

public interface LoginService {

	LoginDTO login(String username, String password) throws Exception;

}
