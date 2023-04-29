package com.aluminium.acoe.app.main.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TrdState {
    OPEN(1, "영업/정상"),
    CLOSE(3, "폐업");

    private final int cd;
    private final String nm;

}
