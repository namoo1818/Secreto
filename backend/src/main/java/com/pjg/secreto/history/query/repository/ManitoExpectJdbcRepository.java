package com.pjg.secreto.history.query.repository;

import com.pjg.secreto.history.query.dto.ManitoExpectedBoard;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Repository
public class ManitoExpectJdbcRepository {

    private final NamedParameterJdbcTemplate template;

    public ManitoExpectJdbcRepository(DataSource dataSource) {
        this.template = new NamedParameterJdbcTemplate(dataSource);
    }

    public List<ManitoExpectedBoard> find(Long roomNo) {
        String sql = "SELECT ru.room_no, ru.room_user_no, ru.nickname, ru.users_manito " +
                "          , sub2.expected_user " +
                "          , IFNULL((ru.users_manito = sub2.expected_user), FALSE) predict_correct " +
                "          , sub2.expected_at"+
                "      FROM tbl_room_user ru " +
                "      LEFT JOIN (SELECT mel.room_user_no, mel.expected_user, mel.expected_at " +
                "                   FROM tbl_manito_expect_log mel " +
                "                   JOIN (SELECT room_user_no, MAX(expected_at) AS latest_expected_at " +
                "                           FROM tbl_manito_expect_log " +
                "                          GROUP BY room_user_no) sub " +
                "                     ON mel.room_user_no = sub.room_user_no AND mel.expected_at = sub.latest_expected_at) sub2 " +
                "        ON ru.room_user_no = sub2.room_user_no " +
                "     WHERE ru.room_no = :room_no AND ru.users_manito IS NOT NULL";

        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("room_no", roomNo);
        log.info("sql={}", sql);
        return template.query(sql, param, manitoExpectedBoardRowMapper());
    }

    private RowMapper<ManitoExpectedBoard> manitoExpectedBoardRowMapper() {
        return (rs, rowNum) -> ManitoExpectedBoard.builder()
                .roomUserNo(rs.getLong("room_user_no"))
                .manitoRoomUserNo(rs.getLong("users_manito"))
                .expectedRoomUserNo(rs.getLong("expected_user"))
                .predictCorrect(rs.getBoolean("predict_correct"))
                .predictAt(rs.getObject("expected_at", LocalDateTime.class))
                .build();
    }
}
