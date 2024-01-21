package com.api.repository;

import com.api.enums.RoleEnum;
import com.api.model.UserModel;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Miguel Castro
 */
@Repository
public interface UserRepository extends JpaRepository<UserModel, Long> {

    public Optional<UserModel> findByEmail(String email);

    public UserModel findByRole(RoleEnum role);
}
