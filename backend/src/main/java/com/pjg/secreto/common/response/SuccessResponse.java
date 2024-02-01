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
    String message;
    Object result;

    public SuccessResponse(HttpStatus status, String message){
        this.status = status;
        this.message = message;
    }
}