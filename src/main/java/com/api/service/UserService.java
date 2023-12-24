package com.api.service;

import com.api.model.UserModel;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author Miguel Castro
 */
public interface UserService {
    
    public UserModel singup(UserModel userModel);
    
    public UserModel update(Long id, UserModel userModel);
    
    public UserModel disabled(Long id);
    
    public List<UserModel> list();
    
    public Optional<UserModel> find(Long id);
    
    public void deleteAll();
}
