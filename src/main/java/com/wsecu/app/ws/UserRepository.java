package com.wsecu.app.ws;

import com.wsecu.app.ws.io.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author mark.jones
 */
@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long> {

    UserEntity findUserByUserId(String userId);

    UserEntity findUserByEmail(String email);
}
