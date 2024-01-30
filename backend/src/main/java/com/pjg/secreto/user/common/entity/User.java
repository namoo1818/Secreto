package com.pjg.secreto.user.common.entity;

import com.pjg.secreto.room.common.entity.RoomUser;
import com.pjg.secreto.user.common.dto.ProviderUser;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "tbl_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_no")
    private Long id;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<RoomUser> roomUsers = new ArrayList<>();

    private String nickname;

    private String password;

    private boolean withdrawalYn;

    private String withdrawalAt;

    private String joinAt;

    private String profileUrl;

    private String provider;

    private String email;

    private String sub;

    public User(ProviderUser providerUser) {
        this.nickname = providerUser.getUsername();
        this.password = providerUser.getPassword();
        this.withdrawalYn = false;
        this.withdrawalAt = null;
        this.joinAt = LocalDateTime.now().toString();
        this.profileUrl = providerUser.getProfileUrl();
        this.provider = providerUser.getProvider();
        this.email = providerUser.getEmail();
        this.sub = providerUser.getSub() == null ? null : providerUser.getSub().toString();
    }

    public User(String email, String password, String nickName){
        this.email = email;
        this.password = password;
        this.nickname = nickName;
        this.provider = "none";
        this.joinAt = LocalDateTime.now().toString();
        this.sub = null;
    }
}
