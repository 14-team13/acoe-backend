package com.aluminium.acoe.sys.repository;

import com.aluminium.acoe.sys.entity.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    /**
     *  username을 기준으로 User정보를 가져올 때 권한정보도 함께 가져옴
     *  - @EntityGraph : 쿼리 수행 시 Lazy 조회 -> Eager조회
     */
    @EntityGraph(attributePaths = "authorities")
    Optional<User> findOneWithAuthoritiesByUsername(String username);
}