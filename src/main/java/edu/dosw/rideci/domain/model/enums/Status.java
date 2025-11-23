package edu.dosw.rideci.domain.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Status {

    ACTIVE,
    CANCELLED,
    COMPLETED,
    IN_COURSE

}