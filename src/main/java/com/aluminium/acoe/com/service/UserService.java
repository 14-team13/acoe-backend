package com.aluminium.acoe.com.service;

import com.aluminium.acoe.dto.SignUpDto;
import com.aluminium.acoe.dto.UserDto;
import com.aluminium.acoe.entity.Authority;
import com.aluminium.acoe.entity.User;
import com.aluminium.acoe.exception.DuplicateMemberException;
import com.aluminium.acoe.exception.NotFoundMemberException;
import com.aluminium.acoe.repository.UserRepository;
import com.aluminium.acoe.util.SecurityUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;


@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * 회원 가입 Service
     *
     * @param userDto
     * @return
     */
    @Transactional
    public UserDto signup(SignUpDto signUpDto) {
        // username이 DB에 존재하는지 검사
        if (userRepository.findOneWithAuthoritiesByUsername(signUpDto.getUsername()).orElse(null) != null)
            throw new DuplicateMemberException(ErrorCode.DUPLICATE_MEMBER.getMessage());

        // username이 없으면 권한 정보 생성 (ROLE_USER)
        Authority authority = Authority.builder()
                .authorityName("ROLE_USER")
                .build();

        // 유저 정보 생성
        User user = User.builder()
                .username(signUpDto.getUsername())
                .password(passwordEncoder.encode(signUpDto.getPassword()))
                .nickname(signUpDto.getNickname())
                .authorities(Collections.singleton(authority))
                .activated(true)
                .build();

        // UserRepos에 저장
        return UserDto.from(userRepository.save(user));
    }

    /**
     * SecurityContext에 저장된 접속 계정 정보 확인 Service
     */
    @Transactional(readOnly = true)
    public UserDto getMyUserWithAuthorities() {
        return UserDto.from(SecurityUtil.getCurrentUsername()
                .flatMap(userRepository::findOneWithAuthoritiesByUsername)
                .orElseThrow(() -> new NotFoundMemberException(ErrorCode.NOT_FOUND_MEMBER.getMessage()))
        );
    }

    // 특정 계정 정보 확인 API Service
    @Transactional(readOnly = true)
    public UserDto getUserWithAuthorities(String username) {
        return UserDto.from(userRepository.findOneWithAuthoritiesByUsername(username).orElse(null));
    }
}