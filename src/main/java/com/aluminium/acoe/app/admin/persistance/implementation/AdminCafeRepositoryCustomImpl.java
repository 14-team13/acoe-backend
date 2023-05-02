package com.aluminium.acoe.app.admin.persistance.implementation;

import static com.aluminium.acoe.app.main.entity.QCafe.cafe;
import static com.querydsl.jpa.JPAExpressions.selectFrom;

import com.aluminium.acoe.app.admin.dto.AdminCafeSearchDto;
import com.aluminium.acoe.app.admin.persistance.AdminCafeRepositoryCustom;
import com.aluminium.acoe.app.main.entity.Cafe;
import com.querydsl.core.BooleanBuilder;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class AdminCafeRepositoryCustomImpl implements AdminCafeRepositoryCustom {

    @Override
    public List<Cafe> searchListByDynamicCond(AdminCafeSearchDto searchDto) {
        BooleanBuilder builder = this.makeBlnBldr(searchDto);

        return selectFrom(cafe)
            .where(builder)
            .orderBy(cafe.cafeNm.asc())
            .fetch();
    }

    private BooleanBuilder makeBlnBldr(AdminCafeSearchDto searchDto) {
        BooleanBuilder builder = new BooleanBuilder();

        if (searchDto.getCafeNm() != null) {
            builder.and(cafe.cafeNm.eq(searchDto.getCafeNm()));
        }
        if (searchDto.getRoadAddr() != null) {
            builder.and(cafe.roadAddr.like(searchDto.getRoadAddr()));
        }
        if (searchDto.getDiscountAmt() != null) {
            builder.and(cafe.discountAmt.eq(searchDto.getDiscountAmt()));
        }
        return builder;
    }
}
