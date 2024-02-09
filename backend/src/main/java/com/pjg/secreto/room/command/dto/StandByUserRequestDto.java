package com.pjg.secreto.room.command.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StandByUserRequestDto {
    private List<Long> roomUserNos;
}
