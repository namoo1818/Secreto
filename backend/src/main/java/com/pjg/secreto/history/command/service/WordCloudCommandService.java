package com.pjg.secreto.history.command.service;

import com.pjg.secreto.history.command.dto.WriteManitoWordCloudRequest;

public interface WordCloudCommandService {
    public void writeWordCloudCommand(Long roomId, WriteManitoWordCloudRequest dto);
}
