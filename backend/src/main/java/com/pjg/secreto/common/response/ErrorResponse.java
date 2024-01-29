package com.pjg.secreto.common.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;
@Getter
public class ErrorResponse {

    private final HttpStatus status;
    private final String message;

    @Builder
    public ErrorResponse(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }


}
