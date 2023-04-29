package com.aluminium.acoe.app.main.entity;

import com.aluminium.acoe.app.main.dto.FranchiseDto;
import com.aluminium.acoe.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "franchise")
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Franchise extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long franchiseId;

    @Column(name = "franchise_nm", length = 50)
    private String franchiseNm;

    @Column(name = "discountAmt")
    private Long discountAmt;

    @Column(name = "logo_img")
    private byte[] logoImg;

    @Column(name = "useYn")
    private Boolean useYn;


    public static Franchise toEntity(FranchiseDto dto){
        return Franchise.builder()
                .franchiseId(dto.getFranchiseId())
                .franchiseNm(dto.getFranchiseNm())
                .discountAmt(dto.getDiscountAmt())
                .logoImg(dto.getLogoImg())
                .useYn(dto.getUseYn())
                .build();
    }

    public void update(FranchiseDto dto){
        this.franchiseNm = dto.getFranchiseNm();
        this.discountAmt = dto.getDiscountAmt();
        this.logoImg = dto.getLogoImg();
        this.useYn = dto.getUseYn();
    }

}