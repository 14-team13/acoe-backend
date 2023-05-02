package com.aluminium.acoe.app.main.persistance;

import com.aluminium.acoe.app.main.entity.Franchise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FranchiseRepository extends JpaRepository<Franchise, Long> {
    /**
     * 사용여부에 따른 프랜차이즈 목록 조회
     * @param useYn
     * @return
     */
    List<Franchise> findAllByUseYn(Boolean useYn);

}