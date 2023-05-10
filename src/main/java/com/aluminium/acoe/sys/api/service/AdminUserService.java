package com.aluminium.acoe.sys.api.service;

import com.aluminium.acoe.sys.api.dto.UserSearchDto;
import com.aluminium.acoe.sys.api.entity.user.User;
import com.aluminium.acoe.sys.api.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdminUserService {
    private final UserRepository userRepository;

    public Page<User> getUserList(UserSearchDto userSearchDto, Pageable pageable) {

        Page<User> resultList = null;

        if (userSearchDto.getUserId() != null)
            resultList = userRepository.findByUserIdContaining(userSearchDto.getUserId(), pageable);
        else if (userSearchDto.getUsername() != null)
            resultList = userRepository.findByUsernameContaining(userSearchDto.getUsername(), pageable);
        else if (userSearchDto.getEmail() != null)
            resultList = userRepository.findByEmailContaining(userSearchDto.getEmail(), pageable);

        return resultList;
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
