package com.ssafy.backend_chat.domain.dto;

import lombok.Data;

@Data
public class UserInfoDto { // 사용자 정보 저장
    private Long userId;
    private String nickname;
    private String profileUrl;
}
