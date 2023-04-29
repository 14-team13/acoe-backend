package com.aluminium.acoe.app.admin.persistance;

import com.aluminium.acoe.app.admin.dto.AdminCafeSearchDto;
import com.aluminium.acoe.app.cafe.entity.Cafe;
import java.util.List;

public interface AdminCafeRepositoryCustom {
    List<Cafe> searchListByDynamicCond(AdminCafeSearchDto searchDto);
}
