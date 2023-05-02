package com.aluminium.acoe.app.main.entity;

import com.aluminium.acoe.app.main.dto.MenuDto;
import com.aluminium.acoe.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.Optional;

@Entity
@Table(name = "menu")
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Menu extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long menuId;

    @Column(name = "menu_nm", length = 50)
    private String menuNm;

    @Column(name = "price")
    private Long price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cafe_id")
    private Cafe cafe;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "franchise_id")
    private Franchise franchise;

    public static Menu toEntity(MenuDto dto, Cafe cafe, Franchise franchise){
        return Menu.builder()
                .menuId(dto.getMenuId())
                .menuNm(dto.getMenuNm())
                .price(dto.getPrice())
                .cafe(Optional.ofNullable(cafe).orElse(new Cafe()))
                .franchise(Optional.ofNullable(franchise).orElse(new Franchise()))
                .build();
    }

    public void update(MenuDto dto){
        this.menuNm = dto.getMenuNm();
        this.price = dto.getPrice();
    }

}