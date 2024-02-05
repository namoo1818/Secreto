package com.pjg.secreto.history.common.entity;


import com.pjg.secreto.room.common.entity.Room;
import com.pjg.secreto.room.common.entity.RoomUser;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tbl_word_cloud")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WordCloud {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "room_user_no")
    private RoomUser user;

    private String content;

    private Long value;

    @Builder
    public WordCloud(RoomUser user, String content, Long value) {
        this.user = user;
        this.content = content;
        this.value = value;
    }
}
