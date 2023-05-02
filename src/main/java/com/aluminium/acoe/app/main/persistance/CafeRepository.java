package com.aluminium.acoe.app.main.persistance;

import com.aluminium.acoe.app.admin.persistance.AdminCafeRepositoryCustom;
import com.aluminium.acoe.app.main.entity.Cafe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CafeRepository extends JpaRepository<Cafe, Long>, AdminCafeRepositoryCustom {
    /**
     * 지역 내 모든 카페 조회
     * @param areaCd
     * @return
     */
    List<Cafe> findByAreaCd(Long areaCd);

    /**
     * 영업 상태별 카페 조회
     * @param areaCd
     * @param trdStateCd
     * @return
     */
    List<Cafe> findByAreaCdAndTrdStateCd(Long areaCd, Long trdStateCd);

    List<Cafe> findByCafeNmContains(String keyword);





}