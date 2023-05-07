package com.aluminium.acoe.sys.api.service;

import com.aluminium.acoe.sys.api.entity.user.User;
import com.aluminium.acoe.sys.api.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdminUserService {
    private final UserRepository userRepository;

    public Page<User> getUserList() {
        PageRequest pageRequest = PageRequest.of(0, 10);
        return userRepository.findAll(pageRequest);
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
