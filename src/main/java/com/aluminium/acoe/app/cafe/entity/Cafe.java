package com.aluminium.acoe.app.cafe.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "cafe")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Cafe {

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

    @Column(name = "road_addr", length = 30)
    private String roadAddr;

    @Column(name = "road_post_no", length = 30)
    private String roadPostNo;

    @Column(name = "x")
    private Long x;

    @Column(name = "y")
    private Long y;

    @Column(name = "ref_no", length = 50)
    private String refNo;   // 공공데이터 참조 번호(관리 번호)

}