package com.pjg.secreto.room.query.repository;

<<<<<<<<< Temporary merge branch 1
import com.pjg.secreto.room.common.entity.RoomUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoomUserQueryRepository extends JpaRepository<RoomUser, Long>  {
    Optional<RoomUser> findById(Long id);
=========
import com.pjg.secreto.room.common.entity.Room;
import com.pjg.secreto.room.common.entity.RoomUser;
import com.pjg.secreto.user.common.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface RoomUserQueryRepository extends JpaRepository<RoomUser, Long>, RoomUserQueryRepositoryCustom{

    RoomUser findRoomUserByRoomAndUser(Room room, User user);

    List<RoomUser> findRoomUserByRoom(Room findRoom);
    Optional<RoomUser> findById(Long id);
}
