package com.api.service;

import com.api.model.UserModel;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author Miguel Castro
 */
public interface UserService {
    
    UserModel singup(UserModel userModel);
    
    UserModel update(Long id, UserModel userModel);
    
    UserModel disabled(Long id);
    
    List<UserModel> list();
    
    Optional<UserModel> find(Long id);
    
    void deleteAll();
}
