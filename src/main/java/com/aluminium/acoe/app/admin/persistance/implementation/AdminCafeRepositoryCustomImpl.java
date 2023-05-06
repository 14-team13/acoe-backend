package com.aluminium.acoe.app.admin.persistance.implementation;

import static com.aluminium.acoe.app.main.entity.QCafe.cafe;
import static com.aluminium.acoe.app.main.entity.QMenu.menu;

import com.aluminium.acoe.app.admin.dto.AdminCafeSearchDto;
import com.aluminium.acoe.app.admin.persistance.AdminCafeRepositoryCustom;
import com.aluminium.acoe.app.main.entity.Cafe;
import com.aluminium.acoe.common.entity.QueryDslSupport;
import com.querydsl.core.BooleanBuilder;

import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Objects;

@Repository
public class AdminCafeRepositoryCustomImpl extends QueryDslSupport implements AdminCafeRepositoryCustom {
    public AdminCafeRepositoryCustomImpl() {
        super(Cafe.class);
    }

    @Override
    public Page<Cafe> searchListByDynamicCond(AdminCafeSearchDto searchDto, Pageable pageable) {
        return applyPagination(pageable, query -> makeSearchQuery(searchDto));
    }

    private JPAQuery<Cafe> makeSearchQuery(AdminCafeSearchDto searchDto){
        BooleanBuilder builder = this.makeBlnBldr(searchDto);

        return getQueryFactory()
                .selectFrom(cafe)
                .leftJoin(menu).on(menu.cafe.cafeId.eq(cafe.cafeId))
                .where(builder)
                .groupBy(cafe.cafeId)
                .orderBy(cafe.cafeNm.asc());
    }

    private BooleanBuilder makeBlnBldr(AdminCafeSearchDto searchDto) {
        BooleanBuilder builder = new BooleanBuilder();

        if (searchDto.getCafeNm() != null) {
            builder.and(cafe.cafeNm.contains(searchDto.getCafeNm()));
        }
        if (searchDto.getRoadAddr() != null) {
            builder.and(cafe.roadAddr.like(searchDto.getRoadAddr()));
        }
        if (searchDto.getDiscountAmt() != null) {
            builder.and(cafe.discountAmt.eq(searchDto.getDiscountAmt()));
        }
        if (Objects.equals(searchDto.getMenuYn(), Boolean.TRUE)){
            builder.and(menu.isNotNull());
        }
        if (Objects.equals(searchDto.getMenuYn(), Boolean.FALSE)){
            builder.and(menu.isNull());
        }
        return builder;
    }
}
