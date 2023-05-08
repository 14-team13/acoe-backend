package com.aluminium.acoe.app.main.entity;

import com.aluminium.acoe.app.main.dto.CafeDto;
import com.aluminium.acoe.common.entity.BaseEntity;
import jakarta.persistence.*;
import java.math.BigDecimal;

import jakarta.validation.constraints.Digits;
import lombok.*;

@Entity
@Table(name = "cafe")
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Cafe extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long cafeId;

    @Column(name = "cafe_nm", length = 50)
    private String cafeNm;

    @Column(name = "area_cd")
    private Long areaCd;

    @Column(name = "trd_state_cd")
    private Long trdStateCd;

    @Column(name = "dtl_state_cd")
    private Long dtlStateCd;

    @Column(name = "tel_no", length = 30)
    private String telNo;

    @Column(name = "road_addr", length = 200)
    private String roadAddr;

    @Column(name = "road_post_no", length = 30)
    private String roadPostNo;

    @Column(name = "x")
    @Digits(integer = 3, fraction = 6)
    private BigDecimal x;

    @Column(name = "y")
    @Digits(integer = 3, fraction = 6)
    private BigDecimal y;

    @Column(name = "ref_no", length = 50)
    private String refNo;   // 공공데이터 참조 번호(관리 번호)

    @Column(name = "discountAmt")
    private Long discountAmt;

    @Column(name = "appOrderYn")
    private Boolean appOrderYn;

    @Column(name = "kioskYn")
    private Boolean kioskYn;

    @Column(name = "useYn")
    private Boolean useYn;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "franchise_id", updatable = false)
    private Franchise franchise;


    public static Cafe toEntity(CafeDto dto, Franchise franchise){
        return Cafe.builder()
            .cafeId(dto.getCafeId())
            .cafeNm(dto.getCafeNm())
            .areaCd(dto.getAreaCd())
            .trdStateCd(dto.getTrdStateCd())
            .dtlStateCd(dto.getDtlStateCd())
            .telNo(dto.getTelNo())
            .roadAddr(dto.getRoadAddr())
            .roadPostNo(dto.getRoadPostNo())
            .x(dto.getX())
            .y(dto.getY())
            .refNo(dto.getRefNo())
            .appOrderYn(dto.getAppOrderYn())
            .kioskYn(dto.getKioskYn())
            .useYn(dto.getUseYn())
            .discountAmt(dto.getDiscountAmt())
            .franchise(franchise)
            .build();
    }

    public void update(CafeDto dto, Franchise franchise){
        this.cafeNm = dto.getCafeNm();
        this.roadAddr = dto.getRoadAddr();
        this.discountAmt = dto.getDiscountAmt();
        this.appOrderYn = dto.getAppOrderYn();
        this.kioskYn = dto.getKioskYn();
        this.useYn = dto.getUseYn();
        this.franchise = franchise;
        this.x = dto.getX();
        this.y = dto.getY();
    }
}