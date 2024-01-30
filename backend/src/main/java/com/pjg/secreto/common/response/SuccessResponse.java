package com.pjg.secreto.common.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
@Builder
public class SuccessResponse {

    HttpStatus status;
    String msg;
    Object result;

    public SuccessResponse(HttpStatus status, String msg){
        this.status = status;
        this.msg = msg;
    }
}