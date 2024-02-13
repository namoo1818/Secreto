package com.pjg.secreto.history.query.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BestMemberDto implements Comparable<BestMemberDto> {
    private PlayerDto player;
    private Long score;
    private StaticsTotalCountDto staticsTotalCountDto;

    public BestMemberDto(PlayerDto player, StaticsTotalCountDto staticsTotalCountDto) {
        this.player = player;
        this.staticsTotalCountDto = staticsTotalCountDto;
        this.score = staticsTotalCountDto.getTotalScore();
    }

    @Override
    public int compareTo(BestMemberDto other) {
        // 내림차순으로 정렬
        return Long.compare(other.getScore(), this.getScore());
    }

    public String contents(){
        return new StringBuilder()
                .append("좋아요 수: ")
                .append(staticsTotalCountDto.getLikeCount())
                .append(" 조회수: ")
                .append(staticsTotalCountDto.getHitCount())
                .toString();
    }
}
