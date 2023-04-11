package com.aluminium.acoe.app.cafe.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TrdDtlState {
    OPEN_DTL(1, "영업"),
    CLOSE_DTL(2, "폐업");

    private final int cd;
    private final String nm;

}
