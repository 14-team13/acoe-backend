package com.aluminium.acoe.sys.api.service;

import com.aluminium.acoe.sys.api.entity.user.User;
import com.aluminium.acoe.sys.api.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User getUser(String userId) {
        return userRepository.findByUserId(userId);
    }
}
