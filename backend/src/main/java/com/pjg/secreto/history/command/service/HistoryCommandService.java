package com.pjg.secreto.history.command.service;

import com.pjg.secreto.history.command.dto.WriteManitoWordCloudRequest;

public interface HistoryCommandService {
    void writeWorldCloud(Long roomId, WriteManitoWordCloudRequest dto);
}
