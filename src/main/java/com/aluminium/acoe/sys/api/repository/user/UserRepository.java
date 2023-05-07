package com.aluminium.acoe.sys.api.repository.user;

import com.aluminium.acoe.sys.api.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUserId(String userId);
    void deleteUserByUserId(String userId);
}
