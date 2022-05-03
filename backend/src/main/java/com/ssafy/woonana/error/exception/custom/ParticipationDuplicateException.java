package com.ssafy.woonana.error.exception.custom;

public class ParticipationDuplicateException extends RuntimeException {
    private static final String MESSAGE = "이미 참여 신청된 사용자입니다.";
    public ParticipationDuplicateException() {
        super(MESSAGE);
    }
}
