package com.pjg.secreto.common.config;

import com.pjg.secreto.board.command.dto.WriteBoardRequestDto;
import com.pjg.secreto.board.command.dto.WriteReplyRequestDto;
import com.pjg.secreto.board.command.service.BoardCommandService;
import com.pjg.secreto.board.common.entity.BoardCategory;
import com.pjg.secreto.room.command.repository.RoomCommandRepository;
import com.pjg.secreto.room.command.repository.RoomUserCommandRepository;
import com.pjg.secreto.room.common.entity.Room;
import com.pjg.secreto.room.common.entity.RoomUser;
import com.pjg.secreto.user.command.repository.UserCommandRepository;
import com.pjg.secreto.user.common.entity.User;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class InitDb {

    private final InitService initService;

    @PostConstruct
    public void init() {
        initService.dbInit();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {


        private final UserCommandRepository userCommandRepository;
        private final RoomCommandRepository roomCommandRepository;
        private final RoomUserCommandRepository roomUserCommandRepository;
        private final BoardCommandService boardCommandService;


        public void dbInit() {
            System.out.println("=================================================== test");
            User user = userCommandRepository.save(
                    new User("test@test.com", "1234", "nickname")
            );

            Room room = roomCommandRepository.save(
                    new Room("room name", null, LocalDateTime.now(), LocalDateTime.now(), true, true, null, null, true, user.getId())
            );

            RoomUser roomUSer = roomUserCommandRepository.save(
                    new RoomUser(user, room, LocalDateTime.now(), LocalDateTime.now(), true, "게임방닉네임", true)
            );

            for(int i = 0; i<100;i++) {
                boardCommandService.writePost(
                        new WriteBoardRequestDto(
                                roomUSer.getId(), "t", "c", null, BoardCategory.BOAST, "aaa", true
                        )
                );
            }

            boardCommandService.writeReply(
                    new WriteReplyRequestDto(
                            roomUSer.getId(), 1L, "댓글입니다.", null, null, true
                    )
            );

        }
    }

}
