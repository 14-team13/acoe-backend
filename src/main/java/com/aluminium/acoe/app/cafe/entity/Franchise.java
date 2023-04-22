package com.aluminium.acoe.app.cafe.entity;

import com.aluminium.acoe.app.cafe.dto.FranchiseDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "franchise")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Franchise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long franchiseId;

    @Column(name = "franchise_nm", length = 50)
    private String franchiseNm;

    @Column(name = "discountAmt")
    private Long discountAmt;

    @Column(name = "logo_img")
    private String logoImg;

    @Column(name = "useYn")
    private Boolean useYn;


    public static Franchise toEntity(FranchiseDto dto){
        return Franchise.builder()
                .franchiseId(dto.getFranchiseId())
                .franchiseNm(dto.getFranchiseNm())
                .discountAmt(dto.getDiscountAmt())
                .logoImg(dto.getLogoImg())
                .build();
    }

}