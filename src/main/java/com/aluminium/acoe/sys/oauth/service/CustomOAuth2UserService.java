package com.aluminium.acoe.sys.oauth.service;

import com.aluminium.acoe.sys.api.entity.user.User;
import com.aluminium.acoe.sys.api.repository.user.UserRepository;
import com.aluminium.acoe.sys.oauth.type.ProviderType;
import com.aluminium.acoe.sys.oauth.type.RoleType;
import com.aluminium.acoe.sys.oauth.entity.UserPrincipal;
import com.aluminium.acoe.sys.oauth.exception.OAuthProviderMissMatchException;
import com.aluminium.acoe.sys.oauth.info.OAuth2UserInfo;
import com.aluminium.acoe.sys.oauth.info.OAuth2UserInfoFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User user = super.loadUser(userRequest);

        try {
            return this.process(userRequest, user);
        } catch (AuthenticationException ex) {
            throw ex;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new InternalAuthenticationServiceException(ex.getMessage(), ex.getCause());
        }
    }

    private OAuth2User process(OAuth2UserRequest userRequest, OAuth2User user) {
        ProviderType providerType = ProviderType.valueOf(userRequest.getClientRegistration().getRegistrationId().toUpperCase());

        OAuth2UserInfo userInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(providerType, user.getAttributes());
        User savedUser = userRepository.findByUserId(userInfo.getId());

        if (savedUser != null) {
            if (providerType != savedUser.getProviderType()) {
                throw new OAuthProviderMissMatchException(
                        "Looks like you're signed up with " + providerType + " account. " +
                                "Please use your " + savedUser.getProviderType() + " account to login."
                );
            }
            updateUser(savedUser, userInfo);
        } else
            savedUser = createUser(userInfo, providerType);

        return UserPrincipal.create(savedUser, user.getAttributes());
    }

    private User createUser(OAuth2UserInfo userInfo, ProviderType providerType) {
        LocalDateTime now = LocalDateTime.now();
        User user = new User(
                userInfo.getId(),
                createRandomUsername(),
//                userInfo.getName(),
                userInfo.getEmail(),
                "Y",
                userInfo.getImageUrl(),
                providerType,
                RoleType.USER,
                now,
                now
        );

        return userRepository.saveAndFlush(user);
    }

    private User updateUser(User user, OAuth2UserInfo userInfo) {
        if (userInfo.getName() != null && !user.getUsername().equals(userInfo.getName()))
            user.setUsername(userInfo.getName());

        if (userInfo.getImageUrl() != null && !user.getProfileImageUrl().equals(userInfo.getImageUrl()))
            user.setProfileImageUrl(userInfo.getImageUrl());

        return user;
    }

    private String createRandomUsername() {
        String[] prefix = {"행복한", "기분좋은", "뿌듯한", "귀여운", "즐거운", "사랑스러운", "감동받은", "자랑스러운", "신난", "희망찬"};
        String[] suffix = {"아아메", "따아메", "아카라", "따카라", "아바라", "따바라", "아포가토", "콜드브루", "에스프레소", "프라푸치노"};

        int preIdx = (int)(Math.random() * 9);
        int sufIdx = (int)(Math.random() * 9);

        return prefix[preIdx] + suffix[sufIdx];
    }
}