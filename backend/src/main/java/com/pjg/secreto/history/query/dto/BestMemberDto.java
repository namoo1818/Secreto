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
        String contents = new StringBuilder()
                .append("좋아요 수,")
                .append("조회 수,")
                .append("작성한 댓글 수,")
                .append("작성한 게시글 수,")
                .append(staticsTotalCountDto.getLikeCount() + ",")
                .append(staticsTotalCountDto.getHitCount() + ",")
                .append(staticsTotalCountDto.getRepliesCount() + ",")
                .append(staticsTotalCountDto.getBoardCount())
                .toString();

        return contents;
    }
}
