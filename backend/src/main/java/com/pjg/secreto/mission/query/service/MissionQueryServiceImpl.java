package com.pjg.secreto.mission.query.service;

import com.pjg.secreto.mission.query.dto.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Transactional(readOnly = true)
@Service
public class MissionQueryServiceImpl implements MissionQueryService {

    @Override
    public List<SearchSystemMissionResponseDto> searchSystemMission() {

        List<SearchSystemMissionResponseDto> resultList = new ArrayList<>();

        SearchSystemMissionResponseDto result1 = SearchSystemMissionResponseDto.builder().content("미션 1").build();
        SearchSystemMissionResponseDto result2 = SearchSystemMissionResponseDto.builder().content("미션 2").build();
        SearchSystemMissionResponseDto result3 = SearchSystemMissionResponseDto.builder().content("미션 3").build();
        SearchSystemMissionResponseDto result4 = SearchSystemMissionResponseDto.builder().content("미션 4").build();
        SearchSystemMissionResponseDto result5 = SearchSystemMissionResponseDto.builder().content("미션 5").build();

        resultList.add(result1);
        resultList.add(result2);
        resultList.add(result3);
        resultList.add(result4);
        resultList.add(result5);

        return resultList;
    }

    @Override
    public List<SearchSuddenMissionResponseDto> searchSuddenMission(Long roomNo) {

        List<SearchSuddenMissionResponseDto> resultList = new ArrayList<>();

        SearchSuddenMissionResponseDto result1 = SearchSuddenMissionResponseDto.builder().missionSubmitAt("2024/01/15").content("돌발 미션1").build();
        SearchSuddenMissionResponseDto result2 = SearchSuddenMissionResponseDto.builder().missionSubmitAt("2024/01/17").content("돌발 미션2").build();
        SearchSuddenMissionResponseDto result3 = SearchSuddenMissionResponseDto.builder().missionSubmitAt("2024/01/19").content("돌발 미션3").build();

        resultList.add(result1);
        resultList.add(result2);
        resultList.add(result3);

        return resultList;
    }

    @Override
    public List<SearchMissionListResponseDto> searchMissionList(Long roomNo) {

        List<SearchMissionListResponseDto> resultList = new ArrayList<>();

        SearchMissionListResponseDto result1 = SearchMissionListResponseDto.builder().content("미션1").build();
        SearchMissionListResponseDto result2 = SearchMissionListResponseDto.builder().content("미션2").build();
        SearchMissionListResponseDto result3 = SearchMissionListResponseDto.builder().content("미션3").build();
        SearchMissionListResponseDto result4 = SearchMissionListResponseDto.builder().content("미션4").build();
        SearchMissionListResponseDto result5 = SearchMissionListResponseDto.builder().content("미션5").build();

        resultList.add(result1);
        resultList.add(result2);
        resultList.add(result3);
        resultList.add(result4);
        resultList.add(result5);

        return resultList;
    }

    @Override
    public List<SearchUserMissionListResponseDto> searchUserMissionList(Long roomNo) {

        List<SearchUserMissionListResponseDto> resultList = new ArrayList<>();

        SearchUserMissionListResponseDto result1 = SearchUserMissionListResponseDto.builder().content("미션1")
                .missionReceivedAt("2024/01/19").missionType("individual").missionRerollCount(2).missionCertifyYn(false).build();

        SearchUserMissionListResponseDto result2 = SearchUserMissionListResponseDto.builder().content("미션2")
                .missionReceivedAt("2024/01/17").missionType("common").missionRerollCount(0).missionCertifyYn(true).build();

        SearchUserMissionListResponseDto result3 = SearchUserMissionListResponseDto.builder().content("미션3")
                .missionReceivedAt("2024/01/15").missionType("individual").missionRerollCount(2).missionCertifyYn(true).build();

        resultList.add(result1);
        resultList.add(result2);
        resultList.add(result3);

        return resultList;
    }

    @Override
    public SearchMemoResponseDto searchMemo(Long userMemoNo) {

        SearchMemoResponseDto result = SearchMemoResponseDto.builder().userMemoNo(1L)
                .memo("얘는 확실히 아닌듯").manitoPredictType("NO").memoTo(3L).build();

        return result;
    }
}
