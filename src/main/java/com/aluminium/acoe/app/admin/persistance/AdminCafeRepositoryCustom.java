package com.aluminium.acoe.app.admin.persistance;

import com.aluminium.acoe.app.admin.dto.AdminCafeSearchDto;
import com.aluminium.acoe.app.main.entity.Cafe;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface AdminCafeRepositoryCustom {
    Page<Cafe> searchListByDynamicCond(AdminCafeSearchDto searchDto, Pageable pageable);
}
