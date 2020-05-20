package com.wsecu.app.ws.service;

import com.wsecu.app.ws.shared.dto.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 *
 * @author mark.jones
 */
public interface UserService extends UserDetailsService {

    UserDto findUser(UserDto user);

    UserDto createUser(UserDto user);

    void deleteUser(String userId);

    UserDto updateUser(String userId, UserDto user);

}
