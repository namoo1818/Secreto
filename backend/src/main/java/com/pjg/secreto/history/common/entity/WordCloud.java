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
    private RoomUser user;

    private String content;

    private Long weight;
}
