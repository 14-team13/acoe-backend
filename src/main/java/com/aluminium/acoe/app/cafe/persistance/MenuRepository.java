package com.aluminium.acoe.app.cafe.persistance;

import com.aluminium.acoe.app.cafe.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MenuRepository extends JpaRepository<Menu, Long> {

    /**
     * @param cafeId
     * @return
     */
    List<Menu> findByCafe_CafeId(Long cafeId);

    /**
     * @param franchiseId
     * @return
     */
    List<Menu> findByFranchise_FranchiseId(Long franchiseId);

}