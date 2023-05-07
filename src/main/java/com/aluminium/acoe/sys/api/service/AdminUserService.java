package com.aluminium.acoe.sys.api.service;

import com.aluminium.acoe.sys.api.entity.user.User;
import com.aluminium.acoe.sys.api.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminUserService {
    private final UserRepository userRepository;

    public List<User> getUserList() {
        return userRepository.findAll();
    }

    @Transactional
    public void updateUser(User user) {
        User findUser = userRepository.findByUserId(user.getUserId());
        findUser.setUsername(user.getUsername());
        userRepository.save(findUser);
    }

    @Transactional
    public void deleteUser(String userId) {
        userRepository.deleteUserByUserId(userId);
    }
}
