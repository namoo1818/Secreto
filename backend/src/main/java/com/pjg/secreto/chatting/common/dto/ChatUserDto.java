package com.pjg.secreto.chatting.common.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Repository;

@Getter
@Repository
@NoArgsConstructor
@AllArgsConstructor
public class ChatUserDto {
    long chatUsrNo;
    long chatNo;
    long userNo;
    long roomNo;
    String userType;
}
