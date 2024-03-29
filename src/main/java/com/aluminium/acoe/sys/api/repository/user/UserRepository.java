package com.aluminium.acoe.sys.api.repository.user;

import com.aluminium.acoe.sys.api.entity.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUserId(String userId);
    void deleteUserByUserId(String userId);

    Page<User> findByUserIdContaining(String userId, Pageable pageable);
    Page<User> findByUsernameContaining(String username, Pageable pageable);
    Page<User> findByEmailContaining(String email, Pageable pageable);
}
