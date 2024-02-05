package com.pjg.secreto.history.command.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WriteManitoWordCloudRequest {
    private String contents;
}
