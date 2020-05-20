package com.wsecu.app.ws.service.Impl;

import com.wsecu.app.ws.UserRepository;
import com.wsecu.app.ws.io.entity.UserEntity;
import com.wsecu.app.ws.service.UserService;
import com.wsecu.app.ws.shared.Utils;
import com.wsecu.app.ws.shared.dto.UserDto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 *
 * @author mark.jones
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    Utils utils;

    @Autowired
    BCryptPasswordEncoder bcryptPasswordEncoder;

    /**
     *
     * @param user
     * @return
     */
    @Override
    public UserDto findUser(UserDto user) {

        if (userRepository.findUserByUserId(user.getUserId()) == null) {
            throw new RuntimeException("userid does not exist.");
        }

        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(user, userEntity);
        UserEntity storedUserDetails = userRepository.findUserByUserId(userEntity.getUserId());

        UserDto returnValue = new UserDto();
        BeanUtils.copyProperties(storedUserDetails, returnValue);

        return returnValue;

    }

    /**
     *
     * @param user
     * @return
     */
    @Override
    public UserDto createUser(UserDto user) {

        if (userRepository.findUserByUserId(user.getUserId()) != null) {
            throw new RuntimeException("userid already exists.");
        }

        if (userRepository.findUserByEmail(user.getEmail()) != null) {
            throw new RuntimeException("email already exists.");
        }

        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(user, userEntity);

        String publicUserId = utils.generateUserId(30); //default to 30
        userEntity.setUserId(publicUserId);

        userEntity.setEncryptedPassword(bcryptPasswordEncoder.encode(user.getPassword()));

        UserEntity storedUserDetails = userRepository.save(userEntity);

        UserDto returnValue = new UserDto();
        BeanUtils.copyProperties(storedUserDetails, returnValue);

        return returnValue;

    }

    /**
     *
     * @param userId
     */
    @Override
    public void deleteUser(String userId) {

        UserEntity userEntity = userRepository.findUserByUserId(userId);

        if (userEntity == null) {
            throw new RuntimeException("No User Found.");
        }

        userRepository.delete(userEntity);

    }

    /**
     *
     * @param userId
     * @param user
     * @return
     */
    @Override
    public UserDto updateUser(String userId, UserDto user) {

        UserEntity userEntity = userRepository.findUserByUserId(userId);

        if (userEntity == null) {
            throw new RuntimeException("No record found.");
        }

        userEntity.setFirstName(user.getFirstName());
        userEntity.setLastName(user.getLastName());
        userEntity.setEmail(user.getEmail());

        UserEntity updatedUserDetails = userRepository.save(userEntity);

        UserDto returnValue = new UserDto();
        BeanUtils.copyProperties(updatedUserDetails, returnValue);

        return returnValue;
    }

    @Override
    public UserDetails loadUserByUsername(String string) throws UsernameNotFoundException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
