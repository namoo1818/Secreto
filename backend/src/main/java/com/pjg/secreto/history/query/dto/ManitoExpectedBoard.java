package com.pjg.secreto.history.query.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
@ToString
public class ManitoExpectedBoard {

//    ru.room_no, ru.room_user_no, ru.nickname, ru.users_manito
//    , sub2.expected_user
//    , ifnull((ru.users_manito = sub2.expected_user), false) predict_correct

//    private Long roomNo;
    private Long roomUserNo;
    private Long manitoRoomUserNo;
    private Long expectedRoomUserNo;
    private boolean predictCorrect;
    private LocalDateTime predictAt;
}
