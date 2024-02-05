package com.pjg.secreto.history.common.entity;


import com.pjg.secreto.room.common.entity.Room;
import com.pjg.secreto.room.common.entity.RoomUser;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "tbl_word_cloud")
@Data
public class WordCloud {
    @Id
    @GeneratedValue
    private Long id;

    @OneToOne
    @JoinColumn(name = "room_user_no")
    private RoomUser user;

    private String content;

    private Long value;
}
